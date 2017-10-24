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
public class GenrePresenter extends BasePresenter {
    
    public List<Genre> present(List<Genre> genres)
    {
        return genres;
        
    }

    public Genre present(Genre genre)
    {
                
        return genre;
    }
}
