ALTER TABLE aero.projects RENAME prj_owner TO usr_id;
ALTER TABLE aero.projects ADD CONSTRAINT owner_id_fk FOREIGN KEY (usr_id) REFERENCES aero.users;

DELETE FROM aero.project_content;
ALTER TABLE aero.project_content DROP CONSTRAINT project_content_pkey;
ALTER TABLE aero.project_content ALTER COLUMN prj_id TYPE BIGINT;
ALTER TABLE aero.project_content ADD COLUMN id SERIAL PRIMARY KEY;
ALTER TABLE aero.project_content RENAME CONSTRAINT project_content_prj_id_fkey TO content_fk;

ALTER TABLE aero.project_images ALTER COLUMN prj_id SET NOT NULL;
ALTER TABLE aero.project_images RENAME CONSTRAINT project_images_prj_id_fkey TO img_fk;

ALTER TABLE aero.project_files ADD COLUMN file_id SERIAL PRIMARY KEY;
ALTER TABLE aero.project_files ALTER COLUMN prj_id SET NOT NULL;
ALTER TABLE aero.project_files ADD COLUMN content_type VARCHAR(32);
ALTER TABLE aero.project_files RENAME CONSTRAINT project_files_prj_id_fkey TO file_fk;

ALTER TABLE aero.avatars RENAME av_owner TO usr_id;
ALTER TABLE aero.avatars ALTER COLUMN av_id TYPE BIGINT;
ALTER TABLE aero.avatars ALTER COLUMN usr_id SET NOT NULL;
ALTER TABLE aero.avatars RENAME CONSTRAINT avatars_av_owner_fkey TO av_fk;

ALTER TABLE aero.company_representatives RENAME CONSTRAINT company_representatives_cr_comp_id_fkey TO comp_fk;
ALTER TABLE aero.company_representatives RENAME cr_comp_id TO comp_id;
ALTER TABLE aero.company_representatives ALTER COLUMN comp_id SET NOT NULL;

ALTER TABLE aero.offers RENAME off_usr_id TO usr_id;
ALTER TABLE aero.offers RENAME off_cr_id TO cr_id;
ALTER TABLE aero.offers ALTER COLUMN usr_id SET NOT NULL;
ALTER TABLE aero.offers ALTER COLUMN cr_id SET NOT NULL;
ALTER TABLE aero.offers RENAME CONSTRAINT offers_off_usr_id_fkey TO off_usr_id;
ALTER TABLE aero.offers ADD CONSTRAINT off_cr_id FOREIGN KEY (cr_id) REFERENCES aero.company_representatives;