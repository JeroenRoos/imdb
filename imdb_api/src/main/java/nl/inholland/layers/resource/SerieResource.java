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
    public SerieResource(SerieService serieService, 
                         SeriePresenter seriePresenter)
    {
        this.serieService = serieService;
        this.seriePresenter = seriePresenter;
    }
    
    
    // Get all series (or get all series based on Query Parameters) and sends it to the presenter
    @GET
    @RolesAllowed( {"ADMIN", "USER"} ) 
    @ApiOperation("Gets all Series (or get all series based on Query Parameters)")
    public List<SerieView> getAll(@DefaultValue("") @QueryParam("directorLastName") String directorLastName,
                                  @DefaultValue("") @QueryParam("genreName") String genreName, 
                                  @DefaultValue("") @QueryParam("actorFirstName") String actorFirstName)
    {
        List<Serie> lstSeries = null;
        lstSeries =       
            (!"".equals(directorLastName)) ? serieService.getSeriesByDirectorLastName(directorLastName) :
            (!"".equals(genreName)) ? serieService.getSeriesByGenreName(genreName) :
            (!"".equals(actorFirstName)) ? serieService.getSeriesByActorFirstName(actorFirstName) :
            serieService.getAll();
        
        return seriePresenter.present(lstSeries);
    }
    
    
    // Get one serie by ID and send it to the presenter
    @GET
    @RolesAllowed( {"ADMIN", "USER"} ) 
    @ApiOperation("Gets one Serie by ID")
    @Path("/{SerieId}")
    public SerieView get(@PathParam("SerieId") String serieId)
    {
        Serie serie = serieService.getById(serieId);
        return seriePresenter.present(serie);
    }
    
    
    // Get and return all series with specific genre and range of years
    @GET
    @RolesAllowed( {"ADMIN", "USER"} ) 
    @ApiOperation("Gets all series between 2 years and with a specific genre.")
    @Path("/genres/{genreName}")
    public List<SerieView> getByYearAndGenre(@PathParam("genreName") String genreName,
                                             @DefaultValue("") @QueryParam("yearFrom") String fromYear, 
                                             @DefaultValue("") @QueryParam("yearTo") String toYear)
    {
        List<Serie> lstSeries = serieService.getSeriesByYearAndGenre(fromYear, toYear, genreName);
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
    public void update(@PathParam("SerieId") String serieId, 
                       Serie serie)
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
