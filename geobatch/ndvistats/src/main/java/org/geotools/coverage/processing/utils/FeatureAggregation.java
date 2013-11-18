/*
 *    GeoTools - The Open Source Java GIS Toolkit
 *    http://geotools.org
 * 
 *    (C) 2005-20013, Open Source Geospatial Foundation (OSGeo)
 *
 *    This library is free software; you can redistribute it and/or
 *    modify it under the terms of the GNU Lesser General Public
 *    License as published by the Free Software Foundation;
 *    version 2.1 of the License.
 *
 *    This library is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *    Lesser General Public License for more details.
 */
package org.geotools.coverage.processing.utils;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author adiaz
 */
public class FeatureAggregation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Map<String, Object> properties;

	private List<String> propertiesOrder;

	private String elementSeparator = ",";

	private Boolean showName = true;

	public FeatureAggregation() {
		super();
	}

	/**
	 * Constructor with parameters
	 * 
	 * @param otherProperties
	 * @param propertiesOrder
	 * @param elementSeparator
	 * @param showName
	 */
	public FeatureAggregation(Map<String, Object> properties,
			List<String> propertiesOrder, String elementSeparator,
			Boolean showName) {
		super();
		this.properties = properties;
		this.propertiesOrder = propertiesOrder;
		this.elementSeparator = elementSeparator;
		this.showName = showName;
	}

	/**
	 * @return the elementSeparator
	 */
	public String getElementSeparator() {
		return elementSeparator;
	}

	/**
	 * @param elementSeparator
	 *            the elementSeparator to set
	 */
	public void setElementSeparator(String elementSeparator) {
		this.elementSeparator = elementSeparator;
	}

	/**
	 * @return the propertiesOrder
	 */
	public List<String> getPropertiesOrder() {
		return propertiesOrder;
	}

	/**
	 * @param propertiesOrder
	 *            the propertiesOrder to set
	 */
	public void setPropertiesOrder(List<String> propertiesOrder) {
		this.propertiesOrder = propertiesOrder;
	}

	/**
	 * @return the properties
	 */
	public Map<String, Object> getProperties() {
		return properties;
	}

	/**
	 * @param properties
	 *            the properties to set
	 */
	public void setProperties(Map<String, Object> properties) {
		this.properties = properties;
	}

	/**
	 * Obtain the string representation for the feature representation
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String value = "";
		int i = 0;
		Collection<String> keyCol = propertiesOrder != null ? propertiesOrder
				: properties.keySet();
		for (String key : keyCol) {
			if (showName) {
				value += key + "=";
			}
			value += properties.get(key).toString();
			i++;
			if (i < keyCol.size()) {
				value += elementSeparator;
			}
		}
		return value;
	}
	
	/**
	 * @return a row for a CSV writer
	 */
	public Object[] toRow() {
		Object[] values = new Object[propertiesOrder != null ? propertiesOrder
				.size() : properties.keySet().size()];
		int i = 0;
		Collection<String> keyCol = propertiesOrder != null ? propertiesOrder
				: properties.keySet();
		for (String key : keyCol) {
			values[i++] = properties.get(key).toString();
		}
		return values;
	}

}
