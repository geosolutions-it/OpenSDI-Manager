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

import java.util.Comparator;
import java.util.Date;

/**
 * Simple comparator for Geobatch run information
 * 
 * @author adiaz
 */
public class GeoBatchRunInfoComparator implements
        Comparator<GeobatchRunInfo> {

private MODE selectedMode;

/**
 * Default order it's last execution first
 */
public GeoBatchRunInfoComparator(){
    this.selectedMode = MODE.LAST_EXECUTION;
}

public GeoBatchRunInfoComparator(MODE mode){
    this.selectedMode = mode;
}

@Override
public int compare(GeobatchRunInfo o1, GeobatchRunInfo o2) {
    Date le1 = o1.getLastExecutionDate();
    Date le2 = o2.getLastExecutionDate();
    Date lc1 = o1.getLastCheckDate();
    Date lc2 = o2.getLastCheckDate();
    
    switch (selectedMode){
        case LAST_EXECUTION:{
            return le2.compareTo(le1);
        }
        case LAST_EXECUTION_INVERSE:{
            return le1.compareTo(le2);
        }
        case LAST_CHECK:{
            return lc2.compareTo(lc1);
        }
        case LAST_CHECK_INVERSE:{
            return lc1.compareTo(lc2);
        }
    }
    
    // shuldn't execute never
    return 0;
}

/**
 * Order mode for the comparator
 * 
 * @author adiaz
 *
 */
public enum MODE{
    LAST_EXECUTION, LAST_EXECUTION_INVERSE, LAST_CHECK, LAST_CHECK_INVERSE 
}
}