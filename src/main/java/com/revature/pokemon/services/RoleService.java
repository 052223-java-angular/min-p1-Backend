package com.revature.pokemon.services;

import org.springframework.stereotype.Service;

import com.revature.pokemon.entities.Role;
import com.revature.pokemon.repositories.RoleRepository;
import com.revature.pokemon.utils.custom_exceptions.ResourceNotFoundException;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class RoleService {
    private final RoleRepository roleRepo;

    public Role findByName(String name){
        return roleRepo.findByName(name).orElseThrow(() -> new ResourceNotFoundException("Role not found!"));
    }
}
