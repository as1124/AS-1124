--商品类别
CREATE TABLE `shangpin_category` (
  `id` int NOT NULL AUTO_INCREMENT,
  `categoryid` varchar(128) NOT NULL COMMENT '类别编号',
  `name` varchar(200) DEFAULT NULL COMMENT '类别名称',
  `ext1` varchar(200) DEFAULT NULL,
  `ext2` varchar(200) DEFAULT NULL,
  `ext3` varchar(200) DEFAULT NULL,
  `ext4` varchar(200) DEFAULT NULL,
  `ext5` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品大类';