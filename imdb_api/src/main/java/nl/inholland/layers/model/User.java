/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.inholland.layers.model;

import java.security.Principal;
import org.mongodb.morphia.annotations.Entity;

@Entity(value = "users", noClassnameStored = true)

public class User extends EntityModel implements Principal
{
    private String name;
    
    private String gender;
    
    private boolean isAdmin;
    
    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getGender()
    {
        return gender;
    }

    public void setGender(String gender)
    {
        this.gender = gender;
    }

    public boolean getIsAdmin()
    {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin)
    {
        this.isAdmin = isAdmin;
    }

    public boolean hasRole(String role)
    {
        // Do something?
        return false;
    }
    
}
