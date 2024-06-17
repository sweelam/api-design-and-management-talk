package com.flight.booking.rest;

import com.flight.booking.exceptions.FlightApiException;
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
@RequestMapping("/booking")
public class FlightBookingController {

}
