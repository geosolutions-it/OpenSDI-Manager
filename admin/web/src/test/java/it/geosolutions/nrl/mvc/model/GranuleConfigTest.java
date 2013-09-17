package it.geosolutions.nrl.mvc.model;

import static org.junit.Assert.*;
import it.geosolutions.nrl.mvc.model.statistics.GranuleConfig;

import org.junit.Before;
import org.junit.Test;

public class GranuleConfigTest {
	private String infoFile;
	@Before
	public void getFile(){
		infoFile = getClass().getClassLoader().getResource("info.xml").getFile();
	}
	
	@Test
	public void GranulesTest(){
		GranuleConfig gf = new GranuleConfig();
		gf.setInfoFile(infoFile);
		String[] ts = gf.getTimeSeries();
		assertNotNull(ts);
    	System.out.println(ts[0]);

	}

}
