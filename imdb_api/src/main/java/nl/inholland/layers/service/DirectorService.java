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
    private final ResultService resultService = new ResultService();

    @Inject
    public DirectorService(DirectorDAO directorDAO)
    {
        this.directorDAO = directorDAO;
    }

    public Director get(String directorId)
    {
        Director director = directorDAO.get(directorId);
        resultService.requireResult(director, "Director not found");
        return director;
    }

    public List<Director> getAll()
    {
        List<Director> lstDirectors = directorDAO.getAll();

        if (lstDirectors.isEmpty())
            resultService.requireResult(null, "No directors found");

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
            resultService.parsingError("Something went wrong while converting the age to an integer.");
        }
        
        List<Director> lstDirectors = directorDAO.getByAge(age);
        return lstDirectors;
    }
    
    
    public void create(Director director)
    {
        checkCreateValidity(director);
        checkDuplicate(director);
        directorDAO.create(director);
    }

    public void createMany(List<Director> lstDirectors)
    {
        for (Director director : lstDirectors)
        {
            checkCreateValidity(director);
            checkDuplicate(director);
        }
        
        directorDAO.createMany(lstDirectors);
    }
    
    private void checkCreateValidity(Director director)
    {
        if ("".equals(director.getFirstName()) || director.getFirstName() == null)
            resultService.emptyField("Firstname cannot be an empty string");
        
        if ("".equals(director.getLastName()) || director.getLastName() == null)
            resultService.emptyField("Lastname cannot be an empty string");
        
        if (director.getAge() == 0)
            resultService.emptyField("Director cannot be aged 0");
    }
    
    public void checkDuplicate(Director director)
    {
        List<Director> lstDirectors = directorDAO.getAll();
        for (Director d : lstDirectors)
        {
            if (d.getFirstName().equals(director.getFirstName()) && d.getLastName().equals(director.getLastName()))
            {
                resultService.duplicateDocument("An director with the name " + director.getFirstName() + " " + director.getLastName() + " already exists.");
            }
        }
    }
    
    public void update(String directorId, Director director)
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
            resultService.noValidObjectId("The director id is not valid");
    }
    
    
    public void updateMany(String[] ids, Director director)
    {
        for (int i = 0; i < ids.length; i++)
        {
            if (ObjectId.isValid(ids[i]))
            {
                checkIfDirectorExists(ids[i]);
                ObjectId objectId = new ObjectId(ids[i]);
                
                Query query = directorDAO.createQuery().field("_id").equal(objectId);
                UpdateOperations<Director> update = directorDAO.createUpdateOperations();
                checkUpdateValidity(update, director, objectId); 
                directorDAO.update(query, update);
            }
            else
                resultService.noValidObjectId("The director id is not valid");
        }
    }

    private void checkUpdateValidity(UpdateOperations<Director> update, Director director, ObjectId id)
    {
        if (!"".equals(director.getFirstName()) && director.getFirstName() != null)
            update.set("firstName", director.getFirstName());
        else if ("".equals(director.getFirstName()))
            resultService.emptyField("Firstname cannot be an empty string");

        if (!"".equals(director.getLastName()) && director.getLastName() != null)
            update.set("lastName", director.getLastName());
        else if ("".equals(director.getLastName()))
            resultService.emptyField("Lastname cannot be an empty string");

        if (director.getAge() >= 18)
            update.set("age", director.getAge());
        else if (director.getAge() == 0)
        {
            Director d = directorDAO.get(id);
            director.setAge(d.getAge()); 
        }
        else 
            resultService.emptyField("Director must be older than 18.");
    }
    
    public void delete(String directorId)
    {
        ObjectId objectId;
        if (ObjectId.isValid(directorId))
        {
            checkIfDirectorExists(directorId);
            objectId = new ObjectId(directorId);
            directorDAO.deleteById(objectId);
        }
        else
            resultService.noValidObjectId("The director id is not valid");
    }

    public void deleteMany(String[] ids)
    {
        List<ObjectId> lstObjectIds = new ArrayList<>();
        
        for (int i = 0; i < ids.length; i++)
        {
            if (ObjectId.isValid(ids[i]))
            {
                checkIfDirectorExists(ids[i]);
                ObjectId objectId = new ObjectId(ids[i]);
                lstObjectIds.add(objectId);
            }
            else
                resultService.noValidObjectId("The director id is not valid");
        }
        
        directorDAO.deleteManyById(lstObjectIds);
    }
    
    private void checkIfDirectorExists(String directorId)
    {
        Director director = directorDAO.get(directorId);
        resultService.requireResult(director, "The Director you're trying to delete doesn't exist");
    }
}
