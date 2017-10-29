/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.inholland.layers.presentation.model;

import java.util.ArrayList;
import java.util.List;
import nl.inholland.layers.model.Genre;
import nl.inholland.layers.model.GenreView;



public class GenrePresenter extends BasePresenter {
    
    public List<GenreView> present(List<Genre> lstGenres)
    {
        List<GenreView> view = new ArrayList<>();
        for (Genre genre : lstGenres)
        {
            GenreView genreView = initGenreView(genre);
            view.add(genreView);
        }
        return view;
    }
    
    public List<GenreView> present(Genre genre)
    {
        List<GenreView> view = new ArrayList<>();
        GenreView genreView = initGenreView(genre);
        view.add(genreView);
        return view;
    }
    
    private GenreView initGenreView(Genre genre)
    {
        GenreView genreView = new GenreView();
        
        genreView.setName(genre.getName());
        
        return genreView;
    }
}
