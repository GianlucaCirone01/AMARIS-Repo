CREATE TABLE user (
  id bigint NOT NULL AUTO_INCREMENT,
  balance double DEFAULT NULL,
  name varchar(255) DEFAULT NULL,
  surname varchar(255) DEFAULT NULL,
  username varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

<<<<<<< HEAD
CREATE TABLE hibernate_sequence(
  next_val bigint DEFAULT NULL
);
=======
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO hibernate_sequence (next_val) VALUES (1);
>>>>>>> AntonioAlberto
