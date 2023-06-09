package com.revature.pokemon.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.revature.pokemon.entities.Build;

import jakarta.transaction.Transactional;

@Repository
public interface BuildRepository extends JpaRepository<Build, String> {
    Optional<Build> findById(String id);

    @Modifying
    @Transactional
    @Query(value = "update builds set team_id = ?1 where id = ?2", nativeQuery = true)
    void updateTeam(String team_id, String id);

    @Query("select b from Build b join fetch b.ability a join fetch b.nature")
    List<Build> findAllBuildsWithPokemon();

    @Query("select b from Build b join fetch b.ability a join fetch b.nature where b.user.id = :user_id")
    List<Build> findAllBuildsWithPokemonByUserId(@Param("user_id") String user_id);
    
}
