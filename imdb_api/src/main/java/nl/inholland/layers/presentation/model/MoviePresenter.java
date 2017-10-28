/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.inholland.layers.presentation.model;

import java.util.ArrayList;
import java.util.List;
import nl.inholland.layers.model.Actor;
import nl.inholland.layers.model.ActorView;
import nl.inholland.layers.model.DirectorView;
import nl.inholland.layers.model.Genre;
import nl.inholland.layers.model.GenreView;
import nl.inholland.layers.model.Movie;
import nl.inholland.layers.model.MovieView;

/**
 *
 * @author youp
 */
public class MoviePresenter extends BasePresenter
{
    public List<MovieView> present(List<Movie> movies)
    {
        List<MovieView> view = new ArrayList<MovieView>();
        
        for (Movie movie : movies){
            MovieView movieView = initMovieView(movie);
            
            view.add(movieView);
        }
        return view;
    }
    
    
    public MovieView present(Movie movie)
    {
        MovieView movieView = initMovieView(movie);
        return movieView;
    }
    
    private MovieView initMovieView(Movie movie)
    {
        MovieView movieView = new MovieView();
            
            movieView.setTitle(movie.getTitle());
            movieView.setSummary(movie.getSummary());
            
            List<ActorView> actorViews = new ArrayList<ActorView>();
            for( Actor actor : movie.getActors()){
                ActorView actorView = new ActorView();
                
                actorView.setFirstName(actor.getFirstName());
                actorView.setLastName(actor.getLastName());
                actorView.setAge(actor.getAge());
                
                actorViews.add(actorView);
            }
            movieView.setActors(actorViews);
            
            List<GenreView> genres = new ArrayList<>();
            for(Genre genre : movie.getGenre()){
                GenreView genreView = new GenreView();
                
                genreView.setName(genre.getName());
                genres.add(genreView);
            }
            
            DirectorView directorView = new DirectorView();
            
            directorView.setAge(movie.getDirector().getAge());
            directorView.setFirstName(movie.getDirector().getFirstName());
            directorView.setLastName(movie.getDirector().getLastName());
           
            movieView.setDirector(directorView);
            movieView.setGenres(genres);
            movieView.setYear(movie.getYear());
        
        return movieView;
    }
   
}