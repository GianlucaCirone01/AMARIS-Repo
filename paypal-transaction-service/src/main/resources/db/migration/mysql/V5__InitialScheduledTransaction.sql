CREATE TABLE if NOT EXISTS Scheduledtransaction (
    Id BIGINT(20) NOT NULL AUTO_INCREMENT,
    Sender VARCHAR(255) NOT NULL,
    Receiver VARCHAR(255) NOT NULL,
    Amount FLOAT,
    TransactionStatus VARCHAR(255) NOT NULL,
    CreationDate datetime,
    Mode int,
    PRIMARY KEY (Id)
);
