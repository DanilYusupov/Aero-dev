-- Creating companies table

CREATE TABLE aero.companies (
  comp_id SERIAL PRIMARY KEY,
  comp_name VARCHAR(128) NOT NULL UNIQUE,
  comp_website VARCHAR(128) DEFAULT '',
  comp_logo_url VARCHAR(250)
);

INSERT INTO aero.companies (comp_name, comp_website, comp_logo_url) VALUES ('ЦАГИ им. Н. Е. Жуковского', 'http://www.tsagi.ru/', 'http://stroyneryd-beton.ru/assets/images/clients/index/1f38f16b1b1a829940be0d83163f04b8.jpg');
INSERT INTO aero.companies (comp_name, comp_website, comp_logo_url) VALUES ('ГККД "Роскосмос"', 'https://www.roscosmos.ru/', 'https://upload.wikimedia.org/wikipedia/commons/d/da/Roscosmos_logo_ru.svg');
INSERT INTO aero.companies (comp_name, comp_website, comp_logo_url) VALUES ('Корпорация "Ростех"', 'http://rostec.ru/', 'http://toplogos.ru/images/logo-rostec.jpg');

-- Creating crs table

CREATE TABLE aero.company_representatives (
  cr_id SERIAL PRIMARY KEY,
  cr_name VARCHAR(64) NOT NULL UNIQUE,
  cr_pass VARCHAR(64) NOT NULL,
  cr_email VARCHAR(128) NOT NULL UNIQUE,
  comp_id BIGINT NOT NULL,
  cr_first_name VARCHAR(32) NOT NULL DEFAULT '',
  cr_last_name VARCHAR(32) NOT NULL DEFAULT '',
  cr_position VARCHAR(40),
  CONSTRAINT comp_fk FOREIGN KEY (comp_id) REFERENCES aero.companies (comp_id)
);

INSERT INTO aero.company_representatives (cr_name, cr_pass, cr_email, comp_id, cr_first_name, cr_last_name, cr_position) VALUES ('cr1', 'pass1', 'mforeman@email.com', 1, 'Mattew', 'Foreman', 'Senior engineer');
INSERT INTO aero.company_representatives (cr_name, cr_pass, cr_email, comp_id, cr_first_name, cr_last_name, cr_position) VALUES ('cr2', 'pass2', 'cjohnson@email.com', 2, 'Carl', 'Johnson', 'Cosmonaut prepare team lead');
INSERT INTO aero.company_representatives (cr_name, cr_pass, cr_email, comp_id, cr_first_name, cr_last_name, cr_position) VALUES ('cr3', 'pass3', 'sbrick@email.com', 3, 'Steve', 'Brick', 'Technological specialist');