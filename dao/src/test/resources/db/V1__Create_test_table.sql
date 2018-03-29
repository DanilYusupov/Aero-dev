CREATE TABLE user_test (
  userId SERIAL PRIMARY KEY,
  userName VARCHAR(64) NOT NULL,
  userPassword VARCHAR(64) NOT NULL,
  userEmail VARCHAR(128) NOT NULL,
  userLevel SMALLINT DEFAULT 0
)