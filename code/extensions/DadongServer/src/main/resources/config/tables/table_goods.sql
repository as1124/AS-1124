--商品信息表
CREATE TABLE `goods_info` (
  `id` int NOT NULL AUTO_INCREMENT,
  `goodsno` varchar(64) NOT NULL COMMENT '商品编号',
  `name` varchar(256) DEFAULT '' COMMENT '商品名称',
  `preview_icon` varchar(256) DEFAULT '' COMMENT '缩略图标',
  `categoryid` varchar(32) DEFAULT '' COMMENT '大类',
  `h5url` varchar(512) DEFAULT '' COMMENT '详情页H5',
  `price` varchar(16) DEFAULT '0' COMMENT '售价',
  `original_price` varchar(16) DEFAULT '0' COMMENT '原价',
  `amount` int DEFAULT '0' COMMENT '存货量',
  `detail` text COMMENT '其他信息的json配置，如颜色等',
  `ext1` varchar(256) DEFAULT '',
  `ext2` varchar(256) DEFAULT '',
  `ext3` varchar(256) DEFAULT '',
  `ext4` varchar(256) DEFAULT '',
  `ext5` varchar(256) DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品信息';