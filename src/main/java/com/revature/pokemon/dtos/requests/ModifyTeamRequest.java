package com.revature.pokemon.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ModifyTeamRequest {
    private String userId;
    private String teamId;
    private String name;
    private String description;
    private String[] builds;
}
