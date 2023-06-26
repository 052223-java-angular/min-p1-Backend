package com.revature.pokemon.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

public class TokenServiceTest {

    @InjectMocks
    TokenService tokenService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testExtractClaim() {
        // String SECRET_KEY = "fb29df36-13bc-11ee-be56-0242ac120002";

        // Principal userPrincipal = new Principal();
        // userPrincipal.setId("a real id");
        // userPrincipal.setRole("USER");
        // userPrincipal.setEmail("user@example.com");
        // userPrincipal.setUsername("username");

        // Map<String, Object> claims = new HashMap<>();
        // claims.put("id", userPrincipal.getId());
        // claims.put("role", userPrincipal.getRole());
        // claims.put("email", userPrincipal.getEmail());

        // Date issuedAt = new Date(System.currentTimeMillis());
        // Date expiration = new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10);

        // String expectedToken = Jwts.builder()
        // .setClaims(claims)
        // .setSubject(userPrincipal.getUsername())
        // .setIssuedAt(issuedAt)
        // .setExpiration(expiration)
        // .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
        // .compact();

        // String resultToken = tokenService.generateJWT(userPrincipal);

        // assertEquals(expectedToken, resultToken);
    }

    @Test
    void testExtractUserId() {

    }

    @Test
    void testGenerateJWT() {

    }

    @Test
    void testValidateToken() {

    }
}
