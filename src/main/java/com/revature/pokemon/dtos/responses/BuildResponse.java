package com.revature.pokemon.dtos.responses;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.revature.pokemon.entities.Build;
import com.revature.pokemon.entities.Move;
import com.revature.pokemon.entities.Team;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BuildResponse {
    private String id;
    private String name;
    private Date create_time;
    private Date edit_time;
    private String description;
    private Set<Move> moves;
    private Set<Team> teams;
    private String pokemonName;
    private String nature;
    private String ability;

    public BuildResponse(Build build){
        this.id = build.getId();
        this.name = build.getName();
        this.create_time = build.getCreate_time();
        this.edit_time = build.getEdit_time();
        this.description = build.getDescription();
        this.moves = build.getMoves();
        this.ability = build.getAbility().getName();
        this.nature = build.getNature().getName();
        this.pokemonName = build.getPokemonName();
    }
}
