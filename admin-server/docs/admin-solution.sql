-- 用户表
DROP TABLE IF EXISTS `User`;
CREATE TABLE `User`
(
    `id`         bigint(20)  NOT NULL AUTO_INCREMENT COMMENT '主键',
    `username`   varchar(64) NOT NULL COMMENT '用户名',
    `password`   varchar(64) NOT NULL COMMENT '用户密码',
    `status`     int(1)      NOT NULL COMMENT '状态(1可用, 0不可用)',
    `createTime` datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updateTime` datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `idx_username` (`username`),
    KEY `idx_createTime` (`createTime`),
    KEY `idx_updateTime` (`updateTime`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8 COMMENT ='用户表';


-- 角色表
DROP TABLE IF EXISTS `Role`;
CREATE TABLE `Role`
(
    `id`         bigint(20)  NOT NULL AUTO_INCREMENT COMMENT '主键',
    `roleName`   varchar(64) NOT NULL COMMENT '角色名',
    `status`     int(1)      NOT NULL COMMENT '状态(1可用, 0不可用)',
    `createTime` datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updateTime` datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `idx_roleName` (`roleName`),
    KEY `idx_createTime` (`createTime`),
    KEY `idx_updateTime` (`updateTime`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8 COMMENT ='角色表';

-- 用户角色表
DROP TABLE IF EXISTS `UserRoleRelation`;
CREATE TABLE `UserRoleRelation`
(
    `id`         bigint(20)  NOT NULL AUTO_INCREMENT COMMENT '主键',
    `roleId`     varchar(64) NOT NULL COMMENT '角色id',
    `userId`     varchar(64) NOT NULL COMMENT '用户id',
    `status`     int(1)      NOT NULL COMMENT '状态(1可用, 0不可用)',
    `createTime` datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updateTime` datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `idx_roleId_userId` (`roleId`, `userId`),
    KEY `idx_createTime` (`createTime`),
    KEY `idx_updateTime` (`updateTime`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8 COMMENT ='用户角色表';

-- 权限表
DROP TABLE IF EXISTS `BackendPermission`;
CREATE TABLE `Permission`
(
    `id`             bigint(20)  NOT NULL AUTO_INCREMENT COMMENT '主键',
    `permissionName` varchar(64) NOT NULL COMMENT '权限名',
    `path`           varchar(64) NOT NULL COMMENT '权限路径',
    `status`         int(1)      NOT NULL COMMENT '状态(1可用, 0不可用)',
    `createTime`     datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updateTime`     datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `idx_permissionName` (`permissionName`),
    UNIQUE KEY `idx_path` (`path`),
    KEY `idx_createTime` (`createTime`),
    KEY `idx_updateTime` (`updateTime`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8 COMMENT ='后端权限表';

-- 角色权限表
DROP TABLE IF EXISTS `RoleBackendPermissionRelation`;
CREATE TABLE `RoleBackendPermissionRelation`
(
    `id`           bigint(20)  NOT NULL AUTO_INCREMENT COMMENT '主键',
    `roleId`       varchar(64) NOT NULL COMMENT '角色id',
    `permissionId` varchar(64) NOT NULL COMMENT '权限id',
    `status`       int(1)      NOT NULL COMMENT '状态(1可用, 0不可用)',
    `createTime`   datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updateTime`   datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `idx_roleId_permissionId` (`roleId`, `permissionId`),
    KEY `idx_createTime` (`createTime`),
    KEY `idx_updateTime` (`updateTime`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8 COMMENT ='后端角色权限关联表';


-- 权限表
DROP TABLE IF EXISTS `FrontendPermission`;
CREATE TABLE `FrontendPermission`
(
    `id`         bigint(20)  NOT NULL AUTO_INCREMENT COMMENT '主键',
    `name`       varchar(64) NOT NULL COMMENT '权限名',
    `path`       varchar(64) NOT NULL COMMENT '权限路径',
    `component`  varchar(64) NOT NULL COMMENT '组件名称',
    `redirect`   varchar(64)          default '' COMMENT '重定向',
    `title`      varchar(64) NOT NULL COMMENT '标题',
    `icon`       varchar(64)          default '' COMMENT '图标',
    `parrent`    varchar(64)          default '' COMMENT '父路径',
    `status`     int(1)      NOT NULL COMMENT '状态(1可用, 0不可用)',
    `createTime` datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updateTime` datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `idx_permissionName` (`name`),
    UNIQUE KEY `idx_path` (`path`),
    KEY `idx_createTime` (`createTime`),
    KEY `idx_updateTime` (`updateTime`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8 COMMENT ='前端权限表';

-- 角色权限表
DROP TABLE IF EXISTS `RoleFrontendPermissionRelation`;
CREATE TABLE `RoleFrontendPermissionRelation`
(
    `id`           bigint(20)  NOT NULL AUTO_INCREMENT COMMENT '主键',
    `roleId`       varchar(64) NOT NULL COMMENT '角色id',
    `permissionId` varchar(64) NOT NULL COMMENT '权限id',
    `status`       int(1)      NOT NULL COMMENT '状态(1可用, 0不可用)',
    `createTime`   datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updateTime`   datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `idx_roleId_permissionId` (`roleId`, `permissionId`),
    KEY `idx_createTime` (`createTime`),
    KEY `idx_updateTime` (`updateTime`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8 COMMENT ='前端角色权限关联表';
