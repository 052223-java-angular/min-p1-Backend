package com.revature.pokemon.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.revature.pokemon.dtos.requests.NewBuildRequest;
import com.revature.pokemon.entities.Build;
import com.revature.pokemon.entities.Pokemon;
import com.revature.pokemon.entities.User;
import com.revature.pokemon.repositories.BuildRepository;
import com.revature.pokemon.utils.custom_exceptions.ResourceNotFoundException;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class BuildService {
    BuildRepository buildRepo;
    PokemonService pokemonService;
    UserService userService;
    public Build create(NewBuildRequest req){ 
        
        Pokemon pokemon = pokemonService.create(req.getName(), req.getNatureName(), req.getAbilityName(), req.getLearnedMoves());
        User user = userService.findById(req.getUserId());

        Build build = new Build(req.getName(), req.getDescription(), user, pokemon);
        buildRepo.save(build);
        return build;
    }

    public Build findById(String id) {
        return buildRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Build (" + id +") not found!"));
    }

    public Set<Build> findByIds(String[] ids) {
        Set<Build> builds = new HashSet<>();
        for(String id : ids){
            Build b = findById(id);
            builds.add(b);
        }
        return builds;
    }

    public List<Build> findAll(){
        return buildRepo.findAll();
    }

    public void setTeam(String team_id, String id){
        buildRepo.updateTeam(team_id, id);
    }
}
