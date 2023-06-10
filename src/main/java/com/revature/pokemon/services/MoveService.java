package com.revature.pokemon.services;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.revature.pokemon.entities.Move;
import com.revature.pokemon.repositories.MoveRepository;
import com.revature.pokemon.utils.custom_exceptions.ResourceNotFoundException;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class MoveService {
    MoveRepository moveRepo;

    private static final Logger logger = LoggerFactory.getLogger(MoveService.class);

    public Move findByName(String name){
        logger.info("Finding move by name");

        return moveRepo.findByName(name).orElseThrow(() -> new ResourceNotFoundException("Move (" + name +") not found!"));
    }
    public Set<Move> findByNames(String[] move_set) {
        logger.info("Finding moves by name");

        Set<Move> moves = new HashSet<>();
        for(String move : move_set){
            Move m = findByName(move);
            moves.add(m);
        }
        return moves;
    }
}
