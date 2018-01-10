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
    
    @Inject
    public SerieService(SerieDAO serieDAO, DirectorDAO directorDAO, GenreDAO genreDAO, ActorDAO actorDAO)
    {
        this.serieDAO = serieDAO;
        this.directorDAO = directorDAO;
        this.genreDAO = genreDAO;
        this.actorDAO = actorDAO;
    }

    public Serie getById(String serieId)
    {
        // Game of Thrones  : 59e5d711df5ac0534fbe1670
        // Mr. Robot        : 59e5d463df5ac0534fbde725
        // South park       : 59ef4baf6384632a4047d441
        
        Serie serie = serieDAO.get(serieId);
        super.requireResult(serie, "Serie not found");
        return serie;
    }
    
    public List<Serie> getAll()
    {
        List<Serie> lstSeries = serieDAO.getAll();
        
        if (lstSeries.isEmpty())
           super.requireResult(lstSeries, "Serie not found");
        
        return lstSeries;
    }
    
    public List<Serie> getSeriesByDirectorLastName(String directorLastName)
    {
        List<Director> lstDirectors = directorDAO.getByLastName(directorLastName);
        
        if (lstDirectors.isEmpty())
            super.requireResult(null, "No series found with name: " + directorLastName);
    
        return serieDAO.getByDirector(lstDirectors);
    }
    
    public List<Serie> getSeriesByGenreName(String genreName)
    {
        Genre genre = genreDAO.getByName(genreName);
        super.requireResult(genre, "No series found with name: " + genreName);
        return serieDAO.getByGenre(genre);
    }
    
    public List<Serie> getSeriesByActorFirstName(String actorName)
    {
        List<Actor> lstActors = actorDAO.getByFirstName(actorName);
        
        if (lstActors.isEmpty())
            super.requireResult(null, "No actors found with name: " + actorName);
    
        return serieDAO.getByActor(lstActors);
    }
    
    public void create(List<Serie> lstSeries)
    {
        if (lstSeries.size() == 1)
            createOne(lstSeries.get(0));
        else
            createMany(lstSeries);
    }
    
    private void createOne(Serie serie)
    {         
        checkCreateValidity(serie);
        //checkDuplicate(serie);
        serieDAO.create(serie);
    }
    
    private void createMany(List<Serie> lstSeries)
    {         
        for (Serie serie : lstSeries)
        {
            checkCreateValidity(serie);
            //checkDuplicate(serie);
        }
        
        // Create all Series after each Serie is checked for validity
        serieDAO.createMany(lstSeries);
    }
    
    private void checkCreateValidity(Serie serie)
    {
            if (serie.getTitle() == null || "".equals(serie.getTitle()))
                super.emptyField("Title cannot be an empty string.");
            
            if (serie.getSummary() == null || "".equals(serie.getSummary()))
                super.emptyField("Summary cannot be an empty string.");
            
            if (serie.getYear() <= 1878)
                super.emptyField("The year of the serie cannot be lower than 1878. The first piece of movie ever made comes from this year.");
            
            if (serie.getGenre().isEmpty())
                super.emptyField("The serie must have a genre.");
            
            if (serie.getActors().isEmpty())
                super.emptyField("The serie must have an actor.");    
            
            if (serie.getDirectors().isEmpty())
                super.emptyField("The serie must have an director.");
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
            super.noValidObjectId("The serie id is not valid");
    }
    
    private void checkUpdateValidity(UpdateOperations<Serie> update, Serie serie, ObjectId id)
    {
        if (serie.getTitle() != null && !"".equals(serie.getTitle()))
                update.set("title", serie.getTitle());
            else if ("".equals(serie.getTitle()))
                super.emptyField("Title cannot be an empty string.");
            
            if (serie.getSummary() != null && !"".equals(serie.getSummary()))
                update.set("summary", serie.getSummary());
            else if ("".equals(serie.getSummary()))
                super.emptyField("Summary cannot be an empty string.");
            
            if (serie.getYear() >= 1878)
                update.set("year", serie.getYear());
            else if (serie.getYear() == 0)
            {
                Serie s = serieDAO.get(id);
                serie.setYear(s.getYear()); 
            }
            else 
                super.emptyField("The year of the serie cannot be lower than 1878. The first movie ever made comes from this year.");
            
            if (serie.getGenre() != null || !serie.getGenre().isEmpty())
                update.set("genre", serie.getGenre());
            else 
                super.emptyField("The serie must have a genre.");
            
            if (serie.getActors() != null || !serie.getActors().isEmpty())
                update.set("actors", serie.getActors());
            else
                super.emptyField("The serie must have an actor.");    
            
            if (serie.getDirectors() != null || !serie.getDirectors().isEmpty())
                update.set("director", serie.getDirectors());
            else 
                super.emptyField("The serie must have an director.");
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
            super.noValidObjectId("The Serie you're trying to delete doesn't exist.");
    }
    
    private void checkIfSerieExists(String serieId)
    {
        Serie serie = serieDAO.get(serieId);
        super.requireResult(serie, "The Serie you're trying to edit doesn't exist");
    }
}
