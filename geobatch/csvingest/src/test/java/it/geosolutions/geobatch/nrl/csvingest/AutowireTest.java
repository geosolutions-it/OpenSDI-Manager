/*
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
package it.geosolutions.geobatch.nrl.csvingest;

import it.geosolutions.geobatch.annotations.GenericActionService;

import static org.junit.Assert.*;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *
 * @author ETj (etj at geo-solutions.it)
 */
public class AutowireTest extends BaseContextTest {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());;

    public AutowireTest() {
    }

    @Test
    public void autowire() {
        GenericActionService gas = new GenericActionService("csv", CSVIngestAction.class);
        gas.setApplicationContext(ctx);
        CSVIngestAction action = gas.createAction(CSVIngestAction.class, new CSVIngestConfiguration("a", "b", "c"));

        assertTrue(action.checkConfiguration());
    }
}
