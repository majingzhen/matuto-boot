-- 菜单表
CREATE TABLE `sys_menu` (
  `menu_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `menu_name` varchar(50) NOT NULL COMMENT '菜单名称',
  `parent_id` bigint(20) DEFAULT '0' COMMENT '父菜单ID',
  `order_num` int(4) DEFAULT '0' COMMENT '显示顺序',
  `path` varchar(200) DEFAULT '' COMMENT '路由地址',
  `component` varchar(255) DEFAULT NULL COMMENT '组件路径',
  `query` varchar(255) DEFAULT NULL COMMENT '路由参数',
  `is_frame` char(1) DEFAULT '1' COMMENT '是否为外链（0是 1否）',
  `is_cache` char(1) DEFAULT '0' COMMENT '是否缓存（0缓存 1不缓存）',
  `menu_type` char(1) DEFAULT '' COMMENT '菜单类型（M目录 C菜单 F按钮）',
  `visible` char(1) DEFAULT '0' COMMENT '菜单状态（0显示 1隐藏）',
  `status` char(1) DEFAULT '0' COMMENT '菜单状态（0正常 1停用）',
  `perms` varchar(100) DEFAULT NULL COMMENT '权限标识',
  `icon` varchar(100) DEFAULT '#' COMMENT '菜单图标',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2000 DEFAULT CHARSET=utf8mb4 COMMENT='菜单权限表';

-- 初始化-菜单信息表数据
INSERT INTO `sys_menu` VALUES ('1', '系统管理', '0', '1', 'system', NULL, NULL, '1', '0', 'M', '0', '0', '', 'system', 'admin', SYSDATE(), '', NULL, '系统管理目录');
INSERT INTO `sys_menu` VALUES ('2', '系统监控', '0', '2', 'monitor', NULL, NULL, '1', '0', 'M', '0', '0', '', 'monitor', 'admin', SYSDATE(), '', NULL, '系统监控目录');
INSERT INTO `sys_menu` VALUES ('3', '系统工具', '0', '3', 'tool', NULL, NULL, '1', '0', 'M', '0', '0', '', 'tool', 'admin', SYSDATE(), '', NULL, '系统工具目录');
INSERT INTO `sys_menu` VALUES ('100', '用户管理', '1', '1', 'user', 'system/user/index', NULL, '1', '0', 'C', '0', '0', 'system:user:list', 'user', 'admin', SYSDATE(), '', NULL, '用户管理菜单');
INSERT INTO `sys_menu` VALUES ('101', '角色管理', '1', '2', 'role', 'system/role/index', NULL, '1', '0', 'C', '0', '0', 'system:role:list', 'peoples', 'admin', SYSDATE(), '', NULL, '角色管理菜单');
INSERT INTO `sys_menu` VALUES ('102', '菜单管理', '1', '3', 'menu', 'system/menu/index', NULL, '1', '0', 'C', '0', '0', 'system:menu:list', 'tree-table', 'admin', SYSDATE(), '', NULL, '菜单管理菜单');
INSERT INTO `sys_menu` VALUES ('103', '部门管理', '1', '4', 'dept', 'system/dept/index', NULL, '1', '0', 'C', '0', '0', 'system:dept:list', 'tree', 'admin', SYSDATE(), '', NULL, '部门管理菜单');
INSERT INTO `sys_menu` VALUES ('104', '岗位管理', '1', '5', 'post', 'system/post/index', NULL, '1', '0', 'C', '0', '0', 'system:post:list', 'post', 'admin', SYSDATE(), '', NULL, '岗位管理菜单');
INSERT INTO `sys_menu` VALUES ('105', '字典管理', '1', '6', 'dict', 'system/dict/index', NULL, '1', '0', 'C', '0', '0', 'system:dict:list', 'dict', 'admin', SYSDATE(), '', NULL, '字典管理菜单');
INSERT INTO `sys_menu` VALUES ('106', '参数设置', '1', '7', 'config', 'system/config/index', NULL, '1', '0', 'C', '0', '0', 'system:config:list', 'config', 'admin', SYSDATE(), '', NULL, '参数设置菜单');
INSERT INTO `sys_menu` VALUES ('107', '通知公告', '1', '8', 'notice', 'system/notice/index', NULL, '1', '0', 'C', '0', '0', 'system:notice:list', 'message', 'admin', SYSDATE(), '', NULL, '通知公告菜单');
INSERT INTO `sys_menu` VALUES ('108', '日志管理', '1', '9', 'log', '', NULL, '1', '0', 'M', '0', '0', '', 'log', 'admin', SYSDATE(), '', NULL, '日志管理菜单');
INSERT INTO `sys_menu` VALUES ('109', '在线用户', '2', '1', 'online', 'monitor/online/index', NULL, '1', '0', 'C', '0', '0', 'monitor:online:list', 'online', 'admin', SYSDATE(), '', NULL, '在线用户菜单');
INSERT INTO `sys_menu` VALUES ('110', '定时任务', '2', '2', 'job', 'monitor/job/index', NULL, '1', '0', 'C', '0', '0', 'monitor:job:list', 'job', 'admin', SYSDATE(), '', NULL, '定时任务菜单');
INSERT INTO `sys_menu` VALUES ('111', '数据监控', '2', '3', 'druid', 'monitor/druid/index', NULL, '1', '0', 'C', '0', '0', 'monitor:druid:list', 'druid', 'admin', SYSDATE(), '', NULL, '数据监控菜单');
INSERT INTO `sys_menu` VALUES ('112', '服务监控', '2', '4', 'server', 'monitor/server/index', NULL, '1', '0', 'C', '0', '0', 'monitor:server:list', 'server', 'admin', SYSDATE(), '', NULL, '服务监控菜单');
INSERT INTO `sys_menu` VALUES ('113', '缓存监控', '2', '5', 'cache', 'monitor/cache/index', NULL, '1', '0', 'C', '0', '0', 'monitor:cache:list', 'cache', 'admin', SYSDATE(), '', NULL, '缓存监控菜单');
INSERT INTO `sys_menu` VALUES ('114', '表单构建', '3', '1', 'build', 'tool/build/index', NULL, '1', '0', 'C', '0', '0', 'tool:build:list', 'build', 'admin', SYSDATE(), '', NULL, '表单构建菜单');
INSERT INTO `sys_menu` VALUES ('115', '代码生成', '3', '2', 'gen', 'tool/gen/index', NULL, '1', '0', 'C', '0', '0', 'tool:gen:list', 'code', 'admin', SYSDATE(), '', NULL, '代码生成菜单');
INSERT INTO `sys_menu` VALUES ('116', '系统接口', '3', '3', 'swagger', 'tool/swagger/index', NULL, '1', '0', 'C', '0', '0', 'tool:swagger:list', 'swagger', 'admin', SYSDATE(), '', NULL, '系统接口菜单'); 