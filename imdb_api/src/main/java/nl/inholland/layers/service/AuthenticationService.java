/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.inholland.layers.service;

import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;
import java.util.Optional;
import javax.inject.Inject;
import nl.inholland.layers.model.User;
import nl.inholland.layers.persistence.UserDAO;
import org.mongodb.morphia.AuthenticationException;

/**
 *
 * @author Jeroen
 */
public class AuthenticationService implements Authenticator<BasicCredentials, User>
{
    
    private final UserDAO userDAO;

    @Inject
    public AuthenticationService(UserDAO userDAO){
        this.userDAO = userDAO;
    }
    
    @Override
    public Optional<User> authenticate(BasicCredentials credentials) throws AuthenticationException
    {
        User user = new User(credentials.getUsername(), credentials.getPassword());
        
//        userDAO.get(user);
        
        User userFromDB = new User("nelleke", "test");
        
        if (!user.getName().equals(userFromDB.getName()))
        {
            return Optional.empty();
        }
        
        if (user.getPassword().equals(userFromDB.getPassword()))
        {
            return Optional.of(user);
        }
        
        return Optional.empty();
    }
}
