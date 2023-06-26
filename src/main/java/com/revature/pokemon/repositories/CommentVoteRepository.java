package com.revature.pokemon.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revature.pokemon.entities.Comment;
import com.revature.pokemon.entities.CommentVote;
import com.revature.pokemon.entities.User;

public interface CommentVoteRepository extends JpaRepository<CommentVote, String>{

    Optional<CommentVote> findByUserAndComment(User user, Comment comment);
    
}
