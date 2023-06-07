package com.revature.pokemon.services;

import java.util.Set;

import org.springframework.stereotype.Service;

import com.revature.pokemon.entities.Ability;
import com.revature.pokemon.entities.Move;
import com.revature.pokemon.entities.Nature;
import com.revature.pokemon.entities.Pokemon;
import com.revature.pokemon.repositories.PokemonRepository;
import com.revature.pokemon.utils.custom_exceptions.ResourceNotFoundException;

import lombok.AllArgsConstructor;
@AllArgsConstructor
@Service
public class PokemonService {
    PokemonRepository pokemonRepo;
    NatureService natureService;
    AbilityService abilityService;
    MoveService moveService;
    public Pokemon create(String name, String nature_name, String ability_name, String[] move_set){
        Nature nature = natureService.findByName(nature_name);
        Ability ability = abilityService.findByName(ability_name);
        Set<Move> learned_moves = moveService.findByNames(move_set);
        Pokemon pokemon = new Pokemon(name, nature, ability, learned_moves);
        pokemonRepo.save(pokemon);
        return pokemon;
    }

    public Pokemon findById(String id){
        return pokemonRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Pokemon (" + id +") not found!"));
    }
}
