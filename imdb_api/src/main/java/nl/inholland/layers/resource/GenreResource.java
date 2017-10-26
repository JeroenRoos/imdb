/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.inholland.layers.resource;

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
import javax.ws.rs.core.MediaType;
import nl.inholland.layers.presentation.model.GenrePresenter;
import nl.inholland.layers.service.GenreService;
import nl.inholland.layers.model.GenreView;
import nl.inholland.layers.model.Genre;


@Path("/genres")
@Consumes (MediaType.APPLICATION_JSON)
@Produces (MediaType.APPLICATION_JSON)
public class GenreResource extends BaseResource{
        
    private final GenreService genreService;
    private final GenrePresenter genrePresenter;
    
    @Inject
    public GenreResource( GenreService genreService,
            GenrePresenter genrePresenter){
        this.genreService = genreService;
        this.genrePresenter = genrePresenter;
    }
    
    @GET
    public List<Genre> getAll(@DefaultValue("") @QueryParam("name") String genreName ){
        if(!"".equals(genreName)){
            List<Genre> genres = genreService.getByName(genreName);
            
            return genrePresenter.present(genres);

        }else{
            
            List<Genre> genres = genreService.getAll();
        
            return genrePresenter.present(genres);
        }
       
    }
    
    @GET
    @Path("/{GenreId}")
    public Genre get( @PathParam("GenreId") String genreId){
        Genre genre = genreService.get(genreId);
        
        return genrePresenter.present(genre);
    }
            
    @POST
    public void createMany(List<Genre> genres){
        genreService.createMany(genres);
    }
    
    @PUT
    @Path("/{GenreId}")
    public void update(@PathParam("GenreId") String genreId, Genre genre){
        genreService.update(genreId, genre);
    }
    
    @DELETE
    @Path("/{GenreId}")
    public void delete(@PathParam("GenreId") String genreId){
        genreService.delete(genreId);
    }
}
