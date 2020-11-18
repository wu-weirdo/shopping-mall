ALTER TABLE `cdshopping`.`goods`
    ADD COLUMN `promotion` int(0) NOT NULL DEFAULT 0 COMMENT '是否推广' AFTER `hot`,
    ADD COLUMN `promotion_costs` decimal(10, 2) NOT NULL DEFAULT 0.05 COMMENT '推广费用' AFTER `promotion`;

ALTER TABLE `cdshopping`.`sys_user_merchant`
    ADD COLUMN `league` int(0) NOT NULL DEFAULT 0 COMMENT '是否联盟' AFTER `total_income`;