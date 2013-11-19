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

import it.geosolutions.geobatch.opensdi.csvingest.processor.CSVProcessException;

import org.springframework.util.StringUtils;

/**
 * Utilities for CSV ingest
 * 
 * @author adiaz
 */
public class CSVIngestUtils {

/**
 * Obtain the s_dec in the year.<br />
 * The s_dec is 1 -- 36 , 1 is the first decade in November 36 is the 3th decad
 * in October
 * 
 * @param month must by a short name of the month in english (@see
 *        {@link DecMonth})
 * @param decadInMonth must be 1, 2 or 3
 * @return the value for s_dec
 * @throws CSVProcessException if <code>month</code> or <code>decadInMonth</code>
 *         have incorrect values
 */
public static Integer getDecad(String month, Integer decadInMonth)
        throws CSVProcessException {
    return getDecad(getDecMonth(month), decadInMonth);
}

/**
 * Obtain the s_dec in the year. <br />
 * The s_dec is 1 -- 36 , 1 is the first decade in November 36 is the 3th decad
 * in October
 * 
 * @param month
 * @param decadInMonth must be 1, 2 or 3
 * @return the value for s_dec
 * @throws CSVProcessException if <code>month</code> or <code>decadInMonth</code>
 *         have incorrect values
 */
public static Integer getDecad(DecMonth month, Integer decadInMonth)
        throws CSVProcessException {
    if (month != null && decadInMonth != null && decadInMonth > 0
            && decadInMonth < 4) {
        return decadInMonth + month.ordinal() * 3;
    } else {
        throw new CSVProcessException("Incorrect decad [month=" + month
                + ", decad=" + decadInMonth + "]");
    }
}

/**
 * Obtain DecMonth for a string
 * 
 * @param month short name of the month (like Nov, NOV or nov for November)
 * @return DecMonth
 * @throws CSVProcessException if <code>month</code> it's a unrecognized string
 *         or null
 */
public static DecMonth getDecMonth(String month) throws CSVProcessException {
    if (month != null) {
        // Obtain DecMonth for a string
        String monthUpper = month.toUpperCase();
        for (DecMonth dec : DecMonth.values()) {
            if (dec.name().equals(monthUpper)) {
                return dec;
            }
        }
    }
    // Incorrect moth
    throw new CSVProcessException("Incorrect month=" + month);
}

/**
 * Obtain a double value of a string or null if it's null or empty
 * 
 * @param doubleString
 * @return Double value
 * @throws CSVProcessException if <code>doubleString</code> it's not a double
 */
public static Double getDoubleValue(String doubleString)
        throws CSVProcessException {
    try {
        return doubleString != null && StringUtils.hasText(doubleString) ? Double
                .parseDouble(doubleString) : null;
    } catch (NumberFormatException nfe) {
        throw new CSVProcessException("Incorrect double=" + doubleString);
    }
}

/**
 * Parse a csv property as known type
 * 
 * @param origin
 * @param type
 * @return
 * @throws Exception
 */
public static Object parse(String origin, CSVPropertyType type)
        throws Exception {
    switch (type) {
    case INTEGER:
        return Integer.parseInt(origin);
    case DOUBLE:
        return CSVIngestUtils.getDoubleValue(origin);
    case IGNORE:
        return null;
    default:
        return origin;
    }
}

}
