CREATE TABLE purchase_orders (
                                 id BIGSERIAL PRIMARY KEY,
                                 supplier_id BIGINT NOT NULL REFERENCES suppliers(id),
                                 status VARCHAR(20) NOT NULL DEFAULT 'DRAFT',
                                 order_date TIMESTAMP NOT NULL DEFAULT now(),
                                 company_id BIGINT NOT NULL REFERENCES companies(id)
);

CREATE TABLE purchase_order_items (
                                      id BIGSERIAL PRIMARY KEY,
                                      purchase_order_id BIGINT NOT NULL REFERENCES purchase_orders(id),
                                      product_id BIGINT NOT NULL REFERENCES products(id),
                                      quantity INTEGER NOT NULL,
                                      unit_cost NUMERIC(12,2) NOT NULL
);