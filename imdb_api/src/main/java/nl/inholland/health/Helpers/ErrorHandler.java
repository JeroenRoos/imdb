/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.inholland.health.Helpers;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.WebApplicationException;

/**
 *
 * @author C Tiel
 */
public class ErrorHandler {
    
        // If the object doesn't exists --> thrown an error
    public void requireResult(Object obj, String message) throws NotFoundException
    {
        if (obj == null)
            throw new NotFoundException(message);
    }
    
    
    // Throw an exception because an required field is empty or doesn't exist
    public void emptyField(String message) throws WebApplicationException
    {
        throw new BadRequestException(message);
    }         
    
    
    // Throw an exception because an number input wan't valid during the parsing
    public void parsingError(String message) throws WebApplicationException
    {
        throw new BadRequestException(message);
    } 
    
    
    // Throw an exception because the given objectID wasn't valid
    public void noValidObjectId(String message) throws BadRequestException
    {
        throw new BadRequestException(message);
    }
    
    
    // Throw an exception because the document already exists
    public void duplicateDocument(String message) throws WebApplicationException
    {
        throw new WebApplicationException(message);
    }
    
    
    // Throw an exception because the user is not authorized for an operation
    public void notAuthorizedException(String message) throws NotAuthorizedException
    {
        throw new NotAuthorizedException(message);
    }

    void get(String actorId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    } 
    
}
