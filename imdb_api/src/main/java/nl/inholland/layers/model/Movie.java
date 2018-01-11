/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.inholland.layers.model;


import java.util.List;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Reference;

@Entity(value = "movies", noClassnameStored = true)
public class Movie extends EntityModel
{
    private String title;    
    private String summary;
    private int rating;
    
    @Reference
    private List<Actor> actors;
    
    @Reference
    private List<Comment> comments;
    
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

    public List<Comment> getComments()
    {
        return comments;
    }

    public void setComments(List<Comment> comments)
    {
        this.comments = comments;
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
    
    public void getRating(int rating){
        
        this.rating = rating;
    }
  
    public void setRating(int rating){
        
        this.rating = rating;
    }
    
}
