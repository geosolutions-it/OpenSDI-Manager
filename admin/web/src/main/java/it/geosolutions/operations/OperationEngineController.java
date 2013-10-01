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

import it.geosolutions.geobatch.services.rest.GeoBatchRESTClient;
import it.geosolutions.geobatch.services.rest.RESTFlowService;
import it.geosolutions.geobatch.services.rest.model.RESTRunInfo;
import it.geosolutions.nrl.utils.ControllerUtils;
import it.geosolutions.opensdi.model.FileUpload;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class OperationEngineController implements ApplicationContextAware{
	
	private ApplicationContext applicationContext;
	
	/**
	 * 
	 * @param operationName
	 * @param gotParam
	 * @param request
	 * @param model
	 * @return String template jsp to display
	 */
	@RequestMapping(value = "/operation/{operationName}/{gotParam:.+}", method = RequestMethod.GET)
	public String issueGetToOperation(	@PathVariable(value = "operationName") String operationName,
										@PathVariable(value = "gotParam") String gotParam,
										HttpServletRequest request,
										ModelMap model) {
		
		System.out.println("Handling by issueGetToOperation : OperationEngine GET");
		
		System.out.println(operationName);

		@SuppressWarnings("unchecked")
		Map<String, String[]> parameters = request.getParameterMap();

	    for(String key : parameters.keySet()) {
	        System.out.println(key);
	        String[] vals = parameters.get(key);
	        for(String val : vals)
	            System.out.println(" -> " + val);
	    }
	    
		// TODO: check gotParam for security
		
		if(applicationContext.containsBean(operationName) && applicationContext.isTypeMatch(operationName, Operation.class)) {
			
		
			Operation op = (Operation)applicationContext.getBean(operationName);

			// TODO: getJSP should modify Model adding it's own attributes
			// Maybe the setCommonModel can be called from within the Operation ?
			
			if(gotParam != null) {
				model.addAttribute("gotParam", gotParam);
			}
			String operationJsp = op.getJsp(model, request, null);

			// Geosolutions authentication
			ControllerUtils.setCommonModel(model);
	
			return operationJsp;
		
		}else {
			model.addAttribute("messageType", "error");
			model.addAttribute("notLocalizedMessage", "Operation not found");
			return "common/messages";
		}

	}

	/**
	 * Proxy function for issueGetToOperation with path variable
	 * @param operationName
	 * @param request
	 * @param model
	 * @return template jsp to display
	 */
	@RequestMapping(value = "/operation/{operationName}", method = RequestMethod.GET)
	public String issueGetToOperation(	@PathVariable(value = "operationName") String operationName,
										HttpServletRequest request,
										ModelMap model) {
		return issueGetToOperation(operationName, null, request, model);
	}
	
	
	@RequestMapping(value = "/operation/{operationName}", method = RequestMethod.POST)
	public String issuePostToOperation(	@PathVariable(value = "operationName") String operationName,
										@RequestHeader HttpHeaders  gotHeaders,
										@ModelAttribute("uploadFile") FileUpload uploadFile,
										HttpServletRequest request,
										ModelMap model) {
		return issuePostToOperation(operationName, null, gotHeaders, uploadFile, request, model);
	}

	/**
	 *  Issue a POST request to the desired Operation
	 * @param operationName
	 * @param gotParam
	 * @param gotHeaders
	 * @param uploadFile
	 * @param request
	 * @param model
	 * @return template jsp to display returned message
	 */
	@RequestMapping(value = "/operation/{operationName}/{gotParam:.+}", method = RequestMethod.POST)
	public String issuePostToOperation(	@PathVariable(value = "operationName") String operationName,
										@PathVariable(value="gotParam") String gotParam,
										@RequestHeader HttpHeaders  gotHeaders,
										@ModelAttribute("uploadFile") FileUpload uploadFile,
										HttpServletRequest request,
										ModelMap model) {
		
		System.out.println("Handling by issuePostToOperation : OperationEngine POST");

		
		if(applicationContext.containsBean(operationName) && applicationContext.isTypeMatch(operationName, Operation.class)) {
			
			
			Operation operation = (Operation)applicationContext.getBean(operationName);

			if(operation instanceof GeoBatchOperation) {

				
				String response = "Operation running";  // Default text
				try {
			        GeoBatchRESTClient client = new GeoBatchRESTClient();
			        client.setGeostoreRestUrl(((GeoBatchOperation)operation).getGeobatchRestUrl());
			        client.setUsername(((GeoBatchOperation)operation).getGeobatchUsername());
			        client.setPassword(((GeoBatchOperation)operation).getGeobatchPassword());
			        
			        // TODO: check ping to GeoBatch (see test)
			        
			        RESTFlowService service = client.getFlowService();

				
				
					if(operation instanceof RemoteOperation) {
						// TODO: better implementation
					   byte[] blob = (byte[]) ((RemoteOperation)operation).getBlob(uploadFile.getFiles().get(0));
				       // TODO: fastFail or not?
					   // TODO: move fastfail to interfaces
				       response = service.run(((RemoteOperation)operation).getFlowID(), true, blob);
					}
					else if (operation instanceof LocalOperation) {
						
						// TODO: implementare i request param al posto del gotParam
						RESTRunInfo runInfo;
						if(gotParam != null) {
							runInfo = (RESTRunInfo) ((LocalOperation)operation).getBlob(gotParam);
						}else {
						
							@SuppressWarnings("unchecked")
							Map<String, String[]> parameters = request.getParameterMap();
							runInfo = (RESTRunInfo) ((LocalOperation)operation).getBlob(parameters);
						}

					    // TODO: fastFail or not?
						// TODO: move fastfail to interfaces
					    response = service.runLocal(((LocalOperation)operation).getFlowID(), true, runInfo);
					}
					
					
				} catch (Exception e) {
					e.printStackTrace();
					model.addAttribute("messageType", "error");
					model.addAttribute("notLocalizedMessage", "Couldn't run "+operation.getName());
					return "common/messages";

				}
				
				model.addAttribute("messageType", "success");
				model.addAttribute("notLocalizedMessage", response);

				return "common/messages";

			}
			
			List<MultipartFile> files = uploadFile.getFiles();
	        
	        //List<String> fileNames = new ArrayList<String>();
	         
			@SuppressWarnings("unchecked")
			Map<String, String[]> parameters = request.getParameterMap();
	
		    for(String key : parameters.keySet()) {
		        System.out.println(key);
		        String[] vals = parameters.get(key);
		        for(String val : vals)
		            System.out.println(" -> " + val);
		    }
	
		    System.out.println("-- HEADERS --");
		    for( Object o : gotHeaders.keySet()) {
	        	
	        	//System.out.println( o.getClass().getName() );
	        	System.out.println( o );
	        	//System.out.println( gotHeaders.get(o).getClass().getName() );
	        	System.out.println( " -> "+ gotHeaders.get(o) );
	        	
	        }
	        
	        if(null != files && files.size() > 0) {
	            for (MultipartFile multipartFile : files) {
	                String fileName = multipartFile.getOriginalFilename();
	 /*
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
	                }*/
	                System.out.println("File Name: "+fileName);
	 
	            }
	        }

			
			// TODO: getJSP should modify Model adding it's own attributes
			// Maybe the setCommonModel can be called from within the Operation ?
			
			model.addAttribute("gotParam", gotParam);
			String operationJsp = operation.getJsp(model, request, files);
	
			// Geosolutions authentication
			ControllerUtils.setCommonModel(model);
	
			return operationJsp;
	
		}else {
			model.addAttribute("messageType", "error");
			model.addAttribute("notLocalizedMessage", "Operation not found");
			return "common/messages";
		}

		
		
		
	}
/*
	private HashMap<String, Operation> getAvailableOperations() {
        
        HashMap<String, Operation> ocontrollersHashMap = new HashMap<String, Operation>();
        
		String[] lista = applicationContext.getBeanNamesForType(LocalOperation.class);
		for (String s : lista) {
			LocalOperation fo = (LocalOperation)applicationContext.getBean(s);
			if(!fo.isMultiple()) {
				 ocontrollersHashMap.put(fo.getExtensions().get(0), fo);
			}
		}
        
		return ocontrollersHashMap;
		
	}
*/
	@Override
	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		this.applicationContext = arg0;
		
	}


}