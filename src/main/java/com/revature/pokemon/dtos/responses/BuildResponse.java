package com.revature.pokemon.dtos.responses;

import java.util.Date;

import com.revature.pokemon.entities.Build;
import com.revature.pokemon.entities.Pokemon;

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
    private Pokemon pokemon;
    private Date create_time;
    private Date edit_time;
    private String description;

    public BuildResponse(Build build){
        this.id =build.getId();
        this.name = build.getName();
        this.pokemon = build.getPokemon();
        this.create_time = build.getCreate_time();
        this.edit_time = build.getEdit_time();
        this.description = build.getDescription();
    }
}
