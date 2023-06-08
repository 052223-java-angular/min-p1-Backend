package com.revature.pokemon.services;

import org.springframework.stereotype.Service;

import com.revature.pokemon.dtos.requests.NewCommenRequest;
import com.revature.pokemon.entities.Comment;
import com.revature.pokemon.entities.Post;
import com.revature.pokemon.entities.User;
import com.revature.pokemon.repositories.CommentRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class CommentService {
    CommentRepository commentRepo;
    UserService userService;
    PostService postService;

    public void create(NewCommenRequest req) {
        User user = userService.findById(req.getUserId());
        Post post = postService.findById(req.getPostId());
        Comment comment = new Comment(req.getComment(), post, user);
        commentRepo.save(comment);
    }
    
}
