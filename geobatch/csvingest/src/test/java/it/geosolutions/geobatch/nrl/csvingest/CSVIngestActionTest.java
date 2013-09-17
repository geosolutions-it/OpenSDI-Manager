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
package it.geosolutions.geobatch.nrl.csvingest;

import it.geosolutions.filesystemmonitor.monitor.FileSystemEvent;
import it.geosolutions.filesystemmonitor.monitor.FileSystemEventType;
import static it.geosolutions.geobatch.nrl.csvingest.BaseDAOTest.cropDataDAO;
import static it.geosolutions.geobatch.nrl.csvingest.BaseDAOTest.cropDescriptorDAO;
import it.geosolutions.nrl.model.CropDescriptor;
import it.geosolutions.nrl.model.Season;
import java.io.File;
import java.io.FileFilter;
import java.util.EventObject;
import java.util.LinkedList;
import java.util.Queue;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.SuffixFileFilter;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ETj (etj at geo-solutions.it)
 */
public class CSVIngestActionTest extends BaseDAOTest {

    public CSVIngestActionTest() {
    }

    /**
     * Test of execute method, of class CSVIngestAction.
     */
//    @Test
    public void testExecute() throws Exception {

        Queue<EventObject> events = new LinkedList<EventObject>();
        File cropFile = loadFile("testdata/cropdistr.csv");
        assertNotNull(cropFile);

        { // create FK crop descriptor
            CropDescriptor cd = new CropDescriptor();
            cd.setId("crop0");
            cd.setLabel("label0");
            cd.setSeasons(Season.KHARIF);
            cropDescriptorDAO.persist(cd);
        }

        FileSystemEvent event = new FileSystemEvent(cropFile, FileSystemEventType.FILE_ADDED);
        events.add(event);

        CSVIngestAction action = new CSVIngestAction(new CSVIngestConfiguration(null, null, null));
        action.setCropDataDao(cropDataDAO);
        action.setCropDescriptorDao(cropDescriptorDAO);
        action.afterPropertiesSet();

        Queue result = action.execute(events);

        assertEquals(1, cropDataDAO.count(null));
    }

    @Test
    public void laodAll() throws Exception {

        createCropDescriptors();

        Queue<EventObject> events = new LinkedList<EventObject>();
        File dir = loadFile("all");
        assertNotNull(dir);
        assertTrue(dir.isDirectory());

        CSVIngestAction action = new CSVIngestAction(new CSVIngestConfiguration(null, null, null));
        action.setCropDataDao(cropDataDAO);
        action.setCropDescriptorDao(cropDescriptorDAO);
        action.setAgrometDao(agrometDAO);
        action.afterPropertiesSet();


        for(File file : FileUtils.listFiles(dir, new String[]{"csv"}, true)) {
            FileSystemEvent event = new FileSystemEvent(file, FileSystemEventType.FILE_ADDED);
            events.add(event);
            Queue result = action.execute(events);
        }

    }

    private void createCropDescriptors() {
            cropDescriptorDAO.persist(new CropDescriptor("Rice",  "rice", Season.KHARIF));
            cropDescriptorDAO.persist(new CropDescriptor("Maize", "maize", Season.RABI));
            cropDescriptorDAO.persist(new CropDescriptor("Wheat", "wheat", Season.RABI));
            cropDescriptorDAO.persist(new CropDescriptor("Sugarcane", "Sugarcane", Season.RABI));
            cropDescriptorDAO.persist(new CropDescriptor("Cotton", "Cotton", Season.RABI));

    }


}