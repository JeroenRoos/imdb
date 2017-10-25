/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.inholland.layers.service;

import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import nl.inholland.layers.model.Director;
import nl.inholland.layers.persistence.DirectorDAO;

/**
 *
 * @author Jeroen
 */
public class DirectorService extends BaseService
{
    private final DirectorDAO directorDAO;
    private ResultService resultService = new ResultService();
    
    @Inject
    public DirectorService(DirectorDAO directorDAO)
    {
        this.directorDAO = directorDAO;
    }
    
    @GET
    @Path("/{_id}")
    public Director get(@PathParam("_id") String directorId)
    {
        //TODO
        return null;
    }
    
    @GET
    public List<Director> getAll()
    {
        //TODO
        return null;
    }
    
    @POST
    public void create(Director director)
    {
        directorDAO.create(director);
    }
}
