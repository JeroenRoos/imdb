/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.inholland.layers.service;

import javax.inject.Singleton;
<<<<<<< HEAD
import javax.ws.rs.NotAuthorizedException;
=======
import javax.ws.rs.BadRequestException;
>>>>>>> 6267c4d45b6fe9b4df5a0d77ab4507fd7e4622f0
import javax.ws.rs.NotFoundException;
import javax.ws.rs.WebApplicationException;

@Singleton
public class ResultService 
{    
    public void requireResult(Object obj, String message) throws NotFoundException
    {
        if (obj == null)
            throw new NotFoundException(message);
    }
    
    public void emptyField(String message) throws WebApplicationException
    {
        throw new BadRequestException(message);
    }         
    
    public void parsingError(String message) throws WebApplicationException
    {
        throw new BadRequestException(message);
    } 
    
    public void noValidObjectId(String message)
    {
        throw new BadRequestException(message);
    }
    
    public void duplicateDocument(String message)
    {
        throw new WebApplicationException(message);
<<<<<<< HEAD
    }
    
    public void notAuthorizedException(String message) throws NotAuthorizedException
    {
        throw new NotAuthorizedException(message);
    }
        
=======
    }     
>>>>>>> 6267c4d45b6fe9b4df5a0d77ab4507fd7e4622f0
}
