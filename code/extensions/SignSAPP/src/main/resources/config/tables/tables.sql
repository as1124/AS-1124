
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
) ENGINE = InnoDB AUTO_INCREMENT = 4 COMMENT = '程序配置信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of app_setting
-- ----------------------------
INSERT INTO `app_setting` VALUES (1, '1.0.0', 0, '{"bannerImg":["http://www.xnnews.com.cn/wenyu/lxsj/201704/W020170420667356383883.jpg","https://www.uz456.com/zb_users/upload/2019/08/201908071565146830889674.jpg","/images/banner/banner3.jpeg","/images/banner/banner4.jpg"]}');
INSERT INTO `app_setting` VALUES (2, '1.0.0', 1, '{"bannerImg":["http://www.xnnews.com.cn/wenyu/lxsj/201704/W020170420667356383883.jpg","https://www.uz456.com/zb_users/upload/2019/08/201908071565146830889674.jpg","/images/banner/banner3.jpeg","/images/banner/banner4.jpg"]}');
INSERT INTO `app_setting` VALUES (3, '1.0.0', 2, '{"bannerImg":["http://www.xnnews.com.cn/wenyu/lxsj/201704/W020170420667356383883.jpg","https://www.uz456.com/zb_users/upload/2019/08/201908071565146830889674.jpg","/images/banner/banner3.jpeg","/images/banner/banner4.jpg"]}');

