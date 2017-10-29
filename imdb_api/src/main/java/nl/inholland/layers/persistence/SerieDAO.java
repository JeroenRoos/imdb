/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.inholland.layers.persistence;

import java.util.List;
import javax.inject.Inject;
import nl.inholland.layers.model.Director;
import nl.inholland.layers.model.Genre;
import nl.inholland.layers.model.Serie;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

/**
 *
 * @author Jeroen
 */
public class SerieDAO extends BaseDAO<Serie>
{
    private Datastore ds;
    
    @Inject
    public SerieDAO(Datastore ds)
    {
        super(Serie.class, ds);
        this.ds = ds;
    }   
    
     public List<Serie> getByDirector(List<Director> lstDirectors)
     {
        Query<Serie> query = ds.createQuery(Serie.class);      
        query.filter("director. in", lstDirectors);
        return query.asList();
    }
     
     public List<Serie> getByGenre(Genre genre)
     {
        Query<Serie> query = ds.createQuery(Serie.class);      
        query.filter("genre ==", genre);
        return query.asList();
     }
}
