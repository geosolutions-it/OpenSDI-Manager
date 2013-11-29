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
package it.geosolutions.opensdi.destination.config;

import it.geosolutions.opensdi.config.GeoBatchRunInfoPostProcessor;
import it.geosolutions.opensdi.config.GeoBatchRunInfoPostProcessorOperation;
import it.geosolutions.opensdi.destination.dto.TraceDto;
import it.geosolutions.opensdi.destination.service.TraceService;
import it.geosolutions.opensdi.dto.GeobatchRunInfo;
import it.geosolutions.opensdi.service.GeoBatchClient;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * GeoBatchRunInformation post processor interface.
 * 
 * @author adiaz
 * @see GeoBatchClient
 */
public class DestinationGeoBatchRunInfoPostProcessor extends
        GeoBatchRunInfoPostProcessor {

/**
 * String key for a trace element
 */
public static String DESTINATION_TRACE_KEY = "trace";

@Autowired
TraceService traceService;

/**
 * Process a GeobatchrunInfo bean adding trace element if found
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
        case READ:
        case UPDATE:
            // create, read and update it's the same method
            runInformation = postProcessRead(runInformation,
                    parameters.length > 1 && Boolean.TRUE.equals(parameters[1]));
            break;
        case DELETE:
            runInformation = postProcessDelete(runInformation);
            break;
        default:
            // not need to process
            break;
        }
    }

    return runInformation;
}

/**
 * Process a read operation to add some extra information
 * 
 * @param runInformation to be processed
 * @param complete indicates if we need to complete the run information with a
 *        trace element
 * @return run information with trace element in extra information if complete
 *         it's true and the trace it's found
 */
public GeobatchRunInfo postProcessRead(GeobatchRunInfo runInformation,
        Boolean complete) {
    if (complete) {
        TraceDto trace = traceService.searchClosestTraceByRun(runInformation);
        if (trace != null) {
            Map<String, Object> extraInformation = runInformation
                    .getExtraInformation();
            if (extraInformation == null) {
                extraInformation = new HashMap<String, Object>();
            }
            extraInformation.put(DESTINATION_TRACE_KEY, trace);
            runInformation.setExtraInformation(extraInformation);
        }
    }
    return runInformation;
}

/**
 * Process a delete operation. Also delete trace elements related
 * 
 * @param runInformation to deleted
 * @return the same run information (but trace element are removed)
 */
private GeobatchRunInfo postProcessDelete(GeobatchRunInfo runInformation) {
    traceService.cleanByRun(runInformation);
    return runInformation;
}

}
