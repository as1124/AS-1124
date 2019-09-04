--收货地址表
CREATE TABLE `user_address` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `openid` varchar(256) NOT NULL COMMENT '映射的openid',
  `short_name` varchar(128) DEFAULT NULL COMMENT '地址简称',
  `address` varchar(512) DEFAULT NULL COMMENT '收件地址',
  `user_name` varchar(256) DEFAULT NULL COMMENT '收件人',
  `user_phone` varchar(15) DEFAULT NULL COMMENT '电话',
  `ext1` varchar(256) DEFAULT NULL,
  `ext2` varchar(256) DEFAULT NULL,
  `ext3` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='收货地址';