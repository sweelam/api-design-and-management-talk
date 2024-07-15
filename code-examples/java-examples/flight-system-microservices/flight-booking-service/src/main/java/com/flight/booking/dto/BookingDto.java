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

//   @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
   Instant bookingTime,
   BookingStatus status
) {}
