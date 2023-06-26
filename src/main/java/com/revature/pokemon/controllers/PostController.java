package com.revature.pokemon.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
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

@CrossOrigin
@AllArgsConstructor
@RestController
@RequestMapping("/post")
/**
 * The PostController class handles HTTP requests related to posts.
 */
public class PostController {
    private final TokenService tokenService;
    private final PostService postService;

    /**
     * The logger instance for logging messages related to PostController.
     */
    private static final Logger logger = LoggerFactory.getLogger(PostController.class);

    static {
        logger.info("POST path");
    }

    /**
     * Creates a new post based on the provided request.
     *
     * @param req   the NewPostRequest containing the post details
     * @param token the authorization token
     * @return a ResponseEntity with the appropriate HTTP status
     */
    @PostMapping("/create")
    public ResponseEntity<?> createPost(@RequestBody NewPostRequest req, @RequestHeader("Authorization") String token) {
        logger.info("Processing create request");

        tokenService.validateToken(token, req.getUserId());

        postService.create(req);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * Votes on a post based on the provided request.
     *
     * @param req   the PostVoteRequest containing the vote details
     * @param token the authorization token
     * @return a ResponseEntity with the appropriate HTTP status
     */
    @PostMapping("/vote")
    public ResponseEntity<?> votePost(@RequestBody PostVoteRequest req, @RequestHeader("Authorization") String token) {
        logger.info("Processing vote request");

        tokenService.validateToken(token, req.getUserId());

        postService.vote(req);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * Modifies an existing post based on the provided request.
     *
     * @param req   the ModifyPostRequest containing the modified post details
     * @param token the authorization token
     * @return a ResponseEntity with the appropriate HTTP status
     */
    @PostMapping("/modify")
    public ResponseEntity<?> modifyPost(@RequestBody ModifyPostRequest req,
            @RequestHeader("Authorization") String token) {
        logger.info("Processing modify request");

        tokenService.validateToken(token, req.getUserId());

        postService.modify(req);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * Deletes a post based on the provided request.
     *
     * @param req   the PostDeleteRequest containing the post details to be deleted
     * @param token the authorization token
     * @return a ResponseEntity with the appropriate HTTP status
     */
    @PostMapping("/delete")
    public ResponseEntity<?> deletePost(@RequestBody PostDeleteRequest req,
            @RequestHeader("Authorization") String token) {
        logger.info("Processing delete request");

        tokenService.validateToken(token, req.getUserId());

        postService.delete(req);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * Retrieves all posts.
     *
     * @return a ResponseEntity with the list of PostResponse objects and the
     *         appropriate HTTP status
     */
    @GetMapping("/all")
    public ResponseEntity<List<PostResponse>> getAllPosts() {
        logger.info("Processing get all request");

        return ResponseEntity.status(HttpStatus.OK).body(postService.findAll());
    }

    /**
     * Retrieves a post by its ID.
     *
     * @param id the ID of the post to retrieve
     * @return a ResponseEntity with the PostResponse object and the appropriate
     *         HTTP status
     */
    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPostById(@PathVariable String id) {
        logger.info("Processing get by id request");

        return ResponseEntity.status(HttpStatus.OK).body(postService.findByIdResponse(id));
    }

    /**
     * Retrieves all posts by a user ID.
     *
     * @param user_id the ID of the user whose posts to retrieve
     * @return a ResponseEntity with the list of PostResponse objects and the
     *         appropriate HTTP status
     */
    @GetMapping("/")
    public ResponseEntity<List<PostResponse>> getPostsByUserId(@RequestParam String user_id) {
        logger.info("Processing get by user id request");

        return ResponseEntity.status(HttpStatus.OK).body(postService.findByUserId(user_id));
    }
}
