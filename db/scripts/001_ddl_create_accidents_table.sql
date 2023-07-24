CREATE TABLE accidents (
  id serial primary key,
  name text,
  text text,
  address text,
  type int,
  rules integer[]
);