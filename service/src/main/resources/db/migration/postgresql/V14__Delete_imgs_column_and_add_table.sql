ALTER TABLE aero.project_content DROP COLUMN prj_imgs;
CREATE TABLE aero.project_images (
  img_id SERIAL PRIMARY KEY ,
  prj_id BIGINT REFERENCES aero.projects (prj_id),
  prj_image BYTEA NOT NULL,
  img_type VARCHAR(32)
);