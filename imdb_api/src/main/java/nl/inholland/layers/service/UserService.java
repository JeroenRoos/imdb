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
    private final ResultService resultService = new ResultService();
    
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
    
    
    public User getByName(String name){
       User user = userDAO.getByName(name);
        return user;
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
        checkEntryValidity(user);
        checkDuplicate(user);
        userDAO.create(user);
    }
    
    public void createMany(List<User> users){
        for (User u : users)
        {
            checkEntryValidity(u);
            checkDuplicate(u);
        }
        userDAO.createMany(users);
        
    }
    
    public void delete(String userId)
    {
        ObjectId objectId;
        if (ObjectId.isValid(userId))
        {
            objectId = new ObjectId(userId);
            userDAO.deleteById(objectId);
        }
        else
            resultService.noValidObjectId("The user id is not valid");
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
            else
                resultService.noValidObjectId("The director id is not valid");
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
            checkEntryValidity(user);
            userDAO.update(query, ops);
        }
        else
            resultService.noValidObjectId("The user id is not valid");
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
            else
                resultService.noValidObjectId("The user id is not valid");
        }
    }
    
    
    
    private void checkEntryValidity(User u)
    {
        if ("".equals(u.getName()) || u.getName() == null)
            resultService.emptyField("name cannot be an empty");
        
        if ("".equals(u.getGender()) || u.getGender() == null)
            resultService.emptyField("gender cannot be an empty");
    }
    
    public void checkDuplicate(User user)
    {
        List<User> users = userDAO.getAll();
        for (User u : users)
        {
            if (u.getName().equals(user.getName()))
            {
                resultService.duplicateDocument("A user with the name " + user.getName() + " already exists.");
            }
        }
    }
}