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
import nl.inholland.layers.model.Movie;
import nl.inholland.layers.persistence.MovieDAO;
import org.bson.types.ObjectId;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

public class MovieService extends BaseService
{

    private final MovieDAO movieDAO;
    
    @Inject
    public MovieService(MovieDAO movieDAO){
        this.movieDAO = movieDAO;
    }

    public Movie get(String movieId)
    {
        Movie movie = movieDAO.get(movieId);
        return movie;
    }
    
    public List<Movie> getAll()
    {
        List<Movie> movies = movieDAO.getAll();
        return movies;
    }
    
    
    public void update(String movieId, Movie movie){
        if(ObjectId.isValid(movieId))
        {
            ObjectId objectId = new ObjectId(movieId);
            Query query = movieDAO.createQuery().field("_id").equal(objectId);
            UpdateOperations<Movie> update = movieDAO.createUpdateOperations();
            movieDAO.update(query, update);
        }
    }
    
    public void updateMany(String[] movieIds, Movie movie)
    {
        for (int i = 0; i < movieIds.length; i++)
        {
            if (ObjectId.isValid(movieIds[i]))
            {
                ObjectId objectId = new ObjectId(movieIds[i]);
                
                Query query = movieDAO.createQuery().field("_id").equal(objectId);
                UpdateOperations<Movie> update = movieDAO.createUpdateOperations();
                movieDAO.update(query, update);
            }
        }
    }
    
    public void delete(String movieId)
    {
        ObjectId objectId;
        if (ObjectId.isValid(movieId))
        {
            objectId = new ObjectId(movieId);
            movieDAO.deleteById(objectId);
        }
    }

    public void deleteMany(String[] ids)
    {
        List<ObjectId> lstObjectIds = new ArrayList<>();
        
        for (int i = 0; i < ids.length; i++)
        {
            if (ObjectId.isValid(ids[i]))
            {
                ObjectId objectId = new ObjectId(ids[i]);
                lstObjectIds.add(objectId);
            }
        }
        
        movieDAO.deleteManyById(lstObjectIds);
    }
    
}
