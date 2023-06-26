package com.revature.pokemon.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.revature.pokemon.entities.Move;
import com.revature.pokemon.repositories.MoveRepository;
import com.revature.pokemon.utils.custom_exceptions.ResourceNotFoundException;

public class MoveServiceTest {
    @Mock
    MoveRepository moveRepo;

    @InjectMocks
    MoveService moveService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindByNameSuccess() {
        String name = "pound";
        Move move = new Move();
        move.setName(name);

        when(moveRepo.findByNameIgnoreCase(name)).thenReturn(java.util.Optional.of(move));

        Move result = moveService.findByName(name);

        assertEquals(move, result);
    }

    @Test
    void testFindByNameFailure() {
        String name = "not pound";

        when(moveRepo.findByNameIgnoreCase(name)).thenReturn(java.util.Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> moveService.findByName(name));
    }

    @Test
    void testFindByNamesSuccess() {
        String[] learnedMoves = { "pound", "karate-chop", "double-slap", "comet-punch" };
        Set<Move> moves = new HashSet<>();

        for (String moveName : learnedMoves) {
            Move move = new Move();
            move.setName(moveName);
            moves.add(move);
            when(moveRepo.findByNameIgnoreCase(moveName)).thenReturn(java.util.Optional.of(move));
        }

        Set<Move> result = moveService.findByNames(learnedMoves);

        assertEquals(moves, result);
    }

    @Test
    void testFindByNamesFailure() {
        String[] learnedMoves = { "not pound", "not karate-chop", "not double-slap", "not comet-punch" };

        when(moveRepo.findByNameIgnoreCase("not pound")).thenReturn(java.util.Optional.empty());
        when(moveRepo.findByNameIgnoreCase("not karate-chop")).thenReturn(java.util.Optional.empty());
        when(moveRepo.findByNameIgnoreCase("not double-slap")).thenReturn(java.util.Optional.empty());
        when(moveRepo.findByNameIgnoreCase("not comet-punch")).thenReturn(java.util.Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> moveService.findByNames(learnedMoves));
    }
}
