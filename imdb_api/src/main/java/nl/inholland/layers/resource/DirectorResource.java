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
import nl.inholland.layers.model.Director;
import nl.inholland.layers.presentation.model.DirectorPresenter;
import nl.inholland.layers.service.DirectorService;

/**
 *
 * @author Jeroen
 */

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
    public List<Director> getAll()
    {
        //TODO
        return null;
    }
    
    @GET
    @Path("{GenreId}")
    public Director get (@PathParam("DirectorId") String directorId)
    {
        //TODO
        return null;
    }
    
    @POST
    public void create(Director director)
    {
        directorService.create(director);
    }
}
