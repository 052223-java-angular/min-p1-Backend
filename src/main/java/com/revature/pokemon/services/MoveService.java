package com.revature.pokemon.services;

import org.springframework.stereotype.Service;

import com.revature.pokemon.entities.Move;
import com.revature.pokemon.repositories.MoveRepository;
import com.revature.pokemon.utils.custom_exceptions.ResourceNotFoundException;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class MoveService {
    MoveRepository moveRepo;
    public Move findByName(String name){
        return moveRepo.findByName(name).orElseThrow(() -> new ResourceNotFoundException("Move not found!"));
    }
}
