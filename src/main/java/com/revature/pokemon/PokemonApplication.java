package com.revature.pokemon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.revature.pokemon.services.CommentVoteService;

@SpringBootApplication
public class PokemonApplication {
	private static final Logger logger = LoggerFactory.getLogger(PokemonApplication.class);

	public static void main(String[] args) {
		logger.info("Starting application");
		SpringApplication.run(PokemonApplication.class, args);
	}

}
