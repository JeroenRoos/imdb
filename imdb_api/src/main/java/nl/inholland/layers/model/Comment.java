/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.inholland.layers.model;

import org.joda.time.DateTime;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Reference;

@Entity(value = "comments", noClassnameStored = true)
public class Comment extends EntityModel
{
    private String message;
    
    private DateTime postDate;
    
    @Reference
    private User user;

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public DateTime getPostDate()
    {
        return postDate;
    }

    public void setPostDate(DateTime postDate)
    {
        this.postDate = postDate;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }
}
