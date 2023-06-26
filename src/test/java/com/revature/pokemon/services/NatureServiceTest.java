package com.revature.pokemon.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.revature.pokemon.entities.Nature;
import com.revature.pokemon.repositories.NatureRepository;
import com.revature.pokemon.utils.custom_exceptions.ResourceNotFoundException;

public class NatureServiceTest {
    @Mock
    NatureRepository natureRepo;

    @InjectMocks
    NatureService natureService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindByNameSuccess() {
        String name = "hardy";
        Nature nature = new Nature();
        nature.setName(name);

        when(natureRepo.findByNameIgnoreCase(name)).thenReturn(java.util.Optional.of(nature));

        Nature result = natureService.findByName(name);

        assertEquals(nature, result);
    }

    @Test
    void testFindByNameFailure() {
        String name = "not hardy";

        when(natureRepo.findByNameIgnoreCase(name)).thenReturn(java.util.Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> natureService.findByName(name));
    }
}
