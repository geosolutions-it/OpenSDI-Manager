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

import javax.media.jai.ROI;

import org.geotools.coverage.grid.GridCoverage2D;

/**
 * 
 * Prepared coverage configuration bean
 * 
 * @author adiaz
 */
public class PreparedCoverage implements Serializable {

	/**
	 * Region of Interest (ROI) to perform the operation
	 */
	private ROI roi;

	/**
	 * Cropped coverage to perform the operation
	 */
	private GridCoverage2D cropped;

	public PreparedCoverage() {
		super();
	}

	public PreparedCoverage(ROI roi, GridCoverage2D cropped) {
		super();
		this.roi = roi;
		this.cropped = cropped;
	}

	/**
	 * @return Region of Interest (ROI) to perform the operation
	 */
	public ROI getRoi() {
		return roi;
	}

	/**
	 * Setter for ROI
	 * 
	 * @param roi
	 */
	public void setRoi(ROI roi) {
		this.roi = roi;
	}

	/**
	 * @return Cropped coverage to perform the operation
	 */
	public GridCoverage2D getCropped() {
		return cropped;
	}

	/**
	 * Setter for the cropped coverage
	 * 
	 * @param cropped
	 */
	public void setCropped(GridCoverage2D cropped) {
		this.cropped = cropped;
	}

}
