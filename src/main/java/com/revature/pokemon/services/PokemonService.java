package com.revature.pokemon.services;

import org.springframework.stereotype.Service;

import com.revature.pokemon.entities.Ability;
import com.revature.pokemon.entities.Nature;
import com.revature.pokemon.entities.Pokemon;
import com.revature.pokemon.repositories.PokemonRepository;

import lombok.AllArgsConstructor;
@AllArgsConstructor
@Service
public class PokemonService {
    PokemonRepository pokemonRepo;
    NatureService natureService;
    AbilityService abilityService;
    public Pokemon create(String name, String nature_name, String ability_name){
        Nature nature = natureService.findByName(nature_name);
        Ability ability = abilityService.findByName(ability_name);

        Pokemon pokemon = new Pokemon(name, nature, ability);
        pokemonRepo.save(pokemon);
        return pokemon;
    }
}
