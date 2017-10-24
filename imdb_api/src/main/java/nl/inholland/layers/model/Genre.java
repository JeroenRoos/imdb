/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.inholland.layers.model;

import java.security.Principal;
import org.mongodb.morphia.annotations.Entity;

@Entity(value = "genres", noClassnameStored = true)
public class Genre extends EntityModel
{
    private String name;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
    
    
    
}
