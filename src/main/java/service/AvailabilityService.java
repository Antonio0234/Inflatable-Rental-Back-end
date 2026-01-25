package com.antonio.napuhanci.service;

import com.antonio.napuhanci.domain.BookingStatus;
import com.antonio.napuhanci.repository.BookingRequestRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class AvailabilityService {

    private final BookingRequestRepository bookingRequestRepository;

    public AvailabilityService(BookingRequestRepository bookingRequestRepository) {
        this.bookingRequestRepository = bookingRequestRepository;
    }

    public boolean isAvailable(Long inflatableId, LocalDate date) {
        return !bookingRequestRepository.existsByInflatable_IdAndDateAndStatus(
                inflatableId, date, BookingStatus.CONFIRMED
        );
    }
}
