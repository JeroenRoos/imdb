/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.inholland.layers.resource;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.annotation.security.RolesAllowed;
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
import nl.inholland.layers.model.Director;
import nl.inholland.layers.model.DirectorView;
import nl.inholland.layers.presentation.model.DirectorPresenter;
import nl.inholland.layers.service.DirectorService;

/**
 *
 * @author Jeroen
 */

@Api("Directors")
@Path("/directors")
@Consumes (MediaType.APPLICATION_JSON)
@Produces (MediaType.APPLICATION_JSON)
public class DirectorResource extends BaseResource
{
    private final DirectorService directorService;
    private final DirectorPresenter directorPresenter;
    
    @Inject
    public DirectorResource(DirectorService directorService, 
            DirectorPresenter directorPresenter)
    {
        this.directorService = directorService;
        this.directorPresenter = directorPresenter;
    }
    
    @GET
    @RolesAllowed( {"ADMIN", "USER"} )  
    @ApiOperation("Gets all Directors")
    public List<DirectorView> getAll(@DefaultValue("") @QueryParam("lastName") String lastName,
            @DefaultValue("") @QueryParam("age") String age)
    {
        List<Director> lstDirectors = null;
        lstDirectors = 
            (!"".equals(lastName)) ? directorService.getByName(lastName) :
            (!"".equals(age)) ? directorService.getByAge(age):        
            directorService.getAll();
        
        return directorPresenter.present(lstDirectors);
    }
    
    @GET
    @RolesAllowed( {"ADMIN", "USER"} )  
    @ApiOperation("Gets a Director")
    @Path("/{DirectorId}")
    public DirectorView get (@PathParam("DirectorId") String directorId)
    {
        Director director = directorService.get(directorId);
        return directorPresenter.present(director);
    }
    
    @POST
    @RolesAllowed("ADMIN")
    @ApiOperation("Create a Director")
    public void create(List<Director> lstDirectors)
    {
          directorService.create(lstDirectors);
    }
    
    @PUT
    @RolesAllowed("ADMIN")
    @ApiOperation("Update a Director")
    public void update(@QueryParam("id") String directorIds, Director director)
    {      
        directorService.update(directorIds, director);
    }
    
    
    @DELETE
    @RolesAllowed("ADMIN")
    @ApiOperation("Delete a Director")
    public void delete(@QueryParam("id") String directorIds) 
    {  
        directorService.delete(directorIds);
    }
}
