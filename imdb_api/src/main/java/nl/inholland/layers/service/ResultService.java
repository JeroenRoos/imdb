/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.inholland.layers.service;

import javax.inject.Singleton;
import javax.ws.rs.NotFoundException;

@Singleton
public class ResultService {
    
    public void requireResult(Object obj, String message) throws NotFoundException
    {
        if (obj == null)
            throw new NotFoundException(message);
    }
}
