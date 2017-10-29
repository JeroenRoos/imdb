/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.inholland.layers.model;

import java.util.List;

public class MovieView
{
    private String title;
    
    private String summary;
    
    private List<ActorView> actors;
    
    private DirectorView director;
    
    private List<GenreView> genres;
    
    private int year;

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getSummary()
    {
        return summary;
    }

    public void setSummary(String summary)
    {
        this.summary = summary;
    }

    public int getYear()
    {
        return year;
    }

    public void setYear(int year)
    {
        this.year = year;
    }

    public List<ActorView> getActors()
    {
        return actors;
    }

    public void setActors(List<ActorView> actors)
    {
        this.actors = actors;
    }

    public DirectorView getDirector()
    {
        return director;
    }

    public void setDirector(DirectorView director)
    {
        this.director = director;
    }

    public List<GenreView> getGenres()
    {
        return genres;
    }

    public void setGenres(List<GenreView> genres)
    {
        this.genres = genres;
    }
    
      
}
