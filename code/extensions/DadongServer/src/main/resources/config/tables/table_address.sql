--收货地址表
CREATE TABLE `user_address` (
  `id` int NOT NULL AUTO_INCREMENT,
  `openid` varchar(128) NOT NULL COMMENT '映射的openid',
  `short_name` varchar(128) DEFAULT NULL COMMENT '地址简称',
  `address` varchar(200) DEFAULT NULL COMMENT '收件地址',
  `user_name` varchar(64) DEFAULT NULL COMMENT '收件人',
  `user_phone` varchar(16) DEFAULT NULL COMMENT '电话',
  `ext1` varchar(200) DEFAULT NULL,
  `ext2` varchar(200) DEFAULT NULL,
  `ext3` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='收货地址';