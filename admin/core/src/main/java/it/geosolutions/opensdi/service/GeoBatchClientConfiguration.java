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
package it.geosolutions.opensdi.service;

/**
 * Simple envelop to use same parameters for GeoBatch connection
 * 
 * @author adiaz
 */
public interface GeoBatchClientConfiguration {

/**
 * @return the geobatchRestUrl
 */
public String getGeobatchRestUrl();

/**
 * @return the geostoreUsername
 */
public String getGeobatchUsername();

/**
 * @return the geostorePassword
 */
public String getGeobatchPassword();

/**
 * @param geobatchRestUrl the geobatchRestUrl to set
 */
public void setGeobatchRestUrl(String geobatchRestUrl);

/**
 * @param geobatchUsername the geobatchUsername to set
 */
public void setGeobatchUsername(String geobatchUsername);

/**
 * @param geobatchPassword the geobatchPassword to set
 */
public void setGeobatchPassword(String geobatchPassword);

}
