REPLACE INTO `cdshopping`.`sys_menu`(`id`, `parent_id`, `title`, `path`)
VALUES ('54', '50', '添加供应链', '/supplyChainPublish');

REPLACE INTO `cdshopping`.`sys_menu_conf`(`role_id`, `menu_id`)
VALUES ('1', '54');