

create table if not exists fine
(
    id                  INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    fine_id             VARCHAR(50) UNIQUE,
    issue_date           DATE,
    status              VARCHAR(50)

);

create table if not exists fine_payment(
    fine_id             INTEGER,
    amount              DECIMAL(19,2),
    currency            VARCHAR(50),
    payment_date         DATE,
    status              VARCHAR(50),
    payment_method       VARCHAR (50)
);

