CREATE DATABASE IF NOT EXISTS `cloud_nacos`;

USE `cloud_nacos`;

CREATE TABLE `config_info` (
    `id`           BIGINT (20) NOT NULL AUTO_INCREMENT COMMENT 'id',
    `data_id`      VARCHAR(255) NOT NULL COMMENT 'data_id',
    `group_id`     VARCHAR(255)          DEFAULT NULL,
    `content`      LONGTEXT     NOT NULL COMMENT 'content',
    `md5`          VARCHAR(32)           DEFAULT NULL COMMENT 'md5',
    `gmt_create`   datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified` datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
    `src_user`     text COMMENT 'source user',
    `src_ip`       VARCHAR(20)           DEFAULT NULL COMMENT 'source ip',
    `app_name`     VARCHAR(128)          DEFAULT NULL,
    `tenant_id`    VARCHAR(128)          DEFAULT '' COMMENT '租户字段',
    `c_desc`       VARCHAR(256)          DEFAULT NULL,
    `c_use`        VARCHAR(64)           DEFAULT NULL,
    `effect`       VARCHAR(64)           DEFAULT NULL,
    `type`         VARCHAR(64)           DEFAULT NULL,
    `c_schema`     text,
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_configinfo_datagrouptenant` (`data_id`, `group_id`, `tenant_id`)
) ENGINE = INNODB DEFAULT CHARSET = utf8 COLLATE = utf8_bin COMMENT = 'config_info';

