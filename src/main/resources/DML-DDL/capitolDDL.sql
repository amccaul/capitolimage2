DROP TABLE IF EXISTS Transaction;

DROP TABLE IF EXISTS Subscription;
DROP TABLE IF EXISTS capitolUser;
CREATE TABLE capitolUser (
	User_ID SERIAL PRIMARY KEY,
	Username VARCHAR NOT NULL, 
	Password VARCHAR NOT NULL, 
	Updated TIMESTAMP, 
	Created TIMESTAMP,
	role VARCHAR,
	Enabled BOOLEAN,
	expired BOOLEAN,
	locked BOOLEAN,
	credentials_Locked BOOLEAN, 
	permissions VARCHAR
	
);

DROP TABLE IF EXISTS images;
CREATE TABLE images (
    User_ID INTEGER PRIMARY KEY,
    url VARCHAR NOT NULL,
    uploaded TIMESTAMP NOT NULL,
)
