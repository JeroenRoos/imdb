/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.inholland.layers.resource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import nl.inholland.layers.model.Movie;
import nl.inholland.layers.model.MovieView;
import nl.inholland.layers.presentation.model.MoviePresenter;
import nl.inholland.layers.service.MovieService;
import org.bson.types.ObjectId;

@Path("/movies")
@Consumes (MediaType.APPLICATION_JSON)
@Produces (MediaType.APPLICATION_JSON)
public class MovieResource extends BaseResource
{
    private final MovieService movieService;
    private final MoviePresenter moviePresenter;
    
    @Inject
    public MovieResource( MovieService movieService,
            MoviePresenter moviePresenter){
        this.movieService = movieService;
        this.moviePresenter = moviePresenter;
    }
    
    @GET
    public List<MovieView> getAll(@DefaultValue("") @QueryParam("actorLastName") String actorName,
            @DefaultValue("") @QueryParam("genre") String genre){
        List<Movie> movies;
        if(!"".equals(actorName)){
            
            movies = movieService.getMoviesForActorName(actorName);            
        }else if(!"".equals(genre)){
            movies = movieService.getMoviesForGenre(genre);
        }
        else{
            
            movies = movieService.getAll();
        }
        
        return moviePresenter.present(movies);

    }
    
    @GET
    @Path("/{MovieId}")
    public MovieView get( @PathParam("MovieId") String movieId){
        Movie Movie = movieService.get(movieId);
        
        return moviePresenter.present(Movie);
    }
    @PUT
    @Path("/{movieId}")
    public void update(@PathParam("movieId") String movieId, Movie movie){
        
        movieService.update(movieId, movie);
    }
    
    @DELETE
    public void delete(@QueryParam("id") String movieIds) //@PathParam
    {     
        String[] ids = movieIds.split(",");
        
        if (ids.length == 1)
            movieService.delete(ids[0]);
        else
            movieService.deleteMany(ids);
    }
    
    @POST
    public void create(Movie Movie){
        
    }
}
