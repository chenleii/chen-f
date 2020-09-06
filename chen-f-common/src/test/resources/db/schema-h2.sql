-- ----------------------------
-- Table structure for country
-- ----------------------------
DROP TABLE IF EXISTS `country`;
CREATE TABLE `country`
(
    `ID`           varchar(64) NOT NULL COMMENT '国家ID',
    `LETTER_CODE2` varchar(2)  NOT NULL COMMENT '国家二位字母编码',
    `LETTER_CODE3` varchar(3)  NOT NULL COMMENT '国家三位字母编码',
    `NAME`         varchar(80) NOT NULL COMMENT '国家英文名称',
    `CHINESE_NAME` varchar(80) NOT NULL COMMENT '国家中文名称',
    `NUMERIC_CODE` varchar(3)  NOT NULL COMMENT '国家三位数字编码',
    `AREA_CODE`    varchar(5)  NOT NULL COMMENT '国家国际电话区号',
    PRIMARY KEY (`ID`)
);

-- ----------------------------
-- Table structure for region
-- ----------------------------
DROP TABLE IF EXISTS `region`;
CREATE TABLE `region`
(
    `ID`           varchar(64)      NOT NULL COMMENT '地区ID',
    `PARENT_ID`    varchar(64)      NOT NULL COMMENT '父级的地区ID(引用本表ID字段,空白字符串为顶级)',
    `LEVEL`        int(11) unsigned NOT NULL COMMENT '地区等级(0:国家;1:省;2:市;3:区/县;)',
    `NAME`         varchar(30)      NOT NULL COMMENT '地区名称',
    `CITY_CODE`    varchar(10)      NOT NULL DEFAULT '' COMMENT '地区城市编码',
    `POSTAL_CODE`  varchar(10)      NOT NULL DEFAULT '' COMMENT '地区邮政编码',
    `SHORT_NAME`   varchar(20)      NOT NULL COMMENT '地区简称',
    `FULL_NAME`    varchar(100)     NOT NULL COMMENT '地区全称',
    `ENGLISH_NAME` varchar(30)      NOT NULL COMMENT '地区英文名称',
    PRIMARY KEY (`ID`)
);
CREATE INDEX `IDX_PARENT_ID` ON `region`(`PARENT_ID`);

-- ----------------------------
-- Table structure for region_id_card
-- ----------------------------
DROP TABLE IF EXISTS `region_id_card`;
CREATE TABLE `region_id_card`
(
    `ID`            varchar(64) NOT NULL COMMENT '地区身份证ID',
    `PARENT_ID`     varchar(64) NOT NULL COMMENT '父级的地区身份证ID(引用本表ID字段,空白字符串为顶级)',
    `PREFIX`        varchar(6)  NOT NULL COMMENT '地区身份证号前缀',
    `PARENT_PREFIX` varchar(6)  NOT NULL COMMENT '父级的地区身份证号前缀(引用本表PREFIX字段,空白字符串为顶级)',
    `PROVINCE`      varchar(2)  NOT NULL COMMENT '地区身份证省前缀',
    `CITY`          varchar(4)  NOT NULL COMMENT '地区身份证城市前缀',
    `NAME`          varchar(30) NOT NULL COMMENT '地区身份证地区名称',
    `FULL_NAME`     varchar(90) NOT NULL COMMENT '地区身份证地区全称',
    PRIMARY KEY (`ID`)
);
CREATE UNIQUE INDEX `UK_PREFIX` ON `region_id_card`(`PREFIX`);
CREATE INDEX `UK_PARENT_ID` ON `region_id_card`(`PARENT_ID`);
CREATE INDEX `IDX_PARENT_PREFIX` ON `region_id_card`(`PARENT_PREFIX`);

