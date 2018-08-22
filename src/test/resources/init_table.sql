DROP TABLE IF EXISTS `user`;
DROP TABLE IF EXISTS `role`;
DROP TABLE IF EXISTS `users_roles`;
DROP TABLE IF EXISTS `permission`;
DROP TABLE IF EXISTS `roles_permissions`;

CREATE TABLE `user` (
  `id`  INT(11) UNSIGNED NOT NULL  AUTO_INCREMENT,
  `username`  VARCHAR(50)  NOT NULL  COMMENT '用户名',
  `password`  VARCHAR(200)  NOT NULL  COMMENT '密码',
  `salt`  VARCHAR(200)  NOT NULL  COMMENT '加密盐',
  `alive`  BIT  DEFAULT 1  COMMENT '是否激活',
  `created_at`  DATETIME  DEFAULT CURRENT_TIMESTAMP  COMMENT '创建时间',
  `updated_at`  DATETIME  DEFAULT CURRENT_TIMESTAMP  COMMENT '编辑时间',
  PRIMARY KEY (`id`)
);

CREATE TABLE `role` (
  `id`  INT(11) UNSIGNED NOT NULL  AUTO_INCREMENT,
  `name`  VARCHAR(50)  NOT NULL COMMENT '角色名称(英文)' UNIQUE,
  `content` VARCHAR(50)  NOT NULL COMMENT '角色名称' UNIQUE,
  `state`  INT(11)  DEFAULT 1 COMMENT '状态1：可用 0：禁用',
  `created_at`  DATETIME  DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at`  DATETIME  DEFAULT CURRENT_TIMESTAMP COMMENT '编辑时间',
  PRIMARY KEY (`id`)
);

CREATE TABLE `users_roles` (
  `id`  INT(11) UNSIGNED NOT NULL  AUTO_INCREMENT,
  `role_id`  INT(11)  NOT NULL,
  `user_id`  INT(11)  NOT NULL,
  `created_at` DATETIME  DEFAULT CURRENT_TIMESTAMP  COMMENT '创建时间',
  `updated_at`  DATETIME  DEFAULT CURRENT_TIMESTAMP  COMMENT '编辑时间',
  PRIMARY KEY (`id`)
);

CREATE TABLE `permission` (
  `id`  INT(11) UNSIGNED NOT NULL  AUTO_INCREMENT,
  `name`  VARCHAR(50)  NOT NULL  COMMENT '权限名称',
  `permission`  VARCHAR(200)  NOT NULL  COMMENT '权限名称 例如：user:read' UNIQUE,
  `resource_type` VARCHAR(20)  NOT NULL  COMMENT '权限类别',
  `available`  INT(11)  DEFAULT NULL,
  `created_at`  DATETIME  DEFAULT CURRENT_TIMESTAMP  COMMENT '创建时间',
  `updated_at`  DATETIME  DEFAULT CURRENT_TIMESTAMP  COMMENT '编辑时间',
  PRIMARY KEY (`id`)
);

CREATE TABLE `roles_permissions` (
  `id`  INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `role_id`  INT(11)  NOT NULL,
  `permission_id` INT(11)  NOT NULL,
  `created_at`  DATETIME  DEFAULT CURRENT_TIMESTAMP  COMMENT '创建时间',
  `updated_at`  DATETIME  DEFAULT CURRENT_TIMESTAMP  COMMENT '编辑时间',
  PRIMARY KEY (`id`)
);

INSERT INTO `role` (`id`, `name`, `content`, `state`)
VALUES
  (1, 'admin', '管理员', 1),
  (2, 'manager', '客户经理', 1),
  (3, 'driver', '司机', 1);

-- admin/admin
INSERT INTO `user` (`id`, `username`, `password`, `alive`, salt)
VALUES
  (1, 'admin', 'ffba7c673c2d5b79e4e0dd310f400c47', TRUE, 'YWRtaW5hZG1pbg=='),
  (2, 'user', '13111112222', TRUE, 'salt');


INSERT INTO `permission` (`id`, `name`, `permission`, `resource_type`, `available`)
VALUES
  (1, '读取用户', 'user:read', 'read', 1),
  (2, '注册用户', 'user:create', 'create', 1),
  (3, '编辑用户', 'user:edit', 'edit', 1);



