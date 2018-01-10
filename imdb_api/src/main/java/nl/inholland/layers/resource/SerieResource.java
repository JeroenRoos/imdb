/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.inholland.layers.resource;

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
    
    @GET
    @RolesAllowed( {"ADMIN", "USER"} ) 
    public List<SerieView> getAll(@DefaultValue("") @QueryParam("directorLastName") String directorLastName,
            @DefaultValue("") @QueryParam("genreName") String genreName, @DefaultValue("") 
                    @QueryParam("actorFirstName") String actorFirstName)
    {
        List<Serie> lstSeries;
        if (!"".equals(directorLastName))
            lstSeries = serieService.getSeriesByDirectorLastName(directorLastName);
        else if (!"".equals(genreName))
            lstSeries = serieService.getSeriesByGenreName(genreName);
        else if (!"".equals(actorFirstName))
            lstSeries = serieService.getSeriesByActorFirstName(actorFirstName);
        else
            lstSeries = serieService.getAll();
        
        return seriePresenter.present(lstSeries);
    }
    
    @GET
    @RolesAllowed( {"ADMIN", "USER"} ) 
    @Path("/{SerieId}")
    public SerieView get(@PathParam("SerieId") String serieId)
    {
        Serie serie = serieService.getById(serieId);
        return seriePresenter.present(serie);
    }
    
    @POST
    @RolesAllowed("ADMIN")
    public void create(List<Serie> lstSeries)
    {
        serieService.create(lstSeries);
    }
    
    @PUT
    @RolesAllowed("ADMIN")
    @Path("/{SerieId}")
    public void update(@PathParam("SerieId") String serieId, Serie serie)
    {
        serieService.update(serieId, serie);
    }
    
    @DELETE
    @RolesAllowed("ADMIN")
    @Path("/{SerieId}")
    public void delete(@PathParam("SerieId") String serieId)
    {
        serieService.delete(serieId);
    }
}
