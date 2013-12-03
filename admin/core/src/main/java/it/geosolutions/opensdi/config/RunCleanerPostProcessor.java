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
package it.geosolutions.opensdi.config;

import it.geosolutions.opensdi.dto.GeobatchRunInfo;
import it.geosolutions.opensdi.service.GeoBatchClient;
import it.geosolutions.opensdi.utils.GeoBatchRunInfoUtils;

import java.io.File;

import org.apache.log4j.Logger;

/**
 * RunCleanerPostProcessor post processor class. This post processor delete
 * unused files after some process. Is used in {@link RunOperation#init()} to remove
 * empty files from the work directory.
 * 
 * @author adiaz
 * @see GeoBatchClient
 * @see RunOperation
 */
public class RunCleanerPostProcessor extends GeoBatchRunInfoPostProcessor {

private final static Logger LOGGER = Logger
        .getLogger(RunCleanerPostProcessor.class);

/**
 * File extensions to be removed
 */
private String[] cleanExtensions;

/**
 * Working path to remove the files
 */
private String workPath;

/**
 * Process a GeobatchrunInfo bean removing executed files @see
 * {@link RunOperation}
 * 
 * @param runInformation
 * @param parameters optional parameters to parse to the operation
 * @return processed bean
 */
public GeobatchRunInfo postProcessAfterOperation(
        GeobatchRunInfo runInformation, Object... parameters) {

    // obtain trace information
    if (runInformation != null
            // Just only if the first parameter it's the complete flag == true
            && parameters != null && parameters.length > 0
            && parameters[0] instanceof GeoBatchRunInfoPostProcessorOperation) {
        GeoBatchRunInfoPostProcessorOperation operation = (GeoBatchRunInfoPostProcessorOperation) parameters[0];
        switch (operation) {
        case CREATE:
            // only need to call to create method (to remove the file)
            runInformation = postProcessCreate(runInformation);
        case READ:
        case UPDATE:
        case DELETE:
        default:
            // not need to process
            break;
        }
    }

    return runInformation;
}

/**
 * Process a create operation to remove file after execute.
 * Removes the temporal file related to runInformation 
 * 
 * @param runInformation to be processed
 * @return run information without changes
 */
public GeobatchRunInfo postProcessCreate(GeobatchRunInfo runInformation) {
    if (runInformation != null) {
        String fileName = GeoBatchRunInfoUtils.getFileName(runInformation,
                false);
        if (fileName != null && cleanExtensions != null && workPath != null) {
            for (String extension : this.cleanExtensions) {
                if (fileName.endsWith(extension)) {
                    // Remove file
                    try {
                        boolean delete = false;
                        File file = new File(workPath
                                + GeoBatchRunInfoUtils.SEPARATOR
                                // Path
                                + runInformation.getCompositeId()[0]
                                // Name
                                + fileName);
                        if (file.exists()) {
                            delete = file.delete();
                        }
                        if (!delete) {
                            LOGGER.info("Error removing file " + fileName);
                        } else {
                            LOGGER.info("File " + fileName
                                    + " removed succesfully");
                        }
                    } catch (Exception e) {
                        LOGGER.error("Error removing file " + fileName);
                    }
                    break;
                }
            }
        }
    }
    return runInformation;
}

/**
 * @return the cleanExtensions
 */
public String[] getCleanExtensions() {
    return cleanExtensions;
}

/**
 * @param cleanExtensions the cleanExtensions to set
 */
public void setCleanExtensions(String[] cleanExtensions) {
    this.cleanExtensions = cleanExtensions;
}

/**
 * @return the workPath
 */
public String getWorkPath() {
    return workPath;
}

/**
 * @param workPath the workPath to set
 */
public void setWorkPath(String workPath) {
    this.workPath = workPath;
}

}
