package com.revature.pokemon.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.revature.pokemon.entities.Ability;
import com.revature.pokemon.repositories.AbilityRepository;
import com.revature.pokemon.utils.custom_exceptions.ResourceNotFoundException;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
/**
 * The AbilityService class provides methods for retrieving abilities.
 */
public class AbilityService {
    AbilityRepository abilityRepo;

    /**
     * The logger instance for logging messages related to AbilityService.
     */
    private static final Logger logger = LoggerFactory.getLogger(AbilityService.class);

    /**
     * Finds an ability by its name.
     *
     * @param name the name of the ability to be found
     * @return the Ability object with the specified name
     * @throws ResourceNotFoundException if the ability is not found
     */
    public Ability findByName(String name) throws ResourceNotFoundException {
        logger.info("Finding ability by name");

        return abilityRepo.findByNameIgnoreCase(name)
                .orElseThrow(() -> new ResourceNotFoundException("Ability (" + name + ") not found!"));
    }
}
