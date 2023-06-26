package com.revature.pokemon.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.pokemon.dtos.requests.CommentDeleteRequest;
import com.revature.pokemon.dtos.requests.CommentVoteRequest;
import com.revature.pokemon.dtos.requests.ModifyCommentRequest;
import com.revature.pokemon.dtos.requests.NewCommentRequest;
import com.revature.pokemon.services.CommentService;
import com.revature.pokemon.services.TokenService;

import lombok.AllArgsConstructor;

@CrossOrigin
@AllArgsConstructor
@RestController
@RequestMapping("comment")
/**
 * The CommentController class handles HTTP requests related to comments.
 */
public class CommentController {
    private final CommentService commentService;
    private final TokenService tokenService;

    /**
     * The logger instance for logging messages related to CommentController.
     */
    private static final Logger logger = LoggerFactory.getLogger(CommentController.class);

    /**
     * Creates a new comment based on the provided request.
     *
     * @param req   the NewCommentRequest containing the comment information
     * @param token the authorization token
     * @return a ResponseEntity with the appropriate HTTP status
     */
    @PostMapping("/create")
    public ResponseEntity<?> createComment(@RequestBody NewCommentRequest req,
            @RequestHeader("Authorization") String token) {
        logger.info("Processing create request");

        tokenService.validateToken(token, req.getUserId());

        commentService.create(req);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * Votes on a comment based on the provided request.
     *
     * @param req   the CommentVoteRequest containing the vote information
     * @param token the authorization token
     * @return a ResponseEntity with the appropriate HTTP status
     */
    @PostMapping("/vote")
    public ResponseEntity<?> voteComment(@RequestBody CommentVoteRequest req,
            @RequestHeader("Authorization") String token) {
        logger.info("Processing vote request");

        tokenService.validateToken(token, req.getUserId());

        commentService.vote(req);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * Modifies an existing comment based on the provided request.
     *
     * @param req   the ModifyCommentRequest containing the comment information
     * @param token the authorization token
     * @return a ResponseEntity with the appropriate HTTP status
     */
    @PostMapping("/modify")
    public ResponseEntity<?> modifyComment(@RequestBody ModifyCommentRequest req,
            @RequestHeader("Authorization") String token) {
        logger.info("Processing modify request");

        tokenService.validateToken(token, req.getUserId());

        commentService.modify(req);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * Deletes a comment based on the provided request.
     *
     * @param req   the CommentDeleteRequest containing the comment ID and user ID
     * @param token the authorization token
     * @return a ResponseEntity with the appropriate HTTP status
     */
    @PostMapping("/delete")
    public ResponseEntity<?> deleteComment(@RequestBody CommentDeleteRequest req,
            @RequestHeader("Authorization") String token) {
        logger.info("Processing delete request");

        tokenService.validateToken(token, req.getUserId());

        commentService.delete(req);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
