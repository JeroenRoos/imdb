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
            resultService.requireResult(lstDirectors, "No directors found");

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
        directorDAO.create(director);
    }

    public void createMany(List<Director> lstDirectors)
    {
        for (Director director : lstDirectors)
            checkCreateValidity(director);
        
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

    public void update(String directorId, Director director)
    {
        ObjectId objectId;
        if (ObjectId.isValid(directorId))
        {
            objectId = new ObjectId(directorId);
            Query query = directorDAO.createQuery().field("_id").equal(objectId);
            UpdateOperations<Director> update = directorDAO.createUpdateOperations();

            checkUpdateValidity(update, director);
            
            directorDAO.update(query, update);
        }
    }

    private void checkUpdateValidity(UpdateOperations<Director> update, Director director)
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
        else if (director.getAge() < 18)
            resultService.emptyField("Director must be older than 18.");
    }

    public void delete(String directorId)
    {
        ObjectId objectId;
        if (ObjectId.isValid(directorId))
        {
            objectId = new ObjectId(directorId);
            directorDAO.deleteById(objectId);
        }
    }
}
