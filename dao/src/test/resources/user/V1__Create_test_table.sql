CREATE TABLE user_test (
  usr_id SERIAL PRIMARY KEY,
  usr_name VARCHAR(64) NOT NULL UNIQUE,
  usr_password VARCHAR(64) NOT NULL,
  usr_email VARCHAR(128) NOT NULL UNIQUE,
  usr_level SMALLINT DEFAULT 0,
  usr_first_name VARCHAR(32),
  usr_last_name VARCHAR(32),
  usr_biography TEXT,
  usr_rating INT DEFAULT 0
)