ALTER TABLE aero.users ADD COLUMN usr_first_name VARCHAR(32);
ALTER TABLE aero.users ADD COLUMN usr_last_name VARCHAR(32);
ALTER TABLE aero.users ADD COLUMN usr_biography TEXT;
ALTER TABLE aero.users ADD COLUMN usr_rating INT DEFAULT 0;