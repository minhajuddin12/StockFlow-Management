CREATE TABLE stock_movements (
                                 id BIGSERIAL PRIMARY KEY,
                                 product_id BIGINT NOT NULL REFERENCES products(id),
                                 type VARCHAR(10) NOT NULL,
                                 quantity INTEGER NOT NULL,
                                 reason VARCHAR(255),
                                 created_at TIMESTAMP NOT NULL DEFAULT now()
);