CREATE TABLE crs_test (
  cr_id SERIAL PRIMARY KEY,
  cr_name VARCHAR(64) NOT NULL UNIQUE,
  cr_pass VARCHAR(64) NOT NULL,
  cr_email VARCHAR(128) NOT NULL UNIQUE,
  cr_comp_id BIGINT,
  cr_first_name VARCHAR(32) NOT NULL DEFAULT '',
  cr_last_name VARCHAR(32) NOT NULL DEFAULT '',
  cr_position VARCHAR(40)
)