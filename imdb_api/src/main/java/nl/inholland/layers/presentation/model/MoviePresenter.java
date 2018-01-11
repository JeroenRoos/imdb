/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.inholland.layers.presentation.model;

import java.util.ArrayList;
import java.util.List;
import nl.inholland.layers.model.Movie;
import nl.inholland.layers.model.MovieView;

/**
 *
 * @author youp
 */
public class MoviePresenter extends BasePresenter
{
    private final GenrePresenter genrePresenter = new GenrePresenter();
    private final ActorPresenter actorPresenter = new ActorPresenter();
    private final DirectorPresenter directorPresenter = new DirectorPresenter();
    
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
        movieView.setId(movie.getId());
        movieView.setTitle(movie.getTitle());
        movieView.setSummary(movie.getSummary());
        movieView.setYear(movie.getYear());
            
        movieView.setGenres(genrePresenter.present(movie.getGenre()));
        movieView.setDirector(directorPresenter.present(movie.getDirector()));
        movieView.setActors(actorPresenter.present(movie.getActors()));
        
        return movieView;
    }
   
}