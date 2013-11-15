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
package it.geosolutions.operations;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;

@Controller
public class GeotiffPublishOperation extends SingleFileLocalOperation {
	
	/**
	 * The name of this Operation
	 */
	public static String name = "GeotiffPublish";
	
	/**
	 * The path were to GET the form and POST the request
	 * Typically all lower case
	 */
	private String path;
	
	/**
	 * File extension this Operation will work on
	 */
	private String[] extensions = {"tiff"};
	
	/**
	 * Directory where to scan for files
	 */

	
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

	// TODO: This jsp should be placed in a common folder, set in the OperationManager (OperationMapping)
	@Override
	public String getJsp() {
		return "geotiffpublish";
	}

	/**
	 * @param path the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}

}