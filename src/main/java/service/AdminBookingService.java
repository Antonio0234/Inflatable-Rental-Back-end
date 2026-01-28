package com.antonio.napuhanci.service;

import com.antonio.napuhanci.domain.BookingRequest;
import com.antonio.napuhanci.domain.BookingStatus;
import com.antonio.napuhanci.domain.Inflatable;
import com.antonio.napuhanci.repository.BookingRequestRepository;
import com.antonio.napuhanci.web.error.BadRequestException;
import com.antonio.napuhanci.web.error.ConflictException;
import com.antonio.napuhanci.web.error.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Service
public class AdminBookingService {

    private final BookingRequestRepository bookingRequestRepository;

    public AdminBookingService(BookingRequestRepository bookingRequestRepository) {
        this.bookingRequestRepository = bookingRequestRepository;
    }

    @Transactional(readOnly = true)
    public List<BookingRequest> getPendingRequests() {
        return bookingRequestRepository.findByStatusOrderByIdDesc(BookingStatus.PENDING);
    }

    @Transactional
    public BookingRequest confirm(long requestId, String adminNote) {
        BookingRequest req = bookingRequestRepository.findById(requestId)
                .orElseThrow(() -> new NotFoundException("BookingRequest not found: id=" + requestId));

        if (req.getStatus() != BookingStatus.PENDING) {
            throw new BadRequestException("Only PENDING requests can be confirmed. Current=" + req.getStatus());
        }

        Inflatable inflatable = req.getInflatable();
        if (inflatable == null || inflatable.getId() == null) {
            throw new BadRequestException("BookingRequest has no inflatable.");
        }

        boolean alreadyConfirmed = bookingRequestRepository
                .existsByInflatable_IdAndDateAndStatus(inflatable.getId(), req.getDate(), BookingStatus.CONFIRMED);

        if (alreadyConfirmed) {
            throw new ConflictException("Date not available (already CONFIRMED) for inflatableId="
                    + inflatable.getId() + ", date=" + req.getDate());
        }

        // ✅ snapshot cijene kod potvrde
        BigDecimal priceSnapshot = inflatable.getPrice();
        BigDecimal deliverySnapshot = BigDecimal.ZERO; // TODO: kasnije izračun (mjesto/km)
        BigDecimal totalSnapshot = priceSnapshot.add(deliverySnapshot);

        req.setStatus(BookingStatus.CONFIRMED);
        req.setConfirmedAt(OffsetDateTime.now(ZoneOffset.UTC));

        req.setPriceAtConfirm(priceSnapshot);
        req.setDeliveryFeeAtConfirm(deliverySnapshot);
        req.setTotalAtConfirm(totalSnapshot);

        if (adminNote != null && !adminNote.isBlank()) {
            req.setAdminNote(adminNote.trim());
        }

        return bookingRequestRepository.save(req);
    }

    @Transactional
    public BookingRequest reject(long requestId, String adminNote) {
        BookingRequest req = bookingRequestRepository.findById(requestId)
                .orElseThrow(() -> new NotFoundException("BookingRequest not found: id=" + requestId));

        if (req.getStatus() != BookingStatus.PENDING) {
            throw new BadRequestException("Only PENDING requests can be rejected. Current=" + req.getStatus());
        }

        req.setStatus(BookingStatus.REJECTED);
        req.setRejectedAt(OffsetDateTime.now(ZoneOffset.UTC));

        if (adminNote != null && !adminNote.isBlank()) {
            req.setAdminNote(adminNote.trim());
        }

        return bookingRequestRepository.save(req);
    }
}
