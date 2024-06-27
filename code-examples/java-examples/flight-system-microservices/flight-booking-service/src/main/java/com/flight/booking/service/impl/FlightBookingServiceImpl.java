package com.flight.booking.service.impl;

import com.flight.booking.dto.BookingDto;
import com.flight.booking.mappers.FlightBookingMapper;
import com.flight.booking.repo.FlightBookingRepo;
import com.flight.booking.service.FlightBookingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class FlightBookingServiceImpl implements FlightBookingService {
    private final FlightBookingRepo flightBookingRepo;
    private final FlightBookingMapper flightBookingMapper;

    @Override
    public List<BookingDto> getBookings() {
        return flightBookingRepo.findAll()
                .stream().map(flightBookingMapper::convertToBookingtDto)
                .toList();
    }

    @Override
    public BookingDto getBookingById(Integer bookingId) {
        return null;
    }

    @Override
    public BookingDto createBooking(BookingDto bookingDto) {
        return null;
    }

    @Override
    public BookingDto updateBooking(BookingDto bookingDto) {
        return null;
    }
}
