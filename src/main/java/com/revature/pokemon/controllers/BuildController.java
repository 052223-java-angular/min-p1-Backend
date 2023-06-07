package com.revature.pokemon.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.revature.pokemon.dtos.requests.GetBuildRequest;
import com.revature.pokemon.dtos.requests.NewBuildRequest;
import com.revature.pokemon.entities.Build;
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

    @GetMapping("/{id}")
    public ResponseEntity<Build> getAllBuilds(@PathVariable String id){
        return ResponseEntity.status(HttpStatus.OK).body(buildService.findById(id));
    }

    @GetMapping("/all1")
    public ResponseEntity<List<Build>> getAllBuilds1(){
        return ResponseEntity.status(HttpStatus.OK).body(buildService.findAll1());
    }

    @GetMapping("/all2")
    public ResponseEntity<List<Build>> getAllBuilds2(){
        return ResponseEntity.status(HttpStatus.OK).body(buildService.findAll2());
    }

}
