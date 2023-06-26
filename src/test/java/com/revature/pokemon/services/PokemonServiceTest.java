package com.revature.pokemon.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.revature.pokemon.entities.Pokemon;
import com.revature.pokemon.repositories.PokemonRepository;
import com.revature.pokemon.utils.custom_exceptions.ResourceNotFoundException;

public class PokemonServiceTest {
    @Mock
    PokemonRepository pokemonRepo;

    @InjectMocks
    PokemonService pokemonService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindByNameSuccess() {
        String name = "pikachu";
        Pokemon pokemon = new Pokemon();
        pokemon.setName(name);

        when(pokemonRepo.findByNameIgnoreCase(name)).thenReturn(java.util.Optional.of(pokemon));

        Pokemon result = pokemonService.findByName(name);

        assertEquals(pokemon, result);
    }

    @Test
    void testFindByNameFailure() {
        String name = "not pikachu";

        when(pokemonRepo.findByNameIgnoreCase(name)).thenReturn(java.util.Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> pokemonService.findByName(name));
    }
}
