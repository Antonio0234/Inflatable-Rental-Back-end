package com.antonio.napuhanci.web;

import com.antonio.napuhanci.domain.BookingRequest;
import com.antonio.napuhanci.domain.Inflatable;
import com.antonio.napuhanci.repository.BookingRequestRepository;
import com.antonio.napuhanci.repository.InflatableRepository;
import com.antonio.napuhanci.service.AvailabilityService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/booking-requests")
public class BookingController {

    private final BookingRequestRepository bookingRequestRepository;
    private final InflatableRepository inflatableRepository;
    private final AvailabilityService availabilityService;

    public BookingController(BookingRequestRepository bookingRequestRepository,
                             InflatableRepository inflatableRepository,
                             AvailabilityService availabilityService) {
        this.bookingRequestRepository = bookingRequestRepository;
        this.inflatableRepository = inflatableRepository;
        this.availabilityService = availabilityService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookingRequest create(@RequestBody BookingRequest request) {

        if (request.getInflatable() == null || request.getInflatable().getId() == null) {
            throw new IllegalArgumentException("Inflatable ID is required");
        }

        Inflatable inflatable = inflatableRepository.findById(request.getInflatable().getId())
                .orElseThrow(() -> new IllegalArgumentException("Inflatable not found"));

        boolean available = availabilityService.isAvailable(inflatable.getId(), request.getDate());
        if (!available) {
            throw new IllegalStateException("Inflatable not available for that date");
        }

        request.setInflatable(inflatable);
        request.setStatus(com.antonio.napuhanci.domain.BookingStatus.PENDING);

        return bookingRequestRepository.save(request);
    }
}
