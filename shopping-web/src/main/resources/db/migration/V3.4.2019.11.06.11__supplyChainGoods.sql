ALTER TABLE `cdshopping`.`goods`
    ADD COLUMN `supply_chain` int(0) NOT NULL DEFAULT 0 COMMENT '是否是供应链商品' AFTER `promotion_costs`,
    ADD COLUMN `supply_chain_id` varchar(255) NULL COMMENT '供应链商品id' AFTER `supply_chain`;