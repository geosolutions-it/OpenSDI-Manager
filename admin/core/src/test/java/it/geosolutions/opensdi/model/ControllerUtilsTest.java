/*
 *  OpenSDI Manager
 *  Copyright (C) 2013 GeoSolutions S.A.S.
 *  http://www.geo-solutions.it
 *
 *  GPLv3 + Classpath exception
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package it.geosolutions.opensdi.model;

import static org.junit.Assert.assertEquals;
import it.geosolutions.opensdi.utils.ControllerUtils;

import org.junit.Test;

/**
 * Simple test for ControllerUtils class
 * 
 * @author adiaz
 */
public class ControllerUtilsTest {

@Test
public void testGetFlows() {
    assertEquals(ControllerUtils.removeExtension("test.ext"), "test");
    assertEquals(ControllerUtils.removeExtension("test.ext.ext2"), "test.ext");
    assertEquals(ControllerUtils.removeExtension("t.e.s.t.ext"), "t.e.s.t");
    assertEquals(ControllerUtils.removeExtension("t.e.s/__.t.ext"),
            "t.e.s/__.t");
}

}