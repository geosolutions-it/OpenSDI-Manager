/*
 *  Copyright (C) 2007 - 2012 GeoSolutions S.A.S.
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
package it.geosolutions.geobatch.nrl.ndvi;

import it.geosolutions.filesystemmonitor.monitor.FileSystemEvent;
import it.geosolutions.filesystemmonitor.monitor.FileSystemEventType;
import it.geosolutions.geobatch.flow.event.action.ActionException;
import java.io.File;
import java.io.IOException;
import java.util.EventObject;
import java.util.LinkedList;
import java.util.Queue;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author ETj (etj at geo-solutions.it)
 */
public class NDVIIngestionTest extends BaseContextTest {

    private final Logger LOGGER;

    public NDVIIngestionTest() {
        LOGGER = LoggerFactory.getLogger(getClass());
    }

    @Test
    public void testFileCopy() throws IOException, ActionException {
        NDVIIngestConfiguration cfg = new NDVIIngestConfiguration("", "", "");
        cfg.setDestinationDir(getTempDir());
        NDVIIngestAction action = new NDVIIngestAction(cfg);

        File inputDir = loadFile("in_tiff");

        Queue<EventObject> inq = new LinkedList<EventObject>();
        for (File inp : inputDir.listFiles()) {
            FileSystemEvent e = new FileSystemEvent(inp, FileSystemEventType.FILE_ADDED);
            inq.add(e);
        }

        Queue<EventObject> outq = action.execute(inq);
        for (EventObject eventObject : outq) {
            System.out.println(eventObject);
        }


    }




}
