/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.inholland.layers.model;

import java.security.Principal;
import java.util.List;
import org.mongodb.morphia.annotations.Entity;

@Entity(value = "series")

public class Series extends EntityModel implements Principal
{
    private String name;
    
    private List<EntityModel> genre;
    
    private List<EntityModel> actor;
    
    private String summary;
    
    private EntityModel director;

    
    @Override
    public String getName()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
