package com.flight.search.repo;

import com.flight.search.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FlightSearchRepo extends JpaRepository<Flight, Integer> {
    Optional<Flight> findByFlightNumber(String flightNumber);
    List<Flight> findAllByDepartureAirportAndAndArrivalAirport(String departureAirport, String arrivalAirport);
}
