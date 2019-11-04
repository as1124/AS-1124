
-- ----------------------------
-- Table structure for app_setting
-- ----------------------------
DROP TABLE IF EXISTS `app_setting`;
CREATE TABLE `app_setting`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `client_version` varchar(16) NULL DEFAULT '' COMMENT '用户小程序版本号',
  `client_type` int(11) NOT NULL DEFAULT -1 COMMENT '客户端类型',
  `setting` json NULL COMMENT '客户端配置json',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '程序配置信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of app_setting
-- ----------------------------
INSERT INTO `app_setting` VALUES (1, '1.0.0', 0, '{\"bannerImg\": [\"http://www.xnnews.com.cn/wenyu/lxsj/201704/W020170420667356383883.jpg\", \"https://www.uz456.com/zb_users/upload/2019/08/201908071565146830889674.jpg\", \"/images/banner/banner3.jpeg\", \"/images/banner/banner4.jpg\"]}');
INSERT INTO `app_setting` VALUES (2, '1.0.0', 1, '{\"bannerImg\": [\"http://www.xnnews.com.cn/wenyu/lxsj/201704/W020170420667356383883.jpg\", \"https://www.uz456.com/zb_users/upload/2019/08/201908071565146830889674.jpg\", \"/images/banner/banner3.jpeg\", \"/images/banner/banner4.jpg\"]}');
INSERT INTO `app_setting` VALUES (3, '1.0.0', 2, '{\"bannerImg\": [\"http://www.xnnews.com.cn/wenyu/lxsj/201704/W020170420667356383883.jpg\", \"https://www.uz456.com/zb_users/upload/2019/08/201908071565146830889674.jpg\", \"/images/banner/banner3.jpeg\", \"/images/banner/banner4.jpg\"]}');

-- ----------------------------
-- Table structure for goods_category
-- ----------------------------
DROP TABLE IF EXISTS `goods_category`;
CREATE TABLE `goods_category`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `categoryid` varchar(16) NOT NULL UNIQUE COMMENT '类别编号',
  `name` varchar(256) NULL DEFAULT '' COMMENT '类别名称',
  `ext1` varchar(256) NULL DEFAULT NULL,
  `ext2` varchar(256) NULL DEFAULT NULL,
  `ext3` varchar(256) NULL DEFAULT NULL,
  `ext4` varchar(256) NULL DEFAULT NULL,
  `ext5` varchar(256) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 COMMENT = '商品大类' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of goods_category
