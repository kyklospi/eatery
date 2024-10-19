package com.favourite.eatery.config;

import com.favourite.eatery.model.*;
import com.favourite.eatery.repository.AppUserRepository;
import com.favourite.eatery.repository.EateryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Set;

@Configuration
public class FavouriteEateryDatabase {

    private static final Logger logger = LoggerFactory.getLogger(FavouriteEateryDatabase.class);

    @Bean
    CommandLineRunner initDatabase(EateryRepository eateryRepository, AppUserRepository userRepository) {
        return _ -> {
            BusinessDayTime restaurant1Opening = new BusinessDayTime(DayOfWeek.MONDAY, LocalTime.of(18, 0), LocalTime.of(23, 0));
            logger.info("Preloading {}", eateryRepository.save(new Restaurant("restaurant-1", "address-restaurant-1", Set.of(restaurant1Opening), 100, "service@restaurant-1.com", "030-123-456")));

            BusinessDayTime bar1Opening1 = new BusinessDayTime(DayOfWeek.SATURDAY, LocalTime.of(18, 0), LocalTime.MAX);
            BusinessDayTime bar1Opening2 = new BusinessDayTime(DayOfWeek.SUNDAY, LocalTime.MIDNIGHT, LocalTime.of(4, 0));
            logger.info("Preloading {}", eateryRepository.save(new Bar("bar-1", "address-bar-1", Set.of(bar1Opening1, bar1Opening2), 50, "hello@bar-1.de", "069-123-456")));

            BusinessDayTime cafe1Opening = new BusinessDayTime(DayOfWeek.FRIDAY, LocalTime.of(7, 0), LocalTime.of(15, 0));
            logger.info("Preloading {}", eateryRepository.save(new Cafe("cafe-1", "address-cafe-1", Set.of(cafe1Opening), 20, "kontakt@cafe-1.com", "040-123-456")));

            logger.info("Preloading {}", userRepository.save(new AppUser("Max", "Mustermann", "mustermann@example.com", "0152-1234-5678")));
        };
    }
}
