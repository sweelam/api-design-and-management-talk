package com.flight.booking.api;


import com.flight.booking.dto.BookingDto;
import com.flight.booking.service.FlightBookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.List;

import static com.flight.booking.api.ApiResponseUtils.toResponse;
import static java.util.Objects.nonNull;

@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
@Validated
public class FlightBookingController {
    private final FlightBookingService flightBookingService;

    @GetMapping
    public DeferredResult<List<BookingDto>> getAllBookings(@RequestParam(required = false) Integer bookingId) {
        return nonNull(bookingId) ?
                toResponse(List.of(flightBookingService.getBookingById(bookingId))) :
                toResponse(flightBookingService.getBookings());
    }

    @PostMapping
    public DeferredResult<ResponseEntity<BookingDto>> createBooking(@Valid @RequestBody BookingDto bookingDto) {
        return toResponse(flightBookingService.createBooking(bookingDto), HttpStatus.CREATED);
    }
}
