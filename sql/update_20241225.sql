ALTER TABLE  `sys_quartz_job` CHANGE COLUMN `is_pause` `pause` bit(1) NULL DEFAULT NULL COMMENT '状态：1暂停、0启用' AFTER `cron_expression`;

ALTER TABLE `sys_quartz_log` CHANGE COLUMN `is_success` `success` bit(1) NULL DEFAULT NULL AFTER `exception_detail`;

ALTER TABLE  `sys_user` CHANGE COLUMN `is_admin` `admin` bit(1) NULL DEFAULT b'0' COMMENT '是否为admin账号' AFTER `password`;

update sys_dict_detail set `value`=1 where `value`='true';

update sys_dict_detail set `value`=0 where `value`='false';