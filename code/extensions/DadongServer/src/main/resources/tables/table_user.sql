--用户基本信息表
CREATE TABLE `user_info` (
  `userid` int(11) NOT NULL AUTO_INCREMENT,
  `openid` varchar(256) DEFAULT NULL,
  `unionid` varchar(256) DEFAULT NULL,
  `user_level` int(11) DEFAULT '-1' COMMENT '用户级别',
  `wechat_name` blob COMMENT '微信昵称',
  `head_icon` varchar(512) DEFAULT NULL COMMENT '头像URL',
  `telphone` varchar(15) DEFAULT NULL,
  `name` blob,
  `register_time` varchar(16) DEFAULT NULL COMMENT '注册时间',
  `user_status` int(11) DEFAULT NULL COMMENT '用户状态',
  `ext1` varchar(256) DEFAULT NULL,
  `ext2` varchar(256) DEFAULT NULL,
  `ext3` varchar(256) DEFAULT NULL,
  `ext4` varchar(256) DEFAULT NULL,
  `ext5` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`userid`),
  UNIQUE KEY `unionid_UNIQUE` (`unionid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户基本信息表';
