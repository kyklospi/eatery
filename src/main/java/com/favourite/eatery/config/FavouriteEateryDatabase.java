package com.favourite.eatery.config;

import com.favourite.eatery.model.Eatery;
import com.favourite.eatery.repository.EateryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FavouriteEateryDatabase {

    private static final Logger logger = LoggerFactory.getLogger(FavouriteEateryDatabase.class);

    @Bean
    CommandLineRunner initDatabase(EateryRepository repository) {
        return args -> {
            logger.info("Preloading {}", repository.save(new Eatery("restaurant-1", "address-restaurant-1")));
            logger.info("Preloading {}", repository.save(new Eatery("bar-1", "address-bar-1")));
        };
    }
}
