package com.flight.search.service;

import com.flight.search.config.RedisClient;
import com.flight.search.dto.CustomerResponse;
import com.flight.search.dto.Flight;
import com.flight.search.exceptions.FlightApiException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static java.lang.Thread.sleep;
import static java.util.Optional.ofNullable;

@Service
@RequiredArgsConstructor
public class FlightService {
    private final Logger logger = LoggerFactory.getLogger("RedisClient");

    private final CustomerService customerService;
    private final EmailService emailService;
    private final RedisClient redisClient;


    public Flight getFlightByName(String flightName) {
        return ofNullable(redisClient.get(flightName))
                .map(t -> (Flight) t)
                .orElseGet(() -> {
                    logger.info("Fetching from DB");
                    try {
                        sleep(25);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    return new Flight(null, null, null, null);
                });
    }

    public void bookNewFlight(final Flight flightAdded) {
        var email = flightAdded.customerEmail();

        var customerFound =
                ofNullable(redisClient.get(email))
                .map(t -> (CustomerResponse) t)
                .orElseGet(() -> {
                    var customerResponse = customerService.customerFound(email);
                    redisClient.setAndGet(email, customerResponse, 2);
                    return customerResponse;
                });

        if (Objects.isNull(customerFound)) {
            throw new FlightApiException("No customer found with provided email", HttpStatus.NOT_FOUND);
        }


        var fl = (Flight) redisClient.get(flightAdded.flightName());
        if (fl != null && (fl.flightName().equalsIgnoreCase(flightAdded.flightName()) || fl.id().equals(flightAdded.id()))) {
            logger.warn("Flight was already added!");
            throw new FlightApiException("Flight already booked!", HttpStatus.BAD_REQUEST);
        }


        redisClient.set(flightAdded.flightName(), flightAdded, 2);

        // push email event
        emailService.sendEmail(flightAdded.customerEmail(), flightAdded.flightName());
    }


}
