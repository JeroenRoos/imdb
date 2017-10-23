/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.inholland.layers.model;

import java.security.Principal;
import java.util.List;
import org.bson.types.ObjectId;
import org.hibernate.validator.constraints.NotEmpty;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Reference;

@Entity(value = "movies")
public class Movie extends EntityModel
{
    private String title;
    
    private String summary;
    
    @Reference
    private List<Actor> actors;
    
    @Reference
    private Director director;
    
    @Reference
    private List<Genre> genre;
    
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

    public List<Actor> getActors()
    {
        return actors;
    }

    public void setActors(List<Actor> actors)
    {
        this.actors = actors;
    }

    public Director getDirector()
    {
        return director;
    }

    public void setDirector(Director director)
    {
        this.director = director;
    }

    public List<Genre> getGenre()
    {
        return genre;
    }

    public void setGenre(List<Genre> genre)
    {
        this.genre = genre;
    }

    public int getYear()
    {
        return year;
    }

    public void setYear(int year)
    {
        this.year = year;
    }
    
    
    
}
