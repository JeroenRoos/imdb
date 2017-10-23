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
import nl.inholland.layers.model.Serie;
import nl.inholland.layers.model.SerieView;

/**
 *
 * @author Jeroen
 */
public class SeriePresenter extends BasePresenter
{
    public Serie present(Serie serie)
    {
        Serie serieView;
        serieView = serie;
        return serieView;
    }
    
    public List<SerieView> present(List<Serie> lstSeries)
    {
        List<SerieView> view = new ArrayList<SerieView>();
        
        for (Serie serie : lstSeries)
        {
            SerieView serieView = new SerieView();          
            serieView.setTitle(serie.getTitle());
            serieView.setSummary(serie.getSummary());
            serieView.setYear(serie.getYear());
            
            DirectorView directorView = new DirectorView();
            directorView.setAge(serie.getDirector().getAge());
            directorView.setFirstName(serie.getDirector().getFirstName());
            directorView.setLastName(serie.getDirector().getLastName());         
            serieView.setDirector(directorView);
            
            List<GenreView> lstGenreViews = new ArrayList<>();
            for(Genre genre : serie.getGenre())
            {                
                GenreView genreView = new GenreView();             
                genreView.setName(genre.getName());
                lstGenreViews.add(genreView);
            }
            serieView.setGenre(lstGenreViews);
            
            List<ActorView> lstActorViews = new ArrayList<>();
            for (Actor actor : serie.getActors())
            {
                ActorView actorView = new ActorView();
                actorView.setFirstName(actor.getFirstName());
                actorView.setLastName(actor.getLastName());
                actorView.setAge(actor.getAge());
                lstActorViews.add(actorView);
            }
            serieView.setActors(lstActorViews);
            
            view.add(serieView);
        }
        
        return view;
    }    
}
