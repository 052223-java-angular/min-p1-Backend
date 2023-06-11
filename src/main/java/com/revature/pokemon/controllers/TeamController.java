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

import com.revature.pokemon.dtos.requests.ModifyTeamRequest;
import com.revature.pokemon.dtos.requests.NewTeamRequest;
import com.revature.pokemon.dtos.requests.TeamDeleteRequest;
import com.revature.pokemon.dtos.responses.TeamResponse;
import com.revature.pokemon.services.TeamService;
import com.revature.pokemon.services.TokenService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/team")
public class TeamController {
    private final TeamService teamService;
    private final TokenService tokenService;

    private static final Logger logger = LoggerFactory.getLogger(TeamController.class);
    static{
        logger.info("TEAM path");
    }
    
    @PostMapping("/create")
    public ResponseEntity<?> createTeam(@RequestBody NewTeamRequest req, @RequestHeader ("Authorization") String token){
        logger.info("Processing create request");
        
        tokenService.validateToken(token, req.getUserId());
        
        teamService.create(req);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/modify")
    public ResponseEntity<?> modifyTeam(@RequestBody ModifyTeamRequest req, @RequestHeader ("Authorization") String token){
        logger.info("Processing modify request");

        tokenService.validateToken(token, req.getUserId());
        
        teamService.modify(req);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteTeam(@RequestBody TeamDeleteRequest req, @RequestHeader ("Authorization") String token){
        logger.info("Processing delete request");

        tokenService.validateToken(token, req.getUserId());
        
        teamService.delete(req);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeamResponse> getTeamById(@PathVariable String id){
        return ResponseEntity.status(HttpStatus.OK).body(teamService.findTeamWithBuildsById(id));
    }

    @GetMapping("/")
    public ResponseEntity<List<TeamResponse>> getTeamssByUserId(@RequestParam String user_id){
        return ResponseEntity.status(HttpStatus.OK).body(teamService.findByUserId(user_id));
    }
}
