/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.inholland.layers.persistence;

import java.util.List;
import javax.inject.Inject;
import nl.inholland.layers.model.User;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

public class UserDAO extends BaseDAO<User>
{
    @Inject
    public UserDAO(Datastore ds)
    {
        super(User.class, ds);
    }
    
    public User getByName(String username){
        Query<User> query = createQuery().field("name").equal(username);
        return findOne(query);
    }
    
    public List<User> getByGender(String gender){
        return createQuery().field("gender").equal(gender).asList();
    }

}