package com.eatery.config;

import com.eatery.entity.BusinessDayTime;
import com.eatery.entity.Customer;
import com.eatery.entity.Eatery;
import com.eatery.entity.EateryManager;
import com.eatery.repository.EateryManagerRepository;
import com.eatery.repository.CustomerRepository;
import com.eatery.repository.EateryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Set;

/**
 * Database Class for initializing and storing data
 */
@Configuration
public class EateryDatabase {

    // Logger to log information during the initialization process
    private static final Logger logger = LoggerFactory.getLogger(EateryDatabase.class);

    /**
     * CommandLineRunner bean to preload data into the database when the application starts.
     * @param eateryRepository Repository to handle Eatery entities
     * @param customerRepository Repository to handle Customer entities
     * @param eateryManagerRepository Repository to handle EateryManager entities
     * @return CommandLineRunner that initializes the database with predefined data
     */
    @Bean
    CommandLineRunner initDatabase(EateryRepository eateryRepository, CustomerRepository customerRepository, EateryManagerRepository eateryManagerRepository) {
        return args -> {
            // Create BusinessDayTime instance for a restaurant's opening hours
            BusinessDayTime restaurant1Opening = new BusinessDayTime(
                    DayOfWeek.MONDAY,
                    LocalTime.of(18, 0),
                    LocalTime.of(23, 0)
            );
            // Preload a restaurant entity into the database
            logger.info("Preloading {}", eateryRepository.save(
                    new Eatery(
                            Eatery.Type.RESTAURANT,
                            "restaurant-1",
                            "address-restaurant-1",
                            Set.of(restaurant1Opening),
                            100,
                            "service@restaurant-1.com",
                            "030-123-456"
                    )
            ));

            // Create BusinessDayTime instances for a bar's opening hours on Saturday and Sunday
            BusinessDayTime bar1Opening1 = new BusinessDayTime(
                    DayOfWeek.SATURDAY,
                    LocalTime.of(18, 0),
                    LocalTime.MAX
            );
            BusinessDayTime bar1Opening2 = new BusinessDayTime(
                    DayOfWeek.SUNDAY,
                    LocalTime.MIDNIGHT,
                    LocalTime.of(4, 0)
            );
            // Preload a bar entity into the database
            logger.info("Preloading {}", eateryRepository.save(
                    new Eatery(
                            Eatery.Type.BAR,
                            "bar-1",
                            "address-bar-1",
                            Set.of(bar1Opening1, bar1Opening2),
                            50,
                            "hello@bar-1.de",
                            "069-123-456"
                    )
            ));

            // Create BusinessDayTime instance for a cafe's opening hours
            BusinessDayTime cafe1Opening = new BusinessDayTime(
                    DayOfWeek.FRIDAY,
                    LocalTime.of(7, 0),
                    LocalTime.of(15, 0)
            );
            // Preload a cafe entity into the database
            logger.info("Preloading {}", eateryRepository.save(
                    new Eatery(
                            Eatery.Type.CAFE,
                            "cafe-1",
                            "address-cafe-1",
                            Set.of(cafe1Opening),
                            20,
                            "kontakt@cafe-1.com",
                            "040-123-456"
                    )
            ));

            // Preload a customer entity into the database
            logger.info("Preloading {}", customerRepository.save(
                    new Customer(
                            "Max",
                            "Mustermann",
                            "mustermann@example.com",
                            "0152-1234-5678"
                    )
            ));

            // Preload an eatery manager entity into the database
            logger.info("Preloading {}", eateryManagerRepository.save(
                    new EateryManager(
                            "Erika",
                            "Müller",
                            "mueller@home.de",
                            "0175-1234-5678"
                    )
            ));
        };
    }
}
