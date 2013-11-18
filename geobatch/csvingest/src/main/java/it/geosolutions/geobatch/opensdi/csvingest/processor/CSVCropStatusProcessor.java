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
package it.geosolutions.geobatch.opensdi.csvingest.processor;

import it.geosolutions.geobatch.opensdi.csvingest.utils.CSVIngestUtils;
import it.geosolutions.opensdi.model.CropStatus;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import au.com.bytecode.opencsv.CSVReader;

import com.googlecode.genericdao.search.Search;

/**
 *
 * @author adiaz
 */
public class CSVCropStatusProcessor extends CSVProcessor {

    private final static Logger LOGGER = LoggerFactory.getLogger(CSVCropStatusProcessor.class);

    private final static List<String> HEADERS =
            Collections.unmodifiableList(Arrays.asList("*","factor","crop","month","dec","max","min","opt"));

    public CSVCropStatusProcessor() {
    }


    @Override
    public List<String> getHeaders() {
        return HEADERS;
    }

    @Override
    public void process(CSVReader reader) throws CSVProcessException {
        String nextLine[];
        long ok = 0;

        try {
            while ((nextLine = reader.readNext()) != null) {
                int idx = 0;
                idx++; // rowid not needed
                
                // Parameters into the CSV
                String factor = nextLine[idx++];
                String crop = nextLine[idx++];
                String month = nextLine[idx++];
                Integer dec = Integer.parseInt(nextLine[idx++]);
                Double max =  CSVIngestUtils.getDoubleValue(nextLine[idx++]);
                Double min = CSVIngestUtils.getDoubleValue(nextLine[idx++]);
                Double opt = CSVIngestUtils.getDoubleValue(nextLine[idx++]);
        		Integer sDec = CSVIngestUtils.getDecad(month, dec);

                try {

            		if(max == null && min == null && opt == null){
            			// we need to remove it!!
            			cropStatusDAO.removeByPK(crop, month, factor, dec);
            		}else {
            			// looking for old record 
                    	Search search = new Search(CropStatus.class);
                    	search.addFilterEqual("crop", crop);
                    	search.addFilterEqual("month", month);
                    	search.addFilterEqual("factor", factor);
                    	search.addFilterEqual("dec", dec);
                    	CropStatus oldCropStatus = cropStatusDAO.searchUnique(search);
                    	if(oldCropStatus != null){
	            			// update!!
	            			oldCropStatus.setMax(max);
	                		oldCropStatus.setMin(min);
	                		oldCropStatus.setOpt(opt);
	                		oldCropStatus.setS_dec(sDec);
	                        cropStatusDAO.save(oldCropStatus);
                    	}else{
	                		// it's a new record
	                        CropStatus cropStatus = new CropStatus();
	                		// pk
	                        cropStatus.setFactor(factor);
	                        cropStatus.setCrop(crop);
	                        cropStatus.setMonth(month);
	                        cropStatus.setDec(dec);
	                        // data
	                        cropStatus.setMax(max);
	                        cropStatus.setMin(min);
	                        cropStatus.setOpt(opt);
	                        cropStatus.setS_dec(sDec);
	                        cropStatusDAO.persist(cropStatus);
                    	}
                	}

                    ok++;
                } catch (Exception e) {
                    throw new CSVProcessException("Could not persist #" + ok + " CropStatus", e);
                }
            }
        } catch (IOException e) {
            throw new CSVProcessException("Error in reading CSV file", e);
        }

        LOGGER.info("Crop status ingestion -- persisted "+ ok + " cropstatus");
    }

}
