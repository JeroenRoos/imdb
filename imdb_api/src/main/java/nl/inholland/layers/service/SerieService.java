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
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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
    @Path("{SerieId}")
    public Serie get (@PathParam("SerieId") String serieId)
    {
        return null;
    }
    
    @GET
    public List<Serie> getAll()
    {
        List<Serie> lstSeries = new ArrayList<Serie>(serieDAO.getAll());
        return lstSeries;
    }
    
}
