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
/**
 * The MoveService class provides methods for retrieving moves.
 */
public class MoveService {
    MoveRepository moveRepo;

    /**
     * The logger instance for logging messages related to MoveService.
     */
    private static final Logger logger = LoggerFactory.getLogger(MoveService.class);

    /**
     * Finds a move by its name.
     *
     * @param name the name of the move to be found
     * @return the Move object with the specified name
     * @throws ResourceNotFoundException if the move is not found
     */
    public Move findByName(String name) throws ResourceNotFoundException {
        logger.info("Finding move by name");

        return moveRepo.findByNameIgnoreCase(name)
                .orElseThrow(() -> new ResourceNotFoundException("Move (" + name + ") not found!"));
    }

    /**
     * Finds a set of moves by their names.
     *
     * @param move_set an array of move names
     * @return a set of Move objects with the specified names
     * @throws ResourceNotFoundException if any of the moves are not found
     */
    public Set<Move> findByNames(String[] move_set) throws ResourceNotFoundException {
        logger.info("Finding moves by name");

        Set<Move> moves = new HashSet<>();
        for (String move : move_set) {
            Move m = findByName(move);
            moves.add(m);
        }
        return moves;
    }
}
