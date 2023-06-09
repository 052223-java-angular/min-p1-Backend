package com.revature.pokemon.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NewUserRequest {
    private String username;
    private String password;
    private String email;
    private String confirmedPassword;
}
