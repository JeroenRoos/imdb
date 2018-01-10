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


// The resource class for Directors
// This class handles the application logic and turns values to objects to be presented later
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
    
    // Get all directors (or get all directors based on Query Parameters) and sends it to the presenter
    @GET
    @RolesAllowed( {"ADMIN", "USER"} )  
    @ApiOperation("Gets all Directors (or get all directors based on Query Parameters)")
    public List<DirectorView> getAll(@DefaultValue("") @QueryParam("lastName") String lastName,
            @DefaultValue("") @QueryParam("age") String age)
    {
        List<Director> lstDirectors = null;
        lstDirectors = 
            (!"".equals(lastName)) ? directorService.getByLastName(lastName) :
            (!"".equals(age)) ? directorService.getByAge(age):        
            directorService.getAll();
        
        return directorPresenter.present(lstDirectors);
    }
    
    // Get one presenter and send it to the presenter
    @GET
    @RolesAllowed( {"ADMIN", "USER"} )  
    @ApiOperation("Gets one Director")
    @Path("/{DirectorId}")
    public DirectorView get (@PathParam("DirectorId") String directorId)
    {
        Director director = directorService.getDirectorById(directorId);
        return directorPresenter.present(director);
    }
    
    
    // Create one or multiple directors
    @POST
    @RolesAllowed("ADMIN")
    @ApiOperation("Create a Director")
    public void create(List<Director> lstDirectors)
    {
          directorService.create(lstDirectors);
    }
    
    
    // Update on ore multiple directors
    @PUT
    @RolesAllowed("ADMIN")
    @ApiOperation("Update a Director")
    public void update(@QueryParam("id") String directorIds, Director director)
    {      
        directorService.update(directorIds, director);
    }
    
    
    // Delete one or multiple directors
    @DELETE
    @RolesAllowed("ADMIN")
    @ApiOperation("Delete a Director")
    public void delete(@QueryParam("id") String directorIds) 
    {  
        directorService.delete(directorIds);
    }
}