-- ----------------------------
-- Table structure for sys_api
-- ----------------------------
DROP TABLE IF EXISTS `sys_api`;
CREATE TABLE `sys_api`
(
    `ID`                  varchar(64)  NOT NULL COMMENT '系统接口ID',
    `NAME`                varchar(50)  NOT NULL COMMENT '系统接口名称',
    `URL`                 varchar(255) NOT NULL COMMENT '系统接口URL',
    `HTTP_METHOD`         varchar(16)  NOT NULL DEFAULT 'GET' COMMENT '系统接口HTTP请求方法(GET:GET请求;HEAD:HEAD请求;POST:POST请求;PUT:PUT请求;PATCH:PATCH请求;DELETE:DELETE请求;OPTIONS:OPTIONS请求;TRACE:TRACE请求;ANY:任意的请求;)',
    `TYPE`                varchar(16)  NOT NULL COMMENT '系统接口类型(SYSTEM:系统接口;LOGIN:登陆接口;LOGOUT:登出接口;)',
    `REMARK`              varchar(255) NOT NULL DEFAULT '' COMMENT '系统接口备注',
    `STATUS`              varchar(16)  NOT NULL DEFAULT 'ENABLED' COMMENT '系统接口状态(ENABLED:启用;DISABLED:禁用;)',
    `UPDATED_SYS_USER_ID` varchar(64)  NOT NULL COMMENT '更新的系统用户ID(空白字符串为初始化创建)',
    `CREATED_SYS_USER_ID` varchar(64)  NOT NULL COMMENT '创建的系统用户ID(空白字符串为初始化创建)',
    `UPDATED_DATE_TIME`   datetime     NOT NULL COMMENT '更新的日期时间',
    `CREATED_DATE_TIME`   datetime     NOT NULL COMMENT '创建的日期时间',
    PRIMARY KEY (`ID`)
);

-- ----------------------------
-- Table structure for sys_dictionary
-- ----------------------------
DROP TABLE IF EXISTS `sys_dictionary`;
CREATE TABLE `sys_dictionary`
(
    `ID`                  varchar(64)  NOT NULL COMMENT '系统字典ID',
    `CODE`                varchar(100) NOT NULL COMMENT '系统字典编码',
    `NAME`                varchar(50)  NOT NULL COMMENT '系统字典名称',
    `TYPE`                varchar(16)  NOT NULL DEFAULT 'DB_TABLE_COLUMN' COMMENT '系统字典类型(DB_TABLE_COLUMN:数据库表字段;OTHER:其他;)',
    `REMARK`              varchar(255) NOT NULL DEFAULT '' COMMENT '系统字典备注',
    `STATUS`              varchar(16)  NOT NULL DEFAULT 'ENABLED' COMMENT '系统字典状态(ENABLED:启用;DISABLED:禁用;)',
    `UPDATED_SYS_USER_ID` varchar(64)  NOT NULL COMMENT '更新的系统用户ID(空白字符串为初始化创建)',
    `CREATED_SYS_USER_ID` varchar(64)  NOT NULL COMMENT '创建的系统用户ID(空白字符串为初始化创建)',
    `UPDATED_DATE_TIME`   datetime     NOT NULL COMMENT '更新的日期时间',
    `CREATED_DATE_TIME`   datetime     NOT NULL COMMENT '创建的日期时间',
    PRIMARY KEY (`ID`)
);
CREATE UNIQUE INDEX `sys_dictionary_UK_CODE` ON `sys_dictionary`(`CODE`);

