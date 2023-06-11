package com.revature.pokemon.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NewTeamRequest {
    private String userId;
    private String name;
    private String description;
    private String[] builds;
}
