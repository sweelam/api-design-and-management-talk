package com.flight.search.service;

import com.flight.search.dto.FlightDto;

import java.util.List;

public interface FlightSearchService {
    FlightDto getFlightByFlightNumber(String flightNumber);

    FlightDto getFlightById(Integer flightId);

    List<FlightDto> getAllFlights();

    List<FlightDto> getAllFlightsFromTo(String departureAirport, String arrivalAirport);

    List<String> shortestPath(String from, String to);
}
