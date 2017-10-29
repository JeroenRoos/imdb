/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.inholland.layers.persistence;

import java.util.List;
import javax.inject.Inject;
import nl.inholland.layers.model.Comment;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

/**
 *
 * @author Jeroen
 */
public class CommentDAO extends BaseDAO<Comment> 
{
    private Datastore ds;
    
    @Inject
    public CommentDAO(Datastore ds)
    {
        super(Comment.class, ds);
        this.ds = ds;
    }    
    public void deleteManyById(List<ObjectId> lstObjects) {
        Query<Comment> query = ds.createQuery(Comment.class);
        ds.delete(query.filter("_id in", lstObjects));
    }
}
