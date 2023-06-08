package com.revature.pokemon.services;

import org.springframework.stereotype.Service;

import com.revature.pokemon.dtos.requests.NewPostRequest;
import com.revature.pokemon.entities.Post;
import com.revature.pokemon.entities.User;
import com.revature.pokemon.repositories.PostRepository;
import com.revature.pokemon.utils.custom_exceptions.ResourceNotFoundException;

import lombok.AllArgsConstructor;
@AllArgsConstructor
@Service
public class PostService {
    PostRepository postRepo;
    UserService userService;

    public void create(NewPostRequest req) {
        User user = userService.findById(req.getUserId());
        Post post = new Post(req.getPostTitle(), req.getMessage(), user);
        postRepo.save(post);
    }

    public Post findById(String postId) {
        return postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post (" + postId +") not found!"));
    }
    
}
