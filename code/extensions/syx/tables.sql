create table syx_user(
	id int primary key auto_increment,
    telephone varchar(15) not null,
    wechat varchar(50) null,
    wechat_name varchar(128) null,
    child_name varchar(128) null,
    ext1 varchar(128) null,
    ext2 varchar(128) null,
    ext3 varchar(128) null,
    ext4 varchar(128) null,
    ext5 varchar(128) null
);

create table syx_activity(
	id int primary key auto_increment,
    activity_id int,
    activity varchar(128),
    district varchar(50),
    address varchar(256),
    class_time varchar(20),
    telphone varchar(15) not null,
    child_name varchar(128),
    child_height int,
    createtime datetime,
    pay_state varchar(10),
    pay_no varchar(128),
    ext1 varchar(128) null,
    ext2 varchar(128) null,
    ext3 varchar(128) null,
    ext4 varchar(128) null,
    ext5 varchar(128) null
);