
CREATE TABLE if NOT EXISTS transaction (
    Id BIGINT(20) NOT NULL AUTO_INCREMENT,
    Sender VARCHAR(255) NOT NULL,
    Receiver VARCHAR(255) NOT NULL,
    Amount FLOAT,
    TransactionStatus VARCHAR(255) NOT NULL,
    PRIMARY KEY (Id)
);

