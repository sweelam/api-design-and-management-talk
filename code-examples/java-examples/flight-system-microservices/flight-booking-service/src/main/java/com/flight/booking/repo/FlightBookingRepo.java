package com.flight.booking.repo;

import com.flight.booking.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlightBookingRepo extends JpaRepository<Booking, Integer> {
}
