package com.eatery;

import com.eatery.api.controller.EateryManagerController;
import com.eatery.api.controller.EateryController;
import com.eatery.api.controller.ReservationController;
import com.eatery.api.controller.CustomerController;
import com.eatery.api.service.CustomerService;
import com.eatery.api.service.EateryService;
import com.eatery.notification.NotificationHandler;
import com.eatery.repository.*;
import com.eatery.api.service.EateryManagerService;
import com.eatery.api.service.ReservationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("local")
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
	private EateryService eateryService;
	@Autowired
	private CustomerService customerService;
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
	@Autowired
	private ReservationHistoryRepository historyRepository;

	@Autowired
	private NotificationHandler notificationHandler;

	@Test
	void contextLoads() {
		assertNotNull(eateryController);
		assertNotNull(customerController);
		assertNotNull(reservationController);
		assertNotNull(eateryManagerController);

		assertNotNull(eateryService);
		assertNotNull(customerService);
		assertNotNull(eateryManagerService);
		assertNotNull(reservationService);

		assertNotNull(eateryRepository);
		assertNotNull(customerRepository);
		assertNotNull(reservationRepository);
		assertNotNull(eateryManagerRepository);
		assertNotNull(historyRepository);

		assertNotNull(notificationHandler);
	}

}
