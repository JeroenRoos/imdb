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
import nl.inholland.layers.model.Genre;
import nl.inholland.layers.persistence.GenreDAO;
import org.bson.types.ObjectId;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;


/**
 *
 * @author youp
 */
public class GenreService extends BaseService {
    
    private final GenreDAO genreDAO;
    
    @Inject
    public GenreService(GenreDAO genreDAO){
        this.genreDAO = genreDAO;
    }
    
    public Genre get(String genreId)
    {
        Genre genre = new Genre();
        genre = genreDAO.get(genreId);
        return genre;
    }

    
    public List<Genre> getAll()
    {
        List<Genre> genre = new ArrayList<Genre>(genreDAO.getAll());
        return genre;
    }
    
    public void create(List<Genre> genres){
        genreDAO.save(genres);
    }
    
//    public void createMultiple(List<Genre> genres){
//        genreDAO.createQuery();
//    }
    
    public void update(String genreId, Genre genre){
        ObjectId objectId;
        if(ObjectId.isValid(genreId)){
            objectId = new ObjectId(genreId);
            Query query = genreDAO.createQuery().field("_id").equal(objectId);
            UpdateOperations<Genre> ops = genreDAO.createUpdateOperations().set("name", genre.getName());
            genreDAO.update(query, ops);
        }
    }
    
    public void delete(String genreId){
        ObjectId objectId;
        if(ObjectId.isValid(genreId)){
            objectId = new ObjectId(genreId);
            genreDAO.deleteById(objectId);
        }
    }
}
