package org.booking.reservation.repositories;

import org.booking.reservation.models.Reservation;
import org.booking.reservation.models.ReservationPeriod;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends MongoRepository<Reservation, String> {
    List<Reservation> findByDate(LocalDate date);

    Optional<Reservation> findReservationByDateAndReservationPeriod(LocalDate date, ReservationPeriod reservationPeriod);
}
