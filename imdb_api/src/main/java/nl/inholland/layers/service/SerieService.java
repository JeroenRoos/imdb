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
        checkCreateValidity(serie);
        serieDAO.create(serie);
    }
    
    public void createMany(List<Serie> lstSeries)
    {         
        for (Serie serie : lstSeries)
            checkCreateValidity(serie);
        
        serieDAO.createMany(lstSeries);
    }
    
    private void checkCreateValidity(Serie serie)
    {
            if (serie.getTitle() == null || serie.getTitle() == "")
                resultService.emptyField("Title cannot be an empty string.");
            
            if (serie.getSummary() == null || serie.getSummary() == "")
                resultService.emptyField("Summary cannot be an empty string.");
            
            if (serie.getYear() <= 1878)
                resultService.emptyField("The year of the movie cannot be lower than 1878. The first movie ever made comes from this year.");
            
            if (serie.getGenre().isEmpty())
                resultService.emptyField("The movie must have a genre.");
            
            if (serie.getActors().isEmpty())
                resultService.emptyField("The movie must have an actor.");    
            
            if (serie.getDirectors().isEmpty())
                resultService.emptyField("The movie must have an director.");
    }
    
    public void update(String serieId, Serie serie)
    {
        ObjectId objectId;
        if(ObjectId.isValid(serieId))
        {
            objectId = new ObjectId(serieId);
            Query query = serieDAO.createQuery().field("_id").equal(objectId);
            UpdateOperations<Serie> update = serieDAO.createUpdateOperations();
            
            checkUpdateValidity(update, serie);
         
            serieDAO.update(query, update);
        }
    }
    
    private void checkUpdateValidity(UpdateOperations<Serie> update, Serie serie)
    {
        if (serie.getTitle() != null && serie.getTitle() != "")
                update.set("title", serie.getTitle());
            else if (serie.getTitle() == "")
                resultService.emptyField("Title cannot be an empty string.");
            
            if (serie.getSummary() != null && serie.getSummary() != "")
                update.set("summary", serie.getSummary());
            else if (serie.getSummary() == "")
                resultService.emptyField("Summary cannot be an empty string.");
            
            if (serie.getYear() >= 1878)
                update.set("year", serie.getYear());
            else 
                resultService.emptyField("The year of the movie cannot be lower than 1878. The first movie ever made comes from this year.");
            
            if (!serie.getGenre().isEmpty())
                update.set("genre", serie.getGenre());
            else 
                resultService.emptyField("The movie must have a genre.");
            
            if (!serie.getActors().isEmpty())
                update.set("actors", serie.getActors());
            else
                resultService.emptyField("The movie must have an actor.");    
            
            if (!serie.getDirectors().isEmpty())
                update.set("director", serie.getDirectors());
            else 
                resultService.emptyField("The movie must have an director.");
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
