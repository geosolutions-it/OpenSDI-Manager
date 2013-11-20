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

import org.springframework.beans.factory.annotation.Autowired;

import it.geosolutions.opensdi.service.GeoBatchClient;

/**
 * GeoBatch operation. It uses GeoBatchClient autowired for the GeoBatch access
 * 
 * @author adiaz
 */
public abstract class GeoBatchOperationImpl implements GeoBatchOperation{

@Autowired
GeoBatchClient geobatchClient;

/**
 * @return the geobatchRestUrl
 */
public String getGeobatchRestUrl(){
    return geobatchClient.getGeobatchRestUrl();
}

/**
 * @return the geostoreUsername
 */
public String getGeobatchUsername(){
    return geobatchClient.getGeobatchUsername();
}

/**
 * @return the geostorePassword
 */
public String getGeobatchPassword(){
    return geobatchClient.getGeobatchPassword();
}

}
