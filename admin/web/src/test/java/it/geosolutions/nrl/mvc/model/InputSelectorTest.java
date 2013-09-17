package it.geosolutions.nrl.mvc.model;

import static org.junit.Assert.*;

import it.geosolutions.nrl.mvc.model.statistics.FileBrowser;
import it.geosolutions.nrl.mvc.model.statistics.GranuleConfig;
import it.geosolutions.nrl.mvc.model.statistics.InputSelectorConfig;
import it.geosolutions.nrl.mvc.model.statistics.StatisticsConfigList;
import it.geosolutions.nrl.mvc.model.statistics.InputSelectorElement;
import java.io.File;

import javax.xml.bind.JAXB;

import org.junit.Before;
import org.junit.Test;

public class InputSelectorTest {
	private File file;
	
	@Before
	public void getFile(){
		String url = getClass().getClassLoader().getResource("testInputSelectors.xml").getFile();
		file = new File(url);
		
		
		
	}
	
	@Test
	public void loadInputSelectorsTest(){
		assertNotNull(file);
		StatisticsConfigList list  = JAXB.unmarshal(file, StatisticsConfigList.class);
		
		assertTrue(list.getConfigs().size()>0) ;
		for(InputSelectorConfig isc : list.getConfigs()){
			assertNotNull("id not found for some Input Selector configuration:",isc.getId());
			FileBrowser fb = isc.getFileBrowser();
			assertNotNull("file browser not found for configuration:"  + isc.getId(),fb);
			assertNotNull("baseDir not found for file browser configuration:"  + isc.getId(),fb.getBaseDir());
			assertNotNull("files not found for file browser in configuration:"  + isc.getId(),fb.getFiles());
			assertNotNull("regex not found for file browser in configuration:"  + isc.getId(),fb.getRegex());
			assertNotNull(isc.getFileBrowser());
			
			for(InputSelectorElement ise : isc.getElements()){
				assertNotNull("element 'id not present",ise.getId());	
			}			
			assertNotNull("granule not present in configuration "+ isc.getId(),isc.getGranule());
			GranuleConfig gc = isc.getGranule();
			assertNotNull("no time series for configuration: "+ isc.getId(),gc.getTimeSeries());
			
			

		}





		
	}
}
