USE `matuto_boot`;

-- 初始化-用户表数据
INSERT INTO `sys_user` VALUES (1, 'admin', '管理员', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', 'admin@163.com', '15888888888', '', 1, 0, '127.0.0.1', NOW(), 'admin', NOW(), '', NOW(), '管理员', 0);
INSERT INTO `sys_user` VALUES (2, 'test', '测试用户', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', 'test@163.com', '15666666666', '', 1, 0, '127.0.0.1', NOW(), 'admin', NOW(), '', NOW(), '测试用户', 0);

-- 初始化-角色表数据
INSERT INTO `sys_role` VALUES (1, '超级管理员', 'admin', 1, 0, 'admin', NOW(), '', NOW(), '超级管理员', 0);
INSERT INTO `sys_role` VALUES (2, '普通角色', 'common', 2, 0, 'admin', NOW(), '', NOW(), '普通角色', 0);

-- 初始化-菜单表数据
INSERT INTO `sys_menu` VALUES (1, '系统管理', 0, 1, 'system', NULL, NULL, 0, 0, 'M', '0', '0', '', 'system', 'admin', NOW(), '', NOW(), '系统管理目录', 0);
INSERT INTO `sys_menu` VALUES (2, '系统监控', 0, 2, 'monitor', NULL, NULL, 0, 0, 'M', '0', '0', '', 'monitor', 'admin', NOW(), '', NOW(), '系统监控目录', 0);
INSERT INTO `sys_menu` VALUES (3, '系统工具', 0, 3, 'tool', NULL, NULL, 0, 0, 'M', '0', '0', '', 'tool', 'admin', NOW(), '', NOW(), '系统工具目录', 0);
INSERT INTO `sys_menu` VALUES (100, '用户管理', 1, 1, 'user', 'system/user/index', NULL, 0, 0, 'C', '0', '0', 'system:user:list', 'user', 'admin', NOW(), '', NOW(), '用户管理菜单', 0);
INSERT INTO `sys_menu` VALUES (101, '角色管理', 1, 2, 'role', 'system/role/index', NULL, 0, 0, 'C', '0', '0', 'system:role:list', 'peoples', 'admin', NOW(), '', NOW(), '角色管理菜单', 0);
INSERT INTO `sys_menu` VALUES (102, '菜单管理', 1, 3, 'menu', 'system/menu/index', NULL, 0, 0, 'C', '0', '0', 'system:menu:list', 'tree-table', 'admin', NOW(), '', NOW(), '菜单管理菜单', 0);
INSERT INTO `sys_menu` VALUES (103, '部门管理', 1, 4, 'dept', 'system/dept/index', NULL, 0, 0, 'C', '0', '0', 'system:dept:list', 'tree', 'admin', NOW(), '', NOW(), '部门管理菜单', 0);
INSERT INTO `sys_menu` VALUES (104, '岗位管理', 1, 5, 'post', 'system/post/index', NULL, 0, 0, 'C', '0', '0', 'system:post:list', 'post', 'admin', NOW(), '', NOW(), '岗位管理菜单', 0);
INSERT INTO `sys_menu` VALUES (105, '字典管理', 1, 6, 'dict', 'system/dict/index', NULL, 0, 0, 'C', '0', '0', 'system:dict:list', 'dict', 'admin', NOW(), '', NOW(), '字典管理菜单', 0);
INSERT INTO `sys_menu` VALUES (106, '参数设置', 1, 7, 'config', 'system/config/index', NULL, 0, 0, 'C', '0', '0', 'system:config:list', 'config', 'admin', NOW(), '', NOW(), '参数设置菜单', 0);
INSERT INTO `sys_menu` VALUES (107, '通知公告', 1, 8, 'notice', 'system/notice/index', NULL, 0, 0, 'C', '0', '0', 'system:notice:list', 'message', 'admin', NOW(), '', NOW(), '通知公告菜单', 0);
INSERT INTO `sys_menu` VALUES (108, '日志管理', 1, 9, 'log', '', NULL, 0, 0, 'M', '0', '0', '', 'log', 'admin', NOW(), '', NOW(), '日志管理菜单', 0);
INSERT INTO `sys_menu` VALUES (109, '在线用户', 2, 1, 'online', 'monitor/online/index', NULL, 0, 0, 'C', '0', '0', 'monitor:online:list', 'online', 'admin', NOW(), '', NOW(), '在线用户菜单', 0);
INSERT INTO `sys_menu` VALUES (110, '定时任务', 2, 2, 'job', 'monitor/job/index', NULL, 0, 0, 'C', '0', '0', 'monitor:job:list', 'job', 'admin', NOW(), '', NOW(), '定时任务菜单', 0);
INSERT INTO `sys_menu` VALUES (111, '数据监控', 2, 3, 'druid', 'monitor/druid/index', NULL, 0, 0, 'C', '0', '0', 'monitor:druid:list', 'druid', 'admin', NOW(), '', NOW(), '数据监控菜单', 0);
INSERT INTO `sys_menu` VALUES (112, '服务监控', 2, 4, 'server', 'monitor/server/index', NULL, 0, 0, 'C', '0', '0', 'monitor:server:list', 'server', 'admin', NOW(), '', NOW(), '服务监控菜单', 0);
INSERT INTO `sys_menu` VALUES (113, '缓存监控', 2, 5, 'cache', 'monitor/cache/index', NULL, 0, 0, 'C', '0', '0', 'monitor:cache:list', 'cache', 'admin', NOW(), '', NOW(), '缓存监控菜单', 0);
INSERT INTO `sys_menu` VALUES (114, '表单构建', 3, 1, 'build', 'tool/build/index', NULL, 0, 0, 'C', '0', '0', 'tool:build:list', 'build', 'admin', NOW(), '', NOW(), '表单构建菜单', 0);
INSERT INTO `sys_menu` VALUES (115, '代码生成', 3, 2, 'gen', 'tool/gen/index', NULL, 0, 0, 'C', '0', '0', 'tool:gen:list', 'code', 'admin', NOW(), '', NOW(), '代码生成菜单', 0);
INSERT INTO `sys_menu` VALUES (116, '系统接口', 3, 3, 'swagger', 'tool/swagger/index', NULL, 0, 0, 'C', '0', '0', 'tool:swagger:list', 'swagger', 'admin', NOW(), '', NOW(), '系统接口菜单', 0);

-- 初始化-用户和角色关联表数据
INSERT INTO `sys_user_role` VALUES (1, 1);
INSERT INTO `sys_user_role` VALUES (2, 2);

-- 初始化-角色和菜单关联表数据
INSERT INTO `sys_role_menu` VALUES (1, 1);
INSERT INTO `sys_role_menu` VALUES (1, 2);
INSERT INTO `sys_role_menu` VALUES (1, 3);
INSERT INTO `sys_role_menu` VALUES (1, 100);
INSERT INTO `sys_role_menu` VALUES (1, 101);
INSERT INTO `sys_role_menu` VALUES (1, 102);
INSERT INTO `sys_role_menu` VALUES (1, 103);
INSERT INTO `sys_role_menu` VALUES (1, 104);
INSERT INTO `sys_role_menu` VALUES (1, 105);
INSERT INTO `sys_role_menu` VALUES (1, 106);
INSERT INTO `sys_role_menu` VALUES (1, 107);
INSERT INTO `sys_role_menu` VALUES (1, 108);
INSERT INTO `sys_role_menu` VALUES (1, 109);
INSERT INTO `sys_role_menu` VALUES (1, 110);
INSERT INTO `sys_role_menu` VALUES (1, 111);
INSERT INTO `sys_role_menu` VALUES (1, 112);
INSERT INTO `sys_role_menu` VALUES (1, 113);
INSERT INTO `sys_role_menu` VALUES (1, 114);
INSERT INTO `sys_role_menu` VALUES (1, 115);
INSERT INTO `sys_role_menu` VALUES (1, 116);
INSERT INTO `sys_role_menu` VALUES (2, 1);
INSERT INTO `sys_role_menu` VALUES (2, 2);
INSERT INTO `sys_role_menu` VALUES (2, 3);
INSERT INTO `sys_role_menu` VALUES (2, 100);
INSERT INTO `sys_role_menu` VALUES (2, 101);
INSERT INTO `sys_role_menu` VALUES (2, 102);
INSERT INTO `sys_role_menu` VALUES (2, 103);
INSERT INTO `sys_role_menu` VALUES (2, 104);
INSERT INTO `sys_role_menu` VALUES (2, 105);
INSERT INTO `sys_role_menu` VALUES (2, 106);
INSERT INTO `sys_role_menu` VALUES (2, 107);
INSERT INTO `sys_role_menu` VALUES (2, 108);
INSERT INTO `sys_role_menu` VALUES (2, 109);
INSERT INTO `sys_role_menu` VALUES (2, 110);
INSERT INTO `sys_role_menu` VALUES (2, 111);
INSERT INTO `sys_role_menu` VALUES (2, 112);
INSERT INTO `sys_role_menu` VALUES (2, 113);
INSERT INTO `sys_role_menu` VALUES (2, 114);
INSERT INTO `sys_role_menu` VALUES (2, 115);
INSERT INTO `sys_role_menu` VALUES (2, 116);

-- 初始化-字典类型表数据
INSERT INTO `sys_dict_type` VALUES (1, '用户性别', 'sys_user_sex', 0, 'admin', NOW(), '', NOW(), '性别字典', 0);
INSERT INTO `sys_dict_type` VALUES (2, '菜单状态', 'sys_show_hide', 0, 'admin', NOW(), '', NOW(), '菜单状态列表', 0);
INSERT INTO `sys_dict_type` VALUES (3, '系统开关', 'sys_normal_disable', 0, 'admin', NOW(), '', NOW(), '系统开关列表', 0);
INSERT INTO `sys_dict_type` VALUES (4, '任务状态', 'sys_job_status', 0, 'admin', NOW(), '', NOW(), '任务状态列表', 0);
INSERT INTO `sys_dict_type` VALUES (5, '系统是否', 'sys_yes_no', 0, 'admin', NOW(), '', NOW(), '系统是否列表', 0);
INSERT INTO `sys_dict_type` VALUES (6, '通知类型', 'sys_notice_type', 0, 'admin', NOW(), '', NOW(), '通知类型列表', 0);
INSERT INTO `sys_dict_type` VALUES (7, '通知状态', 'sys_notice_status', 0, 'admin', NOW(), '', NOW(), '通知状态列表', 0);
INSERT INTO `sys_dict_type` VALUES (8, '操作类型', 'sys_oper_type', 0, 'admin', NOW(), '', NOW(), '操作类型列表', 0);
INSERT INTO `sys_dict_type` VALUES (9, '系统状态', 'sys_common_status', 0, 'admin', NOW(), '', NOW(), '登录状态列表', 0);

-- 初始化-字典数据表数据
INSERT INTO `sys_dict_data` VALUES (1, 1, '男', '0', 'sys_user_sex', '', '', 'Y', 0, 'admin', NOW(), '', NOW(), '性别男', 0);
INSERT INTO `sys_dict_data` VALUES (2, 2, '女', '1', 'sys_user_sex', '', '', 'N', 0, 'admin', NOW(), '', NOW(), '性别女', 0);
INSERT INTO `sys_dict_data` VALUES (3, 3, '未知', '2', 'sys_user_sex', '', '', 'N', 0, 'admin', NOW(), '', NOW(), '性别未知', 0);
INSERT INTO `sys_dict_data` VALUES (4, 1, '显示', '0', 'sys_show_hide', '', 'primary', 'Y', 0, 'admin', NOW(), '', NOW(), '显示菜单', 0);
INSERT INTO `sys_dict_data` VALUES (5, 2, '隐藏', '1', 'sys_show_hide', '', 'danger', 'N', 0, 'admin', NOW(), '', NOW(), '隐藏菜单', 0);
INSERT INTO `sys_dict_data` VALUES (6, 1, '正常', '0', 'sys_normal_disable', '', 'primary', 'Y', 0, 'admin', NOW(), '', NOW(), '正常状态', 0);
INSERT INTO `sys_dict_data` VALUES (7, 2, '停用', '1', 'sys_normal_disable', '', 'danger', 'N', 0, 'admin', NOW(), '', NOW(), '停用状态', 0);
INSERT INTO `sys_dict_data` VALUES (8, 1, '正常', '0', 'sys_job_status', '', 'primary', 'Y', 0, 'admin', NOW(), '', NOW(), '正常状态', 0);
INSERT INTO `sys_dict_data` VALUES (9, 2, '暂停', '1', 'sys_job_status', '', 'danger', 'N', 0, 'admin', NOW(), '', NOW(), '停用状态', 0);
INSERT INTO `sys_dict_data` VALUES (10, 1, '是', 'Y', 'sys_yes_no', '', 'primary', 'Y', 0, 'admin', NOW(), '', NOW(), '系统默认是', 0);
INSERT INTO `sys_dict_data` VALUES (11, 2, '否', 'N', 'sys_yes_no', '', 'danger', 'N', 0, 'admin', NOW(), '', NOW(), '系统默认否', 0);
INSERT INTO `sys_dict_data` VALUES (12, 1, '通知', '1', 'sys_notice_type', '', 'warning', 'Y', 0, 'admin', NOW(), '', NOW(), '通知', 0);
INSERT INTO `sys_dict_data` VALUES (13, 2, '公告', '2', 'sys_notice_type', '', 'success', 'N', 0, 'admin', NOW(), '', NOW(), '公告', 0);
INSERT INTO `sys_dict_data` VALUES (14, 1, '正常', '0', 'sys_notice_status', '', 'primary', 'Y', 0, 'admin', NOW(), '', NOW(), '正常状态', 0);
INSERT INTO `sys_dict_data` VALUES (15, 2, '关闭', '1', 'sys_notice_status', '', 'danger', 'N', 0, 'admin', NOW(), '', NOW(), '关闭状态', 0);
INSERT INTO `sys_dict_data` VALUES (16, 1, '新增', '1', 'sys_oper_type', '', 'info', 'N', 0, 'admin', NOW(), '', NOW(), '新增操作', 0);
INSERT INTO `sys_dict_data` VALUES (17, 2, '修改', '2', 'sys_oper_type', '', 'info', 'N', 0, 'admin', NOW(), '', NOW(), '修改操作', 0);
INSERT INTO `sys_dict_data` VALUES (18, 3, '删除', '3', 'sys_oper_type', '', 'danger', 'N', 0, 'admin', NOW(), '', NOW(), '删除操作', 0);
INSERT INTO `sys_dict_data` VALUES (19, 4, '授权', '4', 'sys_oper_type', '', 'primary', 'N', 0, 'admin', NOW(), '', NOW(), '授权操作', 0);
INSERT INTO `sys_dict_data` VALUES (20, 5, '导出', '5', 'sys_oper_type', '', 'warning', 'N', 0, 'admin', NOW(), '', NOW(), '导出操作', 0);
INSERT INTO `sys_dict_data` VALUES (21, 6, '导入', '6', 'sys_oper_type', '', 'warning', 'N', 0, 'admin', NOW(), '', NOW(), '导入操作', 0);
INSERT INTO `sys_dict_data` VALUES (22, 7, '强退', '7', 'sys_oper_type', '', 'danger', 'N', 0, 'admin', NOW(), '', NOW(), '强退操作', 0);
INSERT INTO `sys_dict_data` VALUES (23, 8, '生成代码', '8', 'sys_oper_type', '', 'warning', 'N', 0, 'admin', NOW(), '', NOW(), '生成操作', 0);
INSERT INTO `sys_dict_data` VALUES (24, 9, '清空数据', '9', 'sys_oper_type', '', 'danger', 'N', 0, 'admin', NOW(), '', NOW(), '清空操作', 0);
INSERT INTO `sys_dict_data` VALUES (25, 1, '成功', '0', 'sys_common_status', '', 'primary', 'N', 0, 'admin', NOW(), '', NOW(), '正常状态', 0);
INSERT INTO `sys_dict_data` VALUES (26, 2, '失败', '1', 'sys_common_status', '', 'danger', 'N', 0, 'admin', NOW(), '', NOW(), '停用状态', 0);

-- 初始化-系统配置表数据
INSERT INTO `sys_config` VALUES (1, '用户管理-账号初始密码', 'sys.user.initPassword', '123456', 'Y', 'admin', NOW(), '', NOW(), '初始化密码 123456', 0);
INSERT INTO `sys_config` VALUES (2, '用户管理-密码字符范围', 'sys.account.charset', 'abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789', 'Y', 'admin', NOW(), '', NOW(), '密码字符范围', 0);
INSERT INTO `sys_config` VALUES (3, '用户管理-初始密码修改策略', 'sys.account.initPasswordModify', '0', 'Y', 'admin', NOW(), '', NOW(), '0：初始密码修改策略关闭, 1：开启', 0);
INSERT INTO `sys_config` VALUES (4, '用户管理-账号密码-默认密码', 'sys.account.defaultPassword', '123456', 'Y', 'admin', NOW(), '', NOW(), '账号默认密码', 0);
INSERT INTO `sys_config` VALUES (5, '用户管理-账号密码-密码最小长度', 'sys.account.passwordMinLength', '5', 'Y', 'admin', NOW(), '', NOW(), '密码最小长度', 0);
INSERT INTO `sys_config` VALUES (6, '用户管理-账号密码-密码组成类型', 'sys.account.passwordStartWith', '0', 'Y', 'admin', NOW(), '', NOW(), '密码组成类型', 0);
INSERT INTO `sys_config` VALUES (7, '主框架页-默认皮肤', 'sys.index.skinName', 'skin-blue', 'Y', 'admin', NOW(), '', NOW(), '蓝色 skin-blue、绿色 skin-green、紫色 skin-purple、红色 skin-red、黄色 skin-yellow', 0);
INSERT INTO `sys_config` VALUES (8, '用户管理-账号密码-密码更新周期', 'sys.account.passwordValidateDays', '0', 'Y', 'admin', NOW(), '', NOW(), '密码更新周期（开启密码有效期限制）', 0);
INSERT INTO `sys_config` VALUES (9, '系统生成代码-默认作者', 'gen.author', 'Matuto', 'Y', 'admin', NOW(), '', NOW(), '代码生成默认作者', 0);
INSERT INTO `sys_config` VALUES (10, '系统生成代码-默认包前缀', 'gen.packageName', 'com.matuto', 'Y', 'admin', NOW(), '', NOW(), '代码生成默认包前缀', 0);
INSERT INTO `sys_config` VALUES (11, '系统生成代码-默认模块名', 'gen.moduleName', 'system', 'Y', 'admin', NOW(), '', NOW(), '代码生成默认模块名', 0);
INSERT INTO `sys_config` VALUES (12, '系统生成代码-默认生成路径', 'gen.genPath', '/', 'Y', 'admin', NOW(), '', NOW(), '代码生成默认生成路径', 0);
INSERT INTO `sys_config` VALUES (13, '系统生成代码-默认生成方式', 'gen.genType', '0', 'Y', 'admin', NOW(), '', NOW(), '代码生成默认生成方式', 0);
INSERT INTO `sys_config` VALUES (14, '系统生成代码-默认生成功能名', 'gen.functionName', '单表（增删改查）', 'Y', 'admin', NOW(), '', NOW(), '代码生成默认生成功能名', 0);
INSERT INTO `sys_config` VALUES (15, '系统生成代码-默认生成功能作者', 'gen.functionAuthor', 'Matuto', 'Y', 'admin', NOW(), '', NOW(), '代码生成默认生成功能作者', 0);
INSERT INTO `sys_config` VALUES (16, '系统生成代码-默认生成功能描述', 'gen.functionDesc', '单表（增删改查）', 'Y', 'admin', NOW(), '', NOW(), '代码生成默认生成功能描述', 0);
INSERT INTO `sys_config` VALUES (17, '系统生成代码-默认生成功能包名', 'gen.functionPackage', 'com.matuto.system', 'Y', 'admin', NOW(), '', NOW(), '代码生成默认生成功能包名', 0);
INSERT INTO `sys_config` VALUES (18, '系统生成代码-默认生成功能模块名', 'gen.functionModule', 'system', 'Y', 'admin', NOW(), '', NOW(), '代码生成默认生成功能模块名', 0);
INSERT INTO `sys_config` VALUES (19, '系统生成代码-默认生成功能表前缀', 'gen.functionTablePrefix', 'sys_', 'Y', 'admin', NOW(), '', NOW(), '代码生成默认生成功能表前缀', 0);
INSERT INTO `sys_config` VALUES (20, '系统生成代码-默认生成功能表注释', 'gen.functionTableComment', '系统表', 'Y', 'admin', NOW(), '', NOW(), '代码生成默认生成功能表注释', 0); 