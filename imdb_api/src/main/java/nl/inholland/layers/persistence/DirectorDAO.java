/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.inholland.layers.persistence;

import java.util.List;
import javax.inject.Inject;
import nl.inholland.layers.model.Director;
import nl.inholland.layers.model.Genre;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

/**
 *
 * @author Jeroen
 */


// The DAO class for Directors
// This class is used to perform a queries and return the result to the service
// The basic requests are handled by BaseDAO, only director specific request will be handled by this DAO
public class DirectorDAO extends BaseDAO<Director> 
{
    private Datastore ds;
    
    @Inject
    public DirectorDAO(Datastore ds)
    {
        super(Director.class, ds);
        this.ds = ds;
    }
    
    // Get and return all directors with a specific lastname
    public List<Director> getByLastName(String lastname)
    {
        return createQuery().field("lastName").equal(lastname).asList();
    }
    
    
    // Get and return all directors with a specific age
    public List<Director> getByAge(int age)
    {
        return createQuery().field("age").equal(age).asList();
    }
    
    
    // Delete multiple directors by ID
    public void deleteManyById(List<ObjectId> lstObjects)
    {
        Query<Director> query = ds.createQuery(Director.class);
        ds.delete(query.filter("_id in", lstObjects));
    }
    
    
    // Update multiple directors 
    public void updateMany(Query[] lstQueries, UpdateOperations[] lstUpdateOperations)
    {
        for (int i = 0; i < lstUpdateOperations.length; i++)
            update(lstQueries[i], lstUpdateOperations[i]);
    }
    
}
