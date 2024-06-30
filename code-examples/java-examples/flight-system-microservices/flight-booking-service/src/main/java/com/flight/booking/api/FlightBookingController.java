package com.flight.booking.api;


import com.flight.booking.dto.BookingDto;
import com.flight.booking.service.FlightBookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static java.util.Objects.nonNull;

@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
@Slf4j
@Validated
public class FlightBookingController {
    private final FlightBookingService flightBookingService;

    @GetMapping
    public ResponseEntity<List<BookingDto>> getAllBookings(@RequestParam(required = false) Integer bookingId) {
        return nonNull(bookingId) ?
                ResponseEntity.ok(List.of(flightBookingService.getBookingById(bookingId))) :
                ResponseEntity.ok(flightBookingService.getBookings());
    }

    @PostMapping
    public CompletableFuture<ResponseEntity<BookingDto>> createBooking(@Valid @RequestBody BookingDto bookingDto) {

        return flightBookingService.createBooking(bookingDto)
                .thenApply(bookingResponse -> new ResponseEntity<>(bookingResponse, HttpStatus.CREATED));

    }
}
