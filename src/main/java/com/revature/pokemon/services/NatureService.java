package com.revature.pokemon.services;

import org.springframework.stereotype.Service;

import com.revature.pokemon.entities.Nature;
import com.revature.pokemon.repositories.NatureRepository;
import com.revature.pokemon.utils.custom_exceptions.ResourceNotFoundException;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class NatureService {
    NatureRepository natureRepo;

    public Nature findByName(String name){
        return natureRepo.findByName(name).orElseThrow(() -> new ResourceNotFoundException("Nature (" + name +") not found!"));
    }
}
