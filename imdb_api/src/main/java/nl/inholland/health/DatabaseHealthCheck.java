/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.inholland.health;


import com.codahale.metrics.health.HealthCheck;
import com.mongodb.CommandResult;
import com.mongodb.MongoException;
import javax.inject.Inject;
import org.mongodb.morphia.Datastore;

/**
 *
 * @author Jeroen
 */
public class DatabaseHealthCheck extends HealthCheck
{
     private final Datastore datastore;
    
    @Inject
    public DatabaseHealthCheck( Datastore datastore )
    {
        this.datastore = datastore;
    }
    
    @Override
    protected Result check() throws Exception
    {
        try
        {
            CommandResult result = datastore.getDB().getStats();
            if ( result.ok() )
                return Result.healthy();
            else
                return Result.unhealthy( result.getErrorMessage() );
        }
        catch ( MongoException exception ) { }
        
        return Result.unhealthy( "Can't get MongoDB status" );
    }
}
