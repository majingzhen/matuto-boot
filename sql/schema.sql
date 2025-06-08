-- 创建数据库
CREATE DATABASE IF NOT EXISTS `matuto_boot` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

USE `matuto_boot`;

-- 用户表
CREATE TABLE IF NOT EXISTS `sys_user` (
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '用户ID',
    `username` VARCHAR(30) NOT NULL COMMENT '用户账号',
    `nickname` VARCHAR(30) NOT NULL COMMENT '用户昵称',
    `password` VARCHAR(100) NOT NULL COMMENT '密码',
    `email` VARCHAR(50) DEFAULT '' COMMENT '用户邮箱',
    `phone` VARCHAR(20) DEFAULT '' COMMENT '手机号码',
    `avatar` VARCHAR(255) DEFAULT '' COMMENT '头像地址',
    `gender` TINYINT(1) DEFAULT 0 COMMENT '用户性别 (0-未知 1-男 2-女)',
    `status` TINYINT(1) DEFAULT 0 COMMENT '帐号状态 (0-正常 1-停用)',
    `login_ip` VARCHAR(50) DEFAULT '' COMMENT '最后登录IP',
    `login_date` DATETIME NULL COMMENT '最后登录时间',
    `create_by` VARCHAR(64) DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by` VARCHAR(64) DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
    `deleted` TINYINT(1) DEFAULT 0 COMMENT '逻辑删除标志 (0-未删除 1-已删除)',
    PRIMARY KEY (`id`),
    UNIQUE KEY `idx_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 角色表
CREATE TABLE IF NOT EXISTS `sys_role` (
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '角色ID',
    `role_name` VARCHAR(30) NOT NULL COMMENT '角色名称',
    `role_key` VARCHAR(100) NOT NULL COMMENT '角色权限字符串',
    `role_sort` INT DEFAULT 0 COMMENT '显示顺序',
    `status` TINYINT(1) DEFAULT 0 COMMENT '角色状态 (0-正常 1-停用)',
    `create_by` VARCHAR(64) DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by` VARCHAR(64) DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
    `deleted` TINYINT(1) DEFAULT 0 COMMENT '逻辑删除标志 (0-未删除 1-已删除)',
    PRIMARY KEY (`id`),
    UNIQUE KEY `idx_role_key` (`role_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色表';

-- 菜单表
CREATE TABLE IF NOT EXISTS `sys_menu` (
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
    `menu_name` VARCHAR(50) NOT NULL COMMENT '菜单名称',
    `parent_id` BIGINT UNSIGNED DEFAULT 0 COMMENT '父菜单ID',
    `order_num` INT DEFAULT 0 COMMENT '显示顺序',
    `path` VARCHAR(200) DEFAULT '' COMMENT '路由地址',
    `component` VARCHAR(255) DEFAULT NULL COMMENT '组件路径',
    `query` VARCHAR(255) DEFAULT NULL COMMENT '路由参数',
    `is_frame` TINYINT(1) DEFAULT 0 COMMENT '是否为外链 (0-否 1-是)',
    `is_cache` TINYINT(1) DEFAULT 0 COMMENT '是否缓存 (0-不缓存 1-缓存)',
    `menu_type` CHAR(1) DEFAULT '' COMMENT '菜单类型 (M-目录 C-菜单 F-按钮)',
    `visible` CHAR(1) DEFAULT '0' COMMENT '菜单状态 (0-显示 1-隐藏)',
    `status` CHAR(1) DEFAULT '0' COMMENT '菜单状态 (0-正常 1-停用)',
    `perms` VARCHAR(100) DEFAULT NULL COMMENT '权限标识',
    `icon` VARCHAR(100) DEFAULT '#' COMMENT '菜单图标',
    `create_by` VARCHAR(64) DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by` VARCHAR(64) DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
    `deleted` TINYINT(1) DEFAULT 0 COMMENT '逻辑删除标志 (0-未删除 1-已删除)',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='菜单表';

-- 用户角色关联表
CREATE TABLE IF NOT EXISTS `sys_user_role` (
    `user_id` BIGINT UNSIGNED NOT NULL COMMENT '用户ID',
    `role_id` BIGINT UNSIGNED NOT NULL COMMENT '角色ID',
    PRIMARY KEY (`user_id`, `role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户角色关联表';

-- 角色菜单关联表
CREATE TABLE IF NOT EXISTS `sys_role_menu` (
    `role_id` BIGINT UNSIGNED NOT NULL COMMENT '角色ID',
    `menu_id` BIGINT UNSIGNED NOT NULL COMMENT '菜单ID',
    PRIMARY KEY (`role_id`, `menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色菜单关联表';

-- 字典类型表
CREATE TABLE IF NOT EXISTS `sys_dict_type` (
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '字典主键',
    `dict_name` VARCHAR(100) DEFAULT '' COMMENT '字典名称',
    `dict_type` VARCHAR(100) DEFAULT '' COMMENT '字典类型',
    `status` TINYINT(1) DEFAULT 0 COMMENT '状态 (0-正常 1-停用)',
    `create_by` VARCHAR(64) DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by` VARCHAR(64) DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
    `deleted` TINYINT(1) DEFAULT 0 COMMENT '逻辑删除标志 (0-未删除 1-已删除)',
    PRIMARY KEY (`id`),
    UNIQUE KEY `idx_dict_type` (`dict_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='字典类型表';

-- 字典数据表
CREATE TABLE IF NOT EXISTS `sys_dict_data` (
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '字典编码',
    `dict_sort` INT DEFAULT 0 COMMENT '字典排序',
    `dict_label` VARCHAR(100) DEFAULT '' COMMENT '字典标签',
    `dict_value` VARCHAR(100) DEFAULT '' COMMENT '字典键值',
    `dict_type` VARCHAR(100) DEFAULT '' COMMENT '字典类型',
    `css_class` VARCHAR(100) DEFAULT NULL COMMENT '样式属性',
    `list_class` VARCHAR(100) DEFAULT NULL COMMENT '表格回显样式',
    `is_default` CHAR(1) DEFAULT 'N' COMMENT '是否默认 (Y是 N否)',
    `status` TINYINT(1) DEFAULT 0 COMMENT '状态 (0-正常 1-停用)',
    `create_by` VARCHAR(64) DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by` VARCHAR(64) DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
    `deleted` TINYINT(1) DEFAULT 0 COMMENT '逻辑删除标志 (0-未删除 1-已删除)',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='字典数据表';

-- 系统配置表
CREATE TABLE IF NOT EXISTS `sys_config` (
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '参数主键',
    `config_name` VARCHAR(100) DEFAULT '' COMMENT '参数名称',
    `config_key` VARCHAR(100) DEFAULT '' COMMENT '参数键名',
    `config_value` VARCHAR(500) DEFAULT '' COMMENT '参数键值',
    `config_type` CHAR(1) DEFAULT 'N' COMMENT '系统内置 (Y是 N否)',
    `create_by` VARCHAR(64) DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by` VARCHAR(64) DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
    `deleted` TINYINT(1) DEFAULT 0 COMMENT '逻辑删除标志 (0-未删除 1-已删除)',
    PRIMARY KEY (`id`),
    UNIQUE KEY `idx_config_key` (`config_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统配置表';

-- 附件表
CREATE TABLE IF NOT EXISTS `sys_attachment` (
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '附件ID',
    `file_name` VARCHAR(255) NOT NULL COMMENT '文件名',
    `file_path` VARCHAR(500) NOT NULL COMMENT '文件存储路径',
    `file_size` BIGINT DEFAULT 0 COMMENT '文件大小',
    `file_suffix` VARCHAR(20) DEFAULT '' COMMENT '文件后缀名',
    `mime_type` VARCHAR(100) DEFAULT '' COMMENT 'MIME类型',
    `storage_type` VARCHAR(20) NOT NULL COMMENT '存储类型',
    `user_id` BIGINT UNSIGNED NULL COMMENT '上传用户ID',
    `create_by` VARCHAR(64) DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by` VARCHAR(64) DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
    `deleted` TINYINT(1) DEFAULT 0 COMMENT '逻辑删除标志 (0-未删除 1-已删除)',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='附件表';

-- 操作日志表
CREATE TABLE IF NOT EXISTS `sys_oper_log` (
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '日志主键',
    `title` VARCHAR(50) DEFAULT '' COMMENT '模块标题',
    `business_type` TINYINT DEFAULT 0 COMMENT '业务类型',
    `method` VARCHAR(100) DEFAULT '' COMMENT '方法名称',
    `request_method` VARCHAR(10) DEFAULT '' COMMENT '请求方式',
    `operator_type` TINYINT DEFAULT 0 COMMENT '操作类别',
    `oper_name` VARCHAR(50) DEFAULT '' COMMENT '操作人员',
    `dept_name` VARCHAR(50) DEFAULT '' COMMENT '部门名称',
    `oper_url` VARCHAR(255) DEFAULT '' COMMENT '请求URL',
    `oper_ip` VARCHAR(128) DEFAULT '' COMMENT '主机地址',
    `oper_location` VARCHAR(255) DEFAULT '' COMMENT '操作地点',
    `oper_param` VARCHAR(2000) DEFAULT '' COMMENT '请求参数',
    `json_result` VARCHAR(2000) DEFAULT '' COMMENT '返回参数',
    `status` TINYINT DEFAULT 0 COMMENT '操作状态',
    `error_msg` VARCHAR(2000) DEFAULT '' COMMENT '错误消息',
    `oper_time` DATETIME NULL COMMENT '操作时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='操作日志表';

-- 登录日志表
CREATE TABLE IF NOT EXISTS `sys_login_log` (
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '访问ID',
    `user_name` VARCHAR(50) DEFAULT '' COMMENT '用户账号',
    `ipaddr` VARCHAR(128) DEFAULT '' COMMENT '登录IP地址',
    `login_location` VARCHAR(255) DEFAULT '' COMMENT '登录地点',
    `browser` VARCHAR(50) DEFAULT '' COMMENT '浏览器类型',
    `os` VARCHAR(50) DEFAULT '' COMMENT '操作系统',
    `status` TINYINT DEFAULT 0 COMMENT '登录状态',
    `msg` VARCHAR(255) DEFAULT '' COMMENT '提示消息',
    `login_time` DATETIME NULL COMMENT '访问时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='登录日志表';

-- 在线用户表
CREATE TABLE IF NOT EXISTS `sys_user_online` (
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '会话编号',
    `token_id` VARCHAR(100) DEFAULT '' COMMENT '用户会话id',
    `user_name` VARCHAR(50) DEFAULT '' COMMENT '用户账号',
    `dept_name` VARCHAR(50) DEFAULT '' COMMENT '部门名称',
    `ipaddr` VARCHAR(128) DEFAULT '' COMMENT '登录IP地址',
    `login_location` VARCHAR(255) DEFAULT '' COMMENT '登录地点',
    `browser` VARCHAR(50) DEFAULT '' COMMENT '浏览器类型',
    `os` VARCHAR(50) DEFAULT '' COMMENT '操作系统',
    `login_time` DATETIME NULL COMMENT '登录时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='在线用户表'; 