package com.revature.pokemon.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.revature.pokemon.controllers.BuildController;
import com.revature.pokemon.entities.Comment;
import com.revature.pokemon.entities.CommentVote;
import com.revature.pokemon.entities.User;
import com.revature.pokemon.repositories.CommentVoteRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class CommentVoteService {
    CommentVoteRepository commentVoteRepo;

    private static final Logger logger = LoggerFactory.getLogger(CommentVoteService.class);

    public void vote(CommentVote vote){
        commentVoteRepo.save(vote);
    }

    public CommentVote findById(String id){
        logger.info("Finding vote by id");

        return commentVoteRepo.findById(id).orElse(null);
    }

    public void delete(CommentVote vote) {
        commentVoteRepo.delete(vote);
    }

    public CommentVote findByUserAndComment(User user, Comment comment) {
        logger.info("Finding vote by user and comment");

        return commentVoteRepo.findByUserAndComment(user, comment).orElse(null);
    }
}
