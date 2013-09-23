package it.geosolutions.nrl.mvc.model.statistics;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class StatisticsConfigList {
	
	private List<InputSelectorConfig> configs;
	
	@XmlElement(name="config")
	public List<InputSelectorConfig> getConfigs() {
		return configs;
	}

	public void setConfigs(List<InputSelectorConfig> configs) {
		this.configs = configs;
	}

	
	
}
