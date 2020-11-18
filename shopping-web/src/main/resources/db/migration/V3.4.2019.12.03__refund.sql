ALTER TABLE `pay_refund_apply` ADD COLUMN `out_order_no`  varchar(19) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '外部订单号' AFTER `collect_merchant_status`;
ALTER TABLE `pay_refund_apply` ADD COLUMN `order_type`  tinyint(2) NULL DEFAULT 1 COMMENT '订单类型' AFTER `out_order_no`;


UPDATE pay_refund_apply p ,order_main o SET p.`out_order_no` = o.`order_no`
WHERE p.`order_id` = o.id;