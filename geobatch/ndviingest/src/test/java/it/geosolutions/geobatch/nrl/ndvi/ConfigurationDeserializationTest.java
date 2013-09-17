package it.geosolutions.geobatch.nrl.ndvi;

import it.geosolutions.geobatch.configuration.event.action.ActionConfiguration;
import it.geosolutions.geobatch.configuration.flow.file.FileBasedFlowConfiguration;
import it.geosolutions.geobatch.registry.AliasRegistry;
import it.geosolutions.geobatch.xstream.Alias;

import java.io.File;

import org.junit.Test;
import static org.junit.Assert.*;

import com.thoughtworks.xstream.XStream;
import org.springframework.context.support.ClassPathXmlApplicationContext;


//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations={"classpath:test-context.xml"})

public class ConfigurationDeserializationTest extends BaseTest{

//	@Autowired
	private AliasRegistry aliasRegistry;

//	@Configuration
//	static class ContextConfiguration {
//
//	}

    protected static ClassPathXmlApplicationContext ctx = null;

    public ConfigurationDeserializationTest() {

        synchronized (ConfigurationDeserializationTest.class) {
            if (ctx == null) {
                String[] paths = {
//                    "classpath*:applicationContext.xml"
//                         "applicationContext-test.xml"
                        "classpath:test-context.xml",
                        "classpath*:applicationContext-listeners.xml"
                };
                ctx = new ClassPathXmlApplicationContext(paths);

            }
        }
        aliasRegistry = (AliasRegistry)ctx.getBean("aliasRegistry");
        if(aliasRegistry == null)
            throw new IllegalStateException("AliasRegistry not found");

    }



	@Test
	public void testDeserialization() throws Exception{
		XStream xstream = new XStream();
		Alias alias=new Alias();
		alias.setAliasRegistry(aliasRegistry);
		alias.setAliases(xstream);
		File configFile = loadFile("data/ndviingestion.xml");

        FileBasedFlowConfiguration configuration = (FileBasedFlowConfiguration)xstream.fromXML(configFile);
        NDVIIngestConfiguration cfg = null;

		for(ActionConfiguration actionConfiguration : configuration.getEventConsumerConfiguration().getActions()){
			if(actionConfiguration != null && actionConfiguration instanceof NDVIIngestConfiguration){
				cfg=(NDVIIngestConfiguration)actionConfiguration;
				break;
			}
		}

		assertNotNull(cfg);
        assertEquals(new File("/tmp/ndvi"), cfg.getDestinationDir());
      
	}


    @Test
	public void testSerialization() throws Exception{

		XStream xstream = new XStream();
		Alias alias=new Alias();
		alias.setAliasRegistry(aliasRegistry);
		alias.setAliases(xstream);

        NDVIIngestConfiguration cfg = new NDVIIngestConfiguration("id", "name", "descr");
        cfg.setDestinationDir(new File("/tmp"));

        xstream.toXML(cfg, System.out);
        System.out.println();
    }
}
