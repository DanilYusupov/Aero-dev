CREATE TABLE aero.offers (
  off_id SERIAL PRIMARY KEY,
  off_usr_id BIGINT,
  off_cr_id BIGINT,
  off_description TEXT,
  FOREIGN KEY (off_usr_id) REFERENCES aero.users (usr_id)
);