package com.revature.pokemon.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.pokemon.entities.Build;

@Repository
public interface BuildRepository extends JpaRepository<Build, String> {
    Optional<Build> findById(String id);
}
