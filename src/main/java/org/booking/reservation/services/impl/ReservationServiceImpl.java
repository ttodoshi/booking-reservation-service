package org.booking.reservation.services.impl;

import lombok.RequiredArgsConstructor;
import org.booking.reservation.dto.ReservationPeriodDto;
import org.booking.reservation.exceptions.classes.ReservationExistsException;
import org.booking.reservation.exceptions.classes.ReservationPeriodNotFoundException;
import org.booking.reservation.exceptions.classes.WrongDateException;
import org.booking.reservation.models.Reservation;
import org.booking.reservation.models.ReservationPeriod;
import org.booking.reservation.repositories.ReservationPeriodRepository;
import org.booking.reservation.repositories.ReservationRepository;
import org.booking.reservation.services.api.ReservationService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {
    private final ReservationRepository reservationRepository;
    private final ReservationPeriodRepository reservationPeriodRepository;
    private final ModelMapper mapper;

    public List<ReservationPeriodDto> findPeriods(LocalDate date) {
        List<String> reservedPeriods = reservationRepository
                .findByDate(date)
                .stream()
                .map(r -> r.getReservationPeriod().getID())
                .toList();
        return reservationPeriodRepository
                .findReservationPeriodsByIDNotIn(reservedPeriods)
                .stream()
                .map(r -> mapper.map(
                        r, ReservationPeriodDto.class
                )).toList();
    }

    @Override
    public String saveReservation(String userID, LocalDate date, String periodId) {
        if (date.isBefore(LocalDate.now())) {
            throw new WrongDateException();
        }

        ReservationPeriod reservationPeriod = reservationPeriodRepository
                .findById(periodId)
                .orElseThrow(ReservationPeriodNotFoundException::new);

        reservationRepository.findReservationByDateAndReservationPeriod(
                date, reservationPeriod
        ).ifPresent(r -> {
            throw new ReservationExistsException();
        });

        return reservationRepository.save(
                new Reservation(date, reservationPeriod, userID)
        ).getID();
    }
}
