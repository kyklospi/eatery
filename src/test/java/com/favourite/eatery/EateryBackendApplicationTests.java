package com.favourite.eatery;

import com.favourite.eatery.controller.AdminController;
import com.favourite.eatery.controller.EateryController;
import com.favourite.eatery.controller.ReservationController;
import com.favourite.eatery.controller.UserController;
import com.favourite.eatery.repository.AdminRepository;
import com.favourite.eatery.repository.AppUserRepository;
import com.favourite.eatery.repository.EateryRepository;
import com.favourite.eatery.repository.ReservationRepository;
import com.favourite.eatery.service.AdminService;
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
	private UserController userController;
	@Autowired
	private ReservationController reservationController;
	@Autowired
	private AdminController adminController;

	@Autowired
	private AdminService adminService;
	@Autowired
	private ReservationService reservationService;

	@Autowired
	private EateryRepository eateryRepository;
	@Autowired
	private AppUserRepository appUserRepository;
	@Autowired
	private ReservationRepository reservationRepository;
	@Autowired
	private AdminRepository adminRepository;

	@Test
	void contextLoads() {
		assertNotNull(eateryController);
		assertNotNull(userController);
		assertNotNull(reservationController);
		assertNotNull(adminController);

		assertNotNull(adminService);
		assertNotNull(reservationService);

		assertNotNull(eateryRepository);
		assertNotNull(appUserRepository);
		assertNotNull(reservationRepository);
		assertNotNull(adminRepository);
	}

}
