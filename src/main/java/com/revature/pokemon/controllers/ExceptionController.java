package com.revature.pokemon.controllers;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.revature.pokemon.utils.custom_exceptions.InvalidCredentialException;
import com.revature.pokemon.utils.custom_exceptions.InvalidTokenException;
import com.revature.pokemon.utils.custom_exceptions.PermissionException;
import com.revature.pokemon.utils.custom_exceptions.ResourceNotFoundException;

import io.jsonwebtoken.ExpiredJwtException;

@RestControllerAdvice
/**
 * The ExceptionController class handles exceptions thrown during the execution
 * of the application.
 */
public class ExceptionController {

    /**
     * The logger instance for logging messages related to ExceptionController.
     */
    private static final Logger logger = LoggerFactory.getLogger(ExceptionController.class);

    /**
     * Handles InvalidCredentialException and returns an appropriate error response.
     *
     * @param e the InvalidCredentialException
     * @return a ResponseEntity with the appropriate HTTP status and error message
     */
    @ExceptionHandler(InvalidCredentialException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidCredentialException(InvalidCredentialException e) {
        logger.error("Invalid Credential");

        Map<String, Object> map = new HashMap<>();
        map.put("timestamp", new Date(System.currentTimeMillis()));
        map.put("message", e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(map);
    }

    /**
     * Handles ResourceNotFoundException and returns an appropriate error response.
     *
     * @param e the ResourceNotFoundException
     * @return a ResponseEntity with the appropriate HTTP status and error message
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleResourceNotFoundException(ResourceNotFoundException e) {
        logger.error("Resource not found");

        Map<String, Object> map = new HashMap<>();
        map.put("timestamp", new Date(System.currentTimeMillis()));
        map.put("message", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(map);
    }

    /**
     * Handles InvalidTokenException and returns an appropriate error response.
     *
     * @param e the InvalidTokenException
     * @return a ResponseEntity with the appropriate HTTP status and error message
     */
    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidTokenException(InvalidTokenException e) {
        logger.error("Invalid token");

        Map<String, Object> map = new HashMap<>();
        map.put("timestamp", new Date(System.currentTimeMillis()));
        map.put("message", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(map);
    }

    /**
     * Handles PermissionException and returns an appropriate error response.
     *
     * @param e the PermissionException
     * @return a ResponseEntity with the appropriate HTTP status and error message
     */
    @ExceptionHandler(PermissionException.class)
    public ResponseEntity<Map<String, Object>> handlePermissionException(PermissionException e) {
        logger.error("No permission");

        Map<String, Object> map = new HashMap<>();
        map.put("timestamp", new Date(System.currentTimeMillis()));
        map.put("message", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(map);
    }

    /**
     * Handles ExpiredJwtException and returns an appropriate error response.
     *
     * @param e the ExpiredJwtException
     * @return a ResponseEntity with the appropriate HTTP status and error message
     */
    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<Map<String, Object>> handleExpiredJwtException(ExpiredJwtException e) {
        logger.error("Expired token");

        Map<String, Object> map = new HashMap<>();
        map.put("timestamp", new Date(System.currentTimeMillis()));
        map.put("message", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(map);
    }

}
