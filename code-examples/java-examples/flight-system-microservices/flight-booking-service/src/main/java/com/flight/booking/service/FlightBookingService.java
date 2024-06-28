package com.flight.booking.service;

import com.flight.booking.dto.BookingDto;

import java.util.List;

public interface FlightBookingService {
    List<BookingDto> getBookings();

    BookingDto getBookingById(Integer bookingId);

    BookingDto createBooking(BookingDto bookingDto);

    BookingDto updateBooking(BookingDto bookingDto);
}
