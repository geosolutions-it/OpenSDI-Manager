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

/**
 * 
 */
package it.geosolutions.opensdi.operations;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Lorenzo Pini
 * @author adiaz add virtualPath function
 */
public interface GeoBatchOperation extends Operation {

/**
 * @return the geobatch flow ID associated to this Operation
 */
public String getFlowID();

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
 * Create Object to be sent to GeoBatchRESTClient
 * 
 * @param request TODO
 * @return Object
 */
public Object getBlob(Object inputParam, HttpServletRequest request);

/**
 * List of Strings representing all the extensions this Operation can work on.
 * Extensions can be specified multiple times if isMultiple == true, meaning
 * that this Operation needs more than one file to run. IE: {'shp', 'shp',
 * 'dxf'}
 * 
 * @return
 */
public List<String> getExtensions();

/**
 * Tells if all the specified Extensions are necessary to run
 * 
 * @return
 */
public boolean isMultiple();

/**
 * Obtain a virtual path for a file managed from this operation
 * 
 * @param originalPath
 * @return virtual path for the operation
 */
public String getVirtualPath(String originalPath);

}
