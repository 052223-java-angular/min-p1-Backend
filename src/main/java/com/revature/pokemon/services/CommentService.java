package com.revature.pokemon.services;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.revature.pokemon.controllers.BuildController;
import com.revature.pokemon.dtos.requests.CommentDeleteRequest;
import com.revature.pokemon.dtos.requests.CommentVoteRequest;
import com.revature.pokemon.dtos.requests.ModifyCommentRequest;
import com.revature.pokemon.dtos.requests.NewCommenRequest;
import com.revature.pokemon.entities.Comment;
import com.revature.pokemon.entities.CommentVote;
import com.revature.pokemon.entities.Post;
import com.revature.pokemon.entities.User;
import com.revature.pokemon.repositories.CommentRepository;
import com.revature.pokemon.utils.custom_exceptions.PermissionException;
import com.revature.pokemon.utils.custom_exceptions.ResourceNotFoundException;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class CommentService {
    CommentRepository commentRepo;
    UserService userService;
    PostService postService;
    CommentVoteService commentVoteService;

    private static final Logger logger = LoggerFactory.getLogger(CommentService.class);

    public void create(NewCommenRequest req) {
        logger.info("Creating new comment");

        User user = userService.findById(req.getUserId());
        Post post = postService.findById(req.getPostId());
        Comment comment = new Comment(req.getComment(), post, user);
        commentRepo.save(comment);

        logger.info("Created new comment");
    }

    public Comment findById(String commentId) {
        logger.info("Finding comment by id");

        return commentRepo.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment (" + commentId +") not found!"));
    }

    public void vote(CommentVoteRequest req) {
        logger.info("Voting on comment");

        User user = userService.findById(req.getUserId());
        Comment comment = findById(req.getCommentId());
        CommentVote vote = commentVoteService.findByUserAndComment(user, comment);
        if(vote == null){
            vote = new CommentVote(user, comment ,req.isVote());
        }else{
            if(vote.isVote() == req.isVote()){
                commentVoteService.delete(vote);
                return;
            }else{
                vote.setVote(req.isVote());
            }
        }
        commentVoteService.vote(vote);

        logger.info("Voted on comment");

    }

    public void modify(ModifyCommentRequest req) {
        logger.info("Modifying comment");

        Comment comment = findById(req.getCommentId());

        if(!req.getUserId().equals(comment.getUser().getId())){
            throw new PermissionException("You do not have permission to make this change!");
        }

        comment.setComment(req.getComment());
        comment.setEdit_time(new Date(System.currentTimeMillis()));
        
        commentRepo.save(comment);

        logger.info("Modified comment");
    }

    public void delete(CommentDeleteRequest req) {
        logger.info("Deleting comment");

        Comment comment = findById(req.getCommentId());

        if(!req.getUserId().equals(comment.getUser().getId())){
            throw new PermissionException("You do not have permission to make this change!");
        }

        commentRepo.delete(comment);

        logger.info("Deleted comment");
    }
    
}
