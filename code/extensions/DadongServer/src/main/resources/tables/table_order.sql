CREATE TABLE `shangpin_category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `orderid` varchar(128) NOT NULL COMMENT '订单编号',
  `goods_info` text DEFAULT NULL COMMENT '订单信息json',
  `total_price` varchar(256) DEFAULT NULL,
  `ext2` varchar(256) DEFAULT NULL,
  `ext3` varchar(256) DEFAULT NULL,
  `ext4` varchar(256) DEFAULT NULL,
  `ext5` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品大类';