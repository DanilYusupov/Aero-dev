ALTER TABLE aero.project_content
  DROP COLUMN prj_imgs;

ALTER TABLE aero.projects DROP COLUMN prj_description;

CREATE TABLE aero.project_images (
  img_id    SERIAL PRIMARY KEY,
  prj_id    BIGINT REFERENCES aero.projects (prj_id),
  prj_image BYTEA NOT NULL,
  img_type  VARCHAR(32)
);

ALTER TABLE aero.project_content
  ALTER COLUMN prj_id TYPE BIGINT;

ALTER TABLE aero.project_files
  ALTER COLUMN prj_id TYPE BIGINT;