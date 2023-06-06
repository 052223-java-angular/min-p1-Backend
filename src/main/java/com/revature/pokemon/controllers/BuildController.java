package com.revature.pokemon.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.pokemon.dtos.requests.NewBuildRequest;
import com.revature.pokemon.services.BuildService;
import com.revature.pokemon.services.TokenService;
import com.revature.pokemon.utils.custom_exceptions.InvalidTokenException;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/build")
public class BuildController {
    private final BuildService buildService;
    private final TokenService tokenService;

    @PostMapping("/create")
    public ResponseEntity<?> createBuild(@RequestBody NewBuildRequest req){
        if(!tokenService.validateToken(req.getToken(), req.getUserId())){
            throw new InvalidTokenException("Token is invalid or expired");
        }
        buildService.create(req);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
