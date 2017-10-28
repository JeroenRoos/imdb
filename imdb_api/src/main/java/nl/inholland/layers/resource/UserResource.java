/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.inholland.layers.resource;

import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import nl.inholland.layers.presentation.model.UserPresenter;
import nl.inholland.layers.service.UserService;
import nl.inholland.layers.model.User;
import nl.inholland.layers.model.UserView;


@Path("/users")
@Consumes (MediaType.APPLICATION_JSON)
@Produces (MediaType.APPLICATION_JSON)
public class UserResource extends BaseResource
{
    private final UserService userService;
    private final UserPresenter userPresenter;
    
    @Inject
    public UserResource( UserService userService,
            UserPresenter userPresenter){
        this.userService = userService;
        this.userPresenter = userPresenter;
    }
    
    @GET
    public List<UserView> getAll(@DefaultValue("") @QueryParam("gender") String gender,
            @DefaultValue("") @QueryParam("name") String name,
            @DefaultValue("") @QueryParam("isAdmin") String isAdmin){
        /*if(!"".equals(gender)){
            List<User> users = userService.getByGender(gender);
            
            return userPresenter.present(users);
        }else{
            List<User> users = userService.getAll();
        
            return userPresenter.present(users);
        }*/
        List<User> users;
        if (!"".equals(gender) || !"".equals(name) || !"".equals(isAdmin))
             users = userService.getByParameters(name, gender, isAdmin);
        else
             users = userService.getAll();
            
        
        return userPresenter.present(users);
    }
    
    @GET
    @Path("/{userId}")
    public UserView get( @PathParam("userId") String userId){
        User user = userService.get(userId);
        
        return userPresenter.present(user);
    }
    
    @POST
    public void create(List<User> users){
        if (users.size() == 1)
            userService.create(users.get(0));
        else
            userService.createMany(users);
    }
}

