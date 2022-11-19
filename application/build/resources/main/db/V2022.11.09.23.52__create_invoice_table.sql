CREATE TABLE invoices
(
    id              SERIAL      NOT NULL,
    num             VARCHAR(10) NOT NULL UNIQUE,
    date            DATE        NOT NULL,
    organization_id INT         NOT NULL REFERENCES organizations (id)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT invoices_pk PRIMARY KEY (id),
    CONSTRAINT check_num CHECK
        (num ~ '\d{10}')
);
