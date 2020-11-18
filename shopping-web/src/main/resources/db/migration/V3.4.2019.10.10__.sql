/** 会员订单添加区县合伙人id，便于统计以及计算分润 */
ALTER TABLE `member_order` ADD COLUMN `agent_id`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '区县合伙人id' AFTER `partener_id`;

/** 商家添加总收入字段 */
ALTER TABLE `sys_user_merchant` ADD COLUMN `total_income`  decimal(10,4) NULL DEFAULT 0.0000 COMMENT '商户总收入' AFTER `end_business`;

/** 初始化商户总收入*/
UPDATE sys_user_merchant su,(
SELECT SUM(tdm.`total_income`) total_income, merchant_id
FROM trade_detail_merchant tdm
GROUP BY tdm.`merchant_id`
) tdm
SET su.`total_income` = tdm.`total_income`
WHERE su.id = tdm.`merchant_id`;

/** 初始化会员订单信息对应的区县合伙人信息 */
UPDATE member_order m ,agent_county_info aci
SET m.`agent_id` = aci.`agent_id`
WHERE m.`county_id` = aci.`city_code`;


/** 给区县合伙人商户管理权限 */
REPLACE INTO `cdshopping`.`sys_menu_conf`(`id`, `role_id`, `menu_id`)
VALUES ('84', '1', '38');


