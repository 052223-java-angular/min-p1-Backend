package com.revature.pokemon.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revature.pokemon.entities.Team;

public interface TeamRepository extends JpaRepository<Team, String>{
    Optional<Team> findById(String id);
}

