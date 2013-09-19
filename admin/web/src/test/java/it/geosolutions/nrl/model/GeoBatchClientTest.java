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
package it.geosolutions.nrl.model;

import static org.junit.Assume.assumeTrue;
import it.geosolutions.geobatch.services.rest.GeoBatchRESTClient;
import it.geosolutions.geobatch.services.rest.model.RESTFlowList;
import it.geosolutions.geobatch.services.rest.model.RESTFlowShort;

import java.net.ConnectException;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author Lorenzo Natali (lorenzo.natali at geo-solutions.it)
 */
public class GeoBatchClientTest {
   //
    
    private final static Logger LOGGER = Logger.getLogger(GeoBatchClientTest.class);

    public GeoBatchClientTest() {
    }


    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void before() throws Exception {
        GeoBatchRESTClient client = createClient();
        assumeTrue(pingGeoBatch(client));

    }

    protected GeoBatchRESTClient createClient() {
        GeoBatchRESTClient client = new GeoBatchRESTClient();
        client.setGeostoreRestUrl("http://localhost:8081/geobatch/rest/");
        client.setUsername("admin");
        client.setPassword("admin");

        return client;
    }

    protected boolean pingGeoBatch(GeoBatchRESTClient client) {
        try {
            client.getFlowService().getFlowList();
            return true;
        } catch (Exception ex) {
             LOGGER.debug("Error connecting to GeoBatch", ex);
            //... and now for an awful example of heuristic.....
            Throwable t = ex;
            while(t!=null) {
                if(t instanceof ConnectException) {
                    LOGGER.warn("Testing GeoBatch is offline");
                    return false;
                }
                t = t.getCause();
            }
            throw new RuntimeException("Unexpected exception: " + ex.getMessage(), ex);
        }
    }

    @Test
    public void testGetFlows() {
        GeoBatchRESTClient client = createClient();
        RESTFlowList l = client.getFlowService().getFlowList();
        Iterator<RESTFlowShort> it = l.iterator();
        while(it.hasNext()){
            RESTFlowShort flow = it.next();
            System.out.println(flow.getDescription());
        }
    }
    @Ignore
    @Test
    public void testGetFlow() {
        // TODO

    }
}