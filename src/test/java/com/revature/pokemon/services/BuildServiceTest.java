package com.revature.pokemon.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.revature.pokemon.dtos.requests.BuildDeleteRequest;
import com.revature.pokemon.dtos.requests.ModifyBuildRequest;
import com.revature.pokemon.dtos.requests.NewBuildRequest;
import com.revature.pokemon.dtos.responses.BuildResponse;
import com.revature.pokemon.entities.Ability;
import com.revature.pokemon.entities.Build;
import com.revature.pokemon.entities.Move;
import com.revature.pokemon.entities.Nature;
import com.revature.pokemon.entities.Pokemon;
import com.revature.pokemon.entities.User;
import com.revature.pokemon.repositories.BuildRepository;
import com.revature.pokemon.utils.custom_exceptions.PermissionException;
import com.revature.pokemon.utils.custom_exceptions.ResourceNotFoundException;

public class BuildServiceTest {
    @Mock
    BuildRepository buildRepo;

    @Mock
    AbilityService abilityService;

    @Mock
    NatureService natureService;

    @Mock
    PokemonService pokemonService;

    @Mock
    MoveService moveService;

    @Mock
    UserService userService;

    @InjectMocks
    BuildService buildService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateSuccess() {
        NewBuildRequest req = new NewBuildRequest();
        req.setName("mew build");
        req.setAbilityName("stench");
        req.setNatureName("hardy");
        req.setPokemonName("mew");
        req.setDescription("best mew build");
        req.setUserId("real user id");
        String[] learnedMoves = { "pound", "karate-chop", "double-slap", "comet-punch" };
        req.setLearnedMoves(learnedMoves);

        Nature nature = new Nature();
        nature.setName("hardy");
        Ability ability = new Ability();
        ability.setName("stench");
        Pokemon pokemon = new Pokemon();
        pokemon.setName("mew");
        Set<Move> moves = new HashSet<>();
        Move move1 = new Move();
        move1.setName("pound");
        Move move2 = new Move();
        move2.setName("karate-chop");
        Move move3 = new Move();
        move3.setName("double-slap");
        Move move4 = new Move();
        move4.setName("comet-punch");
        moves.add(move1);
        moves.add(move2);
        moves.add(move3);
        moves.add(move4);
        User user = new User();
        user.setId("real user id");

        when(natureService.findByName(req.getNatureName())).thenReturn(nature);
        when(abilityService.findByName(req.getAbilityName())).thenReturn(ability);
        when(pokemonService.findByName(req.getPokemonName())).thenReturn(pokemon);
        when(moveService.findByNames(req.getLearnedMoves())).thenReturn(moves);
        when(userService.findById(req.getUserId())).thenReturn(user);

        Build result = buildService.create(req);

        assertEquals(req.getName(), result.getName());
        assertEquals(req.getDescription(), result.getDescription());
        assertEquals(nature, result.getNature());
        assertEquals(ability, result.getAbility());
        assertEquals(pokemon, result.getPokemon());
        assertEquals(moves, result.getMoves());

        verify(buildRepo).save(result);
    }

    @Test
    void testCreateFailure() {
        NewBuildRequest req = new NewBuildRequest();
        req.setName("mew build");
        req.setAbilityName("stench");
        req.setNatureName("hardy");
        req.setPokemonName("mew");
        req.setDescription("best mew build");
        req.setUserId("real user id");
        String[] learnedMoves = { "pound", "karate-chop", "double-slap", "comet-punch" };
        req.setLearnedMoves(learnedMoves);

        when(natureService.findByName(req.getNatureName()))
                .thenThrow(new ResourceNotFoundException("Nature not found!"));

        assertThrows(ResourceNotFoundException.class, () -> buildService.create(req));
    }

    @Test
    void testDeleteSuccess() {
        BuildDeleteRequest req = new BuildDeleteRequest();
        req.setBuildId("real build id");
        req.setUserId("real user id");

        Build build = new Build();
        build.setId("real id");
        User user = new User();
        user.setId("real user id");
        build.setUser(user);

        when(buildRepo.findById(req.getBuildId())).thenReturn(java.util.Optional.of(build));

        buildService.delete(req);

        verify(buildRepo).delete(build);

    }

