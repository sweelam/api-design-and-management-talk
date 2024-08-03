package com.flight.booking.service.impl;

import com.flight.booking.dto.BookingDto;
import com.flight.booking.dto.FlightResponse;
import com.flight.booking.dto.UserResponse;
import com.flight.booking.exceptions.BookingApiException;
import com.flight.booking.mappers.FlightBookingMapper;
import com.flight.booking.repo.FlightBookingRepo;
import com.flight.booking.service.FlightBookingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.flight.booking.builders.EmailBuilders.buildEmailEvent;
import static java.util.Objects.nonNull;

@Service
@Slf4j
@RequiredArgsConstructor
public class FlightBookingServiceImpl implements FlightBookingService {
    private final FlightBookingRepo flightBookingRepo;
    private final FlightBookingMapper flightBookingMapper;
    private final RestTemplate restTemplate;
    private final ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor();

    @Value("${app.user-service.url}")
    private String userServiceUrl;

    @Value("${app.flight-service.url}")
    private String flightServiceUrl;

    @Override
    public List<BookingDto> getBookings() {
        return flightBookingRepo.findAll()
                .stream().map(flightBookingMapper::convertToBookingtDto)
                .toList();
    }

    @Override
    public BookingDto getBookingById(Integer bookingId) {
        return flightBookingRepo.findById(bookingId)
                .map(flightBookingMapper::convertToBookingtDto)
                .orElseThrow(() -> new BookingApiException("Booking not found"));
    }

    @Override
    public CompletableFuture<BookingDto> createBooking(BookingDto bookingDto) {
        var userDetails = isUserIdValid(bookingDto.userId());
        var flightDetails = isFlightIdValid(bookingDto.flightId());

        return CompletableFuture.allOf(userDetails, flightDetails)
                .thenComposeAsync(v -> {
                    try {
                        var flightResponse = flightDetails.get();
                        var userResponse = userDetails.get();

                        if (isValidUserResponse(userResponse) && isValidFlightResponse(flightResponse)) {

                            return CompletableFuture.supplyAsync(() -> {
                                var bookingEntity = flightBookingMapper.convertToBookingEntity(bookingDto);
                                var savedBooking = flightBookingRepo.save(bookingEntity);
                                var booking = flightBookingMapper.convertToBookingtDto(savedBooking);

                                buildEmailEvent(userResponse.getBody().email(), flightResponse.getBody().flightNumber());

                                return booking;
                            });

                        } else {
                            throw new BookingApiException("Invalid user or flight ID");
                        }
                    } catch (InterruptedException | ExecutionException e) {
                        Thread.currentThread().interrupt();
                        throw new BookingApiException("Error occurred while saving flight details " + e);
                    }
                });
    }

    private static boolean isValidFlightResponse(ResponseEntity<FlightResponse> flightResponse) {
        return nonNull(flightResponse) && flightResponse.getStatusCode().is2xxSuccessful();
    }

    private static boolean isValidUserResponse(ResponseEntity<UserResponse> userResponse) {
        return nonNull(userResponse) && userResponse.getStatusCode().is2xxSuccessful();
    }

    private CompletableFuture<ResponseEntity<UserResponse>> isUserIdValid(Integer userId) {
        return CompletableFuture.supplyAsync(() ->
                    restTemplate.exchange(userServiceUrl + "/" + userId, HttpMethod.GET,
                            null, UserResponse.class)
         , executorService);
    }

    private CompletableFuture<ResponseEntity<FlightResponse>> isFlightIdValid(Integer flightId) {
        return CompletableFuture.supplyAsync(() ->
                    restTemplate.exchange(flightServiceUrl + "/" + flightId, HttpMethod.GET,
                            null, FlightResponse.class)
                , executorService);
    }

    @Override
    public BookingDto updateBooking(BookingDto bookingDto) {
        return null;
    }
}
