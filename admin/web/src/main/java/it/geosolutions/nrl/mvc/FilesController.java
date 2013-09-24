package it.geosolutions.nrl.mvc;

import it.geosolutions.geostore.services.rest.AdministratorGeoStoreClient;
import it.geosolutions.nrl.mvc.model.statistics.FileBrowser;
import it.geosolutions.nrl.utils.ControllerUtils;
import it.geosolutions.operations.FileOperation;
import it.geosolutions.operations.Operation;
import java.util.HashMap;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class FilesController implements ApplicationContextAware, Operation{
	
	@Autowired
	AdministratorGeoStoreClient geoStoreClient;
	
	private ApplicationContext applicationContext;

	private String operationName = "FileBrowser";

	private String operationRESTPath = "filebrowser";

	private String operationJsp = "files";
	
	@RequestMapping(value = "/files", method = RequestMethod.GET)
	public String fileList(ModelMap model) {
		
		FileBrowser fb = new FileBrowser();
		fb.setBaseDir("G:/OpenSDIManager/test_shapes");
		fb.setRegex(null);
		model.addAttribute("fileBrowser", fb);	

		model.addAttribute("operations", getAvailableOperations()); 
		
		model.addAttribute("context", operationJsp);
		ControllerUtils.setCommonModel(model);

		return "template";

	}

	private HashMap<String, Operation> getAvailableOperations() {
        
        HashMap<String, Operation> ocontrollersHashMap = new HashMap<String, Operation>();
        //ocontrollersHashMap.put("zip", z2pController);

		String[] lista = applicationContext.getBeanNamesForType(FileOperation.class);
		for (String s : lista) {
			FileOperation fo = (FileOperation)applicationContext.getBean(s);
			if(!fo.isMultiple()) {
				 ocontrollersHashMap.put(fo.getExtensions().get(0), fo);
			}
		}
        
		return ocontrollersHashMap;
		
	}

	// GETTERS AND SETTERS
	public AdministratorGeoStoreClient getGeoStoreClient() {
		return geoStoreClient;
	}

	public void setGeoStoreClient(AdministratorGeoStoreClient geoStoreClient) {
		this.geoStoreClient = geoStoreClient;
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

	@Override
	public String getRESTPath() {
		return operationRESTPath ;
	}

	@Override
	public String getJsp() {
		return operationJsp ;
	}
}