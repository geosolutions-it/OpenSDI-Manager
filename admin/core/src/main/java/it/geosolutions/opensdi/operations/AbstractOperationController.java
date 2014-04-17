/*
 *  OpenSDI Manager
 *  Copyright (C) 2014 GeoSolutions S.A.S.
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

import it.geosolutions.opensdi.utils.ControllerUtils;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

/**
 * Abstract operation controller to add default implementation of the JSON
 * response
 * 
 * @author adiaz
 */
public abstract class AbstractOperationController implements Operation {

private final static Logger LOGGER = Logger
        .getLogger(AbstractOperationController.class);

/**
 * This method could be redefined to allow less operations that needed on getJsp method.
 * For example, you can @see {@link FileBrowserOperationController#getRestResponse(ModelMap, HttpServletRequest, List)}
 * 
 * @return the JSON response on file upload
 */
public Map<String, Object> getRestResponse(ModelMap model,
        HttpServletRequest request, List<File> files) {

    Map<String, Object> response = new HashMap<String, Object>();
    try {
        response.put(ControllerUtils.SUCCESS, true);
        response.put(ControllerUtils.ROOT, getJsp(model, request, files));
    } catch (Exception e) {
        LOGGER.error("Error uploading files", e);
        response.put(ControllerUtils.SUCCESS, false);
        response.put(ControllerUtils.ROOT, e.getLocalizedMessage());
    }
    return response;
}

}