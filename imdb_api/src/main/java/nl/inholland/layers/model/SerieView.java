/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.inholland.layers.model;

import java.util.List;
import org.bson.types.ObjectId;
/**
 *
 * @author Jeroen
 */
public class SerieView 
{
    private ObjectId id;
    private String title;
    private String summary;
    private List<ActorView> lstActors;
    private List<DirectorView> director;
    private List<GenreView> lstGenres;
    
    private int year;
    
    public ObjectId getId()
    {
        return id;
    }

    public void setId(ObjectId id)
    {
        this.id = id;
    }

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

    public List<ActorView> getActors()
    {
        return lstActors;
    }

    public void setActors(List<ActorView> lstActors)
    {
        this.lstActors = lstActors;
    }

    public List<DirectorView> getDirectors()
    {
        return director;
    }

    public void setDirector(List<DirectorView> director)
    {
        this.director = director;
    }

    public List<GenreView> getGenre()
    {
        return lstGenres;
    }

    public void setGenre(List<GenreView> lstGenres)
    {
        this.lstGenres = lstGenres;
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
