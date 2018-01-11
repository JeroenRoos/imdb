/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.inholland.layers.service;

import java.util.List;
import javax.inject.Inject;
import nl.inholland.Helpers.Range;
import nl.inholland.layers.model.Actor;
import nl.inholland.layers.model.Comment;
import nl.inholland.layers.model.Director;
import nl.inholland.layers.persistence.DirectorDAO;
import nl.inholland.layers.model.Genre;
import nl.inholland.layers.model.Movie;
import nl.inholland.layers.model.User;
import nl.inholland.layers.persistence.ActorDAO;
import nl.inholland.layers.persistence.CommentDAO;
import nl.inholland.layers.persistence.GenreDAO;
import nl.inholland.layers.persistence.MovieDAO;
import nl.inholland.layers.persistence.UserDAO;
import org.bson.types.ObjectId;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

public class MovieService extends BaseService
{

    private final MovieDAO movieDAO;
    private final ActorDAO actorDAO;
    private final DirectorDAO directorDAO;
    private final CommentDAO commentDAO;
    private final UserDAO userDAO;
    private final GenreDAO genreDAO;
    
    @Inject
    public MovieService(MovieDAO movieDAO, ActorDAO actorDAO, GenreDAO genreDAO, DirectorDAO directorDAO, UserDAO userDAO, CommentDAO commentDAO){
        this.movieDAO = movieDAO;
        this.actorDAO = actorDAO;
        this.genreDAO = genreDAO;
        this.directorDAO = directorDAO;
        this.userDAO = userDAO;
        this.commentDAO = commentDAO;
    }

    public Movie getById(String movieId)
    {
        Movie movie = (Movie) super.getById(movieId);  
        return movie;

    }
    
    public List<Movie> getAll()
    {
        List<Movie> movies = super.getAll();
        return movies;
    }   
    
    //get movie by rating between year x and year y
    public List<Movie>getByRatingAndYear(String yearMin, String yearMax, String rating)
    {        
        List<Movie> movies;
        int ratingToInt = 0; 
        Range range = super.setRange(yearMin, yearMax);
        
        try{
            
          ratingToInt = Integer.parseInt(rating);
           
        }catch(NumberFormatException e){
            
          super.errorHandler.parsingError("Rating is a number between 0 and 10");
        }
        
        movies = movieDAO.getByRatingAndYearRange(range.getMin(), range.getMax(), ratingToInt);

        
        if (movies.isEmpty())
        {                                                
           super.errorHandler.requireResult(null, "No movies found.");
        }
        
        return movies;      
    }

                      
            
    public List<Movie> getMoviesForActorName(String actorName){
        List<Actor> actors = actorDAO.getByFirstName(actorName);
        
        super.errorHandler.requireResult(actors, "No actors found with last name: " + actorName);
        
        return movieDAO.getByActor(actors);
    }
    
     public List<Movie> getMoviesForDirectorName(String directorLastName){
        List<Director> directors = directorDAO.getByLastName(directorLastName);
        
        super.errorHandler.requireResult(directors, "No directors found with last name: " + directorLastName);
        
        return movieDAO.getByDirector(directors);
    }
    
    public List<Movie> getMoviesForUserNameCommented(String userName){
        User userObject = userDAO.getSingleUserByName(userName);
        
        super.errorHandler.requireResult(userObject, "No user found with name: " + userName);
        
        List<Comment> comments = commentDAO.getByUser(userObject);
        
        super.errorHandler.requireResult(comments, "No comments found from user: " + userName);

        return movieDAO.getByComments(comments);
    }
    
    public List<Movie> getByUserNameCommentedAndTimeSpan(String userName, String timeMin, String timeMax)
    {
        User userObject =  userDAO.getSingleUserByName(userName);
        super.errorHandler.requireResult(userObject, "No users found for the provided user name");
        
        int timeMinInt = 0;
        int timeMaxInt = 0;
        
        try
        {
            timeMinInt = Integer.parseInt(timeMin);
            timeMaxInt = Integer.parseInt(timeMax);
        }
        catch (Exception ex)
        {
            super.errorHandler.parsingError("Something went wrong while converting the time to a valid integer.");
        }
        
        //If the "year from" is greater than the "year to", swap them around
        if (timeMinInt > timeMaxInt){
            int tempYear = timeMinInt;
            timeMinInt = timeMaxInt;
            timeMaxInt = tempYear;
        }
        
        List<Comment> comments = commentDAO.getByUserAndTimeSpan(userObject, timeMinInt, timeMaxInt);        
        super.errorHandler.requireResult(userObject, "No comments found for the provided user name");
        
       
        List<Movie> movies = movieDAO.getByComments(comments);
        
        super.errorHandler.requireResult(movies, "No movies found for the provided parameters");
        
        return movies;
    }
    
    
    public List<Movie> getMoviesForGenre(String genre){
        Genre genreObject = genreDAO.getByName(genre);
        
        super.errorHandler.requireResult(genreObject, "No genre found with name: + genre");
        
        return movieDAO.getByGenre(genreObject);
    }
       
    
    public List<Movie> getMoviesForYear(int year){
        List<Movie> movies = movieDAO.getByYear(year);
        
        super.errorHandler.requireResult(movies, "No movies found with year: "+ year);
        
        return movies;
    }
    
    public List<Movie> getByYearAndGenre(String genreName, String yearFromString, String yearToString, String sortKey, boolean sortDesc){

        
        List<Movie> movies = null;
        
        // Get genre by the genre name supplied
        Genre genre = genreDAO.getByName(genreName);
        super.errorHandler.requireResult(genre, "No genres found for the provided genre name");
        
        int yearFrom = 0;
        int yearTo = 0;
        
        //Validation, try to parse the year to a valid integer
        try{
            yearFrom = Integer.parseInt(yearFromString);
            yearTo = Integer.parseInt(yearToString);
        }
        catch (Exception ex){
            super.errorHandler.parsingError("Something went wrong while converting the year to a valid integer.");
        }
        
        //If the "year from" is greater than the "year to", swap them around
        if (yearFrom > yearTo){
            int tempYear = yearFrom;
            yearFrom = yearTo;
            yearTo = tempYear;
        }
        
        //If sortKey was not supplied, set it to default to "title", else: check if sortKey is a field in the Movie class
        try{  

            Movie.class.getDeclaredField(sortKey);
            
        }catch (Exception ex){
            super.errorHandler.parsingError("Invalid sorting field");
        }

        //Add "-" to sortKey to get descending sort
        if(sortDesc){
            sortKey = "-" + sortKey;
        }
        
        
        movies = movieDAO.getByYearAndGenre(genre, yearFrom, yearTo, sortKey);
        
        super.errorHandler.requireResult(movies, "No movies found for the provided parameters");
        
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
                    .set("comments", movie.getComments())
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
        super.delete(movieId);
    }

    public void deleteMany(String[] ids)
    {
        super.deleteMany(ids);
    }
    
    
}
