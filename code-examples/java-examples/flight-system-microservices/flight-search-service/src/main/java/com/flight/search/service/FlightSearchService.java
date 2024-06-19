package com.flight.search.service;

import com.flight.search.dto.FlightDto;

import java.util.List;

public interface FlightSearchService {
    FlightDto getFlightByFlightNumber(String flightNumber);
    List<FlightDto> getAllFlights();
    List<FlightDto> getAllFlightsFromTo(String departureAirport, String arrivalAirport);
}
