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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.revature.pokemon.dtos.requests.BuildDeleteRequest;
import com.revature.pokemon.dtos.requests.ModifyBuildRequest;
import com.revature.pokemon.dtos.requests.NewBuildRequest;
import com.revature.pokemon.dtos.responses.BuildResponse;
import com.revature.pokemon.services.BuildService;
import com.revature.pokemon.services.TokenService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/build")
public class BuildController {
    private final BuildService buildService;
    private final TokenService tokenService;

    private static final Logger logger = LoggerFactory.getLogger(BuildController.class);
    static{
        logger.info("BUILD path");
    }

    @PostMapping("/create")
    public ResponseEntity<?> createBuild(@RequestBody NewBuildRequest req, @RequestHeader ("Authorization") String token){
        logger.info("Processing create request");

        tokenService.validateToken(token, req.getUserId());

        buildService.create(req);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/modify")
    public ResponseEntity<?> modifyBuild(@RequestBody ModifyBuildRequest req, @RequestHeader ("Authorization") String token){
        logger.info("Processing modify request");

        tokenService.validateToken(token, req.getUserId());

        buildService.modify(req);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteBuild(@RequestBody BuildDeleteRequest req, @RequestHeader ("Authorization") String token){
        logger.info("Processing delete request");

        tokenService.validateToken(token, req.getUserId());

        buildService.delete(req);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BuildResponse> getBuildsById(@PathVariable String id){
        logger.info("Processing get by id request");

        return ResponseEntity.status(HttpStatus.OK).body(buildService.findBuildsWithPokemonById(id));
    }

    @GetMapping("/")
    public ResponseEntity<List<BuildResponse>> getBuildsByUserId(@RequestParam String user_id){
        logger.info("Processing get by user id request");
        
        return ResponseEntity.status(HttpStatus.OK).body(buildService.findByUserId(user_id));
    }


}
