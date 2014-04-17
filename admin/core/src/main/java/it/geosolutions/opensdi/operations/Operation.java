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

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.ModelMap;

public interface Operation {
	
	/**
	 * @return the name of this Operation
	 */
	public String getName();
	
	/*
	 * Setter
	public void setName(String name);
	 */
	
	
	/**
	 * @return the REST path where to send GET and POST requests
	 */
	public String getRESTPath();
	
	/**
	 * @return the Jsp name to build the GUI
	 */
	public String getJsp();

        /**
         * @return the Jsp name to build the GUI end set the provided model with the right attributes
         */
        public String getJsp(ModelMap model, HttpServletRequest request, List<File> files);

        /**
         * @return the rest JSON response on file upload
         */
        public Map<String, Object> getRestResponse(ModelMap model, HttpServletRequest request, List<File> files);

}
