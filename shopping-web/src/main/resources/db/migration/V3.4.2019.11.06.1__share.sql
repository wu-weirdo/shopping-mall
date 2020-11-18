/** 订单表添加分享商家以及分享的推广费比例 **/
ALTER TABLE `order_main` ADD COLUMN `share_merchant_id`  varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '分享商家id' AFTER `collect_info_id`;
ALTER TABLE `order_main` ADD COLUMN `share_rate`  decimal(10,2) NULL DEFAULT 0.00 COMMENT '分享费率(%单位的)' AFTER `share_merchant_id`;


/** 推广分润明细 **/
CREATE TABLE if not exists `trade_detail_share_profit`
(
`id`  int(20) NOT NULL AUTO_INCREMENT ,
`order_id`  varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '订单主键' ,
`user_type`  tinyint(2) NULL DEFAULT 3 COMMENT '用户角色类型(1:总部，3:商家，4:代理商)' ,
`user_id`  varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '分润用户id' ,
`share_profit`  decimal(15,2) NULL DEFAULT 0.00 COMMENT '分润金额' ,
`to_account_money`  decimal(15,2) NULL DEFAULT 0.00 COMMENT '分润到账金额' ,
`create_time`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间' ,
`update_time`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间' ,
PRIMARY KEY (`id`),
INDEX `user_type` (`user_type`, `user_id`) USING BTREE ,
INDEX `order_id` (`order_id`) USING BTREE
)
    ENGINE=InnoDB
    DEFAULT CHARACTER SET=utf8mb4 COLLATE=utf8mb4_general_ci
    ROW_FORMAT = Dynamic;


/** 目录权限添加 **/
REPLACE INTO `cdshopping`.`sys_menu`(`id`, `parent_id`, `title`, `path` ,sort)
VALUES ('52', '37', '推广订单', '/shareOrderManage',302);

REPLACE INTO `cdshopping`.`sys_menu`(`id`, `parent_id`, `title`, `path`,sort)
VALUES ('53', '14', '推广费管理', '/shareProfitMange',406);


REPLACE INTO `cdshopping`.`sys_menu_conf`(`id`, `role_id`, `menu_id`)
VALUES ('90', '1', '52');
REPLACE INTO `cdshopping`.`sys_menu_conf`(`id`, `role_id`, `menu_id`)
VALUES ('91', '3', '52');
REPLACE INTO `cdshopping`.`sys_menu_conf`(`id`, `role_id`, `menu_id`)
VALUES ('92', '4', '52');

REPLACE INTO `cdshopping`.`sys_menu_conf`(`id`, `role_id`, `menu_id`)
VALUES ('93', '1', '53');
REPLACE INTO `cdshopping`.`sys_menu_conf`(`id`, `role_id`, `menu_id`)
VALUES ('94', '3', '53');
REPLACE INTO `cdshopping`.`sys_menu_conf`(`id`, `role_id`, `menu_id`)
VALUES ('95', '4', '53');


REPLACE INTO `cdshopping`.`sys_menu_conf`(`id`, `role_id`, `menu_id`)
VALUES ('104', '3', '14');



