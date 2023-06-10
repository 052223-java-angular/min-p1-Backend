package com.revature.pokemon.controllers;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.pokemon.dtos.requests.ModifyUserSignatureRequest;
import com.revature.pokemon.dtos.requests.NewLoginRequest;
import com.revature.pokemon.dtos.requests.NewUserRequest;
import com.revature.pokemon.dtos.responses.Principal;
import com.revature.pokemon.services.TokenService;
import com.revature.pokemon.services.UserService;
import com.revature.pokemon.utils.custom_exceptions.InvalidCredentialException;
import com.revature.pokemon.utils.custom_exceptions.InvalidTokenException;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/auth")
public class UserController {
    private final UserService userService;
    private final TokenService tokenService;

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

        userService.register(req);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<Principal> login(@RequestBody NewLoginRequest req){
        Optional<Principal> principalOpt = userService.login(req);
        if(principalOpt.isEmpty()){
            throw new InvalidCredentialException("No user with that combination of username and password found!");
        }

        return ResponseEntity.status(HttpStatus.OK).body(principalOpt.get());
    }

    @PostMapping("/ModifySignature")
    public ResponseEntity<Principal> modifySignature(@RequestBody ModifyUserSignatureRequest req){
        tokenService.validateToken(req.getToken(), req.getUserId());
        
        userService.modifySignature(req);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
