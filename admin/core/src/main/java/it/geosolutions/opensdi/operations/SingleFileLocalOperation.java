/*
 *    GeoTools - The Open Source Java GIS Toolkit
 *    http://geotools.org
 *
 *    (C) 2002-2011, Open Source Geospatial Foundation (OSGeo)
 *
 *    This library is free software; you can redistribute it and/or
 *    modify it under the terms of the GNU Lesser General Public
 *    License as published by the Free Software Foundation;
 *    version 2.1 of the License.
 *
 *    This library is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *    Lesser General Public License for more details.
 */
package it.geosolutions.opensdi.operations;

import it.geosolutions.geobatch.services.rest.model.RESTRunInfo;
import it.geosolutions.opensdi.utils.ControllerUtils;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Lorenzo Natali 
 * an abstract implementation of <LocalOperation> that contains all
 * the common methods for local GeoBatch Operations that needs a single file
 *
 */
public abstract class SingleFileLocalOperation extends GeoBatchOperationImpl implements LocalOperation {
   private final static Logger LOGGER = Logger.getLogger(SingleFileLocalOperation.class);

    protected String jspName="singlefileoperation";
    protected String flowID ;
    protected String basedirString;
    
    @Override
    public Object getBlob(Object inputParam, HttpServletRequest request) {
        
        String fileName = request.getParameter("fileName");
           
        if( fileName==null){
            fileName = (String)inputParam;
        }
        fileName=ControllerUtils.preventDirectoryTrasversing(fileName);
        RESTRunInfo runInfo = new RESTRunInfo();
        List<String> flist = new ArrayList<String>();
        
        String fullPath = basedirString + fileName;
        flist.add(fullPath);
        LOGGER.info("request full path:"+fullPath);
        runInfo.setFileList(flist);
        return runInfo;
    }
    
    @Override
    public String getJsp(ModelMap model, HttpServletRequest request, List<MultipartFile> files) {
        
        if(model.containsKey("gotParam")){
            String gotParam =(String) model.get("gotParam");
            String d = request.getParameter("d");
            if(d!=null){
                gotParam = ControllerUtils.preventDirectoryTrasversing(d)+gotParam;
                
            }
            model.addAttribute("fileName",gotParam );
        } else {
            model.addAttribute("fileName", "Insert File Name");
        }
        
        return jspName;

    }
    
    @Override
    public String getFlowID() {
        return this.flowID;
    }
    /**
     * @param flowID the flowID to set
     */
    public final void setFlowID(String flowID) {
        this.flowID = flowID;
    }
    
    public String getBasedirString() {
        return basedirString;
    }
    public final void setBasedirString(String basedirString) {
        if(!basedirString.endsWith("/")) {
            LOGGER.warn("basedirString not ending with slash \"/\", appending one");
            basedirString = basedirString.concat("/");
        }
        this.basedirString = basedirString;
    }
    public String getJspName() {
        return jspName;
    }
    public final void setJspName(String jspName) {
        this.jspName = jspName;
    }

    @Override
    public String getJsp() {
        return jspName;
    }
}
