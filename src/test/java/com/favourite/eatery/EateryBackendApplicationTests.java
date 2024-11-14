package com.favourite.eatery;

import com.favourite.eatery.controller.EateryManagerController;
import com.favourite.eatery.controller.EateryController;
import com.favourite.eatery.controller.ReservationController;
import com.favourite.eatery.controller.CustomerController;
import com.favourite.eatery.repository.EateryManagerRepository;
import com.favourite.eatery.repository.CustomerRepository;
import com.favourite.eatery.repository.EateryRepository;
import com.favourite.eatery.repository.ReservationRepository;
import com.favourite.eatery.service.EateryManagerService;
import com.favourite.eatery.service.ReservationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class EateryBackendApplicationTests {
	@Autowired
	private EateryController eateryController;
	@Autowired
	private CustomerController customerController;
	@Autowired
	private ReservationController reservationController;
	@Autowired
	private EateryManagerController eateryManagerController;

	@Autowired
	private EateryManagerService eateryManagerService;
	@Autowired
	private ReservationService reservationService;

	@Autowired
	private EateryRepository eateryRepository;
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private ReservationRepository reservationRepository;
	@Autowired
	private EateryManagerRepository eateryManagerRepository;

	@Test
	void contextLoads() {
		assertNotNull(eateryController);
		assertNotNull(customerController);
		assertNotNull(reservationController);
		assertNotNull(eateryManagerController);

		assertNotNull(eateryManagerService);
		assertNotNull(reservationService);

		assertNotNull(eateryRepository);
		assertNotNull(customerRepository);
		assertNotNull(reservationRepository);
		assertNotNull(eateryManagerRepository);
	}

}
