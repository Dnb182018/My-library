
create table if not exists users
(
    id                  INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_id             VARCHAR(50) UNIQUE,
    first_name          VARCHAR(50),
    last_name           VARCHAR(50),
    email               VARCHAR(50),
    street_name         VARCHAR(50),
    city                VARCHAR(50),
    province            VARCHAR(50),
    country             VARCHAR(50),
    postal_code         VARCHAR(9)
);


create table if not exists user_phonenumbers
(
    user_id     INTEGER,
    type        VARCHAR(50),
    number      VARCHAR(50)
);



create table if not exists memberrecords
(
    id                  INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    member_id           VARCHAR(50) UNIQUE,
    start_date          DATE,
    end_date            DATE,
    type                VARCHAR(50),
    user_id             VARCHAR(50)
);

