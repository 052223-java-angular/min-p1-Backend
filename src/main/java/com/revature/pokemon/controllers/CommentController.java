package com.revature.pokemon.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.pokemon.dtos.requests.CommentDeleteRequest;
import com.revature.pokemon.dtos.requests.CommentVoteRequest;
import com.revature.pokemon.dtos.requests.ModifyCommentRequest;
import com.revature.pokemon.dtos.requests.ModifyPostRequest;
import com.revature.pokemon.dtos.requests.NewCommenRequest;
import com.revature.pokemon.dtos.requests.PostDeleteRequest;
import com.revature.pokemon.services.CommentService;
import com.revature.pokemon.services.TokenService;
import com.revature.pokemon.utils.custom_exceptions.InvalidTokenException;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("comment")
public class CommentController {
    private final CommentService commentService;
    private final TokenService tokenService;

    @PostMapping("/create")
    public ResponseEntity<?> createBuild(@RequestBody NewCommenRequest req){
        if(!tokenService.validateToken(req.getToken(), req.getUserId())){
            throw new InvalidTokenException("Token is invalid or expired");
        }
        commentService.create(req);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/vote")
    public ResponseEntity<?> votePost(@RequestBody CommentVoteRequest req){
        if(!tokenService.validateToken(req.getToken(), req.getUserId())){
            throw new InvalidTokenException("Token is invalid or expired");
        }
        commentService.vote(req);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/modify")
    public ResponseEntity<?> modifyComment(@RequestBody ModifyCommentRequest req){
        if(!tokenService.validateToken(req.getToken(), req.getUserId())){
            throw new InvalidTokenException("Token is invalid or expired");
        }
        commentService.modify(req);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteComment(@RequestBody CommentDeleteRequest req){
        if(!tokenService.validateToken(req.getToken(), req.getUserId())){
            throw new InvalidTokenException("Token is invalid or expired");
        }
        commentService.delete(req);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
