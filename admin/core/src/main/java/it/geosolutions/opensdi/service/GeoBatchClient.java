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
package it.geosolutions.opensdi.service;

import java.util.List;

import it.geosolutions.geobatch.services.rest.model.RESTRunInfo;
import it.geosolutions.opensdi.dto.GeobatchRunInfo;
import it.geosolutions.opensdi.model.FileUpload;
import it.geosolutions.opensdi.operations.GeoBatchOperation;
import it.geosolutions.opensdi.operations.LocalOperation;
import it.geosolutions.opensdi.operations.RemoteOperation;

/**
 * Simple envelop to use same parameters for GeoBatch connection and operations on all
 * controllers
 * 
 * @author adiaz
 */
public interface GeoBatchClient {

/**
 * @return the geobatchRestUrl
 */
public String getGeobatchRestUrl();

/**
 * @return the geostoreUsername
 */
public String getGeobatchUsername();

/**
 * @return the geostorePassword
 */
public String getGeobatchPassword();

/**
 * @param geobatchUsername the geobatchUsername to set
 */
public void setGeobatchUsername(String geobatchUsername);

/**
 * @param geobatchPassword the geobatchPassword to set
 */
public void setGeobatchPassword(String geobatchPassword);

/**
 * Obtain last run updated or not for a compositeId
 * 
 * @param updateStatus flag to check status of the run or not
 * @param compositeId unique ID for a file in admin project
 * 
 * @return last execution for this file or null if not found 
 */
public GeobatchRunInfo getLastRunInfo(Boolean updateStatus, String... compositeId);

/**
 * Obtain run information for a file identified by compositeId
 * 
 * @param updateStatus flag to check status of the run or not
 * @param compositeId identifier of the file (to concatenate)
 * 
 * @return List of runs for a composite id
 */
public List<GeobatchRunInfo> getRunInfo(Boolean updateStatus, String... compositeId);

/**
 * Update execution status 
 * 
 * @param runUid to be updated
 * @param status new status
 * 
 * @return updated run info
 */
public GeobatchRunInfo updateRunInfo(String runUid, String status);


/**
 * Save run information for a GeoBatch operation
 * 
 * @param obj parameters for the operation
 * @param operation executedrunUid
 * 
 * @return GeobatchRunInfo stored
 */
public GeobatchRunInfo saveRunInfo(Object[] obj, GeoBatchOperation operation);

/**
 * Save run information for a local operation
 * 
 * @param runUid
 * @param operation
 * @param runInfo
 * 
 * @return run information saved
 */
public GeobatchRunInfo saveRunInfo(String runUid, LocalOperation operation, RESTRunInfo runInfo);

/**
 * Save run information for a remote operation
 * 
 * @param runUid
 * @param operation
 * @param uploadFile
 * 
 * @return run information saved
 */
public GeobatchRunInfo saveRunInfo(String runUid, RemoteOperation operation, FileUpload uploadFile);

}
