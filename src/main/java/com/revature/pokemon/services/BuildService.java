package com.revature.pokemon.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.revature.pokemon.dtos.requests.NewBuildRequest;
import com.revature.pokemon.entities.Build;
import com.revature.pokemon.entities.Pokemon;
import com.revature.pokemon.entities.Team;
import com.revature.pokemon.entities.User;
import com.revature.pokemon.repositories.BuildRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class BuildService {
    BuildRepository buildRepo;
    PokemonService pokemonService;
    UserService userService;
    TeamService teamService;
    public Build create(NewBuildRequest req){ 
        
        Pokemon pokemon = pokemonService.create(req.getName(), req.getNatureName(), req.getAbilityName());
        User user = userService.getUserById(req.getUserId());

        Build build = new Build(req.getName(), req.getDescription(), user, pokemon);
        buildRepo.save(build);
        return build;
    }
}
