#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
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

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartFile;

/**
 * Custom operation. Example for generate a specific operation
 * 
 * @author adiaz
 */
@Controller
public class CustomOperation extends GeoBatchOperationImpl implements
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

public String getRESTPath() {
    return getPath();
}

public List<String> getExtensions() {
    List<String> l = new ArrayList<String>();
    for (String s : extensions) {
        l.add(s);
    }
    return l;
}

public boolean isMultiple() {
    return false;
}

public String getJsp() {
    return "custom";
}

/**
 * @param path the path to set
 */
public void setPath(String path) {
    this.path = path;
}

public String getJsp(ModelMap model, HttpServletRequest request,
        List<MultipartFile> files) {

    // TODO: Include model data

    return "custom";

}

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