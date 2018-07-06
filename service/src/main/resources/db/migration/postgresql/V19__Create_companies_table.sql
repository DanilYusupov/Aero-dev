CREATE TABLE aero.companies (
  comp_id SERIAL PRIMARY KEY,
  comp_name VARCHAR(128) NOT NULL UNIQUE,
  comp_website VARCHAR(128) DEFAULT '',
  comp_logo_url VARCHAR(250)
);

INSERT INTO aero.companies (comp_name, comp_website, comp_logo_url) VALUES ('ЦАГИ им. Н. Е. Жуковского', 'http://www.tsagi.ru/', 'http://stroyneryd-beton.ru/assets/images/clients/index/1f38f16b1b1a829940be0d83163f04b8.jpg');
INSERT INTO aero.companies (comp_name, comp_website, comp_logo_url) VALUES ('ГККД "Роскосмос"', 'https://www.roscosmos.ru/', 'https://upload.wikimedia.org/wikipedia/commons/d/da/Roscosmos_logo_ru.svg');
INSERT INTO aero.companies (comp_name, comp_website, comp_logo_url) VALUES ('Корпорация "Ростех"', 'http://rostec.ru/', 'http://toplogos.ru/images/logo-rostec.jpg');