/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.inholland.layers.persistence;

import java.util.List;
import javax.inject.Inject;
import nl.inholland.layers.model.Director;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

/**
 *
 * @author Jeroen
 */
public class DirectorDAO extends BaseDAO<Director> 
{
    private Datastore ds;
    
    @Inject
    public DirectorDAO(Datastore ds)
    {
        super(Director.class, ds);
        this.ds = ds;
    }
    
    public List<Director> getByLastName(String directorName)
    {
        return createQuery().field("lastName").equal(directorName).asList();
    }
    
    public List<Director> getByAge(int age)
    {
        return createQuery().field("age").equal(age).asList();
    }
    
    public void deleteManyById(List<ObjectId> lstObjects)
    {
        Query<Director> query = ds.createQuery(Director.class);
        ds.delete(query.filter("_id in", lstObjects));
    }
    
    public void updateManyById()
    {
        //save(obj);
    }
    
}
