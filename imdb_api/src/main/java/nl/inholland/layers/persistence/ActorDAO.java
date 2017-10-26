/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.inholland.layers.persistence;

import java.util.List;
import javax.inject.Inject;
import nl.inholland.layers.model.Actor;
import org.mongodb.morphia.Datastore;

/**
 *
 * @author CTiel
 */
public class ActorDAO extends BaseDAO<Actor> {

    @Inject
    public ActorDAO(Datastore ds)
    {
        super(Actor.class, ds);
    }
    
    /**
     *
     * @param lastName
     * @return
     */
    public List<Actor> getByName(String lastName){
        return createQuery().field("lastName").equal(lastName).asList();
    }    
        public List<Actor> getByAge(int age)
    {
        return createQuery().field("age").equal(age).asList();
    }
}
