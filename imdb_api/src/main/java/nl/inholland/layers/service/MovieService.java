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
import nl.inholland.layers.model.Movie;
import nl.inholland.layers.persistence.MovieDAO;

public class MovieService extends BaseService
{

    private final MovieDAO movieDAO;
    
    @Inject
    public MovieService(MovieDAO movieDAO){
        this.movieDAO = movieDAO;
    }
    
    @GET
    @Path("{movieId}")
    public Movie get(@PathParam("MovieId")String movieId)
    {
        return null;
    }

    @GET
    public List<Movie> getAll()
    {
        List<Movie> movies = movieDAO.getAll();
        return movies;
    }
    
}
