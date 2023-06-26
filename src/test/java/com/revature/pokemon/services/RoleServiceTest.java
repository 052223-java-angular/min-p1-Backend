package com.revature.pokemon.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.revature.pokemon.entities.Role;
import com.revature.pokemon.repositories.RoleRepository;
import com.revature.pokemon.utils.custom_exceptions.ResourceNotFoundException;

public class RoleServiceTest {
    @Mock
    RoleRepository roleRepo;

    @InjectMocks
    RoleService roleService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindByNameSuccess() {
        String name = "user";
        Role role = new Role();
        role.setName(name);

        when(roleRepo.findByName(name)).thenReturn(java.util.Optional.of(role));

        Role result = roleService.findByName(name);

        assertEquals(role, result);
    }

    @Test
    void testFindByNameFailure() {
        String name = "not user";

        when(roleRepo.findByName(name)).thenReturn(java.util.Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> roleService.findByName(name));
    }
}
