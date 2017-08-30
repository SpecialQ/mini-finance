CREATE TABLE IF NOT EXISTS `mini-finance`.`user` (
  `id` BIGINT NOT NULL,
  `username` VARCHAR(32) NOT NULL,
  `password` VARCHAR(128) NOT NULL,
  `create_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `enabled` TINYINT NOT NULL DEFAULT 0 COMMENT 'o - 未激活\n1 - 已激活',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `user_username_UNIQUE` (`username` ASC));
  
INSERT INTO `mini-finance`.`user` (`id`, `username`, `password`, `create_time`, `enabled`) VALUES (0, 'admin', '19871129', now(), 1);

CREATE TABLE IF NOT EXISTS `mini-finance`.`user_ext` (
  `user_id` BIGINT NOT NULL,
  `phone` VARCHAR(32) NULL,
  `email` VARCHAR(32) NULL,
  `address` VARCHAR(64) NULL,
  `alias` VARCHAR(32) NULL,
  `sex` CHAR(2) NULL COMMENT 'x - 女\ny - 男\nxy - 不明',
  PRIMARY KEY (`user_id`),
  CONSTRAINT `fk_ext_user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `mini-finance`.`user` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

INSERT INTO `mini-finance`.`user_ext` (`user_id`, `phone`, `email`, `address`, `alias`, `sex`) VALUES (0, '18964749949', '252051985@qq.com', NULL, 'SpecialQ', 'y');

CREATE TABLE IF NOT EXISTS `mini-finance`.`role` (
  `id` BIGINT NOT NULL,
  `role_name` VARCHAR(32) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

INSERT INTO `mini-finance`.`role` (`id`, `role_name`) VALUES (0, 'root');

CREATE TABLE IF NOT EXISTS `mini-finance`.`user_role_rela` (
  `user_id` BIGINT NOT NULL,
  `role_id` BIGINT NOT NULL,
  PRIMARY KEY (`user_id`, `role_id`),
  INDEX `fk_role_id_idx` (`role_id` ASC),
  CONSTRAINT `fk_rela_user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `mini-finance`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_rela_role_id`
    FOREIGN KEY (`role_id`)
    REFERENCES `mini-finance`.`role` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

INSERT INTO `mini-finance`.`user_role_rela` (`user_id`, `role_id`) VALUES (0, 0);

CREATE TABLE IF NOT EXISTS `mini-finance`.`authorization` (
  `id` BIGINT NOT NULL,
  `path` VARCHAR(64) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `auth_path_UNIQUE` (`path` ASC))
ENGINE = InnoDB;

INSERT INTO `mini-finance`.`authorization` (`id`, `path`) VALUES (0, '/*');
INSERT INTO `mini-finance`.`authorization` (`id`, `path`) VALUES (1, '/**');

CREATE TABLE IF NOT EXISTS `mini-finance`.`role_auth_rela` (
  `role_id` BIGINT NOT NULL,
  `auth_id` BIGINT NOT NULL,
  PRIMARY KEY (`role_id`, `auth_id`),
  INDEX `fk_auth_id_idx` (`auth_id` ASC),
  CONSTRAINT `fk_role_id`
    FOREIGN KEY (`role_id`)
    REFERENCES `mini-finance`.`role` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_auth_id`
    FOREIGN KEY (`auth_id`)
    REFERENCES `mini-finance`.`authorization` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

INSERT INTO `mini-finance`.`role_auth_rela` (`role_id`, `auth_id`) VALUES (0, 0);
INSERT INTO `mini-finance`.`role_auth_rela` (`role_id`, `auth_id`) VALUES (0, 1);

CREATE TABLE IF NOT EXISTS `mini-finance`.`menu` (
  `id` VARCHAR(32) NOT NULL,
  `menu_name` VARCHAR(64) NOT NULL,
  `icon_path` VARCHAR(60) NULL,
  `href` VARCHAR(128) NULL,
  `isleaf` TINYINT(1) NOT NULL COMMENT '0 - 菜单目录\n1 - 菜单',
  `parent_id` VARCHAR(32) NULL,
  `auth_id` BIGINT NULL,
  `order_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_menu_auth_id_idx` (`auth_id` ASC),
  CONSTRAINT `fk_menu_auth_id`
    FOREIGN KEY (`auth_id`)
    REFERENCES `mini-finance`.`authorization` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;
INSERT INTO `mini-finance`.`menu` (`id`, `menu_name`, `icon_path`, `isleaf`, `href`, `parent_id`, `auth_id`, `order_id`) VALUES ('root', '根目录', NULL, NULL, 0, NULL, NULL, 0);
INSERT INTO `mini-finance`.`menu` (`id`, `menu_name`, `icon_path`, `isleaf`, `href`, `parent_id`, `auth_id`, `order_id`) VALUES ('powerManage', '权限管理', NULL, NULL, 0, 'root', NULL, 0);
INSERT INTO `mini-finance`.`menu` (`id`, `menu_name`, `icon_path`, `isleaf`, `href`, `parent_id`, `auth_id`, `order_id`) VALUES ('businessManage', '业务管理', NULL, NULL, 0, 'root', NULL, 1);
INSERT INTO `mini-finance`.`menu` (`id`, `menu_name`, `icon_path`, `isleaf`, `href`, `parent_id`, `auth_id`, `order_id`) VALUES ('financeManage', '财务管理', NULL, NULL, 0, 'root', NULL, 2);
INSERT INTO `mini-finance`.`menu` (`id`, `menu_name`, `icon_path`, `isleaf`, `href`, `parent_id`, `auth_id`, `order_id`) VALUES ('userManage', '用户管理', NULL, NULL, 1, 'powerManage', NULL, 0);
