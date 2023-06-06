package com.revature.pokemon.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.revature.pokemon.entities.Team;
import com.revature.pokemon.repositories.TeamRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class TeamService {
    TeamRepository teamRepo;
    public Optional<Team> findById(String id){
        return teamRepo.findById(id);
    }
}
