package com.antonio.napuhanci.repository;

import com.antonio.napuhanci.domain.BookingRequest;
import com.antonio.napuhanci.domain.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface BookingRequestRepository extends JpaRepository<BookingRequest, Long> {

    boolean existsByInflatable_IdAndDateAndStatus(
            Long inflatableId,
            LocalDate date,
            BookingStatus status
    );

    List<BookingRequest> findByStatusOrderByIdDesc(BookingStatus status);
}
