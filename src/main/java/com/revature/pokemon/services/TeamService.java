package com.revature.pokemon.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.revature.pokemon.controllers.BuildController;
import com.revature.pokemon.dtos.requests.ModifyTeamRequest;
import com.revature.pokemon.dtos.requests.NewTeamRequest;
import com.revature.pokemon.dtos.requests.TeamDeleteRequest;
import com.revature.pokemon.dtos.responses.BuildResponse;
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
public class TeamService {
    TeamRepository teamRepo;
    BuildService buildService;
    UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(TeamService.class);

    public Team create(NewTeamRequest req){
        logger.info("Creating new team");

        User user = userService.findById(req.getUserId());
        Set<Build> builds = buildService.findByIds(req.getBuilds());
        Team team = new Team(req.getName(), req.getDescription(), user, builds);
        teamRepo.save(team);
        
        logger.info("Created new team");

        return team;
    }

    public Team findById(String id){
        logger.info("Finding team by id");

        return teamRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Team not found!"));
    }

    public void modify(ModifyTeamRequest req) {
        logger.info("Modifying team");

        Team team = findById(req.getTeamId());

        if(!req.getUserId().equals(team.getUser().getId())){
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

    public void delete(TeamDeleteRequest req) {
        logger.info("Deleting team");

        Team team = findById(req.getTeamId());

        if(!req.getUserId().equals(team.getUser().getId())){
            throw new PermissionException("You do not have permission to make this change!");
        }

        teamRepo.delete(team);

        logger.info("Deleted team");
    }

    public List<TeamResponse> findByUserId(String user_id) {
        logger.info("Fidning team by user id");

        List<Team> teams = teamRepo.findAllTeamsWithBuildsByUserId(user_id);
        List<TeamResponse> responseList = new ArrayList<>();
        
        for(Team team : teams){
            responseList.add(new TeamResponse(team));
        }
        return responseList;
    }

    public TeamResponse findTeamWithBuildsById(String id) {
        logger.info("Finding team by id");

        Team team = teamRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Team (" + id +") not found!"));
        return new TeamResponse(team);
    }
}
