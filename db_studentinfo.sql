/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50634
Source Host           : localhost:3306
Source Database       : db_studentinfo

Target Server Type    : MYSQL
Target Server Version : 50634
File Encoding         : 65001

Date: 2016-12-14 21:37:14
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `admin_user`
-- ----------------------------
DROP TABLE IF EXISTS `admin_user`;
CREATE TABLE `admin_user` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `adminName` varchar(16) DEFAULT NULL,
  `password` varchar(16) DEFAULT NULL,
  PRIMARY KEY (`Id`),
  KEY `adminName` (`adminName`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admin_user
-- ----------------------------
INSERT INTO `admin_user` VALUES ('1', 'admin', '123456');

-- ----------------------------
-- Table structure for `stu_user`
-- ----------------------------
DROP TABLE IF EXISTS `stu_user`;
CREATE TABLE `stu_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userName` varchar(20) DEFAULT NULL,
  `password` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `FK_t_user` (`userName`) USING BTREE,
  CONSTRAINT `FK_t_user` FOREIGN KEY (`userName`) REFERENCES `t_student` (`stuNo`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of stu_user
-- ----------------------------
INSERT INTO `stu_user` VALUES ('3', '04140805', '123456');
INSERT INTO `stu_user` VALUES ('4', '04140826', '123456');

-- ----------------------------
-- Table structure for `t_admin`
-- ----------------------------
DROP TABLE IF EXISTS `t_admin`;
CREATE TABLE `t_admin` (
  `adminId` varchar(16) NOT NULL,
  `adminName` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`adminId`),
  CONSTRAINT `FK_admin` FOREIGN KEY (`adminId`) REFERENCES `admin_user` (`adminName`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_admin
-- ----------------------------
INSERT INTO `t_admin` VALUES ('admin', '管理员');

-- ----------------------------
-- Table structure for `t_depa`
-- ----------------------------
DROP TABLE IF EXISTS `t_depa`;
CREATE TABLE `t_depa` (
  `dId` int(5) NOT NULL AUTO_INCREMENT,
  `deId` varchar(10) NOT NULL,
  `depaName` varchar(20) NOT NULL,
  PRIMARY KEY (`dId`),
  UNIQUE KEY `SY_depa` (`deId`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_depa
-- ----------------------------
INSERT INTO `t_depa` VALUES ('1', '01', '旅游管理系');
INSERT INTO `t_depa` VALUES ('2', '02', '机械与汽车工程系');
INSERT INTO `t_depa` VALUES ('3', '03', '工商管理系');
INSERT INTO `t_depa` VALUES ('4', '04', '计算机科学与技术系');
INSERT INTO `t_depa` VALUES ('5', '05', '建筑系');
INSERT INTO `t_depa` VALUES ('6', '08', '行政管理系');

-- ----------------------------
-- Table structure for `t_grade`
-- ----------------------------
DROP TABLE IF EXISTS `t_grade`;
CREATE TABLE `t_grade` (
  `gId` int(5) NOT NULL AUTO_INCREMENT,
  `grId` varchar(11) DEFAULT '',
  `gradeName` varchar(20) DEFAULT NULL,
  `specId` varchar(10) DEFAULT NULL,
  `depaId` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`gId`),
  UNIQUE KEY `SY_grade` (`grId`) USING BTREE,
  KEY `FK_t_grade1` (`depaId`),
  KEY `FK_t_grade2` (`specId`),
  CONSTRAINT `FK_t_grade1` FOREIGN KEY (`depaId`) REFERENCES `t_depa` (`deId`) ON UPDATE CASCADE,
  CONSTRAINT `FK_t_grade2` FOREIGN KEY (`specId`) REFERENCES `t_spec` (`spId`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_grade
-- ----------------------------
INSERT INTO `t_grade` VALUES ('1', '020101', '汽服一班', '0201', '02');
INSERT INTO `t_grade` VALUES ('2', '030101', '会计学一班', '0301', '03');
INSERT INTO `t_grade` VALUES ('3', '030102', '会计学二班', '0301', '03');
INSERT INTO `t_grade` VALUES ('4', '030201', '工商管理一班', '0302', '03');
INSERT INTO `t_grade` VALUES ('5', '030202', '工商管理二班', '0302', '03');
INSERT INTO `t_grade` VALUES ('6', '040101', '网工一班', '0401', '04');
INSERT INTO `t_grade` VALUES ('7', '040201', '软工一班', '0402', '04');
INSERT INTO `t_grade` VALUES ('8', '040208', '软工八班', '0402', '04');
INSERT INTO `t_grade` VALUES ('9', '080202', '人力二班', '0802', '08');

-- ----------------------------
-- Table structure for `t_spec`
-- ----------------------------
DROP TABLE IF EXISTS `t_spec`;
CREATE TABLE `t_spec` (
  `sId` int(5) NOT NULL AUTO_INCREMENT,
  `spId` varchar(10) NOT NULL,
  `specName` varchar(20) NOT NULL,
  `depaId` varchar(10) NOT NULL,
  PRIMARY KEY (`sId`),
  UNIQUE KEY `SY_spec` (`spId`) USING BTREE,
  KEY `FK_t_depa` (`depaId`),
  CONSTRAINT `FK_t_depa` FOREIGN KEY (`depaId`) REFERENCES `t_depa` (`deId`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_spec
-- ----------------------------
INSERT INTO `t_spec` VALUES ('1', '0201', '汽车服务', '02');
INSERT INTO `t_spec` VALUES ('2', '0301', '会计学', '03');
INSERT INTO `t_spec` VALUES ('3', '0302', '工商管理', '03');
INSERT INTO `t_spec` VALUES ('4', '0303', '财务管理', '03');
INSERT INTO `t_spec` VALUES ('5', '0401', '网络工程', '04');
INSERT INTO `t_spec` VALUES ('6', '0402', '软件工程', '04');
INSERT INTO `t_spec` VALUES ('7', '0403', '计算机科学', '04');
INSERT INTO `t_spec` VALUES ('8', '0802', '人力资源管理', '08');

-- ----------------------------
-- Table structure for `t_student`
-- ----------------------------
DROP TABLE IF EXISTS `t_student`;
CREATE TABLE `t_student` (
  `stuId` int(11) NOT NULL AUTO_INCREMENT,
  `stuNo` varchar(20) DEFAULT NULL,
  `stuName` varchar(10) DEFAULT NULL,
  `sex` varchar(5) DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  `gradeId` varchar(10) DEFAULT NULL,
  `email` varchar(20) DEFAULT NULL,
  `inTime` varchar(10) DEFAULT NULL,
  `depaId` varchar(10) DEFAULT NULL,
  `specId` varchar(10) DEFAULT NULL,
  `stuDesc` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`stuId`),
  UNIQUE KEY `stuNo` (`stuNo`) USING BTREE,
  KEY `FK_t_student` (`gradeId`),
  KEY `FK_t_student2` (`specId`),
  KEY `FK_t_student3` (`depaId`),
  CONSTRAINT `FK_t_student1` FOREIGN KEY (`gradeId`) REFERENCES `t_grade` (`grId`) ON UPDATE CASCADE,
  CONSTRAINT `FK_t_student2` FOREIGN KEY (`specId`) REFERENCES `t_spec` (`spId`) ON UPDATE CASCADE,
  CONSTRAINT `FK_t_student3` FOREIGN KEY (`depaId`) REFERENCES `t_depa` (`deId`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_student
-- ----------------------------
INSERT INTO `t_student` VALUES ('2', '04140826', '余伟靖', '男', '1996-03-13', '040208', '31321@qq.com', '2014', '04', '0402', null);
INSERT INTO `t_student` VALUES ('3', '04140001', '张三1', '男', '1989-11-03', '040101', '31321@qq.com', '2014', '04', '0401', '什么都没有');
INSERT INTO `t_student` VALUES ('4', '04140002', '张三2', '男', '1989-11-03', '030102', '31321@qq.com', '2014', '03', '0301', '什么都没有');
INSERT INTO `t_student` VALUES ('9', '04140003', '张三3', '男', '1989-11-03', '040208', '31321@qq.com', '2013', '04', '0402', '什么都没有');
INSERT INTO `t_student` VALUES ('10', '04140004', '张三4', '男', '1989-11-03', '030102', '31321@qq.com', '2013', '03', '0301', '什么都没有');
INSERT INTO `t_student` VALUES ('11', '04140005', '张三5', '男', '1989-11-03', '040208', '31321@qq.com', '2013', '04', '0402', '什么都没有');
INSERT INTO `t_student` VALUES ('12', '04140006', '张三6', '男', '1989-11-03', '040201', '31321@qq.com', '2013', '04', '0402', '什么都没有');
INSERT INTO `t_student` VALUES ('13', '04140007', '张三7', '男', '1988-11-03', '030102', '31321@qq.com', '2013', '03', '0301', '什么都没有');
INSERT INTO `t_student` VALUES ('14', '04140008', '张三8', '男', '1989-11-03', '030102', '31321@qq.com', '2013', '03', '0301', '什么都没有');
INSERT INTO `t_student` VALUES ('22', '04140805', '霍健聪', '男', '1996-06-06', '040208', '1002938072@qq.com', '2014', '04', '0402', '这个学生很22222');
INSERT INTO `t_student` VALUES ('23', '04140009', '张三9', '女', '2016-10-04', '030101', 'ww@qq.com', '2016', '03', '0301', '');
INSERT INTO `t_student` VALUES ('24', '04140811', '林国滨', '男', '1995-12-22', '040208', 'ryuka-tenjouen@hotma', '2014', '04', '0402', '.................');
