package com.favourite.eatery.config;

import com.favourite.eatery.model.AppUser;
import com.favourite.eatery.model.BusinessDayTime;
import com.favourite.eatery.model.Eatery;
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
        return args -> {
            BusinessDayTime restaurant1Opening = new BusinessDayTime(DayOfWeek.MONDAY, LocalTime.of(18, 0), LocalTime.of(23, 0));
            logger.info("Preloading {}", eateryRepository.save(new Eatery("restaurant-1", "address-restaurant-1", Set.of(restaurant1Opening))));

            BusinessDayTime bar1Opening1 = new BusinessDayTime(DayOfWeek.SATURDAY, LocalTime.of(18, 0), LocalTime.MAX);
            BusinessDayTime bar1Opening2 = new BusinessDayTime(DayOfWeek.SUNDAY, LocalTime.MIDNIGHT, LocalTime.of(4, 0));
            logger.info("Preloading {}", eateryRepository.save(new Eatery("bar-1", "address-bar-1", Set.of(bar1Opening1, bar1Opening2))));

            logger.info("Preloading {}", userRepository.save(new AppUser("Max", "Mustermann")));
        };
    }
}
