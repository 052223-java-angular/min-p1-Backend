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
/**
 * The UserService class provides methods for user management and
 * authentication.
 */
public class UserService {
    private final UserRepository userRepo;
    private final RoleService roleService;
    private final TokenService tokenService;

    /**
     * The logger instance for logging messages related to UserService.
     */
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    /**
     * Checks if the provided username is unique.
     *
     * @param username the username to be checked
     * @return true if the username is unique, false otherwise
     */
    public boolean isUniqueUsername(String username) {
        logger.info("Verifying unique username");

        Optional<User> userOpt = userRepo.findByUsername(username);
        return userOpt.isEmpty();
    }

    /**
     * Checks if the provided username is valid.
     *
     * @param username the username to be checked
     * @return true if the username is valid, false otherwise
     */
    public boolean isValidUserName(String username) {
        logger.info("Verifying valid username");

        return username.matches("^(?=[a-zA-Z0-9._]{8,20}$)(?!.*[_.]{2})[^_.].*[^_.]$");
    }

    /**
     * Checks if the provided password is valid.
     *
     * @param password the password to be checked
     * @return true if the password is valid, false otherwise
     */
    public boolean isValidPassword(String password) {
        logger.info("Verifying valid password");

        return password.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$");
    }

    /**
     * Checks if the provided password and confirmed password are the same.
     *
     * @param password          the password to be checked
     * @param confirmedPassword the confirmed password to be checked
     * @return true if the passwords are the same, false otherwise
     */
    public boolean isSamePassword(String password, String confirmedPassword) {
        logger.info("Verifying same password");

        return password.equals(confirmedPassword);
    }

    /**
     * Registers a new user based on the provided request.
     *
     * @param req the NewUserRequest containing the user information
     * @return the created User object
     */
    public User register(NewUserRequest req) {
        logger.info("Creating new user");

        Role role = roleService.findByName("USER");
        String hashed = BCrypt.hashpw(req.getPassword(), BCrypt.gensalt());
        User newUser = new User(req.getUsername(), hashed, req.getEmail(), role);
        userRepo.save(newUser);

        logger.info("Created new user");

        return newUser;
    }

    /**
     * Logs in a user based on the provided login request.
     *
     * @param req the NewLoginRequest containing the login information
     * @return an Optional containing the authenticated Principal if login is
     *         successful, empty otherwise
     */
    public Optional<Principal> login(NewLoginRequest req) {
        logger.info("Logging in");

        Optional<User> userOpt = userRepo.findByUsername(req.getUsername());

        if (userOpt.isEmpty() || !BCrypt.checkpw(req.getPassword(), userOpt.get().getPassword())) {
            return Optional.empty();
        }
        Principal principal = new Principal(userOpt.get());
        principal.setToken(tokenService.generateJWT(principal));

        logger.info("Login success");
        return Optional.of(principal);
    }

    /**
     * Finds a user by the specified ID.
     *
     * @param id the ID of the user
     * @return the User object representing the user
     * @throws ResourceNotFoundException if the user is not found
     */
    public User findById(String id) {
        logger.info("Finding user by id");

        return userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found!"));
    }

    /**
     * Modifies the signature of a user based on the provided request.
     *
     * @param req the ModifyUserSignatureRequest containing the user ID and the new
     *            signature
     */
    public void modifySignature(ModifyUserSignatureRequest req) {
        logger.info("Modifying signature");

        User user = findById(req.getUserId());
        user.setSignature(req.getSignature());
        userRepo.save(user);

        logger.info("Modified signature");
    }
}
