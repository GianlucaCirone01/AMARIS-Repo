CREATE TABLE if NOT EXISTS public.transaction (
    Id BIGSERIAL NOT NULL,
    Sender VARCHAR(255) NOT NULL,
    Receiver VARCHAR(255) NOT NULL,
    Amount FLOAT,
    TransactionStatus VARCHAR(255) NOT NULL,
    PRIMARY KEY (Id)
);
