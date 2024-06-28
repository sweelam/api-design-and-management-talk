package com.flight.search.api;

import com.flight.search.dto.FlightDto;
import com.flight.search.service.FlightSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.List;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isEmpty;


@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class FlightSearchController {

    private final FlightSearchService flightSearchService;

    @GetMapping
    public ResponseEntity<List<FlightDto>> getFlights(@RequestParam(required = false) String flightNumber) {
        return ResponseEntity.ok(
                (isEmpty(flightNumber) || isBlank(flightNumber)) ?
                        flightSearchService.getAllFlights() :
                        List.of(flightSearchService.getFlightByFlightNumber(flightNumber))
        );
    }

    @GetMapping("{flightId}")
    public ResponseEntity<FlightDto> getFlightById(@PathVariable Integer flightId) {
        return ResponseEntity.ok(flightSearchService.getFlightById(flightId));
    }

    @GetMapping("direct-path")
    public DeferredResult<List<FlightDto>> getFlightsFromTo(@RequestParam String from, @RequestParam String to) {
        return ApiResponseUtils.toResponse(flightSearchService.getAllFlightsFromTo(from, to));
    }


    @GetMapping("shortest-path")
    public DeferredResult<List<String>> getShortestFlightPathFromTo(
            @RequestParam String from, @RequestParam String to) {
        return ApiResponseUtils.toResponse(flightSearchService.shortestPath(from, to));
    }

}
