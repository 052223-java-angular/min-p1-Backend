package com.revature.pokemon.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.revature.pokemon.entities.Nature;
import com.revature.pokemon.repositories.NatureRepository;
import com.revature.pokemon.utils.custom_exceptions.ResourceNotFoundException;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
/**
 * The NatureService class provides methods for retrieving natures.
 */
public class NatureService {
    NatureRepository natureRepo;

    /**
     * The logger instance for logging messages related to NatureService.
     */
    private static final Logger logger = LoggerFactory.getLogger(NatureService.class);

    /**
     * Finds a nature by its name.
     *
     * @param name the name of the nature to be found
     * @return the Nature object with the specified name
     * @throws ResourceNotFoundException if the nature is not found
     */
    public Nature findByName(String name) throws ResourceNotFoundException {
        logger.info("Finding nature by name");

        return natureRepo.findByNameIgnoreCase(name)
                .orElseThrow(() -> new ResourceNotFoundException("Nature (" + name + ") not found!"));
    }
}
