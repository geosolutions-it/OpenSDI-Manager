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
package it.geosolutions.opensdi.operations;

import it.geosolutions.opensdi.utils.GeoBatchRunInfoUtils;

import org.springframework.security.core.context.SecurityContextHolder;

/**
 * User operation. This operation can be configurable as different directory for each user logged
 * 
 * @author adiaz
 */
public abstract class UserOperation extends AbstractOperationController implements Operation{

/**
 * Use different directory by user
 */
protected Boolean differentDirectoryByUser;

/**
 * @return the defaultBaseDir
 */
public abstract String getDefaultBaseDir();

/**
 * Obtain run time directory
 * 
 * @return default directory if differentDirectoryByUser it's disabled or user
 *         directory otherwise
 */
public String getRunTimeDir() {
    String dir = null;
    if (Boolean.TRUE.equals(differentDirectoryByUser)) {

        String username = SecurityContextHolder.getContext()
                .getAuthentication().getName();
        dir = GeoBatchRunInfoUtils.getRunDir(getDefaultBaseDir(), username);
    } else {
        dir = getDefaultBaseDir();
    }
    return dir;
}

/**
 * @return the differentDirectoryByUser
 */
public Boolean getDifferentDirectoryByUser() {
    return differentDirectoryByUser;
}

/**
 * @param differentDirectoryByUser the differentDirectoryByUser to set
 */
public void setDifferentDirectoryByUser(Boolean differentDirectoryByUser) {
    this.differentDirectoryByUser = differentDirectoryByUser;
}

}
