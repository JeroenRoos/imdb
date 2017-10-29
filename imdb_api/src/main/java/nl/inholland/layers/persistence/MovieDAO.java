/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.inholland.layers.persistence;

import java.util.List;
import javax.inject.Inject;
import nl.inholland.layers.model.Actor;
import nl.inholland.layers.model.Director;
import nl.inholland.layers.model.Genre;
import nl.inholland.layers.model.Movie;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

/**
 *
 * @author youp
 */
public class MovieDAO extends BaseDAO<Movie>
{
    private Datastore ds;
    @Inject
    public MovieDAO(Datastore ds)
    {
        super(Movie.class, ds);
        this.ds = ds;
    }
    
    public List<Movie> getByActor(List<Actor> actors){
        Query<Movie> query = ds.createQuery(Movie.class);
        
        query.filter("actors in", actors);
        return query.asList();
    }
    
    public List<Movie> getByGenre(Genre genre){
        Query<Movie> query = ds.createQuery(Movie.class);
        
        query.filter("genre ==", genre);
        return query.asList();
    }
    
    public List<Movie> getByYear(int year){
        Query<Movie> query = ds.createQuery(Movie.class);
        
        query.filter("year ==", year);
        return query.asList();
    }
 
        public List<Movie> getByDirector(List<Director> directors){
        Query<Movie> query = ds.createQuery(Movie.class);
        
        query.filter("director in", directors);
        List<Movie> movies = query.asList();
        return movies;
    }
    public void deleteManyById( List<ObjectId> objects){
        Query<Movie> query = ds.createQuery(Movie.class);
        
        ds.delete(query.filter("_id in", objects));
    }
    
}
