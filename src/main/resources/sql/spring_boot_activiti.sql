/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50527
Source Host           : localhost:3306
Source Database       : spring_boot_activiti

Target Server Type    : MYSQL
Target Server Version : 50527
File Encoding         : 65001

Date: 2017-12-07 19:58:48
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for approval_history
-- ----------------------------
DROP TABLE IF EXISTS `approval_history`;
CREATE TABLE `approval_history` (
  `id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `leave_bill_id` bigint(20) NOT NULL,
  `memo` varchar(255) DEFAULT NULL,
  `create_by` bigint(20) NOT NULL,
  `update_by` bigint(20) NOT NULL,
  `gm_create` datetime NOT NULL,
  `gm_modified` datetime NOT NULL,
  `is_delete` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for leave_bill
-- ----------------------------
DROP TABLE IF EXISTS `leave_bill`;
CREATE TABLE `leave_bill` (
  `id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `days` int(11) NOT NULL,
  `reson` varchar(255) NOT NULL,
  `begin_time` datetime NOT NULL,
  `create_by` bigint(20) NOT NULL,
  `update_by` bigint(20) NOT NULL,
  `gm_create` datetime NOT NULL,
  `gm_modified` datetime NOT NULL,
  `is_delete` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_en_name` varchar(50) NOT NULL,
  `role_cn_name` varchar(50) NOT NULL,
  `create_by` bigint(20) NOT NULL,
  `update_by` bigint(20) NOT NULL,
  `gm_create` datetime NOT NULL,
  `gm_modified` datetime NOT NULL,
  `is_delete` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(50) NOT NULL,
  `moble` varchar(11) NOT NULL,
  `password` varchar(50) NOT NULL,
  `create_by` bigint(20) NOT NULL,
  `update_by` bigint(20) NOT NULL,
  `gm_create` datetime NOT NULL,
  `gm_modified` datetime NOT NULL,
  `is_delete` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  `create_by` bigint(20) NOT NULL,
  `update_by` bigint(20) NOT NULL,
  `gm_create` datetime NOT NULL,
  `gm_modified` datetime NOT NULL,
  `is_delete` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