-- ----------------------------
-- Table structure for sys_dictionary_item
-- ----------------------------
DROP TABLE IF EXISTS `sys_dictionary_item`;
CREATE TABLE `sys_dictionary_item`
(
    `ID`                  varchar(64)      NOT NULL COMMENT '系统字典项目ID',
    `SYS_DICTIONARY_ID`   varchar(64)      NOT NULL COMMENT '系统字典ID',
    `CODE`                varchar(100)     NOT NULL COMMENT '系统字典编码(冗余)',
    `NAME`                varchar(50)      NOT NULL COMMENT '系统字典名称(冗余)',
    `KEY`                 varchar(100)     NOT NULL COMMENT '系统字典项目KEY',
    `VALUE`               varchar(30)      NOT NULL COMMENT '系统字典项目值',
    `VALUE_I18N`          varchar(100)     NOT NULL DEFAULT '' COMMENT '系统字典项目值的国际化',
    `KEY_TYPE`            varchar(16)      NOT NULL DEFAULT 'STRING' COMMENT '系统字典项目KEY类型(STRING:字符串;BYTE:整数BYTE;SHORT:整数SHORT;INTEGER:整数INTEGER;LONG:整数LONG;BIG_INTEGER:大的整数;FLOAT:小数FLOAT;DOUBLE:小数DOUBLE;BIG_DECIMAL:大的小数;BOOLEAN:布尔;)',
    `VALUE_TYPE`          varchar(16)      NOT NULL DEFAULT 'STRING' COMMENT '系统字典项目值类型(STRING:字符串;BYTE:整数BYTE;SHORT:整数SHORT;INTEGER:整数INTEGER;LONG:整数LONG;BIG_INTEGER:大的整数;FLOAT:小数FLOAT;DOUBLE:小数DOUBLE;BIG_DECIMAL:大的小数;BOOLEAN:布尔;)',
    `COLOR`               varchar(20)      NOT NULL DEFAULT '' COMMENT '系统字典项目颜色值(用于展示,例如#ffffff)',
    `ORDER`               int(11) unsigned NOT NULL COMMENT '系统字典项目显示顺序',
    `REMARK`              varchar(255)     NOT NULL DEFAULT '' COMMENT '系统字典项目备注',
    `STATUS`              varchar(16)      NOT NULL DEFAULT 'ENABLED' COMMENT '系统字典项目状态(ENABLED:启用;DISABLED:禁用;)',
    `UPDATED_SYS_USER_ID` varchar(64)      NOT NULL COMMENT '更新的系统用户ID(空白字符串为初始化创建)',
    `CREATED_SYS_USER_ID` varchar(64)      NOT NULL COMMENT '创建的系统用户ID(空白字符串为初始化创建)',
    `UPDATED_DATE_TIME`   datetime         NOT NULL COMMENT '更新的日期时间',
    `CREATED_DATE_TIME`   datetime         NOT NULL COMMENT '创建的日期时间',
    PRIMARY KEY (`ID`)
);
CREATE UNIQUE INDEX `sys_dictionary_item_UK_CODE_KEY` ON `sys_dictionary_item`(`CODE`, `KEY`);
CREATE INDEX `IDX_SYS_DICTIONARY_ID` ON `sys_dictionary_item`(`SYS_DICTIONARY_ID`);

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`
(
    `ID`                  varchar(64)      NOT NULL COMMENT '系统菜单ID',
    `PARENT_ID`           varchar(64)      NOT NULL DEFAULT '' COMMENT '父级的系统菜单ID(引用本表ID字段,空白字符串为顶级)',
    `NAME`                varchar(32)      NOT NULL COMMENT '系统菜单名称',
    `NAME_I18N`           varchar(100)     NOT NULL DEFAULT '' COMMENT '系统字典名称的国际化',
    `URL`                 varchar(255)     NOT NULL COMMENT '系统菜单URL',
    `ICON`                varchar(30)      NOT NULL DEFAULT '' COMMENT '系统菜单图标',
    `TYPE`                varchar(16)      NOT NULL COMMENT '系统菜单类型(GROUP:组;MENU:菜单;LINK:链接;EXTERNAL_LINK:外部链接;)',
    `ORDER`               int(11) unsigned NOT NULL DEFAULT '1' COMMENT '系统菜单显示顺序',
    `REMARK`              varchar(255)     NOT NULL DEFAULT '' COMMENT '系统菜单备注',
    `STATUS`              varchar(16)      NOT NULL DEFAULT 'ENABLED' COMMENT '系统菜单状态(ENABLED:启用;DISABLED:禁用;)',
    `UPDATED_SYS_USER_ID` varchar(64)      NOT NULL COMMENT '更新的系统用户ID(空白字符串为初始化创建)',
    `CREATED_SYS_USER_ID` varchar(64)      NOT NULL COMMENT '创建的系统用户ID(空白字符串为初始化创建)',
    `UPDATED_DATE_TIME`   datetime         NOT NULL COMMENT '更新的日期时间',
    `CREATED_DATE_TIME`   datetime         NOT NULL COMMENT '创建的日期时间',
    PRIMARY KEY (`ID`)
);
CREATE INDEX `sys_menu_IDX_PARENT_ID` ON `sys_menu`(`PARENT_ID`);

-- ----------------------------
-- Table structure for sys_organization
-- ----------------------------
DROP TABLE IF EXISTS `sys_organization`;
CREATE TABLE `sys_organization`
(
    `ID`                  varchar(64)  NOT NULL COMMENT '系统组织ID',
    `PARENT_ID`           varchar(64)  NOT NULL COMMENT '父级的系统组织ID(引用本表ID字段,空白字符串为顶级)',
    `NAME`                varchar(50)  NOT NULL COMMENT '系统组织名称',
    `FULL_NAME`           varchar(100) NOT NULL COMMENT '系统组织全称',
    `TYPE`                varchar(16)  NOT NULL COMMENT '系统组织类型(ORDINARY:普通组织;COMPANY:公司;DEPARTMENT:部门;)',
    `REMARK`              varchar(255) NOT NULL DEFAULT '' COMMENT '系统组织备注',
    `STATUS`              varchar(16)  NOT NULL COMMENT '系统组织状态(ENABLED:启用;DISABLED:禁用;)',
    `UPDATED_SYS_USER_ID` varchar(64)  NOT NULL COMMENT '更新的系统用户ID(空白字符串为初始化创建)',
    `CREATED_SYS_USER_ID` varchar(64)  NOT NULL COMMENT '创建的系统用户ID(空白字符串为初始化创建)',
    `UPDATED_DATE_TIME`   datetime     NOT NULL COMMENT '更新的日期时间',
    `CREATED_DATE_TIME`   datetime     NOT NULL COMMENT '创建的日期时间',
    PRIMARY KEY (`ID`)
);
CREATE INDEX `sys_organization_IDX_PARENT_ID` ON `sys_organization`(`PARENT_ID`);

-- ----------------------------
-- Table structure for sys_organization_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_organization_role`;
CREATE TABLE `sys_organization_role`
(
    `ID`                  varchar(64) NOT NULL COMMENT '系统组织角色ID',
    `SYS_ORGANIZATION_ID` varchar(64) NOT NULL COMMENT '系统组织ID',
    `SYS_ROLE_ID`         varchar(64) NOT NULL COMMENT '系统角色ID',
    `CREATED_SYS_USER_ID` varchar(64) NOT NULL COMMENT '创建的系统用户ID(空白字符串为初始化创建)',
    `CREATED_DATE_TIME`   datetime    NOT NULL COMMENT '创建的日期时间',
    PRIMARY KEY (`ID`)
);
CREATE UNIQUE INDEX `UK_SYS_ORGANIZATION_ID_SYS_ROLE_ID` ON `sys_organization_role`(`SYS_ORGANIZATION_ID`, `SYS_ROLE_ID`);

