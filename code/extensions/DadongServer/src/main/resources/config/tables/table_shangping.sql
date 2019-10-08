--商品信息表
CREATE TABLE `goods_info` (
  `id` int NOT NULL AUTO_INCREMENT,
  `goodsno` varchar(128) NOT NULL COMMENT '商品编号',
  `name` varchar(200) DEFAULT NULL COMMENT '商品名称',
  `preview_icon` varchar(200) DEFAULT NULL COMMENT '缩略图标',
  `categoryid` varchar(128) DEFAULT NULL COMMENT '大类',
  `h5url` varchar(512) DEFAULT NULL COMMENT '详情页H5',
  `price` varchar(16) DEFAULT '0' COMMENT '售价',
  `original_price` varchar(16) DEFAULT '0' COMMENT '原价',
  `amount` int DEFAULT '0' COMMENT '存货量',
  `detail` text COMMENT '一些其他信息的json',
  `ext1` varchar(256) DEFAULT NULL,
  `ext2` varchar(256) DEFAULT NULL,
  `ext3` varchar(256) DEFAULT NULL,
  `ext4` varchar(256) DEFAULT NULL,
  `ext5` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品信息';