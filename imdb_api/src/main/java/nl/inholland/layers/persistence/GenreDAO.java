/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.inholland.layers.persistence;

import java.util.List;
import javax.inject.Inject;
import nl.inholland.layers.model.Genre;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;


public class GenreDAO extends BaseDAO<Genre> {
    
    @Inject
    public GenreDAO(Datastore ds)
    {
        super(Genre.class, ds);
    }
    
        public List<Genre> getByName(String genreName){
        return createQuery().field("name").equal(genreName).asList();
    }
}
