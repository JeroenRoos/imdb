/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.inholland.layers.presentation.model;

import java.util.List;
import nl.inholland.layers.model.Actor;

/**
 *
 * @author Tiel
 */
public class ActorPresenter extends BasePresenter {
    
    //present a list of actors
    public List<Actor> present(List<Actor> actors)
    {
        
        return actors;
        
    }

    //present an actor
    public Actor present(Actor actor)
    {               
        return actor;
    }
}
