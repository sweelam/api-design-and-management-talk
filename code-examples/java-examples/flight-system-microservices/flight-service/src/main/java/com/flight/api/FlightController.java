package com.flight.api;

import com.flight.dto.Flight;
import com.flight.exceptions.FlightApiException;
import com.flight.service.FlightService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static io.micrometer.core.instrument.util.StringUtils.isEmpty;

@RestController
@RequestMapping("api/v1/flights")
public class FlightController {
    private final List<Flight> allFlights = new ArrayList(
            List.of(
                    new Flight(UUID.fromString("558c85a2-b542-11ed-afa1-0242ac120002"), "cairo-827",
                            Instant.now().plus(15, ChronoUnit.DAYS), "md.ahmed@gmail.com"),

                    new Flight(UUID.fromString("94631732-b542-11ed-afa1-0242ac120002"), "tokyo-661",
                            Instant.now().plus(20, ChronoUnit.DAYS), "alex.mon@gmail.com"),

                    new Flight(UUID.fromString("bc0776c0-b542-11ed-afa1-0242ac120002"), "dubai-515",
                            Instant.now().plus(25, ChronoUnit.DAYS), "hesham.masoud@gmail.com")
            )
    );

    private final FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @GetMapping("")
    public ResponseEntity<List<Flight>> getAllFlights() {
        return ResponseEntity.ok(allFlights);
    }

    @GetMapping("/")
    public ResponseEntity<Flight> getFlightByCode(@RequestParam String flightName) {
        return ResponseEntity.ok(flightService.getFlightByName(flightName));
    }

    @PostMapping("api/v1/flights/")
    public ResponseEntity<Flight> bookNewFlight(@RequestBody Flight flight) {
        if (isEmpty(flight.customerEmail())) {
            throw new FlightApiException("No customer found with provided email", HttpStatus.NOT_FOUND);
        }

        Flight flightAdded = new Flight(UUID.randomUUID(), flight.flightName(),
                null == flight.time() ? Instant.now() : flight.time(), flight.customerEmail());

        flightService.bookNewFlight(flightAdded);

        return new ResponseEntity<>(flightAdded, HttpStatus.CREATED);
    }
}
