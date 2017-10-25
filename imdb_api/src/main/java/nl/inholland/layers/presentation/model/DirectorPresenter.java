/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.inholland.layers.presentation.model;

import java.util.ArrayList;
import java.util.List;
import nl.inholland.layers.model.Director;
import nl.inholland.layers.model.DirectorView;

/**
 *
 * @author Jeroen
 */
public class DirectorPresenter extends BasePresenter
{
    public List<DirectorView> present (List<Director> lstDirectors)
    {
        List<DirectorView> view = new ArrayList<>();
        for (Director director : lstDirectors)
        {
            DirectorView directorView = initDirectorView(director);
            view.add(directorView);
        }
        return view;
    }
    
    public DirectorView present(Director director)
    {
        DirectorView directorView = initDirectorView(director);
        return directorView;
    }
    
    private DirectorView initDirectorView(Director director)
    {
        DirectorView directorView = new DirectorView();
        
        directorView.setFirstName(director.getFirstName());
        directorView.setLastName(director.getLastName());
        directorView.setAge(director.getAge());
        
        return directorView;
    }
    
}
