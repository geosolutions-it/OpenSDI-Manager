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
package it.geosolutions.opensdi.destination.dao;

import it.geosolutions.opensdi.destination.model.Trace;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

/**
 * Trace DAO interface for destination project
 * 
 * @author adiaz
 */
public interface TraceDAO extends GenericDAO<Trace, BigInteger> {

/**
 * Search closest trace element for file. This method uses date and trace
 * elements to obtain the closest one
 * 
 * @param fileName
 * @param executionDate
 * @return trace element closest to executionDate
 */
public Trace searchClosestTraceByFile(String fileName, Date executionDate);

/**
 * Search all trace elements for a file
 * 
 * @param runfileName
 * 
 * @return trace elements found for the file name
 */
public List<Trace> searchTraceByFile(String fileName);

/**
 * Delete a trace
 * 
 * @param idTrace identifier
 */
public void deleteTrace(BigInteger idTrace);

/**
 * Delete a process for a trace
 * 
 * @param idTrace identifier
 */
public void deleteProcess(BigInteger idProcess);
}
