CREATE TABLE products (
                          id BIGSERIAL PRIMARY KEY,
                          sku VARCHAR(100) NOT NULL,
                          name VARCHAR(255) NOT NULL,
                          category VARCHAR(100),
                          unit_price NUMERIC(12,2) NOT NULL,
                          quantity_on_hand INTEGER NOT NULL DEFAULT 0,
                          reorder_level INTEGER NOT NULL DEFAULT 10,
                          company_id BIGINT NOT NULL REFERENCES companies(id)
);