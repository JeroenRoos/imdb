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
    public AuthenticationService(UserDAO userDAO)
    {
        this.userDAO = userDAO;
    }
    
    // Authenticates a user with the credentials and returns the user
    @Override
    public Optional<User> authenticate(BasicCredentials credentials) throws AuthenticationException
    {      
       // Get the user from the database with the User Credentials
        User userFromDB = userDAO.getByCredentials(credentials.getUsername(), credentials.getPassword());
        
        // Checks if the user exists, if not, the user will see an error message
        if(userFromDB == null)
        {
            return Optional.empty();
        }
        else
        {
            return Optional.of(userFromDB);
        }
                
    }
}
