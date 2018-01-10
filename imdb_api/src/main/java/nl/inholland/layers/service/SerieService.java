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


// The service class of the Series
// This class handles the business logic as well as the validation (and exceptions if needed)
// The results are returned to the resource
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

    
    // Get an existing serie by ID
    public Serie getById(String serieId)
    {
        Serie serie = serieDAO.get(serieId);
        
        // Validation, check if the serie exists
        super.requireResult(serie, "Serie not found");
        
        return serie;
    }
    
    
    // Get all existing series
    public List<Serie> getAll()
    {
        List<Serie> lstSeries = serieDAO.getAll();
        
        // Validation, check if any series exist 
        if (lstSeries.isEmpty())
           super.requireResult(null, "Serie not found");
        
        return lstSeries;
    }
    
    
    // Get all series with a specific Director first name
    public List<Serie> getSeriesByDirectorLastName(String directorLastName)
    {
        List<Director> lstDirectors = directorDAO.getByLastName(directorLastName);
        
        // Validation, check if any directors exist with the lastname
        if (lstDirectors.isEmpty())
            super.requireResult(null, "No series found with name: " + directorLastName);
    
        return serieDAO.getByDirector(lstDirectors);
    }
    
    
    // Get all series with a specific genre
    public List<Serie> getSeriesByGenreName(String genreName)
    {
        Genre genre = genreDAO.getByName(genreName);
        
        // Validation, check if any genres exist with the genre name
        super.requireResult(genre, "No series found with name: " + genreName);
        
        return serieDAO.getByGenre(genre);
    }
    
    
    // Get all series with a specific Actor first name
    public List<Serie> getSeriesByActorFirstName(String actorName)
    {
        List<Actor> lstActors = actorDAO.getByFirstName(actorName);
        
        // Validation, check if any actors exist with the firstname
        if (lstActors.isEmpty())
            super.requireResult(null, "No actors found with name: " + actorName);
    
        return serieDAO.getByActor(lstActors);
    }
    
    
    // Create one or multiple series
    public void create(List<Serie> lstSeries)
    {
        // Create on or more series based on the lenght of the list
        if (lstSeries.size() == 1)
            createOne(lstSeries.get(0));
        else
            createMany(lstSeries);
    }
    
    
    // Create one serie
    private void createOne(Serie serie)
    {         
        // Validation, check if all the required fields do exist and are not empty
        checkCreateValidity(serie);
        
        serieDAO.create(serie);
    }
    
    
    // Create multiple series
    private void createMany(List<Serie> lstSeries)
    {         
        // Validation, check if all the required fields do exist and are not empty
        for (Serie serie : lstSeries)
        {
            checkCreateValidity(serie);
        }
        
        // Create all Series after each Serie is checked for validity
        serieDAO.createMany(lstSeries);
    }
    
    
    // Validation, check if all the required fields do exist and are not empty
    private void checkCreateValidity(Serie serie)
    {
        // Check the validity of the title field
        if (serie.getTitle() == null || "".equals(serie.getTitle()))
            super.emptyField("Title cannot be an empty string.");
            
        // Check the validity of the summary field
        if (serie.getSummary() == null || "".equals(serie.getSummary()))
            super.emptyField("Summary cannot be an empty string.");
            
        // Check the validity of the year field
        if (serie.getYear() <= 1878)
            super.emptyField("The year of the serie cannot be lower than 1878. The first piece of movie ever made comes from this year.");
            
        // Check the validity of the genre field
        if (serie.getGenre().isEmpty())
            super.emptyField("The serie must have a genre.");
            
        // Check the validity of the actor field
        if (serie.getActors().isEmpty())
            super.emptyField("The serie must have an actor.");    
            
        // Check the validity of the director field
        if (serie.getDirectors().isEmpty())
            super.emptyField("The serie must have an director.");
    }
           
    
    // Update one serie
    public void update(String serieId, Serie serie)
    {
        ObjectId objectId;
        
        // Validation, check if the ID is valid
        if(ObjectId.isValid(serieId))
        {
            // Validation, Check if the serie exists 
            checkIfSerieExists(serieId);
            
            objectId = new ObjectId(serieId);
            Query query = serieDAO.createQuery().field("_id").equal(objectId);
            UpdateOperations<Serie> update = serieDAO.createUpdateOperations();
            
            // Validation, check if all the field(s) that are gonne be updated are not empty
            checkUpdateValidity(update, serie, objectId);   
            
            serieDAO.update(query, update);
        }
        else
            super.noValidObjectId("The serie id is not valid");
    }
    
    
    // Validation, check if all the field(s) that are gonne be updated are not empty
    private void checkUpdateValidity(UpdateOperations<Serie> update, Serie serie, ObjectId id)
    {
        // Check the validity of the title field. If its not empty, add it to the update Operation
        // If it's does exists but is empty, throw an exception
        if (serie.getTitle() != null && !"".equals(serie.getTitle()))
                update.set("title", serie.getTitle());
        else if ("".equals(serie.getTitle()))
            super.emptyField("Title cannot be an empty string.");
            
        // Check the validity of the summary field. If its not empty, add it to the update Operation
        // If it's does exists but is empty, throw an exception
        if (serie.getSummary() != null && !"".equals(serie.getSummary()))
            update.set("summary", serie.getSummary());
        else if ("".equals(serie.getSummary()))
            super.emptyField("Summary cannot be an empty string.");
            
        // Check the validity of the year field. If its not empty, add it to the update Operation
        // If it's does exists but is empty, add the year of the serie that's gonne be updated. 
        // This is needed because the year field is automatically put to 0 if it doesn't need to be updated
        if (serie.getYear() >= 1878)
            update.set("year", serie.getYear());
        else if (serie.getYear() == 0)
        {
            Serie s = serieDAO.get(id);
            serie.setYear(s.getYear()); 
        }
        else 
            super.emptyField("The year of the serie cannot be lower than 1878. The first movie ever made comes from this year.");
            
        // Check the genre of the title field. If its not empty, add it to the update Operation
        // If it's does exists but is empty, throw an exception
        if (serie.getGenre() != null || !serie.getGenre().isEmpty())
            update.set("genre", serie.getGenre());
        else 
            super.emptyField("The serie must have a genre.");
            
        // Check the validity of the actor field. If its not empty, add it to the update Operation
        // If it's does exists but is empty, throw an exception
        if (serie.getActors() != null || !serie.getActors().isEmpty())
            update.set("actors", serie.getActors());
        else
            super.emptyField("The serie must have an actor.");    
            
        // Check the validity of the director field. If its not empty, add it to the update Operation
        // If it's does exists but is empty, throw an exception
        if (serie.getDirectors() != null || !serie.getDirectors().isEmpty())
            update.set("director", serie.getDirectors());
        else 
            super.emptyField("The serie must have an director.");
    }
      
    
    // Delete one director by ID
    public void delete(String serieId)
    {
        ObjectId objectId;
        
        // Validation, check if the ID is valid
        if(ObjectId.isValid(serieId))
        {
            // Validation, Check if the director exists 
            checkIfSerieExists(serieId);
            
            objectId = new ObjectId(serieId);
            serieDAO.deleteById(objectId);
        }
        else
            super.noValidObjectId("The Serie you're trying to delete doesn't exist.");
    }
    
    
    // Validation, checks if a director exists by ID
    private void checkIfSerieExists(String serieId)
    {
        Serie serie = serieDAO.get(serieId);
        super.requireResult(serie, "The Serie you're trying to edit doesn't exist");
    }
}
