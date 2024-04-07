package org.booking.reservation.exceptions.classes;

public class ReservationPeriodNotFoundException extends RuntimeException {
    public ReservationPeriodNotFoundException() {
        super("Reservation period not found");
    }
}
