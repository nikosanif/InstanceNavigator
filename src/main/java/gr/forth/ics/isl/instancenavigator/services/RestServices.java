/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.forth.ics.isl.instancenavigator.services;

import gr.forth.ics.isl.instancenavigator.Resources;
import gr.forth.ics.isl.instancenavigator.instances.InstanceDataGenerator;
import gr.forth.ics.isl.instancenavigator.instances.data.Instances;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import org.apache.log4j.Logger;

/**
 * REST Web Service
 *
 * @author Anyfantis Nikos (nanifant 'at' ics 'dot' forth 'dot' gr)
 */
@Path("instances")
public class RestServices {
    private static Logger logger = Logger.getLogger(RestServices.class);

    @GET
    @Path("/get")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Instances getXml() {
        try {
            String recordsFile = Resources.getFirstFileFromFolder();
            Instances results = new Instances();
            InstanceDataGenerator generator = new InstanceDataGenerator();
            results = generator.createInstancesData(recordsFile);
            return results;
        } catch (Exception ex) {
           logger.error("Cannot generage instances! ",ex);
           return null; 
        }
    }
    
}
