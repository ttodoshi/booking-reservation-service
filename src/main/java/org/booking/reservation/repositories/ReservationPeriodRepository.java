package org.booking.reservation.repositories;

import org.booking.reservation.models.ReservationPeriod;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ReservationPeriodRepository extends MongoRepository<ReservationPeriod, String> {
    List<ReservationPeriod> findReservationPeriodsByIDNotIn(List<String> ids);
}
