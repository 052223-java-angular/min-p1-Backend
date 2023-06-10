package com.revature.pokemon.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.revature.pokemon.controllers.BuildController;
import com.revature.pokemon.entities.Pokemon;
import com.revature.pokemon.repositories.PokemonRepository;
import com.revature.pokemon.utils.custom_exceptions.ResourceNotFoundException;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class PokemonService {
    PokemonRepository pokemonRepo;

    private static final Logger logger = LoggerFactory.getLogger(PokemonService.class);

    public Pokemon findByName(String name){
        logger.info("Finding pokemon by name");

        return pokemonRepo.findByName(name).orElseThrow(() -> new ResourceNotFoundException("Pokemon (" + name +") not found!"));
    }

}
