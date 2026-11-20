CREATE TABLE orders (
    id             BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    customer_email VARCHAR(255) NOT NULL,
    status         VARCHAR(20)  NOT NULL DEFAULT 'NEW'
                   CHECK (status IN ('NEW', 'PAID', 'SHIPPED', 'CANCELLED')),
    created_at     TIMESTAMPTZ  NOT NULL DEFAULT now()
);

CREATE TABLE order_item (
    id         BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    order_id   BIGINT        NOT NULL REFERENCES orders (id) ON DELETE CASCADE,
    product_id BIGINT        NOT NULL REFERENCES product (id),
    quantity   INT           NOT NULL CHECK (quantity > 0),
    unit_price NUMERIC(12,2) NOT NULL CHECK (unit_price >= 0),
    UNIQUE (order_id, product_id)
);

CREATE INDEX idx_order_item_order_id   ON order_item (order_id);
CREATE INDEX idx_order_item_product_id ON order_item (product_id);