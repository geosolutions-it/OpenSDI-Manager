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

import it.geosolutions.opensdi.dto.GeobatchRunInfo;
import it.geosolutions.opensdi.utils.GeoBatchRunInfoUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;

/**
 * Flow log operation. Obtain log of a selected file or of all runs on GeoBatch
 * 
 * @author adiaz
 */
@Controller
public class FlowLogOperation extends GeoBatchOperationImpl implements
        GeoBatchOperation {

/**
 * The name of this Operation
 */
public static String name = "FlowLog";

/**
 * The path were to GET the form and POST the request Typically all lower case
 */
private String path = "flowlog";

/**
 * File extension this Operation will work on
 */
private String[] extensions = {};

private Boolean showPath;

/**
 * @return the name
 */
public String getName() {
    return name;
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

@Override
public String getJsp() {
    return "flowlog";
}

/**
 * @param path the path to set
 */
public void setPath(String path) {
    this.path = path;
}

@Override
public String getJsp(ModelMap model, HttpServletRequest request,
        List<File> files) {

    String id = request.getParameter("id");
    String returnUrl = request.getParameter("returnUrl");
    String update = request.getParameter("update");
    
    model.addAttribute("returnUrl", returnUrl);
    model.addAttribute("showPath", showPath);

    List<GeobatchRunInfo> runInformation;
    if(id != null){
        model.addAttribute("id", id);
        String [] compositeId = GeoBatchRunInfoUtils.getCompositeId(id);
        if(compositeId != null && compositeId.length == 3){
            model.addAttribute("path", compositeId[0]);
            model.addAttribute("fileName", compositeId[1]);
            model.addAttribute("operation", compositeId[2]);
        }
        runInformation = geobatchClient.getRunInfo(
                update != null, id);
    }else{
        runInformation = geobatchClient.getRunInfo(
                update != null);
    }

    model.addAttribute("runInformation", runInformation);

    return "flowlog";

}

@Override
public Object getBlob(Object inputParam, HttpServletRequest postRequest) {

    HttpServletRequest request = (HttpServletRequest) ((Object[]) inputParam)[1];
    ModelMap model = (ModelMap) ((Object[]) inputParam)[2];

    return getJsp(model, request, null);
}

@Override
public String getFlowID() {
    // unused
    return null;
}

/**
 * @return the showPath
 */
public Boolean getShowPath() {
    return showPath;
}

/**
 * @param showPath the showPath to set
 */
public void setShowPath(Boolean showPath) {
    this.showPath = showPath;
}

public String getDefaultBaseDir() {
    // Don't use because the base dir it's on id parameter
    return "/";
}

}