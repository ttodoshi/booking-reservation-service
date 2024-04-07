package org.booking.reservation.exceptions.classes;

public class ReservationExistsException extends RuntimeException {
    public ReservationExistsException() {
        super("Reservation already exists");
    }
}
