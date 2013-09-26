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

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class OperationEngineController implements ApplicationContextAware{
	
	private ApplicationContext applicationContext;
	
	/**
	 * Shows the list of files inside the selected folder
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/operation/{operationName}/{gotParam:.+}", method = RequestMethod.GET)
	public String issueGetToOperation(@PathVariable(value = "operationName") String operationName, @PathVariable(value = "gotParam") String gotParam, ModelMap model) {
		
		System.out.println(operationName);
		System.out.println(gotParam);
		
		// TODO: check gotParam for security
		
		if(applicationContext.containsBean(gotParam) && applicationContext.isTypeMatch(gotParam, Operation.class)) {
			
		
			Operation op = (Operation)applicationContext.getBean(gotParam);

			// TODO: getJSP should modify Model adding it's own attributes
			// Maybe the setCommonModel can be called from within the Operation ?
			
			String operationJsp = op.getJsp(model);

			ControllerUtils.setCommonModel(model);
	
			return operationJsp;
		
		}else {
			model.addAttribute("messageType", "error");
			model.addAttribute("notLocalizedMessage", "Operation not found");
			return "common/messages";
		}

	}

	/**
	 * Shows the list of files inside the selected folder after a file upload
	 * @param model
	 * @return
 	 */
	@RequestMapping(value = "/operation/{operationName}/{gotParam:.+}", method = RequestMethod.POST)
	public String saveFileAndList(@PathVariable(value = "operationName") String operationName, @PathVariable(value = "gotParam") String gotParam, @ModelAttribute("uploadFile") FileUpload uploadFile, ModelMap model) {
		
        List<MultipartFile> files = uploadFile.getFiles();
        
        List<String> fileNames = new ArrayList<String>();
         
        if(null != files && files.size() > 0) {
            for (MultipartFile multipartFile : files) {
 
                String fileName = multipartFile.getOriginalFilename();
                if(!"".equalsIgnoreCase(fileName)){
                    //Handle file content - multipartFile.getInputStream()
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
         
        model.addAttribute("uploadedFiles", fileNames);
        
		FileBrowser fb = new FileBrowser();
		fb.setBaseDir(baseDir);
		fb.setRegex(null);
		model.addAttribute("fileBrowser", fb);	

		model.addAttribute("operations", getAvailableOperations()); 
		
		model.addAttribute("context", operationJsp);
		ControllerUtils.setCommonModel(model);

		return "template";

	}

	private HashMap<String, Operation> getAvailableOperations() {
        
        HashMap<String, Operation> ocontrollersHashMap = new HashMap<String, Operation>();
        
		String[] lista = applicationContext.getBeanNamesForType(FileOperation.class);
		for (String s : lista) {
			FileOperation fo = (FileOperation)applicationContext.getBean(s);
			if(!fo.isMultiple()) {
				 ocontrollersHashMap.put(fo.getExtensions().get(0), fo);
			}
		}
        
		return ocontrollersHashMap;
		
	}

	@Override
	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		this.applicationContext = arg0;
		
	}


}