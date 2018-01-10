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
public class DirectorService extends BaseService
{

    private final DirectorDAO directorDAO;

    @Inject
    public DirectorService(DirectorDAO directorDAO)
    {
        this.directorDAO = directorDAO;
    }

    public Director get(String directorId)
    {
        Director director = directorDAO.get(directorId);
        super.requireResult(director, "Director not found");
        return director;
    }

    public List<Director> getAll()
    {
        List<Director> lstDirectors = directorDAO.getAll();

        if (lstDirectors.isEmpty())
            super.requireResult(null, "No directors found");

        return lstDirectors;
    }
    
    public List<Director> getByName(String directorName)
    {
        List<Director> lstDirectors = directorDAO.getByLastName(directorName);
        return lstDirectors;
    }  
    
    public List<Director> getByAge(String directorAge)
    {
        int age = 0;
        try
        {
            age = Integer.parseInt(directorAge);
        }
        catch (Exception ex)
        {
            super.parsingError("Something went wrong while converting the age to an integer.");
        }
        
        List<Director> lstDirectors = directorDAO.getByAge(age);
        return lstDirectors;
    }
    
    public void create(List<Director> lstDirectors)
    {
        if (lstDirectors.size() == 1)
            createOne(lstDirectors.get(0));
        else
            createMany(lstDirectors);
    }
    
    private void createOne(Director director)
    {
        checkCreateValidity(director);
        checkDuplicate(director);
        directorDAO.create(director);
    }

    private void createMany(List<Director> lstDirectors)
    {
        // Check all the Director for validity
        for (Director director : lstDirectors)
        {
            checkCreateValidity(director);
            checkDuplicate(director);
        }
        
        // Create all the Directors after each director is checked for validity
        directorDAO.createMany(lstDirectors);
    }
    
    private void checkCreateValidity(Director director)
    {
        if ("".equals(director.getFirstName()) || director.getFirstName() == null)
            super.emptyField("Firstname cannot be an empty string");
        
        if ("".equals(director.getLastName()) || director.getLastName() == null)
            super.emptyField("Lastname cannot be an empty string");
        
        if (director.getAge() == 0)
            super.emptyField("Director cannot be aged 0");
    }
    
    public void checkDuplicate(Director director)
    {
        List<Director> lstDirectors = directorDAO.getAll();
        for (Director d : lstDirectors)
        {
            if (d.getFirstName().equals(director.getFirstName()) && d.getLastName().equals(director.getLastName()))
            {
                super.duplicateDocument("An director with the name " + director.getFirstName() + " " + director.getLastName() + " already exists.");
            }
        }
    }
    
    public void update(String directorIds, Director director)
    {
        String[] ids = directorIds.split(",");

        if (ids.length == 1)
            updateOne(ids[0], director);
        else
            updateMany(ids, director);  
    }
    
    private void updateOne(String directorId, Director director)
    {
        ObjectId objectId;
        if (ObjectId.isValid(directorId))
        {
            checkIfDirectorExists(directorId);
            objectId = new ObjectId(directorId);
            Query query = directorDAO.createQuery().field("_id").equal(objectId);
            UpdateOperations<Director> update = directorDAO.createUpdateOperations();

            checkUpdateValidity(update, director, objectId);          
            directorDAO.update(query, update);
        }
        else
            super.noValidObjectId("The director id is not valid");
    }
    
    private void updateMany(String[] ids, Director director)
    {       
        Query[] lstQueries = new Query[ids.length];
        UpdateOperations[] lstUpdateOperations = new UpdateOperations[ids.length];
        
        for (int i = 0; i < ids.length; i++)
        {
            if (ObjectId.isValid(ids[i]))
            {
                checkIfDirectorExists(ids[i]);
                ObjectId objectId = new ObjectId(ids[i]);
                
                Query query = directorDAO.createQuery().field("_id").equal(objectId);
                UpdateOperations<Director> update = directorDAO.createUpdateOperations();
                checkUpdateValidity(update, director, objectId); 
                
                lstQueries[i] = query;
                lstUpdateOperations[i] = update;
            }
            else
                super.noValidObjectId("The director id is not valid");
        }
        
        directorDAO.updateMany(lstQueries, lstUpdateOperations);
    }

    private void checkUpdateValidity(UpdateOperations<Director> update, Director director, ObjectId id)
    {
        if (!"".equals(director.getFirstName()) && director.getFirstName() != null)
            update.set("firstName", director.getFirstName());
        else if ("".equals(director.getFirstName()))
            super.emptyField("Firstname cannot be an empty string");

        if (!"".equals(director.getLastName()) && director.getLastName() != null)
            update.set("lastName", director.getLastName());
        else if ("".equals(director.getLastName()))
            super.emptyField("Lastname cannot be an empty string");

        if (director.getAge() >= 18)
            update.set("age", director.getAge());
        else if (director.getAge() == 0)
        {
            Director d = directorDAO.get(id);
            director.setAge(d.getAge()); 
        }
        else 
            super.emptyField("Director must be older than 18.");
    }
    
    public void delete(String directorIds)
    {
        String[] ids = directorIds.split(",");
        
        if (ids.length == 1)
            deleteOne(ids[0]);
        else
            deleteMany(ids);
    }
    
    private void deleteOne(String directorId)
    {
        ObjectId objectId;
        if (ObjectId.isValid(directorId))
        {
            checkIfDirectorExists(directorId);
            objectId = new ObjectId(directorId);
            directorDAO.deleteById(objectId);
        }
        else
            super.noValidObjectId("The director id is not valid");
    }

    private void deleteMany(String[] ids)
    {
        List<ObjectId> lstObjectIds = new ArrayList<>();
        
        // Check each Director for validity
        for (int i = 0; i < ids.length; i++)
        {
            if (ObjectId.isValid(ids[i]))
            {
                checkIfDirectorExists(ids[i]);
                ObjectId objectId = new ObjectId(ids[i]);
                lstObjectIds.add(objectId);
            }
            else
                super.noValidObjectId("The director id is not valid");
        }
        
        // Delete all the Directors after each director is checked for validity
        directorDAO.deleteManyById(lstObjectIds);
    }
    
    private void checkIfDirectorExists(String directorId)
    {
        Director director = directorDAO.get(directorId);
        super.requireResult(director, "The Director you're trying to delete doesn't exist");
    }
}
