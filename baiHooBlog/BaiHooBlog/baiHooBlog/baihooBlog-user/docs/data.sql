/*
Navicat MySQL Data Transfer

Source Server         : 192.168.1.15
Source Server Version : 50630
Source Host           : 192.168.1.15:3306
Source Database       : baihooblog

Target Server Type    : MYSQL
Target Server Version : 50630
File Encoding         : 65001

Date: 2018-08-09 17:11:41
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for authority
-- ----------------------------
DROP TABLE IF EXISTS `authority`;
CREATE TABLE `authority` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主鍵，權限唯一標識 ， 自動增長策略',
  `NAME` varchar(55) DEFAULT NULL COMMENT '權限名稱',
  `CREATE_TIME` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '創建時間',
  `UPDATE_TIME` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最後更新時間',
  `DESCRIPTION` varchar(255) DEFAULT NULL COMMENT '描述',
  `VERSION` bigint(20) DEFAULT NULL COMMENT '版本',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of authority
-- ----------------------------
INSERT INTO `authority` VALUES ('1', 'ROLE_ADMIN', '2018-08-09 17:07:59', '2018-08-09 17:07:59', 'ROLE_ADMIN', null);
INSERT INTO `authority` VALUES ('2', 'ROLE_USER', '2018-08-09 17:08:14', '2018-08-09 17:08:23', 'ROLE_USER', null);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主鍵，用戶唯一標識 ， 自動增長策略',
  `NAME` varchar(55) DEFAULT NULL COMMENT '用戶姓名',
  `EMAIL` varchar(55) DEFAULT NULL COMMENT '用戶郵箱',
  `USERNAME` varchar(55) DEFAULT NULL COMMENT '用户账号，用户登录时的唯一标识,唯一不能重複',
  `PASSWORD` varchar(99) DEFAULT NULL COMMENT '用戶密碼',
  `AVATAR` varchar(199) DEFAULT NULL COMMENT '頭像圖片地址',
  `CREATE_TIME` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '創建時間',
  `UPDATE_TIME` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最後更新時間',
  `DESCRIPTION` varchar(525) DEFAULT NULL COMMENT '描述',
  `VERSION` bigint(20) DEFAULT NULL COMMENT '版本',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'baihoo', 'baihoo@gmail.com', 'baihoo', '$2a$10$qcEMlD0TEdO4I5uE4OBpI.2ijZC8pZ7hJuFxiZofSLacLJfHWBe6O', '/favicon.ico', '2018-08-09 17:02:17', '2018-08-09 17:02:50', null, null);
INSERT INTO `user` VALUES ('2', 'admin', 'admin@baihoo.com', 'admin', '$2a$10$qcEMlD0TEdO4I5uE4OBpI.2ijZC8pZ7hJuFxiZofSLacLJfHWBe6O', '/favicon.ico', '2018-08-09 17:02:44', '2018-08-09 17:03:05', null, null);

-- ----------------------------
-- Table structure for user_authority
-- ----------------------------
DROP TABLE IF EXISTS `user_authority`;
CREATE TABLE `user_authority` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主鍵，中間表唯一標識 ， 自動增長策略',
  `USER_ID` bigint(20) DEFAULT NULL COMMENT '用戶表主鍵id',
  `AUTHORITY_ID` bigint(20) DEFAULT NULL COMMENT '權限表主鍵id',
  PRIMARY KEY (`ID`),
  KEY `FKgvxjs381k6f48d5d2yi11uh89` (`AUTHORITY_ID`),
  KEY `FKpqlsjpkybgos9w2svcri7j8xy` (`USER_ID`),
  CONSTRAINT `FKgvxjs381k6f48d5d2yi11uh89` FOREIGN KEY (`AUTHORITY_ID`) REFERENCES `authority` (`ID`),
  CONSTRAINT `FKpqlsjpkybgos9w2svcri7j8xy` FOREIGN KEY (`USER_ID`) REFERENCES `user` (`ID`),
  CONSTRAINT `user_authority_ibfk_1` FOREIGN KEY (`USER_ID`) REFERENCES `user` (`ID`) ON DELETE CASCADE,
  CONSTRAINT `user_authority_ibfk_2` FOREIGN KEY (`AUTHORITY_ID`) REFERENCES `authority` (`ID`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_authority
-- ----------------------------
INSERT INTO `user_authority` VALUES ('1', '1', '1');
INSERT INTO `user_authority` VALUES ('2', '2', '2');
