/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.inholland.layers.persistence;

import java.util.List;
import javax.inject.Inject;
import nl.inholland.layers.model.Director;
import org.mongodb.morphia.Datastore;

/**
 *
 * @author Jeroen
 */
public class DirectorDAO extends BaseDAO<Director> 
{
    
    @Inject
    public DirectorDAO(Datastore ds)
    {
        super(Director.class, ds);
    }
    
    public List<Director> getByLastName(String directorName)
    {
        return createQuery().field("lastName").equal(directorName).asList();
    }
    
    public List<Director> getByAge(int age)
    {
        return createQuery().field("age").equal(age).asList();
    }
    
}
