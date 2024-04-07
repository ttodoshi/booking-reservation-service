package org.booking.reservation.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.time.LocalDate;

@Document(collection = "reservations")
@Data
@NoArgsConstructor
public class Reservation {
    @Id
    private String ID;
    private LocalDate date;
    @DocumentReference
    private ReservationPeriod reservationPeriod;
    private String userID;

    public Reservation(LocalDate date, ReservationPeriod reservationPeriod, String userID) {
        this.date = date;
        this.reservationPeriod = reservationPeriod;
        this.userID = userID;
    }
}
