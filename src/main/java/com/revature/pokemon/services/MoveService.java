package com.revature.pokemon.services;

import java.util.HashSet;
import java.util.Set;

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
        return moveRepo.findByName(name).orElseThrow(() -> new ResourceNotFoundException("Move (" + name +") not found!"));
    }
    public Set<Move> findByNames(String[] move_set) {
        Set<Move> moves = new HashSet<>();
        for(String move : move_set){
            Move m = moveRepo.findByName(move).orElseThrow(() -> new ResourceNotFoundException("Move (" + move +") not found!"));
            moves.add(m);
        }
        return moves;
    }
}
