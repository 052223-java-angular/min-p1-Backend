package com.revature.pokemon.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revature.pokemon.entities.Comment;

public interface CommentRepository extends JpaRepository<Comment, String>{
    
}
