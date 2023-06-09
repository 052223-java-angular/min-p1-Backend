package com.revature.pokemon.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.revature.pokemon.entities.Team;

public interface TeamRepository extends JpaRepository<Team, String>{
    Optional<Team> findById(String id);

    @Query("select t from Team t join fetch t.builds where t.user.id = :user_id")
    List<Team> findAllTeamsWithBuildsByUserId(String user_id);
}

