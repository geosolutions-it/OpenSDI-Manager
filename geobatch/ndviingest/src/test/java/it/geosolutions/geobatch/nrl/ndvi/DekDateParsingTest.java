/*
 *  Copyright (C) 2007 - 2012 GeoSolutions S.A.S.
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
package it.geosolutions.geobatch.nrl.ndvi;

import it.geosolutions.geobatch.flow.event.action.ActionException;
import java.io.IOException;
import java.util.Calendar;
import org.junit.Test;
import static org.junit.Assert.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author ETj (etj at geo-solutions.it)
 */
public class DekDateParsingTest extends BaseContextTest {

    private final Logger LOGGER;

    public DekDateParsingTest() {
        LOGGER = LoggerFactory.getLogger(getClass());
    }


    @Test
    public void testDateParsing() throws IOException, ActionException {

        checkParsing("13091", 2013,9,1,10);
        checkParsing("13092", 2013,9,11,20);
        checkParsing("13093", 2013,9,21,30);
        checkParsing("13123", 2013,12,21,31);
    }

    @Test
    public void testDateParsingFebruary() throws IOException, ActionException {
        checkParsing("12023", 2012,2,21,29);
        checkParsing("13023", 2013,2,21,28);
    }

    @Test
    public void testDateParsingXXCentury() throws IOException, ActionException {
        checkParsing("90011", 1990,1,1,10);
    }

    @Test
    public void testDateParsingBadDekad() throws IOException, ActionException {
        NDVIIngestAction action = new NDVIIngestAction(new NDVIIngestConfiguration("", "", ""));

        String fileName = createTifName("01019");
        try {
            action.parseDekDate(fileName);
            fail("Bad dekad not trapped");
        } catch (ActionException ex) {
            LOGGER.info("Properly trapped dek error:" + ex.getMessage());
        }
    }

    @Test
    public void testDateParsingBadDate() throws IOException, ActionException {
        NDVIIngestAction action = new NDVIIngestAction(new NDVIIngestConfiguration("", "", ""));

        String fileName = createTifName("aa019");
        try {
            action.parseDekDate(fileName);
            fail("Bad date not trapped");
        } catch (ActionException ex) {
            LOGGER.info("Properly trapped date error:" + ex.getMessage());
        }
    }

    @Test
    public void testDateParsingNotmatch() throws IOException, ActionException {
        NDVIIngestAction action = new NDVIIngestAction(new NDVIIngestConfiguration("", "", ""));

        try {
            action.parseDekDate("unmatching_file_name.tif");
            fail("Bad filename not trapped");
        } catch (ActionException ex) {
            LOGGER.info("Properly trapped bad filename:" + ex.getMessage());
        }
    }



    private void checkParsing(String s, int y, int m, int sd, int ed) throws ActionException {
        NDVIIngestAction action = new NDVIIngestAction(new NDVIIngestConfiguration("", "", ""));
        
        String fileName = createTifName(s);
        Calendar[] cal = action.parseDekDate(fileName);

        assertEquals(y, cal[0].get(Calendar.YEAR));
        assertEquals(y, cal[1].get(Calendar.YEAR));

        assertEquals(m, cal[0].get(Calendar.MONTH)+1);
        assertEquals(m, cal[1].get(Calendar.MONTH)+1);

        assertEquals(sd, cal[0].get(Calendar.DAY_OF_MONTH));
        assertEquals(ed, cal[1].get(Calendar.DAY_OF_MONTH));
    }

    public static String createTifName(String s) {
        return "dv"+s+"_pak.tif";
    }

}
