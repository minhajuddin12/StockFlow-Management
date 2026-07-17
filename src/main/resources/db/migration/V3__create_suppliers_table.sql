CREATE TABLE suppliers (
                           id BIGSERIAL PRIMARY KEY,
                           name VARCHAR(255) NOT NULL,
                           contact_email VARCHAR(255),
                           phone VARCHAR(50),
                           company_id BIGINT NOT NULL REFERENCES companies(id)
);