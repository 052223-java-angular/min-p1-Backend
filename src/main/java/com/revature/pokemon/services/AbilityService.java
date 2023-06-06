package com.revature.pokemon.services;

import org.springframework.stereotype.Service;

import com.revature.pokemon.entities.Ability;
import com.revature.pokemon.repositories.AbilityRepository;
import com.revature.pokemon.utils.custom_exceptions.ResourceNotFoundException;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class AbilityService {
    AbilityRepository abilityRepo;
    public Ability findByName(String name){
        return abilityRepo.findByName(name).orElseThrow(() -> new ResourceNotFoundException("Ability not found!"));
    }
}
