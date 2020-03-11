create table author(
	id int primary key,
	name varchar(64),
	address varchar(128)
);

create table book(
	id int primary key,
	book_name varchar(64),
	price double,
	author_id int forgien key
);