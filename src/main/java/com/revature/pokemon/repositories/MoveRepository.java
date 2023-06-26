package com.revature.pokemon.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.pokemon.entities.Move;

@Repository
public interface MoveRepository extends JpaRepository<Move, String> {
    Optional<Move> findByName(String name);

    Optional<Move> findByNameIgnoreCase(String name);
}
