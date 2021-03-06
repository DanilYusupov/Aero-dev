-- Create projects table

CREATE TABLE aero.projects (
  prj_id   SERIAL PRIMARY KEY,
  prj_name VARCHAR(64) NOT NULL UNIQUE,
  usr_id   BIGINT      NOT NULL,
  prj_type VARCHAR(32),
  CONSTRAINT owner_id_fk FOREIGN KEY (usr_id) REFERENCES aero.users
);

INSERT INTO aero.projects (prj_name, usr_id, prj_type) VALUES ('Discover #1', 1, 'AERODYNAMICS');
INSERT INTO aero.projects (prj_name, usr_id, prj_type) VALUES ('Discover #2', 1, 'AERODYNAMICS');
INSERT INTO aero.projects (prj_name, usr_id, prj_type) VALUES ('Discover #3', 1, 'AERODYNAMICS');

-- Create project's content data

CREATE TABLE aero.project_content (
  id              SERIAL PRIMARY KEY,
  prj_id          BIGINT NOT NULL,
  prj_logo        BYTEA,
  prj_description TEXT,
  prj_date        DATE,
  CONSTRAINT content_fk FOREIGN KEY (prj_id) REFERENCES aero.projects
);

-- Create project's images data

CREATE TABLE aero.project_images (
  img_id    SERIAL PRIMARY KEY,
  prj_id    BIGINT NOT NULL ,
  prj_image BYTEA NOT NULL,
  img_type  VARCHAR(32),
  CONSTRAINT img_fk FOREIGN KEY (prj_id) REFERENCES aero.projects
);

CREATE TABLE aero.project_files (
  file_id SERIAL PRIMARY KEY,
  prj_id BIGINT NOT NULL ,
  file BYTEA NOT NULL ,
  content_type VARCHAR(32),
  CONSTRAINT file_fk FOREIGN KEY (prj_id) REFERENCES aero.projects
)