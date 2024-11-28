package com.eatery;

import com.eatery.api.controller.EateryManagerController;
import com.eatery.api.controller.EateryController;
import com.eatery.api.controller.ReservationController;
import com.eatery.api.controller.CustomerController;
import com.eatery.repository.EateryManagerRepository;
import com.eatery.repository.CustomerRepository;
import com.eatery.repository.EateryRepository;
import com.eatery.repository.ReservationRepository;
import com.eatery.api.service.EateryManagerService;
import com.eatery.api.service.ReservationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
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
