/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.inholland.layers.presentation.model;

import java.util.ArrayList;
import java.util.List;
import nl.inholland.layers.model.Comment;
import nl.inholland.layers.model.CommentView;
import nl.inholland.layers.model.UserView;

/**
 *
 * @author Jeroen
 */
public class CommentPresenter extends BasePresenter
{
    private final UserPresenter userPresenter = new UserPresenter();
    
    public List<CommentView> present (List<Comment> comments)
    {
        List<CommentView> view = new ArrayList<>();
        for (Comment comment : comments)
        {
            CommentView commentView = initCommentView(comment);
            view.add(commentView);
        }
        return view;
    }
    
    public CommentView present(Comment comment)
    {
        CommentView commentView = initCommentView(comment);
        return commentView;
    }
    
    private CommentView initCommentView(Comment comment)
    {
        CommentView commentView = new CommentView();
        
        commentView.setId(comment.getId());
        commentView.setMessage(comment.getMessage());     
        commentView.setUser(userPresenter.present(comment.getUser()));
        
        return commentView;
    }
    
}
