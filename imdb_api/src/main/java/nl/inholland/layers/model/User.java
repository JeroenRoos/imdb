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
    
    private String username;
    
    private String password;
    
    private String role;

    public User(String username, String password)
    {
        this.username = username;
        this.password = password;
    }
    
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
        // Een gok dat het zo moet, werkelijk geen idee
        if (role.equals(this.role))
            return true;
        else 
            return false;
    }
    
    public void setRole(String role)
    {
        this.role = role;
    }
    
    public String getRole()
    {
        return this.role;
    }

    public Object getPassword()
    {
        return password;
    }
    
    public Object getUsername()
    {
        return username;
    }
    
}
