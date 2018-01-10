/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.inholland.layers.resource;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import nl.inholland.layers.model.Actor;
import nl.inholland.layers.model.ActorView;
import nl.inholland.layers.presentation.model.ActorPresenter;
import nl.inholland.layers.service.ActorService;

/**
 *
 * @author CTiel
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
    public List<ActorView> getAll(@DefaultValue("") @QueryParam("lastName") String lastName, 
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
    //post actor
    @POST
    public void create(List<Actor> lstActors)
    {
        if (lstActors.size() == 1)
            actorService.createActor(lstActors.get(0));
        else
            actorService.createMany(lstActors);
    }
        
      @PUT
     @Path("/{id}")
    public void update(@PathParam("id") String actorIds, Actor actor) //@PathParam
    {
        String[] ids = actorIds.split(",");
        
         if (ids.length == 1)
             actorService.update(ids[0], actor);
         else
            actorService.updateMany(ids, actor);
    }  
    //get by Id
    @GET
    @Path("/{actorId}")
    public ActorView get( @PathParam("actorId") String actorId){
        Actor actor = actorService.get(actorId);
        
        return actorPresenter.present(actor);
    }

     @DELETE
    public void delete(@DefaultValue("") @QueryParam("id") String directorIds) 
    {     
        String[] ids = directorIds.split(",");
        
        if (ids.length == 1)
            actorService.deleteActor(ids[0]);
        else
            actorService.deleteManyActors(ids);
    }
}
