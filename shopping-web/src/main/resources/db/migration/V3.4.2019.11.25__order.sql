ALTER TABLE `sys_user` ADD COLUMN `buy_num`  int(15) NULL DEFAULT 0 COMMENT '购买次数' AFTER `discount_money`;