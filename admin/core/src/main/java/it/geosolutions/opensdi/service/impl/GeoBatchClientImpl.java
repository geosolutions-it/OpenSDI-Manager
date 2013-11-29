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
import it.geosolutions.opensdi.config.GeoBatchRunInfoPostProcessor;
import it.geosolutions.opensdi.config.GeoBatchRunInfoPostProcessorOperation;
import it.geosolutions.opensdi.dao.GeoBatchRunInfoDAO;
import it.geosolutions.opensdi.dto.GeobatchRunInfo;
import it.geosolutions.opensdi.model.FileUpload;
import it.geosolutions.opensdi.operations.GeoBatchOperation;
import it.geosolutions.opensdi.operations.LocalOperation;
import it.geosolutions.opensdi.operations.RemoteOperation;
import it.geosolutions.opensdi.service.GeoBatchClient;
import it.geosolutions.opensdi.service.GeoBatchClientConfiguration;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

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

protected List<GeoBatchRunInfoPostProcessor> postProcessors;

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
    return postProcessAfterOperation(
            getLastRunInfo(updateStatus, false, compositeId),
            GeoBatchRunInfoPostProcessorOperation.READ);
}

/**
 * Obtain last run updated or not for a compositeId
 * 
 * @param updateStatus flag to check status of the run or not
 * @param complete flag to load extra information on the run information
 * @param compositeId unique ID for a file in admin project
 * @return last execution for this file or null if not found
 */
public GeobatchRunInfo getLastRunInfo(Boolean updateStatus, Boolean complete,
        String... compositeId) {
    return postProcessAfterOperation(
            geoBatchRunInfoDAO.getLastRunInfo(updateStatus, compositeId),
            GeoBatchRunInfoPostProcessorOperation.READ, complete);
}

@Override
public GeobatchRunInfo updateRunInfo(String flowUid, String status) {
    return updateRunInfo(flowUid, status, false);
}

/**
 * Update execution status
 * 
 * @param runUid to be updated
 * @param status new status
 * @param complete obtain a complete instance of run information
 * @return updated run info
 */
public GeobatchRunInfo updateRunInfo(String runUid, String status,
        Boolean complete) {
    return postProcessAfterOperation(
            geoBatchRunInfoDAO.updateRunInfo(runUid, status),
            GeoBatchRunInfoPostProcessorOperation.UPDATE, complete);
}

@Override
public GeobatchRunInfo saveRunInfo(Object[] parameters,
        GeoBatchOperation operation) {
    return postProcessAfterOperation(
            geoBatchRunInfoDAO.saveRunInfo(parameters, operation),
            GeoBatchRunInfoPostProcessorOperation.CREATE);
}

@Override
public GeobatchRunInfo saveRunInfo(String runUid, LocalOperation operation,
        RESTRunInfo runInfo) {
    return postProcessAfterOperation(
            geoBatchRunInfoDAO.saveRunInfo(runUid, operation, runInfo),
            GeoBatchRunInfoPostProcessorOperation.CREATE);
}

@Override
public GeobatchRunInfo saveRunInfo(String runUid, RemoteOperation operation,
        FileUpload uploadFile) {
    return postProcessAfterOperation(
            geoBatchRunInfoDAO.saveRunInfo(runUid, operation, uploadFile),
            GeoBatchRunInfoPostProcessorOperation.CREATE);
}

@Override
public List<GeobatchRunInfo> getRunInfo(Boolean updateStatus,
        String... compositeId) {
    return postProcessAfterOperation(
            geoBatchRunInfoDAO.getRunInfo(updateStatus, compositeId),
            GeoBatchRunInfoPostProcessorOperation.READ);
}

/**
 * Register a post processor instance
 * 
 * @param postProcessor to be register
 */
public void registerPostProcessor(GeoBatchRunInfoPostProcessor postProcessor) {
    if (this.postProcessors == null) {
        postProcessors = new LinkedList<GeoBatchRunInfoPostProcessor>();
    }
    postProcessors.add(postProcessor);
}

/**
 * Clean all run information for a compositeId
 * 
 * @param compositeId unique ID for a file in admin project
 */
public void cleanRunInformation(String... compositeId) {
    GeobatchRunInfo lastRun = geoBatchRunInfoDAO
            .cleanRunInformation(compositeId);
    if (lastRun != null) {
        // call to post processor
        postProcessAfterOperation(lastRun,
                GeoBatchRunInfoPostProcessorOperation.DELETE);
    }
}

/**
 * Process a list of GeoBatch run information DTO
 * 
 * @param originList
 * @return post processed list
 */
protected List<GeobatchRunInfo> postProcessAfterOperation(
        List<GeobatchRunInfo> originList, Object... parameters) {
    if (originList != null && !originList.isEmpty()) {
        // Process each bean, GeoBatchRunInfoPostProcessorOperation.CREATE
        List<GeobatchRunInfo> result = new LinkedList<GeobatchRunInfo>();
        for (GeobatchRunInfo origin : originList) {
            result.add(postProcessAfterOperation(origin, parameters));
        }
        return result;
    } else {
        // Nothing to process
        return originList;
    }
}

/**
 * Process a GeoBatch run information DTO
 * 
 * @param origin to process
 * @param parameters for the post processor
 * @return processed bean
 */
protected GeobatchRunInfo postProcessAfterOperation(GeobatchRunInfo origin,
        Object... parameters) {
    GeobatchRunInfo result = origin;
    if (this.postProcessors != null) {
        for (GeoBatchRunInfoPostProcessor postProcessor : this.postProcessors) {
            origin = postProcessor
                    .postProcessAfterOperation(origin, parameters);
        }
    }
    return result;
}

/**
 * Process a GeoBatch run information DTO
 * 
 * @param origin to process
 * @param parameters for the post processor
 * @return processed bean
 */
protected GeobatchRunInfo postProcessBeforeOperation(GeobatchRunInfo origin,
        Object... parameters) {
    GeobatchRunInfo result = origin;
    if (this.postProcessors != null) {
        for (GeoBatchRunInfoPostProcessor postProcessor : this.postProcessors) {
            origin = postProcessor
                    .postProcessAfterOperation(origin, parameters);
        }
    }
    return result;
}

}
