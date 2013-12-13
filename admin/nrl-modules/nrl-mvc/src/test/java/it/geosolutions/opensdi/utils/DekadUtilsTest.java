/*
 *    GeoTools - The Open Source Java GIS Toolkit
 *    http://geotools.org
 *
 *    (C) 2002-2013, Open Source Geospatial Foundation (OSGeo)
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
package it.geosolutions.opensdi.utils;

import static org.junit.Assert.assertEquals;
import it.geosolutions.opensdi.utils.DekadUtils;

import org.junit.Test;

/**
 * Test for utilities class
 * 
 * @author adiaz
 */
public class DekadUtilsTest {

/**
 * Last day of the dekad
 * 
 * @throws CSVProcessException
 */
@Test
public void lastDayTest() {
    // January have 31 days 
    Integer lastDay = DekadUtils.getLastDay(2013, 1, 2);
    assertEquals(lastDay, new Integer(31));
    // February have 28 days on 2013 
    lastDay = DekadUtils.getLastDay(2013, 2, 2);
    assertEquals(lastDay, new Integer(28));
    // February have 29 days on 1992 
    lastDay = DekadUtils.getLastDay(1992, 2, 2);
    assertEquals(lastDay, new Integer(29));
    // December have 31 days 
    lastDay = DekadUtils.getLastDay(1992, 12, 2);
    assertEquals(lastDay, new Integer(31));
    // First decad (ends on 10)
    lastDay = DekadUtils.getLastDay(1992, 12, 0);
    assertEquals(lastDay, new Integer(10));
    // Second decad (ends on 20)
    lastDay = DekadUtils.getLastDay(1992, 12, 1);
    assertEquals(lastDay, new Integer(20));
}

}
