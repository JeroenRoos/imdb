/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.inholland.layers.persistence;

import java.util.List;
import javax.inject.Inject;
import nl.inholland.layers.model.Actor;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

/**
 *
 * @author CTiel
 */
public class ActorDAO extends BaseDAO<Actor> {

    private Datastore ds;

    @Inject
    public ActorDAO(Datastore ds) {
        super(Actor.class, ds);
        this.ds = ds;
    }

    public List<Actor> getByFirstName(String firstName) {
        return createQuery().field("firstName").equal(firstName).asList();
    }

    public List<Actor> getByLastName(String lastName) {
        return createQuery().field("lastName").equal(lastName).asList();
    }

    public List<Actor> getByAge(int age) {
        return createQuery().field("age").equal(age).asList();
    }

    public void deleteManyById(List<ObjectId> lstObjects) {
        Query<Actor> query = ds.createQuery(Actor.class);
        ds.delete(query.filter("_id in", lstObjects));
    }
        public Actor getById(ObjectId id) {
        return createQuery().field("_id").equal(id).get();
    }
    public void updateManyById() {
        //
    }

}
