package com.revature.pokemon.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.revature.pokemon.dtos.requests.ModifyTeamRequest;
import com.revature.pokemon.dtos.requests.NewTeamRequest;
import com.revature.pokemon.dtos.requests.TeamDeleteRequest;
import com.revature.pokemon.dtos.responses.TeamResponse;
import com.revature.pokemon.entities.Build;
import com.revature.pokemon.entities.Team;
import com.revature.pokemon.entities.User;
import com.revature.pokemon.repositories.TeamRepository;

public class TeamServiceTest {
    @Mock
    TeamRepository teamRepo;

    @Mock
    BuildService buildService;

    @Mock
    UserService userService;

    @InjectMocks
    TeamService teamService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreate() {
        NewTeamRequest req = new NewTeamRequest();
        req.setUserId("a real user id");

        User user = new User();
        user.setId(req.getUserId());

        Set<Build> builds = new HashSet<>();

        Team team = new Team(req.getName(), req.getDescription(), user, builds);

        when(userService.findById(req.getUserId())).thenReturn(user);
        when(buildService.findByIds(req.getBuilds())).thenReturn(builds);

        teamService.create(req);

        ArgumentCaptor<Team> teamCaptor = ArgumentCaptor.forClass(Team.class);
        verify(teamRepo).save(teamCaptor.capture());
        Team savedTeam = teamCaptor.getValue();
        assertEquals(team.getBuilds(), savedTeam.getBuilds());

    }

    @Test
    void testDelete() {
        TeamDeleteRequest req = new TeamDeleteRequest();
        req.setTeamId("team id");
        req.setUserId("user id");

        Team team = new Team();
        team.setId(req.getTeamId());
        User user = new User();
        user.setId(req.getUserId());
        team.setUser(user);

        when(teamRepo.findById(req.getTeamId())).thenReturn(java.util.Optional.of(team));

        teamService.delete(req);

        verify(teamRepo).delete(team);

    }

    @Test
    void testFindById() {
        String id = "a good id";

        Team team = new Team();
        team.setId(id);

        when(teamRepo.findById(id)).thenReturn(java.util.Optional.of(team));

        Team result = teamService.findById(id);

        assertEquals(result, team);

    }

    @Test
    void testFindByUserId() {
        String id = "a id";
        List<Team> teams = new ArrayList<>();
        List<TeamResponse> responseList = new ArrayList<>();

        when(teamRepo.findAllTeamsWithBuildsByUserId(id)).thenReturn(teams);

        List<TeamResponse> results = teamService.findByUserId(id);

        assertEquals(responseList, results);
    }

    @Test
    void testFindTeamWithBuildsById() {
        String id = "id";
        Team team = new Team();
        team.setId(id);
        TeamResponse teamResponse = new TeamResponse(team);

        when(teamRepo.findById(id)).thenReturn(java.util.Optional.of(team));

        TeamResponse result = teamService.findTeamWithBuildsById(id);

        assertEquals(teamResponse.getBuilds(), result.getBuilds());
    }

    @Test
    void testModify() {
        ModifyTeamRequest req = new ModifyTeamRequest();
        req.setTeamId("id");
        req.setUserId("id user");

        User user = new User();
        user.setId(req.getUserId());

        Team team = new Team();
        team.setId(req.getTeamId());
        team.setUser(user);

        Set<Build> builds = new HashSet<>();
        when(teamRepo.findById(req.getTeamId())).thenReturn(java.util.Optional.of(team));
        when(buildService.findByIds(req.getBuilds())).thenReturn(builds);

        teamService.modify(req);

        verify(teamRepo).save(team);

    }
}
