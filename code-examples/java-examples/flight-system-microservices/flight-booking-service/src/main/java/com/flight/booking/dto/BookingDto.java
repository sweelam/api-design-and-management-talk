package com.flight.booking.dto;

import com.flight.booking.dto.enums.BookingStatus;

import java.time.Instant;

public record BookingDto(
   Integer bookingId, Integer userId, Integer flightId, Instant bookingTime, BookingStatus status
) {}
