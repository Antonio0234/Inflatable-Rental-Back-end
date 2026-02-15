package com.antonio.napuhanci.service;

import com.antonio.napuhanci.domain.BookingRequest;
import com.antonio.napuhanci.domain.BookingStatus;
import com.antonio.napuhanci.repository.BookingRequestRepository;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class CalendarService {

    private final BookingRequestRepository bookingRequestRepository;

    public CalendarService(BookingRequestRepository bookingRequestRepository) {
        this.bookingRequestRepository = bookingRequestRepository;
    }

    public String generateCalendar() {
        List<BookingRequest> confirmed =
                bookingRequestRepository.findByStatus(BookingStatus.CONFIRMED);

        StringBuilder sb = new StringBuilder();

        sb.append("BEGIN:VCALENDAR\n");
        sb.append("VERSION:2.0\n");
        sb.append("PRODID:-//Napuhanci//Booking Calendar//HR\n");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        for (BookingRequest r : confirmed) {

            sb.append("BEGIN:VEVENT\n");
            sb.append("UID:booking-").append(r.getId()).append("@napuhanci.hr\n");
            sb.append("DTSTART;VALUE=DATE:")
                    .append(r.getDate().format(formatter)).append("\n");
            sb.append("DTEND;VALUE=DATE:")
                    .append(r.getDate().plusDays(1).format(formatter)).append("\n");
            sb.append("SUMMARY:")
                    .append(r.getInflatable().getName())
                    .append(" - ")
                    .append(r.getPlace())
                    .append("\n");
            sb.append("DESCRIPTION:")
                    .append("Kupac: ").append(r.getCustomerName())
                    .append(" Tel: ").append(r.getPhone())
                    .append("\n");
            sb.append("END:VEVENT\n");
        }

        sb.append("END:VCALENDAR");

        return sb.toString();
    }
}
