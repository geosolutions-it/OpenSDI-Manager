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
package it.geosolutions.opensdi.dto;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Run information for GeoBatch flow
 * 
 * @author adiaz
 */
public class GeobatchRunInfo implements Serializable {

/** serialVersionUID */
private static final long serialVersionUID = -7174872618502299685L;

private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

private static SimpleDateFormat sdft = new SimpleDateFormat("HH:mm:ss");

private Long id;

private String externalUid;

private String internalUid;

private String flowUid;

private String flowStatus;

private Date lastExecutionDate;

private Date lastCheckDate;

/**
 * @return the id
 */
public Long getId() {
    return id;
}

/**
 * @param id the id to set
 */
public void setId(Long id) {
    this.id = id;
}

/**
 * @return the externalUid
 */
public String getExternalUid() {
    return externalUid;
}

/**
 * @param externalUid the externalUid to set
 */
public void setExternalUid(String externalUid) {
    this.externalUid = externalUid;
}

/**
 * @return the internalUid
 */
public String getInternalUid() {
    return internalUid;
}

/**
 * @param internalUid the internalUid to set
 */
public void setInternalUid(String internalUid) {
    this.internalUid = internalUid;
}

/**
 * @return the flowUid
 */
public String getFlowUid() {
    return flowUid;
}

/**
 * @param flowUid the flowUid to set
 */
public void setFlowUid(String flowUid) {
    this.flowUid = flowUid;
}

/**
 * @return the flowStatus
 */
public String getFlowStatus() {
    return flowStatus;
}

/**
 * @param flowStatus the flowStatus to set
 */
public void setFlowStatus(String flowStatus) {
    this.flowStatus = flowStatus;
}

/**
 * @return the lastExecution
 */
public String getLastExecution() {
    Date today = new Date();
    String lastExecution = null;
    if (lastExecutionDate != null) {
        lastExecution = sdf.format(lastExecutionDate);
        if (lastExecution.equals(sdf.format(today))) {
            // It's today, we show time!!
            lastExecution = sdft.format(lastExecutionDate);
        }
    }
    return lastExecution;
}

/**
 * @param lastExecution the lastExecution to set
 */
public void setLastExecution(Date lastExecution) {
    this.lastExecutionDate = lastExecution;
}

/**
 * @return the lastCheck
 */
public String getLastCheck() {
    return lastCheckDate != null ? sdf.format(lastCheckDate) : null;
}

/**
 * @param lastCheck the lastCheck to set
 */
public void setLastCheck(Date lastCheck) {
    this.lastCheckDate = lastCheck;
}

/**
 * @return the lastExecutionDate
 */
public Date getLastExecutionDate() {
    return lastExecutionDate;
}

/**
 * @return the lastCheckDate
 */
public Date getLastCheckDate() {
    return lastCheckDate;
}

}
