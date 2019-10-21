--订单信息表
CREATE TABLE `goods_order` (
  `id` int NOT NULL AUTO_INCREMENT,
  `orderid` varchar(32) NOT NULL COMMENT '订单编号',
  `openid` varchar(256) DEFAULT '',
  `unionid` varchar(256) DEFAULT '',
  `goods_info` text DEFAULT NULL COMMENT '订单信息json',
  `total_price` varchar(16) DEFAULT '0' COMMENT '订单总价',
  `discount` varchar(16) DEFAULT '0' COMMENT '折扣金额',
  `order_time` date DEFAULT NULL COMMENT '下单时间',
  `order_status` int DEFAULT -1 COMMENT '订单状态',
  `wx_prepayid` varchar(128) DEFAULT '' COMMENT '微信预支付订单号',
  `wx_payid` varchar(128) DEFAULT '' COMMENT '微信支付订单号',
  `express_info` text DEFAULT NULL COMMENT '物流信息',
  `ext1` varchar(256) DEFAULT '',
  `ext2` varchar(256) DEFAULT '',
  `ext3` varchar(256) DEFAULT '',
  `ext4` varchar(256) DEFAULT '',
  `ext5` varchar(256) DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单';

drop table goods_order;