package org.booking.reservation.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.booking.reservation.models.Reservation;
import org.booking.reservation.models.ReservationPeriod;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReservationRepository extends MongoRepository<Reservation, String> {
    List<Reservation> findByDate(LocalDate date);

    List<Reservation> findByUserIDAndDateAfter(String userID, LocalDate date);

    Boolean existsReservationByDateAndReservationPeriod(LocalDate date, ReservationPeriod reservationPeriod);
}
