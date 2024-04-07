package org.booking.reservation.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "periods")
@Data
public class ReservationPeriod {
    @Id
    private String ID;
    private String value;
}
