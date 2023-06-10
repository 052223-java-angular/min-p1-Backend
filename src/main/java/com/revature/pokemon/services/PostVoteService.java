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
public class PostVoteService {
    PostVoteRepository postVoteRepo;

    private static final Logger logger = LoggerFactory.getLogger(PostVoteService.class);

    public void vote(PostVote vote){
        postVoteRepo.save(vote);
    }

    public PostVote findById(String id){
        logger.info("Finding vote by id");

        return postVoteRepo.findById(id).orElse(null);
    }

    public void delete(PostVote vote) {
        postVoteRepo.delete(vote);
    }

    public PostVote findByUserAndPost(User user, Post post) {
        logger.info("Finding post by user and post");

        return postVoteRepo.findByUserAndPost(user, post).orElse(null);
    }
}
