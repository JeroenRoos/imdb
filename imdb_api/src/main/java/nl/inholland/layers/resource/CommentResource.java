/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.inholland.layers.resource;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import nl.inholland.layers.model.Comment;
import nl.inholland.layers.model.CommentView;
import nl.inholland.layers.presentation.model.CommentPresenter;
import nl.inholland.layers.service.CommentService;

@Path("/comments")
@Consumes (MediaType.APPLICATION_JSON)
@Produces (MediaType.APPLICATION_JSON)
public class CommentResource extends BaseResource{
    
    private final CommentService commentService;
    private final CommentPresenter commentPresenter;
    
    @Inject
    public CommentResource( CommentService commentService,
            CommentPresenter commentPresenter){
        this.commentService = commentService;
        this.commentPresenter = commentPresenter;
    }
    
    //get all
    @GET
    public List<CommentView> getAll(){
        
        List<Comment> comments = commentService.getAll();
        
        return commentPresenter.present(comments);
      
    }
    
    @POST
    public void create(List<Comment> comments)
    {
        if (comments.size() == 1)
            commentService.create(comments.get(0));
        else
            commentService.createMany(comments);
    }
        
      @PUT
     @Path("/{id}")
    public void update(@PathParam("id") String commentIds, Comment comment) //@PathParam
    {
        String[] ids = commentIds.split(",");
        
         if (ids.length == 1)
             commentService.update(ids[0], comment);
         else
            commentService.updateMany(ids, comment);
    }  
    //get by Id
    @GET
    @Path("/{CommentId}")
    public CommentView get( @PathParam("CommentId") String commentId){
        Comment comment = commentService.get(commentId);
        
        return commentPresenter.present(comment);
    }

     @DELETE
    public void delete(@DefaultValue("") @QueryParam("id") String commentIds) 
    {     
        String[] ids = commentIds.split(",");
        
        if (ids.length == 1)
            commentService.delete(ids[0]);
        else
            commentService.deleteMany(ids);
    }
}
