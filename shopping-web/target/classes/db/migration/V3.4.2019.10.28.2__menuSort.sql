ALTER TABLE `cdshopping`.`sys_menu`
    ADD COLUMN `sort` int(0) NULL DEFAULT 999 COMMENT '排序,递增' AFTER `update_time`;

UPDATE `cdshopping`.`sys_menu`
SET `sort` = 0
WHERE `id` = '01';
UPDATE `cdshopping`.`sys_menu`
SET `sort` = 1
WHERE `id` = '06';
UPDATE `cdshopping`.`sys_menu`
SET `sort` = 2
WHERE `id` = '37';
UPDATE `cdshopping`.`sys_menu`
SET `sort` = 3
WHERE `id` = '14';
UPDATE `cdshopping`.`sys_menu`
SET `sort` = 4
WHERE `id` = '31';
UPDATE `cdshopping`.`sys_menu`
SET `sort` = 5
WHERE `id` = '19';
UPDATE `cdshopping`.`sys_menu`
SET `sort` = 6
WHERE `id` = '39';
UPDATE `cdshopping`.`sys_menu`
SET `sort` = 7
WHERE `id` = '02';
UPDATE `cdshopping`.`sys_menu`
SET `sort` = 8
WHERE `id` = '10';
UPDATE `cdshopping`.`sys_menu`
SET `sort` = 9
WHERE `id` = '03';
UPDATE `cdshopping`.`sys_menu`
SET `sort` = 10
WHERE `id` = '38';
UPDATE `cdshopping`.`sys_menu`
SET `sort` = 11
WHERE `id` = '05';
UPDATE `cdshopping`.`sys_menu`
SET `sort` = 12
WHERE `id` = '11';
UPDATE `cdshopping`.`sys_menu`
SET `sort` = 13
WHERE `id` = '08';
UPDATE `cdshopping`.`sys_menu`
SET `sort` = 14
WHERE `id` = '09';
UPDATE `cdshopping`.`sys_menu`
SET `sort` = 15
WHERE `id` = '07';
UPDATE `cdshopping`.`sys_menu`
SET `sort` = 16
WHERE `id` = '15';
UPDATE `cdshopping`.`sys_menu`
SET `sort` = 17
WHERE `id` = '28';
UPDATE `cdshopping`.`sys_menu`
SET `sort` = 18
WHERE `id` = '30';
UPDATE `cdshopping`.`sys_menu`
SET `sort` = 19
WHERE `id` = '12';
UPDATE `cdshopping`.`sys_menu`
SET `sort` = 20
WHERE `id` = '18';
UPDATE `cdshopping`.`sys_menu`
SET `sort` = 21
WHERE `id` = '23';
UPDATE `cdshopping`.`sys_menu`
SET `sort` = 22
WHERE `id` = '16';
UPDATE `cdshopping`.`sys_menu`
SET `sort` = 23
WHERE `id` = '32';
UPDATE `cdshopping`.`sys_menu`
SET `sort` = 24
WHERE `id` = '33';
UPDATE `cdshopping`.`sys_menu`
SET `sort` = 25
WHERE `id` = '45';
UPDATE `cdshopping`.`sys_menu`
SET `sort` = 26
WHERE `id` = '22';
UPDATE `cdshopping`.`sys_menu`
SET `sort` = 27
WHERE `id` = '34';
UPDATE `cdshopping`.`sys_menu`
SET `sort` = 28
WHERE `id` = '25';
UPDATE `cdshopping`.`sys_menu`
SET `sort` = 29
WHERE `id` = '44';
UPDATE `cdshopping`.`sys_menu`
SET `sort` = 30
WHERE `id` = '29';

DELETE
FROM `cdshopping`.`sys_menu`
WHERE `id` = '36';

delete from sys_menu
where id = 20;