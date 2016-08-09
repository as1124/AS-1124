--红包记录
create table red_package(
	id int primary key auto_increment,
	openid varchar(256) not null,
	count int not null,
	subject_id int,
	device_id int,
	foreign key(subject_id) references red_package_subject(id),
	foreign key(device_id) references pworld_devices(id)
);

--红包活动主题
create table red_package_subject(
	id int primary key auto_increment,
	title varchar(100) not null,
	description varchar(200),
	start_time varchar(20),
	end_time varchar(20),
	totle_count int,
	people_count int default(0),
	ext1 varchar(50),
	ext2 varchar(50)
);

--接入设备列表
create table pworld_devices(
	id int primary key auto_increment,
	device_ip varchar(50) not null,
	emp_token varchar(50) not null,
	device_os varchar(10)
);