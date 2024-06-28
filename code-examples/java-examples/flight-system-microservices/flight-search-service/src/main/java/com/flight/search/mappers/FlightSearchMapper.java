package com.flight.search.mappers;


import com.flight.search.dto.FlightDto;
import com.flight.search.entity.Flight;
import org.mapstruct.Mapper;

@Mapper(config = MapperSpringConfig.class)
public interface FlightSearchMapper {
    FlightDto convertToFlightDto(Flight flight);

    Flight convertToFlightEntity(FlightDto flightDto);
}
