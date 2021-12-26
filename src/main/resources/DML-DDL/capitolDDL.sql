DROP TABLE IF EXISTS capitolImage	;
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

CREATE TABLE capitolImage (
                               User_ID INTEGER,
                               Image_Id SERIAL PRIMARY KEY,
                               image_name VARCHAR NOT NULL,
                               url VARCHAR,
                               uploaded TIMESTAMP NOT NULL,
                               updated TIMESTAMP NOT NULL,
                               CONSTRAINT fk_capitoluserid
                                   FOREIGN KEY (User_ID)
                                       REFERENCES capitoluser(user_id)
)
