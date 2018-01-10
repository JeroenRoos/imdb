/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.inholland.layers.service;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import nl.inholland.layers.model.Genre;
import nl.inholland.layers.persistence.GenreDAO;
import org.bson.types.ObjectId;



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
    
    public Genre getById(String genreId)
    {
        Genre genre = (Genre) super.getById(genreId, genreDAO);
        return genre;
    }

    public List<Genre> getMany(String[] ids)
    {        
        List<Genre> genres = new ArrayList<>();
        for (int i = 0; i < ids.length; i++)
        {
            genres.add(genreDAO.get(ids[i]));
        }
        super.requireResult(genres, "genres not found");
        
        return genres;
    }
    
    
    public List<Genre> getAll()
    {
        List<Genre> genre = super.getAll(genreDAO);
        return genre;
    }
    
    public Genre getByName(String genreName){
        Genre genre;
        genre = genreDAO.getByName(genreName);
        return genre;
    }
    
    public void create(Genre genre){
        genreDAO.create(genre);
    }
    
    public void createMany(List<Genre> genres){
        genreDAO.createMany(genres);
    }
    
    public void update(String genreId, Genre genre){
        ObjectId objectId;
        if(ObjectId.isValid(genreId)){
            objectId = new ObjectId(genreId);
            genreDAO.updateById(objectId, genre);

        }else{
            super.noValidObjectId("Invalid genre id");
        }
    }
    
    public void updateMany(List<String> genreIds, Genre genre){
        List<ObjectId> objectIds = new ArrayList<>();
        for(String id : genreIds){
            if(ObjectId.isValid(id)){
                objectIds.add(new ObjectId(id));
            }
        }
        
        genreDAO.updateMany(objectIds, genre);
    }
    
    public void delete(String genreId){
        super.delete(genreId, genreDAO);
    }
}
