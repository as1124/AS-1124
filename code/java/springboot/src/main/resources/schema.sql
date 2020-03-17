create table author(
	id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
	user_name VARCHAR(64) NOT NULL,
	password VARCHAR(64),
	address VARCHAR(128)
);

create table book(
	id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
	reader VARCHAR(128),
	isbn VARCHAR(64),
	title VARCHAR(64) NOT NULL,
	author VARCHAR(64),
	price DOUBLE,
	description VARCHAR(64),
	author_id INT UNSIGNED, 
	FOREIGN KEY(author_id) REFERENCES author(id)
);