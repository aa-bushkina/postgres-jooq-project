CREATE TABLE positions
(
    id         SERIAL  NOT NULL,
    price      DECIMAL NOT NULL,
    product_id INT     NOT NULL REFERENCES products (id)
        ON UPDATE CASCADE
        ON DELETE SET NULL,
    quantity   INT     NOT NULL,
    CONSTRAINT position_pk PRIMARY KEY (id),
    CONSTRAINT check_quantity CHECK (quantity >= 0)
);
