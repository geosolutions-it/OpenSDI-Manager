package it.geosolutions.nrl.mvc;

import it.geosolutions.geostore.services.rest.AdministratorGeoStoreClient;
import it.geosolutions.nrl.mvc.model.statistics.FileBrowser;
import it.geosolutions.nrl.utils.ControllerUtils;
import it.geosolutions.operations.Operation;
import it.geosolutions.operations.Zip2pgOperationController;

import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class FilesController {
	
	@Autowired
	AdministratorGeoStoreClient geoStoreClient;
	
	/*
	@Autowired
	RequestMappingHandlerMapping handlerMapping;
*/

	@Autowired
	Zip2pgOperationController z2pController;
	
	@RequestMapping(value = "/files", method = RequestMethod.GET)
	public String fileList(ModelMap model) {
		
		FileBrowser fb = new FileBrowser();
		fb.setBaseDir("G:/OpenSDIManager/test_shapes");
		fb.setRegex(null);
		model.addAttribute("fileBrowser",fb);	

		model.addAttribute("operations", getAvailableOperations()); 
		
		model.addAttribute("context", "files");
		ControllerUtils.setCommonModel(model);

		return "template";

	}

	private HashMap<String, Operation> getAvailableOperations() {
		
		
		ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
        scanner.addIncludeFilter(new AnnotationTypeFilter(Controller.class));
        for (BeanDefinition beanDefinition : scanner.findCandidateComponents("it.geosolutions.operations")){
        	System.out.println(beanDefinition.getBeanClassName());
        	/*
        	try {
        		System.out.println(Class.forName(beanDefinition.getBeanClassName(), false, null).getField("name"));
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
        }
        /*
        
        model.addAttribute( "endPoints", requestMappingHandlerMapping.getHandlerMethods().keySet() );

	 	
		
		//requestMappingHandlerMapping.getHandlerMethods().keySet();
		for (RequestMappingInfo i : handlerMapping.getHandlerMethods().keySet()) {
			
			System.out.println(i.getPatternsCondition());
			
		}
        */
        
        HashMap<String, Operation> ocontrollersHashMap = new HashMap<String, Operation>();
        ocontrollersHashMap.put("zip", z2pController);
        
		return ocontrollersHashMap;
		
	}
/*	
	@RequestMapping(value = "/files/create", method = RequestMethod.GET)
	public String createFile(ModelMap model) {
		User user = new User();
		List<UserAttribute> attrs = new ArrayList<UserAttribute>();
		UserAttribute email = new UserAttribute();
		email.setName("email");
		attrs.add(email);
		user.setAttribute(attrs);
		model.addAttribute("user", user);
		model.addAttribute("context", "create");

		return "snipplets/modal/createuser";

	}

	@RequestMapping(value = "/files/create", method = RequestMethod.POST)
	public String createFile(@ModelAttribute("user") User user, ModelMap model) {

		try {
			geoStoreClient.insert(user);
		} catch (Exception e) {
			model.addAttribute("messageType", "error");
			model.addAttribute("notLocalizedMessage", "Couldn't save User");
			return "common/messages";

		}
		model.addAttribute("messageType", "success");
		model.addAttribute("notLocalizedMessage", "User Saved successfully");

		return "common/messages";

	}

	@RequestMapping(value = "/files/action-1/{fileName:.+}", method = RequestMethod.GET)
	public String zip2pg(@PathVariable(value = "fileName") String fileName, ModelMap model) {
		
		model.addAttribute("fileName", fileName);

		return "snipplets/modal/zip2pg";

	}
	*/
	/**
	 *  This is the actual Action Launcher
	 *  It connects to GeoBatch sending the parameters
	 */
	/*
	@RequestMapping(value = "/files/action-1/{fileName:.+}", method = RequestMethod.POST)
	public String zip2pg(@PathVariable(value = "fileName") String fileName,@ModelAttribute("user") User user, ModelMap model) {

		String response = "Zip2pg running";
		try {
	        GeoBatchRESTClient client = new GeoBatchRESTClient();
	        // TODO: parameterize
	        client.setGeostoreRestUrl("http://localhost:8081/geobatch/rest/");
	        client.setUsername("admin");
	        client.setPassword("admin");
	        
	        // TODO: check ping to GeoBatch (see test)
	        
	        RESTFlowService service = client.getFlowService();
	        RESTRunInfo runInfo = new RESTRunInfo();
	        List<String> flist = new ArrayList<String>();
			flist.add("G:/OpenSDIManager/test_shapes/"+fileName);
	        runInfo.setFileList(flist);
	        // TODO: what is fastFail?
	        response = service.runLocal("ds2ds_zip2pg", true, runInfo);

		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("messageType", "error");
			model.addAttribute("notLocalizedMessage", "Couldn't run Zip2pg");
			return "common/messages";

		}
		model.addAttribute("messageType", "success");
		model.addAttribute("notLocalizedMessage", response);

		return "common/messages";

	}

	@RequestMapping(value = "/files/delete/{id}", method = RequestMethod.GET)
	public String deleteUser(@PathVariable(value = "id") Long id, ModelMap model) {
		RESTUser user = geoStoreClient.getUser(id);
		model.addAttribute("user", user);
		model.addAttribute("resource",user.getName());

		return "snipplets/modal/confirmdelete";

	}
	@RequestMapping(value = "/files/delete/{id}", method = RequestMethod.POST)
	public String deleteUser(@PathVariable(value = "id") Long id,@ModelAttribute("user") User user, ModelMap model) {

		try {
			geoStoreClient.deleteUser(id);
		} catch (Exception e) {
			model.addAttribute("messageType", "error");
			model.addAttribute("notLocalizedMessage", "Couldn't delete User");
			return "common/messages";

		}
		model.addAttribute("messageType", "success");
		model.addAttribute("notLocalizedMessage", "User Saved successfully");

		return "common/messages";

	}
	*/
	// GETTERS AND SETTERS
	public AdministratorGeoStoreClient getGeoStoreClient() {
		return geoStoreClient;
	}

	public void setGeoStoreClient(AdministratorGeoStoreClient geoStoreClient) {
		this.geoStoreClient = geoStoreClient;
	}
}