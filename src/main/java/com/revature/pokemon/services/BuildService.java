package com.revature.pokemon.services;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.revature.pokemon.controllers.BuildController;
import com.revature.pokemon.dtos.requests.BuildDeleteRequest;
import com.revature.pokemon.dtos.requests.ModifyBuildRequest;
import com.revature.pokemon.dtos.requests.NewBuildRequest;
import com.revature.pokemon.dtos.responses.BuildResponse;
import com.revature.pokemon.entities.Ability;
import com.revature.pokemon.entities.Build;
import com.revature.pokemon.entities.Move;
import com.revature.pokemon.entities.Nature;
import com.revature.pokemon.entities.Pokemon;
import com.revature.pokemon.entities.User;
import com.revature.pokemon.repositories.BuildRepository;
import com.revature.pokemon.utils.custom_exceptions.PermissionException;
import com.revature.pokemon.utils.custom_exceptions.ResourceNotFoundException;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class BuildService {
    BuildRepository buildRepo;
    AbilityService abilityService;
    NatureService natureService;
    PokemonService pokemonService;
    MoveService moveService;
    UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(BuildService.class);

    public Build create(NewBuildRequest req){ 
        logger.info ("Creating build");

        Nature nature = natureService.findByName(req.getNatureName());
        Ability ability = abilityService.findByName(req.getAbilityName());
        Pokemon pokemon = pokemonService.findByName(req.getPokemonName());
        Set<Move> moves = moveService.findByNames(req.getLearnedMoves());
        User user = userService.findById(req.getUserId());
        Build build = new Build(req.getName(), req.getDescription(), user, nature, ability, moves, pokemon);
        buildRepo.save(build);

        logger.info ("Processed create request");

        return build;
    }

    public Build findById(String id) {
        logger.info ("Finding build by id");

        return buildRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Build (" + id +") not found!"));
    }

    public Set<Build> findByIds(String[] ids) {
        logger.info ("Finding builds by id");

        Set<Build> builds = new HashSet<>();
        for(String id : ids){
            Build b = findById(id);
            builds.add(b);
        }
        return builds;
    }

    public List<Build> findAll1(){
        logger.info ("Finding all builds");

        return buildRepo.findAll();
    }

    public List<BuildResponse> findAll2() {
        logger.info ("Finding all builds with pokemon");

        List<Build> builds = buildRepo.findAllBuildsWithPokemon();
        List<BuildResponse> responseList = new ArrayList<>();
        for(Build build : builds){
            responseList.add(new BuildResponse(build));
        }
        return responseList;
    }

    public List<BuildResponse> findByUserId(String user_id) {
        logger.info ("Finding builds by user id");

        List<Build> builds = buildRepo.findAllBuildsWithPokemonByUserId(user_id);
        List<BuildResponse> responseList = new ArrayList<>();
        for(Build build : builds){
            responseList.add(new BuildResponse(build));
        }

        logger.info ("Builds found");

        return responseList;
    }

    public BuildResponse findBuildsWithPokemonById(String id) {
        logger.info ("Finding builds with pokemon by id");

        Build build = buildRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Build (" + id +") not found!"));
        
        logger.info ("Build found");
        return new BuildResponse(build);
    }

    public void modify(ModifyBuildRequest req) {
        logger.info ("Modifying build");

        Nature nature = natureService.findByName(req.getNatureName());
        Ability ability = abilityService.findByName(req.getAbilityName());
        Pokemon pokemon = pokemonService.findByName(req.getPokemonName());
        Set<Move> moves = moveService.findByNames(req.getLearnedMoves());
        Build build = findById(req.getBuildId());

        if(!req.getUserId().equals(build.getUser().getId())){
            throw new PermissionException("You do not have permission to make this change!");
        }

        build.setName(req.getName());
        build.setDescription(req.getDescription());
        build.setAbility(ability);
        build.setNature(nature);
        build.setPokemon(pokemon);
        build.setMoves(moves);
        build.setEdit_time(new Date(System.currentTimeMillis()));

        buildRepo.save(build);

        logger.info ("Modified build");
    }

    public void delete(BuildDeleteRequest req) {
        logger.info ("Deleting build");

        Build build = findById(req.getBuildId());

        if(!req.getUserId().equals(build.getUser().getId())){
            throw new PermissionException("You do not have permission to make this change!");
        }

        buildRepo.delete(build);

        logger.info ("Deleted build");
    }
}
