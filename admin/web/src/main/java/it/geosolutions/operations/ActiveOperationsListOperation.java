package it.geosolutions.operations;

import it.geosolutions.nrl.utils.ControllerUtils;
import it.geosolutions.operations.Operation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ActiveOperationsListOperation implements ApplicationContextAware, Operation{
	
	private ApplicationContext applicationContext;

	private String operationName = "OperationsBrowser";

	private final static String operationRESTPath = "oplist";

	private String operationJsp = "operations";
	
	@RequestMapping(value = operationRESTPath, method = RequestMethod.GET)
	public String fileList(ModelMap model) {
		
		model.addAttribute("operations", getAvailableOperations()); 
		
		model.addAttribute("context", operationJsp);
		ControllerUtils.setCommonModel(model);

		return "template";

	}

	private List<Object[]> getAvailableOperations() {
        
        List<Object[]> ocontrollersList = new ArrayList<Object[]>();
        
		String[] lista = applicationContext.getBeanNamesForType(Operation.class);
		for (String s : lista) {
			Operation op = (Operation)applicationContext.getBean(s);
			Object[] obj = new Object[4];
			obj[0] = op.getName();
			obj[1] = op.getRESTPath();
			obj[2] = op.getClass().getName();
			obj[3] = (op instanceof FileOperation);

			ocontrollersList.add(obj );
		}
        
		return ocontrollersList;
		
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