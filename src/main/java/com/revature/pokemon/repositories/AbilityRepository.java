package com.revature.pokemon.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.pokemon.entities.Ability;

@Repository
public interface AbilityRepository extends JpaRepository<Ability, String> {
    Optional<Ability> findByName(String name);

    Optional<Ability> findByNameIgnoreCase(String name);
}
