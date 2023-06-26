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
/**
 * The RoleService class provides methods for managing roles.
 */
public class RoleService {
    private final RoleRepository roleRepo;

    /**
     * The logger instance for logging messages related to RoleService.
     */
    private static final Logger logger = LoggerFactory.getLogger(RoleService.class);

    /**
     * Finds a role by its name.
     *
     * @param name the name of the role to be found
     * @return the Role object with the specified name
     * @throws ResourceNotFoundException if the role is not found
     */
    public Role findByName(String name) {
        logger.info("Finding role");

        return roleRepo.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found!"));
    }
}
