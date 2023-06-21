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
import com.revature.pokemon.dtos.requests.NewCommenRequest;
import com.revature.pokemon.services.CommentService;
import com.revature.pokemon.services.TokenService;

import lombok.AllArgsConstructor;

@CrossOrigin
@AllArgsConstructor
@RestController
@RequestMapping("comment")
public class CommentController {
    private final CommentService commentService;
    private final TokenService tokenService;

    private static final Logger logger = LoggerFactory.getLogger(CommentController.class);
    static {
        logger.info("COMMENT path");
    }

    @PostMapping("/create")
    public ResponseEntity<?> createBuild(@RequestBody NewCommenRequest req,
            @RequestHeader("Authorization") String token) {
        logger.info("Processing create request");

        tokenService.validateToken(token, req.getUserId());

        commentService.create(req);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/vote")
    public ResponseEntity<?> votePost(@RequestBody CommentVoteRequest req,
            @RequestHeader("Authorization") String token) {
        logger.info("Processing vote request");

        tokenService.validateToken(token, req.getUserId());

        commentService.vote(req);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/modify")
    public ResponseEntity<?> modifyComment(@RequestBody ModifyCommentRequest req,
            @RequestHeader("Authorization") String token) {
        logger.info("Processing modify request");

        tokenService.validateToken(token, req.getUserId());

        commentService.modify(req);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteComment(@RequestBody CommentDeleteRequest req,
            @RequestHeader("Authorization") String token) {
        logger.info("Processing delete request");

        tokenService.validateToken(token, req.getUserId());

        commentService.delete(req);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
