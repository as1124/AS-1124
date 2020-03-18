drop table author;
create table author(
	id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
	user_name VARCHAR(64) NOT NULL,
	password VARCHAR(64),
	address VARCHAR(128)
);

drop table book;
create table book(
	id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
	reader VARCHAR(128),
	isbn VARCHAR(64),
	title VARCHAR(64) NOT NULL,
	price DOUBLE,
	description VARCHAR(64),
	author_id INT UNSIGNED, 
	FOREIGN KEY(author_id) REFERENCES author(id)
);

insert into `book`values(3, null, 'ISBN', '三体', 33.5, '外星人大战', 2);
insert into `book`values(4, null, 'ISBN', '福尔摩斯', 44.5, '抽大烟来破案', 3);
insert into `book`values(5, null, 'ISBN', '放风筝的人', 55.5, '伊拉克战争后遗症', 4);
