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
            // Preload eatery manager entities into the database
            EateryManager managerRestaurant1 = new EateryManager(
                    "Erika",
                    "Müller",
                    "erikamüller",
                    "13353Ber",
                    1,
                    "Receptionist",
                    Set.of(
                            new BusinessDayTime(DayOfWeek.FRIDAY, LocalTime.of(11, 30), LocalTime.of(19, 30)),
                            new BusinessDayTime(DayOfWeek.SATURDAY, LocalTime.of(11, 30), LocalTime.of(19, 30)),
                            new BusinessDayTime(DayOfWeek.SUNDAY, LocalTime.of(11, 30), LocalTime.of(19, 30))
                    )
            );
            logger.info("Preloading {}", eateryManagerRepository.save(managerRestaurant1));

            EateryManager managerBar1 = new EateryManager(
                    "Tina",
                    "Lindner",
                    "lindner.tina",
                    "12345Abc!",
                    2,
                    "Bar Manager",
                    Set.of(
                            new BusinessDayTime(DayOfWeek.FRIDAY, LocalTime.of(19, 0), LocalTime.MAX),
                            new BusinessDayTime(DayOfWeek.SATURDAY, LocalTime.MIDNIGHT, LocalTime.of(3, 0)),
                            new BusinessDayTime(DayOfWeek.SATURDAY, LocalTime.of(19, 0), LocalTime.MAX),
                            new BusinessDayTime(DayOfWeek.SUNDAY, LocalTime.MIDNIGHT, LocalTime.of(3, 0))
                    )
            );
            logger.info("Preloading {}", eateryManagerRepository.save(managerBar1));

            EateryManager managerCafe1 = new EateryManager(
                    "Elisa",
                    "Köhler",
                    "kelisa",
                    "k03h13r",
                    3,
                    "Assistant",
                    Set.of(
                            new BusinessDayTime(DayOfWeek.MONDAY, LocalTime.of(9, 0), LocalTime.of(17,0)),
                            new BusinessDayTime(DayOfWeek.TUESDAY, LocalTime.of(9, 0), LocalTime.of(17,0)),
                            new BusinessDayTime(DayOfWeek.WEDNESDAY, LocalTime.of(13, 0), LocalTime.of(21,0)),
                            new BusinessDayTime(DayOfWeek.THURSDAY, LocalTime.of(13, 0), LocalTime.of(21,0))
                    )
            );
            logger.info("Preloading {}", eateryManagerRepository.save(managerCafe1));

            // Preload eatery entities into the database
            Eatery restaurant1 = new Eatery(
                    Eatery.Type.RESTAURANT,
                    "Jim Block",
                    "Jungfernstieg 1-3, 20095 Hamburg",
                    Set.of(
                            new BusinessDayTime(DayOfWeek.MONDAY, LocalTime.of(11, 30), LocalTime.of(22, 0)),
                            new BusinessDayTime(DayOfWeek.TUESDAY, LocalTime.of(11, 30), LocalTime.of(22, 0)),
                            new BusinessDayTime(DayOfWeek.WEDNESDAY, LocalTime.of(11, 30), LocalTime.of(22, 0)),
                            new BusinessDayTime(DayOfWeek.THURSDAY, LocalTime.of(11, 30), LocalTime.of(22, 0)),
                            new BusinessDayTime(DayOfWeek.FRIDAY, LocalTime.of(11, 30), LocalTime.of(23, 0)),
                            new BusinessDayTime(DayOfWeek.SATURDAY, LocalTime.of(11, 30), LocalTime.of(23, 0)),
                            new BusinessDayTime(DayOfWeek.SUNDAY, LocalTime.of(11, 30), LocalTime.of(22, 0))
                    ),
                    80,
                    "info@jim-block.de",
                    "040 30382217"
            );
            restaurant1.setManagerId(managerRestaurant1.getId());
            logger.info("Preloading {}", eateryRepository.save(restaurant1));

            Eatery bar1 = new Eatery(
                    Eatery.Type.BAR,
                    "Pusser's",
                    "Falkenturmstraße 9, 80331 München",
                    Set.of(
                            new BusinessDayTime(DayOfWeek.FRIDAY, LocalTime.of(19, 0), LocalTime.MAX),
                            new BusinessDayTime(DayOfWeek.SATURDAY, LocalTime.MIDNIGHT, LocalTime.of(3, 0)),
                            new BusinessDayTime(DayOfWeek.SATURDAY, LocalTime.of(19, 0), LocalTime.MAX),
                            new BusinessDayTime(DayOfWeek.SUNDAY, LocalTime.MIDNIGHT, LocalTime.of(3, 0)),
                            new BusinessDayTime(DayOfWeek.SUNDAY, LocalTime.of(20, 0), LocalTime.MAX)
                    ),
                    50,
                    "cocktails@pussers.info",
                    "089 220500"
            );
            bar1.setManagerId(managerBar1.getId());
            logger.info("Preloading {}", eateryRepository.save(bar1));

            Eatery cafe1 = new Eatery(
                    Eatery.Type.CAFE,
                    "Café Anna Blume",
                    "Kollwitzstrasse 83, 10435 Berlin",
                    Set.of(
                            new BusinessDayTime(DayOfWeek.MONDAY, LocalTime.of(9, 0), LocalTime.of(21, 0)),
                            new BusinessDayTime(DayOfWeek.TUESDAY, LocalTime.of(9, 0), LocalTime.of(21, 0)),
                            new BusinessDayTime(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(21, 0)),
                            new BusinessDayTime(DayOfWeek.THURSDAY, LocalTime.of(9, 0), LocalTime.of(21, 0))
                    ),
                    50,
                    "info@cafe-anna-blume.de",
                    "030 44048749"
            );
            cafe1.setManagerId(managerCafe1.getId());
            logger.info("Preloading {}", eateryRepository.save(cafe1));

            // Preload customer entities into the database
            logger.info("Preloading {}", customerRepository.save(
                    new Customer(
                            "Max",
                            "Mustermann",
                            "maxMustermann",
                            "max1985",
                            "+4915212345678"
                    )
            ));
            logger.info("Preloading {}", customerRepository.save(
                    new Customer(
                            "Alexandra",
                            "Ullrich",
                            "ullrich-A",
                            "p455w0rd",
                            "+4915312345678"
                    )
            ));

            // Preload customer review entities for an eatery
            logger.info("Preloading {}", reviewRepository.save(
                    new Review(1, 1, "Best place in town", 5)
            ));
            logger.info("Preloading {}", reviewRepository.save(
                    new Review(2, 2, "the cocktails taste average", 3)
            ));
        };
    }
}
