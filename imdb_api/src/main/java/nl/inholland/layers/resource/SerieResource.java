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
import nl.inholland.layers.model.Serie;
import nl.inholland.layers.model.SerieView;
import nl.inholland.layers.presentation.model.SeriePresenter;
import nl.inholland.layers.service.SerieService;

/**
 *
 * @author Jeroen
 */


// The resource class for Series
// This class handles the application logic and turns values to objects to be presented later
@Api("Series")
@Path("/series")
@Consumes (MediaType.APPLICATION_JSON)
@Produces (MediaType.APPLICATION_JSON)
public class SerieResource extends BaseResource
{
    private final SerieService serieService;
    private final SeriePresenter seriePresenter;
    
    @Inject
    public SerieResource(SerieService serieService, SeriePresenter seriePresenter)
    {
        this.serieService = serieService;
        this.seriePresenter = seriePresenter;
    }
    
    
    // Get all series (or get all series based on Query Parameters) and sends it to the presenter
    @GET
    @RolesAllowed( {"ADMIN", "USER"} ) 
    @ApiOperation("Gets all Series")
    public List<SerieView> getAll(@DefaultValue("") @QueryParam("directorLastName") String directorLastName,
            @DefaultValue("") @QueryParam("genreName") String genreName, @DefaultValue("") 
            @QueryParam("actorFirstName") String actorFirstName)
    {
        List<Serie> lstSeries = null;
        lstSeries =       
            (!"".equals(directorLastName)) ? lstSeries = serieService.getSeriesByDirectorLastName(directorLastName) :
            (!"".equals(genreName)) ? serieService.getSeriesByGenreName(genreName) :
            (!"".equals(actorFirstName)) ? serieService.getSeriesByActorFirstName(actorFirstName) :
            serieService.getAll();
        
        return seriePresenter.present(lstSeries);
    }
    
    
    // Get one serie and send it to the presenter
    @GET
    @RolesAllowed( {"ADMIN", "USER"} ) 
    @ApiOperation("Gets one Serie")
    @Path("/{SerieId}")
    public SerieView get(@PathParam("SerieId") String serieId)
    {
        Serie serie = serieService.getById(serieId);
        return seriePresenter.present(serie);
    }
    
    
    // Get one serie and send it to the presenter
    @GET
    @RolesAllowed( {"ADMIN", "USER"} ) 
    @ApiOperation("Gets one Serie")
    @Path("/{year}/{genreName}")
    public List<SerieView> getByYearAndGenre(@PathParam("year") String years, @PathParam("genreName") String genreName)
    {
        List<Serie> lstSeries = serieService.getSeriesByYearAndGenre(years, genreName);
        return seriePresenter.present(lstSeries);
    }
    
    
    // Create one serie
    @POST
    @RolesAllowed("ADMIN")
    @ApiOperation("Create one Serie")
    public void create(List<Serie> lstSeries)
    {
        serieService.create(lstSeries);
    }
    
    
    // Update one serie
    @PUT
    @RolesAllowed("ADMIN")
    @ApiOperation("Update one Serie")
    @Path("/{SerieId}")
    public void update(@PathParam("SerieId") String serieId, Serie serie)
    {
        serieService.update(serieId, serie);
    }
    
    
    // Delete one serie
    @DELETE
    @RolesAllowed("ADMIN")
    @ApiOperation("Delete one Serie")
    @Path("/{SerieId}")
    public void delete(@PathParam("SerieId") String serieId)
    {
        serieService.delete(serieId);
    }
}
