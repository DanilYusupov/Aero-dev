CREATE TABLE offers_test (
  off_id SERIAL PRIMARY KEY,
  off_usr_id BIGINT,
  off_cr_id BIGINT,
  off_description TEXT,
  status VARCHAR(10)
);