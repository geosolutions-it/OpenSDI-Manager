package it.geosolutions.nrl.mvc.model.statistics;

import javax.xml.bind.annotation.XmlElement;

public class InputSelectorElement {

	private String id;

	private String label;

	private String value;

	@XmlElement
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@XmlElement
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	@XmlElement
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}

