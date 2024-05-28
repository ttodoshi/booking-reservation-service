package org.booking.reservation.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDto {
    private String ID;
    private LocalDate date;
    private ReservationPeriodDto reservationPeriod;
    private String userID;
}
