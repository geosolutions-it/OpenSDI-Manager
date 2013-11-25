/*
 *  OpenSDI Manager
 *  Copyright (C) 2013 GeoSolutions S.A.S.
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
package it.geosolutions.opensdi.service.impl;

import it.geosolutions.geobatch.services.rest.model.RESTRunInfo;
import it.geosolutions.geostore.services.rest.GeoStoreClient;
import it.geosolutions.opensdi.dao.GeoBatchRunInfoDAO;
import it.geosolutions.opensdi.dto.GeobatchRunInfo;
import it.geosolutions.opensdi.model.FileUpload;
import it.geosolutions.opensdi.operations.GeoBatchOperation;
import it.geosolutions.opensdi.operations.LocalOperation;
import it.geosolutions.opensdi.operations.RemoteOperation;
import it.geosolutions.opensdi.service.GeoBatchClient;
import it.geosolutions.opensdi.service.GeoBatchClientConfiguration;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Simple envelop to use same parameters for GeoBatch connection and GeoBatch
 * operations on all controllers
 * 
 * @author adiaz
 */
public class GeoBatchClientImpl implements GeoBatchClient, Serializable {

/** serialVersionUID */
private static final long serialVersionUID = 7299932957642777660L;

@Autowired
protected GeoBatchClientConfiguration geobatchConfiguration;

@Autowired
protected GeoStoreClient geostoreClient;

@Autowired
protected GeoBatchRunInfoDAO geoBatchRunInfoDAO;

/**
 * @return the geobatchRestUrl
 */
public String getGeobatchRestUrl() {
    return geobatchConfiguration.getGeobatchRestUrl();
}

/**
 * @param geobatchRestUrl the geobatchRestUrl to set
 */
public void setGeobatchRestUrl(String geobatchRestUrl) {
    geobatchConfiguration.setGeobatchRestUrl(geobatchRestUrl);
}

/**
 * @return the geobatchUsername
 */
public String getGeobatchUsername() {
    return geobatchConfiguration.getGeobatchUsername();
}

/**
 * @param geobatchUsername the geobatchUsername to set
 */
public void setGeobatchUsername(String geobatchUsername) {
    geobatchConfiguration.setGeobatchUsername(geobatchUsername);
}

/**
 * @return the geobatchPassword
 */
public String getGeobatchPassword() {
    return geobatchConfiguration.getGeobatchPassword();
}

/**
 * @param geobatchPassword the geobatchPassword to set
 */
public void setGeobatchPassword(String geobatchPassword) {
    geobatchConfiguration.setGeobatchPassword(geobatchPassword);
}

/**
 * @return the geoBatchRunInfoDAO
 */
public GeoBatchRunInfoDAO getGeoBatchRunInfoDAO() {
    return geoBatchRunInfoDAO;
}

/**
 * @param geoBatchRunInfoDAO the geoBatchRunInfoDAO to set
 */
public void setGeoBatchRunInfoDAO(GeoBatchRunInfoDAO geoBatchRunInfoDAO) {
    this.geoBatchRunInfoDAO = geoBatchRunInfoDAO;
}

@Override
public GeobatchRunInfo getLastRunInfo(Boolean updateStatus,
        String... compositeId) {
    return geoBatchRunInfoDAO.getLastRunInfo(updateStatus, compositeId);
}

@Override
public GeobatchRunInfo updateRunInfo(String flowUid, String status) {
    return geoBatchRunInfoDAO.updateRunInfo(flowUid, status);
}

@Override
public GeobatchRunInfo saveRunInfo(Object[] parameters, GeoBatchOperation operation) {
    return geoBatchRunInfoDAO.saveRunInfo(parameters, operation);
}

@Override
public GeobatchRunInfo saveRunInfo(String runUid, LocalOperation operation,
        RESTRunInfo runInfo) {
    return geoBatchRunInfoDAO.saveRunInfo(runUid, operation, runInfo);
}

@Override
public GeobatchRunInfo saveRunInfo(String runUid, RemoteOperation operation,
        FileUpload uploadFile) {
    return geoBatchRunInfoDAO.saveRunInfo(runUid, operation, uploadFile);
}

}
