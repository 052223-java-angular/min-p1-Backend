package com.revature.pokemon.controllers;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.pokemon.dtos.requests.NewUserRequest;
import com.revature.pokemon.entities.User;
import com.revature.pokemon.services.UserService;
import com.revature.pokemon.utils.custom_exceptions.InvalidCredentialException;
import com.revature.pokemon.utils.custom_exceptions.ResourceConflictException;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody NewUserRequest req){
        // check unique username
        if(!userService.isUniqueUsername(req.getUsername())){
            throw new InvalidCredentialException("Username is not unique");
        }
        // check username is valid
        if(!userService.isValidUserName(req.getUsername())){
            throw new InvalidCredentialException("Username is not valid");
        }
        // check password
        if(!userService.isValidPassword(req.getPassword())){
            throw new InvalidCredentialException("Password is not valid");
        }

        // check confirmedPassword
        if(!userService.isSamePassword(req.getPassword(), req.getConfirmedPassword())){
            throw new InvalidCredentialException("Password does not match");
        }

        User newUser = userService.register(req);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @ExceptionHandler(InvalidCredentialException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidCredentialException(InvalidCredentialException e){
        Map<String, Object> map = new HashMap<>();
        map.put("timestamp", new Date(System.currentTimeMillis()));
        map.put("message", e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(map);
    }

}
