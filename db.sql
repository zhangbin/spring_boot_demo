
CREATE TABLE `permission` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50) NOT NULL COMMENT '权限名称',
  `permission` VARCHAR(200) NOT NULL COMMENT '权限名称 例如：user:read',
  `resource_type` VARCHAR(20) NOT NULL COMMENT '权限类别',
  `available` INT(11) DEFAULT NULL,
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `permission` (`permission`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

INSERT INTO `permission` (`id`, `name`, `permission`, `resource_type`, `available`)
VALUES
	(1,'读取用户','user:read','Read',1),
	(2,'创建用户','user:create','create',1),
	(3,'编辑用户','user:edit','edit',1),
	(4,'查看用户','user:show','Read',1);

CREATE TABLE `role` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50) NOT NULL COMMENT '角色名称(英文)',
  `content` VARCHAR(50) NOT NULL COMMENT '角色名称',
  `state` INT(11) DEFAULT '1' COMMENT '状态1：可用 0：禁用',
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`),
  UNIQUE KEY `content` (`content`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

INSERT INTO `role` (`id`, `name`, `content`, `state`)
VALUES
	(1,'admin','管理员',1),
	(2,'user','用户',1);

CREATE TABLE `roles_permissions` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `role_id` INT(11) NOT NULL,
  `permission_id` INT(11) NOT NULL,
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

INSERT INTO `roles_permissions` (`id`, `role_id`, `permission_id`)
VALUES
	(1,1,1),
	(2,1,2);


CREATE TABLE `user` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(50) NOT NULL COMMENT '用户名',
  `password` VARCHAR(200) NOT NULL COMMENT '密码',
  `salt` VARCHAR(200) NOT NULL COMMENT '加密盐',
  `alive` BIT(1) DEFAULT b'1' COMMENT '是否激活',
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '编辑时间',
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;


INSERT INTO `user` (`id`, `username`, `password`, `salt`, `alive`)
VALUES
	(1,'admin','fed29ec5b518eacc1d414c33f1dd641e','YWRtaW5hZG1pbg==',b'1');

CREATE TABLE `users_roles` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` INT(11) NOT NULL COMMENT '用户id',
  `role_id` INT(11) NOT NULL COMMENT '角色id',
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


INSERT INTO `users_roles` (`id`, `user_id`, `role_id`)
VALUES
	(1,1,1),
	(2,1,2);