    @Test
    void testDeleteFailure() {
        BuildDeleteRequest req = new BuildDeleteRequest();
        req.setBuildId("real build id");
        req.setUserId("real user id");

        Build build = new Build();
        build.setId("real id");
        User user = new User();
        user.setId("fake user id");
        build.setUser(user);

        when(buildRepo.findById(req.getBuildId())).thenReturn(java.util.Optional.of(build));

        assertThrows(PermissionException.class, () -> buildService.delete(req));
    }

    @Test
    void testFindBuildsWithPokemonByIdSuccess() {
        String id = "real id";
        Nature nature = new Nature();
        nature.setName("hardy");
        Ability ability = new Ability();
        ability.setName("stench");
        Pokemon pokemon = new Pokemon();
        pokemon.setName("mew");

        Build build = new Build();
        build.setId(id);
        build.setAbility(ability);
        build.setNature(nature);
        build.setPokemon(pokemon);

        BuildResponse buildResponse = new BuildResponse(build);

        when(buildRepo.findById(id)).thenReturn(java.util.Optional.of(build));

        BuildResponse result = buildService.findBuildsWithPokemonById(id);

        assertEquals(buildResponse.getId(), result.getId());
    }

    @Test
    void testFindBuildsWithPokemonByIdFailure() {
        String id = "fake id";

        when(buildRepo.findById(id)).thenReturn(java.util.Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> buildService.findBuildsWithPokemonById(id));
    }

    @Test
    void testFindByIdSuccess() {
        String id = "real id";

        Build build = new Build();
        build.setId(id);

        when(buildRepo.findById(id)).thenReturn(java.util.Optional.of(build));

        Build result = buildService.findById(id);

        assertEquals(build, result);
    }

    @Test
    void testFindByIdFailure() {
        String id = "real id";

        when(buildRepo.findById(id)).thenReturn(java.util.Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> buildService.findById(id));
    }

    @Test
    void testFindByIdsSuccess() {
        String[] ids = { "one", "two", "three" };

        Set<Build> builds = new HashSet<>();
        Build build1 = new Build();
        build1.setId("one");

        Build build2 = new Build();
        build2.setId("two");

        Build build3 = new Build();
        build3.setId("three");

        builds.add(build1);
        builds.add(build2);
        builds.add(build3);

        when(buildRepo.findById("one")).thenReturn(java.util.Optional.of(build1));
        when(buildRepo.findById("two")).thenReturn(java.util.Optional.of(build2));
        when(buildRepo.findById("three")).thenReturn(java.util.Optional.of(build3));

        Set<Build> results = buildService.findByIds(ids);

        assertEquals(builds, results);

    }

    @Test
    void testFindByIdsFailure() {
        String[] ids = { "one", "two", "three" };

        Set<Build> builds = new HashSet<>();
        Build build1 = new Build();
        build1.setId("one");

        Build build2 = new Build();
        build2.setId("two");

        Build build3 = new Build();
        build3.setId("three");

        builds.add(build1);
        builds.add(build2);
        builds.add(build3);

        when(buildRepo.findById("one")).thenReturn(java.util.Optional.of(build1));
        when(buildRepo.findById("two")).thenReturn(java.util.Optional.of(build1));
        when(buildRepo.findById("three")).thenReturn(java.util.Optional.of(build1));

        Set<Build> results = buildService.findByIds(ids);

        assertNotEquals(builds, results);

    }

