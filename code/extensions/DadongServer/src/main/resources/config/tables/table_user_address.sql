--收货地址表
CREATE TABLE `user_address` (
  `addressid` int NOT NULL AUTO_INCREMENT,
  `openid` varchar(256) NOT NULL COMMENT '映射的openid',
  `address` varchar(256) DEFAULT NULL COMMENT '收件地址',
  `remark` varchar(64) DEFAULT NULL COMMENT '地址简称',
  `user_name` varchar(64) DEFAULT NULL COMMENT '收件人',
  `telphone` varchar(16) DEFAULT NULL COMMENT '电话',
  `ext1` varchar(256) DEFAULT NULL,
  `ext2` varchar(256) DEFAULT NULL,
  `ext3` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='收货地址';
