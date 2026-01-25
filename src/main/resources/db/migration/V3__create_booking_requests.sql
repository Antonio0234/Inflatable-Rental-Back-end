CREATE TABLE booking_requests (
                                  id BIGSERIAL PRIMARY KEY,
                                  inflatable_id BIGINT NOT NULL REFERENCES inflatables(id),

                                  customer_name VARCHAR(255) NOT NULL,
                                  phone VARCHAR(50) NOT NULL,
                                  email VARCHAR(255),
                                  place VARCHAR(255) NOT NULL,
                                  date DATE NOT NULL,

                                  status VARCHAR(30) NOT NULL DEFAULT 'PENDING',

                                  price_at_confirm NUMERIC(12,2),
                                  delivery_fee_at_confirm NUMERIC(12,2),
                                  total_at_confirm NUMERIC(12,2),

                                  created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
                                  confirmed_at TIMESTAMPTZ,
                                  rejected_at TIMESTAMPTZ,
                                  admin_note VARCHAR(500)
);

CREATE INDEX idx_booking_requests_status ON booking_requests(status);
CREATE INDEX idx_booking_requests_inflatable_date_status
    ON booking_requests(inflatable_id, date, status);
