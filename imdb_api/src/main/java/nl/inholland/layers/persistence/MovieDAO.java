/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.inholland.layers.persistence;

import javax.inject.Inject;
import nl.inholland.layers.model.Movie;
import org.mongodb.morphia.Datastore;

/**
 *
 * @author youp
 */
public class MovieDAO extends BaseDAO<Movie>
{
    @Inject
    public MovieDAO(Datastore ds)
    {
        super(Movie.class, ds);
    }
    
}
