-- Create projects table

CREATE TABLE aero.projects (
  prj_id          SERIAL PRIMARY KEY,
  prj_name        VARCHAR(64) NOT NULL UNIQUE,
  usr_id          BIGINT NOT NULL,
  prj_type        VARCHAR(32),
  CONSTRAINT owner_id_fk FOREIGN KEY (usr_id) REFERENCES aero.users
);

INSERT INTO aero.projects (prj_name, usr_id, prj_type) VALUES ('Discover #1', 1, 'AERODYNAMICS');
INSERT INTO aero.projects (prj_name, usr_id, prj_type) VALUES ('Discover #2', 1, 'AERODYNAMICS');
INSERT INTO aero.projects (prj_name, usr_id, prj_type) VALUES ('Discover #3', 1, 'AERODYNAMICS');