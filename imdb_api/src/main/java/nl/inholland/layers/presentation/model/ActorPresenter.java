/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.inholland.layers.presentation.model;

import java.util.ArrayList;
import java.util.List;
import nl.inholland.layers.model.Actor;
import nl.inholland.layers.model.ActorView;

/**
 *
 * @author CTiel
 */
public class ActorPresenter extends BasePresenter 
{
    
    public List<ActorView> present(List<Actor> lstActors)
    {
        List<ActorView> view = new ArrayList<>();
        for (Actor a : lstActors)
        {
            ActorView directorView = initActorView(a);
            view.add(directorView);
        }
        return view;     
    }

    public ActorView present(Actor actor)
    {               
        ActorView actorView = initActorView(actor);
        return actorView;
    }
    
    private ActorView initActorView(Actor actor)
    {
        ActorView actorView = new ActorView();
        
        actorView.setId(actor.getId());
        actorView.setFirstName(actor.getFirstName());
        actorView.setLastName(actor.getLastName());
        actorView.setAge(actor.getAge());
        
        return actorView;
    }
}
