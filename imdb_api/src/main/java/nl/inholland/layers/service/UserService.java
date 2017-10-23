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
import nl.inholland.layers.model.User;
import nl.inholland.layers.persistence.UserDAO;

public class UserService extends BaseService
{

    private final UserDAO userDAO;
    
    @Inject
    public UserService(UserDAO userDAO){
        this.userDAO = userDAO;
    }
    
    @GET
    @Path("{userId}")
    public User get(@PathParam("userId")String userId)
    {
        return null;
    }

    @GET
    public List<User> getAll()
    {
        List<User> users = new ArrayList<User>(userDAO.getAll());
        return users;
    }
    
}