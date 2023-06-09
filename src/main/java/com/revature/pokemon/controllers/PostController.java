package com.revature.pokemon.controllers;

import org.hibernate.event.spi.PostDeleteEvent;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.pokemon.dtos.requests.BuildDeleteRequest;
import com.revature.pokemon.dtos.requests.ModifyBuildRequest;
import com.revature.pokemon.dtos.requests.ModifyPostRequest;
import com.revature.pokemon.dtos.requests.NewPostRequest;
import com.revature.pokemon.dtos.requests.PostDeleteRequest;
import com.revature.pokemon.dtos.requests.PostVoteRequest;
import com.revature.pokemon.services.PostService;
import com.revature.pokemon.services.TokenService;
import com.revature.pokemon.utils.custom_exceptions.InvalidTokenException;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/post")
public class PostController {
    private final TokenService tokenService;
    private final PostService postService;

    @PostMapping("/create")
    public ResponseEntity<?> createPost(@RequestBody NewPostRequest req){
        if(!tokenService.validateToken(req.getToken(), req.getUserId())){
            throw new InvalidTokenException("Token is invalid or expired");
        }
        postService.create(req);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/vote")
    public ResponseEntity<?> votePost(@RequestBody PostVoteRequest req){
        if(!tokenService.validateToken(req.getToken(), req.getUserId())){
            throw new InvalidTokenException("Token is invalid or expired");
        }
        postService.vote(req);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/modify")
    public ResponseEntity<?> modifyPost(@RequestBody ModifyPostRequest req){
        if(!tokenService.validateToken(req.getToken(), req.getUserId())){
            throw new InvalidTokenException("Token is invalid or expired");
        }
        postService.modify(req);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deletePost(@RequestBody PostDeleteRequest req){
        if(!tokenService.validateToken(req.getToken(), req.getUserId())){
            throw new InvalidTokenException("Token is invalid or expired");
        }
        postService.delete(req);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