-- ----------------------------
INSERT INTO `goods_category` VALUES (1, '100101', '电动自行车', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `goods_category` VALUES (2, '200101', '电动自行车配件', NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for goods_info
-- ----------------------------
DROP TABLE IF EXISTS `goods_info`;
CREATE TABLE `goods_info`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `goodsno` varchar(64) NOT NULL UNIQUE COMMENT '商品编号',
  `name` varchar(256) NULL DEFAULT '' COMMENT '商品名称',
  `preview_icon` varchar(256) NULL DEFAULT '' COMMENT '缩略图标',
  `categoryid` varchar(16) NULL DEFAULT '' COMMENT '大类',
  `h5url` varchar(512) NULL DEFAULT '' COMMENT '详情页H5',
  `price` varchar(16) NULL DEFAULT '0' COMMENT '售价',
  `original_price` varchar(16) NULL DEFAULT '0' COMMENT '原价',
  `amount` int(11) NULL DEFAULT 0 COMMENT '存货量',
  `show_order` tinyint(5) NULL DEFAULT -1 COMMENT '显示顺序',
  `detail` json NULL COMMENT '其他信息的json配置，如颜色等',
  `ext1` varchar(256) NULL DEFAULT '',
  `ext2` varchar(256) NULL DEFAULT '',
  `ext3` varchar(256) NULL DEFAULT '',
  `ext4` varchar(256) NULL DEFAULT '',
  `ext5` varchar(256) NULL DEFAULT '',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 COMMENT = '商品信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of goods_info
-- ----------------------------
INSERT INTO `goods_info` VALUES (1, 'A1001', '初级折叠电动车', 'https://cn.bing.com/th?id=OIP.hz2MgoxjfUc88KtYULuN3gAAAA&pid=Api&rs=1', '100101', 'https://www.baidu.com', '1799.00', '1999.00', 0, 1, '{\"subtype\": [\"黑色\", \"红色\", \"白色\"]}', '', '', '', '', '');
INSERT INTO `goods_info` VALUES (2, 'A1002', '初级折叠电动车', 'https://cn.bing.com/th?id=OIP.QNGmLsUJkqUpgMbYBMpPqQAAAA&pid=Api&rs=1', '100101', 'https://www.cnblogs.com/xiaohuochai/p/9093819.html', '1799.00', '1999.00', 0, 5, '{\"subtype\": [\"黑色\", \"红色\", \"白色\"]}', '', '', '', '', '');
INSERT INTO `goods_info` VALUES (3, 'A1003', '中级折叠电动车', 'http://img.zcool.cn/community/01270857c687670000018c1b182fa9.jpg', '100101', 'https://www.cnblogs.com/xiaohuochai/p/9093819.html', '1799.00', '1999.00', 0, 2, '{\"subtype\": [\"黑色\", \"红色\", \"白色\"]}', '', '', '', '', '');
INSERT INTO `goods_info` VALUES (4, 'A1012', '高级折叠电动车', 'http://jf258.com/uploads/2014-08-06/165418374.jpg', '100101', 'https://www.cnblogs.com/xiaohuochai/p/9093819.html', '1799.00', '1999.00', 0, 6, '{\"subtype\": [\"黑色\", \"红色\", \"白色\"]}', '', '', '', '', '');
INSERT INTO `goods_info` VALUES (5, 'B1002', '折叠电滑板车', 'http://5b0988e595225.cdn.sohucs.com/images/20180421/193a3464c8ba4713ac2d3f2fb93829c7.jpeg', '100101', 'https://www.cnblogs.com/xiaohuochai/p/9093819.html', '1799.00', '1999.00', 0, 0, '{\"subtype\": [\"黑色\", \"红色\", \"白色\"]}', '', '', '', '', '');
INSERT INTO `goods_info` VALUES (6, 'A11102', '大冬折叠电动车X7标准版', 'http://www.jf258.com/uploads/2013-07-07/111335559.jpg', '100101', 'https://www.cnblogs.com/xiaohuochai/p/9093819.html', '3799.00', '4999.00', 0, 10, '{\"subtype\": [\"黑色\", \"红色\", \"白色\"]}', '', '', '', '', '');
INSERT INTO `goods_info` VALUES (7, 'A1005', '大冬折叠电动车X7 PRO升级版', 'http://p1.qzone.la/upload/0/7db08929-8846-4286-9138-42e4d7b512cd.jpg', '100101', 'https://www.cnblogs.com/xiaohuochai/p/9093819.html', '1799.00', '1999.00', 0, 3, '{\"subtype\": [\"黑色\", \"红色\", \"白色\"]}', '', '', '', '', '');
INSERT INTO `goods_info` VALUES (8, 'C1002', '测试擦汗给你字符串螺丝钉咖啡碱看看看看斤斤计较哈哈哈哈哈哈哈哈哈水水水水', 'https://cn.bing.com/th?id=OIP.QNGmLsUJkqUpgMbYBMpPqQAAAA&pid=Api&rs=1', '100101', 'https://www.cnblogs.com/xiaohuochai/p/9093819.html', '2799.00', '1999.00', 0, 21, '{\"subtype\": [\"黑色\", \"红色\", \"白色\"]}', '', '', '', '', '');
INSERT INTO `goods_info` VALUES (9, 'A2002', '模拟数据哈哈哈', 'http://www.gx8899.com/uploads/allimg/2018021008/xphp3ks04im.jpg', '100101', 'https://www.cnblogs.com/xiaohuochai/p/9093819.html', '2799.00', '1999.00', 0, 13, '{\"subtype\": [\"黑色\", \"红色\", \"白色\"]}', '', '', '', '', '');

-- ----------------------------
-- Table structure for goods_order
-- ----------------------------
DROP TABLE IF EXISTS `goods_order`;
CREATE TABLE `goods_order`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `orderid` varchar(32) NOT NULL UNIQUE COMMENT '订单编号',
  `openid` varchar(256) NULL DEFAULT '',
  `unionid` varchar(256) NULL DEFAULT '',
  `goods_info` text NULL COMMENT '订单信息json',
  `total_price` varchar(16) NULL DEFAULT '0' COMMENT '订单总价',
  `discount` varchar(16) NULL DEFAULT '0' COMMENT '折扣金额',
  `order_time` date NULL DEFAULT NULL COMMENT '下单时间',
  `order_status` int(11) NULL DEFAULT -1 COMMENT '订单状态',
  `wx_prepayid` varchar(128) NULL DEFAULT '' COMMENT '微信预支付订单号',
  `wx_payid` varchar(128) NULL DEFAULT '' COMMENT '微信支付订单号',
  `express_info` text NULL COMMENT '物流信息',
  `ext1` varchar(256) NULL DEFAULT '',
  `ext2` varchar(256) NULL DEFAULT '',
  `ext3` varchar(256) NULL DEFAULT '',
  `ext4` varchar(256) NULL DEFAULT '',
  `ext5` varchar(256) NULL DEFAULT '',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 COMMENT = '订单' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_address
-- ----------------------------
DROP TABLE IF EXISTS `user_address`;
CREATE TABLE `user_address`  (
  `addressid` int(11) NOT NULL AUTO_INCREMENT,
  `openid` varchar(256) NOT NULL UNIQUE COMMENT '映射的openid',
  `address` varchar(256) NULL DEFAULT '' COMMENT '收件地址',
  `remark` varchar(64) NULL DEFAULT '' COMMENT '地址简称',
  `user_name` varchar(64) NULL DEFAULT '' COMMENT '收件人',
  `telphone` varchar(16) NULL DEFAULT '' COMMENT '电话',
  `ext1` varchar(256) NULL DEFAULT '',
  `ext2` varchar(256) NULL DEFAULT '',
  `ext3` varchar(256) NULL DEFAULT '',
  PRIMARY KEY (`addressid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 COMMENT = '收货地址' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info`  (
  `userid` int(11) NOT NULL AUTO_INCREMENT,
  `openid` varchar(256) NOT NULL UNIQUE DEFAULT '',
  `unionid` varchar(256) NULL DEFAULT '',
  `appid` varchar(256) NULL DEFAULT '' COMMENT '对应的微信APPID',
  `user_level` int(11) NULL DEFAULT 0 COMMENT '用户级别',
  `wechat_name` text NULL COMMENT '微信昵称',
  `name` varchar(256) NULL DEFAULT '' COMMENT '自己设置的名称',
  `head_icon` varchar(256) NULL DEFAULT '' COMMENT '头像URL',
  `telephone` varchar(16) NULL UNIQUE DEFAULT '',
  `register_time` date NULL DEFAULT NULL COMMENT '注册时间',
  `user_status` int(11) NULL DEFAULT -1 COMMENT '用户状态',
  `goods_car` text NULL COMMENT '购物车内容json',
  `ext1` varchar(256) NULL DEFAULT '',
  `ext2` varchar(256) NULL DEFAULT '',
  `ext3` varchar(256) NULL DEFAULT '',
  `ext4` varchar(256) NULL DEFAULT '',
  `ext5` varchar(256) NULL DEFAULT '',
  PRIMARY KEY (`userid`) USING BTREE,
  UNIQUE INDEX `unionid_UNIQUE`(`unionid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 COMMENT = '用户基本信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_info
-- ----------------------------
INSERT INTO `user_info` VALUES (1, 'oAhJX4-Dud2jrgJu3YtQ47Qz_Bj0', '', '', 0, NULL, NULL, '', '', NULL, -1, NULL, '', '', '', '', '');
