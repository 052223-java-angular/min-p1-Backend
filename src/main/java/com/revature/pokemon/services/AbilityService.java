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
public class AbilityService {
    AbilityRepository abilityRepo;

    private static final Logger logger = LoggerFactory.getLogger(AbilityService.class);

    public Ability findByName(String name){
        logger.info("Finding ability by name");

        return abilityRepo.findByName(name).orElseThrow(() -> new ResourceNotFoundException("Ability (" + name +") not found!"));
    }
}
