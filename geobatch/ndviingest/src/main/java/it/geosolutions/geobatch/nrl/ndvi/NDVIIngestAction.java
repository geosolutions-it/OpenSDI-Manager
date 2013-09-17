/*
 *  GeoBatch - Open Source geospatial batch processing system
 *  http://geobatch.geo-solutions.it/
 *  Copyright (C) 2007-2012 GeoSolutions S.A.S.
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

import it.geosolutions.geobatch.annotations.Action;
import it.geosolutions.filesystemmonitor.monitor.FileSystemEvent;
import it.geosolutions.geobatch.annotations.CheckConfiguration;
import it.geosolutions.geobatch.flow.event.action.ActionException;
import it.geosolutions.geobatch.flow.event.action.BaseAction;
import it.geosolutions.geobatch.imagemosaic.ImageMosaicCommand;
import it.geosolutions.tools.commons.file.Path;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.EventObject;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;


/**
 * Takes a list of files and creates the proper ImageMosaicCommand for granule ingestion.
 *
 * @author ETj (etj at geo-solutions.it)
 */
@Action(configurationClass=NDVIIngestConfiguration.class)
public class NDVIIngestAction extends BaseAction<EventObject>  {

    private final static Logger LOGGER = LoggerFactory.getLogger(NDVIIngestAction.class);

    // dv99101_pak.tif
    private static final String  TIF_REGEX = "dv(\\d\\d)(\\d\\d)(\\d)_pak.tif";
    private static final Pattern TIF_PATTERN = Pattern.compile(TIF_REGEX);

    private NDVIIngestConfiguration configuration;

    public NDVIIngestAction(final NDVIIngestConfiguration configuration) {
        super(configuration);
        this.configuration = configuration;
    }

    @Override
    @CheckConfiguration
    public boolean checkConfiguration() {
        if(! configuration.getDestinationDir().isAbsolute()) {
            LOGGER.warn("Destination dir must be absolute");
            return false;
        }
        if(! configuration.getDestinationDir().isDirectory()) {
            LOGGER.warn("Destination dir is not a directory");
            return false;
        }

        return true;
    }

    /**
     * 
     */
    public Queue<EventObject> execute(Queue<EventObject> events) throws ActionException {

        listenerForwarder.setTask("Check config");
        
        listenerForwarder.started();

        NDVIIngestConfiguration configuration = getConfiguration();
        if (configuration == null) {
            throw new IllegalStateException("ActionConfig is null.");
        }

//        List<File> ndviFiles = new ArrayList<File>();
        Map<File, Calendar[]> inputFiles = new TreeMap<File, Calendar[]>();

        while(! events.isEmpty()) {
            EventObject event = events.poll();
            if(event instanceof FileSystemEvent) {
                FileSystemEvent fse = (FileSystemEvent) event;
                File source = fse.getSource();
                if( ! source.exists() ) {
                    LOGGER.error("File does not exist: " + source);
                    continue;
                }
                Calendar interval[];
                try {
                    interval = parseDekDate(source.getName());
                } catch (ActionException e) {
                    LOGGER.error("Error parsing source name: " + e.getMessage());
                    continue;
                }

                inputFiles.put(source, interval);
            } else {
                throw new ActionException(this, "EventObject not handled " + event);
            }
        }

        ImageMosaicCommand imc = processFiles(inputFiles);

        LinkedList<EventObject> ret = new LinkedList<EventObject>();
        ret.add(new EventObject(imc));
        return ret;
    }

    private ImageMosaicCommand processFiles(Map<File, Calendar[]> inputFiles) {

        ImageMosaicCommand imc = new ImageMosaicCommand(configuration.getDestinationDir(), new ArrayList<File>(), null);

        for (File file : inputFiles.keySet()) {
            Calendar []interval = inputFiles.get(file);
            SimpleDateFormat dt = new SimpleDateFormat("yyyyMMdd");
            String startDate = dt.format(interval[0].getTime());
            String endDate = dt.format(interval[1].getTime());
            String outname = "dv_"+startDate+"_"+endDate+".tif";

            File outFile = new File(getTempDir(), outname);
            LOGGER.info("Copying " + file + " --> " + outFile);
            try {
                FileUtils.copyFile(file, outFile);
            } catch (IOException ex) {
                LOGGER.error("Error copying " + file + " --> " + outFile +" -- Will be skipped.", ex);
                continue;
            }

            imc.getAddFiles().add(outFile);
        }

        return imc;        
    }

    /**
     * @return start date and end date in the array
     */
    public  Calendar[] parseDekDate(String filename) throws ActionException {
        Matcher matcher = TIF_PATTERN.matcher(filename);
        if(matcher.matches()) {
            int y = 0;
            int m = 0;
            int k = 0;
            try {
                y = Integer.parseInt(matcher.group(1));
                m = Integer.parseInt(matcher.group(2));
                k = Integer.parseInt(matcher.group(3));
            } catch (NumberFormatException ex) {
                throw new ActionException(this, "Can't parse filename " + filename);
            }

            // century adjustment
            if(y < 50)
                y += 2000;
            else
                y += 1900;

            LOGGER.debug("Found date y="+y+ " m="+m+" k="+k);

            int startDay = k == 1? 1 : k==2 ? 11 : k==3? 21 : 0;
            if(startDay == 0)
                throw new ActionException(this, "Bad dekad " + k);
            Calendar start = new GregorianCalendar(y, m-1, startDay);

            int endDay = startDay + 9;
            if(k==3) {
                endDay = start.getActualMaximum(Calendar.DAY_OF_MONTH);
            }
            Calendar end = new GregorianCalendar(y, m-1, endDay);

            SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
            LOGGER.info("Granule interval is " + dt.format(start.getTime()) + " - " + dt.format(end.getTime()));

            return new Calendar[]{start,end};
        } else {
            throw new ActionException(this, "Can't parse filename " + filename);
        }
    }

}

