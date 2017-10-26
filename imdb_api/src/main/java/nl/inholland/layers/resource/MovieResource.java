/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.inholland.layers.resource;

import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import nl.inholland.layers.model.Movie;
import nl.inholland.layers.model.MovieView;
import nl.inholland.layers.presentation.model.MoviePresenter;
import nl.inholland.layers.service.MovieService;

@Path("/movies")
@Consumes (MediaType.APPLICATION_JSON)
@Produces (MediaType.APPLICATION_JSON)
public class MovieResource extends BaseResource
{
    private final MovieService MovieService;
    private final MoviePresenter MoviePresenter;
    
    @Inject
    public MovieResource( MovieService MovieService,
            MoviePresenter MoviePresenter){
        this.MovieService = MovieService;
        this.MoviePresenter = MoviePresenter;
    }
    
    @GET
    public List<MovieView> getAll(@Context HttpHeaders httpHeaders){
        
        List<String> headers = httpHeaders.getRequestHeader("authtoken");
        List<Movie> movies = MovieService.getAll();
        
        return MoviePresenter.present(movies);
    }
    
    @GET
    @Path("/{MovieId}")
    public Movie get( @PathParam("MovieId") String MovieId){
        Movie Movie = MovieService.get(MovieId);
        
        return MoviePresenter.present(Movie);
    }
    
    @POST
    public void create(Movie Movie){
        
    }
}
