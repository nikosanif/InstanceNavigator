/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.forth.ics.isl.instancenavigator.utils;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.rdf.model.Property;

/**
 *
 * @author Anyfantis Nikos (nanifant 'at' ics 'dot' forth 'dot' gr)
 */
public class SparQLQueries {
    /**
    * Default prefix for RDFS
    */
    private static final String RDFS_Prefix = "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> \n";
    /**
    * Default prefix for RDF
    */
    private static final String RDF_Prefix = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n";
    
        
    /**
     * Returns a SparQL query for triangles of class.
     * @param clazz OntClass
     * @return String SparQL query
     */
    public static String GetTrianglesOfClass(OntClass clazz){
        String classURI = clazz.getURI();
        
        String query = RDFS_Prefix + RDF_Prefix
                + "SELECT DISTINCT ?AB ?B ?BC ?C ?CA \n"
                + "WHERE{ \n"
                + "     ?AB rdfs:domain <" + classURI + ">. \n"
                + "     ?AB rdfs:range ?B. \n"
                + "     ?BC rdfs:domain ?B. \n"
                + "     ?BC rdfs:range ?C. \n"
                + "     ?CA rdfs:domain ?C. \n"
                + "     ?CA rdfs:range <" + classURI + ">. \n"
                + "     FILTER ( \n"
                + "         <" + classURI + "> != ?B \n"
                + "         && <" + classURI + "> != ?C \n"
                + "         && ?B != ?C \n"
                + "     )"
                + "}";
        
        return query;
    }
    
    /**
     * Returns a SparQL query for getting incoming properties of Individual.
     * @param ind Individual
     * @return String SparQL query
     */
    public static String GetIncomingProperties(Individual ind){
        String indURI = ind.getURI();
        
        String query = RDFS_Prefix + RDF_Prefix
                + "SELECT ?p \n"
                + "WHERE{ \n"
                + "     ?s ?p <" + indURI + ">. \n"    
                + "}";
        
        return query;
    }
    
    /**
     * Returns a SparQL query for getting subject with
     * property and Individual.
     * @param prop Property
     * @param ind Individual
     * @return String SparQL query
     */
    public static String GetSubjectWithPropertiyAndIndividual(Property prop, Individual ind){
        String propURI = prop.getURI();
        String indURI = ind.getURI();
        
        String query = RDFS_Prefix + RDF_Prefix
                + "SELECT ?s \n"
                + "WHERE{ \n"
                + "     ?s <" + propURI + "> <" + indURI + ">. \n"    
                + "}";
        
        return query;
    }
}
