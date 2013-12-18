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

import it.geosolutions.opensdi.destination.model.TraceLine;

import java.math.BigInteger;
import java.util.List;

/**
 * Trace line DAO interface for destination project
 * 
 * @author adiaz
 */
public interface TraceLineDAO extends GenericDAO<TraceLine, BigInteger> {

/**
 * Search all lines for a trace
 * 
 * @param idTrace identifier of the trace
 * @return all lines
 */
public List<TraceLine> searchByTrace(BigInteger idTrace);

/**
 * Delete all lines for a trace
 * 
 * @param idTrace identifier
 */
public void deleteByTrace(BigInteger idTrace);



}
