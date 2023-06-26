package com.revature.pokemon.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.revature.pokemon.dtos.requests.ModifyTeamRequest;
import com.revature.pokemon.dtos.requests.NewTeamRequest;
import com.revature.pokemon.dtos.requests.TeamDeleteRequest;
import com.revature.pokemon.dtos.responses.TeamResponse;
import com.revature.pokemon.services.TeamService;
import com.revature.pokemon.services.TokenService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("/team")
/**
 * The TeamController class handles HTTP requests related to teams.
 */
public class TeamController {
    private final TeamService teamService;
    private final TokenService tokenService;

    /**
     * The logger instance for logging messages related to TeamController.
     */
    private static final Logger logger = LoggerFactory.getLogger(TeamController.class);

    static {
        logger.info("TEAM path");
    }

    /**
     * Creates a new team based on the provided request.
     *
     * @param req   the NewTeamRequest containing the team details
     * @param token the authorization token
     * @return a ResponseEntity with the appropriate HTTP status
     */
    @PostMapping("/create")
    public ResponseEntity<?> createTeam(@RequestBody NewTeamRequest req, @RequestHeader("Authorization") String token) {
        logger.info("Processing create request");

        tokenService.validateToken(token, req.getUserId());

        teamService.create(req);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * Modifies an existing team based on the provided request.
     *
     * @param req   the ModifyTeamRequest containing the modified team details
     * @param token the authorization token
     * @return a ResponseEntity with the appropriate HTTP status
     */
    @PostMapping("/modify")
    public ResponseEntity<?> modifyTeam(@RequestBody ModifyTeamRequest req,
            @RequestHeader("Authorization") String token) {
        logger.info("Processing modify request");

        tokenService.validateToken(token, req.getUserId());

        teamService.modify(req);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * Deletes a team based on the provided request.
     *
     * @param req   the TeamDeleteRequest containing the team details to be deleted
     * @param token the authorization token
     * @return a ResponseEntity with the appropriate HTTP status
     */
    @PostMapping("/delete")
    public ResponseEntity<?> deleteTeam(@RequestBody TeamDeleteRequest req,
            @RequestHeader("Authorization") String token) {
        logger.info("Processing delete request");

        tokenService.validateToken(token, req.getUserId());

        teamService.delete(req);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * Retrieves a team by its ID along with the associated builds.
     *
     * @param id the ID of the team to retrieve
     * @return a ResponseEntity with the TeamResponse object and the appropriate
     *         HTTP status
     */
    @GetMapping("/{id}")
    public ResponseEntity<TeamResponse> getTeamById(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK).body(teamService.findTeamWithBuildsById(id));
    }

    /**
     * Retrieves all teams associated with a user ID.
     *
     * @param user_id the ID of the user whose teams to retrieve
     * @return a ResponseEntity with the list of TeamResponse objects and the
     *         appropriate HTTP status
     */
    @GetMapping("/")
    public ResponseEntity<List<TeamResponse>> getTeamssByUserId(@RequestParam String user_id) {
        return ResponseEntity.status(HttpStatus.OK).body(teamService.findByUserId(user_id));
    }
}
