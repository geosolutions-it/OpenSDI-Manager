package it.geosolutions.nrl.mvc.model.statistics;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
/**
 * Wraps a configuration for geobatch infoFile access utilities
 * @author Lorenzo Natali
 *
 */
public class GranuleConfig {
	
	/**
	 * path to the info.xml
	 */
	private String infoFile;
	
	private String xpath = "/map/entry[string='TIME_DOMAIN']/tree-set/string";
    private final static Logger LOGGER = Logger.getLogger(GranuleConfig.class);

	
	@XmlElement
	public String getInfoFile() {
		return infoFile;
	}

	
	public void setInfoFile(String infoFile) {
		this.infoFile = infoFile;
		
	}
	
	/**
	 * 
	 * @return array of timeSeriesString
	 */
	public String[] getTimeSeries(){
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(this.infoFile);
			return readXMLNodes(doc);

		} catch (ParserConfigurationException e) {
			LOGGER.error("couldn't parse granule info file:" + e.getMessage() );
		} catch (SAXException e) {
			LOGGER.error("couldn't parse granule info file:" + e.getMessage() );
		} catch (IOException e) {
			LOGGER.error("couldn't parse granule info file:" + e.getMessage() );
		} catch (XPathExpressionException e) {
			LOGGER.error("couldn't parse granule info file:" + e.getMessage() );

		} 
		
		
		return null;
	}
	
	
	/**
	 * 
	 * @return array of Dekads string
	 */
	public String[] getDekads(){
		return getTimeSeries();
	}
	
	
	private String[] readXMLNodes(Document doc) throws XPathExpressionException {
        XPath xpath = XPathFactory.newInstance().newXPath();
        XPathExpression expr = xpath.compile(this.xpath);
 
        Object result = expr.evaluate(doc, XPathConstants.NODESET);
        NodeList nodes = (NodeList) result;
        int nnodes = nodes.getLength();
        String[] ret = new String [nnodes];
        for(int i = 0; i <nnodes ; i++){
        	Node n = nodes.item(i);
        	ret[i] = n.getTextContent();
        }
        return ret;
    }
	
}
