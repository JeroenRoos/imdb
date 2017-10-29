/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.inholland.layers.service;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import nl.inholland.layers.model.Genre;
import nl.inholland.layers.model.User;
import nl.inholland.layers.persistence.UserDAO;
import org.bson.types.ObjectId;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

public class UserService extends BaseService
{

    private final UserDAO userDAO;
    
    @Inject
    public UserService(UserDAO userDAO){
        this.userDAO = userDAO;
    }
    
    public User get(String userId)
    {
        User user = userDAO.get(userId);
        return user;
    }
    
    public List<User> getAll()
    {
        List<User> users = new ArrayList<User>(userDAO.getAll());
        return users;
    }
    
    
    public List<User> getByName(String name){
        List<User> users = userDAO.getByName(name);
        return users;
    }
    
    public List<User> getByIsAdmin(boolean isAdmin){
        List<User> users = userDAO.getByIsAdmin(isAdmin);
        return users;
    }
    
    public List<User> getByGender(String gender){
        List<User> users = userDAO.getByGender(gender);
        return users;
    }
    public List<User> getByParameters(String name, String gender, String isAdmin){
        List<User> users = userDAO.getByParameters(name, gender, isAdmin);
        return users;
    }
    
    public void create(User user){
        userDAO.create(user);
    }
    
    public void createMany(List<User> users){
        userDAO.createMany(users);
        
    }public void delete(String userId)
    {
        ObjectId objectId;
        if (ObjectId.isValid(userId))
        {
            objectId = new ObjectId(userId);
            userDAO.deleteById(objectId);
        }
    }

    public void deleteMany(String[] ids)
    {        
        for (int i = 0; i < ids.length; i++)
        {
            if (ObjectId.isValid(ids[i]))
            {
                ObjectId objectId = new ObjectId(ids[i]);
                userDAO.deleteById(objectId);
            }
        }
    }
    
    public void update(String userId, User user){
        ObjectId objectId;
        if(ObjectId.isValid(userId)){
            objectId = new ObjectId(userId);
            Query query = userDAO.createQuery().field("_id").equal(objectId);
            UpdateOperations<User> ops = userDAO.createUpdateOperations()
                    .set("name", user.getName())
                    .set("gender", user.getGender())
                    .set("isAdmin", user.getIsAdmin());
            userDAO.update(query, ops);
        }
    }

    public void updateMany(String[] ids, User[] users)
    {        
        for (int i = 0; i < ids.length; i++)
        {
            if (ObjectId.isValid(ids[i]))
            {
                ObjectId objectId = new ObjectId(ids[i]);
                update(ids[i], users[i]);
            }
        }
    }
}