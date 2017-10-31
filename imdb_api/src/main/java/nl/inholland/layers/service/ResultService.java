/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.inholland.layers.service;

import javax.inject.Singleton;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.WebApplicationException;
import org.mongodb.morphia.query.UpdateOperations;

@Singleton
public class ResultService {
    
    public void requireResult(Object obj, String message) throws NotFoundException
    {
        if (obj == null)
            throw new NotFoundException(message);
    }
    
    public void emptyField(String message) throws WebApplicationException
    {
        throw new WebApplicationException(message);
    }         
    
    public void parsingError(String message) throws WebApplicationException
    {
        throw new WebApplicationException(message);
    } 
    
    public void noValidObjectId(String message)
    {
        throw new WebApplicationException(message);
    }
    
    public void duplicateDocument(String message)
    {
        throw new WebApplicationException(message);
    }
    
    public void notAuthorizedException(String message) throws NotAuthorizedException
    {
        throw new NotAuthorizedException(message);
    }
        
}
