/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50639
Source Host           : localhost:3306
Source Database       : lazy_tcc

Target Server Type    : MYSQL
Target Server Version : 50639
File Encoding         : 65001

Date: 2018-12-16 12:33:41
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_lazy_tcc_transation
-- ----------------------------
DROP TABLE IF EXISTS `t_lazy_tcc_transation`;
CREATE TABLE `t_lazy_tcc_transation` (
  `tx_id` bigint(20) NOT NULL COMMENT '主键',
  `tx_phase` int(5) NOT NULL COMMENT '事务阶段 try,confirm,cancel',
  `retry_count` int(5) NOT NULL COMMENT '重试次数',
  `content_byte` varbinary(8000) NOT NULL COMMENT '参与者字节码',
  `version` bigint(20) NOT NULL COMMENT '乐观锁版本号',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `last_update_time` datetime NOT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`tx_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='lazy-tcc事务日志表';
