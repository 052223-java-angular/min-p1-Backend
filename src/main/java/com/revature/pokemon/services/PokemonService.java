package com.revature.pokemon.services;

import org.springframework.stereotype.Service;

import com.revature.pokemon.entities.Pokemon;
import com.revature.pokemon.repositories.PokemonRepository;
import com.revature.pokemon.utils.custom_exceptions.ResourceNotFoundException;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class PokemonService {
    PokemonRepository pokemonRepo;

    public Pokemon findByName(String name){
        return pokemonRepo.findByName(name).orElseThrow(() -> new ResourceNotFoundException("Pokemon (" + name +") not found!"));
    }

}
