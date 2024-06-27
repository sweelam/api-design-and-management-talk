package com.flight.booking.mappers;


import com.flight.booking.dto.BookingDto;
import com.flight.booking.entity.Booking;
import org.mapstruct.Mapper;

@Mapper(config = MapperSpringConfig.class)
public interface FlightBookingMapper {
    BookingDto convertToBookingtDto(Booking booking);

    Booking convertToBookingEntity(BookingDto flightDto);
}
