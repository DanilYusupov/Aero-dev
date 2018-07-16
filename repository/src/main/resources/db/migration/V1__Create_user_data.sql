-- Creating User table

CREATE TABLE aero.users (
  usr_id SERIAL PRIMARY KEY,
  usr_name VARCHAR(64) NOT NULL UNIQUE,
  usr_password VARCHAR(64) NOT NULL,
  usr_email VARCHAR(128) NOT NULL UNIQUE,
  usr_level SMALLINT DEFAULT 0,
  usr_first_name VARCHAR(32) NOT NULL DEFAULT '',
  usr_last_name VARCHAR(32) NOT NULL DEFAULT '',
  usr_biography TEXT,
  usr_rating INT DEFAULT 0,
  usr_country VARCHAR(32) NOT NULL DEFAULT '',
  usr_city VARCHAR(32) NOT NULL DEFAULT '',
  usr_is_male BOOLEAN DEFAULT TRUE
);

--Filling created table with users
INSERT INTO aero.users (usr_name, usr_password, usr_email, usr_level, usr_first_name, usr_last_name, usr_biography, usr_rating, usr_country, usr_city, usr_is_male)
VALUES ('Aigul', 'passwrd', 'eml', 68, 'Aigul', 'Yusupova', 'bio...', 100, 'Russia', 'Kazan', false);
INSERT INTO aero.users (usr_name, usr_password, usr_email, usr_level, usr_first_name, usr_last_name, usr_biography, usr_rating, usr_country, usr_city, usr_is_male)
VALUES ('Mark', 'rgwer', 'ret', 23, 'Mark', 'Tarkov', 'bio...', 87, 'Russia', 'Moscow', true);
INSERT INTO aero.users (usr_name, usr_password, usr_email, usr_level, usr_first_name, usr_last_name, usr_biography, usr_rating, usr_country, usr_city, usr_is_male)
VALUES ('Claus', 'rgsr', 'er', 14, 'Claus', 'Shultz', 'bio...', 47, 'Germany', 'Dresden', true);
INSERT INTO aero.users (usr_name, usr_password, usr_email, usr_level, usr_first_name, usr_last_name, usr_biography, usr_rating, usr_country, usr_city, usr_is_male)
VALUES ('Marta', 'yujg', 're', 32, 'Marta', 'Ford', 'bio...', 22, 'UK', 'Bournemouth', false);
INSERT INTO aero.users (usr_name, usr_password, usr_email, usr_level, usr_first_name, usr_last_name, usr_biography, usr_rating, usr_country, usr_city, usr_is_male)
VALUES ('Aliya', 'serger', 'rety', 31, 'Aliya', 'Karimova', 'bio...', 73, 'Russia', 'Arsk', false);