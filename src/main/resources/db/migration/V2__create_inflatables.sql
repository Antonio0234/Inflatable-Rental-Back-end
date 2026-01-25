CREATE TABLE inflatables (
                             id BIGSERIAL PRIMARY KEY,
                             name VARCHAR(255) NOT NULL,
                             price NUMERIC(12,2) NOT NULL,
                             active BOOLEAN NOT NULL DEFAULT TRUE
);
