package com.flight.search.api;

import com.flight.search.dto.FlightDto;
import com.flight.search.service.FlightSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.apache.logging.log4j.util.Strings.isBlank;
import static org.springframework.util.ObjectUtils.isEmpty;


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

//    @PostMapping("api/v1/flights/")
//    public ResponseEntity<FlightDto> bookNewFlight(@RequestBody FlightDto flightDto) {
//        if (isEmpty(flightDto.customerEmail())) {
//            throw new FlightApiException("No customer found with provided email", HttpStatus.NOT_FOUND);
//        }
//
//        FlightDto flightDtoAdded = new FlightDto(UUID.randomUUID(), flightDto.flightName(),
//                null == flightDto.time() ? Instant.now() : flightDto.time(), flightDto.customerEmail());
//
//        flightSearchService.bookNewFlight(flightDtoAdded);
//
//        return new ResponseEntity<>(flightDtoAdded, HttpStatus.CREATED);
//    }
}
