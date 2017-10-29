/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.inholland.layers.presentation.model;

import java.util.ArrayList;
import java.util.List;
import nl.inholland.layers.model.User;
import nl.inholland.layers.model.UserView;

public class UserPresenter extends BasePresenter
{
    public List<UserView> present (List<User> users)
    {
        List<UserView> view = new ArrayList<>();
        for (User user : users)
        {
            UserView userView = initUserView(user);
            view.add(userView);
        }
        return view;
    }
    
    public UserView present(User user)
    {
        UserView userView = initUserView(user);
        return userView;
    }
    
    private UserView initUserView(User user)
    {
        UserView userView = new UserView();
        
        userView.setId(user.getId());
        userView.setName(user.getName());
        userView.setGender(user.getGender());
        userView.setIsAdmin(user.getIsAdmin());
        
        return userView;
    }
}