    @Test
    void testFindByUserIdSuccess() {
        String id = "real id";
        Nature nature = new Nature();
        nature.setName("hardy");
        Ability ability = new Ability();
        ability.setName("stench");
        Pokemon pokemon = new Pokemon();
        pokemon.setName("mew");

        List<Build> builds = new ArrayList<>();
        Build build1 = new Build();
        build1.setId("real");
        build1.setAbility(ability);
        build1.setNature(nature);
        build1.setPokemon(pokemon);
        builds.add(build1);

        List<BuildResponse> responses = new ArrayList<>();
        BuildResponse response1 = new BuildResponse(build1);
        responses.add(response1);

        when(buildRepo.findAllBuildsWithPokemonByUserId(id)).thenReturn(builds);

        List<BuildResponse> result = buildService.findByUserId(id);

        assertEquals(responses.get(0).getId(), result.get(0).getId());
    }

    @Test
    void testModify() {
        ModifyBuildRequest req = new ModifyBuildRequest();
        req.setBuildId("real build id");
        req.setName("mew build");
        req.setAbilityName("stench");
        req.setNatureName("hardy");
        req.setPokemonName("mew");
        req.setDescription("best mew build");
        req.setUserId("real user id");
        String[] learnedMoves = { "pound", "karate-chop", "double-slap", "comet-punch" };
        req.setLearnedMoves(learnedMoves);

        Nature nature = new Nature();
        nature.setName("hardy");
        Ability ability = new Ability();
        ability.setName("stench");
        Pokemon pokemon = new Pokemon();
        pokemon.setName("mew");
        Set<Move> moves = new HashSet<>();
        Move move1 = new Move();
        move1.setName("pound");
        Move move2 = new Move();
        move2.setName("karate-chop");
        Move move3 = new Move();
        move3.setName("double-slap");
        Move move4 = new Move();
        move4.setName("comet-punch");
        moves.add(move1);
        moves.add(move2);
        moves.add(move3);
        moves.add(move4);
        User user = new User();
        user.setId("real user id");

        Build build = new Build();
        build.setId(req.getBuildId());
        build.setUser(user);

        when(natureService.findByName(req.getNatureName())).thenReturn(nature);
        when(abilityService.findByName(req.getAbilityName())).thenReturn(ability);
        when(pokemonService.findByName(req.getPokemonName())).thenReturn(pokemon);
        when(moveService.findByNames(req.getLearnedMoves())).thenReturn(moves);

        when(buildRepo.findById(req.getBuildId())).thenReturn(java.util.Optional.of(build));

        buildService.modify(req);

        assertEquals(req.getName(), build.getName());
        assertEquals(req.getDescription(), build.getDescription());
        assertEquals(nature, build.getNature());
        assertEquals(ability, build.getAbility());
        assertEquals(pokemon, build.getPokemon());
        assertEquals(moves, build.getMoves());

        verify(buildRepo).save(build);
    }

    @Test
    void testModifyFailure() {
        ModifyBuildRequest req = new ModifyBuildRequest();
        req.setBuildId("real build id");
        req.setName("mew build");
        req.setAbilityName("stench");
        req.setNatureName("hardy");
        req.setPokemonName("mew");
        req.setDescription("best mew build");
        req.setUserId("real user id");
        String[] learnedMoves = { "pound", "karate-chop", "double-slap", "comet-punch" };
        req.setLearnedMoves(learnedMoves);

        Nature nature = new Nature();
        nature.setName("hardy");
        Ability ability = new Ability();
        ability.setName("stench");
        Pokemon pokemon = new Pokemon();
        pokemon.setName("mew");
        Set<Move> moves = new HashSet<>();
        Move move1 = new Move();
        move1.setName("pound");
        Move move2 = new Move();
        move2.setName("karate-chop");
        Move move3 = new Move();
        move3.setName("double-slap");
        Move move4 = new Move();
        move4.setName("comet-punch");
        moves.add(move1);
        moves.add(move2);
        moves.add(move3);
        moves.add(move4);
        User user = new User();
        user.setId("fake user id");

        Build build = new Build();
        build.setId(req.getBuildId());
        build.setUser(user);

        when(buildRepo.findById(req.getBuildId())).thenReturn(java.util.Optional.of(build));

        assertThrows(PermissionException.class, () -> buildService.modify(req));
    }
}
