/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.inholland.layers.service;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import nl.inholland.layers.model.Actor;
import nl.inholland.layers.model.Director;
import nl.inholland.layers.model.Genre;
import nl.inholland.layers.model.Serie;
import nl.inholland.layers.persistence.ActorDAO;
import nl.inholland.layers.persistence.DirectorDAO;
import nl.inholland.layers.persistence.GenreDAO;
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
    private final DirectorDAO directorDAO;
    private final GenreDAO genreDAO;
    private final ActorDAO actorDAO;

    private ResultService resultService = new ResultService();
    
    @Inject
    public SerieService(SerieDAO serieDAO, DirectorDAO directorDAO, GenreDAO genreDAO, ActorDAO actorDAO)
    {
        this.serieDAO = serieDAO;
        this.directorDAO = directorDAO;
        this.genreDAO = genreDAO;
        this.actorDAO = actorDAO;
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
        List<Serie> lstSeries = serieDAO.getAll();
        
        if (lstSeries.isEmpty())
            resultService.requireResult(lstSeries, "Serie not found");
        
        return lstSeries;
    }
    
    public List<Serie> getSeriesByDirectorLastName(String directorLastName)
    {
        List<Director> lstDirectors = directorDAO.getByLastName(directorLastName);
        
        if (lstDirectors.isEmpty())
            resultService.requireResult(null, "No series found with name: " + directorLastName);
    
        return serieDAO.getByDirector(lstDirectors);
    }
    
    public List<Serie> getSeriesByGenreName(String genreName)
    {
        Genre genre = genreDAO.getByName(genreName);
        resultService.requireResult(genre, "No series found with name: " + genreName);
        return serieDAO.getByGenre(genre);
    }
    
    public List<Serie> getSeriesByActorFirstName(String actorName)
    {
        List<Actor> lstActors = actorDAO.getByFirstName(actorName);
        
        if (lstActors.isEmpty())
            resultService.requireResult(null, "No actors found with name: " + actorName);
    
        return serieDAO.getByActor(lstActors);
    }
    
    public void create(Serie serie)
    {         
        checkCreateValidity(serie);
        //checkDuplicate(serie);
        serieDAO.create(serie);
    }
    
    public void createMany(List<Serie> lstSeries)
    {         
        for (Serie serie : lstSeries)
        {
            checkCreateValidity(serie);
            //checkDuplicate(serie);
        }
        
        serieDAO.createMany(lstSeries);
    }
    
    private void checkCreateValidity(Serie serie)
    {
            if (serie.getTitle() == null || "".equals(serie.getTitle()))
                resultService.emptyField("Title cannot be an empty string.");
            
            if (serie.getSummary() == null || "".equals(serie.getSummary()))
                resultService.emptyField("Summary cannot be an empty string.");
            
            if (serie.getYear() <= 1878)
                resultService.emptyField("The year of the serie cannot be lower than 1878. The first piece of movie ever made comes from this year.");
            
            if (serie.getGenre().isEmpty())
                resultService.emptyField("The serie must have a genre.");
            
            if (serie.getActors().isEmpty())
                resultService.emptyField("The serie must have an actor.");    
            
            if (serie.getDirectors().isEmpty())
                resultService.emptyField("The serie must have an director.");
    }
            
    public void update(String serieId, Serie serie)
    {
        ObjectId objectId;
        if(ObjectId.isValid(serieId))
        {
            checkIfSerieExists(serieId);
            objectId = new ObjectId(serieId);
            Query query = serieDAO.createQuery().field("_id").equal(objectId);
            UpdateOperations<Serie> update = serieDAO.createUpdateOperations();
            
            checkUpdateValidity(update, serie, objectId);        
            serieDAO.update(query, update);
        }
        else
            resultService.noValidObjectId("The serie id is not valid");
    }
    
    private void checkUpdateValidity(UpdateOperations<Serie> update, Serie serie, ObjectId id)
    {
        if (serie.getTitle() != null && !"".equals(serie.getTitle()))
                update.set("title", serie.getTitle());
            else if ("".equals(serie.getTitle()))
                resultService.emptyField("Title cannot be an empty string.");
            
            if (serie.getSummary() != null && !"".equals(serie.getSummary()))
                update.set("summary", serie.getSummary());
            else if ("".equals(serie.getSummary()))
                resultService.emptyField("Summary cannot be an empty string.");
            
            if (serie.getYear() >= 1878)
                update.set("year", serie.getYear());
            else if (serie.getYear() == 0)
            {
                Serie s = serieDAO.get(id);
                serie.setYear(s.getYear()); 
            }
            else 
                resultService.emptyField("The year of the serie cannot be lower than 1878. The first movie ever made comes from this year.");
            
            if (serie.getGenre() != null || !serie.getGenre().isEmpty())
                update.set("genre", serie.getGenre());
            else 
                resultService.emptyField("The serie must have a genre.");
            
            if (serie.getActors() != null || !serie.getActors().isEmpty())
                update.set("actors", serie.getActors());
            else
                resultService.emptyField("The serie must have an actor.");    
            
            if (serie.getDirectors() != null || !serie.getDirectors().isEmpty())
                update.set("director", serie.getDirectors());
            else 
                resultService.emptyField("The serie must have an director.");
    }
      
    public void delete(String serieId)
    {
        ObjectId objectId;
        if(ObjectId.isValid(serieId))
        {
            checkIfSerieExists(serieId);
            objectId = new ObjectId(serieId);
            serieDAO.deleteById(objectId);
        }
        else
            resultService.noValidObjectId("The Serie you're trying to delete doesn't exist.");
    }
    
    private void checkIfSerieExists(String serieId)
    {
        Serie serie = serieDAO.get(serieId);
        resultService.requireResult(serie, "The Serie you're trying to edit doesn't exist");
    }
}
