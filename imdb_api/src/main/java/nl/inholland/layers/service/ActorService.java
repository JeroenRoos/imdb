/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.inholland.layers.service;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import nl.inholland.layers.model.Actor;
import nl.inholland.layers.persistence.ActorDAO;
import org.bson.types.ObjectId;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

/**
 *
 * @author CTiel
 */
public class ActorService extends BaseService {
    
    
    private final ActorDAO actorDAO;

    @Inject
    public ActorService(ActorDAO actorDAO){
        this.actorDAO = actorDAO;
    }
    
    //get actor by id
    public Actor getById(String actorId)
    {
        Actor actor = (Actor) super.getById(actorId,actorDAO);

        return actor;
    }
    
    //get all actors
        public List<Actor> getAll()
    {
        List<Actor> actors = super.getAll(actorDAO);
        
        return actors;
    }
    
        
    //get actor by last name
        public List<Actor> getByLastName(String lastName)
        {
        List<Actor> actors = actorDAO.getByLastName(lastName);
        return actors;
    }
        
        //get actor by first name 
        public List<Actor> getByFirstName(String firstName)
        {
        List<Actor> actors = actorDAO.getByFirstName(firstName);
        return actors;
    }    
     public List<Actor> getByAge(String actorAge)
    {
        int age = 0;
        try
        {
            age = Integer.parseInt(actorAge);
        }
        catch (NumberFormatException ex)
        {
            super.parsingError("Something went wrong while converting the age to an integer.");
        }
        
        List<Actor> actors = actorDAO.getByAge(age);
        return actors;
    }
     
      
     
    // update an actor
    public void update(String actorId, Actor actor)
    {
        ObjectId objectId;
        if (ObjectId.isValid(actorId))
        {
            objectId = new ObjectId(actorId);
            Query myQuery = actorDAO.createQuery().field("_id").equal(objectId);
            UpdateOperations<Actor> update = actorDAO.createUpdateOperations();

            checkUpdateValidity(update, actor, objectId);          
            actorDAO.update(myQuery, update);
        }
        else
            super.noValidObjectId("The actor id is not valid");
    }
    
    
    // update multiple actors
    public void updateMany(String[] ids, Actor actor)
    {
        for (int i = 0; i < ids.length; i++)
        {
            if (ObjectId.isValid(ids[i]))
            {
                ObjectId objectId = new ObjectId(ids[i]);
                
                Query myQuery = actorDAO.createQuery().field("_id").equal(objectId);
                UpdateOperations<Actor> update = actorDAO.createUpdateOperations();
                checkUpdateValidity(update, actor, objectId); 
                actorDAO.update(myQuery, update);
            }
            else
                super.noValidObjectId("The actor id is not valid");
        }
        
    }
    
    //check if input is valid
        private void checkUpdateValidity(UpdateOperations<Actor> update, Actor actor, ObjectId id)
    {
        if (!"".equals(actor.getFirstName()) && actor.getFirstName() != null)
            update.set("firstName", actor.getFirstName());
        else if ("".equals(actor.getFirstName()))
            super.emptyField("Firstname cannot be an empty string");

        if (!"".equals(actor.getLastName()) && actor.getLastName() != null)
            update.set("lastName", actor.getLastName());
        else if ("".equals(actor.getLastName()))
            super.emptyField("Lastname cannot be an empty string");

        if (!"".equals(actor.getAge()))
            update.set("age", actor.getAge());
        else if ("".equals(actor.getAge()))
        {      
            super.emptyField("Actor must be older than 18.");
        }
    }
     private void checkCreateValidity(Actor actor)
    {
        if ("".equals(actor.getFirstName()) || actor.getFirstName() == null)
            super.emptyField("Firstname cannot be an empty string");
        
        if ("".equals(actor.getLastName()) || actor.getLastName() == null)
           super.emptyField("Lastname cannot be an empty string");
        
        if (actor.getAge() == 0)
            super.emptyField("Director cannot be aged 0");
    }
    //create new actor     
    public void create(Actor actor)
    {
        checkCreateValidity(actor);
        actorDAO.create(actor);
    }

    public void createMany(List<Actor> lstActors)
    {
        for (Actor actor : lstActors)
            checkCreateValidity(actor);
        
        actorDAO.createMany(lstActors);
    }
    
    //delete actor
    
        public void delete(String actorId)
    {
         super.delete(actorId, actorDAO);
    }

    public void deleteMany(String[] ids)
    {         
        super.deleteMany(ids, actorDAO);
    }
    
}
