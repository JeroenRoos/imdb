/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.inholland.layers.service;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import nl.inholland.layers.model.Director;
import nl.inholland.layers.persistence.DirectorDAO;
import org.bson.types.ObjectId;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

/**
 *
 * @author Jeroen
 */

// The service class of the Directors
// This class handles the business logic as well as the validation (and exceptions if needed)
// The results are returned to the resource
public class DirectorService extends BaseService
{
    private final DirectorDAO directorDAO;

    @Inject
    public DirectorService(DirectorDAO directorDAO)
    {
        this.directorDAO = directorDAO;
    }

    
    // Get an existing director by ID
    public Director getDirectorById(String directorId)
    {
        Director director = (Director)super.getById(directorId, directorDAO);
        
        // Validation, check if the Director exists
        super.errorHandler.requireResult(director, "Director not found");
        
        return director;
    }

    
    // Get all existing directors
    public List<Director> getAll()
    {
        List<Director> lstDirectors = super.getAll(directorDAO);
        
        // Validation, check if any directors exist 
        if (lstDirectors.isEmpty())
            super.errorHandler.requireResult(null, "Director not found");
        
        return lstDirectors;
    }
    
    
    // Get a director by lastname   
    public List<Director> getByLastName(String directorName)
    {
        List<Director> lstDirectors = directorDAO.getByLastName(directorName);
        
        // Validation, check if any directors exist with this lastname
        if (lstDirectors.isEmpty())
            super.errorHandler.requireResult(null, "Director not found with lastname: " + directorName);
        
        return lstDirectors;
    }  
    
    
    // Get a director by age
    public List<Director> getByAge(String directorAge)
    {
        int age = 0;
        
        // Validation, try to parse the age to a valid integer
        try
        {
            age = Integer.parseInt(directorAge);
        }
        catch (Exception ex)
        {
            super.errorHandler.parsingError("Something went wrong while converting the age to an integer.");
        }
        
        List<Director> lstDirectors = directorDAO.getByAge(age);
        
        // Validation, check if any directors exist with this age
        if (lstDirectors.isEmpty())
            super.errorHandler.requireResult(null, "Director not found with age: " + directorAge);
        
        return lstDirectors;
    }
    
    
    // Create one director
    public void createOne(Director director)
    {
        // Validation, check if all the required fields do exist and are not empty
        checkCreateValidity(director);
            
        // Validation, check if the director already exist in the database
        checkDuplicate(director);
        
        directorDAO.create(director);
    }

    
    // Create multiple directors
    public void createMany(List<Director> lstDirectors)
    {
        for (Director director : lstDirectors)
        {
            // Validation, check if all the required fields do exist and are not empty
            checkCreateValidity(director);
            
            // Validation, check if any of the directors already exist in the database
            checkDuplicate(director);
        }      
        // Create all the Directors after each director is checked for validity
        directorDAO.createMany(lstDirectors);
    }
    
    
    // Validation, check if all the required fields do exist and are not empty
    private void checkCreateValidity(Director director)
    {
        // Check the validity of the firstname field
        if (director.getFirstName() == null || "".equals(director.getFirstName()))
            super.errorHandler.emptyField("Firstname cannot be an empty string");
        
        // Check the validity of the lastname field
        if (director.getLastName() == null || "".equals(director.getLastName()))
            super.errorHandler.emptyField("Lastname cannot be an empty string");
        
        // Check the validity of the age field
        if (director.getAge() == 0)
            super.errorHandler.emptyField("Director cannot be aged 0");
    }
    
    
    // Validation, check if the director already exists in the Database
    public void checkDuplicate(Director director)
    {
        // Get all the directors from the Database
        List<Director> lstDirectors = directorDAO.getAll();
        
        // Validatoin, check for each director if he/she already exists with firstname + lastname
        for (Director d : lstDirectors)
        {
            if (d.getFirstName().equals(director.getFirstName()) && d.getLastName().equals(director.getLastName()))
            {
                super.errorHandler.duplicateDocument("An director with the name " + director.getFirstName() + " " + director.getLastName() + " already exists.");
            }
        }
    }
    
    
    // Update one director
    public void updateOne(String directorId, Director director)
    {
        ObjectId objectId;
        
        // Validation, check if the ID is valid
        if (ObjectId.isValid(directorId))
        {
            // Validation, Check if the directors exists 
            checkIfDirectorExists(directorId);
            
            objectId = new ObjectId(directorId);
            Query query = directorDAO.createQuery().field("_id").equal(objectId);
            UpdateOperations<Director> update = directorDAO.createUpdateOperations();

            // Validation, check if all the field(s) that are gonne be updated are not empty
            checkUpdateValidity(update, director, objectId);       
            
            directorDAO.update(query, update);
        }
        else
            super.errorHandler.noValidObjectId("The director id is not valid");
    }
    
    
    // Update multipe directors at the same time
    public void updateMany(String[] ids, Director director)
    {       
        Query[] lstQueries = new Query[ids.length];
        UpdateOperations[] lstUpdateOperations = new UpdateOperations[ids.length];
        
        for (int i = 0; i < ids.length; i++)
        {
            // Validation, check if the ID is valid
            if (ObjectId.isValid(ids[i]))
            {
                // Validation, Check if the directors exists 
                checkIfDirectorExists(ids[i]);
                
                ObjectId objectId = new ObjectId(ids[i]);
                Query query = directorDAO.createQuery().field("_id").equal(objectId);
                UpdateOperations<Director> update = directorDAO.createUpdateOperations();
                
                // Validation, check if all the field(s) that are gonne be updated are not empty
                checkUpdateValidity(update, director, objectId); 
                
                lstQueries[i] = query;
                lstUpdateOperations[i] = update;
            }
            else
                super.errorHandler.noValidObjectId("The director id is not valid");
        }
        
        // Update all the Directors after each director is checked for validity
        directorDAO.updateMany(lstQueries, lstUpdateOperations);
    }

    
    // Validation, check if all the field(s) that are gonne be updated are not empty
    private void checkUpdateValidity(UpdateOperations<Director> update, Director director, ObjectId id)
    {
        // Check the validity of the firstname field. If its not empty, add it to the update Operation
        // If it's does exists but is empty, throw an exception
        if (director.getFirstName() != null && !"".equals(director.getFirstName()))
            update.set("firstName", director.getFirstName());
        else if ("".equals(director.getFirstName()))
            super.errorHandler.emptyField("Firstname cannot be an empty string");
        
        // Check the validity of the lastname field. If its not empty, add it to the update Operation
        // If it's does exists but is empty, throw an exception
        if (director.getLastName() != null && !"".equals(director.getLastName()))
            update.set("lastName", director.getLastName());
        else if ("".equals(director.getLastName()))
            super.errorHandler.emptyField("Lastname cannot be an empty string");
        
        // Check the validity of the age field. If its not empty, add it to the update Operation
        // If it does exists but is empty, add the age of the director that's gonne be updated. 
        // This is needed because the age field is automatically put to 0 if it doesn't exist
        if (director.getAge() >= 18)
            update.set("age", director.getAge());
        else if (director.getAge() == 0)
        {
            Director d = directorDAO.get(id);
            director.setAge(d.getAge()); 
        }
        else 
            super.errorHandler.emptyField("Director must be older than 18.");
    }
    
    
    // Delete one director by ID
    public void deleteOne(String directorId)
    {
        // Validation, check if the ID is valid
        if(ObjectId.isValid(directorId))
        {
            // Validation, check if the director exists
            checkIfDirectorExists(directorId);
            
            super.delete(directorId, directorDAO);
        }
        else
            super.errorHandler.noValidObjectId("The director ID is not valid.");
    }

    
    // Delete multiple directors by ID
    public void deleteMany(String[] ids)
    {
        List<ObjectId> lstObjectIds = new ArrayList<>();
        
        // Validation, check if the ID of each director is valid
        for (int i = 0; i < ids.length; i++)
        {
            if (ObjectId.isValid(ids[i]))
            {
                // Validation, Check if the directors exists 
                checkIfDirectorExists(ids[i]);
                ObjectId objectId = new ObjectId(ids[i]);
                lstObjectIds.add(objectId);
            }
            else
                super.errorHandler.noValidObjectId("The director id is not valid");
        }
        
        // Delete all the Directors after each director is checked for validity
        directorDAO.deleteManyById(lstObjectIds);
    }
    
    
    // Validation, checks if a director exists by ID
    private void checkIfDirectorExists(String directorId)
    {
        Director director = directorDAO.get(directorId);
        super.errorHandler.requireResult(director, "The Director you're trying to delete doesn't exist");
    }
}
