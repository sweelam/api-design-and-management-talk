package com.flight.booking.dto;

import com.flight.booking.dto.enums.BookingStatus;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;

public record BookingDto(
   Integer bookingId,

   @NotNull(message = "userId can't be null")
   Integer userId,

   @NotNull(message = "flightId can't be null")
   Integer flightId,

   Instant bookingTime,
   BookingStatus status
) {}
