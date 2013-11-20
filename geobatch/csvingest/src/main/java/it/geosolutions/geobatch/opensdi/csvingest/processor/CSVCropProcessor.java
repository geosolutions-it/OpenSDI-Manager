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

import it.geosolutions.geobatch.opensdi.csvingest.utils.CSVPropertyType;
import it.geosolutions.opensdi.model.CropData;
import it.geosolutions.opensdi.persistence.dao.GenericNRLDAO;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * @author ETj (etj at geo-solutions.it)
 * @author adiaz refactor to use GenericCSVProcessor
 */
public class CSVCropProcessor extends GenericCSVProcessor<CropData, Long> {

private final static List<String> HEADERS = Collections.unmodifiableList(Arrays
        .asList("*", "crop", "distr", "prov", "year", "years", "area", "prod",
                "yield"));

static List<CSVPropertyType> TYPES;
static {
    TYPES = new LinkedList<CSVPropertyType>();
    // "*",
    TYPES.add(CSVPropertyType.IGNORE);
    // "crop"
    TYPES.add(CSVPropertyType.STRING);
    // "distr"
    TYPES.add(CSVPropertyType.STRING);
    // "prov"
    TYPES.add(CSVPropertyType.STRING);
    // "year"
    TYPES.add(CSVPropertyType.INTEGER);
    // "years"
    TYPES.add(CSVPropertyType.STRING);
    // "area"
    TYPES.add(CSVPropertyType.DOUBLE);
    // "prod"
    TYPES.add(CSVPropertyType.DOUBLE);
    // "yield"
    TYPES.add(CSVPropertyType.DOUBLE);
}

static List<Integer> PK_PROPERTIES;
static {
    PK_PROPERTIES = new LinkedList<Integer>();
    // crop , district , province , year
    PK_PROPERTIES.add(1);
    PK_PROPERTIES.add(2);
    PK_PROPERTIES.add(3);
    PK_PROPERTIES.add(4);
}

@Override
public List<Integer> getPkProperties() {
    return PK_PROPERTIES;
}

@Override
public List<String> getHeaders() {
    return HEADERS;
}

@Override
public GenericNRLDAO<CropData, Long> getDao() {
    return cropDataDAO;
}

@Override
public List<CSVPropertyType> getTypes() {
    return TYPES;
}

public CropData merge(CropData old, Object[] properties) {
    CropData cropData;
    if (old != null) {
        cropData = (CropData) old;
    } else {
        cropData = new CropData();
    }
    int idx = 1;
    String cropDescrId = (String) properties[idx++];
    // all descriptors are in lower case!!
    cropData.setCropDescriptor(getCropDescriptors().get(cropDescrId.toLowerCase()));
    cropData.setDistrict((String) properties[idx++]);
    cropData.setProvince((String) properties[idx++]);
    cropData.setYear((Integer) properties[idx++]);
    cropData.setYears((String) properties[idx++]);
    cropData.setArea((Double) properties[idx++]);
    cropData.setProduction((Double) properties[idx++]);
    cropData.setYield((Double) properties[idx++]);
    return cropData;
}

public void save(CropData entity) {
    cropDataDAO.save(entity);
}

public void persist(CropData entity) {
    cropDataDAO.persist(entity);
}

}
