package com.flight.booking.api;


import com.flight.booking.dto.BookingDto;
import com.flight.booking.service.FlightBookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.List;

@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class FlightBookingController {
    private final FlightBookingService flightBookingService;

    @GetMapping
    public DeferredResult<List<BookingDto>> getAllBookings() {
        DeferredResult<List<BookingDto>> result = new DeferredResult<>();

        result.setResult(flightBookingService.getBookings());

        return result;
    }
}
