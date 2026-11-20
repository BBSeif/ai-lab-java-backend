CREATE TABLE category (
    id   BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE product (
    id          BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name        VARCHAR(100)  NOT NULL,
    price       NUMERIC(12,2) NOT NULL CHECK (price >= 0),
    category_id BIGINT        NOT NULL REFERENCES category (id),
    created_at  TIMESTAMPTZ   NOT NULL DEFAULT now()
);

CREATE INDEX idx_product_category_id ON product (category_id);