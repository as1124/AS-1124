--用户基本信息表
CREATE TABLE `user_info` (
  `userid` int NOT NULL AUTO_INCREMENT,
  `openid` varchar(256) DEFAULT '',
  `unionid` varchar(256) DEFAULT '',
  `appid` varchar(256) DEFAULT '' COMMENT '对应的微信APPID',
  `user_level` int DEFAULT '-1' COMMENT '用户级别',
  `wechat_name` text COMMENT '微信昵称',
  `name` varchar(256) COMMENT '自己设置的名称',
  `head_icon` varchar(256) DEFAULT NULL COMMENT '头像URL',
  `telephone` varchar(16) DEFAULT NULL,
  `register_time` date DEFAULT NULL COMMENT '注册时间',
  `user_status` int DEFAULT NULL COMMENT '用户状态',
  `ext1` varchar(256) DEFAULT NULL,
  `ext2` varchar(256) DEFAULT NULL,
  `ext3` varchar(256) DEFAULT NULL,
  `ext4` varchar(256) DEFAULT NULL,
  `ext5` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`userid`),
  UNIQUE KEY `unionid_UNIQUE` (`unionid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户基本信息表';

drop table user_info;

