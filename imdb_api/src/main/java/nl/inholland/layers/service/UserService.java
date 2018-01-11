/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.inholland.layers.service;

import java.util.List;
import javax.inject.Inject;
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
    
    public User getById(String userId)
    {
        User user = (User) super.getById(userId);
        return user;
    }
    
    public List<User> getAll()
    {
        List<User> users = super.getAll();
        return users;
    }
    
    public List<User> getByName(String name){
       List<User> user = userDAO.getByName(name);
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
        super.delete(userId);
    }

    public void deleteMany(String[] ids)
    {        
        super.deleteMany(ids);
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
           super.errorHandler.noValidObjectId("The user id is not valid");
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
                super.errorHandler.noValidObjectId("The user id is not valid");
        }
    }
    
    
    
    private void checkEntryValidity(User u)
    {
        if ("".equals(u.getName()) || u.getName() == null)
            super.errorHandler.emptyField("name cannot be an empty");
        
        if ("".equals(u.getGender()) || u.getGender() == null)
            super.errorHandler.emptyField("gender cannot be an empty");
    }
    
    public void checkDuplicate(User user)
    {
        List<User> users = userDAO.getAll();
        for (User u : users)
        {
            if (u.getName().equals(user.getName()))
            {
                super.errorHandler.duplicateDocument("A user with the name " + user.getName() + " already exists.");
            }
        }
    }
}