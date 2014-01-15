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
package it.geosolutions.opensdi.operations;

import it.geosolutions.geobatch.services.rest.model.RESTRunInfo;
import it.geosolutions.opensdi.config.RunCleanerPostProcessor;
import it.geosolutions.opensdi.utils.ControllerUtils;
import it.geosolutions.opensdi.utils.GeoBatchRunInfoUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;

/**
 * Run operation that creates a temporal empty file inside getGbinputdirString
 * with the same name of the gotParam, but with getExecuteExtension instead the
 * default extension
 * 
 * @author adiaz
 */
@Controller
public class RunOperation extends FileOperation {

private final static Logger LOGGER = Logger.getLogger(RunOperation.class);

/**
 * Flag to indicate that RunCleanerPostProcessor needed has been registred
 */
private Boolean POST_PROCESSOR_REGISTRED = Boolean.FALSE;

/**
 * GeoBatch input run directory
 */
private String gbinputdirString;

/**
 * Executor extension. Default it's '.run'
 */
private String executeExtension = ".run";

/**
 * Auxiliary function to register a post processor to remove files after the execution
 */
public void init(){
    if(!POST_PROCESSOR_REGISTRED){
        RunCleanerPostProcessor postProcessor =  new RunCleanerPostProcessor();
        String [] cleanExtensions = {getExecuteExtension()};
        postProcessor.setCleanExtensions(cleanExtensions);
        postProcessor.setWorkPath(getBasedirString());
        geobatchClient.registerPostProcessor(postProcessor);
        POST_PROCESSOR_REGISTRED = Boolean.TRUE;
    }
}

/**
 * Create a temporal empty file inside getGbinputdirString with the same name of
 * the gotParam, but with getExecuteExtension instead the default extension
 */
public Object getBlob(Object inputParam, HttpServletRequest request) {
    
    // register post processor if needed
    init();

    String fileName = request.getParameter("fileName");
    RESTRunInfo runInfo = null;
    try {

        if (fileName == null) {
            fileName = (String) inputParam;
        }
        fileName = ControllerUtils.preventDirectoryTrasversing(fileName);
        runInfo = new RESTRunInfo();
        List<String> flist = new ArrayList<String>();

        // create an empty file
        File outputFile;
        String runTimeDir = getRunTimeDir();
        // Files should be removed after run @see init function
        String outputPath = GeoBatchRunInfoUtils
                .cleanDuplicateSeparators(runTimeDir
                        + GeoBatchRunInfoUtils.SEPARATOR
                        + GeoBatchRunInfoUtils.replaceExtension(fileName,
                                getExecuteExtension()));
        outputFile = new File(outputPath);
        outputFile.createNewFile();
        String fullPath = outputFile.getAbsolutePath();
        flist.add(fullPath);

        runInfo.setFileList(flist);

        // if it's confirmed, we're going to remove old information
        if (Boolean.TRUE.equals(cleanLogInformation)) {
            geobatchClient.cleanRunInformation(getVirtualPath(runTimeDir),
                    fileName, getName());
        }
    } catch (Exception e) {
        LOGGER.error(e);
    }

    return runInfo;

}

/**
 * Prepare jsp initializing model if needed
 * 
 * @param model
 * @param fileName
 */
public void prepareGetJsp(ModelMap model, String fileName) {
    super.prepareGetJsp(model, getFinalFileName(fileName));
}

@Override
public boolean isMultiple() {
    return false;
}

/**
 * @return the gbinputdirString
 */
public String getGbinputdirString() {
    return gbinputdirString;
}

/**
 * @param gbinputdirString the gbinputdirString to set
 */
public void setGbinputdirString(String gbinputdirString) {
    this.gbinputdirString = gbinputdirString;
}

/**
 * @return the executeExtension
 */
public String getExecuteExtension() {
    return executeExtension;
}

/**
 * @param executeExtension the executeExtension to set
 */
public void setExecuteExtension(String executeExtension) {
    this.executeExtension = executeExtension;
}

/**
 * Obtain a virtual path for a file managed from this operation. Also remove
 * file ext and replace with the correctly {@link RunOperation#executeExtension}
 * 
 * @param originalPath
 * @return virtual path for the operation
 */
public String getVirtualPath(String original) {
    return GeoBatchRunInfoUtils.getRunInfoPath(original, getDefaultBaseDir(),
            getExecuteExtension());
}

private String getFinalFileName(String fileName) {
    return GeoBatchRunInfoUtils.replaceExtension(fileName,
            getExecuteExtension());
}

}