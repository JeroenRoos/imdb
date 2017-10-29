/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.inholland.layers.persistence;

import java.util.List;
import javax.inject.Inject;
import nl.inholland.layers.model.Genre;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;


public class GenreDAO extends BaseDAO<Genre> {
    
    @Inject
    public GenreDAO(Datastore ds)
    {
        super(Genre.class, ds);
    }
    
    public Genre getByName(String genreName){
        return createQuery().field("name").equal(genreName).get();
    }
    
    public void updateById(ObjectId id,  Genre genre){
        Query query = createQuery().field("_id").equal(id);
        UpdateOperations<Genre> ops = createUpdateOperations().set("name", genre.getName());
        update(query, ops);
    }

    public void updateMany(List<ObjectId> objectIds, Genre genre)
    {
        Query query = createQuery().filter("_id in", objectIds);
        UpdateOperations<Genre> ops = createUpdateOperations().set("name", genre.getName());
        update(query, ops);
    }
}
