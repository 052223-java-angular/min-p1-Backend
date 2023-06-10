package com.revature.pokemon.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revature.pokemon.entities.Pokemon;

public interface PokemonRepository extends JpaRepository<Pokemon, String>{

    Optional<Pokemon> findByName(String name);
    
}
