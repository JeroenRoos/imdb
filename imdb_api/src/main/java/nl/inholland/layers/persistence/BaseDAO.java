/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.inholland.layers.persistence;

/**
 *
 * @author youp
 */
import java.util.List;
import javax.inject.Singleton;
import nl.inholland.layers.model.EntityModel;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;

/**
 *
 * @author youp
 */

@Singleton
public abstract class BaseDAO< T extends EntityModel > extends BasicDAO< T, ObjectId>
{
    private Datastore ds;
    
    public BaseDAO( Class < T > entityClass, Datastore ds){
        super(entityClass, ds);
        this.ds = ds;
    }
    
    public T get( String id){
        return get(new ObjectId(id));
    }
        
    public List<T> getAll(){
        return find().asList();
    }
    
    public void create( T obj){
        ds.save(obj);
    }
    
    public void createMany( List<T> objects){
        ds.save(objects);
    }
        
    public void update(T obj){
        save(obj);
    }
    
    public void updateMany(List<T> lstObjects)
    {
        for (T o : lstObjects)
            save(o);
    }

}
