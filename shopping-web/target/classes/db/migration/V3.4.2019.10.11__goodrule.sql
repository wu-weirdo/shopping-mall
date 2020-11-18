CREATE TABLE `good_rule` (
`id`  INT(15) NOT NULL AUTO_INCREMENT ,
`type`  TINYINT(3) NULL DEFAULT 0 COMMENT '类型(0:广告违禁词,1:商品滥发信息，2:禁发商品及信息名录&对应违规处理,3:24h团价格规范，4:日期-详情，5:库存-详情)' ,
`context`  TEXT CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '内容' ,
`create_time`  DATETIME NULL DEFAULT CURRENT_TIMESTAMP ,
PRIMARY KEY (`id`)
)
ENGINE=INNODB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
ROW_FORMAT=DYNAMIC
;

REPLACE INTO `cdshopping`.`sys_menu`(`id`, `parent_id`, `title`, `path`)
VALUES ('47', '19', '商品规则管理', '/goodRuleManage');
REPLACE INTO `cdshopping`.`sys_menu_conf`(`id`, `role_id`, `menu_id`)
VALUES ('83', '1', '47');


insert into `good_rule` (`id`, `type`, `context`, `create_time`) values('1','0','<ul><li class=\"ql-indent-1\"><strong class=\"ql-size-small\">不知道好不好吃系列</strong></li></ul>','2019-09-17 17:41:38');
insert into `good_rule` (`id`, `type`, `context`, `create_time`) values('2','1','<ul><li class=\"ql-indent-1\"><strong class=\"ql-size-small\">不知道好不好吃系列</strong></li></ul>','2019-09-17 17:41:41');
insert into `good_rule` (`id`, `type`, `context`, `create_time`) values('3','2','<ul><li class=\"ql-indent-1\"><strong class=\"ql-size-small\">不知道好不好吃系列</strong></li></ul>','2019-09-17 17:41:41');
insert into `good_rule` (`id`, `type`, `context`, `create_time`) values('4','3','<ul><li class=\"ql-indent-1\"><strong class=\"ql-size-small\">不知道好不好吃系列</strong></li></ul>','2019-09-17 17:41:42');
insert into `good_rule` (`id`, `type`, `context`, `create_time`) values('5','4','<ul><li class=\"ql-indent-1\"><strong class=\"ql-size-small\">不知道好不好吃系列</strong></li></ul>','2019-09-17 17:41:44');
insert into `good_rule` (`id`, `type`, `context`, `create_time`) values('6','5','<ul><li class=\"ql-indent-1\"><strong class=\"ql-size-small\">不知道好不好吃系列</strong></li></ul>','2019-09-17 17:41:46');
