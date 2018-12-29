/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50639
Source Host           : localhost:3306
Source Database       : lazy_tcc_shared_service_order

Target Server Type    : MYSQL
Target Server Version : 50639
File Encoding         : 65001

Date: 2018-12-10 23:00:47
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_order_item
-- ----------------------------
DROP TABLE IF EXISTS `t_order_item`;
CREATE TABLE `t_order_item` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `order_no` varchar(32) NOT NULL COMMENT '订单编号',
  `total_amount` varchar(32) NOT NULL COMMENT '产品金额',
  `product_num` int(10) NOT NULL COMMENT '产品数量',
  `product_sku` varchar(32) NOT NULL COMMENT '产品sku',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_1` (`product_sku`) USING BTREE COMMENT '唯一索引-1'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `t_order` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `customer_no` varchar(32) NOT NULL COMMENT '客户编号',
  `total_amount` decimal(13,2) NOT NULL COMMENT '订单总金额',
  `order_no` varchar(32) NOT NULL COMMENT '订单编号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

