DROp TABLE FILE;

CREATE TABLE file(
    id serial PRIMARY KEY,
    name VARCHAR (255) NOT NULL,
    is_public INT,
    dir_id INT,
    owner_id INT NOT NULL,
    FOREIGN KEY (dir_id) REFERENCES directory (id),
    FOREIGN KEY (owner_id) REFERENCES account (id)
);
