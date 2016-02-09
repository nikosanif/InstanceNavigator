/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.forth.ics.isl.instancenavigator.instances;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;
import gr.forth.ics.isl.instancenavigator.instances.data.*;
import gr.forth.ics.isl.instancenavigator.schema_reasoner.SchemaReasoner;

/**
 *
 * @author Anyfantis Nikos (nanifant 'at' ics 'dot' forth 'dot' gr)
 */
public class InstanceDataGenerator {
    private Instances allInstancesData;
    private SchemaReasoner reasoner;
    
    public Instances createInstancesData(String fileName){
        
        this.allInstancesData = new Instances();
        this.reasoner = new SchemaReasoner(fileName);
        
        saveInstances();
        saveClasses();
        
        return this.allInstancesData;
    }

    private void saveInstances() {
        
        for(Individual i : this.reasoner.getIndividuals()){
            
            InstanceRecord instance = new InstanceRecord();
            instance.setUri(i.getURI());
            instance.setLabel(i.getLabel(null));
            
            //SET OUTGOING PROPERTIES
            for(Property p : this.reasoner.getOutgoingPropertiesOfIndividual(i)){
                ExtendedIterator iter = i.listPropertyValues(p);
                while (iter.hasNext()) {
                    PropertyInstance prop = new PropertyInstance();
                    prop.setUri(p.getURI());
                    String label = p.getURI().replace(p.getNameSpace(), "");
                    prop.setLabel(label);
                    
                    RDFNode obj = (RDFNode)iter.next();
                    if(obj.isLiteral()){
                        prop.setType("literal");
                        prop.setResource(obj.asLiteral().getString());
                    }
                    else if(obj.isResource()){
                        prop.setType("resource");
                        prop.setResource(obj.asResource().getURI());
                    }
                    
                    instance.addOutgoingProperty(prop);
                }
            }
            
            //SET INCOMING PROPERTIES
            for(Property p : this.reasoner.getIncomingPropertiesOfIndividual(i)){
                for(String s : this.reasoner.getSubjectOfPropertyAndIndividual(p, i)){
                    PropertyInstance prop = new PropertyInstance();
                    prop.setUri(p.getURI());
                    String label = p.getURI().replace(p.getNameSpace(), "");
                    prop.setLabel(label);
                    prop.setType("resource");
                    prop.setResource(s);
                    instance.addIncomingProperty(prop);
                }
            }
            
            instance.setTypeClass(this.reasoner.getClassTypesOfIndividual(i));
            this.allInstancesData.addInstance(instance);
        }
        
    }

    private void saveClasses() {
        for(OntClass c : this.reasoner.getAllClasses()){
            
            TS_Class clazz = new TS_Class();
            clazz.setUri(c.getURI());
            String label = c.getURI().replace(c.getNameSpace(), "");
            clazz.setLabel(label);
            clazz.setNamespace(c.getNameSpace());
            
            this.allInstancesData.addClass(clazz);
        }
    }
}
