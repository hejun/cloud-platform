CREATE DATABASE IF NOT EXISTS `cloud_uac`;

USE `cloud_uac`;

CREATE TABLE `uac_user`(
    `id`          BIGINT(20)  NOT NULL AUTO_INCREMENT COMMENT 'id',
    `username`    VARCHAR(50) NOT NULL COMMENT '用户名',
    `password`    VARCHAR(50) NOT NULL COMMENT '密码',
    `created_date`  datetime  NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `modified_date` datetime  NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_username` (`username`)
) ENGINE = INNODB DEFAULT CHARSET = utf8 COLLATE = utf8_bin COMMENT = '用户表';