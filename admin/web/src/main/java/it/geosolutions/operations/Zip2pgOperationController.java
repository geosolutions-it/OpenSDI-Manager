package it.geosolutions.operations;

import it.geosolutions.geobatch.services.rest.GeoBatchRESTClient;
import it.geosolutions.geobatch.services.rest.RESTFlowService;
import it.geosolutions.geobatch.services.rest.model.RESTRunInfo;
import it.geosolutions.geostore.core.model.User;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class Zip2pgOperationController implements FileOperation {
	
	/**
	 * The name of this Operation
	 */
	public static String name = "Zip2pg";
	
	/**
	 * The path were to GET the form and POST the request
	 * Typically all lower case
	 */
	private String path = "zip2pg";
	
	/**
	 * File extension this Operation will work on
	 */
	private String[] extensions = {"zip"};
	
	/**
	 * Directory where to scan for files
	 */
	private String basedirString = "G:/OpenSDIManager/test_shapes/";

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

	@RequestMapping(value = "/operation/zip2pg/{fileName:.+}", method = RequestMethod.GET)
	public String zip2pg(@PathVariable(value = "fileName") String fileName, ModelMap model) {
		
		model.addAttribute("fileName", fileName);
		
		return "snipplets/modal/zip2pg";

	}
	/**
	 *  This is the actual Flow Launcher
	 *  It connects to GeoBatch sending the parameters
	 */
	@RequestMapping(value = "/operation/zip2pg/{fileName:.+}", method = RequestMethod.POST)
	public String zip2pg(@PathVariable(value = "fileName") String fileName,@ModelAttribute("user") User user, ModelMap model) {

		String response = "Zip2pg running";
		try {
	        GeoBatchRESTClient client = new GeoBatchRESTClient();
	        // TODO: parameterize?
	        client.setGeostoreRestUrl("http://localhost:8081/geobatch/rest/");
	        client.setUsername("admin");
	        client.setPassword("admin");
	        
	        // TODO: check ping to GeoBatch (see test)
	        
	        RESTFlowService service = client.getFlowService();
	        RESTRunInfo runInfo = new RESTRunInfo();
	        List<String> flist = new ArrayList<String>();
			flist.add(basedirString+fileName);
	        runInfo.setFileList(flist);
	        
	        // TODO: fastFail or not?
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
		return path;
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
		return "zip2pg";
	}

}