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
import nl.inholland.layers.persistence.DirectorDAO;
import nl.inholland.layers.model.Genre;
import nl.inholland.layers.model.Movie;
import nl.inholland.layers.persistence.ActorDAO;
import nl.inholland.layers.persistence.CommentDAO;
import nl.inholland.layers.persistence.GenreDAO;
import nl.inholland.layers.persistence.MovieDAO;
import org.bson.types.ObjectId;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

public class MovieService extends BaseService
{

    private final MovieDAO movieDAO;
    private final ActorDAO actorDAO;
    private final DirectorDAO directorDAO;
    private final CommentDAO commentDAO;

    private final ResultService resultService = new ResultService();
    private final GenreDAO genreDAO;
    
    @Inject
    public MovieService(MovieDAO movieDAO, ActorDAO actorDAO, GenreDAO genreDAO, DirectorDAO directorDAO, CommentDAO commentDAO){
        this.movieDAO = movieDAO;
        this.actorDAO = actorDAO;
        this.genreDAO = genreDAO;
        this.directorDAO = directorDAO;
        this.commentDAO = commentDAO;
               
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
    
    public List<Movie> getMoviesForActorName(String actorName){
        List<Actor> actors = actorDAO.getByFirstName(actorName);
        
        resultService.requireResult(actors, "No actors found with last name: " + actorName);
        
        return movieDAO.getByActor(actors);
    }
     public List<Movie> getMoviesForDirectorName(String directorLastName){
        List<Director> directors = directorDAO.getByLastName(directorLastName);
        
        resultService.requireResult(directors, "No directors found with last name: " + directorLastName);
        
        return movieDAO.getByDirector(directors);
    }
       
    
    public List<Movie> getMoviesForGenre(String genre){
        Genre genreObject = genreDAO.getByName(genre);
        
        resultService.requireResult(genreObject, "No genre found with name: + genre");
        
        return movieDAO.getByGenre(genreObject);
    }
       
    
    public List<Movie> getMoviesForYear(int year){
        List<Movie> movies = movieDAO.getByYear(year);
        
        resultService.requireResult(movies, "No movies found with year: "+ year);
        
        return movies;
    }
    
    
    public void update(String movieId, Movie movie){
        ObjectId objectId;
        if(ObjectId.isValid(movieId))
        {
            objectId = new ObjectId(movieId);
            Query query = movieDAO.createQuery().field("_id").equal(objectId);
            UpdateOperations<Movie> update = movieDAO.createUpdateOperations()
                    .set("title", movie.getTitle())
                    .set("summary", movie.getSummary())
                    .set("actors", movie.getActors())
                    .set("director", movie.getDirector())
                    .set("genre", movie.getGenre())
                    .set("year", movie.getYear());
            movieDAO.update(query, update);
        }
    }
    
    public void updateMany(String[] movieIds, Movie movie)
    {
        ObjectId objectId;
        for (int i = 0; i < movieIds.length; i++)
        {
            if (ObjectId.isValid(movieIds[i]))
            {
                objectId = new ObjectId(movieIds[i]);
                
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
