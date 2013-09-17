package it.geosolutions.nrl.mvc;

import it.geosolutions.nrl.init.Initializer;
import it.geosolutions.nrl.mvc.model.statistics.FileBrowser;
import it.geosolutions.nrl.mvc.model.statistics.FileBrowserManager;
import it.geosolutions.nrl.mvc.model.statistics.InputSelectorConfig;
import it.geosolutions.nrl.mvc.model.statistics.StatisticsConfigList;
import it.geosolutions.nrl.utils.ControllerUtils;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class Statistics {
	
//	@Autowired
//	FileBrowserManager fileBrowserManager;
	@Autowired
	StatisticsConfigList statisticsConfigs;
	
	private final static Logger LOGGER = LoggerFactory
			.getLogger(Statistics.class);
	
	@RequestMapping(value="/statistics/{regions}/{mask}/{granule}", method = RequestMethod.GET)
	public String printWelcome(@PathVariable(value = "regions") String regions,@PathVariable(value = "mask") String mask,@PathVariable(value = "granule") String granule, ModelMap model, Principal principal ) {
		
		setRegions(regions,model);
		setMask(mask,model);
		setGranule(granule,model);
		ControllerUtils.setCommonModel(model);
		model.addAttribute("context", "statistics");
		return "template";
 
	}

	private void setMask(String mask, ModelMap model) {
		if (statisticsConfigs.getConfigs()== null ){
			LOGGER.error("couldn't find statistics configs");
			return ;
		}
		for(InputSelectorConfig config : statisticsConfigs.getConfigs()){
			if(mask.equals(config.getId())){
				model.addAttribute("masks",config);			
			}
		}
		LOGGER.warn("Statistics config not found: " + mask);
	}

	private void setRegions(String regions, ModelMap model) {
		if (statisticsConfigs.getConfigs()== null ){
			LOGGER.error("couldn't find statistics configs");
			return ;
		}
		for(InputSelectorConfig config : statisticsConfigs.getConfigs()){
			if(regions.equals(config.getId())){
				model.addAttribute("regions",config);	
				return;
			}
		}
		LOGGER.warn("Statistics config not found: " + regions);

		
	}
	private void setGranule(String granule, ModelMap model) {
		if (statisticsConfigs.getConfigs()== null ){
			LOGGER.error("couldn't find statistics configs");
			return ;
		}
		for(InputSelectorConfig config : statisticsConfigs.getConfigs()){
			if(granule.equals(config.getId())){
				model.addAttribute("granule",config);	
				return;
			}
		}
		LOGGER.warn("Statistics config not found: " + granule);

		
	}
	
}