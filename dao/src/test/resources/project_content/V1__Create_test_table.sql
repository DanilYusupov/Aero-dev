CREATE SCHEMA aero;

CREATE TABLE aero.projects (
  prj_id SERIAL PRIMARY KEY,
  prj_name VARCHAR(64) NOT NULL UNIQUE,
  prj_owner BIGINT,
  prj_type VARCHAR(32)
);

INSERT INTO aero.projects (prj_id, prj_name, prj_owner, prj_type) VALUES (1, 'project', 1, 'design');

CREATE TABLE aero.project_content (
  prj_id BIGINT REFERENCES aero.projects (prj_id),
  prj_logo BYTEA,
  prj_description TEXT,
  prj_date DATE,
  PRIMARY KEY (prj_id)
);

