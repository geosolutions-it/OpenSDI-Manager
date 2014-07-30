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
package it.geosolutions.opensdi.destination.dao.impl;

import it.geosolutions.opensdi.destination.dao.TraceDAO;
import it.geosolutions.opensdi.destination.model.Trace;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 * Trace DAO implementation
 * 
 * @author adiaz
 */
public class TraceDAOImpl extends GenericHibernateDAOImpl<Trace, BigInteger>
        implements TraceDAO {

/**
 * Indicates the window allowed to found a trace element in milliseconds
 */
private Long maxDiferenceInMilliseconds = Long.MAX_VALUE;

/**
 * Search closest trace element for file. This method uses date and trace
 * elements to obtain the closest one
 * 
 * @param fileName
 * @param executionDate
 * @return trace element closest to executionDate
 */
public Trace searchClosestTraceByFile(String fileName, Date executionDate) {
    List<Trace> orderedList = searchTraceByFile(fileName);
    long interval = Long.MAX_VALUE;
    Trace closest = null;
    if (orderedList != null) {
        if (executionDate != null) {
            // looking for closest trace element
            for (Trace trace : orderedList) {
                long traceInterval = getInterval(trace, executionDate);
                if (traceInterval < interval) {
                    interval = traceInterval;
                    if (interval < maxDiferenceInMilliseconds) {
                        closest = trace;
                    }
                }
            }
        } else if (!orderedList.isEmpty()) {
            // we can't use the last execution, then return the last one
            closest = orderedList.get(0);
        }
    }
    return closest;
}

/**
 * Search all trace elements for a file
 * 
 * @param runfileName
 * @return trace elements found for the file name
 */
public List<Trace> searchTraceByFile(String fileName) {
    @SuppressWarnings("unchecked")
    List<Trace> orderedList = getSession()
            .createCriteria(persistentClass)
            // we return the result filter by name
            .add(Restrictions.eq("nome_file", fileName))
            // ordered by date
            .addOrder(Order.desc("data"))
            .addOrder(Order.desc("data_imp_storage"))
            .addOrder(Order.desc("data_elab")).list();
    return orderedList;
}

/**
 * @return the maxDiferenceInMilliseconds
 */
public Long getMaxDiferenceInMilliseconds() {
    return maxDiferenceInMilliseconds;
}

/**
 * @param maxDiferenceInMilliseconds the maxDiferenceInMilliseconds to set
 */
public void setMaxDiferenceInMilliseconds(Long maxDiferenceInMilliseconds) {
    this.maxDiferenceInMilliseconds = maxDiferenceInMilliseconds;
}

/**
 * Obtain absolute interval between all operations in the trace element and a
 * date
 * 
 * @param trace
 * @param lastExecutionDate
 * @return amount of differences between date and operation dates
 */
private long getInterval(Trace trace, Date date) {
    long traceInterval = 0;
    if (trace.getData() != null) {
        traceInterval += getTimeBreak(date, trace.getData());
    }
    if (trace.getData_elab() != null) {
        traceInterval += getTimeBreak(date, trace.getData_elab());
    }
    if (trace.getData_imp_siig() != null) {
        traceInterval += getTimeBreak(date, trace.getData_imp_siig());
    }
    if (trace.getData_imp_storage() != null) {
        traceInterval += getTimeBreak(date, trace.getData_imp_storage());
    }
    return traceInterval;
}

/**
 * @param date1
 * @param date2
 * @return
 */
private long getTimeBreak(Date date1, Date date2) {
    return Math.abs(date1.getTime() - date2.getTime());
}

/**
 * Delete a process for a trace
 * 
 * @param idTrace identifier
 */
public void deleteProcess(BigInteger idProcess) {
    getSession().createSQLQuery(
            "delete from siig_t_processo where id_processo = " + idProcess)
            .executeUpdate();
}

/**
 * Delete a trace
 * 
 * @param idTrace identifier
 */
public void deleteTrace(BigInteger idTrace) {
    getSession().createSQLQuery(
            "delete from siig_t_tracciamento where id_tracciamento = " + idTrace)
            .executeUpdate();
}

}
