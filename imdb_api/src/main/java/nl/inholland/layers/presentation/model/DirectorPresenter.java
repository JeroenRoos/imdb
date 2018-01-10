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

// The presentation class for the Directors
// This class is used to present the results 
public class DirectorPresenter extends BasePresenter
{
    // Present multiple Directors
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
    
    
    // Present one Director
    public DirectorView present(Director director)
    {
        DirectorView directorView = initDirectorView(director);
        return directorView;
    }
    
    // Initialize the DirectorView and give it all the proper values
    private DirectorView initDirectorView(Director director)
    {
        DirectorView directorView = new DirectorView();
        
        directorView.setId(director.getId());
        directorView.setFirstName(director.getFirstName());
        directorView.setLastName(director.getLastName());
        directorView.setAge(director.getAge());
        
        return directorView;
    }
    
}
