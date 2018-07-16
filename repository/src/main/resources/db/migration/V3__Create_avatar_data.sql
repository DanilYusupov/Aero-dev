CREATE TABLE aero.avatars (
  av_id SERIAL PRIMARY KEY,
  usr_id BIGINT NOT NULL ,
  av_data BYTEA NOT NULL ,
  av_type VARCHAR(32),
  CONSTRAINT av_fk FOREIGN KEY (usr_id) REFERENCES aero.users
)