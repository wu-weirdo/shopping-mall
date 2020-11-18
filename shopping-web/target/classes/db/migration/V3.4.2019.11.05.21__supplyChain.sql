CREATE TABLE IF NOT EXISTS `cdshopping`.`supply_chain`
(
    `id`                varchar(255)                                                  NOT NULL COMMENT '主键',
    `contact_name`      varchar(255)                                                  NOT NULL COMMENT '联系人姓名',
    `contact_phone`     varchar(255)                                                  NOT NULL COMMENT '联系人电话',
    `contact_address`   varchar(255)                                                  NOT NULL DEFAULT '' COMMENT '联系人地址',
    `attention`         varchar(255)                                                  NOT NULL DEFAULT '' COMMENT '注意事项',
    `purchase_price`    decimal(10, 2)                                                NOT NULL COMMENT '采购价格',
    `recommend_price`   decimal(10, 2)                                                NOT NULL COMMENT '平台推荐价格',
    `goods_count`       int(0)                                                        NOT NULL DEFAULT 0 COMMENT '商品数量',
    `create_time`       datetime(0)                                                   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`       datetime(0)                                                   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    `copy_count`        int(0)                                                        NOT NULL DEFAULT 0 COMMENT '复选次数',
    `state`             int(0)                                                        NOT NULL DEFAULT 1 COMMENT '状态',
    `goods_name`        varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci       NOT NULL DEFAULT '' COMMENT '商品名称',
    `goods_detail`      varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci       NOT NULL DEFAULT '' COMMENT '商品详情',
    `goods_type_id`     varchar(19) CHARACTER SET utf8 COLLATE utf8_general_ci        NOT NULL DEFAULT '' COMMENT '商品种类id',
    `goods_subclass_id` varchar(19) CHARACTER SET utf8 COLLATE utf8_general_ci        NOT NULL DEFAULT '' COMMENT '商品子类id',
    `goods_spec`        varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci        NOT NULL DEFAULT '' COMMENT '商品规格',
    `goods_view_url`    varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT '' COMMENT '商品小视屏地址',
    `first_show_img`    varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT '' COMMENT '首页展示的图片url',
    PRIMARY KEY (`id`)
);

REPLACE INTO `cdshopping`.`sys_menu`(`id`, `parent_id`, `title`, `path`)
VALUES ('50', '-1', '共享供应链', '/supplyChainMenu');

REPLACE INTO `cdshopping`.`sys_menu`(`id`, `parent_id`, `title`, `path`)
VALUES ('51', '50', '供应链管理', '/supplyChain');


REPLACE INTO `cdshopping`.`sys_menu_conf`(`id`, `role_id`, `menu_id`)
VALUES ('87', '1', '50');

REPLACE INTO `cdshopping`.`sys_menu_conf`(`id`, `role_id`, `menu_id`)
VALUES ('88', '1', '51');