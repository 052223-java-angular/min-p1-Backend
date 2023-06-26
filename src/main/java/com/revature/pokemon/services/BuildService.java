package com.revature.pokemon.services;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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
/**
 * The BuildService class provides methods for managing builds.
 */
public class BuildService {
    BuildRepository buildRepo;
    AbilityService abilityService;
    NatureService natureService;
    PokemonService pokemonService;
    MoveService moveService;
    UserService userService;

    /**
     * The logger instance for logging messages related to BuildService.
     */
    private static final Logger logger = LoggerFactory.getLogger(BuildService.class);

    /**
     * Creates a new build based on the provided request.
     *
     * @param req the NewBuildRequest containing build information
     * @return the created Build object
     * @throws ResourceNotFoundException if any of the required resources (nature,
     *                                   ability, pokemon, moves, user) are not
     *                                   found
     */
    public Build create(NewBuildRequest req) throws ResourceNotFoundException {
        logger.info("Creating build");

        Nature nature = natureService.findByName(req.getNatureName());
        Ability ability = abilityService.findByName(req.getAbilityName());
        Pokemon pokemon = pokemonService.findByName(req.getPokemonName());
        Set<Move> moves = moveService.findByNames(req.getLearnedMoves());
        User user = userService.findById(req.getUserId());
        Build build = new Build(req.getName(), req.getDescription(), user, nature, ability, moves, pokemon);
        buildRepo.save(build);

        logger.info("Processed create request");

        return build;
    }

    /**
     * Finds a build by its ID.
     *
     * @param id the ID of the build to be found
     * @return the Build object with the specified ID
     * @throws ResourceNotFoundException if the build is not found
     */
    public Build findById(String id) throws ResourceNotFoundException {
        logger.info("Finding build by id");

        return buildRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Build (" + id + ") not found!"));
    }

    /**
     * Finds a set of builds by their IDs.
     *
     * @param ids an array of build IDs
     * @return a set of Build objects with the specified IDs
     * @throws ResourceNotFoundException if any of the builds are not found
     */
    public Set<Build> findByIds(String[] ids) throws ResourceNotFoundException {
        logger.info("Finding builds by id");

        Set<Build> builds = new HashSet<>();
        for (String id : ids) {
            Build b = findById(id);
            builds.add(b);
        }
        return builds;
    }

    /**
     * Finds builds by user ID.
     *
     * @param user_id the ID of the user associated with the builds
     * @return a list of BuildResponse objects representing the builds
     */
    public List<BuildResponse> findByUserId(String user_id) {
        logger.info("Finding builds by user id");

        List<Build> builds = buildRepo.findAllBuildsWithPokemonByUserId(user_id);
        List<BuildResponse> responseList = new ArrayList<>();
        for (Build build : builds) {
            responseList.add(new BuildResponse(build));
        }

        logger.info("Builds found");

        return responseList;
    }

    /**
     * Finds a BuildResponse object with Pokemon information by its ID.
     *
     * @param id the ID of the build to be found
     * @return the BuildResponse object with the specified ID
     * @throws ResourceNotFoundException if the build is not found
     */
    public BuildResponse findBuildsWithPokemonById(String id) throws ResourceNotFoundException {
        logger.info("Finding builds with pokemon by id");

        Build build = buildRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Build (" + id + ") not found!"));

        logger.info("Build found");
        return new BuildResponse(build);
    }

    /**
     * Modifies a build based on the provided request.
     *
     * @param req the ModifyBuildRequest containing build modification information
     * @throws ResourceNotFoundException if any of the required resources (nature,
     *                                   ability, pokemon, moves, build) are not
     *                                   found
     * @throws PermissionException       if the user does not have permission to
     *                                   modify the build
     */
    public void modify(ModifyBuildRequest req) throws ResourceNotFoundException, PermissionException {
        logger.info("Modifying build");

        Nature nature = natureService.findByName(req.getNatureName());
        Ability ability = abilityService.findByName(req.getAbilityName());
        Pokemon pokemon = pokemonService.findByName(req.getPokemonName());
        Set<Move> moves = moveService.findByNames(req.getLearnedMoves());
        Build build = findById(req.getBuildId());

        if (!req.getUserId().equals(build.getUser().getId())) {
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

        logger.info("Modified build");
    }

    /**
     * Deletes a build based on the provided request.
     *
     * @param req the BuildDeleteRequest containing build deletion information
     * @throws ResourceNotFoundException if the build is not found
     * @throws PermissionException       if the user does not have permission to
     *                                   delete the build
     */
    public void delete(BuildDeleteRequest req) throws ResourceNotFoundException, PermissionException {
        logger.info("Deleting build");

        Build build = findById(req.getBuildId());

        if (!req.getUserId().equals(build.getUser().getId())) {
            throw new PermissionException("You do not have permission to make this change!");
        }

        buildRepo.delete(build);

        logger.info("Deleted build");
    }
}
