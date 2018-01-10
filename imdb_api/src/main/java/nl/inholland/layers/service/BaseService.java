/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.inholland.layers.service;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Singleton;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.WebApplicationException;
import nl.inholland.layers.model.EntityModel;
import nl.inholland.layers.persistence.BaseDAO;
import org.bson.types.ObjectId;




@Singleton
public class BaseService <T extends EntityModel>
{

    //get all
    public List<T> getAll(BaseDAO baseDAO)
    {
        List<T> objects;
            objects = baseDAO.getAll();
        
        if (objects.isEmpty())
            
            requireResult(objects, "No results found");

        return objects;
    }
     
    //get by id
    public T getById(String objectId,  BaseDAO baseDAO)
    {
        T object = null;
        try{
           
           object = (T) baseDAO.get(objectId);
           
        }catch(Exception e){
            
           noValidObjectId(objectId + " is geen geldig id");       

        }
        return object;
    }
     
    //delete    
        public void delete(String objectId, BaseDAO baseDAO)
    {
        ObjectId objectIdConverted;
        if (ObjectId.isValid(objectId))
        {
            objectIdConverted = new ObjectId(objectId);
            baseDAO.deleteById(objectIdConverted);
        }
        else
           noValidObjectId("The id is not valid");
    }
    
        
    //delete many
    public void deleteMany(String[] ids, BaseDAO baseDAO)
    {
        List<ObjectId> lstObjectIds = new ArrayList<>();
        
        for (int i = 0; i < ids.length; i++)
        {
            if (ObjectId.isValid(ids[i]))
            {
                ObjectId objectId = new ObjectId(ids[i]);
                lstObjectIds.add(objectId);
            }
            else
                
                noValidObjectId("One or more id's are not valid");
        }
        
        baseDAO.deleteMany(lstObjectIds);
    }
        

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
