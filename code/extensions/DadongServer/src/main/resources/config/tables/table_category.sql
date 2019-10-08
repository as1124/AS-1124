--商品类别
CREATE TABLE `goods_category` (
  `id` int NOT NULL AUTO_INCREMENT,
  `categoryid` varchar(32) NOT NULL COMMENT '类别编号',
  `name` varchar(256) DEFAULT NULL COMMENT '类别名称',
  `ext1` varchar(256) DEFAULT NULL,
  `ext2` varchar(256) DEFAULT NULL,
  `ext3` varchar(256) DEFAULT NULL,
  `ext4` varchar(256) DEFAULT NULL,
  `ext5` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品大类';
