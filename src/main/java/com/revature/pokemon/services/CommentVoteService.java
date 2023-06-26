package com.revature.pokemon.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.revature.pokemon.entities.Comment;
import com.revature.pokemon.entities.CommentVote;
import com.revature.pokemon.entities.User;
import com.revature.pokemon.repositories.CommentVoteRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
/**
 * The CommentVoteService class provides methods for managing comment votes.
 */
public class CommentVoteService {
    CommentVoteRepository commentVoteRepo;

    /**
     * The logger instance for logging messages related to CommentVoteService.
     */
    private static final Logger logger = LoggerFactory.getLogger(CommentVoteService.class);

    /**
     * Saves a comment vote.
     *
     * @param vote the CommentVote object to be saved
     */
    public void vote(CommentVote vote) {
        commentVoteRepo.save(vote);
    }

    /**
     * Finds a comment vote by its ID.
     *
     * @param id the ID of the comment vote to be found
     * @return the CommentVote object with the specified ID, or null if not found
     */
    public CommentVote findById(String id) {
        logger.info("Finding vote by id");

        return commentVoteRepo.findById(id).orElse(null);
    }

    /**
     * Deletes a comment vote.
     *
     * @param vote the CommentVote object to be deleted
     */
    public void delete(CommentVote vote) {
        commentVoteRepo.delete(vote);
    }

    /**
     * Finds a comment vote by user and comment.
     *
     * @param user    the User object associated with the vote
     * @param comment the Comment object associated with the vote
     * @return the CommentVote object matching the user and comment, or null if not
     *         found
     */
    public CommentVote findByUserAndComment(User user, Comment comment) {
        logger.info("Finding vote by user and comment");

        return commentVoteRepo.findByUserAndComment(user, comment).orElse(null);
    }
}
