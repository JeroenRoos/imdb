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
    
    @GET
    @Path("{genreId}")
    public Genre get(@PathParam("genreId")String genreId)
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
    
    public void create(Genre genre){
        genreDAO.create(genre);
    }
}
