CREATE TABLE invoices_positions
(
    id          SERIAL NOT NULL,
    invoice_id  INT    NOT NULL REFERENCES invoices (id)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    position_id INT    NOT NULL REFERENCES positions (id)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT invoices_positions_pk PRIMARY KEY (id)
);
