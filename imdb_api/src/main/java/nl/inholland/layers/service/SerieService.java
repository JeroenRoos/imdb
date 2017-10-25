/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.inholland.layers.service;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import nl.inholland.layers.model.Director;
import nl.inholland.layers.model.Serie;
import nl.inholland.layers.persistence.SerieDAO;
import org.bson.types.ObjectId;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

/**
 *
 * @author Jeroen
 */
public class SerieService extends BaseService
{
    private final SerieDAO serieDAO;
    private ResultService resultService = new ResultService();
    
    @Inject
    public SerieService(SerieDAO serieDAO)
    {
        this.serieDAO = serieDAO;
    }

    public Serie get (String serieId)
    {
        // Game of Thrones  : 59e5d711df5ac0534fbe1670
        // Mr. Robot        : 59e5d463df5ac0534fbde725
        // South park       : 59ef4baf6384632a4047d441
        
        Serie serie = serieDAO.get(serieId);
        resultService.requireResult(serie, "Serie not found");
        return serie;
    }
    
    public List<Serie> getAll()
    {
        List<Serie> lstSeries = new ArrayList<>(serieDAO.getAll());
        
        if (lstSeries.size() == 0)
            resultService.requireResult(lstSeries, "Serie not found");
        
        return lstSeries;
    }
    
    public void create(Serie serie)
    {         
        // TODO: Checks of de data in serie goed ingevuld is
        serieDAO.create(serie);
    }
    
    public void update(String serieId, Serie serie)
    {
        ObjectId objectId;
        if(ObjectId.isValid(serieId))
        {
            objectId = new ObjectId(serieId);
            Query query = serieDAO.createQuery().field("_id").equal(objectId);
            UpdateOperations<Serie> update = serieDAO.createUpdateOperations();
            
            // TODO: Checks of de data in serie goed ingevuld is
            
            serieDAO.update(query, update);
        }
    }
      
    public void delete(String serieId)
    {
        ObjectId objectId;
        if(ObjectId.isValid(serieId))
        {
            objectId = new ObjectId(serieId);
            serieDAO.deleteById(objectId);
        }
        else
            resultService.requireResult(serieId, "The document you're trying to delete doesn't exist.");
    }
    
    
    
}
