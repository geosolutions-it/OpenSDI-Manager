/*
 */
package it.geosolutions.nrl.init;

import java.io.File;

import javax.xml.bind.JAXB;

import it.geosolutions.nrl.mvc.model.statistics.InputSelectorConfig;
import it.geosolutions.nrl.mvc.model.statistics.StatisticsConfigList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * C
 * 
 * @author Lorenzo Natali
 */
public class Initializer implements InitializingBean, ApplicationContextAware {

	private final static Logger LOGGER = LoggerFactory
			.getLogger(Initializer.class);

	private ApplicationContext applicationContext;
	private String statisticsConfigFileName;

	private StatisticsConfigList statisticsConfigs;

	// <bean id="restSurvey"
	// class="it.geosolutions.fra2015.services.rest.impl.SurveyServiceImpl">
	// <bean id="userService"
	// class="it.geosolutions.fra2015.services.UserServiceImpl">

	@Override
	public void afterPropertiesSet() throws Exception {
		File configsFile = applicationContext.getResource(statisticsConfigFileName).getFile();
		StatisticsConfigList statisticsConfigsLoaded = JAXB.unmarshal(
				configsFile, StatisticsConfigList.class);
		if (statisticsConfigsLoaded.getConfigs() != null) {
			LOGGER.info("Statistics Configurations:"
					+ statisticsConfigsLoaded.getConfigs().size()
					+ " configurations Loaded");
		}
		this.statisticsConfigs.setConfigs(statisticsConfigsLoaded.getConfigs());

	}

	public String getStatisticsConfigFileName() {
		return statisticsConfigFileName;
	}

	public void setStatisticsConfigFileName(String statisticsConfigFileName) {
		this.statisticsConfigFileName = statisticsConfigFileName;
	}

	public StatisticsConfigList getStatisticsConfigs() {
		return statisticsConfigs;
	}

	public void setStatisticsConfigs(StatisticsConfigList statisticsConfigs) {
		this.statisticsConfigs = statisticsConfigs;
	}

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	@Override
	public void setApplicationContext(ApplicationContext ac)
			throws BeansException {
		this.applicationContext = ac;
	}

}
