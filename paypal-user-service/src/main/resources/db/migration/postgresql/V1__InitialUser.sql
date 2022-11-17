CREATE TABLE public.user (
  id BIGSERIAL NOT NULL,
  balance double PRECISION DEFAULT NULL,
  name varchar(255) DEFAULT NULL,
  surname varchar(255) DEFAULT NULL,
  username varchar(255) DEFAULT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE public.hibernate_sequence(
  next_val bigint DEFAULT NULL
);
