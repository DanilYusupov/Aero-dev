UPDATE aero.users SET usr_first_name='' WHERE usr_first_name ISNULL;
UPDATE aero.users SET usr_last_name='' WHERE usr_last_name ISNULL;
UPDATE aero.users SET usr_biography='' WHERE usr_biography ISNULL;
ALTER TABLE aero.users ALTER COLUMN usr_first_name SET DEFAULT '';
ALTER TABLE aero.users ALTER COLUMN usr_last_name SET DEFAULT '';
ALTER TABLE aero.users ALTER COLUMN usr_biography SET DEFAULT '';
ALTER TABLE aero.users ALTER COLUMN usr_first_name SET NOT NULL;
ALTER TABLE aero.users ALTER COLUMN usr_last_name SET NOT NULL;
ALTER TABLE aero.users ALTER COLUMN usr_biography SET NOT NULL;