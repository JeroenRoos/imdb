/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.inholland.layers.service;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import nl.inholland.layers.model.Director;
import nl.inholland.layers.model.Genre;
import nl.inholland.layers.model.Serie;
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
    private ResultService resultService = new ResultService();

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
        List<Director> lstDirectors = new ArrayList<>(directorDAO.getAll());

        if (lstDirectors.size() == 0)
        {
            resultService.requireResult(lstDirectors, "No directors found");
        }

        return lstDirectors;
    }

    public void create(Director director)
    {
        if (director.getFirstName() == "" || director.getFirstName() == null)
            resultService.emptyField("Firstname cannot be an empty string");

        if (director.getLastName() == "" || director.getLastName() == null)
            resultService.emptyField("Lastname cannot be an empty string");

        if (director.getAge() == 0)
            resultService.emptyField("Director cannot be aged 0");

        directorDAO.create(director);
    }

    public void update(String directorId, Director director)
    {
        ObjectId objectId;
        if (ObjectId.isValid(directorId))
        {
            objectId = new ObjectId(directorId);
            Query query = directorDAO.createQuery().field("_id").equal(objectId);
            UpdateOperations<Director> update = directorDAO.createUpdateOperations();

            if (director.getFirstName() != "" && director.getFirstName() != null)
                update.set("firstName", director.getFirstName());
            else if (director.getFirstName() == "")
                resultService.emptyField("Firstname cannot be an empty string");

            if (director.getLastName() != "" && director.getLastName() != null)
                update.set("lastName", director.getLastName());
            else if (director.getLastName() == "")
                resultService.emptyField("Lastname cannot be an empty string");

            if (director.getAge() != 0)
                update.set("age", director.getAge());
            else if (director.getAge() == 0)
                resultService.emptyField("Director cannot be aged 0");

            directorDAO.update(query, update);
        }
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
