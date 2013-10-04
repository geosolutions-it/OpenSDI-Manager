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

import it.geosolutions.nrl.utils.ControllerUtils;
import it.geosolutions.operations.Operation;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
/**
 * Operation that shows a list of every available Operation
 * @author Lorenzo Pini
 *
 */
@Controller
public class ActiveOperationsListOperation implements ApplicationContextAware, Operation{
	
	private ApplicationContext applicationContext;

	private String operationName = "OperationsBrowser";

	private final static String operationRESTPath = "oplist";

	private String operationJsp = "operations";
	
	/**
	 * Shows a list of every available Operation
	 * @param model
	 * @return
	 */
	@RequestMapping(value = operationRESTPath, method = RequestMethod.GET)
	public String fileList(ModelMap model) {
		
		model.addAttribute("operations", getAvailableOperations()); 
		
		//model.addAttribute("context", operationJsp);
		ControllerUtils.setCommonModel(model);

		return operationJsp;

	}

	private List<Object[]> getAvailableOperations() {
        
        List<Object[]> ocontrollersList = new ArrayList<Object[]>();
        
		String[] lista = applicationContext.getBeanNamesForType(Operation.class);
		for (String s : lista) {
			
			// TODO: better way to get Operation properties (more bean style)
			Operation op = (Operation)applicationContext.getBean(s);
			Object[] obj = new Object[4];
			obj[0] = op.getName();
			obj[1] = op.getRESTPath();
			obj[2] = op.getClass().getName();
			obj[3] = (op instanceof LocalOperation);

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

	@Override
	public String getJsp(ModelMap model, HttpServletRequest request, List<MultipartFile> files) {
		
		System.out.println("getJSP di ActiveOperations");
		model.addAttribute("operations", getAvailableOperations()); 
		
		return operationJsp;
	}

}