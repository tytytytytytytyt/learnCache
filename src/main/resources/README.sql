#hello 库
CREATE TABLE `sys_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '头像',
  `account` varchar(45) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '账号',
  `password` varchar(45) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '密码',
  `salt` varchar(45) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'md5密码盐',
  `name` varchar(45) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '名字',
  `birthday` datetime DEFAULT NULL COMMENT '生日',
  `sex` int(11) DEFAULT NULL COMMENT '性别（1：男 2：女）',
  `email` varchar(45) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '电子邮件',
  `phone` varchar(45) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '电话',
  `roleid` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '角色id',
  `deptid` int(11) DEFAULT NULL COMMENT '部门id',
  `status` int(11) DEFAULT NULL COMMENT '状态(1：启用  2：冻结  3：删除）',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `updatetime` datetime DEFAULT NULL COMMENT '修改事件',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=696 DEFAULT CHARSET=utf8 COMMENT='系统用户表';


#world库
CREATE TABLE `city` (
                        `ID` int(11) NOT NULL AUTO_INCREMENT,
                        `Name` char(35) NOT NULL DEFAULT '',
                        `CountryCode` char(3) NOT NULL DEFAULT '',
                        `District` char(20) NOT NULL DEFAULT '',
                        `Population` int(11) NOT NULL DEFAULT '0',
                        PRIMARY KEY (`ID`)
) ENGINE=MyISAM AUTO_INCREMENT=4080 DEFAULT CHARSET=latin1;


CREATE TABLE `country` (
                           `Code` char(3) NOT NULL DEFAULT '',
                           `Name` char(52) NOT NULL DEFAULT '',
                           `Continent` enum('Asia','Europe','North America','Africa','Oceania','Antarctica','South America') NOT NULL DEFAULT 'Asia',
                           `Region` char(26) NOT NULL DEFAULT '',
                           `SurfaceArea` float(10,2) NOT NULL DEFAULT '0.00',
                           `IndepYear` smallint(6) DEFAULT NULL,
                           `Population` int(11) NOT NULL DEFAULT '0',
                           `LifeExpectancy` float(3,1) DEFAULT NULL,
                           `GNP` float(10,2) DEFAULT NULL,
                           `GNPOld` float(10,2) DEFAULT NULL,
                           `LocalName` char(45) NOT NULL DEFAULT '',
                           `GovernmentForm` char(45) NOT NULL DEFAULT '',
                           `HeadOfState` char(60) DEFAULT NULL,
                           `Capital` int(11) DEFAULT NULL,
                           `Code2` char(2) NOT NULL DEFAULT '',
                           PRIMARY KEY (`Code`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;


CREATE TABLE `countrylanguage` (
                                   `CountryCode` char(3) NOT NULL DEFAULT '',
                                   `Language` char(30) NOT NULL DEFAULT '',
                                   `IsOfficial` enum('T','F') NOT NULL DEFAULT 'F',
                                   `Percentage` float(4,1) NOT NULL DEFAULT '0.0',
                                   PRIMARY KEY (`CountryCode`,`Language`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;


CREATE TABLE `t_cuser_info` (
                                `id` int(20) NOT NULL COMMENT '主键ID',
                                `cuser_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '客户用户名称',
                                `type` tinyint(2) NOT NULL DEFAULT '0' COMMENT '客户用户权限',
                                `cuser_is_frozen` tinyint(2) NOT NULL DEFAULT '0' COMMENT '客户用户是否冻结',
                                `create_time` datetime NOT NULL COMMENT '创建时间',
                                `create_user` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '创建人',
                                `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
                                `modify_user` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '修改人',
                                `is_delete` tinyint(4) NOT NULL DEFAULT '0' COMMENT '逻辑删除标识（1：删除，0：存在）',
                                `cuser_password` varchar(45) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '客户用户密码',
                                `cuser_account` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '客户用户账号',
                                `cuser_email` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '客户用户邮箱',
                                `cuser_phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '客户用户联系方式',
                                `cuser_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '客户用户地址',
                                `cuser_lp` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '客户用户联系人',
                                `customer_id` int(20) NOT NULL COMMENT '客户Id',
                                `salt` varchar(45) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '加盐',
                                `encrypt_type` tinyint(2) DEFAULT NULL COMMENT '加密类型',
                                `encrypt_key` varchar(16) DEFAULT NULL COMMENT '加密key',
                                `hash_val` varchar(250) DEFAULT NULL COMMENT 'hash值',
                                PRIMARY KEY (`id`) USING BTREE,
                                UNIQUE KEY `cuser_account` (`cuser_account`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='客户用户表';
