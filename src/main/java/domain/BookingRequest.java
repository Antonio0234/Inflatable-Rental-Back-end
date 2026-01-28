package com.antonio.napuhanci.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;

@Entity
@Table(name = "booking_requests")
public class BookingRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "inflatable_id", nullable = false)
    private Inflatable inflatable;

    @Column(nullable = false)
    private String customerName;

    @Column(nullable = false)
    private String phone;

    private String email;

    @Column(nullable = false)
    private String place;

    @Column(nullable = false)
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookingStatus status = BookingStatus.PENDING;

    private BigDecimal priceAtConfirm;
    private BigDecimal deliveryFeeAtConfirm;
    private BigDecimal totalAtConfirm;

    private OffsetDateTime createdAt = OffsetDateTime.now();
    private OffsetDateTime confirmedAt;
    private OffsetDateTime rejectedAt;

    @Column(length = 500)
    private String adminNote;

    public Long getId() { return id; }
    public Inflatable getInflatable() { return inflatable; }
    public String getCustomerName() { return customerName; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }
    public String getPlace() { return place; }
    public LocalDate getDate() { return date; }
    public BookingStatus getStatus() { return status; }

    public void setInflatable(Inflatable inflatable) { this.inflatable = inflatable; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setEmail(String email) { this.email = email; }
    public void setPlace(String place) { this.place = place; }
    public void setDate(LocalDate date) { this.date = date; }
    public void setStatus(BookingStatus status) { this.status = status; }
    // --- SNAPSHOT ---
    public BigDecimal getPriceAtConfirm() { return priceAtConfirm; }
    public BigDecimal getDeliveryFeeAtConfirm() { return deliveryFeeAtConfirm; }
    public BigDecimal getTotalAtConfirm() { return totalAtConfirm; }

    public void setPriceAtConfirm(BigDecimal priceAtConfirm) {
        this.priceAtConfirm = priceAtConfirm;
    }

    public void setDeliveryFeeAtConfirm(BigDecimal deliveryFeeAtConfirm) {
        this.deliveryFeeAtConfirm = deliveryFeeAtConfirm;
    }

    public void setTotalAtConfirm(BigDecimal totalAtConfirm) {
        this.totalAtConfirm = totalAtConfirm;
    }

    // --- TIMESTAMPS ---
    public OffsetDateTime getCreatedAt() { return createdAt; }
    public OffsetDateTime getConfirmedAt() { return confirmedAt; }
    public OffsetDateTime getRejectedAt() { return rejectedAt; }

    public void setConfirmedAt(OffsetDateTime confirmedAt) {
        this.confirmedAt = confirmedAt;
    }

    public void setRejectedAt(OffsetDateTime rejectedAt) {
        this.rejectedAt = rejectedAt;
    }

    // --- ADMIN ---
    public String getAdminNote() { return adminNote; }

    public void setAdminNote(String adminNote) {
        this.adminNote = adminNote;
    }
}
