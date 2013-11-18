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
package it.geosolutions.geobatch.opensdi.csvingest;

import it.geosolutions.filesystemmonitor.monitor.FileSystemEvent;
import it.geosolutions.filesystemmonitor.monitor.FileSystemEventType;
import it.geosolutions.geobatch.opensdi.csvingest.CSVIngestAction;
import it.geosolutions.geobatch.opensdi.csvingest.CSVIngestConfiguration;
import static it.geosolutions.geobatch.opensdi.csvingest.BaseDAOTest.cropDataDAO;
import static it.geosolutions.geobatch.opensdi.csvingest.BaseDAOTest.cropDescriptorDAO;
import it.geosolutions.opensdi.model.CropDescriptor;
import it.geosolutions.opensdi.model.Season;

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

    @Test
    public void loadAll() throws Exception {

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
            LOGGER.info("Loading " + file);
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