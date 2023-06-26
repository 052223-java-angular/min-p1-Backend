package com.revature.pokemon.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.revature.pokemon.entities.Post;
import com.revature.pokemon.entities.PostVote;
import com.revature.pokemon.entities.User;
import com.revature.pokemon.repositories.PostVoteRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
/**
 * The PostVoteService class provides methods for managing post votes.
 */
public class PostVoteService {
    PostVoteRepository postVoteRepo;

    /**
     * The logger instance for logging messages related to PostVoteService.
     */
    private static final Logger logger = LoggerFactory.getLogger(PostVoteService.class);

    /**
     * Saves a post vote.
     *
     * @param vote the PostVote object to be saved
     */
    public void vote(PostVote vote) {
        postVoteRepo.save(vote);
    }

    /**
     * Finds a post vote by its ID.
     *
     * @param id the ID of the post vote to be found
     * @return the PostVote object with the specified ID, or null if not found
     */
    public PostVote findById(String id) {
        logger.info("Finding vote by id");

        return postVoteRepo.findById(id).orElse(null);
    }

    /**
     * Deletes a post vote.
     *
     * @param vote the PostVote object to be deleted
     */
    public void delete(PostVote vote) {
        postVoteRepo.delete(vote);
    }

    /**
     * Finds a post vote by user and post.
     *
     * @param user the User object representing the voter
     * @param post the Post object representing the post
     * @return the PostVote object associated with the specified user and post, or
     *         null if not found
     */
    public PostVote findByUserAndPost(User user, Post post) {
        logger.info("Finding post vote by user and post");

        return postVoteRepo.findByUserAndPost(user, post).orElse(null);
    }
}
