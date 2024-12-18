package com.eatery.service;

import com.eatery.api.dto.CreateReservationRequest;
import com.eatery.api.dto.UpdateReservationRequest;
import com.eatery.entity.*;
import com.eatery.exception.ReservationBadRequestException;
import com.eatery.exception.ReservationNotFoundException;
import com.eatery.notification.NotificationHandler;
import com.eatery.repository.CustomerRepository;
import com.eatery.repository.EateryRepository;
import com.eatery.repository.ReservationHistoryRepository;
import com.eatery.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static com.eatery.entity.Reservation.Status.*;

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
    ReservationHistoryRepository historyRepository;
    @Autowired
    private NotificationHandler notificationHandler;

    /**
     * Retrieves all reservations from the database.
     * @return A list of all reservations.
     */
    public List<Reservation> getAll() {
        return reservationRepository.findAll();
    }

    /**
     * Retrieves a reservation by its ID.
     * @param id The ID of the reservation to retrieve.
     * @return The Reservation object with the specified ID.
     * @throws ReservationNotFoundException if the reservation with the specified ID does not exist.
     */
    public Reservation get(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(ReservationNotFoundException::new);
    }

    /**
     * Creates a new reservation based on the provided reservation request.
     * It also checks the availability of the eatery before creating the reservation.
     * @param reservationRequest The reservation details provided by the customer.
     * @return The newly created Reservation object.
     * @throws ReservationBadRequestException if the eatery or customer ID is invalid or if the reservation time is unavailable.
     */
    public Reservation create(CreateReservationRequest reservationRequest) {
        Eatery reservationEatery = eateryRepository.findById(reservationRequest.getEateryId())
                .orElseThrow(() -> new ReservationBadRequestException("eateryId"));

        Customer customer = customerRepository.findById(reservationRequest.getCustomerId())
                .orElseThrow(() -> new ReservationBadRequestException("customerId"));

        LocalDateTime reservationTime = reservationRequest.getReservationDateTime();
        int guestNumber = reservationRequest.getGuestNumber();
        checkAvailability(reservationEatery, reservationTime, guestNumber);

        Reservation newReservation = new Reservation(
                customer.getId(),
                reservationEatery.getId(),
                reservationTime,
                guestNumber
        );
        newReservation.setStatus(CONFIRMED);
        sendMessage(customer.getPhoneNumber(), newReservation);
        Reservation savedReservation = reservationRepository.save(newReservation);
        ReservationHistory history = new ReservationHistory(
                savedReservation.getId(),
                savedReservation.getCustomerId(),
                savedReservation.getEateryId(),
                savedReservation.getReservationDateTime(),
                savedReservation.getGuestNumber(),
                savedReservation.getStatus()
        );
        historyRepository.save(history);
        return savedReservation;
    }

    /**
     * Updates an existing reservation based on the provided update request.
     * It checks for availability before applying the changes.
     * @param updateReservation The updated reservation details.
     * @param id The ID of the reservation to update.
     * @return The updated Reservation object.
     * @throws ReservationNotFoundException if the reservation with the specified ID does not exist.
     */
    public Reservation replace(UpdateReservationRequest updateReservation, Long id) {
        LocalDateTime updatedTime = updateReservation.getDateTime();
        int updatedGuestNumber = updateReservation.getGuestNumber();

        Reservation reservation = reservationRepository.findById(id).orElseThrow(ReservationNotFoundException::new);

        Customer customer = customerRepository.findById(reservation.getCustomerId())
                .orElseThrow(() -> new ReservationBadRequestException("customerId"));

        Eatery reservedEatery = eateryRepository.findById(reservation.getEateryId())
                .orElseThrow(() -> new ReservationBadRequestException("eateryId"));

        checkAvailability(reservedEatery, updatedTime, updatedGuestNumber);

        reservation.setReservationDateTime(updatedTime);
        reservation.setGuestNumber(updatedGuestNumber);
        reservation.setStatus(CONFIRMED);
        sendMessage(customer.getPhoneNumber(), reservation);
        ReservationHistory history = new ReservationHistory(
                reservation.getId(),
                reservation.getCustomerId(),
                reservation.getEateryId(),
                reservation.getReservationDateTime(),
                reservation.getGuestNumber(),
                reservation.getStatus()
        );
        historyRepository.save(history);
        return reservationRepository.save(reservation);}

    /**
     * Marks a reservation as completed if the reservation time has passed and the status is CONFIRMED.
     * @param id The ID of the reservation to complete.
     * @return The updated Reservation object with status set to COMPLETED.
     * @throws ReservationNotFoundException if the reservation with the specified ID does not exist.
     */
    public Reservation complete(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(ReservationNotFoundException::new);

        if (reservation.getReservationDateTime().isAfter(LocalDateTime.now()) && !reservation.getStatus().equals(CONFIRMED)) {
            throw new ReservationBadRequestException("reservationDateTime or status");
        }

        reservation.setStatus(Reservation.Status.COMPLETED);
        ReservationHistory history = new ReservationHistory(
                reservation.getId(),
                reservation.getCustomerId(),
                reservation.getEateryId(),
                reservation.getReservationDateTime(),
                reservation.getGuestNumber(),
                reservation.getStatus()
        );
        historyRepository.save(history);
        return reservationRepository.save(reservation);
    }

    /**
     * Cancels a reservation if its status is CONFIRMED.
     * It also sends a notification to the customer.
     * @param id The ID of the reservation to cancel.
     * @return The updated Reservation object with status set to CANCELLED.
     * @throws ReservationNotFoundException if the reservation with the specified ID does not exist.
     */
    public Reservation cancel(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(ReservationNotFoundException::new);

        Customer customer = customerRepository.findById(reservation.getCustomerId())
                .orElseThrow(() -> new ReservationBadRequestException("customerId"));

        if (!reservation.getStatus().equals(CONFIRMED)) {
            throw new ReservationBadRequestException("status");
        }

        reservation.setStatus(CANCELLED);
        sendMessage(customer.getPhoneNumber(), reservation);
        ReservationHistory history = new ReservationHistory(
                reservation.getId(),
                reservation.getCustomerId(),
                reservation.getEateryId(),
                reservation.getReservationDateTime(),
                reservation.getGuestNumber(),
                reservation.getStatus()
        );
        historyRepository.save(history);
        return reservationRepository.save(reservation);
    }

    /**
     * Deletes a reservation if it has been completed or cancelled.
     * @param id The ID of the reservation to delete.
     * @throws ReservationNotFoundException if the reservation with the specified ID does not exist.
     */
    public void delete(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(ReservationNotFoundException::new);

        if (reservation.getStatus().equals(Reservation.Status.COMPLETED) ||
                reservation.getStatus().equals(CANCELLED)) {

            reservation.setStatus(DELETED);
            ReservationHistory history = new ReservationHistory(
                    reservation.getId(),
                    reservation.getCustomerId(),
                    reservation.getEateryId(),
                    reservation.getReservationDateTime(),
                    reservation.getGuestNumber(),
                    reservation.getStatus()
            );
            historyRepository.save(history);
            reservationRepository.deleteById(id);
        }
    }

    public List<ReservationHistory> history(Long id) {
        return historyRepository.findAll().stream()
                .filter(record -> record.getReservationId().equals(id))
                .sorted()
                .toList();
    }

    /**
     * Check availability of Eatery by using strategy pattern EateryReservationStrategy
     * @param eatery eatery to be reserved
     * @param reservationDateTime reservation time to check availability
     * @param guestNumber guest number to check availability
     * @throws ReservationBadRequestException if eatery is not available for reservation request
     */
    private void checkAvailability(Eatery eatery, LocalDateTime reservationDateTime, int guestNumber) {
        // Create ReservationContext using EateryReservationStrategy
        final ReservationContext reservationContext = new ReservationContext(new EateryReservationStrategy());
        final LocalDateTime tomorrow = LocalDateTime.now().plusDays(1);

        boolean isReservable = reservationContext.isReservable(
                reservationDateTime.isAfter(tomorrow),
                isOpenAt(eatery, reservationDateTime),
                isFullyBooked(eatery, reservationDateTime, guestNumber)
        );

        if (!isReservable) {
            throw new ReservationBadRequestException();
        }
    }

    /**
     * Checks if eatery is open at reservation time
     * @param eatery eatery to be reserved
     * @param reservationTime reservation time
     * @return true when eatery is open at reservation time
     */
    private boolean isOpenAt(Eatery eatery, LocalDateTime reservationTime) {
        return eatery.getBusinessDayTimes().stream()
                .anyMatch(it ->
                        it.openDay().equals(reservationTime.getDayOfWeek()) &&
                                reservationTime.getHour() >= it.openTime().getHour() &&
                                reservationTime.getMinute() >= it.openTime().getMinute() &&
                                reservationTime.getHour() <= it.closeTime().getHour() &&
                                reservationTime.getMinute() <= it.closeTime().getMinute()
                );
    }

    /**
     * Checks if eatery guest capacity is reached from 2 hours before reservation time until 2 hours after reservation time
     * @param eatery eatery to be booked
     * @param atTime new entry of reservation time
     * @param newGuestNumber new entry of guest number
     * @return true when eatery guest capacity is reached
     */
    private boolean isFullyBooked(Eatery eatery, LocalDateTime atTime, int newGuestNumber) {
        List<Reservation> confirmedReservationsAtDuration = getConfirmedReservationsAtTimeSlot(eatery, atTime.minusHours(2), atTime.plusHours(2));
        int totalGuestNumberAtDuration = countGuestNumber(confirmedReservationsAtDuration);

        return (totalGuestNumberAtDuration + newGuestNumber) > eatery.getGuestCapacity();
    }

    private List<Reservation> getConfirmedReservationsAtTimeSlot(Eatery eatery, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return eatery.getReservationList().stream()
                .filter(eateryReservation -> eateryReservation.getReservationDateTime().isAfter(startDateTime) &&
                        eateryReservation.getReservationDateTime().isBefore(endDateTime) &&
                        eateryReservation.getStatus().equals(Reservation.Status.CONFIRMED)
                )
                .toList();

    }

    private int countGuestNumber(List<Reservation> reservations) {
        int sum = 0;
        for (Reservation reservation : reservations) {
            sum += reservation.getGuestNumber();
        }
        return sum;
    }

    /**
     * send message to customer about his reservation by using command pattern via NotificationHandler
     * @param customerPhoneNumber customer phone number
     * @param customerReservation customer's reservation
     */
    private void sendMessage(String customerPhoneNumber, Reservation customerReservation) {
        String prefixText = getTemplateMessage(customerReservation);

        switch (customerReservation.getStatus()) {
            case CONFIRMED -> {
                String message = prefixText + CONFIRMED;
                notificationHandler.sendSMS(
                        customerPhoneNumber,
                        message
                );
                notificationHandler.save(customerReservation.getCustomerId(), customerReservation.getId(), message);
            }

            case CANCELLED -> {
                String message = prefixText + CANCELLED;
                notificationHandler.sendSMS(
                        customerPhoneNumber,
                        message
                );
                notificationHandler.save(customerReservation.getCustomerId(), customerReservation.getId(), message);
            }

            case COMPLETED -> {}
        }
    }

    /**
     * Generates the message template for a reservation, including date, time, and guest number.
     * @param reservation The reservation object to generate the message from.
     * @return A formatted string with reservation details.
     */
    private static String getTemplateMessage(Reservation reservation) {
        LocalDateTime reservationDateTime = reservation.getReservationDateTime();

        // Example: Your reservation on Monday, 1 January 2025 at 17:00 for 4 persons is CONFIRMED
        return "Your reservation on " + reservationDateTime.getDayOfWeek() + ", " +
                reservationDateTime.getDayOfMonth() + " " + reservationDateTime.getMonth().getValue() + " " + reservationDateTime.getYear() +
                " at " + reservationDateTime.getHour() + ":" + reservationDateTime.getMinute() +
                " for " + reservation.getGuestNumber() + " persons is ";
    }
}