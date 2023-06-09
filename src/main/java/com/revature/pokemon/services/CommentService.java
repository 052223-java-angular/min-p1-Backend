package com.revature.pokemon.services;

import org.springframework.stereotype.Service;

import com.revature.pokemon.dtos.requests.CommentVoteRequest;
import com.revature.pokemon.dtos.requests.NewCommenRequest;
import com.revature.pokemon.entities.Comment;
import com.revature.pokemon.entities.CommentVote;
import com.revature.pokemon.entities.Post;
import com.revature.pokemon.entities.User;
import com.revature.pokemon.repositories.CommentRepository;
import com.revature.pokemon.utils.custom_exceptions.ResourceNotFoundException;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class CommentService {
    CommentRepository commentRepo;
    UserService userService;
    PostService postService;
    CommentVoteService commenVoteService;

    public void create(NewCommenRequest req) {
        User user = userService.findById(req.getUserId());
        Post post = postService.findById(req.getPostId());
        Comment comment = new Comment(req.getComment(), post, user);
        commentRepo.save(comment);
    }

    public Comment findById(String commentId) {
        return commentRepo.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment (" + commentId +") not found!"));
    }

    public void vote(CommentVoteRequest req) {
        User user = userService.findById(req.getUserId());
        Comment comment = findById(req.getCommentId());
        CommentVote vote = commenVoteService.findByUserAndComment(user, comment);
        if(vote == null){
            vote = new CommentVote(user, comment ,req.isVote());
        }else{
            if(vote.isVote() == req.isVote()){
                commenVoteService.delete(vote);
                return;
            }else{
                vote.setVote(req.isVote());
            }
        }
        commenVoteService.vote(vote);
    }
    
}
