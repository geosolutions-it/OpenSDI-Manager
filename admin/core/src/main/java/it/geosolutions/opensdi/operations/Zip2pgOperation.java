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

import java.util.ArrayList;

import org.springframework.stereotype.Controller;

/**
 * Generalize in file operation and configure it in constructor
 * 
 * @author adiaz
 */
@Controller
public class Zip2pgOperation extends FileOperation {

public Zip2pgOperation(){
    super();
    // Default configuration
    this.name = "Zip2pg";
    this.path = "zip2pg";
    this.extensions = new ArrayList<String>();
    this.extensions.add("zip");
}

@Override
public boolean isMultiple() {
    return false;
}

}