package com.flight.booking.service;

import com.flight.booking.dto.BookingDto;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface FlightBookingService {
    List<BookingDto> getBookings();

    BookingDto getBookingById(Integer bookingId);

    CompletableFuture<BookingDto> createBooking(BookingDto bookingDto);

    BookingDto updateBooking(BookingDto bookingDto);
}
