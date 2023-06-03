package com.revature.pokemon.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.revature.pokemon.entities.User;
import com.revature.pokemon.repositories.UserRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepo;

    public boolean isUniqueUsername(String username){
        Optional<User> userOpt = userRepo.findByUsername(username);
        return userOpt.isEmpty();
    }
}
