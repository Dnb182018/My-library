
create table if not exists catalog
(
    id           INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    catalog_id   VARCHAR(36),
    location     VARCHAR(100)
    );


CREATE TABLE IF NOT EXISTS authors (
                                       id                 INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                       author_id          VARCHAR(36) UNIQUE ,
    first_name         VARCHAR(50),
    last_name          VARCHAR(50),
    birth_date         DATE,
    info               VARCHAR (250)
    );



create table if not exists books
(
    id                  INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    book_id             VARCHAR(50) UNIQUE,
    catalog_id          VARCHAR(36),
    title               VARCHAR(50),
    type                VARCHAR(50),
    quantities          INTEGER,
    isbn                VARCHAR(50),
    language            VARCHAR(50),
    status              VARCHAR(50),
    description         VARCHAR(250),
    author_id           VARCHAR(36),
    first_name          VARCHAR(50),
    last_name           VARCHAR(50)
    );

