package com.flight.booking.mappers;


import com.flight.booking.dto.BookingDto;
import com.flight.booking.entity.Booking;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperSpringConfig.class)
public interface FlightBookingMapper {
    @Mapping(source = "id", target = "bookingId")
    BookingDto convertToBookingtDto(Booking booking);

    @Mapping(source = "bookingId", target = "id")
    @Mapping(source = "status", target = "status", defaultValue = "PENDING")
    Booking convertToBookingEntity(BookingDto flightDto);
}
