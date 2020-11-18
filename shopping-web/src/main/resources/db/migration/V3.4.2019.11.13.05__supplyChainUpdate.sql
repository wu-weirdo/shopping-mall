ALTER TABLE `cdshopping`.`supply_chain`
    DROP COLUMN `first_show_img`,
    DROP COLUMN `goods_cost_price`,
    DROP COLUMN `goods_surplus_num`,
    ADD COLUMN `chamberlain` varchar(255) NOT NULL DEFAULT '' COMMENT '收款人' AFTER `total_sales`,
    ADD COLUMN `bank` varchar(255) NOT NULL DEFAULT '' COMMENT '收款银行' AFTER `chamberlain`,
    ADD COLUMN `card_number` varchar(255) NOT NULL DEFAULT '' COMMENT '卡号' AFTER `bank`,
    ADD COLUMN `address` varchar(255) NOT NULL DEFAULT '' COMMENT '负责人地址' AFTER `card_number`,
    ADD COLUMN `name` varchar(255) NOT NULL DEFAULT '' COMMENT '负责人名字' AFTER `address`,
    ADD COLUMN `phone` varchar(255) NOT NULL DEFAULT '' COMMENT '负责人电话' AFTER `name`;