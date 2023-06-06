package com.revature.pokemon.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.pokemon.entities.Pokemon;
@Repository
public interface PokemonRepository extends JpaRepository<Pokemon, String>{
    Optional<Pokemon> findById(String id);
}
