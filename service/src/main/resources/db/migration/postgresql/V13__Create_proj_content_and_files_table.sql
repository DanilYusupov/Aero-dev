CREATE TABLE aero.project_content (
  prj_id INTEGER REFERENCES aero.projects (prj_id),
  prj_logo BYTEA,
  prj_imgs BYTEA[3],
  prj_description TEXT,
  prj_date DATE,
  PRIMARY KEY (prj_id)
);

DELETE FROM aero.projects;

-- ALTER TABLE aero.projects DROP COLUMN prj_description;

INSERT INTO aero.projects (prj_id, prj_name, prj_owner, prj_type) VALUES (1, 'New aircraft', 12, 'DESIGN');
INSERT INTO aero.project_content VALUES (1, null, null, 'Here will be some description...');

CREATE TABLE aero.project_files (
  prj_id INTEGER REFERENCES aero.projects (prj_id),
  flie BYTEA NOT NULL
)