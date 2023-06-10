package com.revature.pokemon.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.revature.pokemon.dtos.requests.ModifyPostRequest;
import com.revature.pokemon.dtos.requests.NewPostRequest;
import com.revature.pokemon.dtos.requests.PostDeleteRequest;
import com.revature.pokemon.dtos.requests.PostVoteRequest;
import com.revature.pokemon.dtos.responses.PostResponse;
import com.revature.pokemon.services.PostService;
import com.revature.pokemon.services.TokenService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/post")
public class PostController {
    private final TokenService tokenService;
    private final PostService postService;

    private static final Logger logger = LoggerFactory.getLogger(PostController.class);
    static{
        logger.info("POST path");
    }

    @PostMapping("/create")
    public ResponseEntity<?> createPost(@RequestBody NewPostRequest req){
        logger.info("Processing create request");

        tokenService.validateToken(req.getToken(), req.getUserId());
        
        postService.create(req);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/vote")
    public ResponseEntity<?> votePost(@RequestBody PostVoteRequest req){
        logger.info("Processing vote request");

        tokenService.validateToken(req.getToken(), req.getUserId());
        
        postService.vote(req);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/modify")
    public ResponseEntity<?> modifyPost(@RequestBody ModifyPostRequest req){
        logger.info("Processing modify request");

        tokenService.validateToken(req.getToken(), req.getUserId());
        
        postService.modify(req);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deletePost(@RequestBody PostDeleteRequest req){
        logger.info("Processing delete request");

        tokenService.validateToken(req.getToken(), req.getUserId());
        
        postService.delete(req);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<PostResponse>> getAllPosts(){
        logger.info("Processing get all request");

        return ResponseEntity.status(HttpStatus.OK).body(postService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPostById(@PathVariable String id){
        logger.info("Processing get by id request");

        return ResponseEntity.status(HttpStatus.OK).body(postService.findByIdResponse(id));
    }

    @GetMapping("/")
    public ResponseEntity<List<PostResponse>> getPostsByUserId(@RequestParam String user_id){
        logger.info("Processing get by user id request");

        return ResponseEntity.status(HttpStatus.OK).body(postService.findByUserId(user_id));
    }
}
