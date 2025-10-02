DROP TABLE if EXISTS memberrecords;

CREATE TABLE memberrecords
(
    id                  SERIAL,
    member_id           VARCHAR(50) UNIQUE,
    start_date          DATE,
    end_date            DATE,
    type                VARCHAR(50),
    user_id             VARCHAR(50),
    PRIMARY KEY         (id)
);

CREATE TABLE users
(
    id                 SERIAL,
    user_id             VARCHAR(50) UNIQUE,
    first_name          VARCHAR(50),
    last_name           VARCHAR(50),
    email               VARCHAR(50),
    street_name         VARCHAR(50),
    city                VARCHAR(50),
    province            VARCHAR(50),
    country             VARCHAR(50),
    postal_code         VARCHAR(9),
    PRIMARY KEY        (id)
    );


CREATE TABLE user_phonenumbers
(
    user_id     INTEGER,
    type        VARCHAR(50),
    number      VARCHAR(50)
    );



