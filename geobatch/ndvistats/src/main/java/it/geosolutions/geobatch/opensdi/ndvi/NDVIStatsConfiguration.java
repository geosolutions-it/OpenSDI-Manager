/*
 *  GeoBatch - Open Source geospatial batch processing system
 *  http://geobatch.geo-solutions.it/
 *  Copyright (C) 2007-2008-2012 GeoSolutions S.A.S.
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
package it.geosolutions.geobatch.opensdi.ndvi;

import it.geosolutions.geobatch.configuration.event.action.ActionConfiguration;

/**
 * NDVI statistics configuration
 * 
 * @author adiaz
 */
public class NDVIStatsConfiguration extends ActionConfiguration {

private String defaultMaskUrl;
private String dbType;
private String dbHost;
private String dbPort;
private String dbSchema;
private String dbName;
private String dbUser;
private String dbPasswd;
private String tiffDirectory;
private String outputDirectory;
private String csvSeparator = ",";

public NDVIStatsConfiguration(String id, String name, String description) {
    super(id, name, description);
}

@Override
public NDVIStatsConfiguration clone() {
    final NDVIStatsConfiguration configuration = (NDVIStatsConfiguration) super.clone();
    return configuration;
}

/**
 * @return the defaultMaskUrl
 */
public String getDefaultMaskUrl() {
    return defaultMaskUrl;
}

/**
 * @param defaultMaskUrl the defaultMaskUrl to set
 */
public void setDefaultMaskUrl(String defaultMaskUrl) {
    this.defaultMaskUrl = defaultMaskUrl;
}

/**
 * @return the dbType
 */
public String getDbType() {
    return dbType;
}

/**
 * @param dbType the dbType to set
 */
public void setDbType(String dbType) {
    this.dbType = dbType;
}

/**
 * @return the host
 */
public String getDbHost() {
    return dbHost;
}

/**
 * @param host the host to set
 */
public void setDbHost(String dbHost) {
    this.dbHost = dbHost;
}

/**
 * @return the port
 */
public Integer getDbPort() {
    return Integer.decode(dbPort);
}

/**
 * @param port the port to set
 */
public void setDbPort(String dbPort) {
    this.dbPort = dbPort;
}

/**
 * @return the schema
 */
public String getDbSchema() {
    return dbSchema;
}

/**
 * @param schema the schema to set
 */
public void setSchema(String dbSchema) {
    this.dbSchema = dbSchema;
}

/**
 * @return the database
 */
public String getDbName() {
    return dbName;
}

/**
 * @param database the database to set
 */
public void setDbName(String dbName) {
    this.dbName = dbName;
}

/**
 * @return the user
 */
public String getDbUser() {
    return dbUser;
}

/**
 * @param user the user to set
 */
public void setDbUser(String dbUser) {
    this.dbUser = dbUser;
}

/**
 * @return the passwd
 */
public String getDbPasswd() {
    return dbPasswd;
}

/**
 * @param passwd the passwd to set
 */
public void setDbPasswd(String dbPasswd) {
    this.dbPasswd = dbPasswd;
}

/**
 * @return the tiffDirectory
 */
public String getTiffDirectory() {
    return tiffDirectory;
}

/**
 * @param tiffDirectory the tiffDirectory to set
 */
public void setTiffDirectory(String tiffDirectory) {
    this.tiffDirectory = tiffDirectory;
}

/**
 * @return the outputDirectory
 */
public String getOutputDirectory() {
    return outputDirectory;
}

/**
 * @param outputDirectory the outputDirectory to set
 */
public void setOutputDirectory(String outputDirectory) {
    this.outputDirectory = outputDirectory;
}

/**
 * @return the csvSeparator
 */
public String getCsvSeparator() {
    return csvSeparator;
}

/**
 * @param csvSeparator the csvSeparator to set
 */
public void setCsvSeparator(String csvSeparator) {
    this.csvSeparator = csvSeparator;
}
}
