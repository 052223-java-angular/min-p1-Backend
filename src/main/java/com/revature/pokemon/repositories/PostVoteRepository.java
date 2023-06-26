package com.revature.pokemon.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revature.pokemon.entities.Post;
import com.revature.pokemon.entities.PostVote;
import com.revature.pokemon.entities.User;

public interface PostVoteRepository extends JpaRepository<PostVote, String>{

    Optional<PostVote> findByUserAndPost(User user, Post post);
    
}
