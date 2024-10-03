package com.favourite.eatery.config;

import com.favourite.eatery.model.AppUser;
import com.favourite.eatery.model.Eatery;
import com.favourite.eatery.repository.AppUserRepository;
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
    CommandLineRunner initDatabase(EateryRepository eateryRepository, AppUserRepository userRepository) {
        return args -> {
            logger.info("Preloading {}", eateryRepository.save(new Eatery("restaurant-1", "address-restaurant-1")));
            logger.info("Preloading {}", eateryRepository.save(new Eatery("bar-1", "address-bar-1")));

            logger.info("Preloading {}", userRepository.save(new AppUser("Max", "Mustermann")));
        };
    }
}
