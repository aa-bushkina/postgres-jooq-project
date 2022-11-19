CREATE TABLE products
(
    id            SERIAL      NOT NULL,
    internal_code VARCHAR(10) NOT NULL UNIQUE,
    name          VARCHAR     NOT NULL,
    CONSTRAINT product_pk PRIMARY KEY (id)
);

CREATE TABLE organizations
(
    id              SERIAL      NOT NULL,
    name            VARCHAR     NOT NULL,
    inn             VARCHAR(10) NOT NULL UNIQUE,
    payment_account VARCHAR(10) NOT NULL UNIQUE,
    CONSTRAINT organization_pk PRIMARY KEY (id),
    CONSTRAINT check_inn CHECK
        (inn ~ '\d{10}'),
    CONSTRAINT check_payment_account CHECK
        (payment_account ~ '\d{10}')
);