CREATE SCHEMA aero;

CREATE TABLE aero.projects (
  prj_id SERIAL PRIMARY KEY,
  prj_name VARCHAR(64) NOT NULL UNIQUE,
  prj_owner BIGINT,
  prj_type VARCHAR(32)
);

INSERT INTO aero.projects (prj_id, prj_name, prj_owner, prj_type) VALUES (1, 'project', 1, 'design');

CREATE TABLE aero.project_image_test (
  img_id SERIAL PRIMARY KEY,
  prj_id BIGINT REFERENCES aero.projects (prj_id),
  prj_image BYTEA NOT NULL,
  img_type VARCHAR(32)
);

