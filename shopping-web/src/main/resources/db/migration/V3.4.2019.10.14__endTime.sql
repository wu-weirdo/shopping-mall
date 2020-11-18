ALTER TABLE `cdshopping`.`message_meta`
    ADD COLUMN `end_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '停止推送时间' AFTER `state`,
    ADD COLUMN `title` varchar(255) NOT NULL DEFAULT '通知' COMMENT '标题' AFTER `end_time`;

ALTER TABLE `cdshopping`.`message_user`
    DROP COLUMN `stage`,
    DROP COLUMN `complete`,
    DROP COLUMN `state`;