
CREATE TABLE humans
(
    id            SERIAL PRIMARY KEY,
    name          TEXT    NOT NULL,
    age           INTEGER CHECK ( age > 0 AND age < 130),
    driverLicense BOOLEAN,
    carsId        INTEGER NOT NULL REFERENCES humans (id)

);
CREATE TABLE cars
(
    id    SERIAL PRIMARY KEY,
    brand VARCHAR(30) NOT NULL,
    model INTEGER     NOT NULL,
    price MONEY       NOT NULL
);