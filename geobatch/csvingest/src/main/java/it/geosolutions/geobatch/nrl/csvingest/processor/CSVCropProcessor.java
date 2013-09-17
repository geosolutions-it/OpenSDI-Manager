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
import it.geosolutions.nrl.model.CropData;
import it.geosolutions.nrl.model.CropDescriptor;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author ETj (etj at geo-solutions.it)
 */
public class CSVCropProcessor extends CSVProcessor {

    private final static Logger LOGGER = LoggerFactory.getLogger(CSVCropProcessor.class);

    private final static List<String> HEADERS =
            Collections.unmodifiableList(Arrays.asList("*","crop","distr","prov","year","years","area_mHa","prod_mTo","yield_kgHa"));

    public CSVCropProcessor() {
    }


    @Override
    public List<String> getHeaders() {
        return HEADERS;
    }

    @Override
    public void process(CSVReader reader) throws CSVProcessException {
        String nextLine[];
        long ok = 0;

        Map<String, CropDescriptor> cropDescriptors = getCropDescriptors();

        try {
            while ((nextLine = reader.readNext()) != null) {
                CropData cropData = new CropData();
                int idx = 0;
                idx++; // rowid not needed
                String cropDescrId = nextLine[idx++];
                cropData.setCropDescriptor(cropDescriptors.get(cropDescrId));
                cropData.setDistrict(nextLine[idx++]);
                cropData.setProvince(nextLine[idx++]);
                cropData.setYear(Integer.parseInt(nextLine[idx++]));
                cropData.setYears(nextLine[idx++]);
                cropData.setArea(Double.parseDouble(nextLine[idx++]));
                cropData.setProduction(Double.parseDouble(nextLine[idx++]));
                cropData.setYield(Double.parseDouble(nextLine[idx++]));

                if( cropData.getCropDescriptor() == null ) {
                    throw new CSVProcessException("CropDescriptor not found " + cropDescrId);
                }

                try {
                    cropDataDAO.persist(cropData);
                    ok++;
                } catch (Exception e) {
//                    LOGGER.warn("Could not persist " + cropData, e);
                    throw new CSVProcessException("Could not persist #" + ok + " " + cropData, e);
                }
            }
        } catch (IOException e) {
            throw new CSVProcessException("Error in reading CSV file", e);
        }

        LOGGER.info("Crop data ingestion -- persisted "+ ok + " cropdata");
    }

}
