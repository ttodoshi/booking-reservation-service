package org.booking.reservation.exceptions.classes;

public class WrongDateException extends RuntimeException {
    public WrongDateException() {
        super("Past date is not allowed");
    }
}
