ALTER TABLE `cdshopping`.`sys_menu_conf`
    MODIFY COLUMN `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '菜单分配编号' FIRST,
    ADD UNIQUE INDEX (`role_id`, `menu_id`) USING BTREE;