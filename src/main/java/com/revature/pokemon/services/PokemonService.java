package com.revature.pokemon.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.revature.pokemon.entities.Pokemon;
import com.revature.pokemon.repositories.PokemonRepository;
import com.revature.pokemon.utils.custom_exceptions.ResourceNotFoundException;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
/**
 * The PokemonService class provides methods for retrieving Pokémon.
 */
public class PokemonService {
    PokemonRepository pokemonRepo;

    /**
     * The logger instance for logging messages related to PokemonService.
     */
    private static final Logger logger = LoggerFactory.getLogger(PokemonService.class);

    /**
     * Finds a Pokémon by its name.
     *
     * @param name the name of the Pokémon to be found
     * @return the Pokemon object with the specified name
     * @throws ResourceNotFoundException if the Pokémon is not found
     */
    public Pokemon findByName(String name) throws ResourceNotFoundException {
        logger.info("Finding pokemon by name");

        return pokemonRepo.findByNameIgnoreCase(name)
                .orElseThrow(() -> new ResourceNotFoundException("Pokemon (" + name + ") not found!"));
    }
}
