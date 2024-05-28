package org.booking.reservation.services.api;

import java.time.LocalDate;
import java.util.List;

import org.booking.reservation.dto.ReservationDto;
import org.booking.reservation.dto.ReservationPeriodDto;

public interface ReservationService {
    List<ReservationPeriodDto> findPeriods(LocalDate date);

    List<ReservationDto> findReservations(String userID);

    String saveReservation(String userID, LocalDate date, String periodID);

    void deleteReservation(String userID, String reservationID);
}
