package com.antonio.napuhanci.web.dto;

import com.antonio.napuhanci.domain.BookingRequest;
import com.antonio.napuhanci.domain.BookingStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;

public record AdminBookingRequestResponse(
        Long id,
        BookingStatus status,

        Long inflatableId,
        String inflatableName,

        String customerName,
        String phone,
        String email,
        String place,
        LocalDate date,

        BigDecimal priceAtConfirm,
        BigDecimal deliveryFeeAtConfirm,
        BigDecimal totalAtConfirm,

        OffsetDateTime createdAt,
        OffsetDateTime confirmedAt,
        OffsetDateTime rejectedAt,

        String adminNote
) {
    public static AdminBookingRequestResponse from(BookingRequest r) {
        return new AdminBookingRequestResponse(
                r.getId(),
                r.getStatus(),

                r.getInflatable() == null ? null : r.getInflatable().getId(),
                r.getInflatable() == null ? null : r.getInflatable().getName(),

                r.getCustomerName(),
                r.getPhone(),
                r.getEmail(),
                r.getPlace(),
                r.getDate(),

                r.getPriceAtConfirm(),
                r.getDeliveryFeeAtConfirm(),
                r.getTotalAtConfirm(),

                r.getCreatedAt(),
                r.getConfirmedAt(),
                r.getRejectedAt(),

                r.getAdminNote()
        );
    }
}
