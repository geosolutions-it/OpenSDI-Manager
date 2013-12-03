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
public class RunOperation extends SingleFileLocalOperation implements
        RefactorFileOperation {

private final static Logger LOGGER = Logger.getLogger(RunOperation.class);

/**
 * The name of this Operation
 */
public String name = "Run";

/**
 * The path were to GET the form and POST the request Typically all lower case
 */
private String path = "run";

/**
 * File extension this Operation will work on
 */
private String[] extensions = { "zip" };

/**
 * GeoBatch input run directory
 */
private String gbinputdirString;

/**
 * Executor extension. Default it's '.run'
 */
private String executeExtension = ".run";

/**
 * Create a temporal empty file inside getGbinputdirString with the same name of
 * the gotParam, but with getExecuteExtension instead the default extension
 */
public Object getBlob(Object inputParam, HttpServletRequest request) {

    String fileName = request.getParameter("fileName");
    RESTRunInfo runInfo = null;
    try {

        if (fileName == null) {
            fileName = (String) inputParam;
        }
        fileName = ControllerUtils.preventDirectoryTrasversing(fileName);
        fileName = ControllerUtils.removeExtension(fileName)
                + getExecuteExtension();
        runInfo = new RESTRunInfo();
        List<String> flist = new ArrayList<String>();

        String basedirString = getDefaultBaseDir();
        File outputFile;
        outputFile = new File(getGbinputdirString()
                + GeoBatchRunInfoUtils.SEPARATOR + fileName);
        outputFile.createNewFile();
        String fullPath = outputFile.getAbsolutePath();
        flist.add(fullPath);

        runInfo.setFileList(flist);

        // if it's confirmed, we're going to remove old information
        if (Boolean.TRUE.equals(cleanLogInformation)) {
            String runTimeDir = getRunTimeDir();
            geobatchClient.cleanRunInformation(
                    runTimeDir.substring(0, runTimeDir.length() - 1),
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

/**
 * @return the path
 */
public String getPath() {
    return path;
}

@Override
public String getRESTPath() {
    return getPath();
}

@Override
public List<String> getExtensions() {
    List<String> l = new ArrayList<String>();
    for (String s : extensions) {
        l.add(s);
    }
    return l;
}

@Override
public boolean isMultiple() {
    return false;
}

// TODO: This jsp should be placed in a common folder, set in the
// OperationManager (OperationMapping)

/**
 * @param path the path to set
 */
public void setPath(String path) {
    this.path = path;
}

/**
 * @return the name
 */
public String getName() {
    return name;
}

/**
 * @param name the name to set
 */
public void setName(String name) {
    this.name = name;
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

public String getFinalFileName(String original) {
    return getFinalFilePath(ControllerUtils.removeExtension(original)
            + getExecuteExtension());
}

public String getFinalFilePath(String original) {

    String finalPath = original;
    if (original.contains(GeoBatchRunInfoUtils.SEPARATOR)) {
        finalPath = getGbinputdirString()
                + original.substring(original
                        .lastIndexOf(GeoBatchRunInfoUtils.SEPARATOR));
    }
    return finalPath;
}

/**
 * The default directory it's the gb input run
 */
public String getDefaultBaseDir() {
    return getGbinputdirString();
}



}