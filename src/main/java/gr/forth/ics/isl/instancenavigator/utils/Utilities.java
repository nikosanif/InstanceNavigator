/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.forth.ics.isl.instancenavigator.utils;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import gr.forth.ics.isl.instancenavigator.Resources;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import org.apache.log4j.Logger;

/**
 *
 * @author Anyfantis Nikos (nanifant 'at' ics 'dot' forth 'dot' gr)
 */
public class Utilities {
    private static Logger logger = Logger.getLogger(Utilities.class);
    
    public static Model retreiveOntology_fromFile(String filePath) {
        try {
            File initialFile = new File(filePath);
            InputStream ins = new FileInputStream(initialFile);
            
            final Model base = ModelFactory.createDefaultModel();
            base.read(ins, null);
            
            return base;
        } catch (Exception ex) {
            logger.error("Cannot retreive target record file. ("+filePath+")",ex);
            return null;
        } 
    }
    
}