-- ----------------------------
-- Table structure for sys_organization_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_organization_user`;
CREATE TABLE `sys_organization_user`
(
    `ID`                  varchar(64) NOT NULL COMMENT '系统组织用户ID',
    `SYS_ORGANIZATION_ID` varchar(64) NOT NULL COMMENT '系统组织ID',
    `SYS_USER_ID`         varchar(64) NOT NULL COMMENT '系统用户ID',
    `CREATED_SYS_USER_ID` varchar(64) NOT NULL COMMENT '创建的系统用户ID(空白字符串为初始化创建)',
    `CREATED_DATE_TIME`   datetime    NOT NULL COMMENT '创建的日期时间',
    PRIMARY KEY (`ID`)
);
CREATE UNIQUE INDEX `UK_SYS_ORGANIZATION_ID_SYS_USER_ID` ON `sys_organization_user`(`SYS_ORGANIZATION_ID`, `SYS_USER_ID`);

-- ----------------------------
-- Table structure for sys_parameter
-- ----------------------------
DROP TABLE IF EXISTS `sys_parameter`;
CREATE TABLE `sys_parameter`
(
    `ID`                  varchar(64)   NOT NULL COMMENT '系统参数ID',
    `CODE`                varchar(100)  NOT NULL COMMENT '系统参数编码',
    `NAME`                varchar(100)  NOT NULL COMMENT '系统参数名称',
    `VALUE`               varchar(1024) NOT NULL COMMENT '系统参数值',
    `VALUE_TYPE`          varchar(16)   NOT NULL DEFAULT 'STRING' COMMENT '系统参数值类型(STRING:字符串;BYTE:整数BYTE;SHORT:整数SHORT;INTEGER:整数INTEGER;LONG:整数LONG;BIG_INTEGER:大的整数;FLOAT:小数FLOAT;DOUBLE:小数DOUBLE;BIG_DECIMAL:大的小数;BOOLEAN:布尔;)',
    `TYPE`                varchar(16)   NOT NULL DEFAULT 'SYSTEM' COMMENT '系统参数类型(SYSTEM:系统参数;)',
    `REMARK`              varchar(255)  NOT NULL DEFAULT '' COMMENT '系统参数备注',
    `STATUS`              varchar(16)   NOT NULL DEFAULT 'ENABLED' COMMENT '系统参数状态(ENABLED:启用;DISABLED:禁用;)',
    `UPDATED_SYS_USER_ID` varchar(64)   NOT NULL COMMENT '更新的系统用户ID(空白字符串为初始化创建)',
    `CREATED_SYS_USER_ID` varchar(64)   NOT NULL COMMENT '创建的系统用户ID(空白字符串为初始化创建)',
    `UPDATED_DATE_TIME`   datetime      NOT NULL COMMENT '更新的日期时间',
    `CREATED_DATE_TIME`   datetime      NOT NULL COMMENT '创建的日期时间',
    PRIMARY KEY (`ID`)
);
CREATE UNIQUE INDEX `sys_parameter_UK_CODE` ON `sys_parameter`(`CODE`);

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission`
(
    `ID`                  varchar(64)  NOT NULL COMMENT '系统权限ID',
    `CODE`                varchar(50)  NOT NULL COMMENT '系统权限编码',
    `NAME`                varchar(50)  NOT NULL COMMENT '系统权限名称',
    `TYPE`                varchar(16)  NOT NULL COMMENT '系统权限类型(MENU:菜单;API:接口;ELEMENT:页面元素可见性;OPERATION:操作;)',
    `REMARK`              varchar(255) NOT NULL DEFAULT '' COMMENT '系统权限备注',
    `STATUS`              varchar(16)  NOT NULL DEFAULT 'ENABLED' COMMENT '系统权限状态(ENABLED:启用;DISABLED:禁用;)',
    `UPDATED_SYS_USER_ID` varchar(64)  NOT NULL COMMENT '更新的系统用户ID(空白字符串为初始化创建)',
    `CREATED_SYS_USER_ID` varchar(64)  NOT NULL COMMENT '创建的系统用户ID(空白字符串为初始化创建)',
    `UPDATED_DATE_TIME`   datetime     NOT NULL COMMENT '更新的日期时间',
    `CREATED_DATE_TIME`   datetime     NOT NULL COMMENT '创建的日期时间',
    PRIMARY KEY (`ID`)
);
CREATE UNIQUE INDEX `sys_permission_UK_CODE` ON `sys_permission`(`CODE`);

