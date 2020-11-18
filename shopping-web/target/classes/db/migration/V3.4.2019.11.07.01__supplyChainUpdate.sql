ALTER TABLE `cdshopping`.`supply_chain`
    ADD COLUMN `goods_price` decimal(20, 2) NOT NULL DEFAULT 0.00 COMMENT '商品价格' AFTER `first_show_img`,
    ADD COLUMN `goods_retail_price` decimal(20, 2) NOT NULL DEFAULT 0.00 COMMENT '商品门市价' AFTER `goods_price`,
    ADD COLUMN `goods_cost_price` decimal(20, 2) NOT NULL DEFAULT 0.00 COMMENT '商品成本价' AFTER `goods_retail_price`,
    ADD COLUMN `brand` varchar(255) NOT NULL COMMENT '品牌' AFTER `goods_cost_price`,
    ADD COLUMN `minimum_shipment` int(0) NOT NULL DEFAULT 0 COMMENT '最低发货量' AFTER `brand`,
    ADD COLUMN `total_sales` int(0) NOT NULL DEFAULT 0 COMMENT '总销量' AFTER `minimum_shipment`,
    ADD COLUMN `goods_surplus_num` int(11) NOT NULL DEFAULT 0 COMMENT '商品剩余数量' AFTER `total_sales`;