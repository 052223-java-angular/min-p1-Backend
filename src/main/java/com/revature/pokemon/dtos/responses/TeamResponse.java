package com.revature.pokemon.dtos.responses;

import java.util.Date;
import java.util.Set;

import com.revature.pokemon.entities.Build;
import com.revature.pokemon.entities.Team;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TeamResponse {
    private String id;
    private String name;
    private String description;
    private Date create_time;
    private Date edit_time;
    private Set<Build> builds;

    public TeamResponse(Team team) {
        this.id = team.getId();
        this.name = team.getName();
        this.description = team.getDescription();
        this.create_time = team.getCreate_time();
        this.edit_time = team.getEdit_time();
        this.builds = team.getBuilds();
    }
    
}
