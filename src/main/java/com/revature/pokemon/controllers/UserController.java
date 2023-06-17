package com.revature.pokemon.controllers;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.pokemon.dtos.requests.ModifyUserSignatureRequest;
import com.revature.pokemon.dtos.requests.NewLoginRequest;
import com.revature.pokemon.dtos.requests.NewUserRequest;
import com.revature.pokemon.dtos.responses.Principal;
import com.revature.pokemon.dtos.responses.TeamResponse;
import com.revature.pokemon.entities.User;
import com.revature.pokemon.services.TokenService;
import com.revature.pokemon.services.UserService;
import com.revature.pokemon.utils.custom_exceptions.InvalidCredentialException;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("/auth")
public class UserController {
    private final UserService userService;
    private final TokenService tokenService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    static {
        logger.info("AUTH path");
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody NewUserRequest req) {
        logger.info("Processing new user request");

        if (!userService.isUniqueUsername(req.getUsername())) {
            throw new InvalidCredentialException("Username is not unique");
        }

        if (!userService.isValidUserName(req.getUsername())) {
            throw new InvalidCredentialException("Username is not valid");
        }

        if (!userService.isValidPassword(req.getPassword())) {
            throw new InvalidCredentialException("Password is not valid");
        }

        if (!userService.isSamePassword(req.getPassword(), req.getConfirmedPassword())) {
            throw new InvalidCredentialException("Password does not match");
        }

        userService.register(req);

        logger.info("Processed new user request");

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<Principal> login(@RequestBody NewLoginRequest req) {
        logger.info("Processing login request");

        Optional<Principal> principalOpt = userService.login(req);
        if (principalOpt.isEmpty()) {
            throw new InvalidCredentialException("No user with that combination of username and password found!");
        }

        logger.info("Processed login request");

        return ResponseEntity.status(HttpStatus.OK).body(principalOpt.get());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getTeamById(@PathVariable String id, @RequestHeader("Authorization") String token) {
        logger.info("Processing get user request");

        tokenService.validateToken(token, id);

        logger.info("Processed get user request");

        return ResponseEntity.status(HttpStatus.OK).body(userService.findById(id));
    }

    @PostMapping("/ModifySignature")
    public ResponseEntity<Principal> modifySignature(@RequestBody ModifyUserSignatureRequest req,
            @RequestHeader("Authorization") String token) {
        logger.info("Processing modify signature request");

        tokenService.validateToken(token, req.getUserId());

        userService.modifySignature(req);

        logger.info("Processed modify signature request");

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
