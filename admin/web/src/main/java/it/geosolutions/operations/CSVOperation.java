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

import it.geosolutions.geobatch.services.rest.model.RESTRunInfo;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class CSVOperation implements LocalOperation {
	
	/**
	 * The name of this Operation
	 */
	public static String name = "CSV";
	
	/**
	 * The path were to GET the form and POST the request
	 * Typically all lower case
	 */
	private String path;
	
	/**
	 * File extension this Operation will work on
	 */
	private String[] extensions = {"csv"};
	
	/**
	 * Directory where to scan for files
	 */
	private String basedirString;

	private String geobatchRestUrl;

	private String geobatchUsername;

	private String geobatchPassword;

	private String jspName;
	
	private String flowID;

	/**
	 * Getter
	 * @return the basedirString
	 */
	public String getBasedirString() {
		return basedirString;
	}

	/**
	 * Setter
	 * @param basedirString the basedirString to set
	 */
	public void setBasedirString(String basedirString) {
		this.basedirString = basedirString;
	}

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
		return jspName;
	}

	/**
	 * @param path the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * @return the geostoreRestUrl
	 */
	public String getGeobatchRestUrl() {
		return geobatchRestUrl;
	}

	/**
	 * @param geobatchRestUrl the geostoreRestUrl to set
	 */
	public void setGeobatchRestUrl(String geobatchRestUrl) {
		this.geobatchRestUrl = geobatchRestUrl;
	}

	/**
	 * @return the geostoreUsername
	 */
	public String getGeobatchUsername() {
		return geobatchUsername;
	}

	/**
	 * @param geobatchUsername the geostoreUsername to set
	 */
	public void setGeobatchUsername(String geobatchUsername) {
		this.geobatchUsername = geobatchUsername;
	}

	/**
	 * @return the geostorePassword
	 */
	public String getGeobatchPassword() {
		return geobatchPassword;
	}

	/**
	 * @param geobatchPassword the geostorePassword to set
	 */
	public void setGeobatchPassword(String geobatchPassword) {
		this.geobatchPassword = geobatchPassword;
	}

	@Override
	public String getJsp(ModelMap model, HttpServletRequest request, List<MultipartFile> files) {
		
		System.out.println("getJSP of "+name);
		
		if(model.containsKey("gotParam"))
			model.addAttribute("fileName", model.get("gotParam"));
		else {
			model.addAttribute("fileName", "Insert File Name");
		}
		
		return jspName;

	}

	@Override
	public Object getBlob(Object inputParam) {
		
		// TODO: look for a HttpServletRequest
		String fileName = (String)inputParam;
        RESTRunInfo runInfo = new RESTRunInfo();
        List<String> flist = new ArrayList<String>();
		// TODO: more flexible
        flist.add(basedirString+fileName);
        runInfo.setFileList(flist);
        
		return runInfo;
	}

	@Override
	public String getFlowID() {
		return this.flowID;
	}

	/**
	 * @return the jspName
	 */
	public String getJspName() {
		return jspName;
	}

	/**
	 * @param jspName the jspName to set
	 */
	public void setJspName(String jspName) {
		this.jspName = jspName;
	}

	/**
	 * @param flowID the flowID to set
	 */
	public void setFlowID(String flowID) {
		this.flowID = flowID;
	}

}