CREATE TABLE `config_info_aggr` (
    `id`           BIGINT (20) NOT NULL AUTO_INCREMENT COMMENT 'id',
    `data_id`      VARCHAR(255) NOT NULL COMMENT 'data_id',
    `group_id`     VARCHAR(255) NOT NULL COMMENT 'group_id',
    `datum_id`     VARCHAR(255) NOT NULL COMMENT 'datum_id',
    `content`      LONGTEXT     NOT NULL COMMENT '内容',
    `gmt_modified` datetime     NOT NULL COMMENT '修改时间',
    `app_name`     VARCHAR(128) DEFAULT NULL,
    `tenant_id`    VARCHAR(128) DEFAULT '' COMMENT '租户字段',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_configinfoaggr_datagrouptenantdatum` (`data_id`, `group_id`, `tenant_id`, `datum_id`)
) ENGINE = INNODB DEFAULT CHARSET = utf8 COLLATE = utf8_bin COMMENT = '增加租户字段';

CREATE TABLE `config_info_beta` (
    `id`           BIGINT (20) NOT NULL AUTO_INCREMENT COMMENT 'id',
    `data_id`      VARCHAR(255) NOT NULL COMMENT 'data_id',
    `group_id`     VARCHAR(128) NOT NULL COMMENT 'group_id',
    `app_name`     VARCHAR(128)          DEFAULT NULL COMMENT 'app_name',
    `content`      LONGTEXT     NOT NULL COMMENT 'content',
    `beta_ips`     VARCHAR(1024)         DEFAULT NULL COMMENT 'betaIps',
    `md5`          VARCHAR(32)           DEFAULT NULL COMMENT 'md5',
    `gmt_create`   datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified` datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
    `src_user`     text COMMENT 'source user',
    `src_ip`       VARCHAR(20)           DEFAULT NULL COMMENT 'source ip',
    `tenant_id`    VARCHAR(128)          DEFAULT '' COMMENT '租户字段',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_configinfobeta_datagrouptenant` (`data_id`, `group_id`, `tenant_id`)
) ENGINE = INNODB DEFAULT CHARSET = utf8 COLLATE = utf8_bin COMMENT = 'config_info_beta';

CREATE TABLE `config_info_tag` (
    `id`           BIGINT (20) NOT NULL AUTO_INCREMENT COMMENT 'id',
    `data_id`      VARCHAR(255) NOT NULL COMMENT 'data_id',
    `group_id`     VARCHAR(128) NOT NULL COMMENT 'group_id',
    `tenant_id`    VARCHAR(128)          DEFAULT '' COMMENT 'tenant_id',
    `tag_id`       VARCHAR(128) NOT NULL COMMENT 'tag_id',
    `app_name`     VARCHAR(128)          DEFAULT NULL COMMENT 'app_name',
    `content`      LONGTEXT     NOT NULL COMMENT 'content',
    `md5`          VARCHAR(32)           DEFAULT NULL COMMENT 'md5',
    `gmt_create`   datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified` datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
    `src_user`     text COMMENT 'source user',
    `src_ip`       VARCHAR(20)           DEFAULT NULL COMMENT 'source ip',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_configinfotag_datagrouptenanttag` (`data_id`, `group_id`, `tenant_id`, `tag_id`)
) ENGINE = INNODB DEFAULT CHARSET = utf8 COLLATE = utf8_bin COMMENT = 'config_info_tag';

CREATE TABLE `config_tags_relation` (
    `id`        BIGINT (20) NOT NULL COMMENT 'id',
    `tag_name`  VARCHAR(128) NOT NULL COMMENT 'tag_name',
    `tag_type`  VARCHAR(64)  DEFAULT NULL COMMENT 'tag_type',
    `data_id`   VARCHAR(255) NOT NULL COMMENT 'data_id',
    `group_id`  VARCHAR(128) NOT NULL COMMENT 'group_id',
    `tenant_id` VARCHAR(128) DEFAULT '' COMMENT 'tenant_id',
    `nid`       BIGINT (20) NOT NULL AUTO_INCREMENT,
    PRIMARY KEY (`nid`),
    UNIQUE KEY `uk_configtagrelation_configidtag` (`id`, `tag_name`, `tag_type`),
    KEY         `idx_tenant_id` (`tenant_id`)
) ENGINE = INNODB DEFAULT CHARSET = utf8 COLLATE = utf8_bin COMMENT = 'config_tag_relation';

CREATE TABLE `group_capacity` (
    `id`                BIGINT (20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `group_id`          VARCHAR(128) NOT NULL DEFAULT '' COMMENT 'Group ID，空字符表示整个集群',
    `quota`             INT (10) UNSIGNED NOT NULL DEFAULT '0' COMMENT '配额，0表示使用默认值',
    `usage`             INT (10) UNSIGNED NOT NULL DEFAULT '0' COMMENT '使用量',
    `max_size`          INT (10) UNSIGNED NOT NULL DEFAULT '0' COMMENT '单个配置大小上限，单位为字节，0表示使用默认值',
    `max_aggr_count`    INT (10) UNSIGNED NOT NULL DEFAULT '0' COMMENT '聚合子配置最大个数，，0表示使用默认值',
    `max_aggr_size`     INT (10) UNSIGNED NOT NULL DEFAULT '0' COMMENT '单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值',
    `max_history_count` INT (10) UNSIGNED NOT NULL DEFAULT '0' COMMENT '最大变更历史数量',
    `gmt_create`        datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified`      datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_group_id` (`group_id`)
) ENGINE = INNODB DEFAULT CHARSET = utf8 COLLATE = utf8_bin COMMENT = '集群、各Group容量信息表';

CREATE TABLE `his_config_info` (
    `id`           BIGINT (64) UNSIGNED NOT NULL,
    `nid`          BIGINT (20) UNSIGNED NOT NULL AUTO_INCREMENT,
    `data_id`      VARCHAR(255) NOT NULL,
    `group_id`     VARCHAR(128) NOT NULL,
    `app_name`     VARCHAR(128)          DEFAULT NULL COMMENT 'app_name',
    `content`      LONGTEXT     NOT NULL,
    `md5`          VARCHAR(32)           DEFAULT NULL,
    `gmt_create`   datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `gmt_modified` datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `src_user`     text,
    `src_ip`       VARCHAR(20)           DEFAULT NULL,
    `op_type`      CHAR(10)              DEFAULT NULL,
    `tenant_id`    VARCHAR(128)          DEFAULT '' COMMENT '租户字段',
    PRIMARY KEY (`nid`),
    KEY            `idx_gmt_create` (`gmt_create`),
    KEY            `idx_gmt_modified` (`gmt_modified`),
    KEY            `idx_did` (`data_id`)
) ENGINE = INNODB DEFAULT CHARSET = utf8 COLLATE = utf8_bin COMMENT = '多租户改造';

CREATE TABLE `tenant_capacity` (
    `id`                BIGINT (20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `tenant_id`         VARCHAR(128) NOT NULL DEFAULT '' COMMENT 'Tenant ID',
    `quota`             INT (10) UNSIGNED NOT NULL DEFAULT '0' COMMENT '配额，0表示使用默认值',
    `usage`             INT (10) UNSIGNED NOT NULL DEFAULT '0' COMMENT '使用量',
    `max_size`          INT (10) UNSIGNED NOT NULL DEFAULT '0' COMMENT '单个配置大小上限，单位为字节，0表示使用默认值',
    `max_aggr_count`    INT (10) UNSIGNED NOT NULL DEFAULT '0' COMMENT '聚合子配置最大个数',
    `max_aggr_size`     INT (10) UNSIGNED NOT NULL DEFAULT '0' COMMENT '单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值',
    `max_history_count` INT (10) UNSIGNED NOT NULL DEFAULT '0' COMMENT '最大变更历史数量',
    `gmt_create`        datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified`      datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_tenant_id` (`tenant_id`)
) ENGINE = INNODB DEFAULT CHARSET = utf8 COLLATE = utf8_bin COMMENT = '租户容量信息表';

CREATE TABLE `tenant_info` (
    `id`            BIGINT (20) NOT NULL AUTO_INCREMENT COMMENT 'id',
    `kp`            VARCHAR(128) NOT NULL COMMENT 'kp',
    `tenant_id`     VARCHAR(128) DEFAULT '' COMMENT 'tenant_id',
    `tenant_name`   VARCHAR(128) DEFAULT '' COMMENT 'tenant_name',
    `tenant_desc`   VARCHAR(256) DEFAULT NULL COMMENT 'tenant_desc',
    `create_source` VARCHAR(32)  DEFAULT NULL COMMENT 'create_source',
    `gmt_create`    BIGINT (20) NOT NULL COMMENT '创建时间',
    `gmt_modified`  BIGINT (20) NOT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_tenant_info_kptenantid` (`kp`, `tenant_id`),
    KEY             `idx_tenant_id` (`tenant_id`)
) ENGINE = INNODB DEFAULT CHARSET = utf8 COLLATE = utf8_bin COMMENT = 'tenant_info';

CREATE TABLE `users` (
    `username` VARCHAR(50)  NOT NULL PRIMARY KEY,
    `password` VARCHAR(500) NOT NULL,
    `enabled`  boolean      NOT NULL
);

CREATE TABLE `roles` (
    `username` VARCHAR(50) NOT NULL,
    `role`     VARCHAR(50) NOT NULL,
    UNIQUE INDEX `idx_user_role` (`username` ASC, `role` ASC) USING BTREE
);

CREATE TABLE `permissions`
(
    `role`     VARCHAR(50)  NOT NULL,
    `resource` VARCHAR(512) NOT NULL,
    `action`   VARCHAR(8)   NOT NULL,
    UNIQUE INDEX `uk_role_permission` (`role`, `resource`, `action`) USING BTREE
);

INSERT INTO users (username, password, enabled) VALUES ('root', '$2a$10$U6YOUTvceqixfXRvLuk0KutDwg2JcwTvSEg71xy0HV1aqCM6eqH0S', TRUE);

INSERT INTO roles (username, role) VALUES ('root', 'ROLE_ADMIN');