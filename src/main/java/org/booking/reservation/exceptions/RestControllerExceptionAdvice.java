package org.booking.reservation.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.booking.reservation.exceptions.classes.NotYourReservationException;
import org.booking.reservation.exceptions.classes.ReservationExistsException;
import org.booking.reservation.exceptions.classes.ReservationNotFoundException;
import org.booking.reservation.exceptions.classes.ReservationPeriodNotFoundException;
import org.booking.reservation.exceptions.classes.WrongDateException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestControllerExceptionAdvice {
    @ExceptionHandler({
            IllegalArgumentException.class,
            MethodArgumentNotValidException.class,
            HttpMessageNotReadableException.class,
            ConstraintViolationException.class
    })
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(HttpServletRequest request) {
        return new ResponseEntity<>(
                new ErrorResponse(
                        HttpStatus.BAD_REQUEST.value(),
                        HttpStatus.BAD_REQUEST.getReasonPhrase(),
                        "wrong data",
                        request.getRequestURI()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(HttpServletRequest request) {
        return new ResponseEntity<>(
                new ErrorResponse(
                        HttpStatus.FORBIDDEN.value(),
                        HttpStatus.FORBIDDEN.getReasonPhrase(),
                        "you don't have access",
                        request.getRequestURI()),
                HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({WrongDateException.class, ReservationExistsException.class})
    public ResponseEntity<ErrorResponse> handleBadRequestException(
            HttpServletRequest request, Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                        new ErrorResponse(
                                HttpStatus.BAD_REQUEST.value(),
                                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                                e.getMessage(),
                                request.getRequestURI())
                );
    }

    @ExceptionHandler(NotYourReservationException.class)
    public ResponseEntity<ErrorResponse> handleForbiddenException(HttpServletRequest request) {
        return new ResponseEntity<>(
                new ErrorResponse(
                        HttpStatus.FORBIDDEN.value(),
                        HttpStatus.FORBIDDEN.getReasonPhrase(),
                        "you don't have access",
                        request.getRequestURI()),
                HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({ReservationPeriodNotFoundException.class, ReservationNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleNotFoundException(HttpServletRequest request, Exception e) {
        return new ResponseEntity<>(
                new ErrorResponse(
                        HttpStatus.NOT_FOUND.value(),
                        HttpStatus.NOT_FOUND.getReasonPhrase(),
                        e.getMessage(),
                        request.getRequestURI()),
                HttpStatus.NOT_FOUND);
    }
}
