package com.revature.pokemon.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ModifyBuildRequest {
    private String token;
    private String userId;
    private String buildId;
    private String name;
    private String pokemonName;
    private String natureName;
    private String abilityName;
    private String description;
    private String[] learnedMoves;
}
