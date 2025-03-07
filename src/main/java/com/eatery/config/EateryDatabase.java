package com.eatery.config;

import com.eatery.entity.*;
import com.eatery.repository.EateryManagerRepository;
import com.eatery.repository.CustomerRepository;
import com.eatery.repository.EateryRepository;
import com.eatery.repository.ReviewRepository;
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
    CommandLineRunner initDatabase(EateryRepository eateryRepository, CustomerRepository customerRepository, EateryManagerRepository eateryManagerRepository, ReviewRepository reviewRepository) {
        return args -> {
            // Create BusinessDayTime instance for a restaurant's opening hours
            BusinessDayTime restaurantOpening1 = new BusinessDayTime(
                    DayOfWeek.MONDAY,
                    LocalTime.of(11, 30),
                    LocalTime.of(22, 0)
            );
            BusinessDayTime restaurantOpening2 = new BusinessDayTime(
                    DayOfWeek.TUESDAY,
                    LocalTime.of(11, 30),
                    LocalTime.of(22, 0)
            );
            BusinessDayTime restaurantOpening3 = new BusinessDayTime(
                    DayOfWeek.WEDNESDAY,
                    LocalTime.of(11, 30),
                    LocalTime.of(22, 0)
            );
            BusinessDayTime restaurantOpening4 = new BusinessDayTime(
                    DayOfWeek.THURSDAY,
                    LocalTime.of(11, 30),
                    LocalTime.of(22, 0)
            );
            BusinessDayTime restaurantOpening5 = new BusinessDayTime(
                    DayOfWeek.FRIDAY,
                    LocalTime.of(11, 30),
                    LocalTime.of(23, 0)
            );
            BusinessDayTime restaurantOpening6 = new BusinessDayTime(
                    DayOfWeek.SATURDAY,
                    LocalTime.of(11, 30),
                    LocalTime.of(23, 0)
            );
            BusinessDayTime restaurantOpening7 = new BusinessDayTime(
                    DayOfWeek.SUNDAY,
                    LocalTime.of(11, 30),
                    LocalTime.of(22, 0)
            );
            // Preload a restaurant entity into the database
            logger.info("Preloading {}", eateryRepository.save(
                    new Eatery(
                            Eatery.Type.RESTAURANT,
                            "Jim Block",
                            "Jungfernstieg 1-3, 20095 Hamburg",
                            Set.of(
                                    restaurantOpening1, restaurantOpening2, restaurantOpening3, restaurantOpening4,
                                    restaurantOpening5, restaurantOpening6, restaurantOpening7
                            ),
                            80,
                            "info@jim-block.de",
                            "040 30382217"
                    )
            ));

            // Create BusinessDayTime instances for a bar's opening hours
            BusinessDayTime barOpening1 = new BusinessDayTime(
                    DayOfWeek.FRIDAY,
                    LocalTime.of(19, 0),
                    LocalTime.MAX
            );
            BusinessDayTime barOpening2 = new BusinessDayTime(
                    DayOfWeek.SATURDAY,
                    LocalTime.MIDNIGHT,
                    LocalTime.of(3, 0)
            );
            BusinessDayTime barOpening3 = new BusinessDayTime(
                    DayOfWeek.SATURDAY,
                    LocalTime.of(19, 0),
                    LocalTime.MAX
            );
            BusinessDayTime barOpening4 = new BusinessDayTime(
                    DayOfWeek.SUNDAY,
                    LocalTime.MIDNIGHT,
                    LocalTime.of(3, 0)
            );
            BusinessDayTime barOpening5 = new BusinessDayTime(
                    DayOfWeek.SUNDAY,
                    LocalTime.of(20, 0),
                    LocalTime.MAX
            );

            // Preload a bar entity into the database
            logger.info("Preloading {}", eateryRepository.save(
                    new Eatery(
                            Eatery.Type.BAR,
                            "Pusser's",
                            "Falkenturmstraße 9, 80331 München",
                            Set.of(barOpening1, barOpening2, barOpening3, barOpening4, barOpening5),
                            50,
                            "cocktails@pussers.info",
                            "089 220500"
                    )
            ));

            // Create BusinessDayTime instance for a cafe's opening hours
            BusinessDayTime cafeOpening1 = new BusinessDayTime(
                    DayOfWeek.MONDAY,
                    LocalTime.of(9, 0),
                    LocalTime.of(21, 0)
            );
            BusinessDayTime cafeOpening2 = new BusinessDayTime(
                    DayOfWeek.TUESDAY,
                    LocalTime.of(9, 0),
                    LocalTime.of(21, 0)
            );
            BusinessDayTime cafeOpening3 = new BusinessDayTime(
                    DayOfWeek.WEDNESDAY,
                    LocalTime.of(9, 0),
                    LocalTime.of(21, 0)
            );
            BusinessDayTime cafeOpening4 = new BusinessDayTime(
                    DayOfWeek.THURSDAY,
                    LocalTime.of(9, 0),
                    LocalTime.of(21, 0)
            );

            // Preload a cafe entity into the database
            logger.info("Preloading {}", eateryRepository.save(
                    new Eatery(
                            Eatery.Type.CAFE,
                            "Café Anna Blume",
                            "Kollwitzstrasse 83, 10435 Berlin",
                            Set.of(cafeOpening1, cafeOpening2, cafeOpening3, cafeOpening4),
                            50,
                            "info@cafe-anna-blume.de",
                            "030 44048749"
                    )
            ));

            // Preload customer entities into the database
            logger.info("Preloading {}", customerRepository.save(
                    new Customer(
                            "Max",
                            "Mustermann",
                            "+4915212345678",
                            "maxMustermann",
                            "max1985"
                    )
            ));
            logger.info("Preloading {}", customerRepository.save(
                    new Customer(
                            "Alexandra",
                            "Ullrich",
                            "+4915312345678",
                            "ullrich-A",
                            "p455w0rd"
                    )
            ));
            // Preload customer review entities for an eatery
            logger.info("Preloading {}", reviewRepository.save(
                    new Review(1, 1, "Best place in town", 5)
            ));
            logger.info("Preloading {}", reviewRepository.save(
                    new Review(2, 2, "the cocktails taste average", 3)
            ));

            // Create BusinessDayTime instance for a manager of restaurant
            BusinessDayTime managerSchedule1 = new BusinessDayTime(
                    DayOfWeek.FRIDAY,
                    LocalTime.of(11, 30),
                    LocalTime.of(19, 30)
            );
            BusinessDayTime managerSchedule2 = new BusinessDayTime(
                    DayOfWeek.SATURDAY,
                    LocalTime.of(11, 30),
                    LocalTime.of(19, 30)
            );
            BusinessDayTime managerSchedule3 = new BusinessDayTime(
                    DayOfWeek.SUNDAY,
                    LocalTime.of(11, 30),
                    LocalTime.of(19, 30)
            );
            // Preload an eatery manager entity of restaurant into the database
            logger.info("Preloading {}", eateryManagerRepository.save(
                    new EateryManager(
                            "Erika",
                            "Müller",
                            "erikamüller",
                            "13353Ber",
                            1,
                            "Receptionist",
                            Set.of(managerSchedule1, managerSchedule2, managerSchedule3)
                    )
            ));

            // Create BusinessDayTime instance for a manager of bar
            BusinessDayTime barManagerSchedule1 = new BusinessDayTime(
                    DayOfWeek.FRIDAY,
                    LocalTime.of(19, 0),
                    LocalTime.MAX
            );
            BusinessDayTime barManagerSchedule2 = new BusinessDayTime(
                    DayOfWeek.SATURDAY,
                    LocalTime.MIDNIGHT,
                    LocalTime.of(3, 0)
            );
            BusinessDayTime barManagerSchedule3 = new BusinessDayTime(
                    DayOfWeek.SATURDAY,
                    LocalTime.of(19, 0),
                    LocalTime.MAX
            );
            BusinessDayTime barManagerSchedule4 = new BusinessDayTime(
                    DayOfWeek.SUNDAY,
                    LocalTime.MIDNIGHT,
                    LocalTime.of(3, 0)
            );
            // Preload an eatery manager entity into the database
            logger.info("Preloading {}", eateryManagerRepository.save(
                    new EateryManager(
                            "Tina",
                            "Lindner",
                            "lindner.tina",
                            "12345Abc!",
                            2,
                            "Bar Manager",
                            Set.of(barManagerSchedule1, barManagerSchedule2, barManagerSchedule3, barManagerSchedule4)
                    )
            ));
        };
    }
}
