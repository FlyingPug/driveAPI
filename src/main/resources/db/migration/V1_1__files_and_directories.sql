CREATE TABLE directory (
	id serial PRIMARY KEY,
	username VARCHAR (255) UNIQUE NOT NULL,
	password VARCHAR (512) NOT NULL,
	owner_id INT NOT NULL,
	parent_id INT,
	FOREIGN KEY (parent_id) REFERENCES directory (id),
	FOREIGN KEY (owner_id) REFERENCES account (id)
);

CREATE TABLE file(
    id serial PRIMARY KEY,
    name VARCHAR (255) NOT NULL,
    is_public BIT NOT NULL,
    dir_id INT,
    owner_id INT NOT NULL,
    FOREIGN KEY (dir_id) REFERENCES directory (id),
    FOREIGN KEY (owner_id) REFERENCES account (id)
);

