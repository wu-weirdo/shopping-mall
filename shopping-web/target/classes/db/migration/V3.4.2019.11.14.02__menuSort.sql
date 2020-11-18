REPLACE INTO `cdshopping`.`sys_menu`(`id`, `parent_id`, `title`, `path`)
VALUES ('56', '19', '菜单排序', '/menuManage');

REPLACE INTO `cdshopping`.`sys_menu_conf`(`role_id`, `menu_id`)
VALUES ('1', '56');
