CREATE TABLE aero.company_representatives (
  cr_id SERIAL PRIMARY KEY,
  cr_name VARCHAR(64) NOT NULL UNIQUE,
  cr_pass VARCHAR(64) NOT NULL,
  cr_email VARCHAR(128) NOT NULL UNIQUE,
  cr_comp_id BIGINT,
  cr_first_name VARCHAR(32) NOT NULL DEFAULT '',
  cr_last_name VARCHAR(32) NOT NULL DEFAULT '',
  cr_position VARCHAR(40),
  FOREIGN KEY (cr_comp_id) REFERENCES aero.companies (comp_id)
);

INSERT INTO aero.company_representatives (cr_name, cr_pass, cr_email, cr_comp_id, cr_first_name, cr_last_name, cr_position) VALUES ('cr1', 'pass1', 'mforeman@email.com', 1, 'Mattew', 'Foreman', 'Senior engineer');
INSERT INTO aero.company_representatives (cr_name, cr_pass, cr_email, cr_comp_id, cr_first_name, cr_last_name, cr_position) VALUES ('cr2', 'pass2', 'cjohnson@email.com', 2, 'Carl', 'Johnson', 'Cosmonaut prepare team lead');
INSERT INTO aero.company_representatives (cr_name, cr_pass, cr_email, cr_comp_id, cr_first_name, cr_last_name, cr_position) VALUES ('cr3', 'pass3', 'sbrick@email.com', 3, 'Steve', 'Brick', 'Technological specialist');

INSERT INTO aero.offers (off_usr_id, off_cr_id, off_description, status) VALUES (12, 1, 'Junior aerodynamic specialist', 'INITIATED');
INSERT INTO aero.offers (off_usr_id, off_cr_id, off_description, status) VALUES (13, 2, 'Junior cosmological specialist', 'INITIATED');
