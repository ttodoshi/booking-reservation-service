package org.booking.reservation.exceptions.classes;

public class NotYourReservationException extends RuntimeException {
    public NotYourReservationException() {
        super("Not your reservation");
    }
}
