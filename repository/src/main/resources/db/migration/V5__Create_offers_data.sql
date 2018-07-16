CREATE TABLE aero.offers(
  off_id SERIAL PRIMARY KEY,
  usr_id BIGINT NOT NULL,
  cr_id BIGINT NOT NULL,
  off_description TEXT,
  status VARCHAR(10),
  CONSTRAINT off_usr_id FOREIGN KEY (usr_id) REFERENCES aero.users,
  CONSTRAINT off_cr_id FOREIGN KEY (cr_id) REFERENCES aero.company_representatives
)