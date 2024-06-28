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

@Service
@Slf4j
@RequiredArgsConstructor
public class FlightBookingServiceImpl implements FlightBookingService {
    private final FlightBookingRepo flightBookingRepo;
    private final FlightBookingMapper flightBookingMapper;
    private final RestTemplate restTemplate;
    private final ExecutorService ex = Executors.newVirtualThreadPerTaskExecutor();

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
    public BookingDto createBooking(BookingDto bookingDto) {
        var userIdIsValid = isUserIdValid(bookingDto.userId());
        var flightIdIsValid = isFlightIdValid(bookingDto.flightId());

        CompletableFuture<BookingDto> resultFuture = CompletableFuture.allOf(userIdIsValid, flightIdIsValid)
                .thenComposeAsync(v -> {
                    try {
                        if (userIdIsValid.get() && flightIdIsValid.get()) {
                            return CompletableFuture.supplyAsync(() -> {
                                var bookingEntity = flightBookingMapper.convertToBookingEntity(bookingDto);
                                var savedBooking = flightBookingRepo.save(bookingEntity);
                                BookingDto booking = flightBookingMapper.convertToBookingtDto(savedBooking);

                                // FIXME send Email

                                return booking;
                            });
                        } else {
                            throw new BookingApiException("Invalid user or flight ID");
                        }
                    } catch (InterruptedException | ExecutionException e) {
                        throw new RuntimeException(e);
                    }
                });
        return resultFuture.join();
    }

    private CompletableFuture<Boolean> isUserIdValid(Integer userId) {
        return CompletableFuture.supplyAsync(() -> {
                    ResponseEntity<UserResponse> exchange =
                            restTemplate.exchange(userServiceUrl + "/" + userId, HttpMethod.GET, null, UserResponse.class);

                    return exchange.getStatusCode().is2xxSuccessful();
                }, ex);
    }

    private CompletableFuture<Boolean> isFlightIdValid(Integer flightId) {
        return CompletableFuture.supplyAsync(() -> {
                    ResponseEntity<FlightResponse> exchange =
                            restTemplate.exchange(flightServiceUrl + "/" + flightId, HttpMethod.GET, null, FlightResponse.class);

                    return exchange.getStatusCode().is2xxSuccessful();
                }, ex);
    }

    @Override
    public BookingDto updateBooking(BookingDto bookingDto) {
        return null;
    }
}
