/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.inholland.layers.presentation.model;

import java.util.ArrayList;
import java.util.List;
import nl.inholland.layers.model.User;

public class UserPresenter extends BasePresenter
{
    public List<User> present(List<User> users)
    {
        List<User> view;

        view = users;

        return view;
        
    }

    public User present(User user)
    {
        User view;
        
        view = user;
        
        return view;
    }
   
}
