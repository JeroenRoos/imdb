/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.inholland.layers.persistence;

import java.util.List;
import javax.inject.Inject;
import nl.inholland.layers.model.User;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.annotations.Field;
import org.mongodb.morphia.query.Query;

public class UserDAO extends BaseDAO<User>
{
    private Datastore ds;
    
    @Inject
    public UserDAO(Datastore ds)
    {
        super(User.class, ds);
    }
    
    /*public User getByName(String username){
        Query<User> query = createQuery().field("name").equal(username);
        return findOne(query);
    }*/
    
    public List<User> getByGender(String gender){
        return createQuery().field("gender").equal(gender).asList();
    }
    
    public List<User> getByName(String name){
        return createQuery().field("name").equal(name).asList();
    }
    
    public List<User> getByIsAdmin(boolean isAdmin){
        return createQuery().field("isAdmin").equal(isAdmin).asList();
    }
    
    public List<User> getByParameters(String name, String gender, String isAdmin){    
        Query<User> query = createQuery();
        if (!"".equals(name))
            query.field("name").equal(name);
        if (!"".equals(gender))
            query.field("gender").equal(gender);
        if (!"".equals(isAdmin))
        {
            boolean isAdminBool = Boolean.valueOf(isAdmin);
            query.field("isAdmin").equal(isAdminBool);
        }
        return query.asList();
    }
}