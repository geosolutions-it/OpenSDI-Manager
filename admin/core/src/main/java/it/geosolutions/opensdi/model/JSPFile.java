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

import it.geosolutions.opensdi.dto.GeobatchRunInfo;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Map;

/**
 * File utility class to provide JSP compliant methods
 * 
 * @author Lorenzo Pini
 */
public class JSPFile extends File {
private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
private static final long serialVersionUID = 6112519219077616669L;
private Map<String, GeobatchRunInfo> runInfo;

/**
 * Expose length() method as Size
 * 
 * @return long length of file
 */
public long getSize() {
    return this.length();
}

/**
 * Expose isDirectory() method
 * 
 * @return boolean isDirectory()
 */
public boolean getIsDirectory() {
    return this.isDirectory();
}

/**
 * Expose lastModified() method
 * 
 * @return String formatted last modified value
 */
public String getLastModified() {
    return sdf.format(this.lastModified());
}

public Map<String, GeobatchRunInfo> getRunInfo() {
    return runInfo;
}

public void setRunInfo(Map<String, GeobatchRunInfo> runInfo) {
    this.runInfo = runInfo;
}

public JSPFile(String path) {
    super(path);
}

}
