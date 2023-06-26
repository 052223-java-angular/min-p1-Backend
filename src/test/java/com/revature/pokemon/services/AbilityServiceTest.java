package com.revature.pokemon.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.revature.pokemon.entities.Ability;
import com.revature.pokemon.repositories.AbilityRepository;
import com.revature.pokemon.utils.custom_exceptions.ResourceNotFoundException;

public class AbilityServiceTest {
    @Mock
    AbilityRepository abilityRepo;

    @InjectMocks
    AbilityService abilityService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindByNameSuccess() {
        String name = "stench";
        Ability ability = new Ability();
        ability.setName(name);

        when(abilityRepo.findByNameIgnoreCase(name)).thenReturn(java.util.Optional.of(ability));

        Ability result = abilityService.findByName(name);

        assertEquals(ability, result);
    }

    @Test
    void testFindByNameFailure() {
        String name = "not stench";

        when(abilityRepo.findByNameIgnoreCase(name)).thenReturn(java.util.Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> abilityService.findByName(name));
    }

}
