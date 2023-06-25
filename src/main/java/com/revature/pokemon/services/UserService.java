package com.revature.pokemon.services;

import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.revature.pokemon.dtos.requests.ModifyUserSignatureRequest;
import com.revature.pokemon.dtos.requests.NewLoginRequest;
import com.revature.pokemon.dtos.requests.NewUserRequest;
import com.revature.pokemon.dtos.responses.Principal;
import com.revature.pokemon.entities.Role;
import com.revature.pokemon.entities.User;
import com.revature.pokemon.repositories.UserRepository;
import com.revature.pokemon.utils.custom_exceptions.ResourceNotFoundException;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepo;
    private final RoleService roleService;
    private final TokenService tokenService;

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public boolean isUniqueUsername(String username) {
        logger.info("Verifying unique username");

        Optional<User> userOpt = userRepo.findByUsername(username);
        return userOpt.isEmpty();
    }

    public boolean isValidUserName(String username) {
        logger.info("Verifying valid username");

        return username.matches("^(?=[a-zA-Z0-9._]{8,20}$)(?!.*[_.]{2})[^_.].*[^_.]$");
    }

    public boolean isValidPassword(String password) {
        logger.info("Verifying valid password");

        return password.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$");
    }

    public boolean isSamePassword(String password, String confirmedPassword) {
        logger.info("Verifying same password");

        return password.equals(confirmedPassword);
    }

    public User register(NewUserRequest req) {
        logger.info("Creating new user");

        Role role = roleService.findByName("USER");
        String hashed = BCrypt.hashpw(req.getPassword(), BCrypt.gensalt());
        User newUser = new User(req.getUsername(), hashed, req.getEmail(), role);
        userRepo.save(newUser);

        logger.info("Created new user");

        return newUser;
    }

    public Optional<Principal> login(NewLoginRequest req) {
        logger.info("Logging in");

        Optional<User> userOpt = userRepo.findByUsername(req.getUsername());

        if (userOpt.isEmpty() || !BCrypt.checkpw(req.getPassword(), userOpt.get().getPassword())) {
            return Optional.empty();
        }
        Principal principal = new Principal(userOpt.get());
        principal.setToken(tokenService.generateJWT(principal));

        logger.info("login sucess");
        return Optional.of(principal);

    }

    public User findById(String id) {
        logger.info("Finding user by id");

        return userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found!"));
    }

    public void modifySignature(ModifyUserSignatureRequest req) {
        logger.info("Modifying signature");

        User user = findById(req.getUserId());
        user.setSignature(req.getSignature());
        userRepo.save(user);

        logger.info("Modified signature");
    }
}
