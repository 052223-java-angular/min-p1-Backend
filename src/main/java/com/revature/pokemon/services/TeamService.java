package com.revature.pokemon.services;

import java.util.Set;

import org.springframework.stereotype.Service;

import com.revature.pokemon.dtos.requests.NewTeamRequest;
import com.revature.pokemon.entities.Build;
import com.revature.pokemon.entities.Team;
import com.revature.pokemon.entities.User;
import com.revature.pokemon.repositories.TeamRepository;
import com.revature.pokemon.utils.custom_exceptions.ResourceNotFoundException;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class TeamService {
    TeamRepository teamRepo;
    BuildService buildService;
    UserService userService;

    public Team create(NewTeamRequest req){
        User user = userService.findById(req.getUserId());
        Set<Build> builds = buildService.findByIds(req.getBuilds());
        System.out.println(builds.size());
        Team team = new Team(req.getName(), req.getDescription(), user, builds);
        teamRepo.save(team);
        
        return team;
    }

    public Team findById(String id){
        return teamRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Team not found!"));
    }
}
