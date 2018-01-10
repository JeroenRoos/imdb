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
import javax.inject.Singleton;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

/**
 *
 * @author youp
 */
public class LayeredModule extends AbstractModule
{
    private final String DB_PASSWORD = System.getenv("DBPassword");
    private final String DB_USERNAME = System.getenv("DBUsername"); 
    
    @Override
    protected void configure()
    {
    }
    
    @Provides
    @Singleton
    Datastore providesDatastore(LayeredConfiguration configuration)
    {
        MongoClient mongo = new MongoClient(new MongoClientURI("mongodb://" + DB_USERNAME + ":" + DB_PASSWORD + "@ds121575.mlab.com:21575/imdb"));
        MongoClient mongo = new MongoClient(new MongoClientURI(configuration.database.mongoURI));

        Morphia morphia = new Morphia();
        
        return morphia.createDatastore(mongo, "imdb");
        //return morphia.createDatastore(mongo, configuration.database.dbName);
    }
   
}
