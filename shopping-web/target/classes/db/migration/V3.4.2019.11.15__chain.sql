/** 供应链管理 **/
CREATE TABLE `chain_recipient_detail` (
`id`  int(15) NOT NULL AUTO_INCREMENT ,
`chain_id`  varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '供应链id' ,
`goods_id`  varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '复用的商品id' ,
`shipments_status`  tinyint(2) NULL DEFAULT 0 COMMENT '发货状态(0:未申请,1:已申请同意,2:已发货待收货，3:已收货，4:收货异常)' ,
`pay_status`  tinyint(2) NULL DEFAULT 0 COMMENT '付款状态(0:未付款，1:已付款待确认，2:已收到款，3:未收到打款)' ,
`create_time`  datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间' ,
`shipments_time`  datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发货时间(已确定收到款时间开始算)' ,
`remark`  varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '备注' ,
`pay_image`  varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '打款图片' ,
`recipients`  varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '收件人' ,
`consignee_address`  varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '收件人地址' ,
`receipt_number`  varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '收件人号码' ,
`can_apply`  tinyint(2) NULL DEFAULT 0 COMMENT '是否可申请发货(0:否，1:是)' ,
`refuse_reason`  varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '未收到款说明' ,
`merchant_id`  varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '商家id' ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8mb4 COLLATE=utf8mb4_general_ci
ROW_FORMAT=Dynamic;


/** 商品添加二维码字段 **/
ALTER TABLE `goods` ADD COLUMN `qr_code`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '二维码地址' AFTER `supply_chain_id`;

/** 拼团添加拼团状态 **/
ALTER TABLE `goods_collect_info` ADD COLUMN `collect_status`  tinyint(1) NULL DEFAULT 0 COMMENT '拼团结束状态(0:未结束，1：已结束)' AFTER `collect_price`;

/** 订单商品类型添加 **/
ALTER TABLE `order_main` ADD COLUMN `goods_type`  tinyint(3) NULL DEFAULT 0 COMMENT '商品类型(1:普通秒杀商品，2:普通爆品，3:普通拼团商品，4:供应链拼团商品)' AFTER `share_rate`;

/** 供应链收入表 **/
CREATE TABLE `trade_detail_chain_profit` (
`id`  int(20) NOT NULL AUTO_INCREMENT ,
`order_id`  varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '订单主键' ,
`user_type`  tinyint(2) NULL DEFAULT 3 COMMENT '用户角色类型(3:商家，4:代理商)' ,
`user_id`  varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '分润用户id' ,
`chain_profit`  decimal(15,2) NULL DEFAULT 0.00 COMMENT '分润金额' ,
`to_account_money`  decimal(15,2) NULL DEFAULT 0.00 COMMENT '分润到账金额' ,
`create_time`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间' ,
`update_time`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间' ,
PRIMARY KEY (`id`),
INDEX `user_type` (`user_type`, `user_id`) USING BTREE ,
INDEX `order_id` (`order_id`) USING BTREE
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8mb4 COLLATE=utf8mb4_general_ci
ROW_FORMAT=Dynamic
;
/** 目录以及权限添加,后面直接目录同步过去吧 **/


/** 字典添加 **/
insert into `sys_dictionary_param` ( `sys_type`, `sys_type_name`, `sys_key`, `sys_value`, `create_time`, `update_time`, `remark`) values('share_goods','商品分享分润','merchant_profit_rate','5000','2019-11-05 13:59:10','2019-11-05 14:00:37','商家分润比例(万分之)');
insert into `sys_dictionary_param` ( `sys_type`, `sys_type_name`, `sys_key`, `sys_value`, `create_time`, `update_time`, `remark`) values('share_goods','商品分享分润','agent_profit_rate','3000','2019-11-05 13:59:32','2019-11-05 14:01:09','代理商分润比例(万分之)');
insert into `sys_dictionary_param` ( `sys_type`, `sys_type_name`, `sys_key`, `sys_value`, `create_time`, `update_time`, `remark`) values('share_goods','商品分享分润','system_profit_rate','2000','2019-11-05 14:01:12','2019-11-05 14:02:16','系统分润比例(万分之)');
insert into `sys_dictionary_param` ( `sys_type`, `sys_type_name`, `sys_key`, `sys_value`, `create_time`, `update_time`, `remark`) values('supply_chain','供应链分润比例','merchant_profit_rate','5000','2019-11-13 14:33:19','2019-11-13 14:34:33','商家分润比例(万分之)');
insert into `sys_dictionary_param` ( `sys_type`, `sys_type_name`, `sys_key`, `sys_value`, `create_time`, `update_time`, `remark`) values('supply_chain','供应链分润比例','agent_profit_rate','3000','2019-11-13 14:34:41','2019-11-13 14:34:51','代理商分润比例(万分之)');
insert into `sys_dictionary_param` ( `sys_type`, `sys_type_name`, `sys_key`, `sys_value`, `create_time`, `update_time`, `remark`) values('supply_chain','供应链分润比例','system_profit_rate','2000','2019-11-13 14:34:56','2019-11-13 14:35:08','系统分润比例(万分之)');



;