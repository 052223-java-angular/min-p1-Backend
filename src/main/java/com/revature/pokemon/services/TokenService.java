package com.revature.pokemon.services;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.revature.pokemon.dtos.responses.Principal;
import com.revature.pokemon.utils.custom_exceptions.InvalidTokenException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenService {

    private static final Logger logger = LoggerFactory.getLogger(TokenService.class);

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    /**
     * Generates a JWT token based on the provided user principal.
     *
     * @param userPrincipal The user principal.
     * @return The generated JWT token.
     */
    public String generateJWT(Principal userPrincipal) {
        logger.info("Generating new JWT token");

        Map<String, Object> claims = new HashMap<>();
        claims.put("id", userPrincipal.getId());
        claims.put("role", userPrincipal.getRole());
        claims.put("email", userPrincipal.getEmail());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userPrincipal.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // Set expiration time to 10 hours
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

        /**
     * Validates the provided JWT token against the user id.
     *
     * @param token         The JWT token to validate.
     * @param id The user id to compare against.
     */
    public void validateToken(String token, String id) {
        logger.info("Validating token");
        extractClaim(token, Claims::getExpiration);

        boolean idMatch = extractUserId(token).equals(id);

        if(!idMatch){
            throw new InvalidTokenException("Invalid Token");
        }
        logger.info("Validation sucess");
    }

    /**
     * Extracts a claim from the JWT token using the provided claims resolver
     * function.
     *
     * @param token          The JWT token.
     * @param claimsResolver The claims resolver function.
     * @param <T>            The type of the claim.
     * @return The extracted claim.
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        logger.info("Extracting claim");

        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Extracts all claims from the JWT token.
     *
     * @param token The JWT token.
     * @return The extracted claims.
     */
    private Claims extractAllClaims(String token) {
        logger.info("Extracting all claim");

        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    /**
     * Extracts the user ID from the JWT token.
     *
     * @param token The JWT token.
     * @return The extracted user ID.
     */
    public String extractUserId(String token) {
        logger.info("Extracting user id");

        return (String) extractAllClaims(token).get("id");
    }
}