/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.inholland.layers.model;

/**
 *
 * @author youp
 */
import java.security.Principal;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Id;

public class EntityModel
{
    @Id
    protected ObjectId id;

    public ObjectId getId()
    {
        return id;
    }

    public void setId( ObjectId id )
    {
        this.id = id;
    }
    
    
}