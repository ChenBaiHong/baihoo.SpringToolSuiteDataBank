/*
Navicat MySQL Data Transfer

Source Server         : MySql
Source Server Version : 50720
Source Host           : localhost:3306
Source Database       : springsecuritystudying

Target Server Type    : MYSQL
Target Server Version : 50720
File Encoding         : 65001

Date: 2018-08-05 00:46:44
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for authority
-- ----------------------------
DROP TABLE IF EXISTS `authority`;
CREATE TABLE `authority` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `auth_Name` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of authority
-- ----------------------------
INSERT INTO `authority` VALUES ('1', 'ADMIN');
INSERT INTO `authority` VALUES ('2', 'MANAGER');
INSERT INTO `authority` VALUES ('3', 'PRESIDENT');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(32) NOT NULL,
  `pass_word` varchar(99) NOT NULL,
  `email` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_name` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'baihoo', '$2a$10$N7ME1n6kScoF3NkNaICqFuD2anQpOanTxDiWXIF9qjsgtWnCTXLsi', 'baihoo@gmail.com');
INSERT INTO `user` VALUES ('2', 'baihoo.god', '$2a$10$N7ME1n6kScoF3NkNaICqFuD2anQpOanTxDiWXIF9qjsgtWnCTXLsi', 'baihoo.god@gmail.com');
INSERT INTO `user` VALUES ('3', 'baihoo.chen', '$2a$10$N7ME1n6kScoF3NkNaICqFuD2anQpOanTxDiWXIF9qjsgtWnCTXLsi', 'baihoo.god@gmail.com');
INSERT INTO `user` VALUES ('5', 'baiHoo2', '$2a$10$pPreldvox9oX/haR.ugUjuZi8nmLXjcECfEX6BPFPxbIPTvGxAsjG', 'baiHoo.chen@hotmail.com');

-- ----------------------------
-- Table structure for user_authority
-- ----------------------------
DROP TABLE IF EXISTS `user_authority`;
CREATE TABLE `user_authority` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL,
  `authority_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `authority_id` (`authority_id`),
  CONSTRAINT `user_authority_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `user_authority_ibfk_2` FOREIGN KEY (`authority_id`) REFERENCES `authority` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_authority
-- ----------------------------
INSERT INTO `user_authority` VALUES ('1', '1', '1');
INSERT INTO `user_authority` VALUES ('2', '1', '2');
INSERT INTO `user_authority` VALUES ('3', '2', '2');
INSERT INTO `user_authority` VALUES ('4', '2', '3');
INSERT INTO `user_authority` VALUES ('5', '3', '1');
INSERT INTO `user_authority` VALUES ('6', '2', '2');
