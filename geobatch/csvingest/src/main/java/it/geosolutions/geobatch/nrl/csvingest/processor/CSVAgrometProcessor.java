/*
 *  Copyright (C) 2007 - 2013 GeoSolutions S.A.S.
 *  http://www.geo-solutions.it
 * 
 *  GPLv3 + Classpath exception
 * 
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 * 
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 * 
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package it.geosolutions.geobatch.nrl.csvingest.processor;

import au.com.bytecode.opencsv.CSVReader;
import it.geosolutions.nrl.model.AgroMet;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author ETj (etj at geo-solutions.it)
 */
public class CSVAgrometProcessor extends CSVProcessor {

    private final static Logger LOGGER = LoggerFactory.getLogger(CSVAgrometProcessor.class);

    //ID_ndv;distr;prov;year;mon;dec;factor;NDVI_avg;
    // last field is a value, but its name may change

    private final static List<String> HEADERS =
            Collections.unmodifiableList(Arrays.asList("*","distr","prov","year","mon","dec","factor","*"));
  
    public CSVAgrometProcessor() {
    }


    @Override
    public List<String> getHeaders() {
        return HEADERS;
    }

    @Override
    public void process(CSVReader reader) throws CSVProcessException{
//    @Transactional(value = "nrlTransactionManager")
//    private void ingestCropProvince(CSVReader reader) throws ActionException {
        String nextLine[];
        long line = 0;

        try {
            while ((nextLine = reader.readNext()) != null) {
                AgroMet agromet = new AgroMet();
                int idx = 0;
                idx++; // rowid not needed
                agromet.setDistrict(nextLine[idx++]);
                agromet.setProvince(nextLine[idx++]);
                agromet.setYear(Integer.parseInt(nextLine[idx++]));
                agromet.setMonth(nextLine[idx++]);
                agromet.setDek(Integer.parseInt(nextLine[idx++]));
                agromet.setFactor(nextLine[idx++]);
                setSVars(agromet);

                String valS = nextLine[idx++];
                if(valS == null || valS.isEmpty()) {
                    // LOGGER.warn("Empty value line#"+line + " for " + agromet);
                    boolean removed = agrometDAO.removeByPK(agromet);
                    if(removed) {
                        LOGGER.info("Removed " + agromet);
                    } else {
                        LOGGER.info("Can't find for removal " + agromet);
                    }
                    continue;
                    //agromet.setValue(0d);
                } else {
                    agromet.setValue(Double.parseDouble(valS));
                }

                try {
                    agrometDAO.persist(agromet);
                    line++;
                } catch (Exception e) {
//                    LOGGER.warn("Could not persist " + cropData, e);
                    throw new CSVProcessException("Could not persist #" + line + " " + agromet, e);
                }
            }
        } catch (IOException e) {
            throw new CSVProcessException("Error in reading CSV file", e);
        }

        LOGGER.info("Agromet ingestion -- persisted "+ line + " entries");
    }

    private void setSVars(AgroMet agromet) {
        Month3 emon = Month3.valueOf(agromet.getMonth().toUpperCase());

        agromet.setS_yr( agromet.getYear()+ emon.getAdd());
        agromet.setS_dec(agromet.getDek() + emon.getPos()*3);
    }


    static enum Month3 {
        JAN("Jan", 2, 0),
        FEB("Feb", 3, 0),
        MAR("Mar", 4, 0),
        APR("Apr", 5, 0),
        MAY("May", 6, 0),
        JUN("Jun", 7, 0),
        JUL("Jul", 8, 0),
        AUG("Aug", 9, 0),
        SEP("Sep", 10, 0),
        OCT("Oct", 11, 0),
        NOV("Nov", 0, 1),
        DEC("Dec", 1, 1);


        private String lit;
        private int pos;
        private int add;

        private Month3(String lit, int pos, int add) {
            this.lit = lit;
            this.pos = pos;
            this.add = add;
        }

        public String getLit() {
            return lit;
        }

        public int getPos() {
            return pos;
        }

        public int getAdd() {
            return add;
        }

    }

}
