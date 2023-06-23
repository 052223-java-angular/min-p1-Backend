package com.revature.pokemon.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.pokemon.entities.Nature;

@Repository
public interface NatureRepository extends JpaRepository<Nature, String> {
    Optional<Nature> findByName(String name);

    Optional<Nature> findByNameIgnoreCase(String name);
}
