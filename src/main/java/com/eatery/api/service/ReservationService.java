package com.eatery.api.service;

import com.eatery.api.dto.CreateReservationRequest;
import com.eatery.api.dto.UpdateReservationRequest;
import com.eatery.entity.Reservable;
import com.eatery.exception.ReservationBadRequestException;
import com.eatery.exception.ReservationNotFoundException;
import com.eatery.entity.Customer;
import com.eatery.entity.Eatery;
import com.eatery.entity.Reservation;
import com.eatery.notification.NotificationHandler;
import com.eatery.repository.CustomerRepository;
import com.eatery.repository.EateryRepository;
import com.eatery.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static com.eatery.entity.Reservation.Status.CANCELLED;
import static com.eatery.entity.Reservation.Status.CONFIRMED;

/**
 * Service class for handling change request of Reservation object
 */
@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private EateryRepository eateryRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    private NotificationHandler notificationHandler;

    public List<Reservation> getAll() {
        return reservationRepository.findAll();
    }

    public Reservation get(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException(id));
    }

    public Reservation create(CreateReservationRequest reservationRequest) {
        Eatery reservationEatery = eateryRepository.findById(reservationRequest.getEateryId())
                .orElseThrow(() -> new ReservationBadRequestException("eateryId"));

        Customer customer = customerRepository.findById(reservationRequest.getCustomerId())
                .orElseThrow(() -> new ReservationBadRequestException("customerId"));

        LocalDateTime reservationTime = reservationRequest.getReservationDateTime();
        int guestNumber = reservationRequest.getGuestNumber();
        checkAvailability(reservationEatery, reservationTime, guestNumber);

        Reservation newReservation = new Reservation(
                customer,
                reservationEatery,
                reservationTime,
                guestNumber
        );
        newReservation.setStatus(CONFIRMED);
        sendMessage(customer.getPhoneNumber(), newReservation);
        return reservationRepository.save(newReservation);
    }

    public Reservation replace(UpdateReservationRequest updateReservation, Long id) {
        LocalDateTime updatedTime = updateReservation.getDateTime();
        int updatedGuestNumber = updateReservation.getGuestNumber();
        return reservationRepository.findById(id)
                .map(reservation -> {
                    checkAvailability(reservation.getEatery(), updatedTime, updatedGuestNumber);

                    reservation.setReservationDateTime(updatedTime);
                    reservation.setGuestNumber(updatedGuestNumber);
                    reservation.setStatus(CONFIRMED);
                    sendMessage(reservation.getCustomer().getPhoneNumber(), reservation);
                    return reservationRepository.save(reservation);
                })
                .orElseThrow(() -> new ReservationNotFoundException(id));
    }

    public Reservation complete(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException(id));

        if (reservation.getReservationDateTime().isBefore(LocalDateTime.now()) &&
                reservation.getStatus().equals(CONFIRMED)) {

            reservation.setStatus(Reservation.Status.COMPLETED);
            return reservationRepository.save(reservation);
        }
        return reservation;
    }

    public Reservation cancel(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException(id));

        if (reservation.getStatus().equals(CONFIRMED)) {
            reservation.setStatus(CANCELLED);
            reservationRepository.save(reservation);
        }
        sendMessage(reservation.getCustomer().getPhoneNumber(), reservation);
        return reservation;
    }

    public void delete(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException(id));

        if (reservation.getStatus().equals(Reservation.Status.COMPLETED) ||
                reservation.getStatus().equals(CANCELLED)) {

            reservationRepository.deleteById(id);
        }
    }

    /**
     * Check availability of Eatery by using strategy pattern Reservable
     * @param reservableObject Reservable interface object
     * @param atDateTime time to check availability
     * @param guestNumber guest number to check availability
     */
    private void checkAvailability(Reservable reservableObject, LocalDateTime atDateTime, int guestNumber) {
        final LocalDateTime tomorrow = LocalDateTime.now().plusDays(1);

        if (!reservableObject.isOpenAt(atDateTime) || atDateTime.isBefore(tomorrow)) {
            throw new ReservationBadRequestException(atDateTime);
        }

        boolean fullyBooked = reservableObject.isFullyBooked(atDateTime, guestNumber);
        if (fullyBooked) {
            throw new ReservationBadRequestException(guestNumber);
        }
    }

    /**
     * send message to customer about his reservation by using command pattern via NotificationHandler
     * @param customerPhoneNumber customer phone number
     * @param customerReservation customer's reservation
     */
    private void sendMessage(String customerPhoneNumber, Reservation customerReservation) {
        String prefixText = getTemplateMessage(customerReservation);
        switch (customerReservation.getStatus()) {
            case CONFIRMED ->
                    notificationHandler.sendSMS(
                            customerPhoneNumber,
                            prefixText + CONFIRMED
                    );
            case CANCELLED ->
                    notificationHandler.sendSMS(
                            customerPhoneNumber,
                            prefixText + CANCELLED
                    );
            case COMPLETED -> {}
        }
    }

    private static String getTemplateMessage(Reservation reservation) {
        LocalDateTime reservationDateTime = reservation.getReservationDateTime();

        // Example: Your reservation on Monday, 1 January 2025 at 17:00 for 4 persons is CONFIRMED
        return "Your reservation on " + reservationDateTime.getDayOfWeek() + ", " +
                reservationDateTime.getDayOfMonth() + " " + reservationDateTime.getMonth().getValue() + " " + reservationDateTime.getYear() +
                " at " + reservationDateTime.getHour() + ":" + reservationDateTime.getMinute() +
                " for " + reservation.getGuestNumber() + " persons is ";
    }
}
