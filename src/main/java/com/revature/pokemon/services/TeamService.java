package com.revature.pokemon.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.revature.pokemon.dtos.requests.ModifyTeamRequest;
import com.revature.pokemon.dtos.requests.NewTeamRequest;
import com.revature.pokemon.dtos.requests.TeamDeleteRequest;
import com.revature.pokemon.dtos.responses.TeamResponse;
import com.revature.pokemon.entities.Build;
import com.revature.pokemon.entities.Team;
import com.revature.pokemon.entities.User;
import com.revature.pokemon.repositories.TeamRepository;
import com.revature.pokemon.utils.custom_exceptions.PermissionException;
import com.revature.pokemon.utils.custom_exceptions.ResourceNotFoundException;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
/**
 * The TeamService class provides methods for managing teams.
 */
public class TeamService {
    private final TeamRepository teamRepo;
    private final BuildService buildService;
    private final UserService userService;

    /**
     * The logger instance for logging messages related to TeamService.
     */
    private static final Logger logger = LoggerFactory.getLogger(TeamService.class);

    /**
     * Creates a new team based on the provided request.
     *
     * @param req the NewTeamRequest containing the team information
     * @return the created Team object
     */
    public Team create(NewTeamRequest req) {
        logger.info("Creating new team");

        User user = userService.findById(req.getUserId());
        Set<Build> builds = buildService.findByIds(req.getBuilds());
        Team team = new Team(req.getName(), req.getDescription(), user, builds);
        teamRepo.save(team);

        logger.info("Created new team");

        return team;
    }

    /**
     * Finds a team by its ID.
     *
     * @param id the ID of the team to be found
     * @return the Team object with the specified ID
     * @throws ResourceNotFoundException if the team is not found
     */
    public Team findById(String id) {
        logger.info("Finding team by id");

        return teamRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Team not found!"));
    }

    /**
     * Modifies a team based on the provided request.
     *
     * @param req the ModifyTeamRequest containing the team modifications
     */
    public void modify(ModifyTeamRequest req) {
        logger.info("Modifying team");

        Team team = findById(req.getTeamId());

        if (!req.getUserId().equals(team.getUser().getId())) {
            throw new PermissionException("You do not have permission to make this change!");
        }

        Set<Build> builds = buildService.findByIds(req.getBuilds());
        team.setBuilds(builds);
        team.setEdit_time(new Date(System.currentTimeMillis()));
        team.setName(req.getName());
        team.setDescription(req.getDescription());

        teamRepo.save(team);

        logger.info("Modified team");
    }

    /**
     * Deletes a team based on the provided request.
     *
     * @param req the TeamDeleteRequest containing the team ID and user ID
     */
    public void delete(TeamDeleteRequest req) {
        logger.info("Deleting team");

        Team team = findById(req.getTeamId());

        if (!req.getUserId().equals(team.getUser().getId())) {
            throw new PermissionException("You do not have permission to make this change!");
        }

        teamRepo.delete(team);

        logger.info("Deleted team");
    }

    /**
     * Finds all teams associated with the specified user ID.
     *
     * @param user_id the ID of the user
     * @return a list of TeamResponse objects representing the teams
     */
    public List<TeamResponse> findByUserId(String user_id) {
        logger.info("Finding team by user id");

        List<Team> teams = teamRepo.findAllTeamsWithBuildsByUserId(user_id);
        List<TeamResponse> responseList = new ArrayList<>();

        for (Team team : teams) {
            responseList.add(new TeamResponse(team));
        }
        return responseList;
    }

    /**
     * Finds a team with its associated builds by the specified ID.
     *
     * @param id the ID of the team
     * @return the TeamResponse object representing the team with builds
     */
    public TeamResponse findTeamWithBuildsById(String id) {
        logger.info("Finding team by id");

        Team team = teamRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Team (" + id + ") not found!"));
        return new TeamResponse(team);
    }
}
