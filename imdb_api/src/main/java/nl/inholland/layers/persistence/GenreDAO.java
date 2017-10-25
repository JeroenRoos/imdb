/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.inholland.layers.persistence;

import javax.inject.Inject;
import nl.inholland.layers.model.Genre;
import org.mongodb.morphia.Datastore;


public class GenreDAO extends BaseDAO<Genre> {
    
    @Inject
    public GenreDAO(Datastore ds)
    {
        super(Genre.class, ds);
    }
    
}
