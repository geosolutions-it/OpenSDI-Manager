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
package it.geosolutions.opensdi.dao;

import it.geosolutions.geobatch.services.rest.model.RESTRunInfo;
import it.geosolutions.opensdi.dto.GeobatchRunInfo;
import it.geosolutions.opensdi.model.FileUpload;
import it.geosolutions.opensdi.operations.GeoBatchOperation;
import it.geosolutions.opensdi.operations.LocalOperation;
import it.geosolutions.opensdi.operations.RemoteOperation;

import java.util.List;

/**
 * DAO interface for GeoBatch runs
 * 
 * @author adiaz
 */
public interface GeoBatchRunInfoDAO {

/**
 * Obtain run information for a file identified by compositeId
 * 
 * @param compositeId identifier of the file (to concatenate)
 * 
 * @return List of runs for a composite id
 */
public List<GeobatchRunInfo> getRunInfo(String... compositeId);

/**
 * Obtain last run for a compositeId
 * 
 * @param compositeId unique ID for a file
 * 
 * @return last execution for this file or null if not found 
 */
public GeobatchRunInfo getLastRunInfo(String... compositeId);

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
 * Obtain a execution for a known runUid and file
 * 
 * @param runUid
 * @param compositeId unique ID for a file
 * 
 * @return run information or null if not found
 */
public GeobatchRunInfo getRunInfoByUid(String runUid, String... compositeId); 

/**
 * Obtain a execution for a known runUid and file
 * 
 * @param runUid
 * @param fileId unique ID for a file
 * 
 * @return run information or null if not found
 */
public GeobatchRunInfo searchUnique(String runUid, String fileId);

/**
 * Obtain all executions for a known file
 * 
 * @param fileId unique ID for a file
 * 
 * @return all runs or null if not found
 */
public List<GeobatchRunInfo> search(String fileId); 

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
 * Update run information
 * 
 * @param runInfo
 * 
 * @return run information updated
 */
public GeobatchRunInfo updateRunInfo(GeobatchRunInfo runInfo);


/**
 * Save run information for a GeoBatch operation
 * 
 * @param obj parameters for the operation
 * @param operation executed
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
