drop table user_info;

create table user_info(
	id int primary key auto_increment,
	user_name varchar(50),
	birthday date null,
	address varchar(128)
);

insert into user_info(id, user_name, address, birthday) values(1, 'abc', '哈哈哈哈', '2019-01-01');

insert into user_info(id, user_name, address, birthday) values(2, 'Huang', '接口开', '2019-02-01');

insert into user_info(id, user_name, address) values(3, '欢哥', '千山鸟飞绝');

insert into user_info(id, user_name, address, birthday) values(4, '头疼', '什么鬼东西', '2020-01-01');
