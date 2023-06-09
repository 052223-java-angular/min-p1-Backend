package com.revature.pokemon.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revature.pokemon.entities.Post;

public interface PostRepository extends JpaRepository<Post, String>{

    List<Post> findAllByUserId(String user_id);
    
}
