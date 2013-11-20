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
import it.geosolutions.geobatch.opensdi.csvingest.utils.CSVPropertyType;
import it.geosolutions.opensdi.model.CropStatus;
import it.geosolutions.opensdi.persistence.dao.GenericNRLDAO;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author adiaz
 */
public class CSVCropStatusProcessor extends
        GenericCSVProcessor<CropStatus, Long> {

private final static Logger LOGGER = LoggerFactory
        .getLogger(CSVCropStatusProcessor.class);

private final static List<String> HEADERS = Collections.unmodifiableList(Arrays
        .asList("*", "factor", "crop", "month", "dec", "max", "min", "opt"));

@Override
public List<String> getHeaders() {
    return HEADERS;
}

static List<CSVPropertyType> TYPES;
static {
    TYPES = new LinkedList<CSVPropertyType>();
    // "*",
    TYPES.add(CSVPropertyType.IGNORE);
    // "factor"
    TYPES.add(CSVPropertyType.STRING);
    // "crop"
    TYPES.add(CSVPropertyType.STRING);
    // "month"
    TYPES.add(CSVPropertyType.STRING);
    // "dec"
    TYPES.add(CSVPropertyType.INTEGER);
    // "max"
    TYPES.add(CSVPropertyType.DOUBLE);
    // "min"
    TYPES.add(CSVPropertyType.DOUBLE);
    // "opt"
    TYPES.add(CSVPropertyType.DOUBLE);
}

static List<Integer> PK_PROPERTIES;
static {
    PK_PROPERTIES = new LinkedList<Integer>();
    // ID : crop, month, factor, dec);
    PK_PROPERTIES.add(2);
    PK_PROPERTIES.add(3);
    PK_PROPERTIES.add(1);
    PK_PROPERTIES.add(4);
}

@Override
public List<Integer> getPkProperties() {
    return PK_PROPERTIES;
}

@Override
public GenericNRLDAO<CropStatus, Long> getDao() {
    return cropStatusDAO;
}

@Override
public List<CSVPropertyType> getTypes() {
    return TYPES;
}

public CropStatus merge(CropStatus old, Object[] properties) {
    CropStatus cropStatus;
    if (old != null) {
        cropStatus = (CropStatus) old;
    } else {
        cropStatus = new CropStatus();
    }
    int idx = 1;
    // pk
    cropStatus.setFactor((String) properties[idx++]);
    cropStatus.setCrop((String) properties[idx++]);
    String month = (String) properties[idx++];
    cropStatus.setMonth(month);
    Integer dec = (Integer) properties[idx++];
    cropStatus.setDec(dec);
    // data
    cropStatus.setMax((Double) properties[idx++]);
    cropStatus.setMin((Double) properties[idx++]);
    cropStatus.setOpt((Double) properties[idx++]);
    Integer sDec = null;
    try {
        sDec = CSVIngestUtils.getDecad(month, dec);
    } catch (CSVProcessException e) {
        LOGGER.error("Incorrect decad (month,dec) = (" + month + "," + dec
                + ")", e);
    }
    cropStatus.setS_dec(sDec);
    return cropStatus;
}

public void save(CropStatus entity) {
    cropStatusDAO.save(entity);
}

public void persist(CropStatus entity) {
    cropStatusDAO.persist(entity);
}

}