-- ----------------------------
-- Table structure for sys_permission_api
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission_api`;
CREATE TABLE `sys_permission_api`
(
    `ID`                  varchar(64) NOT NULL COMMENT '系统权限接口ID',
    `SYS_PERMISSION_ID`   varchar(64) NOT NULL COMMENT '系统权限ID',
    `SYS_API_ID`          varchar(64) NOT NULL COMMENT '系统接口ID',
    `CREATED_SYS_USER_ID` varchar(64) NOT NULL COMMENT '创建的系统用户ID(空白字符串为初始化创建)',
    `CREATED_DATE_TIME`   datetime    NOT NULL COMMENT '创建的日期时间',
    PRIMARY KEY (`ID`)
);
CREATE UNIQUE INDEX `UK_SYS_PERMISSION_ID_SYS_API_ID` ON `sys_permission_api`(`SYS_PERMISSION_ID`, `SYS_API_ID`);

-- ----------------------------
-- Table structure for sys_permission_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission_menu`;
CREATE TABLE `sys_permission_menu`
(
    `ID`                  varchar(64) NOT NULL COMMENT '系统权限菜单ID',
    `SYS_PERMISSION_ID`   varchar(64) NOT NULL COMMENT '系统权限ID',
    `SYS_MENU_ID`         varchar(64) NOT NULL COMMENT '系统菜单ID',
    `CREATED_SYS_USER_ID` varchar(64) NOT NULL COMMENT '创建的系统用户ID(空白字符串为初始化创建)',
    `CREATED_DATE_TIME`   datetime    NOT NULL COMMENT '创建的日期时间',
    PRIMARY KEY (`ID`)
);
CREATE UNIQUE INDEX `UK_SYS_PERMISSION_ID_SYS_MENU_ID` ON `sys_permission_menu`(`SYS_PERMISSION_ID`, `SYS_MENU_ID`);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`
(
    `ID`                  varchar(64)  NOT NULL COMMENT '系统角色ID',
    `CODE`                varchar(50)  NOT NULL COMMENT '系统角色编码',
    `NAME`                varchar(50)  NOT NULL COMMENT '系统角色名称',
    `REMARK`              varchar(255) NOT NULL DEFAULT '' COMMENT '系统角色备注',
    `STATUS`              varchar(16)  NOT NULL DEFAULT 'ENABLED' COMMENT '系统角色状态(ENABLED:启用;DISABLED:禁用;)',
    `UPDATED_SYS_USER_ID` varchar(64)  NOT NULL COMMENT '更新的系统用户ID(空白字符串为初始化创建)',
    `CREATED_SYS_USER_ID` varchar(64)  NOT NULL COMMENT '创建的系统用户ID(空白字符串为初始化创建)',
    `UPDATED_DATE_TIME`   datetime     NOT NULL COMMENT '更新的日期时间',
    `CREATED_DATE_TIME`   datetime     NOT NULL COMMENT '创建的日期时间',
    PRIMARY KEY (`ID`)
);
CREATE UNIQUE INDEX `sys_role_UK_CODE` ON `sys_role`(`CODE`);

-- ----------------------------
-- Table structure for sys_role_api
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_api`;
CREATE TABLE `sys_role_api`
(
    `ID`                  varchar(64) NOT NULL COMMENT '系统角色接口ID',
    `SYS_ROLE_ID`         varchar(64) NOT NULL COMMENT '系统角色ID',
    `SYS_API_ID`          varchar(64) NOT NULL COMMENT '系统接口ID',
    `CREATED_SYS_USER_ID` varchar(64) NOT NULL COMMENT '创建的系统用户ID(空白字符串为初始化创建)',
    `CREATED_DATE_TIME`   datetime    NOT NULL COMMENT '创建的日期时间',
    PRIMARY KEY (`ID`)
);
CREATE UNIQUE INDEX `UK_SYS_ROLE_ID_SYS_API_ID` ON `sys_role_api`(`SYS_ROLE_ID`, `SYS_API_ID`);

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`
(
    `ID`                  varchar(64) NOT NULL COMMENT '系统角色菜单ID',
    `SYS_ROLE_ID`         varchar(64) NOT NULL COMMENT '系统角色ID',
    `SYS_MENU_ID`         varchar(64) NOT NULL COMMENT '系统菜单ID',
    `CREATED_SYS_USER_ID` varchar(64) NOT NULL COMMENT '创建的系统用户ID(空白字符串为初始化创建)',
    `CREATED_DATE_TIME`   datetime    NOT NULL COMMENT '创建的日期时间',
    PRIMARY KEY (`ID`)
);
CREATE UNIQUE INDEX `UK_SYS_ROLE_ID_SYS_MENU_ID` ON `sys_role_menu`(`SYS_ROLE_ID`, `SYS_MENU_ID`);

-- ----------------------------
-- Table structure for sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission`
(
    `ID`                  varchar(64) NOT NULL COMMENT '系统角色权限ID',
    `SYS_ROLE_ID`         varchar(64) NOT NULL COMMENT '系统角色ID',
    `SYS_PERMISSION_ID`   varchar(64) NOT NULL COMMENT '系统权限ID',
    `CREATED_SYS_USER_ID` varchar(64) NOT NULL COMMENT '创建的系统用户ID(空白字符串为初始化创建)',
    `CREATED_DATE_TIME`   datetime    NOT NULL COMMENT '创建的日期时间',
    PRIMARY KEY (`ID`)
);
CREATE UNIQUE INDEX `UK_SYS_ROLE_ID_SYS_PERMISSION_ID` ON `sys_role_permission`(`SYS_ROLE_ID`, `SYS_PERMISSION_ID`);

