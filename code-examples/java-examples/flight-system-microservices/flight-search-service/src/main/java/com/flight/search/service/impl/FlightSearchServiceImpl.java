package com.flight.search.service.impl;

import com.flight.search.config.RedisClient;
import com.flight.search.dto.FlightDto;
import com.flight.search.exceptions.FlightApiException;
import com.flight.search.mappers.FlightSearchMapper;
import com.flight.search.repo.FlightSearchRepo;
import com.flight.search.runner.GraphBuilder;
import com.flight.search.service.FlightSearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

import static java.time.Duration.ofHours;
import static java.util.Optional.ofNullable;

@Service
@RequiredArgsConstructor
@Slf4j
public class FlightSearchServiceImpl implements FlightSearchService {
    private final RedisClient redisClient;
    private final FlightSearchRepo flightSearchRepo;
    private final FlightSearchMapper flightSearchMapper;
    private final GraphBuilder graphBuilder;


    @Override
    public FlightDto getFlightByFlightNumber(String flightNumber) {
        return ofNullable(redisClient.get(flightNumber))
                .map(t -> (FlightDto) t)
                .orElseGet(() -> {
                    log.info("Fetching from DB");

                    return flightSearchRepo.findByFlightNumber(flightNumber)
                            .map(flightSearchMapper::convertToFlightDto)
                            .map(flightDto ->
                                    redisClient.setAndGet(flightNumber, flightDto, ofHours(10).toHours()))
                            .orElseThrow(() -> new FlightApiException("No Flight found with provided flight number"));
                });
    }

    @Override
    public FlightDto getFlightById(Integer flightId) {
        return flightSearchRepo.findById(flightId)
                .map(flightSearchMapper::convertToFlightDto)
                .orElseThrow(() -> new FlightApiException("No Flight found with provided flight id"));
    }

    @Override
    public List<FlightDto> getAllFlights() {
        return flightSearchRepo.findAll().stream()
                .map(flightSearchMapper::convertToFlightDto).toList();
    }

    @Override
    public List<FlightDto> getAllFlightsFromTo(String departureAirport, String arrivalAirport) {
        return flightSearchRepo.findAllByDepartureAirportAndAndArrivalAirport(departureAirport, arrivalAirport)
                .stream().map(flightSearchMapper::convertToFlightDto).toList();
    }

    @Override
    public List<String> shortestPath(String from, String to) {
        return graphBuilder.findPath(from, to);
    }

}
