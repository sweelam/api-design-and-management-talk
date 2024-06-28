package com.flight.booking.dto.enums;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import static java.util.Objects.nonNull;

@Converter
public class BookingStatusConverter implements AttributeConverter<BookingStatus, String> {
    @Override
    public String convertToDatabaseColumn(BookingStatus attribute) {
        return nonNull(attribute) ? attribute.getStatus() : BookingStatus.PENDING.getStatus();
    }

    @Override
    public BookingStatus convertToEntityAttribute(String dbData) {
        return BookingStatus.fromStatus(dbData);
    }
}
