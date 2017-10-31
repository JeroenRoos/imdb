/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.inholland.layers.resource;

import java.util.ArrayList;
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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import nl.inholland.layers.model.Director;
import nl.inholland.layers.model.DirectorView;
import nl.inholland.layers.presentation.model.DirectorPresenter;
import nl.inholland.layers.service.DirectorService;
import nl.inholland.layers.service.ResultService;
import org.bson.types.ObjectId;

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
    private final ResultService resultService = new ResultService();
    private final String HEADER_KEY = System.getenv("HEADER_KEY");
    private final String HEADER_VALUE = System.getenv("HEADER_VALUE");
    
    @Inject
    public DirectorResource(DirectorService directorService, 
            DirectorPresenter directorPresenter)
    {
        this.directorService = directorService;
        this.directorPresenter = directorPresenter;
    }
    
    @GET
    public List<DirectorView> getAll(@DefaultValue("") @QueryParam("lastName") String lastName,
            @DefaultValue("") @QueryParam("age") String age)
    {
        List<Director> lstDirectors;
        if (!"".equals(lastName))
            lstDirectors = directorService.getByName(lastName);
        
        else if (!"".equals(age))
            lstDirectors = directorService.getByAge(age);
        
        else
            lstDirectors = directorService.getAll();
        
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
    public void create(List<Director> lstDirectors)
    {
        if (lstDirectors.size() == 1)
            directorService.create(lstDirectors.get(0));
        else
            directorService.createMany(lstDirectors);
    }
    
    @PUT
    public void update(@QueryParam("id") String directorIds, Director director,
            @Context HttpHeaders headers)
    {
        List<String> headerParams = null;
        if(headers.getRequestHeader(HEADER_KEY) != null)
        {          
            headerParams = headers.getRequestHeader(HEADER_KEY);
        }
        
        if(headerParams != null && headerParams.contains(HEADER_VALUE)){
        String[] ids = directorIds.split(",");
        
         if (ids.length == 1)
             directorService.update(ids[0], director);
         else
            directorService.updateMany(ids, director);
         
        }
        resultService.notAuthorizedException("Invalid headers");
        
        
    }
    
    
    @DELETE
    public void delete(@QueryParam("id") String directorIds) 
    {     
        String[] ids = directorIds.split(",");
        
        if (ids.length == 1)
            directorService.delete(ids[0]);
        else
            directorService.deleteMany(ids);
    }
}
