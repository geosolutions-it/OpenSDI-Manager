/*

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
import java.util.List;

import org.springframework.stereotype.Controller;

/**
 * Single file common operation. <br />
 * <br />
 * You can use it from your application context as below: <br />
 * <br />
 * <code>&nbsp;&lt;bean id="runnerOp"
 * class="it.geosolutions.opensdi.operations.FileOperation"&gt; <br />
 * &nbsp;&nbsp;&lt;property name="name" value="Example"/&gt; <br />
 * &nbsp;&nbsp;&lt;property name="path" value="runnerOp"/&gt; <br />
 * &nbsp;&nbsp;&lt;property name="extensions"&gt; <br />
 * &nbsp;&nbsp; &lt;list&gt; <br />
 * &nbsp;&nbsp; &lt;value&gt;txt&lt;/value&gt; <br />
 * &nbsp;&nbsp; &lt;/list&gt; <br />
 * &nbsp;&nbsp;&lt;/property&gt; <br />
 * &nbsp;&lt;/bean&gt;</code> <br />
 * <br />
 * Also you can override this properties from a opensdi-config-ovr.properties
 * file located in your classpath or externalizing it.
 * 
 * @author adiaz
 */
@Controller
public class FileOperation extends SingleFileLocalOperation {

/**
 * The name of this Operation
 */
protected String name;

/**
 * The path were to GET the form and POST the request Typically all lower case
 */
protected String path;

/**
 * File extension this Operation will work on
 */
protected List<String> extensions;

/**
 * @return the path
 */
public String getPath() {
    return path;
}

@Override
public String getRESTPath() {
    return getPath();
}

@Override
public List<String> getExtensions() {
    List<String> l = new ArrayList<String>();
    for (String s : extensions) {
        l.add(s);
    }
    return l;
}

/**
 * @param extensions the extensions to set
 */
public void setExtensions(List<String> extensions) {
    this.extensions = extensions;
}

public boolean isMultiple() {
    return false;
}

// TODO: This jsp should be placed in a common folder, set in the
// OperationManager (OperationMapping)

/**
 * @param path the path to set
 */
public void setPath(String path) {
    this.path = path;
}

/**
 * @return the name
 */
public String getName() {
    return name;
}

/**
 * @param name the name to set
 */
public void setName(String name) {
    this.name = name;
}

}