/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.inholland.layers.resource;

import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
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
    public List<SerieView> getAll()
    {
        List<Serie> lstSeries = serieService.getAll();
        return seriePresenter.present(lstSeries);
    }
    
    @GET
    @Path("/{SerieId}")
    public Serie get(@PathParam("SerieId") String serieId)
    {
        Serie serie = serieService.get(serieId);
        return seriePresenter.present(serie);
    }
    
    @POST
    public void create(Serie serie)
    {
        
    }
}
