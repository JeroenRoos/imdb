/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.inholland.layers.model;

import org.bson.types.ObjectId;
import org.joda.time.DateTime;
public class CommentView
{
    private ObjectId id;
    private String message;
    private int postDate;
    private UserView user;
    
    public ObjectId getId()
    {
        return id;
    }

    public void setId(ObjectId id)
    {
        this.id = id;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public int getPostDate()
    {
        return postDate;
    }

    public void setPostDate(int postDate)
    {
        this.postDate = postDate;
    }

    public UserView getUser()
    {
        return user;
    }

    public void setUser(UserView user)
    {
        this.user = user;
    }
}
