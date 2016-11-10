# Host: 192.168.1.129  (Version: 5.1.68-community)
# Date: 2016-01-12 17:31:41
# Generator: MySQL-Front 5.3  (Build 4.271)

/*!40101 SET NAMES utf8 */;

#
# Structure for table "common"
#

DROP TABLE IF EXISTS `common`;
CREATE TABLE `common` (
  `Id` bigint(11) NOT NULL AUTO_INCREMENT,
  `Create_Date` datetime DEFAULT NULL COMMENT '创建时间',
  `Modify_Date` datetime DEFAULT NULL COMMENT '修改时间',
  `Modify_By` bigint(20) DEFAULT NULL COMMENT '修改人员ID',
  `Create_By` bigint(20) DEFAULT NULL COMMENT '创建人员ID',
  `Remarks` int(11) DEFAULT NULL COMMENT '备用(数字型)',
  `Remarks1` Varchar(255) DEFAULT NULL COMMENT '备用1',
  `Remarks2` Varchar(255) DEFAULT NULL COMMENT '备用2',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Data for table "common"
#

