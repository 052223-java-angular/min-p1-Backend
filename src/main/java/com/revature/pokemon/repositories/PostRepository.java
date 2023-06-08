package com.revature.pokemon.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revature.pokemon.entities.Post;

public interface PostRepository extends JpaRepository<Post, String>{
    
}
