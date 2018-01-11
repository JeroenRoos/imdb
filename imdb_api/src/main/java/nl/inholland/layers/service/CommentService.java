/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.inholland.layers.service;
import java.util.List;
import javax.inject.Inject;
import nl.inholland.layers.model.Comment;
import nl.inholland.layers.persistence.CommentDAO;
import org.bson.types.ObjectId;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

/**
 *
 * @author CTiel
 */
public class CommentService extends BaseService {

    private final CommentDAO commentDAO;

    @Inject
    public CommentService(CommentDAO commentDAO){
        this.commentDAO = commentDAO;
    }
    
    public Comment getCommentById(String commentID)
    {
        Comment comment = (Comment) super.getById(commentID, commentDAO);
        return comment;
    }
    
    public List<Comment> getAll()
    {
        List<Comment> comments = super.getAll(commentDAO);
        
        return comments;
    }     
        
    public void update(String commentID, Comment comment)
    {
        ObjectId objectId;
        if (ObjectId.isValid(commentID))
        {
            objectId = new ObjectId(commentID);
            Query myQuery = commentDAO.createQuery().field("_id").equal(objectId);
            UpdateOperations<Comment> update = commentDAO.createUpdateOperations();

            checkUpdateValidity(update, comment, objectId);          
            commentDAO.update(myQuery, update);
        }
        else
            super.errorHandler.noValidObjectId("The comment id is not valid");
    }
    
    
    public void updateMany(String[] ids, Comment comment)
    {
        for (int i = 0; i < ids.length; i++)
        {
            if (ObjectId.isValid(ids[i]))
            {
                ObjectId objectId = new ObjectId(ids[i]);
                
                Query myQuery = commentDAO.createQuery().field("_id").equal(objectId);
                UpdateOperations<Comment> update = commentDAO.createUpdateOperations();
                checkUpdateValidity(update, comment, objectId); 
                commentDAO.update(myQuery, update);
            }
            else
                super.errorHandler.noValidObjectId("The comment id is not valid");
        }
        
    }
    
    //check if input is valid
        private void checkUpdateValidity(UpdateOperations<Comment> update, Comment comment, ObjectId id)
    {
        if (!"".equals(comment.getMessage()) && comment.getMessage() != null)
            update.set("message", comment.getMessage());
        else if ("".equals(comment.getMessage()))
            super.errorHandler.emptyField("message cannot be an empty string");

        if (!"".equals(comment.getUser()) && comment.getUser() != null)
            update.set("user", comment.getUser());
        else if ("".equals(comment.getUser()))
           super.errorHandler.emptyField("user cannot be empty");
    }
     private void checkCreateValidity(Comment comment)
    {
        if ("".equals(comment.getMessage()) || comment.getMessage() == null)
            super.errorHandler.emptyField("Firstname cannot be an empty string");
        
        if ("".equals(comment.getUser()) || comment.getUser() == null)
            super.errorHandler.emptyField("Lastname cannot be an empty string");
    }
     
    public void create(Comment comment)
    {
        checkCreateValidity(comment);
        commentDAO.create(comment);
    }

    public void createMany(List<Comment> comments)
    {
        for (Comment comment : comments)
            checkCreateValidity(comment);
        
        commentDAO.createMany(comments);
    }
    
    
    public void delete(String commentId)
    {
        super.delete(commentId, commentDAO);
    }

    public void deleteMany(String[] ids)
    {
        super.deleteMany(ids, commentDAO);
    }
}
