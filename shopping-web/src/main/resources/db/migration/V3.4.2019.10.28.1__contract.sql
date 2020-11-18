UPDATE `cdshopping`.`sys_contract_template`
SET `remark` = '2'
WHERE `id` = 2;

REPLACE INTO `cdshopping`.`sys_menu`(`id`, `parent_id`, `title`, `path`)
VALUES ('48', '19', '用户协议管理', '/userServiceAgreementManage');
REPLACE INTO `cdshopping`.`sys_menu_conf`(`id`, `role_id`, `menu_id`)
VALUES ('85', '1', '48');