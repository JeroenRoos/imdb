/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.inholland.layers.service;

import java.util.List;
import javax.inject.Inject;
import nl.inholland.layers.model.Actor;
import nl.inholland.layers.persistence.ActorDAO;

/**
 *
 * @author CTiel
 */
public class ActorService extends BaseService {
    
    //business logic
    
    
    private final ActorDAO actorDAO;
    private final ResultService resultService = new ResultService();
    @Inject
    public ActorService(ActorDAO actorDAO){
        this.actorDAO = actorDAO;
    }
    
    //get actor by id
    public Actor get(String actorId)
    {
        Actor actor = actorDAO.get(actorId);
        return actor;
    }
    
    //get all actors
        public List<Actor> getAll()
    {
        List<Actor> actors = actorDAO.getAll();
        
        if (actors.isEmpty())
            resultService.requireResult(actors, "No actors found");

        return actors;
    }
    
        
    //get actor by last name
        public List<Actor> getByLastName(String lastName)
        {
        List<Actor> actors = actorDAO.getByName(lastName);
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
            resultService.parsingError("Something went wrong while converting the age to an integer.");
        }
        
        List<Actor> actors = actorDAO.getByAge(age);
        return actors;
    }
}