-- ----------------------------
-- Table structure for sys_timed_task
-- ----------------------------
DROP TABLE IF EXISTS `sys_timed_task`;
CREATE TABLE `sys_timed_task`
(
    `ID`                  varchar(64)  NOT NULL COMMENT '系统定时任务ID',
    `CODE`                varchar(100) NOT NULL COMMENT '系统定时任务编码',
    `NAME`                varchar(100) NOT NULL COMMENT '系统定时任务名称',
    `CLASS_NAME`          varchar(255) NOT NULL COMMENT '系统定时任务类名',
    `CRON_EXPRESSION`     varchar(30)  NOT NULL COMMENT '系统定时任务CORN表达式',
    `DATA`                varchar(255) NOT NULL DEFAULT '' COMMENT '系统定时任务数据(JSON格式)',
    `TYPE`                varchar(16)  NOT NULL DEFAULT 'SYSTEM' COMMENT '系统定时任务类型(SYSTEM:系统定时任务;)',
    `REMARK`              varchar(255) NOT NULL DEFAULT '' COMMENT '系统定时任务备注',
    `STATUS`              varchar(16)  NOT NULL DEFAULT 'ENABLED' COMMENT '系统定时任务状态(ENABLED:启用;DISABLED:禁用;)',
    `UPDATED_SYS_USER_ID` varchar(64)  NOT NULL COMMENT '更新的系统用户ID(空白字符串为初始化创建)',
    `CREATED_SYS_USER_ID` varchar(64)  NOT NULL COMMENT '创建的系统用户ID(空白字符串为初始化创建)',
    `UPDATED_DATE_TIME`   datetime     NOT NULL COMMENT '更新的日期时间',
    `CREATED_DATE_TIME`   datetime     NOT NULL COMMENT '创建的日期时间',
    PRIMARY KEY (`ID`)
);
CREATE UNIQUE INDEX `sys_timed_task_UK_CODE` ON `sys_timed_task`(`CODE`);

