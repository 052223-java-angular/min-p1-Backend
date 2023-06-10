package com.revature.pokemon.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.revature.pokemon.controllers.BuildController;
import com.revature.pokemon.entities.Nature;
import com.revature.pokemon.repositories.NatureRepository;
import com.revature.pokemon.utils.custom_exceptions.ResourceNotFoundException;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class NatureService {
    NatureRepository natureRepo;

    private static final Logger logger = LoggerFactory.getLogger(NatureService.class);

    public Nature findByName(String name){
        logger.info("Finding nature by name");

        return natureRepo.findByName(name).orElseThrow(() -> new ResourceNotFoundException("Nature (" + name +") not found!"));
    }
}
