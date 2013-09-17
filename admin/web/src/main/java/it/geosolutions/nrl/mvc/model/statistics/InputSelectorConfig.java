package it.geosolutions.nrl.mvc.model.statistics;

import it.geosolutions.nrl.mvc.model.statistics.InputSelectorElement;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class InputSelectorConfig{
	
	private List<InputSelectorElement> elements;

	private FileBrowser fileBrowser;
	
	private String id;
	
	private GranuleConfig granule;
	
	
	@XmlElement
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@XmlElement(name="element")
	public List<InputSelectorElement> getElements() {
		return elements;
	}

	public void setElements(List<InputSelectorElement> elements) {
		this.elements = elements;
	}

	@XmlElement
	public FileBrowser getFileBrowser() {
		return fileBrowser;
	}

	public void setFileBrowser(FileBrowser fb) {
		this.fileBrowser = fb;
	}
	
	@XmlElement
	public GranuleConfig getGranule() {
		return granule;
	}

	public void setGranule(GranuleConfig granule) {
		this.granule = granule;
	}


}