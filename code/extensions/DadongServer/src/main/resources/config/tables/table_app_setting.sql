--客户端版本信息
DROP TABLE IF EXISTS `app_setting`;
CREATE TABLE `app_setting` (
  `client_version` varchar(16) NULL,
  `client_type` int NOT NULL DEFAULT -1,
  `setting` text COMMENT '配置json'
) ENGINE = InnoDB DEFAULT CHARSET=utf8 COMMENT='客户端配置表';