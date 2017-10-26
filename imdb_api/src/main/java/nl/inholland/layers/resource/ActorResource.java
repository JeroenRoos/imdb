/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.inholland.layers.resource;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import nl.inholland.layers.model.Actor;
import nl.inholland.layers.model.Director;
import nl.inholland.layers.presentation.model.ActorPresenter;
import nl.inholland.layers.service.ActorService;

/**
 *
 * @author Tiel
 */
@Path("/actors")
@Consumes (MediaType.APPLICATION_JSON)
@Produces (MediaType.APPLICATION_JSON)
public class ActorResource extends BaseResource{
    
    private final ActorService actorService;
    private final ActorPresenter actorPresenter;
    
    @Inject
    public ActorResource( ActorService actorService,
            ActorPresenter actorPresenter){
        this.actorService = actorService;
        this.actorPresenter = actorPresenter;
    }
    
    //get all
    @GET
    public List<Actor> getAll(@DefaultValue("") @QueryParam("lastName") String lastName, 
            @DefaultValue("") @QueryParam("age") String age ){
        
        List<Actor> actors;
        if(!"".equals(lastName)){
             actors = actorService.getByLastName(lastName);
            
            return actorPresenter.present(actors);

        }else if (!"".equals(age)){
                    
            actors = actorService.getByAge(age);
            
            return actorPresenter.present(actors);    
            
        }       
        else{
            
            actors = actorService.getAll();
        
            return actorPresenter.present(actors);
        }
      
    }
    
    
    //get by Id
    @GET
    @Path("/{actorId}")
    public Actor get( @PathParam("actorId") String actorId){
        Actor actor = actorService.get(actorId);
        
        return actorPresenter.present(actor);
    }


}