-- ----------------------------
-- Table structure for sys_timed_task_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_timed_task_log`;
CREATE TABLE `sys_timed_task_log`
(
    `ID`                        varchar(64)  NOT NULL COMMENT '系统定时任务日志ID',
    `SYS_TIMED_TASK_ID`         varchar(64)  NOT NULL COMMENT '系统定时任务ID',
    `CODE`                      varchar(100) NOT NULL COMMENT '系统定时任务编码(冗余)',
    `NAME`                      varchar(100) NOT NULL COMMENT '系统定时任务名称(冗余)',
    `CLASS_NAME`                varchar(255) NOT NULL COMMENT '系统定时任务类名(冗余)',
    `CRON_EXPRESSION`           varchar(30)  NOT NULL COMMENT '系统定时任务CORN表达式(冗余)',
    `DATA`                      varchar(255) NOT NULL DEFAULT '' COMMENT '系统定时任务数据(JSON格式)(冗余)',
    `TYPE`                      varchar(16)  NOT NULL DEFAULT 'SYSTEM' COMMENT '系统定时任务类型(SYSTEM:系统定时任务;)(冗余)',
    `EXECUTION_STATUS`          varchar(16)  NOT NULL COMMENT '系统定时任务执行状态(NON:未执行;EXECUTING:执行中;SUCCESS:执行成功;FAILURE:执行失败;EXCEPTION:执行异常;REJECTION;执行拒绝;)',
    `EXCEPTION_MESSAGE`         varchar(256) NOT NULL DEFAULT '' COMMENT '系统定时任务异常信息',
    `EXECUTION_START_DATE_TIME` datetime     NOT NULL COMMENT '系统定时任务执行开始时间',
    `EXECUTION_END_DATE_TIME`   datetime     NOT NULL COMMENT '系统定时任务执行结束时间',
    `EXECUTION_TIME`            bigint(20)   NOT NULL COMMENT '系统定时任务执行用时(单位毫秒)',
    `REMARK`                    varchar(255) NOT NULL DEFAULT '' COMMENT '系统定时任务日志备注',
    `UPDATED_DATE_TIME`         datetime     NOT NULL COMMENT '更新的日期时间',
    `CREATED_DATE_TIME`         datetime     NOT NULL COMMENT '创建的日期时间',
    PRIMARY KEY (`ID`)
);
CREATE INDEX `IDX_SYS_TIMED_TASK_ID` ON `sys_timed_task_log`(`SYS_TIMED_TASK_ID`);
CREATE INDEX `IDX_CODE` ON `sys_timed_task_log`(`CODE`);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`
(
    `ID`                   varchar(64)      NOT NULL COMMENT '系统用户ID',
    `USERNAME`             varchar(30)      NOT NULL COMMENT '系统用户用户名',
    `PASSWORD`             varchar(64)      NOT NULL COMMENT '系统用户密码',
    `LEVEL`                int(11) unsigned NOT NULL DEFAULT '1' COMMENT '系统用户级别(0为超级用户,数值越小级别越大)',
    `LAST_LOGIN_DATE_TIME` datetime         NOT NULL DEFAULT '1000-01-01 00:00:00' COMMENT '系统用户最后登录日期时间',
    `REMARK`               varchar(255)     NOT NULL DEFAULT '' COMMENT '系统用户备注',
    `STATUS`               varchar(16)      NOT NULL DEFAULT 'ENABLED' COMMENT '系统用户状态(ENABLED:启用;LOCKED:锁定;EXPIRED:过期;DISABLE:禁用;)',
    `UPDATED_SYS_USER_ID`  varchar(64)      NOT NULL COMMENT '更新的系统用户ID(空白字符串为初始化创建)',
    `CREATED_SYS_USER_ID`  varchar(64)      NOT NULL COMMENT '创建的系统用户ID(空白字符串为初始化创建)',
    `UPDATED_DATE_TIME`    datetime         NOT NULL COMMENT '更新的日期时间',
    `CREATED_DATE_TIME`    datetime         NOT NULL COMMENT '创建的日期时间',
    PRIMARY KEY (`ID`)
);
CREATE UNIQUE INDEX `UK_USERNAME` ON `sys_user`(`USERNAME`);

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`
(
    `ID`                  varchar(64) NOT NULL COMMENT '系统用户角色ID',
    `SYS_USER_ID`         varchar(64) NOT NULL COMMENT '系统用户ID',
    `SYS_ROLE_ID`         varchar(64) NOT NULL COMMENT '系统角色ID',
    `CREATED_SYS_USER_ID` varchar(64) NOT NULL COMMENT '创建的系统用户ID(空白字符串为初始化创建)',
    `CREATED_DATE_TIME`   datetime    NOT NULL COMMENT '创建的日期时间',
    PRIMARY KEY (`ID`)
);
CREATE UNIQUE INDEX `UK_SYS_USER_ID_SYS_ROLE_ID` ON `sys_user_role`(`SYS_USER_ID`, `SYS_ROLE_ID`);
