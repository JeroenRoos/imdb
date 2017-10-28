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
    public List<MovieView> getAll(@Context HttpHeaders httpHeaders){
        
        List<String> headers = httpHeaders.getRequestHeader("authtoken");
        List<Movie> movies = movieService.getAll();
        
        return moviePresenter.present(movies);
    }
    
    @GET
    @Path("/{MovieId}")
    public MovieView get( @PathParam("MovieId") String movieId){
        Movie Movie = movieService.get(movieId);
        
        return moviePresenter.present(Movie);
    }
    
    @PUT
    public void update(@QueryParam("id") String movieIds, Movie movie){
        //List<String> movieIdsAsList = Arrays.asList(movieIds.split(","));
        String[] movieIdsAsList = movieIds.split(",");
        
         if (movieIdsAsList.length == 1)
             movieService.update(movieIdsAsList[0], movie);
         else
            movieService.updateMany(movieIdsAsList, movie);
    }
    
    /*@DELETE
    public void delete(@QueryParam("id") String movieIds){
        List<String> movieIdsAsList = Arrays.asList(movieIds.split(","));
        List<ObjectId> movieIdsAsObjectIdList = new ArrayList<>();
        
        for(String id : movieIdsAsList){
            if(ObjectId.isValid(id)){
                ObjectId objectId = new ObjectId(id);
                movieIdsAsObjectIdList.add(objectId);
            }
        }
        movieService.delete(movieIdsAsObjectIdList);
    }*/
    
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
