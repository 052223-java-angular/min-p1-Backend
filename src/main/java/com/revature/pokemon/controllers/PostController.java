package com.revature.pokemon.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.pokemon.dtos.requests.NewPostRequest;
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
}
