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
import nl.inholland.layers.model.Serie;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

/**
 *
 * @author Jeroen
 */


// The DAO class for Series
// This class is used to perform a queries and return the result to the service
// The basic requests are handled by BaseDAO, only serie specific request will be handled by this DAO
public class SerieDAO extends BaseDAO<Serie>
{
    private Datastore ds;
    
    @Inject
    public SerieDAO(Datastore ds)
    {
        super(Serie.class, ds);
        this.ds = ds;
    }   
    
    
    // Get and return all series with a specific director/directors
    public List<Serie> getByDirector(List<Director> lstDirectors)
    {
       Query<Serie> query = ds.createQuery(Serie.class);      
       query.filter("director. in", lstDirectors);
       return query.asList();
   }
     
     
    // Get and return all series with a specific genre
    public List<Serie> getByGenre(Genre genre)
    {
       Query<Serie> query = ds.createQuery(Serie.class);      
       query.filter("genre ==", genre);     
       return query.asList();
    }
     
    
    // Get and return all series with a specific actor/actors
    public List<Serie> getByActor(List<Actor> lstActors)
    {
       Query<Serie> query = ds.createQuery(Serie.class);      
       query.filter("actors in", lstActors);
       List<Serie> lstSeries = query.asList();
       return lstSeries;
    }
       
    
    // Get and return all series with specific genre and range of years
    public List<Serie> getByBetweenYearAndGenre(int year01, 
                                                int year02, 
                                                Genre genre)
    {
        return createQuery()
                .field("year").greaterThanOrEq(year01)
                .field("year").lessThanOrEq(year02)
                .filter("genre ==", genre)
                .asList();
    }
    
}
