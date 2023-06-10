package com.revature.pokemon.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.revature.pokemon.entities.Role;
import com.revature.pokemon.repositories.RoleRepository;
import com.revature.pokemon.utils.custom_exceptions.ResourceNotFoundException;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class RoleService {
    private final RoleRepository roleRepo;

    private static final Logger logger = LoggerFactory.getLogger(RoleService.class);

    public Role findByName(String name){
        logger.info("Finding role");

        return roleRepo.findByName(name).orElseThrow(() -> new ResourceNotFoundException("Role not found!"));
    }
}
