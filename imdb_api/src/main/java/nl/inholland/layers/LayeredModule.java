/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.inholland.layers;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.ServerAddress;
import javax.inject.Singleton;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

/**
 *
 * @author youp
 */
public class LayeredModule extends AbstractModule
{
    @Override
    protected void configure()
    {
    }
    
    @Provides
    @Singleton
    Datastore providesDatastore()
    {
        MongoClient mongo = new MongoClient(new MongoClientURI("mongodb://imdb:imdb@ds121575.mlab.com:21575/imdb"));
        
        Morphia morphia = new Morphia();
        
        return morphia.createDatastore(mongo, "imdb");
    }
   
}
