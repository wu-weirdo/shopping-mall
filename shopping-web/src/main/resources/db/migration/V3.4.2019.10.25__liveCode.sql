ALTER TABLE `cdshopping`.`sys_user_agent`
    ADD COLUMN `live_code` varchar(255) NULL COMMENT '直播间验证码' AFTER `openid`;

ALTER TABLE `cdshopping`.`sys_community_partner`
    ADD COLUMN `live_code` varchar(255) NULL COMMENT '直播间验证码' AFTER `pay_money`;