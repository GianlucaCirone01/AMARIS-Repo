CREATE TABLE user (
  id bigint NOT NULL AUTO_INCREMENT,
  balance double DEFAULT NULL,
  name varchar(255) DEFAULT NULL,
  surname varchar(255) DEFAULT NULL,
  username varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE hibernate_sequence(
  next_val bigint DEFAULT NULL
);
