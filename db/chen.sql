/*

*/
SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for SYS_API
-- ----------------------------
DROP TABLE IF EXISTS `SYS_API`;
CREATE TABLE `SYS_API` (
  `ID` varchar(64) NOT NULL COMMENT '主键ID',
  `NAME` varchar(50) NOT NULL COMMENT 'api名称',
  `URL` varchar(255) NOT NULL COMMENT 'API路径',
  `HTTP_METHOD` varchar(10) NOT NULL DEFAULT 'GET' COMMENT 'HTTP请求方法(GET;HEAD;POST;PUT;PATCH;DELETE;OPTIONS;TRACE;ANY;)',
  `TYPE` varchar(10) NOT NULL COMMENT '系统API类型(SYSTEM:系统api;)',
  `REMARK` varchar(255) NOT NULL DEFAULT '' COMMENT '备注',
  `STATUS` varchar(10) NOT NULL DEFAULT 'ENABLED' COMMENT '状态(ENABLED:启用;DISABLE:禁用;)',
  `UPDATE_SYS_USER_ID` varchar(64) NOT NULL DEFAULT '' COMMENT '更新系统用户id(''为初始化创建)',
  `CREATE_SYS_USER_ID` varchar(64) NOT NULL COMMENT '创建系统用户id(''为初始化创建)',
  `UPDATE_DATE_TIME` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `CREATE_DATE_TIME` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统api表';

-- ----------------------------
-- Table structure for SYS_DICT
-- ----------------------------
DROP TABLE IF EXISTS `SYS_DICT`;
CREATE TABLE `SYS_DICT` (
  `CODE` varchar(100) NOT NULL COMMENT '字典标识',
  `KEY` varchar(100) NOT NULL COMMENT '字典key',
  `NAME` varchar(50) NOT NULL COMMENT '字典名称',
  `VALUE` varchar(30) NOT NULL COMMENT '字典值',
  `REMARK` varchar(255) NOT NULL DEFAULT '' COMMENT '备注',
  `COLOR` varchar(10) NOT NULL DEFAULT '' COMMENT '颜色值(冗余字段用户前端展示,需要保证前端可用(success,processing,default,error,warning)(geekblue,blue,purple,success,red,volcano,orange,gold,lime,green,cyan)(#f50,#ff0)预设和色值',
  `TYPE` varchar(10) NOT NULL DEFAULT 'STRING' COMMENT '字典值类型(STRING:字符串;BYTE:数字byte;SHORT:数字short;INTEGER:数字integer;LONG:数字long;FLOAT:小数float;DOUBLE:小数double;BOOLEAN:布尔)',
  `STATUS` varchar(10) NOT NULL DEFAULT 'ENABLED' COMMENT '状态(ENABLED:启用;DISABLE:禁用;)',
  `UPDATE_SYS_USER_ID` varchar(64) NOT NULL DEFAULT '' COMMENT '更新系统用户id(''为初始化创建)',
  `CREATE_SYS_USER_ID` varchar(64) NOT NULL COMMENT '创建系统用户id(''为初始化创建)',
  `UPDATE_DATE_TIME` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `CREATE_DATE_TIME` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`CODE`,`KEY`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统字典表';

-- ----------------------------
-- Table structure for SYS_MENU
-- ----------------------------
DROP TABLE IF EXISTS `SYS_MENU`;
CREATE TABLE `SYS_MENU` (
  `ID` varchar(64) NOT NULL COMMENT '主键ID',
  `PARENT_ID` varchar(64) NOT NULL DEFAULT '' COMMENT '父级的id(引用本表id字段,空字符为顶级菜单)',
  `NAME` varchar(32) NOT NULL COMMENT '菜单名称',
  `URL` varchar(255) NOT NULL COMMENT '菜单url',
  `ICON` varchar(20) NOT NULL DEFAULT '' COMMENT '图标',
  `TYPE` varchar(10) NOT NULL COMMENT '类型(GROUP:组;MENU:菜单;LINK:链接;EXTERNAL_LINK:外部链接;)',
  `REMARK` varchar(255) NOT NULL DEFAULT '' COMMENT '备注',
  `ORDER` int(11) NOT NULL DEFAULT '1' COMMENT '显示顺序(从1开始)',
  `STATUS` varchar(10) NOT NULL DEFAULT 'ENABLED' COMMENT '状态(ENABLED:启用;DISABLE:禁用;)',
  `UPDATE_SYS_USER_ID` varchar(64) NOT NULL DEFAULT '' COMMENT '更新系统用户id(''为初始化创建)',
  `CREATE_SYS_USER_ID` varchar(64) NOT NULL COMMENT '创建系统用户id(''为初始化创建)',
  `UPDATE_DATE_TIME` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `CREATE_DATE_TIME` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统菜单表';

-- ----------------------------
-- Table structure for SYS_PARAMETER
-- ----------------------------
DROP TABLE IF EXISTS `SYS_PARAMETER`;
CREATE TABLE `SYS_PARAMETER` (
  `CODE` varchar(100) NOT NULL COMMENT '参数标识',
  `NAME` varchar(100) NOT NULL COMMENT '参数名称',
  `VALUE` varchar(1024) NOT NULL COMMENT '参数值',
  `TYPE` varchar(10) NOT NULL DEFAULT 'SYSTEM' COMMENT '参数类型(SYSTEM:系统关键参数)',
  `REMARK` varchar(255) NOT NULL DEFAULT '' COMMENT '备注',
  `STATUS` varchar(10) NOT NULL DEFAULT 'ENABLED' COMMENT '状态(ENABLED:启用;DISABLE:禁用;)',
  `UPDATE_SYS_USER_ID` varchar(64) NOT NULL DEFAULT '' COMMENT '更新系统用户id(''为初始化创建)',
  `CREATE_SYS_USER_ID` varchar(64) NOT NULL COMMENT '创建系统用户id(''为初始化创建)',
  `UPDATE_DATE_TIME` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `CREATE_DATE_TIME` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='系统参数表';

-- ----------------------------
-- Table structure for SYS_PERMISSION
-- ----------------------------
DROP TABLE IF EXISTS `SYS_PERMISSION`;
CREATE TABLE `SYS_PERMISSION` (
  `ID` varchar(64) NOT NULL COMMENT 'id',
  `NAME` varchar(30) NOT NULL COMMENT '权限名称',
  `REMARK` varchar(255) NOT NULL DEFAULT '' COMMENT '备注',
  `STATUS` varchar(10) NOT NULL DEFAULT 'ENABLED' COMMENT '状态(ENABLED:启用;DISABLE:禁用;)',
  `UPDATE_SYS_USER_ID` varchar(64) NOT NULL DEFAULT '' COMMENT '更新系统用户id(''为初始化创建)',
  `CREATE_SYS_USER_ID` varchar(64) NOT NULL COMMENT '创建系统用户id(''为初始化创建)',
  `UPDATE_DATE_TIME` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `CREATE_DATE_TIME` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统权限表';

-- ----------------------------
-- Table structure for SYS_PERMISSION_API
-- ----------------------------
DROP TABLE IF EXISTS `SYS_PERMISSION_API`;
CREATE TABLE `SYS_PERMISSION_API` (
  `SYS_PERMISSION_ID` varchar(64) NOT NULL COMMENT '系统权限id',
  `SYS_API_ID` varchar(64) NOT NULL COMMENT '系统APIid',
  `CREATE_SYS_USER_ID` varchar(64) NOT NULL COMMENT '创建系统用户id(''为初始化创建)',
  `CREATE_DATE_TIME` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`SYS_PERMISSION_ID`,`SYS_API_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统权限API表';

-- ----------------------------
-- Table structure for SYS_ROLE
-- ----------------------------
DROP TABLE IF EXISTS `SYS_ROLE`;
CREATE TABLE `SYS_ROLE` (
  `ID` varchar(64) NOT NULL COMMENT 'id',
  `NAME` varchar(30) NOT NULL COMMENT '角色名称',
  `REMARK` varchar(255) NOT NULL DEFAULT '' COMMENT '备注',
  `STATUS` varchar(10) NOT NULL DEFAULT 'ENABLED' COMMENT '状态(ENABLED:启用;DISABLE:禁用;)',
  `UPDATE_SYS_USER_ID` varchar(64) NOT NULL DEFAULT '' COMMENT '更新系统用户id(''为初始化创建)',
  `CREATE_SYS_USER_ID` varchar(64) NOT NULL COMMENT '创建系统用户id(''为初始化创建)',
  `UPDATE_DATE_TIME` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `CREATE_DATE_TIME` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统角色表';

-- ----------------------------
-- Table structure for SYS_ROLE_API
-- ----------------------------
DROP TABLE IF EXISTS `SYS_ROLE_API`;
CREATE TABLE `SYS_ROLE_API` (
  `SYS_ROLE_ID` varchar(64) NOT NULL COMMENT '系统角色id',
  `SYS_API_ID` varchar(64) NOT NULL COMMENT '系统APIid',
  `CREATE_SYS_USER_ID` varchar(64) NOT NULL COMMENT '创建系统用户id(''为初始化创建)',
  `CREATE_DATE_TIME` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`SYS_ROLE_ID`,`SYS_API_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统角色API表';

-- ----------------------------
-- Table structure for SYS_ROLE_MENU
-- ----------------------------
DROP TABLE IF EXISTS `SYS_ROLE_MENU`;
CREATE TABLE `SYS_ROLE_MENU` (
  `SYS_ROLE_ID` varchar(64) NOT NULL COMMENT '系统角色id',
  `SYS_MENU_ID` varchar(64) NOT NULL COMMENT '系统菜单id',
  `CREATE_SYS_USER_ID` varchar(64) NOT NULL COMMENT '创建系统用户id(''为初始化创建)',
  `CREATE_DATE_TIME` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`SYS_ROLE_ID`,`SYS_MENU_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统角色菜单表';

-- ----------------------------
-- Table structure for SYS_ROLE_PERMISSION
-- ----------------------------
DROP TABLE IF EXISTS `SYS_ROLE_PERMISSION`;
CREATE TABLE `SYS_ROLE_PERMISSION` (
  `SYS_ROLE_ID` varchar(64) NOT NULL COMMENT '系统角色id',
  `SYS_PERMISSION_ID` varchar(64) NOT NULL COMMENT '系统权限id',
  `CREATE_SYS_USER_ID` varchar(64) NOT NULL COMMENT '创建系统用户id(''为初始化创建)',
  `CREATE_DATE_TIME` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`SYS_ROLE_ID`,`SYS_PERMISSION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统角色权限表';

-- ----------------------------
-- Table structure for SYS_TIMED_TASK
-- ----------------------------
DROP TABLE IF EXISTS `SYS_TIMED_TASK`;
CREATE TABLE `SYS_TIMED_TASK` (
  `CODE` varchar(100) NOT NULL COMMENT '任务标识',
  `NAME` varchar(100) NOT NULL COMMENT '任务名称',
  `CLASS_NAME` varchar(255) NOT NULL COMMENT '类名',
  `CRON_EXPRESSION` varchar(30) NOT NULL COMMENT 'corn表达式',
  `DATA` varchar(255) NOT NULL DEFAULT '' COMMENT '任务数据(json格式)',
  `TYPE` varchar(10) NOT NULL DEFAULT 'SYSTEM' COMMENT '定时任务类型(SYSTEM:系统定时任务)',
  `REMARK` varchar(255) NOT NULL DEFAULT '' COMMENT '备注',
  `STATUS` varchar(10) NOT NULL DEFAULT 'ENABLED' COMMENT '状态(ENABLED:启用;DISABLE:禁用;)',
  `UPDATE_SYS_USER_ID` varchar(64) NOT NULL DEFAULT '' COMMENT '更新系统用户id(''为初始化创建)',
  `CREATE_SYS_USER_ID` varchar(64) NOT NULL COMMENT '创建系统用户id(''为初始化创建)',
  `UPDATE_DATE_TIME` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `CREATE_DATE_TIME` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统定时任务表';

-- ----------------------------
-- Table structure for SYS_TIMED_TASK_LOG
-- ----------------------------
DROP TABLE IF EXISTS `SYS_TIMED_TASK_LOG`;
CREATE TABLE `SYS_TIMED_TASK_LOG` (
  `ID` varchar(64) NOT NULL COMMENT 'ID',
  `CODE` varchar(100) NOT NULL COMMENT '任务标识',
  `NAME` varchar(100) NOT NULL COMMENT '任务名称',
  `CRON_EXPRESSION` varchar(30) NOT NULL COMMENT 'corn表达式',
  `TYPE` varchar(10) NOT NULL DEFAULT 'SYSTEM' COMMENT '定时任务类型(SYSTEM:系统定时任务)',
  `EXECUTION_STATUS` varchar(10) NOT NULL COMMENT '执行状态(NON:未执行;EXECUTING:执行中;SUCCESS:执行成功;FAILURE:执行失败;EXCEPTION:执行异常;REJECTION;执行拒绝;)',
  `EXCEPTION_MESSAGE` varchar(256) NOT NULL DEFAULT '' COMMENT '异常信息',
  `EXECUTION_START_DATE_TIME` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '执行开始时间',
  `EXECUTION_END_DATE_TIME` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '执行结束时间',
  `EXECUTION_TIME` bigint(20) NOT NULL COMMENT '执行用时(毫秒)',
  `REMARK` varchar(255) NOT NULL DEFAULT '' COMMENT '备注',
  `UPDATE_DATE_TIME` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `CREATE_DATE_TIME` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统定时任务日志表';

-- ----------------------------
-- Table structure for SYS_USER
-- ----------------------------
DROP TABLE IF EXISTS `SYS_USER`;
CREATE TABLE `SYS_USER` (
  `ID` varchar(64) NOT NULL COMMENT 'id',
  `USERNAME` varchar(30) NOT NULL COMMENT '用户名',
  `PASSWORD` varchar(64) NOT NULL COMMENT '密码',
  `REMARK` varchar(255) NOT NULL DEFAULT '' COMMENT '备注',
  `STATUS` varchar(10) NOT NULL DEFAULT 'ENABLED' COMMENT '用户状态(ENABLED:启用;LOCKED:锁定;EXPIRED:过期;DISABLE:禁用;)',
  `LEVEL` int(11) unsigned NOT NULL DEFAULT '1' COMMENT '级别(0:超级用户;1.数值越小级别越大,2.低级别用户不能修改比自己高级别的用户,3.新创建用户的级别默认比创建用户低一级别,4.高级别用户最高可以修改低级别用户为和自己同一级别)',
  `DELETED` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否删除(1:已删除;0未删除;)',
  `LAST_LOGIN_DATE_TIME` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '最后登录时间',
  `UPDATE_SYS_USER_ID` varchar(64) NOT NULL DEFAULT '' COMMENT '更新系统用户id(''为初始化创建)',
  `CREATE_SYS_USER_ID` varchar(64) NOT NULL COMMENT '创建系统用户id(''为初始化创建)',
  `UPDATE_DATE_TIME` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `CREATE_DATE_TIME` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `INDEX_USERNAME` (`USERNAME`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统用户表';

-- ----------------------------
-- Table structure for SYS_USER_ROLE
-- ----------------------------
DROP TABLE IF EXISTS `SYS_USER_ROLE`;
CREATE TABLE `SYS_USER_ROLE` (
  `SYS_USER_ID` varchar(64) NOT NULL COMMENT '系统用户id',
  `SYS_ROLE_ID` varchar(64) NOT NULL COMMENT '系统角色id',
  `CREATE_SYS_USER_ID` varchar(64) NOT NULL COMMENT '创建系统用户id(''为初始化创建)',
  `CREATE_DATE_TIME` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`SYS_USER_ID`,`SYS_ROLE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统用户角色表';
