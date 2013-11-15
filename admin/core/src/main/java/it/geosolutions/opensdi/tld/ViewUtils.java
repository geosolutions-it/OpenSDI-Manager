/*
 *    GeoTools - The Open Source Java GIS Toolkit
 *    http://geotools.org
 *
 *    (C) 2002-2011, Open Source Geospatial Foundation (OSGeo)
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
package it.geosolutions.opensdi.tld;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.log4j.Logger;

/**
 * A set of utilities for jsps 
 * @author Lorenzo Natali
 *
 */
public class ViewUtils {
    private final static Logger LOGGER = Logger.getLogger(ViewUtils.class);

    public static String encodeURIComponent(String uriComponent){
      
        try {
            return  URLEncoder.encode(uriComponent, "UTF-8");//TODO make it flexible
        } catch (UnsupportedEncodingException e) {
            LOGGER.error(e.getMessage(), e);
            return uriComponent;
        }
        
    }
}
