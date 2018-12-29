/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50639
Source Host           : localhost:3306
Source Database       : lazy_tcc_shared_services_customer

Target Server Type    : MYSQL
Target Server Version : 50639
File Encoding         : 65001

Date: 2018-12-10 23:06:00
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_customer
-- ----------------------------
DROP TABLE IF EXISTS `t_customer`;
CREATE TABLE `t_customer` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `customer_no` varchar(32) NOT NULL COMMENT '客户编号',
  `total_capital` decimal(15,3) NOT NULL COMMENT '总资金',
  `frozen_capital` decimal(15,3) NOT NULL COMMENT '冻结资金',
  `version` bigint(20) NOT NULL COMMENT '乐观锁版本号',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_1` (`customer_no`) USING BTREE COMMENT '唯一索引-1'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='资金表';
