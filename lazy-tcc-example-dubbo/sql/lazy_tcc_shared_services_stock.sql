/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50639
Source Host           : localhost:3306
Source Database       : lazy_tcc_shared_services_stock

Target Server Type    : MYSQL
Target Server Version : 50639
File Encoding         : 65001

Date: 2018-12-10 23:01:03
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_stock
-- ----------------------------
DROP TABLE IF EXISTS `t_stock`;
CREATE TABLE `t_stock` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `product_sku` varchar(32) NOT NULL COMMENT '产品sku',
  `stock_num` int(10) NOT NULL COMMENT '库存数量',
  `frozen_num` int(10) NOT NULL COMMENT '冻结数量',
  `version` bigint(20) NOT NULL COMMENT '乐观锁版本号',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_1` (`product_sku`) USING BTREE COMMENT '唯一索引-1'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
