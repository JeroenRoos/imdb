/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.inholland.layers.service;

import nl.inholland.health.Helpers.ErrorHandler;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import nl.inholland.health.Helpers.Range;
import nl.inholland.layers.model.EntityModel;
import nl.inholland.layers.persistence.BaseDAO;
import org.bson.types.ObjectId;


@Singleton
public class BaseService <T extends EntityModel>
{
    @Inject ErrorHandler errorHandler;
    private BaseDAO baseDAO;

    
    //get all
    public List<T> getAll()
    {
        List<T> objects;
            objects = baseDAO.getAll();

        return objects;
    }
     
    //get by id
    public T getById(String objectId)
    {
        T object = null;
        try{
           
           object = (T) baseDAO.get(objectId);
           
        }catch(Exception e){
            
           errorHandler.noValidObjectId(objectId + " is not a valid id");       

        }
        return object;
    }
     
    //delete    
        public void delete(String objectId)
    {
        ObjectId objectIdConverted;
        if (ObjectId.isValid(objectId))
        {
            objectIdConverted = new ObjectId(objectId);
            baseDAO.deleteById(objectIdConverted);
        }
        else
           errorHandler.noValidObjectId(objectId + " is not a valid id");
    }
    
        
    //delete many
    public void deleteMany(String[] ids)
    {
        List<ObjectId> lstObjectIds = new ArrayList<>();
        
        for (int i = 0; i < ids.length; i++)
        {
            if (ObjectId.isValid(ids[i]))
            {
                ObjectId objectId = new ObjectId(ids[i]);
                lstObjectIds.add(objectId);
            }
            else
                
          errorHandler.noValidObjectId("One or more id's are not valid");
        }
        
        baseDAO.deleteMany(lstObjectIds);
    }

    //set range
    public Range setRange(String min, String max){
        
        return new Range(min, max);
    }
}
