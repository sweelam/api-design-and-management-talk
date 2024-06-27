package com.flight.booking.dto.enums;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class BookingStatusConverter implements AttributeConverter<BookingStatus, String> {
    @Override
    public String convertToDatabaseColumn(BookingStatus attribute) {
        return attribute.getStatus();
    }

    @Override
    public BookingStatus convertToEntityAttribute(String dbData) {
        return BookingStatus.fromStatus(dbData);
    }
}
