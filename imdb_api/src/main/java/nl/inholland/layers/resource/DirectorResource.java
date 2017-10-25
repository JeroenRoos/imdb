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
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import nl.inholland.layers.model.Director;
import nl.inholland.layers.model.DirectorView;
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
    public List<DirectorView> getAll()
    {
        List<Director> lstDirectors = directorService.getAll();
        return directorPresenter.present(lstDirectors);
    }
    
    @GET
    @Path("/{DirectorId}")
    public DirectorView get (@PathParam("DirectorId") String directorId)
    {
        Director director = directorService.get(directorId);
        return directorPresenter.present(director);
    }
    
    @POST
    public void create(Director director)
    {
        directorService.create(director);
    }
    
    @PUT
    @Path("/{DirectorId}")
    public void update(@PathParam("DirectorId")String directorId, Director director)
    {
        directorService.update(directorId, director);
    }
    
    @DELETE
    @Path("/{DirectorId}")
    public void delete(@PathParam("DirectorId") String directorId)
    {
        directorService.delete(directorId);
    }
}
