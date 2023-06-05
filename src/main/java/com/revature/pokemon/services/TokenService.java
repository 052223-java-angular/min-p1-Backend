package com.revature.pokemon.services;

import java.util.Date;

import com.revature.pokemon.dtos.responses.Principal;

import io.jsonwebtoken.Jwts;

public class TokenService {
    
    public String generateJWT(Principal principal){
        String jws = Jwts.builder()
            .setIssuer("Pokemon")
            .setSubject(principal.getRole().getName())
            .setAudience(principal.getUsername())
            .setExpiration(new Date(System.currentTimeMillis() + 60*60*1000)) //a java.util.Date
            .setIssuedAt(new Date()) // for example, now
            .setId(principal.getId()) //just an example id
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .compact();

        return jws;
    }
}
