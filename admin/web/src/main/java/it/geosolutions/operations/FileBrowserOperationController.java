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

import it.geosolutions.nrl.mvc.model.statistics.FileBrowser;
import it.geosolutions.nrl.utils.ControllerUtils;
import it.geosolutions.opensdi.model.FileUpload;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FileBrowserOperationController implements ApplicationContextAware, Operation{
	
	private ApplicationContext applicationContext;

	private String operationName = "FileBrowser";

	private String operationRestPath = "filebrowser";

	private String operationJSP = "files";

	private String defaultBaseDir;
	
	private Boolean canNavigate;
	
	private Boolean canUpload;
	
	private Boolean canDelete;
	
	private UUID uniqueKey;
		
	public FileBrowserOperationController() {
		setDefaultBaseDir("G:/OpenSDIManager/test_shapes/");
		uniqueKey = UUID.randomUUID();   
	}

	
	/**
	 * Shows the list of files inside the selected folder after a file upload
	 * @param model
	 * @return
	 */
	//@RequestMapping(value = "/files", method = RequestMethod.POST)
	public String saveFileAndList(@ModelAttribute("uploadFile") FileUpload uploadFile, ModelMap model) {
		
        List<MultipartFile> files = uploadFile.getFiles();
        
        List<String> fileNames = new ArrayList<String>();
         
        if(null != files && files.size() > 0) {
            for (MultipartFile multipartFile : files) {
 
                String fileName = multipartFile.getOriginalFilename();
                if(!"".equalsIgnoreCase(fileName)){
                    //Handle file content - multipartFile.getInputStream()
                    try {
						multipartFile.transferTo(new File(getDefaultBaseDir() + fileName));
					} catch (IllegalStateException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
                    fileNames.add(fileName);
                }
                System.out.println(fileName);
 
            }
        }
         
        model.addAttribute("uploadedFiles", fileNames);
        
		FileBrowser fb = new FileBrowser();
		fb.setBaseDir(getDefaultBaseDir());
		fb.setRegex(null);
		fb.setScanDiretories(canNavigate);
		model.addAttribute("fileBrowser", fb);	

		model.addAttribute("operations", getAvailableOperations()); 
		
		model.addAttribute("context", operationJSP);
		ControllerUtils.setCommonModel(model);

		return "template";

	}

	/**
	 * Provide a HashMap of every LocalOperation bean loaded
	 * @return
	 */
	private HashMap<String, List<Operation>> getAvailableOperations() {
        
        HashMap<String, List<Operation>> ocontrollersHashMap = new HashMap<String, List<Operation>>();
        
		String[] lista = applicationContext.getBeanNamesForType(LocalOperation.class);
		for (String s : lista) {
			LocalOperation fo = (LocalOperation)applicationContext.getBean(s);
			if(!fo.isMultiple()) {
				List<String> exts = fo.getExtensions();
				for (String extString : exts) {
					if(ocontrollersHashMap.containsKey(extString)) {
						ocontrollersHashMap.get(extString).add(fo);
					}else {
						List<Operation> olist = new ArrayList<Operation>();
						olist.add(fo);
						ocontrollersHashMap.put(extString, olist);
					}
				}
			}
		}
        
		return ocontrollersHashMap;
		
	}

	@Override
	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		this.applicationContext = arg0;
		
	}

	@Override
	public String getName() {
		return operationName ;
	}

	/**
	 * Getter for operationName
	 * @return
	 */
	public String getOperationName() {
		return operationName ;
	}

	/**
	 * Setter for operationName
	 * @param newName
	 */
	public void setOperationName(String newName) {
		this.operationName = newName ;
	}

	@Override
	public String getRESTPath() {
		return operationRestPath ;
	}

	/**
	 * @param operationRestPath the operationRestPath to set
	 */
	public void setRESTPath(String operationRestPath) {
		this.operationRestPath = operationRestPath;
	}


	@Override
	public String getJsp() {
		return operationJSP ;
	}

	@Override
	public String getJsp(ModelMap model, HttpServletRequest request, List<MultipartFile> files) {
		
		System.out.println("getJSP di FileBrowser");

		String baseDir = getDefaultBaseDir();
		FileBrowser fb = new FileBrowser();
		
		Object gotParam = model.get("gotParam");
		
		@SuppressWarnings("unchecked")
		Map<String, String[]> parameters = request.getParameterMap();

	    for(String key : parameters.keySet()) {
	        System.out.println(key);  // debug
	        String[] vals = parameters.get(key);
	        for(String val : vals)  // debug
	            System.out.println(" -> " + val);  // debug
	        if(key.equalsIgnoreCase("d")) {
	        	String dirString = parameters.get(key)[0].trim();

	        	// prevent directory traversing
	        	dirString = dirString.replace("..", "");
	        	// clean path
	        	dirString = dirString.replace("/./", "/");
	        	dirString = dirString.replaceAll("/{2,}", "/");
	        	
	        	if(dirString.startsWith("/")) {
	        		dirString = dirString.substring(1);
	        	}
	        	
	        	//remove last slash
	        	
	        	if(dirString.lastIndexOf("/")>=0 && 
	        			dirString.lastIndexOf("/")==(dirString.length()-1)){
	        		System.out.println("stripping last slash"); // debug
	        		dirString = dirString.substring(0, dirString.length()-1);
	        	}
	        	
	        	//second check
	        	if(dirString.lastIndexOf("/")>=0) {
		        	model.addAttribute("directoryBack", dirString.substring(0, dirString.lastIndexOf("/")));
	        	}
	        	else {
	        		model.addAttribute("directoryBack", "");
				}
	        	
	        	dirString = dirString.concat("/");
	        	baseDir = baseDir + dirString;
	        	model.addAttribute("directory", dirString);
	        }
	    }
		
		if(gotParam != null) {
			System.out.println(gotParam);  // debug
		}
		String gotAction = request.getParameter("action");
		String fileToDel = request.getParameter("toDel");
		if(gotAction != null && gotAction.equalsIgnoreCase("delete")
				&& fileToDel != null ) {
			String deleteFileString = baseDir + fileToDel;
			boolean res = deleteFile(deleteFileString);
			System.out.println("Deletted "+deleteFileString+": "+res);  // debug
		}
		
		model.addAttribute("operationName", this.operationName);	
		model.addAttribute("operationRESTPath", this.getRESTPath());
		
		fb.setBaseDir(baseDir);			
		fb.setRegex(null);
		fb.setScanDiretories(canNavigate);
        
        if(null != files && files.size() > 0) {
        	List<String> fileNames = new ArrayList<String>();
            for (MultipartFile multipartFile : files) {
 
                String fileName = multipartFile.getOriginalFilename();
                if(!"".equalsIgnoreCase(fileName)){
                    try {
						multipartFile.transferTo(new File(baseDir + fileName));
					} catch (IllegalStateException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
                    fileNames.add(fileName);
                }
                System.out.println(fileName);
            }
        }
		
		model.addAttribute("fileBrowser", fb);	

		model.addAttribute("operations", getAvailableOperations()); 
		
		model.addAttribute("canDelete", this.canDelete);	
		model.addAttribute("canUpload", this.canUpload);	
		
		model.addAttribute("containerId", uniqueKey.toString().substring(0, 8));
		model.addAttribute("formId", uniqueKey.toString().substring(27, 36));
		
		return operationJSP ;
	}

	/**
	 * @return the defaultBaseDir
	 */
	public String getDefaultBaseDir() {
		return defaultBaseDir;
	}

	/**
	 * @param defaultBaseDir the defaultBaseDir to set
	 */
	public void setDefaultBaseDir(String defaultBaseDir) {
		if(!defaultBaseDir.endsWith("/")) {
			System.out.println("[WARN] defaultBaseDir not ending with slash \"\\\", appending one");
			defaultBaseDir = defaultBaseDir.concat("/");
		}
		this.defaultBaseDir = defaultBaseDir;
	}

	/**
	 * @return the canNavigate
	 */
	public Boolean getCanNavigate() {
		return canNavigate;
	}

	/**
	 * @param canNavigate the canNavigate to set
	 */
	public void setCanNavigate(Boolean canNavigate) {
		this.canNavigate = canNavigate;
	}
	
	private boolean deleteFile(String fileName) {
		if(fileName != null) {
			File toDel = new File(fileName);
			if(toDel.exists() && toDel.isFile()) {
		        return toDel.delete();
			}
		}
		return false;
	}


	/**
	 * @return the canUpload
	 */
	public Boolean getCanUpload() {
		return canUpload;
	}


	/**
	 * @param canUpload the canUpload to set
	 */
	public void setCanUpload(Boolean canUpload) {
		this.canUpload = canUpload;
	}


	/**
	 * @return the canDelete
	 */
	public Boolean getCanDelete() {
		return canDelete;
	}


	/**
	 * @param canDelete the canDelete to set
	 */
	public void setCanDelete(Boolean canDelete) {
		this.canDelete = canDelete;
	}
}