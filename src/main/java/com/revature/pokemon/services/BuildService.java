package com.revature.pokemon.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.revature.pokemon.dtos.requests.NewBuildRequest;
import com.revature.pokemon.dtos.responses.BuildResponse;
import com.revature.pokemon.entities.Ability;
import com.revature.pokemon.entities.Build;
import com.revature.pokemon.entities.Move;
import com.revature.pokemon.entities.Nature;
import com.revature.pokemon.entities.User;
import com.revature.pokemon.repositories.BuildRepository;
import com.revature.pokemon.utils.custom_exceptions.ResourceNotFoundException;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class BuildService {
    BuildRepository buildRepo;
    AbilityService abilityService;
    NatureService natureService;
    MoveService moveService;
    UserService userService;
    public Build create(NewBuildRequest req){ 
        Nature nature = natureService.findByName(req.getNatureName());
        Ability ability = abilityService.findByName(req.getAbilityName());
        Set<Move> moves = moveService.findByNames(req.getLearnedMoves());
        User user = userService.findById(req.getUserId());
        Build build = new Build(req.getName(), req.getDescription(), user, nature, ability, moves, req.getPokemonName());
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

    public List<Build> findAll1(){
        return buildRepo.findAll();
    }

    public void setTeam(String team_id, String id){
        buildRepo.updateTeam(team_id, id);
    }

    public List<BuildResponse> findAll2() {
        List<Build> builds = buildRepo.findAllBuildsWithPokemon();
        List<BuildResponse> responseList = new ArrayList<>();
        for(Build build : builds){
            responseList.add(new BuildResponse(build));
        }
        return responseList;
    }
}
