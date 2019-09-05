--订单信息表
CREATE TABLE `shangpin_order` (
  `id` int NOT NULL AUTO_INCREMENT,
  `orderid` varchar(128) NOT NULL COMMENT '订单编号',
  `openid` varchar(256) DEFAULT NULL,
  `unionid` varchar(256) DEFAULT NULL,
  `goods_info` text DEFAULT NULL COMMENT '订单信息json',
  `total_price` varchar(16) DEFAULT NULL COMMENT '订单总价',
  `order_time` date DEFAULT NULL COMMENT '下单时间',
  `order_status` int DEFAULT -1 COMMENT '订单状态',
  `ext2` varchar(256) DEFAULT NULL,
  `ext3` varchar(256) DEFAULT NULL,
  `ext4` varchar(256) DEFAULT NULL,
  `ext5` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单';