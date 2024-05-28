package org.booking.reservation.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.time.LocalDate;

import lombok.RequiredArgsConstructor;
import org.booking.reservation.dto.UserDto;
import org.booking.reservation.services.api.ReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/bookings")
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;

    @GetMapping("/{date}/periods/")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Find periods", tags = "reservation")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Find all periods",
                            content = {@Content(mediaType = "application/json")})
            })
    public ResponseEntity<?> findPeriods(@PathVariable LocalDate date) {
        return ResponseEntity.ok(reservationService.findPeriods(date));
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Find my reservations", tags = "reservation")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Find my reservations",
                            content = {@Content(mediaType = "application/json")})
            })
    public ResponseEntity<?> findReservations(@AuthenticationPrincipal UserDto currentUser) {
        return ResponseEntity.ok(reservationService.findReservations(currentUser.getUserID()));
    }

    @PostMapping("/{date}/periods/{periodID}/")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Save reservation", tags = "reservation")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Save reservation",
                            content = {@Content(mediaType = "text/plain")})
            })
    public ResponseEntity<?> saveReservation(
            @AuthenticationPrincipal UserDto currentUser,
            @PathVariable LocalDate date,
            @PathVariable String periodID) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.TEXT_PLAIN)
                .body(reservationService.saveReservation(currentUser.getUserID(), date, periodID));
    }

    @DeleteMapping("/{reservationID}/")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Delete reservation", tags = "reservation")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Delete reservation"
                    )
            })
    public ResponseEntity<?> deleteReservation(
            @AuthenticationPrincipal UserDto currentUser, @PathVariable String reservationID) {
        reservationService.deleteReservation(currentUser.getUserID(), reservationID);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
