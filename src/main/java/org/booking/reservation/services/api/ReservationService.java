package org.booking.reservation.services.api;

import org.booking.reservation.dto.ReservationPeriodDto;

import java.time.LocalDate;
import java.util.List;

public interface ReservationService {
    List<ReservationPeriodDto> findPeriods(LocalDate date);

    String saveReservation(String userID, LocalDate date, String periodId);
}
