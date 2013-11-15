/*
 *  Copyright (C) 2007 - 2013 GeoSolutions S.A.S.
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
package it.geosolutions.geobatch.opensdi.csvingest.utils;

import static org.junit.Assert.*;
import it.geosolutions.geobatch.opensdi.csvingest.processor.CSVProcessException;
import it.geosolutions.geobatch.opensdi.csvingest.utils.CSVIngestUtils;

import org.junit.Test;

/**
 * Simple test for the utilities class
 * 
 * @author adiaz
 *
 */
public class CSVIngestUtilsTest {
	
	/**
	 * Last decad must be 36 (Oct, 3)
	 * 
	 * @throws CSVProcessException
	 */
	@Test
	public void lastDecadTest() throws CSVProcessException{
		Integer decad = CSVIngestUtils.getDecad("Oct", 3);
		assertEquals(decad, new Integer(36));
	}
	
	/**
	 * First decad must be 1 (Nov, 1)
	 * 
	 * @throws CSVProcessException
	 */
	@Test
	public void firstDecadTest() throws CSVProcessException{
		Integer decad = CSVIngestUtils.getDecad("Nov", 1);
		assertEquals(decad, new Integer(1));
	}

}
