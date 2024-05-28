package org.booking.reservation.services.impl;

import java.time.LocalDate;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.booking.reservation.dto.ReservationDto;
import org.booking.reservation.dto.ReservationPeriodDto;
import org.booking.reservation.exceptions.classes.NotYourReservationException;
import org.booking.reservation.exceptions.classes.ReservationExistsException;
import org.booking.reservation.exceptions.classes.ReservationNotFoundException;
import org.booking.reservation.exceptions.classes.ReservationPeriodNotFoundException;
import org.booking.reservation.exceptions.classes.WrongDateException;
import org.booking.reservation.models.Reservation;
import org.booking.reservation.models.ReservationPeriod;
import org.booking.reservation.repositories.ReservationPeriodRepository;
import org.booking.reservation.repositories.ReservationRepository;
import org.booking.reservation.services.api.ReservationService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {
    private final ReservationRepository reservationRepository;
    private final ReservationPeriodRepository reservationPeriodRepository;
    private final ModelMapper mapper;

    @Override
    public List<ReservationPeriodDto> findPeriods(LocalDate date) {
        List<String> reservedPeriods =
                reservationRepository.findByDate(date).stream()
                        .map(r -> r.getReservationPeriod().getID())
                        .toList();
        return reservationPeriodRepository.findReservationPeriodsByIDNotIn(reservedPeriods).stream()
                .map(r -> mapper.map(r, ReservationPeriodDto.class))
                .toList();
    }

    @Override
    public List<ReservationDto> findReservations(String userID) {
        return reservationRepository
                .findByUserIDAndDateAfter(userID, LocalDate.now().minusDays(1))
                .stream()
                .map(r -> mapper.map(r, ReservationDto.class))
                .toList();
    }

    @Override
    public String saveReservation(String userID, LocalDate date, String periodID) {
        if (date.isBefore(LocalDate.now())) {
            throw new WrongDateException();
        }

        ReservationPeriod reservationPeriod = reservationPeriodRepository
                .findById(periodID)
                .orElseThrow(ReservationPeriodNotFoundException::new);

        if (reservationRepository.existsReservationByDateAndReservationPeriod(date, reservationPeriod)) {
            throw new ReservationExistsException();
        }

        return reservationRepository.save(new Reservation(date, reservationPeriod, userID)).getID();
    }

    @Override
    public void deleteReservation(String userID, String reservationID) {
        Reservation reservation =
                reservationRepository
                        .findById(reservationID)
                        .orElseThrow(ReservationNotFoundException::new);
        if (!reservation.getUserID().equals(userID)) {
            throw new NotYourReservationException();
        }
        if (reservation.getDate().isBefore(LocalDate.now())) {
            throw new ReservationNotFoundException();
        }
        reservationRepository.deleteById(reservationID);
    }
}
