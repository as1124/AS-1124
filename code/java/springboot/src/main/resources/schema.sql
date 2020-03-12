create table author(
	id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR(64) NOT NULL,
	address VARCHAR(128)
);

create table book(
	id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
	book_name VARCHAR(64) NOT NULL,
	price DOUBLE,
	author_id INT UNSIGNED, 
	FOREIGN KEY(author_id) REFERENCES author(id)
);