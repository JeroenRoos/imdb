/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.inholland.layers.service;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import nl.inholland.layers.model.Serie;
import nl.inholland.layers.persistence.SerieDAO;

/**
 *
 * @author Jeroen
 */
public class SerieService extends BaseService
{
    private final SerieDAO serieDAO;
    
    @Inject
    public SerieService(SerieDAO serieDAO)
    {
        this.serieDAO = serieDAO;
    }
    
    @GET
    @Path("/{_id}")
    public Serie get (@PathParam("_id") String serieId)
    {
        // Game of Thrones  : 59e5d711df5ac0534fbe1670
        // Mr. Robot        : 59e5d463df5ac0534fbde725
        Serie serie = serieDAO.get(serieId);
        requireResult(serie, "Serie not found");
        return serie;
    }
    
    @GET
    public List<Serie> getAll()
    {
        List<Serie> lstSeries = new ArrayList<Serie>(serieDAO.getAll());
        return lstSeries;
    }
    
    @POST
    public void create(Serie serie)
    {
        serieDAO.create(serie);
    }
    
    
    protected void requireResult(Object obj, String message) throws NotFoundException
    {
        if (obj == null)
            throw new NotFoundException(message);
    }
    
}
