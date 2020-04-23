/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 80018
Source Host           : 127.0.0.1:3306
Source Database       : chen

Target Server Type    : MYSQL
Target Server Version : 80018
File Encoding         : 65001

Date: 2020-04-23 16:02:23
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_api
-- ----------------------------
DROP TABLE IF EXISTS `sys_api`;
CREATE TABLE `sys_api` (
                           `ID` varchar(64) NOT NULL COMMENT '系统接口ID',
                           `NAME` varchar(50) NOT NULL COMMENT '系统接口名称',
                           `URL` varchar(255) NOT NULL COMMENT '系统接口URL',
                           `HTTP_METHOD` varchar(16) NOT NULL DEFAULT 'GET' COMMENT '系统接口HTTP请求方法(GET:GET请求;HEAD:HEAD请求;POST:POST请求;PUT:PUT请求;PATCH:PATCH请求;DELETE:DELETE请求;OPTIONS:OPTIONS请求;TRACE:TRACE请求;ANY:任意的请求;)',
                           `TYPE` varchar(16) NOT NULL COMMENT '系统接口类型(SYSTEM:系统接口;)',
                           `REMARK` varchar(255) NOT NULL DEFAULT '' COMMENT '系统接口备注',
                           `STATUS` varchar(16) NOT NULL DEFAULT 'ENABLED' COMMENT '系统接口状态(ENABLED:启用;DISABLED:禁用;)',
                           `UPDATED_SYS_USER_ID` varchar(64) NOT NULL COMMENT '更新的系统用户ID(空白字符串为初始化创建)',
                           `CREATED_SYS_USER_ID` varchar(64) NOT NULL COMMENT '创建的系统用户ID(空白字符串为初始化创建)',
                           `UPDATED_DATE_TIME` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新的日期时间',
                           `CREATED_DATE_TIME` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建的日期时间',
                           PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统接口表';

-- ----------------------------
-- Records of sys_api
-- ----------------------------
INSERT INTO `sys_api` VALUES ('1253232604056920065', '启用系统参数', '/chen/admin/sys/parameter/code/{code}/enable', 'POST', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232604165971970', '锁定系统用户', '/chen/admin/sys/user/{sysUserId}/lock', 'POST', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232604182749186', '获取在线登录用户的系统用户的角色列表', '/chen/admin/online/loginUser/sysRoleList', 'GET', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232604195332098', '获取系统组织的系统用户', '/chen/admin/sys/organization/{sysOrganizationId}/sysUser', 'GET', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232604203720706', '启用系统角色', '/chen/admin/sys/role/{sysRoleId}/enable', 'POST', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232604228886529', '禁用系统定时任务', '/chen/admin/sys/timedTask/{sysTimedTaskId}/disable', 'POST', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232604237275138', '获取定时任务日志列表', '/chen/admin/sys/timedTask/log', 'GET', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232604245663745', '获取系统用户', '/chen/admin/sys/user/{sysUserId}', 'GET', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232604270829570', '删除系统用户', '/chen/admin/sys/user/{sysUserId}', 'DELETE', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232604279218177', '修改系统用户', '/chen/admin/sys/user/{sysUserId}', 'PUT', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232604291801089', '获取系统定时任务', '/chen/admin/sys/timedTask/code/{code}', 'GET', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232604308578306', '删除系统定时任务', '/chen/admin/sys/timedTask/code/{code}', 'DELETE', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232604316966914', '创建系统权限', '/chen/admin/sys/permission', 'POST', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232604316966915', '获取分页的系统权限', '/chen/admin/sys/permission', 'GET', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232604316966916', '获取系统字典项目', '/chen/admin/sys/dictionary/item/{sysDictionaryItemId}', 'GET', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232604316966917', '删除系统字典项目', '/chen/admin/sys/dictionary/item/{sysDictionaryItemId}', 'DELETE', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232604451184642', '修改系统字典项目', '/chen/admin/sys/dictionary/item/{sysDictionaryItemId}', 'PUT', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232604463767553', '获取系统权限的系统菜单列表', '/chen/admin/sys/permission/menu/{sysPermissionId}', 'GET', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232604472156161', '获取系统参数', '/chen/admin/sys/parameter/{sysParameterId}', 'GET', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232604480544770', '删除系统参数', '/chen/admin/sys/parameter/{sysParameterId}', 'DELETE', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232604488933377', '修改系统参数', '/chen/admin/sys/parameter/{sysParameterId}', 'PUT', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232604497321985', '启用系统权限', '/chen/admin/sys/permission/{sysPermissionId}/enable', 'POST', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232604509904898', '获取所有的系统接口', '/chen/admin/sys/api/all', 'GET', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232604518293505', '获取系统权限', '/chen/admin/sys/permission/{sysPermissionId}', 'GET', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232604526682114', '删除系统权限', '/chen/admin/sys/permission/{sysPermissionId}', 'DELETE', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232604535070722', '修改系统权限', '/chen/admin/sys/permission/{sysPermissionId}', 'PUT', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232604543459330', '获取启用的系统接口', '/chen/admin/sys/api/all/enabled', 'GET', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232604560236546', '获取系统接口', '/chen/admin/sys/api/{sysApiId}', 'GET', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232604568625154', '删除系统接口', '/chen/admin/sys/api/{sysApiId}', 'DELETE', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232604577013761', '修改系统接口', '/chen/admin/sys/api/{sysApiId}', 'PUT', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232604585402369', '禁用系统接口', '/chen/admin/sys/api/{sysApiId}/disable', 'POST', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232604597985282', '获取启用的系统权限', '/chen/admin/sys/permission/all/enabled', 'GET', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232604614762497', '禁用系统参数', '/chen/admin/sys/parameter/{sysParameterId}/disable', 'POST', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232604623151105', '禁用系统权限', '/chen/admin/sys/permission/{sysPermissionId}/disable', 'POST', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232604635734017', '启用系统字典', '/chen/admin/sys/dictionary/{sysDictionaryId}/enable', 'POST', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232604644122625', '获取在线登录用户的系统用户的接口列表', '/chen/admin/online/loginUser/sysApiList', 'GET', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232604644122626', '获取启用的系统参数', '/chen/admin/sys/parameter/enabled', 'GET', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232604665094146', '获取启用的系统字典项目', '/chen/admin/sys/dictionary/item/enabled', 'GET', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232604665094147', '获取系统字典', '/chen/common/sys/dictionary/item/code/key/{code}/{key}', 'GET', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232604707037186', '获取系统字典列表', '/chen/common/sys/dictionary/item/alain/select/{code}', 'GET', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232604715425794', '设置系统角色', '/chen/admin/sys/organization/{sysOrganizationId}/setSysRole', 'PUT', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232604723814401', '设置系统权限', '/chen/admin/sys/role/{sysRoleId}/setSysPermission', 'PUT', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232604744785921', '获取系统菜单', '/chen/admin/sys/menu/{sysMenuId}', 'GET', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232604744785922', '删除系统菜单', '/chen/admin/sys/menu/{sysMenuId}', 'DELETE', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232604786728962', '修改系统菜单', '/chen/admin/sys/menu/{sysMenuId}', 'PUT', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232604786728963', '设置系统接口', '/chen/admin/sys/permission/{sysPermissionId}/setSysApi', 'PUT', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232604807700482', '获取系统字典', '/chen/common/sys/dictionary/item/{sysDictionaryItemId}', 'GET', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232604820283393', '获取定时任务日志', '/chen/admin/sys/timedTask/log/{sysTimedTaskLogId}', 'GET', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232604828672001', '删除定时任务日志', '/chen/admin/sys/timedTask/log/{sysTimedTaskLogId}', 'DELETE', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232604828672002', '创建系统菜单', '/chen/admin/sys/menu', 'POST', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232604837060610', '获取系统字典', '/chen/admin/sys/dictionary/{sysDictionaryId}', 'GET', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232604853837825', '删除系统字典', '/chen/admin/sys/dictionary/{sysDictionaryId}', 'DELETE', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232604862226434', '修改系统字典', '/chen/admin/sys/dictionary/{sysDictionaryId}', 'PUT', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232604874809345', '设置系统角色', '/chen/admin/sys/user/{sysUserId}/setSysRole', 'PUT', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232604883197954', '设置系统菜单', '/chen/admin/sys/permission/{sysPermissionId}/setSysMenu', 'PUT', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232604895780866', '获取系统字典项目', '/chen/admin/sys/dictionary/item/sysDictionaryId/{sysDictionaryId}', 'GET', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232604904169474', '获取系统角色的系统权限', '/chen/admin/sys/role/permission/{sysRoleId}', 'GET', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232604912558081', '获取所有的系统用户', '/chen/admin/sys/user/all', 'GET', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232604920946690', '获取系统角色', '/chen/admin/sys/role/{sysRoleId}', 'GET', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232604933529601', '删除系统角色', '/chen/admin/sys/role/{sysRoleId}', 'DELETE', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232604962889730', '修改系统角色', '/chen/admin/sys/role/{sysRoleId}', 'PUT', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232604971278338', '禁用系统菜单', '/chen/admin/sys/menu/{sysMenuId}/disable', 'POST', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232605021609985', '获取系统字典项目', '/chen/admin/sys/dictionary/item/code/key/{code}/{key}', 'GET', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232605029998593', '删除系统字典项目', '/chen/admin/sys/dictionary/item/code/key/{code}/{key}', 'DELETE', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232605038387202', '获取系统权限的系统接口列表', '/chen/admin/sys/permission/api/{sysPermissionId}', 'GET', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232605046775810', '禁用系统参数', '/chen/admin/sys/parameter/code/{code}/disable', 'POST', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232605059358722', '启用系统定时任务', '/chen/admin/sys/timedTask/code/{code}/enable', 'POST', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232605080330241', '启用系统用户', '/chen/admin/sys/user/{sysUserId}/enable', 'POST', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232605080330242', '获取系统菜单列表', '/chen/admin/sys/organization/list', 'GET', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232605097107458', '禁用系统定时任务', '/chen/admin/sys/timedTask/code/{code}/disable', 'POST', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232605113884674', '禁用系统角色', '/chen/admin/sys/role/{sysRoleId}/disable', 'POST', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232605151633410', '禁用系统用户', '/chen/admin/sys/user/{sysUserId}/disable', 'POST', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232605160022018', '获取系统角色的系统菜单列表', '/chen/admin/sys/role/menu/{sysRoleId}', 'GET', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232605160022019', '创建系统参数', '/chen/admin/sys/parameter', 'POST', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232605160022020', '获取分页的系统参数', '/chen/admin/sys/parameter', 'GET', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232605160022021', '获取启用的系统角色', '/chen/admin/sys/role/all/enabled', 'GET', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232605160022022', '获取系统字典列表', '/chen/admin/sys/dictionary/code/{code}', 'GET', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232605218742273', '删除系统字典', '/chen/admin/sys/dictionary/code/{code}', 'DELETE', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232605231325186', '禁用系统组织', '/chen/admin/sys/organization/{sysOrganizationId}/disable', 'POST', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232605239713793', '过期系统用户', '/chen/admin/sys/user/{sysUserId}/expire', 'POST', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232605248102401', '创建系统角色', '/chen/admin/sys/role', 'POST', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232605260685313', '获取分页的系统角色', '/chen/admin/sys/role', 'GET', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232605269073921', '禁用系统字典项目', '/chen/admin/sys/dictionary/item/{sysDictionaryItemId}/disable', 'POST', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232605273268225', '设置系统菜单', '/chen/admin/sys/role/{sysRoleId}/setSysMenu', 'PUT', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232605273268226', '禁用系统字典项目', '/chen/admin/sys/dictionary/item/code/key/{code}/{key}/disable', 'POST', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232605285851138', '获取启用的系统用户', '/chen/admin/sys/user/all/enabled', 'GET', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232605294239746', '获取在线登录用户的系统用户的菜单列表', '/chen/admin/online/loginUser/sysMenuList', 'GET', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232605306822658', '获取所有系统组织', '/chen/admin/sys/organization/all', 'GET', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232605306822659', '创建系统定时任务', '/chen/admin/sys/timedTask', 'POST', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232605336182786', '获取分页的系统定时任务列表', '/chen/admin/sys/timedTask', 'GET', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232605336182787', '获取启用的系统字典', '/chen/admin/sys/dictionary/enabled', 'GET', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232605352960002', '启用系统组织', '/chen/admin/sys/organization/{sysOrganizationId}/enable', 'POST', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232605378125826', '获取在线登录用户的系统用户', '/chen/admin/online/loginUser/sysUser', 'GET', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232605390708738', '获取系统组织', '/chen/admin/sys/organization/{sysOrganizationId}', 'GET', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232605399097346', '删除系统组织', '/chen/admin/sys/organization/{sysOrganizationId}', 'DELETE', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232605399097347', '修改系统组织', '/chen/admin/sys/organization/{sysOrganizationId}', 'PUT', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232605407485954', '启用系统菜单', '/chen/admin/sys/menu/{sysMenuId}/enable', 'POST', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232605415874561', '获取在线登录用户的系统用户的权限列表', '/chen/admin/online/loginUser/sysPermissionList', 'GET', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232605415874562', '获取系统用户的角色', '/chen/admin/sys/user/{sysUserId}/sysRole', 'GET', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232605424263170', '创建系统字典项目', '/chen/admin/sys/dictionary/item', 'POST', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232605432651777', '获取分页的系统字典项目', '/chen/admin/sys/dictionary/item', 'GET', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232605441040385', '创建系统组织', '/chen/admin/sys/organization', 'POST', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232605449428994', '创建系统字典', '/chen/admin/sys/dictionary', 'POST', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232605449428995', '获取分页的系统字典', '/chen/admin/sys/dictionary', 'GET', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232605457817601', '禁用系统字典', '/chen/admin/sys/dictionary/{sysDictionaryId}/disable', 'POST', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232605466206209', '获取系统菜单列表', '/chen/admin/sys/menu/list', 'GET', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232605466206210', '创建系统接口', '/chen/admin/sys/api', 'POST', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232605474594817', '获取分页的系统接口', '/chen/admin/sys/api', 'GET', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232605482983426', '获取系统参数', '/chen/admin/sys/parameter/code/{code}', 'GET', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232605491372033', '删除系统参数', '/chen/admin/sys/parameter/code/{code}', 'DELETE', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232605491372034', '执行系统定时任务', '/chen/admin/sys/timedTask/{sysTimedTaskId}/execution', 'POST', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232605499760641', '获取系统组织的系统角色', '/chen/admin/sys/organization/{sysOrganizationId}/sysRole', 'GET', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232605499760642', '获取系统字典项目列表', '/chen/admin/sys/dictionary/item/code/{code}', 'GET', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232605512343554', '删除系统字典项目', '/chen/admin/sys/dictionary/item/code/{code}', 'DELETE', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232605516537858', '执行系统定时任务', '/chen/admin/sys/timedTask/code/{code}/execution', 'POST', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232605516537859', '获取启用的系统组织', '/chen/admin/sys/organization/all/enabled', 'GET', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232605516537860', '启用系统字典项目', '/chen/admin/sys/dictionary/item/{sysDictionaryItemId}/enable', 'POST', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232605558480898', '启用系统参数', '/chen/admin/sys/parameter/{sysParameterId}/enable', 'POST', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232605558480899', '获取系统字典列表', '/chen/common/sys/dictionary/item/code/{code}', 'GET', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232605558480900', '获取启用的系统菜单', '/chen/admin/sys/menu/all/enabled', 'GET', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232605613006850', '获取系统定时任务', '/chen/admin/sys/timedTask/{sysTimedTaskId}', 'GET', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232605617201154', '删除系统定时任务', '/chen/admin/sys/timedTask/{sysTimedTaskId}', 'DELETE', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232605617201155', '修改系统定时任务', '/chen/admin/sys/timedTask/{sysTimedTaskId}', 'PUT', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232605625589762', '获取系统角色的系统接口列表', '/chen/admin/sys/role/api/{sysRoleId}', 'GET', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232605633978370', '启用系统接口', '/chen/admin/sys/api/{sysApiId}/enable', 'POST', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232605646561282', '启用系统字典项目', '/chen/admin/sys/dictionary/item/code/key/{code}/{key}/enable', 'POST', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232605654949890', '获取所有系统菜单', '/chen/admin/sys/menu/all', 'GET', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232605663338497', '设置系统接口', '/chen/admin/sys/role/{sysRoleId}/setSysApi', 'PUT', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232605663338498', '设置系统用户', '/chen/admin/sys/organization/{sysOrganizationId}/setSysUser', 'PUT', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232605671727106', '启用系统定时任务', '/chen/admin/sys/timedTask/{sysTimedTaskId}/enable', 'POST', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232605671727107', '获取系统字典列表', '/chen/common/sys/dictionary/item/alain/tag/{code}', 'GET', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232605671727108', '创建系统用户', '/chen/admin/sys/user', 'POST', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232605680115713', '获取分页的系统用户', '/chen/admin/sys/user', 'GET', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');
INSERT INTO `sys_api` VALUES ('1253232605688504321', '获取在线登录用户', '/chen/admin/online/loginUser', 'GET', 'SYSTEM', '', 'ENABLED', '', '', '2020-04-23 16:01:51', '2020-04-23 16:01:51');

-- ----------------------------
-- Table structure for sys_dictionary
-- ----------------------------
DROP TABLE IF EXISTS `sys_dictionary`;
CREATE TABLE `sys_dictionary` (
                                  `ID` varchar(64) NOT NULL COMMENT '系统字典ID',
                                  `CODE` varchar(100) NOT NULL COMMENT '系统字典编码',
                                  `NAME` varchar(50) NOT NULL COMMENT '系统字典名称',
                                  `TYPE` varchar(16) NOT NULL DEFAULT 'DB_TABLE_COLUMN' COMMENT '系统字典类型(DB_TABLE_COLUMN:数据库表字段;OTHER:其他;)',
                                  `REMARK` varchar(255) NOT NULL DEFAULT '' COMMENT '系统字典备注',
                                  `STATUS` varchar(16) NOT NULL DEFAULT 'ENABLED' COMMENT '系统字典状态(ENABLED:启用;DISABLED:禁用;)',
                                  `UPDATED_SYS_USER_ID` varchar(64) NOT NULL COMMENT '更新的系统用户ID(空白字符串为初始化创建)',
                                  `CREATED_SYS_USER_ID` varchar(64) NOT NULL COMMENT '创建的系统用户ID(空白字符串为初始化创建)',
                                  `UPDATED_DATE_TIME` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新的日期时间',
                                  `CREATED_DATE_TIME` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建的日期时间',
                                  PRIMARY KEY (`ID`),
                                  UNIQUE KEY `UK_CODE` (`CODE`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统字典表';

-- ----------------------------
-- Records of sys_dictionary
-- ----------------------------
INSERT INTO `sys_dictionary` VALUES ('1', 'STATUS', '状态', 'DB_TABLE_COLUMN', '', 'ENABLED', '', '', '2019-08-29 17:53:21', '2019-01-11 16:26:28');
INSERT INTO `sys_dictionary` VALUES ('12', 'SYS_API.TYPE', '系统API类型', 'DB_TABLE_COLUMN', '', 'ENABLED', '', '', '2019-08-29 17:53:40', '2019-03-27 15:07:37');
INSERT INTO `sys_dictionary` VALUES ('13', 'SYS_MENU.TYPE', '系统菜单类型', 'DB_TABLE_COLUMN', '', 'ENABLED', '', '', '2019-08-29 17:53:42', '2019-08-21 16:16:13');
INSERT INTO `sys_dictionary` VALUES ('155', 'SYS_PARAMETER.TYPE', '系统参数类型', 'DB_TABLE_COLUMN', '', 'ENABLED', '', '', '2019-08-29 17:53:21', '2019-01-11 16:26:28');
INSERT INTO `sys_dictionary` VALUES ('160', 'SYS_TIMED_TASK.EXECUTION_STATUS', '系统定时任务执行状态', 'DB_TABLE_COLUMN', '', 'ENABLED', '', '', '2019-08-29 17:53:21', '2019-01-11 16:26:28');
INSERT INTO `sys_dictionary` VALUES ('17', 'SYS_TIMED_TASK.TYPE', '系统定时任务类型', 'DB_TABLE_COLUMN', '', 'ENABLED', '', '', '2019-08-29 17:53:50', '2019-03-17 00:46:47');
INSERT INTO `sys_dictionary` VALUES ('18', 'SYS_USER.LEVEL', '系统用户等级', 'DB_TABLE_COLUMN', '', 'ENABLED', '', '', '2019-08-29 17:53:53', '2019-01-11 16:26:28');
INSERT INTO `sys_dictionary` VALUES ('200', 'SYS_ORGANIZATION.TYPE', '系统组织类型', 'DB_TABLE_COLUMN', '', 'ENABLED', '', '', '2019-08-29 17:53:34', '2019-03-27 15:02:00');
INSERT INTO `sys_dictionary` VALUES ('29', 'SYS_USER.STATUS', '系统用户状态', 'DB_TABLE_COLUMN', '', 'ENABLED', '', '', '2019-08-29 17:54:18', '2019-01-11 16:26:28');
INSERT INTO `sys_dictionary` VALUES ('3', 'SYS_API.HTTP_METHOD', 'HTTP请求方法', 'DB_TABLE_COLUMN', '', 'ENABLED', '', '', '2020-04-05 17:31:39', '2019-03-27 15:02:00');
INSERT INTO `sys_dictionary` VALUES ('34', 'SYS_PERMISSION.TYPE', '系统权限类型', 'DB_TABLE_COLUMN', '', 'ENABLED', '', '', '2020-03-29 17:53:34', '2019-03-29 15:02:00');
INSERT INTO `sys_dictionary` VALUES ('58', 'VALUE_TYPE', '值类型', 'DB_TABLE_COLUMN', '', 'ENABLED', '', '', '2019-08-29 17:53:34', '2019-03-27 15:02:00');
INSERT INTO `sys_dictionary` VALUES ('60', 'SYS_DICTIONARY.TYPE', '系统字典类型', 'DB_TABLE_COLUMN', '', 'ENABLED', '', '', '2019-08-29 17:53:34', '2019-03-27 15:02:00');

-- ----------------------------
-- Table structure for sys_dictionary_item
-- ----------------------------
DROP TABLE IF EXISTS `sys_dictionary_item`;
CREATE TABLE `sys_dictionary_item` (
                                       `ID` varchar(64) NOT NULL COMMENT '系统字典项目ID',
                                       `SYS_DICTIONARY_ID` varchar(64) NOT NULL COMMENT '系统字典ID',
                                       `CODE` varchar(100) NOT NULL COMMENT '系统字典编码(冗余)',
                                       `NAME` varchar(50) NOT NULL COMMENT '系统字典名称(冗余)',
                                       `KEY` varchar(100) NOT NULL COMMENT '系统字典项目KEY',
                                       `VALUE` varchar(30) NOT NULL COMMENT '系统字典项目值',
                                       `VALUE_I18N` varchar(100) NOT NULL DEFAULT '' COMMENT '系统字典项目值的国际化',
                                       `KEY_TYPE` varchar(16) NOT NULL DEFAULT 'STRING' COMMENT '系统字典项目KEY类型(STRING:字符串;BYTE:整数BYTE;SHORT:整数SHORT;INTEGER:整数INTEGER;LONG:整数LONG;BIG_INTEGER:大的整数;FLOAT:小数FLOAT;DOUBLE:小数DOUBLE;BIG_DECIMAL:大的小数;BOOLEAN:布尔;)',
                                       `VALUE_TYPE` varchar(16) NOT NULL DEFAULT 'STRING' COMMENT '系统字典项目值类型(STRING:字符串;BYTE:整数BYTE;SHORT:整数SHORT;INTEGER:整数INTEGER;LONG:整数LONG;BIG_INTEGER:大的整数;FLOAT:小数FLOAT;DOUBLE:小数DOUBLE;BIG_DECIMAL:大的小数;BOOLEAN:布尔;)',
                                       `COLOR` varchar(20) NOT NULL DEFAULT '' COMMENT '系统字典项目颜色值(用于展示,例如#ffffff)',
                                       `ORDER` int(11) unsigned NOT NULL COMMENT '系统字典项目显示顺序',
                                       `REMARK` varchar(255) NOT NULL DEFAULT '' COMMENT '系统字典项目备注',
                                       `STATUS` varchar(16) NOT NULL DEFAULT 'ENABLED' COMMENT '系统字典项目状态(ENABLED:启用;DISABLED:禁用;)',
                                       `UPDATED_SYS_USER_ID` varchar(64) NOT NULL COMMENT '更新的系统用户ID(空白字符串为初始化创建)',
                                       `CREATED_SYS_USER_ID` varchar(64) NOT NULL COMMENT '创建的系统用户ID(空白字符串为初始化创建)',
                                       `UPDATED_DATE_TIME` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新的日期时间',
                                       `CREATED_DATE_TIME` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建的日期时间',
                                       PRIMARY KEY (`ID`),
                                       UNIQUE KEY `UK_CODE_KEY` (`CODE`,`KEY`) USING BTREE,
                                       KEY `IDX_SYS_DICTIONARY_ID` (`SYS_DICTIONARY_ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统字典项目表';

-- ----------------------------
-- Records of sys_dictionary_item
-- ----------------------------
INSERT INTO `sys_dictionary_item` VALUES ('1', '1', 'STATUS', '状态', 'DISABLED', '禁用', '', 'STRING', 'STRING', 'red', '0', '', 'ENABLED', '', '', '2020-04-17 11:04:46', '2019-01-11 16:26:28');
INSERT INTO `sys_dictionary_item` VALUES ('10', '3', 'SYS_API.HTTP_METHOD', 'HTTP请求方法', 'PUT', 'PUT请求', '', 'STRING', 'STRING', 'red', '0', '', 'ENABLED', '', '', '2020-04-09 23:30:59', '2019-03-27 15:02:00');
INSERT INTO `sys_dictionary_item` VALUES ('11', '3', 'SYS_API.HTTP_METHOD', 'HTTP请求方法', 'TRACE', 'TRACE请求', '', 'STRING', 'STRING', 'red', '0', '', 'ENABLED', '', '', '2020-04-09 23:30:59', '2019-03-27 15:02:00');
INSERT INTO `sys_dictionary_item` VALUES ('12', '12', 'SYS_API.TYPE', '系统API类型', 'SYSTEM', '系统API', '', 'STRING', 'STRING', 'blue', '0', '', 'ENABLED', '', '', '2019-08-29 17:53:40', '2019-03-27 15:07:37');
INSERT INTO `sys_dictionary_item` VALUES ('13', '13', 'SYS_MENU.TYPE', '系统菜单类型', 'EXTERNAL_LINK', '外部链接', '', 'STRING', 'STRING', 'orange', '0', '', 'ENABLED', '', '', '2019-08-29 17:53:42', '2019-08-21 16:16:13');
INSERT INTO `sys_dictionary_item` VALUES ('14', '13', 'SYS_MENU.TYPE', '系统菜单类型', 'GROUP', '组', '', 'STRING', 'STRING', 'cyan', '0', '', 'ENABLED', '', '', '2020-04-09 23:30:59', '2019-08-21 16:16:13');
INSERT INTO `sys_dictionary_item` VALUES ('15', '13', 'SYS_MENU.TYPE', '系统菜单类型', 'LINK', '链接', '', 'STRING', 'STRING', 'gold', '0', '', 'ENABLED', '', '', '2020-04-09 23:30:59', '2019-08-21 16:16:13');
INSERT INTO `sys_dictionary_item` VALUES ('155', '155', 'SYS_PARAMETER.TYPE', '系统参数类型', 'SYSTEM', '系统参数', '', 'STRING', 'STRING', 'red', '0', '', 'ENABLED', '', '', '2020-04-09 23:30:59', '2019-03-27 15:02:00');
INSERT INTO `sys_dictionary_item` VALUES ('16', '13', 'SYS_MENU.TYPE', '系统菜单类型', 'MENU', '菜单', '', 'STRING', 'STRING', 'geekblue', '0', '', 'ENABLED', '', '', '2020-04-09 23:30:59', '2019-08-21 16:16:13');
INSERT INTO `sys_dictionary_item` VALUES ('160', '160', 'SYS_TIMED_TASK.EXECUTION_STATUS', '系统定时任务执行状态', 'NON', '未执行', '', 'STRING', 'STRING', '', '0', '', 'ENABLED', '', '', '2020-04-09 23:30:59', '2019-03-27 15:02:00');
INSERT INTO `sys_dictionary_item` VALUES ('161', '160', 'SYS_TIMED_TASK.EXECUTION_STATUS', '系统定时任务执行状态', 'EXECUTING', '执行中', '', 'STRING', 'STRING', '', '0', '', 'ENABLED', '', '', '2020-04-09 23:30:59', '2019-03-27 15:02:00');
INSERT INTO `sys_dictionary_item` VALUES ('162', '160', 'SYS_TIMED_TASK.EXECUTION_STATUS', '系统定时任务执行状态', 'SUCCESS', '执行成功', '', 'STRING', 'STRING', '', '0', '', 'ENABLED', '', '', '2020-04-09 23:30:59', '2019-03-27 15:02:00');
INSERT INTO `sys_dictionary_item` VALUES ('163', '160', 'SYS_TIMED_TASK.EXECUTION_STATUS', '系统定时任务执行状态', 'FAILURE', '执行失败', '', 'STRING', 'STRING', '', '0', '', 'ENABLED', '', '', '2020-04-09 23:30:59', '2019-03-27 15:02:00');
INSERT INTO `sys_dictionary_item` VALUES ('164', '160', 'SYS_TIMED_TASK.EXECUTION_STATUS', '系统定时任务执行状态', 'EXCEPTION', '执行异常', '', 'STRING', 'STRING', '', '0', '', 'ENABLED', '', '', '2020-04-09 23:30:59', '2019-03-27 15:02:00');
INSERT INTO `sys_dictionary_item` VALUES ('17', '17', 'SYS_TIMED_TASK.TYPE', '系统定时任务类型', 'SYSTEM', '系统', '', 'STRING', 'STRING', 'blue', '0', '', 'ENABLED', '', '', '2019-08-29 17:53:50', '2019-03-17 00:46:47');
INSERT INTO `sys_dictionary_item` VALUES ('18', '18', 'SYS_USER.LEVEL', '系统用户等级', '0', '超级', '', 'BYTE', 'STRING', 'geekblue', '0', '', 'ENABLED', '', '', '2020-04-11 15:12:28', '2019-01-11 16:26:28');
INSERT INTO `sys_dictionary_item` VALUES ('19', '18', 'SYS_USER.LEVEL', '系统用户等级', '1', '一级', '', 'BYTE', 'STRING', 'blue', '0', '', 'ENABLED', '', '', '2020-04-11 15:12:30', '2019-01-11 16:26:28');
INSERT INTO `sys_dictionary_item` VALUES ('2', '1', 'STATUS', '状态', 'ENABLED', '启用', '', 'STRING', 'STRING', 'geekblue', '0', '', 'ENABLED', '', '', '2020-04-09 23:30:59', '2019-01-11 16:26:28');
INSERT INTO `sys_dictionary_item` VALUES ('20', '18', 'SYS_USER.LEVEL', '系统用户等级', '10', '十级', '', 'BYTE', 'STRING', 'success', '0', '', 'ENABLED', '', '', '2020-04-11 15:12:32', '2019-01-11 16:26:28');
INSERT INTO `sys_dictionary_item` VALUES ('200', '200', 'SYS_ORGANIZATION.TYPE', '系统组织类型', 'ORDINARY', '普通组织', '', 'STRING', 'STRING', 'red', '0', '', 'ENABLED', '', '', '2020-04-09 23:30:59', '2019-03-27 15:02:00');
INSERT INTO `sys_dictionary_item` VALUES ('201', '200', 'SYS_ORGANIZATION.TYPE', '系统组织类型', 'COMPANY', '公司', '', 'STRING', 'STRING', 'red', '0', '', 'ENABLED', '', '', '2020-04-09 23:30:59', '2019-03-27 15:02:00');
INSERT INTO `sys_dictionary_item` VALUES ('202', '200', 'SYS_ORGANIZATION.TYPE', '系统组织类型', 'DEPARTMENT', '部门', '', 'STRING', 'STRING', 'red', '0', '', 'ENABLED', '', '', '2020-04-09 23:30:59', '2019-03-27 15:02:00');
INSERT INTO `sys_dictionary_item` VALUES ('21', '18', 'SYS_USER.LEVEL', '系统用户等级', '2', '二级', '', 'BYTE', 'STRING', 'purple', '0', '', 'ENABLED', '', '', '2020-04-11 15:12:33', '2019-01-11 16:26:28');
INSERT INTO `sys_dictionary_item` VALUES ('22', '18', 'SYS_USER.LEVEL', '系统用户等级', '3', '三级', '', 'BYTE', 'STRING', 'red', '0', '', 'ENABLED', '', '', '2020-04-11 15:12:35', '2019-01-11 16:26:28');
INSERT INTO `sys_dictionary_item` VALUES ('23', '18', 'SYS_USER.LEVEL', '系统用户等级', '4', '四级', '', 'BYTE', 'STRING', 'volcano', '0', '', 'ENABLED', '', '', '2020-04-11 15:12:09', '2019-01-11 16:26:28');
INSERT INTO `sys_dictionary_item` VALUES ('24', '18', 'SYS_USER.LEVEL', '系统用户等级', '5', '五级', '', 'BYTE', 'STRING', 'orange', '0', '', 'ENABLED', '', '', '2020-04-11 15:12:37', '2019-01-11 16:26:28');
INSERT INTO `sys_dictionary_item` VALUES ('25', '18', 'SYS_USER.LEVEL', '系统用户等级', '6', '六级', '', 'BYTE', 'STRING', 'gold', '0', '', 'ENABLED', '', '', '2020-04-11 15:12:38', '2019-01-11 16:26:28');
INSERT INTO `sys_dictionary_item` VALUES ('26', '18', 'SYS_USER.LEVEL', '系统用户等级', '7', '七级', '', 'BYTE', 'STRING', 'lime', '0', '', 'ENABLED', '', '', '2020-04-11 15:12:39', '2019-01-11 16:26:28');
INSERT INTO `sys_dictionary_item` VALUES ('27', '18', 'SYS_USER.LEVEL', '系统用户等级', '8', '八级', '', 'BYTE', 'STRING', 'green', '0', '', 'ENABLED', '', '', '2020-04-11 15:12:41', '2019-01-11 16:26:28');
INSERT INTO `sys_dictionary_item` VALUES ('28', '18', 'SYS_USER.LEVEL', '系统用户等级', '9', '九级', '', 'BYTE', 'STRING', 'cyan', '0', '', 'ENABLED', '', '', '2020-04-11 15:12:43', '2019-01-11 16:26:28');
INSERT INTO `sys_dictionary_item` VALUES ('29', '29', 'SYS_USER.STATUS', '系统用户状态', 'DISABLED', '禁用', '', 'STRING', 'STRING', 'red', '0', '', 'ENABLED', '', '', '2020-04-17 11:04:50', '2019-01-11 16:26:28');
INSERT INTO `sys_dictionary_item` VALUES ('3', '3', 'SYS_API.HTTP_METHOD', 'HTTP请求方法', 'ANY', '任意请求', '', 'STRING', 'STRING', 'red', '0', '', 'ENABLED', '', '', '2020-04-05 17:31:39', '2019-03-27 15:02:00');
INSERT INTO `sys_dictionary_item` VALUES ('30', '29', 'SYS_USER.STATUS', '系统用户状态', 'ENABLED', '启用', '', 'STRING', 'STRING', 'geekblue', '0', '', 'ENABLED', '', '', '2020-04-09 23:30:59', '2019-01-11 16:26:28');
INSERT INTO `sys_dictionary_item` VALUES ('31', '29', 'SYS_USER.STATUS', '系统用户状态', 'EXPIRED', '过期', '', 'STRING', 'STRING', 'purple', '0', '', 'ENABLED', '', '', '2020-04-09 23:30:59', '2019-01-11 16:26:28');
INSERT INTO `sys_dictionary_item` VALUES ('32', '29', 'SYS_USER.STATUS', '系统用户状态', 'LOCKED', '锁定', '', 'STRING', 'STRING', 'orange', '0', '', 'ENABLED', '', '', '2020-04-09 23:30:59', '2019-01-11 16:26:28');
INSERT INTO `sys_dictionary_item` VALUES ('33', '34', 'SYS_PERMISSION.TYPE', '系统权限类型', 'MENU', '菜单', '', 'STRING', 'STRING', '#ff8080', '0', '', 'ENABLED', '', '', '2020-04-09 23:30:59', '2019-03-29 15:02:00');
INSERT INTO `sys_dictionary_item` VALUES ('34', '34', 'SYS_PERMISSION.TYPE', '系统权限类型', 'API', '接口', '', 'STRING', 'STRING', '#ff8080', '0', '', 'ENABLED', '', '', '2020-04-09 22:01:10', '2019-03-29 15:02:00');
INSERT INTO `sys_dictionary_item` VALUES ('35', '34', 'SYS_PERMISSION.TYPE', '系统权限类型', 'ELEMENT', '页面元素可见性', '', 'STRING', 'STRING', '#ff8080', '0', '', 'ENABLED', '', '', '2020-04-09 23:30:59', '2019-03-29 15:02:00');
INSERT INTO `sys_dictionary_item` VALUES ('36', '34', 'SYS_PERMISSION.TYPE', '系统权限类型', 'OPERATION', '操作', '', 'STRING', 'STRING', '#ff8080', '0', '', 'ENABLED', '', '', '2020-04-09 23:30:59', '2019-03-29 15:02:00');
INSERT INTO `sys_dictionary_item` VALUES ('4', '3', 'SYS_API.HTTP_METHOD', 'HTTP请求方法', 'DELETE', 'DELETE请求', '', 'STRING', 'STRING', 'red', '0', '', 'ENABLED', '', '', '2020-04-09 23:30:59', '2019-03-27 15:02:00');
INSERT INTO `sys_dictionary_item` VALUES ('5', '3', 'SYS_API.HTTP_METHOD', 'HTTP请求方法', 'GET', 'GET请求', '', 'STRING', 'STRING', 'red', '0', '', 'ENABLED', '', '', '2020-04-09 23:30:59', '2019-03-27 15:02:00');
INSERT INTO `sys_dictionary_item` VALUES ('50', '58', 'VALUE_TYPE', '值类型', 'STRING', '字符串', '', 'STRING', 'STRING', '', '0', '', 'ENABLED', '', '', '2020-04-09 23:30:59', '2019-03-27 15:02:00');
INSERT INTO `sys_dictionary_item` VALUES ('51', '58', 'VALUE_TYPE', '值类型', 'BYTE', '整数BYTE', '', 'STRING', 'STRING', '', '0', '', 'ENABLED', '', '', '2020-04-09 23:30:59', '2019-03-27 15:02:00');
INSERT INTO `sys_dictionary_item` VALUES ('52', '58', 'VALUE_TYPE', '值类型', 'SHORT', '整数SHORT', '', 'STRING', 'STRING', '', '0', '', 'ENABLED', '', '', '2020-04-09 23:30:59', '2019-03-27 15:02:00');
INSERT INTO `sys_dictionary_item` VALUES ('53', '58', 'VALUE_TYPE', '值类型', 'INTEGER', '整数INTEGER', '', 'STRING', 'STRING', '', '0', '', 'ENABLED', '', '', '2020-04-09 23:30:59', '2019-03-27 15:02:00');
INSERT INTO `sys_dictionary_item` VALUES ('54', '58', 'VALUE_TYPE', '值类型', 'LONG', '整数LONG', '', 'STRING', 'STRING', '', '0', '', 'ENABLED', '', '', '2020-04-09 23:30:59', '2019-03-27 15:02:00');
INSERT INTO `sys_dictionary_item` VALUES ('55', '58', 'VALUE_TYPE', '值类型', 'BIG_INTEGER', '大的整数', '', 'STRING', 'STRING', '', '0', '', 'ENABLED', '', '', '2020-04-09 23:30:59', '2019-03-27 15:02:00');
INSERT INTO `sys_dictionary_item` VALUES ('56', '58', 'VALUE_TYPE', '值类型', 'FLOAT', '小数FLOAT', '', 'STRING', 'STRING', '', '0', '', 'ENABLED', '', '', '2020-04-09 23:30:59', '2019-03-27 15:02:00');
INSERT INTO `sys_dictionary_item` VALUES ('57', '58', 'VALUE_TYPE', '值类型', 'DOUBLE', '小数DOUBLE', '', 'STRING', 'STRING', '', '0', '', 'ENABLED', '', '', '2020-04-09 23:30:59', '2019-03-27 15:02:00');
INSERT INTO `sys_dictionary_item` VALUES ('58', '58', 'VALUE_TYPE', '值类型', 'BIG_DECIMAL', '大的小数', '', 'STRING', 'STRING', '', '0', '', 'ENABLED', '', '', '2019-08-29 17:53:34', '2019-03-27 15:02:00');
INSERT INTO `sys_dictionary_item` VALUES ('59', '58', 'VALUE_TYPE', '值类型', 'BOOLEAN', '布尔', '', 'STRING', 'STRING', '', '0', '', 'ENABLED', '', '', '2020-04-09 23:30:59', '2019-03-27 15:02:00');
INSERT INTO `sys_dictionary_item` VALUES ('6', '3', 'SYS_API.HTTP_METHOD', 'HTTP请求方法', 'HEAD', 'HEAD请求', '', 'STRING', 'STRING', 'red', '0', '', 'ENABLED', '', '', '2020-04-09 23:30:59', '2019-03-27 15:02:00');
INSERT INTO `sys_dictionary_item` VALUES ('7', '3', 'SYS_API.HTTP_METHOD', 'HTTP请求方法', 'OPTIONS', 'OPTIONS请求', '', 'STRING', 'STRING', 'red', '0', '', 'ENABLED', '', '', '2020-04-09 23:30:59', '2019-03-27 15:02:00');
INSERT INTO `sys_dictionary_item` VALUES ('70', '60', 'SYS_DICTIONARY.TYPE', '系统字典类型', 'DB_TABLE_COLUMN', '数据库表字段', '', 'STRING', 'STRING', '', '0', '', 'ENABLED', '', '', '2020-04-09 23:30:59', '2019-03-27 15:02:00');
INSERT INTO `sys_dictionary_item` VALUES ('71', '60', 'SYS_DICTIONARY.TYPE', '系统字典类型', 'OTHER', '其他', '', 'STRING', 'STRING', '', '0', '', 'ENABLED', '', '', '2020-04-09 23:30:59', '2019-03-27 15:02:00');
INSERT INTO `sys_dictionary_item` VALUES ('8', '3', 'SYS_API.HTTP_METHOD', 'HTTP请求方法', 'PATCH', 'PATCH请求', '', 'STRING', 'STRING', 'red', '0', '', 'ENABLED', '', '', '2020-04-09 23:30:59', '2019-03-27 15:02:00');
INSERT INTO `sys_dictionary_item` VALUES ('9', '3', 'SYS_API.HTTP_METHOD', 'HTTP请求方法', 'POST', 'POST请求', '', 'STRING', 'STRING', 'red', '0', '', 'ENABLED', '', '', '2020-04-09 23:30:59', '2019-03-27 15:02:00');

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
                            `ID` varchar(64) NOT NULL COMMENT '系统菜单ID',
                            `PARENT_ID` varchar(64) NOT NULL DEFAULT '' COMMENT '父级的系统菜单ID(引用本表ID字段,空白字符串为顶级)',
                            `NAME` varchar(32) NOT NULL COMMENT '系统菜单名称',
                            `NAME_I18N` varchar(100) NOT NULL DEFAULT '' COMMENT '系统字典名称的国际化',
                            `URL` varchar(255) NOT NULL COMMENT '系统菜单URL',
                            `ICON` varchar(30) NOT NULL DEFAULT '' COMMENT '系统菜单图标',
                            `TYPE` varchar(16) NOT NULL COMMENT '系统菜单类型(GROUP:组;MENU:菜单;LINK:链接;EXTERNAL_LINK:外部链接;)',
                            `ORDER` int(11) unsigned NOT NULL DEFAULT '1' COMMENT '系统菜单显示顺序',
                            `REMARK` varchar(255) NOT NULL DEFAULT '' COMMENT '系统菜单备注',
                            `STATUS` varchar(16) NOT NULL DEFAULT 'ENABLED' COMMENT '系统菜单状态(ENABLED:启用;DISABLED:禁用;)',
                            `UPDATED_SYS_USER_ID` varchar(64) NOT NULL COMMENT '更新的系统用户ID(空白字符串为初始化创建)',
                            `CREATED_SYS_USER_ID` varchar(64) NOT NULL COMMENT '创建的系统用户ID(空白字符串为初始化创建)',
                            `UPDATED_DATE_TIME` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新的日期时间',
                            `CREATED_DATE_TIME` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建的日期时间',
                            PRIMARY KEY (`ID`),
                            KEY `IDX_PARENT_ID` (`PARENT_ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统菜单表';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('1', '', '主导航', '', '', '', 'GROUP', '0', '', 'ENABLED', '', '', '2020-04-10 20:18:33', '2019-08-21 15:56:56');
INSERT INTO `sys_menu` VALUES ('10', '2', '定时任务管理', '', '/sys/timed-task', '', 'LINK', '8', '', 'ENABLED', '', '', '2020-04-12 21:45:18', '2019-08-21 15:59:12');
INSERT INTO `sys_menu` VALUES ('11', '2', '定时任务日志', '', '/sys/timed-task-log', '', 'LINK', '9', '', 'ENABLED', '', '', '2020-04-04 18:37:44', '2019-08-21 15:59:12');
INSERT INTO `sys_menu` VALUES ('12', '2', 'DRUID管理', '', '/sys/druid', '', 'LINK', '10', '', 'DISABLED', '', '', '2020-04-17 09:35:52', '2019-08-21 15:59:12');
INSERT INTO `sys_menu` VALUES ('2', '1', '系统管理', '', '', 'anticon anticon-dashboard', 'MENU', '0', '', 'ENABLED', '', '', '2020-04-10 20:18:37', '2019-08-21 15:56:08');
INSERT INTO `sys_menu` VALUES ('3', '2', '用户管理', '', '/sys/user', '', 'LINK', '1', '', 'ENABLED', '', '', '2020-04-10 20:18:28', '2019-08-21 15:58:41');
INSERT INTO `sys_menu` VALUES ('31', '2', '组织管理', '', '/sys/organization', '', 'LINK', '0', '', 'ENABLED', '', '', '2020-04-10 20:18:23', '2019-08-21 15:58:41');
INSERT INTO `sys_menu` VALUES ('4', '2', '角色管理', '', '/sys/role', '', 'LINK', '2', '', 'ENABLED', '', '', '2020-04-10 20:21:43', '2019-08-21 15:59:12');
INSERT INTO `sys_menu` VALUES ('5', '2', '权限管理', '', '/sys/permission', '', 'LINK', '3', '', 'ENABLED', '', '', '2020-04-04 21:02:23', '2019-08-21 15:59:12');
INSERT INTO `sys_menu` VALUES ('6', '2', '接口管理', '', '/sys/api', '', 'LINK', '4', '', 'ENABLED', '', '', '2020-04-04 18:37:31', '2019-08-21 15:59:12');
INSERT INTO `sys_menu` VALUES ('7', '2', '菜单管理', '', '/sys/menu', '', 'LINK', '5', '', 'ENABLED', '', '', '2020-04-04 18:37:34', '2019-08-21 15:59:12');
INSERT INTO `sys_menu` VALUES ('8', '2', '字典管理', '', '/sys/dictionary', '', 'LINK', '6', '', 'ENABLED', '', '', '2020-04-07 16:15:49', '2019-08-21 15:59:12');
INSERT INTO `sys_menu` VALUES ('9', '2', '参数管理', '', '/sys/parameter', '', 'LINK', '7', '', 'ENABLED', '', '', '2020-04-06 19:39:50', '2019-08-21 15:59:12');

-- ----------------------------
-- Table structure for sys_organization
-- ----------------------------
DROP TABLE IF EXISTS `sys_organization`;
CREATE TABLE `sys_organization` (
                                    `ID` varchar(64) NOT NULL COMMENT '系统组织ID',
                                    `PARENT_ID` varchar(64) NOT NULL COMMENT '父级的系统组织ID(引用本表ID字段,空白字符串为顶级)',
                                    `NAME` varchar(50) NOT NULL COMMENT '系统组织名称',
                                    `FULL_NAME` varchar(100) NOT NULL COMMENT '系统组织全称',
                                    `TYPE` varchar(16) NOT NULL COMMENT '系统组织类型(ORDINARY:普通组织;COMPANY:公司;DEPARTMENT:部门;)',
                                    `REMARK` varchar(255) NOT NULL DEFAULT '' COMMENT '系统组织备注',
                                    `STATUS` varchar(16) NOT NULL COMMENT '系统组织状态(ENABLED:启用;DISABLED:禁用;)',
                                    `UPDATED_SYS_USER_ID` varchar(64) NOT NULL COMMENT '更新的系统用户ID(空白字符串为初始化创建)',
                                    `CREATED_SYS_USER_ID` varchar(64) NOT NULL COMMENT '创建的系统用户ID(空白字符串为初始化创建)',
                                    `UPDATED_DATE_TIME` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新的日期时间',
                                    `CREATED_DATE_TIME` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建的日期时间',
                                    PRIMARY KEY (`ID`),
                                    KEY `IDX_PARENT_ID` (`PARENT_ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统组织表';

-- ----------------------------
-- Records of sys_organization
-- ----------------------------
INSERT INTO `sys_organization` VALUES ('1', '', 'chen', 'chen', 'ORDINARY', '', 'ENABLED', '', '', '2020-04-12 14:50:09', '2020-03-29 16:56:09');

-- ----------------------------
-- Table structure for sys_organization_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_organization_role`;
CREATE TABLE `sys_organization_role` (
                                         `ID` varchar(64) NOT NULL COMMENT '系统组织角色ID',
                                         `SYS_ORGANIZATION_ID` varchar(64) NOT NULL COMMENT '系统组织ID',
                                         `SYS_ROLE_ID` varchar(64) NOT NULL COMMENT '系统角色ID',
                                         `CREATED_SYS_USER_ID` varchar(64) NOT NULL COMMENT '创建的系统用户ID(空白字符串为初始化创建)',
                                         `CREATED_DATE_TIME` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建的日期时间',
                                         PRIMARY KEY (`ID`),
                                         UNIQUE KEY `UK_SYS_ORGANIZATION_ID_SYS_ROLE_ID` (`SYS_ORGANIZATION_ID`,`SYS_ROLE_ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统组织角色表';

-- ----------------------------
-- Records of sys_organization_role
-- ----------------------------
INSERT INTO `sys_organization_role` VALUES ('1250955932041605122', '1', '1', '1', '2020-04-17 09:15:10');
INSERT INTO `sys_organization_role` VALUES ('1250955932087742465', '1', '2', '1', '2020-04-17 09:15:10');

-- ----------------------------
-- Table structure for sys_organization_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_organization_user`;
CREATE TABLE `sys_organization_user` (
                                         `ID` varchar(64) NOT NULL COMMENT '系统组织用户ID',
                                         `SYS_ORGANIZATION_ID` varchar(64) NOT NULL COMMENT '系统组织ID',
                                         `SYS_USER_ID` varchar(64) NOT NULL COMMENT '系统用户ID',
                                         `CREATED_SYS_USER_ID` varchar(64) NOT NULL COMMENT '创建的系统用户ID(空白字符串为初始化创建)',
                                         `CREATED_DATE_TIME` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建的日期时间',
                                         PRIMARY KEY (`ID`),
                                         UNIQUE KEY `UK_SYS_ORGANIZATION_ID_SYS_USER_ID` (`SYS_ORGANIZATION_ID`,`SYS_USER_ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统组织用户表';

-- ----------------------------
-- Records of sys_organization_user
-- ----------------------------
INSERT INTO `sys_organization_user` VALUES ('1250957115992961026', '1', '1', '1', '2020-04-17 09:19:53');

-- ----------------------------
-- Table structure for sys_parameter
-- ----------------------------
DROP TABLE IF EXISTS `sys_parameter`;
CREATE TABLE `sys_parameter` (
                                 `ID` varchar(64) NOT NULL COMMENT '系统参数ID',
                                 `CODE` varchar(100) NOT NULL COMMENT '系统参数编码',
                                 `NAME` varchar(100) NOT NULL COMMENT '系统参数名称',
                                 `VALUE` varchar(1024) NOT NULL COMMENT '系统参数值',
                                 `VALUE_TYPE` varchar(16) NOT NULL DEFAULT 'STRING' COMMENT '系统参数值类型(STRING:字符串;BYTE:整数BYTE;SHORT:整数SHORT;INTEGER:整数INTEGER;LONG:整数LONG;BIG_INTEGER:大的整数;FLOAT:小数FLOAT;DOUBLE:小数DOUBLE;BIG_DECIMAL:大的小数;BOOLEAN:布尔;)',
                                 `TYPE` varchar(16) NOT NULL DEFAULT 'SYSTEM' COMMENT '系统参数类型(SYSTEM:系统参数;)',
                                 `REMARK` varchar(255) NOT NULL DEFAULT '' COMMENT '系统参数备注',
                                 `STATUS` varchar(16) NOT NULL DEFAULT 'ENABLED' COMMENT '系统参数状态(ENABLED:启用;DISABLED:禁用;)',
                                 `UPDATED_SYS_USER_ID` varchar(64) NOT NULL COMMENT '更新的系统用户ID(空白字符串为初始化创建)',
                                 `CREATED_SYS_USER_ID` varchar(64) NOT NULL COMMENT '创建的系统用户ID(空白字符串为初始化创建)',
                                 `UPDATED_DATE_TIME` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新的日期时间',
                                 `CREATED_DATE_TIME` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建的日期时间',
                                 PRIMARY KEY (`ID`),
                                 UNIQUE KEY `UK_CODE` (`CODE`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统参数表';

-- ----------------------------
-- Records of sys_parameter
-- ----------------------------
INSERT INTO `sys_parameter` VALUES ('1', 'SERVER_TYPE', '服务类型', 'BETA', 'STRING', 'SYSTEM', '', 'ENABLED', '1', '', '2020-04-11 23:10:15', '2019-06-27 16:43:14');

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission` (
                                  `ID` varchar(64) NOT NULL COMMENT '系统权限ID',
                                  `CODE` varchar(50) NOT NULL COMMENT '系统权限编码',
                                  `NAME` varchar(50) NOT NULL COMMENT '系统权限名称',
                                  `TYPE` varchar(16) NOT NULL COMMENT '系统权限类型(MENU:菜单;API:接口;ELEMENT:页面元素可见性;OPERATION:操作;)',
                                  `REMARK` varchar(255) NOT NULL DEFAULT '' COMMENT '系统权限备注',
                                  `STATUS` varchar(16) NOT NULL DEFAULT 'ENABLED' COMMENT '系统权限状态(ENABLED:启用;DISABLED:禁用;)',
                                  `UPDATED_SYS_USER_ID` varchar(64) NOT NULL COMMENT '更新的系统用户ID(空白字符串为初始化创建)',
                                  `CREATED_SYS_USER_ID` varchar(64) NOT NULL COMMENT '创建的系统用户ID(空白字符串为初始化创建)',
                                  `UPDATED_DATE_TIME` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新的日期时间',
                                  `CREATED_DATE_TIME` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建的日期时间',
                                  PRIMARY KEY (`ID`),
                                  UNIQUE KEY `UK_CODE` (`CODE`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统权限表';

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
INSERT INTO `sys_permission` VALUES ('1', 'ADMIN:INSERT', 'ADMIN:INSERT', 'MENU', '插入', 'ENABLED', '', '', '2020-04-09 22:04:55', '2018-11-03 11:36:07');
INSERT INTO `sys_permission` VALUES ('1246724911762870273', 'test', 'test', 'API', '', 'ENABLED', '1', '1', '2020-04-11 18:37:25', '2020-04-05 17:02:37');
INSERT INTO `sys_permission` VALUES ('1250791833370361858', '111', '111', 'ELEMENT', '', 'ENABLED', '1', '1', '2020-04-16 22:23:38', '2020-04-16 22:23:06');
INSERT INTO `sys_permission` VALUES ('1250791875686694914', '1213', '1', 'ELEMENT', '', 'ENABLED', '1', '1', '2020-04-16 22:23:42', '2020-04-16 22:23:16');
INSERT INTO `sys_permission` VALUES ('2', 'ADMIN:DELETE', 'ADMIN:DELETE', 'MENU', '删除', 'ENABLED', '', '', '2020-04-09 22:04:59', '2018-11-03 11:36:07');

-- ----------------------------
-- Table structure for sys_permission_api
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission_api`;
CREATE TABLE `sys_permission_api` (
                                      `ID` varchar(64) NOT NULL COMMENT '系统权限接口ID',
                                      `SYS_PERMISSION_ID` varchar(64) NOT NULL COMMENT '系统权限ID',
                                      `SYS_API_ID` varchar(64) NOT NULL COMMENT '系统接口ID',
                                      `CREATED_SYS_USER_ID` varchar(64) NOT NULL COMMENT '创建的系统用户ID(空白字符串为初始化创建)',
                                      `CREATED_DATE_TIME` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建的日期时间',
                                      PRIMARY KEY (`ID`),
                                      UNIQUE KEY `UK_SYS_PERMISSION_ID_SYS_API_ID` (`SYS_PERMISSION_ID`,`SYS_API_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统权限接口表';

-- ----------------------------
-- Records of sys_permission_api
-- ----------------------------
INSERT INTO `sys_permission_api` VALUES ('1248909227498414082', '1246724911762870273', '1', '1', '2020-04-11 17:42:18');
INSERT INTO `sys_permission_api` VALUES ('1250791024171679746', '1', '1', '1', '2020-04-16 22:19:53');

-- ----------------------------
-- Table structure for sys_permission_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission_menu`;
CREATE TABLE `sys_permission_menu` (
                                       `ID` varchar(64) NOT NULL COMMENT '系统权限菜单ID',
                                       `SYS_PERMISSION_ID` varchar(64) NOT NULL COMMENT '系统权限ID',
                                       `SYS_MENU_ID` varchar(64) NOT NULL COMMENT '系统菜单ID',
                                       `CREATED_SYS_USER_ID` varchar(64) NOT NULL COMMENT '创建的系统用户ID(空白字符串为初始化创建)',
                                       `CREATED_DATE_TIME` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建的日期时间',
                                       PRIMARY KEY (`ID`),
                                       UNIQUE KEY `UK_SYS_PERMISSION_ID_SYS_MENU_ID` (`SYS_PERMISSION_ID`,`SYS_MENU_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统权限菜单表';

-- ----------------------------
-- Records of sys_permission_menu
-- ----------------------------
INSERT INTO `sys_permission_menu` VALUES ('1246700511663497218', '2', '1', '1', '2020-04-05 15:25:39');
INSERT INTO `sys_permission_menu` VALUES ('1246700511671885826', '2', '2', '1', '2020-04-05 15:25:39');
INSERT INTO `sys_permission_menu` VALUES ('1246700511684468737', '2', '12', '1', '2020-04-05 15:25:39');
INSERT INTO `sys_permission_menu` VALUES ('1246703548217958401', '1', '1', '1', '2020-04-05 15:37:43');
INSERT INTO `sys_permission_menu` VALUES ('1246703548226347010', '1', '2', '1', '2020-04-05 15:37:43');
INSERT INTO `sys_permission_menu` VALUES ('1246703548226347011', '1', '12', '1', '2020-04-05 15:37:43');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
                            `ID` varchar(64) NOT NULL COMMENT '系统角色ID',
                            `CODE` varchar(50) NOT NULL COMMENT '系统角色编码',
                            `NAME` varchar(50) NOT NULL COMMENT '系统角色名称',
                            `REMARK` varchar(255) NOT NULL DEFAULT '' COMMENT '系统角色备注',
                            `STATUS` varchar(16) NOT NULL DEFAULT 'ENABLED' COMMENT '系统角色状态(ENABLED:启用;DISABLED:禁用;)',
                            `UPDATED_SYS_USER_ID` varchar(64) NOT NULL COMMENT '更新的系统用户ID(空白字符串为初始化创建)',
                            `CREATED_SYS_USER_ID` varchar(64) NOT NULL COMMENT '创建的系统用户ID(空白字符串为初始化创建)',
                            `UPDATED_DATE_TIME` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新的日期时间',
                            `CREATED_DATE_TIME` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建的日期时间',
                            PRIMARY KEY (`ID`),
                            UNIQUE KEY `UK_CODE` (`CODE`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统角色表';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', 'SUPER_ADMIN', 'SUPER_ADMIN', '超级管理员', 'ENABLED', '', '', '2019-03-08 17:59:56', '2018-11-03 11:35:22');
INSERT INTO `sys_role` VALUES ('2', 'ADMIN', 'ADMIN', '管理员', 'ENABLED', '1', '', '2019-03-16 10:30:22', '2018-11-03 11:35:22');

-- ----------------------------
-- Table structure for sys_role_api
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_api`;
CREATE TABLE `sys_role_api` (
                                `ID` varchar(64) NOT NULL COMMENT '系统角色接口ID',
                                `SYS_ROLE_ID` varchar(64) NOT NULL COMMENT '系统角色ID',
                                `SYS_API_ID` varchar(64) NOT NULL COMMENT '系统接口ID',
                                `CREATED_SYS_USER_ID` varchar(64) NOT NULL COMMENT '创建的系统用户ID(空白字符串为初始化创建)',
                                `CREATED_DATE_TIME` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建的日期时间',
                                PRIMARY KEY (`ID`),
                                UNIQUE KEY `UK_SYS_ROLE_ID_SYS_API_ID` (`SYS_ROLE_ID`,`SYS_API_ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统角色接口表';

-- ----------------------------
-- Records of sys_role_api
-- ----------------------------
INSERT INTO `sys_role_api` VALUES ('1248911733028823042', '1', '1251764654095503361', '1', '2020-04-11 17:52:15');
INSERT INTO `sys_role_api` VALUES ('12489117330288230421', '2', '1251764654095503361', '1', '2020-04-11 17:52:15');

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
                                 `ID` varchar(64) NOT NULL COMMENT '系统角色菜单ID',
                                 `SYS_ROLE_ID` varchar(64) NOT NULL COMMENT '系统角色ID',
                                 `SYS_MENU_ID` varchar(64) NOT NULL COMMENT '系统菜单ID',
                                 `CREATED_SYS_USER_ID` varchar(64) NOT NULL COMMENT '创建的系统用户ID(空白字符串为初始化创建)',
                                 `CREATED_DATE_TIME` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建的日期时间',
                                 PRIMARY KEY (`ID`),
                                 UNIQUE KEY `UK_SYS_ROLE_ID_SYS_MENU_ID` (`SYS_ROLE_ID`,`SYS_MENU_ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统角色菜单表';

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES ('1', '1', '1', '', '2020-04-04 18:38:59');
INSERT INTO `sys_role_menu` VALUES ('10', '1', '10', '', '2020-04-04 18:38:59');
INSERT INTO `sys_role_menu` VALUES ('11', '1', '11', '', '2020-04-04 18:38:59');
INSERT INTO `sys_role_menu` VALUES ('111', '1', '31', '', '2020-04-04 18:38:59');
INSERT INTO `sys_role_menu` VALUES ('12', '1', '12', '', '2020-04-04 18:38:59');
INSERT INTO `sys_role_menu` VALUES ('1248905075238117377', '2', '1', '1', '2020-04-11 17:25:48');
INSERT INTO `sys_role_menu` VALUES ('1248905075439443970', '2', '2', '1', '2020-04-11 17:25:48');
INSERT INTO `sys_role_menu` VALUES ('1248905075502358529', '2', '12', '1', '2020-04-11 17:25:48');
INSERT INTO `sys_role_menu` VALUES ('2', '1', '2', '', '2020-04-04 18:38:59');
INSERT INTO `sys_role_menu` VALUES ('3', '1', '3', '', '2020-04-04 18:38:59');
INSERT INTO `sys_role_menu` VALUES ('4', '1', '4', '', '2020-04-04 18:38:59');
INSERT INTO `sys_role_menu` VALUES ('5', '1', '5', '', '2020-04-04 18:38:59');
INSERT INTO `sys_role_menu` VALUES ('6', '1', '6', '', '2020-04-04 18:38:59');
INSERT INTO `sys_role_menu` VALUES ('7', '1', '7', '', '2020-04-04 18:38:59');
INSERT INTO `sys_role_menu` VALUES ('8', '1', '8', '', '2020-04-04 18:38:59');
INSERT INTO `sys_role_menu` VALUES ('9', '1', '9', '', '2020-04-04 18:38:59');

-- ----------------------------
-- Table structure for sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission` (
                                       `ID` varchar(64) NOT NULL COMMENT '系统角色权限ID',
                                       `SYS_ROLE_ID` varchar(64) NOT NULL COMMENT '系统角色ID',
                                       `SYS_PERMISSION_ID` varchar(64) NOT NULL COMMENT '系统权限ID',
                                       `CREATED_SYS_USER_ID` varchar(64) NOT NULL COMMENT '创建的系统用户ID(空白字符串为初始化创建)',
                                       `CREATED_DATE_TIME` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建的日期时间',
                                       PRIMARY KEY (`ID`),
                                       UNIQUE KEY `UK_SYS_ROLE_ID_SYS_PERMISSION_ID` (`SYS_ROLE_ID`,`SYS_PERMISSION_ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统角色权限表';

-- ----------------------------
-- Records of sys_role_permission
-- ----------------------------
INSERT INTO `sys_role_permission` VALUES ('1248907997137084417', '1', '1', '1', '2020-04-11 17:37:25');
INSERT INTO `sys_role_permission` VALUES ('1248907997158055938', '1', '1246724911762870273', '1', '2020-04-11 17:37:25');
INSERT INTO `sys_role_permission` VALUES ('1248907997162250242', '1', '2', '1', '2020-04-11 17:37:25');

-- ----------------------------
-- Table structure for sys_timed_task
-- ----------------------------
DROP TABLE IF EXISTS `sys_timed_task`;
CREATE TABLE `sys_timed_task` (
                                  `ID` varchar(64) NOT NULL COMMENT '系统定时任务ID',
                                  `CODE` varchar(100) NOT NULL COMMENT '系统定时任务编码',
                                  `NAME` varchar(100) NOT NULL COMMENT '系统定时任务名称',
                                  `CLASS_NAME` varchar(255) NOT NULL COMMENT '系统定时任务类名',
                                  `CRON_EXPRESSION` varchar(30) NOT NULL COMMENT '系统定时任务CORN表达式',
                                  `DATA` varchar(255) NOT NULL DEFAULT '' COMMENT '系统定时任务数据(JSON格式)',
                                  `TYPE` varchar(16) NOT NULL DEFAULT 'SYSTEM' COMMENT '系统定时任务类型(SYSTEM:系统定时任务;)',
                                  `REMARK` varchar(255) NOT NULL DEFAULT '' COMMENT '系统定时任务备注',
                                  `STATUS` varchar(16) NOT NULL DEFAULT 'ENABLED' COMMENT '系统定时任务状态(ENABLED:启用;DISABLED:禁用;)',
                                  `UPDATED_SYS_USER_ID` varchar(64) NOT NULL COMMENT '更新的系统用户ID(空白字符串为初始化创建)',
                                  `CREATED_SYS_USER_ID` varchar(64) NOT NULL COMMENT '创建的系统用户ID(空白字符串为初始化创建)',
                                  `UPDATED_DATE_TIME` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新的日期时间',
                                  `CREATED_DATE_TIME` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建的日期时间',
                                  PRIMARY KEY (`ID`),
                                  UNIQUE KEY `UK_CODE` (`CODE`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统定时任务表';

-- ----------------------------
-- Records of sys_timed_task
-- ----------------------------

-- ----------------------------
-- Table structure for sys_timed_task_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_timed_task_log`;
CREATE TABLE `sys_timed_task_log` (
                                      `ID` varchar(64) NOT NULL COMMENT '系统定时任务日志ID',
                                      `SYS_TIMED_TASK_ID` varchar(64) NOT NULL COMMENT '系统定时任务ID',
                                      `CODE` varchar(100) NOT NULL COMMENT '系统定时任务编码(冗余)',
                                      `NAME` varchar(100) NOT NULL COMMENT '系统定时任务名称(冗余)',
                                      `CLASS_NAME` varchar(255) NOT NULL COMMENT '系统定时任务类名(冗余)',
                                      `CRON_EXPRESSION` varchar(30) NOT NULL COMMENT '系统定时任务CORN表达式(冗余)',
                                      `DATA` varchar(255) NOT NULL DEFAULT '' COMMENT '系统定时任务数据(JSON格式)(冗余)',
                                      `TYPE` varchar(16) NOT NULL DEFAULT 'SYSTEM' COMMENT '系统定时任务类型(SYSTEM:系统定时任务;)(冗余)',
                                      `EXECUTION_STATUS` varchar(16) NOT NULL COMMENT '系统定时任务执行状态(NON:未执行;EXECUTING:执行中;SUCCESS:执行成功;FAILURE:执行失败;EXCEPTION:执行异常;REJECTION;执行拒绝;)',
                                      `EXCEPTION_MESSAGE` varchar(256) NOT NULL DEFAULT '' COMMENT '系统定时任务异常信息',
                                      `EXECUTION_START_DATE_TIME` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '系统定时任务执行开始时间',
                                      `EXECUTION_END_DATE_TIME` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '系统定时任务执行结束时间',
                                      `EXECUTION_TIME` bigint(20) NOT NULL COMMENT '系统定时任务执行用时(单位毫秒)',
                                      `REMARK` varchar(255) NOT NULL DEFAULT '' COMMENT '系统定时任务日志备注',
                                      `UPDATED_DATE_TIME` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新的日期时间',
                                      `CREATED_DATE_TIME` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建的日期时间',
                                      PRIMARY KEY (`ID`),
                                      KEY `IDX_SYS_TIMED_TASK_ID` (`SYS_TIMED_TASK_ID`) USING BTREE,
                                      KEY `IDX_CODE` (`CODE`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统定时任务日志表';

-- ----------------------------
-- Records of sys_timed_task_log
-- ----------------------------
INSERT INTO `sys_timed_task_log` VALUES ('1', '1', '1', '1', '1', '1', '1', 'SYSTEM', 'NON', '1', '2020-04-12 00:00:05', '2020-04-12 00:00:05', '1', '1', '2020-04-12 00:00:05', '2020-04-11 23:58:40');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
                            `ID` varchar(64) NOT NULL COMMENT '系统用户ID',
                            `USERNAME` varchar(30) NOT NULL COMMENT '系统用户用户名',
                            `PASSWORD` varchar(64) NOT NULL COMMENT '系统用户密码',
                            `LEVEL` int(11) unsigned NOT NULL DEFAULT '1' COMMENT '系统用户级别(0为超级用户,数值越小级别越大)',
                            `LAST_LOGIN_DATE_TIME` datetime NOT NULL DEFAULT '1000-01-01 00:00:00' COMMENT '系统用户最后登录日期时间',
                            `REMARK` varchar(255) NOT NULL DEFAULT '' COMMENT '系统用户备注',
                            `STATUS` varchar(16) NOT NULL DEFAULT 'ENABLED' COMMENT '系统用户状态(ENABLED:启用;LOCKED:锁定;EXPIRED:过期;DISABLE:禁用;)',
                            `UPDATED_SYS_USER_ID` varchar(64) NOT NULL COMMENT '更新的系统用户ID(空白字符串为初始化创建)',
                            `CREATED_SYS_USER_ID` varchar(64) NOT NULL COMMENT '创建的系统用户ID(空白字符串为初始化创建)',
                            `UPDATED_DATE_TIME` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新的日期时间',
                            `CREATED_DATE_TIME` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建的日期时间',
                            PRIMARY KEY (`ID`),
                            UNIQUE KEY `UK_USERNAME` (`USERNAME`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统用户表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', 'chen', '$2a$10$ovFUzQgle5M/OO3OYroTE.1qM2Jq97ufTHyi4ZLqmZynRRuoATnmq', '0', '2020-04-23 15:45:38', '系统默认用户', 'ENABLED', '', '', '2020-04-08 21:40:51', '2018-08-27 19:19:16');
INSERT INTO `sys_user` VALUES ('1248847896044904450', 'cehn11', '$2a$10$svX.ZIjOUh26mH7EgDW1FOTjSe4cyScRZGbTXMnbdyfutUUbuXdYS', '1', '1000-01-01 00:00:00', '', 'DISABLED', '1', '1', '2020-04-17 11:25:14', '2020-04-11 13:38:36');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
                                 `ID` varchar(64) NOT NULL COMMENT '系统用户角色ID',
                                 `SYS_USER_ID` varchar(64) NOT NULL COMMENT '系统用户ID',
                                 `SYS_ROLE_ID` varchar(64) NOT NULL COMMENT '系统角色ID',
                                 `CREATED_SYS_USER_ID` varchar(64) NOT NULL COMMENT '创建的系统用户ID(空白字符串为初始化创建)',
                                 `CREATED_DATE_TIME` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建的日期时间',
                                 PRIMARY KEY (`ID`),
                                 UNIQUE KEY `UK_SYS_USER_ID_SYS_ROLE_ID` (`SYS_USER_ID`,`SYS_ROLE_ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统用户角色表';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('1248857823916339202', '1248847896044904450', '1', '1', '2020-04-11 14:18:03');
INSERT INTO `sys_user_role` VALUES ('1248857823924727810', '1248847896044904450', '2', '1', '2020-04-11 14:18:03');
INSERT INTO `sys_user_role` VALUES ('1248864109454176258', '1', '1', '1', '2020-04-11 14:43:01');
INSERT INTO `sys_user_role` VALUES ('1248864109466759169', '1', '2', '1', '2020-04-11 14:43:01');
