/*
 Navicat Premium Data Transfer

 Source Server         :
 Source Server Type    : MySQL
 Source Server Version : 80027
 Source Host           : 127.0.0.1:3306
 Source Schema         : siival_test

 Target Server Type    : MySQL
 Target Server Version : 80027
 File Encoding         : 65001

 Date: 15/04/2022 21:57:16
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for code_column_config
-- ----------------------------
DROP TABLE IF EXISTS `code_column_config`;
CREATE TABLE `code_column_config`  (
  `column_id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `table_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `column_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `column_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `dict_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `extra` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `form_show` bit(1) NULL DEFAULT NULL,
  `form_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `key_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `list_show` bit(1) NULL DEFAULT NULL,
  `not_null` bit(1) NULL DEFAULT NULL,
  `query_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `date_annotation` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`column_id`) USING BTREE,
  INDEX `idx_table_name`(`table_name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '代码生成字段信息存储' ROW_FORMAT = COMPACT;


-- ----------------------------
-- Table structure for code_gen_config
-- ----------------------------
DROP TABLE IF EXISTS `code_gen_config`;
CREATE TABLE `code_gen_config`  (
  `config_id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `table_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '表名',
  `author` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '作者',
  `cover` bit(1) NULL DEFAULT NULL COMMENT '是否覆盖',
  `module_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '模块名称',
  `pack` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '至于哪个包下',
  `path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '前端代码生成的路径',
  `api_path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '前端Api文件路径',
  `prefix` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '表前缀',
  `api_alias` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '接口名称',
  PRIMARY KEY (`config_id`) USING BTREE,
  INDEX `idx_table_name`(`table_name`(100)) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '代码生成器配置' ROW_FORMAT = COMPACT;

-- ----------------------------
-- Table structure for exam_info
-- ----------------------------
DROP TABLE IF EXISTS `exam_info`;
CREATE TABLE `exam_info`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `exam_name` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `status` int NULL DEFAULT 0 COMMENT '0-禁用,1-启用',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `file_label` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'filelabel',
  `file_size` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'file 大小',
  `version` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '教材版本version',
  `pid` int NOT NULL DEFAULT 0 COMMENT 'pid',
  `temp_id` int NULL DEFAULT NULL COMMENT '临时表id用户校验数据完整性',
  `file_type` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '文件类型',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '试卷信息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for exam_menu
-- ----------------------------
DROP TABLE IF EXISTS `exam_menu`;
CREATE TABLE `exam_menu`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单名称',
  `status` tinyint(1) NOT NULL DEFAULT 1 COMMENT '1-启用,0-禁用',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `pid` int NOT NULL DEFAULT 0 COMMENT '上级标签',
  `sort` int NOT NULL DEFAULT 0 COMMENT '排序字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 24 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'exam menu info' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of exam_menu
-- ----------------------------
INSERT INTO `exam_menu` VALUES (1, '语文', 1, '2022-03-20 10:54:15', NULL, 0, 1);
INSERT INTO `exam_menu` VALUES (2, '数学', 1, '2022-03-20 10:56:01', NULL, 0, 2);
INSERT INTO `exam_menu` VALUES (3, '英语', 1, '2022-03-20 10:56:01', NULL, 0, 3);
INSERT INTO `exam_menu` VALUES (4, '物理', 1, '2022-03-20 10:56:01', NULL, 0, 4);
INSERT INTO `exam_menu` VALUES (5, '化学', 1, '2022-03-20 10:56:01', NULL, 0, 5);
INSERT INTO `exam_menu` VALUES (6, '政治', 1, '2022-03-20 10:56:01', NULL, 0, 6);
INSERT INTO `exam_menu` VALUES (7, '历史', 1, '2022-03-20 10:56:01', NULL, 0, 7);
INSERT INTO `exam_menu` VALUES (8, '地理', 1, '2022-03-20 10:56:01', NULL, 0, 8);
INSERT INTO `exam_menu` VALUES (9, '生物', 1, '2022-03-20 10:56:01', NULL, 0, 9);
INSERT INTO `exam_menu` VALUES (10, '一年级', 1, '2022-03-20 11:00:22', NULL, 1, 1);
INSERT INTO `exam_menu` VALUES (11, '二年级', 1, '2022-03-20 11:00:22', NULL, 1, 2);
INSERT INTO `exam_menu` VALUES (12, '三年级', 1, '2022-03-20 11:00:22', NULL, 1, 3);
INSERT INTO `exam_menu` VALUES (13, '四年级', 1, '2022-03-20 11:00:22', NULL, 1, 4);
INSERT INTO `exam_menu` VALUES (14, '五年级', 1, '2022-03-20 11:00:22', NULL, 1, 5);
INSERT INTO `exam_menu` VALUES (15, '六年级', 1, '2022-03-20 11:00:22', NULL, 1, 6);
INSERT INTO `exam_menu` VALUES (16, '七年级', 1, '2022-03-20 11:00:22', NULL, 1, 7);
INSERT INTO `exam_menu` VALUES (17, '八年级', 1, '2022-03-20 11:00:22', NULL, 1, 8);
INSERT INTO `exam_menu` VALUES (18, '九年级', 1, '2022-03-20 11:00:22', NULL, 1, 9);
INSERT INTO `exam_menu` VALUES (19, '中考试卷', 1, '2022-03-20 11:00:22', NULL, 1, 10);
INSERT INTO `exam_menu` VALUES (20, '高一', 1, '2022-03-20 11:00:22', NULL, 1, 11);
INSERT INTO `exam_menu` VALUES (21, '高二', 1, '2022-03-20 11:00:22', NULL, 1, 12);
INSERT INTO `exam_menu` VALUES (22, '高三', 1, '2022-03-20 11:00:22', NULL, 1, 13);
INSERT INTO `exam_menu` VALUES (23, '高考', 1, '2022-03-20 11:00:22', NULL, 1, 14);

-- ----------------------------
-- Table structure for mnt_app
-- ----------------------------
DROP TABLE IF EXISTS `mnt_app`;
CREATE TABLE `mnt_app`  (
  `app_id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '应用名称',
  `upload_path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '上传目录',
  `deploy_path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '部署路径',
  `backup_path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备份路径',
  `port` int NULL DEFAULT NULL COMMENT '应用端口',
  `start_script` varchar(4000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '启动脚本',
  `deploy_script` varchar(4000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '部署脚本',
  `create_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `update_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建日期',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`app_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '应用管理' ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of mnt_app
-- ----------------------------

-- ----------------------------
-- Table structure for mnt_database
-- ----------------------------
DROP TABLE IF EXISTS `mnt_database`;
CREATE TABLE `mnt_database`  (
  `db_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ID',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '名称',
  `jdbc_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'jdbc连接',
  `user_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '账号',
  `pwd` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `create_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `update_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`db_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '数据库管理' ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of mnt_database
-- ----------------------------

-- ----------------------------
-- Table structure for mnt_deploy
-- ----------------------------
DROP TABLE IF EXISTS `mnt_deploy`;
CREATE TABLE `mnt_deploy`  (
  `deploy_id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `app_id` bigint NULL DEFAULT NULL COMMENT '应用编号',
  `create_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `update_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新者',
  `create_time` datetime NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`deploy_id`) USING BTREE,
  INDEX `FK6sy157pseoxx4fmcqr1vnvvhy`(`app_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '部署管理' ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of mnt_deploy
-- ----------------------------

-- ----------------------------
-- Table structure for mnt_deploy_history
-- ----------------------------
DROP TABLE IF EXISTS `mnt_deploy_history`;
CREATE TABLE `mnt_deploy_history`  (
  `history_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ID',
  `app_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '应用名称',
  `deploy_date` datetime NOT NULL COMMENT '部署日期',
  `deploy_user` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '部署用户',
  `ip` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '服务器IP',
  `deploy_id` bigint NULL DEFAULT NULL COMMENT '部署编号',
  PRIMARY KEY (`history_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '部署历史管理' ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of mnt_deploy_history
-- ----------------------------

-- ----------------------------
-- Table structure for mnt_deploy_server
-- ----------------------------
DROP TABLE IF EXISTS `mnt_deploy_server`;
CREATE TABLE `mnt_deploy_server`  (
  `deploy_id` bigint NOT NULL COMMENT '部署ID',
  `server_id` bigint NOT NULL COMMENT '服务ID',
  PRIMARY KEY (`deploy_id`, `server_id`) USING BTREE,
  INDEX `FKeaaha7jew9a02b3bk9ghols53`(`server_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '应用与服务器关联' ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of mnt_deploy_server
-- ----------------------------

-- ----------------------------
-- Table structure for mnt_server
-- ----------------------------
DROP TABLE IF EXISTS `mnt_server`;
CREATE TABLE `mnt_server`  (
  `server_id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `account` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '账号',
  `ip` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'IP地址',
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名称',
  `password` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `port` int NULL DEFAULT NULL COMMENT '端口',
  `create_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `update_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`server_id`) USING BTREE,
  INDEX `idx_ip`(`ip`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '服务器管理' ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of mnt_server
-- ----------------------------

-- ----------------------------
-- Table structure for question_info
-- ----------------------------
DROP TABLE IF EXISTS `question_info`;
CREATE TABLE `question_info`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `type` tinyint(1) NOT NULL DEFAULT 1 COMMENT '1-选择题, 2-简答',
  `multiply` tinyint(1) NULL DEFAULT 0 COMMENT '0-单选, 1-多选',
  `pid` int NULL DEFAULT 0 COMMENT '上级分类',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `analysis` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '答案解析',
  `status` tinyint(1) NOT NULL DEFAULT 1 COMMENT '1-启用, 0 -禁用',
  `right_answer` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '答案',
  `question` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '问题',
  `select_list` json NULL COMMENT '选项列表',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '问题列表' ROW_FORMAT = DYNAMIC;


-- ----------------------------
-- Table structure for question_menu
-- ----------------------------
DROP TABLE IF EXISTS `question_menu`;
CREATE TABLE `question_menu`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `status` tinyint(1) NOT NULL DEFAULT 1 COMMENT '1-启用,0-禁用',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `pid` int NOT NULL DEFAULT 0 COMMENT '上级标签',
  `category` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'href',
  `sort` int NOT NULL DEFAULT 0 COMMENT '排序字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'question menu info' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of question_menu
-- ----------------------------
INSERT INTO `question_menu` VALUES (1, '国学百科', 1, '2022-01-17 14:18:14', '2022-01-17 14:21:52', 0, NULL, 1);

INSERT INTO `question_menu` VALUES (3, '最强答题王', 1, '2022-01-17 15:12:15', '2022-01-17 15:12:15', 0, NULL, 3);

-- ----------------------------
-- Table structure for question_today
-- ----------------------------
DROP TABLE IF EXISTS `question_today`;
CREATE TABLE `question_today`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `qid` int NOT NULL DEFAULT 0 COMMENT '题目id',
  `integral` int NULL DEFAULT 0 COMMENT '答对获得积分',
  `status` tinyint(1) NULL DEFAULT 0 COMMENT '0-禁用, 1-启用',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '每日一题' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for question_today_answer
-- ----------------------------
DROP TABLE IF EXISTS `question_today_answer`;
CREATE TABLE `question_today_answer`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `qid` int NOT NULL DEFAULT 0 COMMENT '题目id',
  `integral` int NULL DEFAULT 0 COMMENT '获得积分',
  `uid` int NOT NULL DEFAULT 0 COMMENT '用户id',
  `user_answer` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '用户答案',
  `status` tinyint(1) NULL DEFAULT 0 COMMENT '0-答错, 1-答对',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '每日一题用户答题' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept`  (
  `dept_id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `pid` bigint NULL DEFAULT NULL COMMENT '上级部门',
  `sub_count` int NULL DEFAULT 0 COMMENT '子部门数目',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '名称',
  `dept_sort` int NULL DEFAULT 999 COMMENT '排序',
  `enabled` bit(1) NOT NULL COMMENT '状态',
  `create_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `update_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建日期',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`dept_id`) USING BTREE,
  INDEX `inx_pid`(`pid`) USING BTREE,
  INDEX `inx_enabled`(`enabled`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '部门' ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES (2, 7, 1, '研发部', 3, b'1', 'admin', 'admin', '2019-03-25 09:15:32', '2020-08-02 14:48:47');
INSERT INTO `sys_dept` VALUES (5, 7, 0, '运维部', 4, b'1', 'admin', 'admin', '2019-03-25 09:20:44', '2020-05-17 14:27:27');
INSERT INTO `sys_dept` VALUES (6, 8, 0, '测试部', 6, b'1', 'admin', 'admin', '2019-03-25 09:52:18', '2020-06-08 11:59:21');
INSERT INTO `sys_dept` VALUES (7, NULL, 2, '华南分部', 0, b'1', 'admin', 'admin', '2019-03-25 11:04:50', '2020-06-08 12:08:56');
INSERT INTO `sys_dept` VALUES (8, NULL, 2, '华北分部', 1, b'1', 'admin', 'admin', '2019-03-25 11:04:53', '2020-05-14 12:54:00');
INSERT INTO `sys_dept` VALUES (15, 8, 0, 'UI部门', 7, b'1', 'admin', 'admin', '2020-05-13 22:56:53', '2020-05-14 12:54:13');
INSERT INTO `sys_dept` VALUES (17, 2, 0, '研发一组', 999, b'1', 'admin', 'admin', '2020-08-02 14:49:07', '2020-08-02 14:49:07');

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict`  (
  `dict_id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '字典名称',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `create_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `update_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建日期',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`dict_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '数据字典' ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
INSERT INTO `sys_dict` VALUES (1, 'user_status', '用户状态', NULL, NULL, '2019-10-27 20:31:36', NULL);
INSERT INTO `sys_dict` VALUES (4, 'dept_status', '部门状态', NULL, NULL, '2019-10-27 20:31:36', NULL);
INSERT INTO `sys_dict` VALUES (5, 'job_status', '岗位状态', NULL, NULL, '2019-10-27 20:31:36', NULL);
INSERT INTO `sys_dict` VALUES (6, 'common_status', '通用字典状态', 'admin', 'admin', '2022-01-16 21:32:47', '2022-01-16 21:32:47');
INSERT INTO `sys_dict` VALUES (7, 'question_type', '问题类型', 'admin', 'admin', '2022-02-12 15:54:38', '2022-02-12 15:54:46');
INSERT INTO `sys_dict` VALUES (8, 'multiply_type', '是否多选', 'admin', 'admin', '2022-02-12 15:55:44', '2022-02-12 15:55:44');
INSERT INTO `sys_dict` VALUES (9, 'integral_type', '积分类型', 'admin', 'admin', '2022-02-21 11:15:42', '2022-02-21 11:15:42');
INSERT INTO `sys_dict` VALUES (10, 'export_type', '导出题库类型', 'admin', 'admin', '2022-03-11 15:48:24', '2022-03-11 15:48:24');
INSERT INTO `sys_dict` VALUES (11, 'correct_type', '纠错类型', 'admin', 'admin', '2022-03-24 10:54:27', '2022-03-24 10:54:27');
INSERT INTO `sys_dict` VALUES (12, 'question_answer_type', '答题正确与否', 'admin', 'admin', '2022-03-28 11:17:03', '2022-03-28 11:17:03');
INSERT INTO `sys_dict` VALUES (13, 'msg_type', '微信消息类型', 'admin', 'admin', '2022-04-02 10:29:57', '2022-04-02 10:29:57');
INSERT INTO `sys_dict` VALUES (14, 'wx_app_type', '微信app type', 'admin', 'admin', '2022-04-02 10:32:10', '2022-04-02 10:32:10');

-- ----------------------------
-- Table structure for sys_dict_detail
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_detail`;
CREATE TABLE `sys_dict_detail`  (
  `detail_id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `dict_id` bigint NULL DEFAULT NULL COMMENT '字典id',
  `label` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '字典标签',
  `value` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '字典值',
  `dict_sort` int NULL DEFAULT NULL COMMENT '排序',
  `create_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `update_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建日期',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`detail_id`) USING BTREE,
  INDEX `FK5tpkputc6d9nboxojdbgnpmyb`(`dict_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 29 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '数据字典详情' ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of sys_dict_detail
-- ----------------------------
INSERT INTO `sys_dict_detail` VALUES (1, 1, '激活', 'true', 1, NULL, NULL, '2019-10-27 20:31:36', NULL);
INSERT INTO `sys_dict_detail` VALUES (2, 1, '禁用', 'false', 2, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_detail` VALUES (3, 4, '启用', 'true', 1, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_detail` VALUES (4, 4, '停用', 'false', 2, NULL, NULL, '2019-10-27 20:31:36', NULL);
INSERT INTO `sys_dict_detail` VALUES (5, 5, '启用', 'true', 1, NULL, NULL, NULL, NULL);
INSERT INTO `sys_dict_detail` VALUES (6, 5, '停用', 'false', 2, NULL, NULL, '2019-10-27 20:31:36', NULL);
INSERT INTO `sys_dict_detail` VALUES (7, 6, '启用', '1', 1, 'admin', 'admin', '2022-01-16 21:32:56', '2022-03-23 17:51:57');
INSERT INTO `sys_dict_detail` VALUES (8, 6, '禁用', '0', 2, 'admin', 'admin', '2022-01-16 21:33:05', '2022-03-23 17:52:01');
INSERT INTO `sys_dict_detail` VALUES (9, 7, '选择题', '1', 1, 'admin', 'admin', '2022-02-12 15:55:03', '2022-02-12 15:55:12');
INSERT INTO `sys_dict_detail` VALUES (10, 7, '简答题', '2', 2, 'admin', 'admin', '2022-02-12 15:55:19', '2022-02-12 15:55:19');
INSERT INTO `sys_dict_detail` VALUES (11, 8, '是', '1', 1, 'admin', 'admin', '2022-02-12 15:55:51', '2022-02-12 15:56:03');
INSERT INTO `sys_dict_detail` VALUES (12, 8, '否', '0', 2, 'admin', 'admin', '2022-02-12 15:55:59', '2022-02-12 15:55:59');
INSERT INTO `sys_dict_detail` VALUES (13, 9, '增加', '1', 0, 'admin', 'admin', '2022-02-21 11:15:57', '2022-02-21 11:15:57');
INSERT INTO `sys_dict_detail` VALUES (14, 9, '减少', '2', 2, 'admin', 'admin', '2022-02-21 11:16:10', '2022-02-21 11:16:10');
INSERT INTO `sys_dict_detail` VALUES (15, 10, '用户提交', '0', 1, 'admin', 'admin', '2022-03-11 15:48:39', '2022-03-11 15:49:01');
INSERT INTO `sys_dict_detail` VALUES (16, 10, '导出成功', '1', 2, 'admin', 'admin', '2022-03-11 15:48:49', '2022-03-11 15:49:10');
INSERT INTO `sys_dict_detail` VALUES (17, 10, '导出失败', '2', 3, 'admin', 'admin', '2022-03-11 15:49:21', '2022-03-11 15:49:21');
INSERT INTO `sys_dict_detail` VALUES (18, 7, '判断题', '3', 3, 'admin', 'admin', '2022-03-23 16:53:47', '2022-03-23 16:53:47');
INSERT INTO `sys_dict_detail` VALUES (19, 11, '待审核', '1', 1, 'admin', 'admin', '2022-03-24 10:54:38', '2022-03-24 10:54:38');
INSERT INTO `sys_dict_detail` VALUES (20, 11, '审核通过', '2', 2, 'admin', 'admin', '2022-03-24 10:54:44', '2022-03-24 10:54:44');
INSERT INTO `sys_dict_detail` VALUES (21, 11, '审核不通过', '3', 3, 'admin', 'admin', '2022-03-24 10:54:56', '2022-03-24 10:54:56');
INSERT INTO `sys_dict_detail` VALUES (22, 12, '答对', '1', 1, 'admin', 'admin', '2022-03-28 11:17:15', '2022-03-28 11:17:15');
INSERT INTO `sys_dict_detail` VALUES (23, 12, '答错', '0', 2, 'admin', 'admin', '2022-03-28 11:17:32', '2022-03-28 11:17:32');
INSERT INTO `sys_dict_detail` VALUES (24, 13, 'XML', 'xml', 1, 'admin', 'admin', '2022-04-02 10:30:09', '2022-04-02 10:30:09');
INSERT INTO `sys_dict_detail` VALUES (25, 13, 'JSON', 'json', 2, 'admin', 'admin', '2022-04-02 10:30:16', '2022-04-02 10:30:16');
INSERT INTO `sys_dict_detail` VALUES (26, 14, '小程序', '1', 1, 'admin', 'admin', '2022-04-02 10:32:25', '2022-04-02 10:32:25');
INSERT INTO `sys_dict_detail` VALUES (27, 14, '公众号', '2', 2, 'admin', 'admin', '2022-04-02 10:32:35', '2022-04-02 10:32:35');
INSERT INTO `sys_dict_detail` VALUES (28, 14, '服务号', '3', 3, 'admin', 'admin', '2022-04-02 10:32:46', '2022-04-02 10:32:46');

-- ----------------------------
-- Table structure for sys_job
-- ----------------------------
DROP TABLE IF EXISTS `sys_job`;
CREATE TABLE `sys_job`  (
  `job_id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '岗位名称',
  `enabled` bit(1) NOT NULL COMMENT '岗位状态',
  `job_sort` int NULL DEFAULT NULL COMMENT '排序',
  `create_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `update_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建日期',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`job_id`) USING BTREE,
  UNIQUE INDEX `uniq_name`(`name`) USING BTREE,
  INDEX `inx_enabled`(`enabled`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '岗位' ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of sys_job
-- ----------------------------
INSERT INTO `sys_job` VALUES (8, '人事专员', b'1', 3, NULL, NULL, '2019-03-29 14:52:28', NULL);
INSERT INTO `sys_job` VALUES (10, '产品经理', b'1', 4, NULL, NULL, '2019-03-29 14:55:51', NULL);
INSERT INTO `sys_job` VALUES (11, '全栈开发', b'1', 2, NULL, 'admin', '2019-03-31 13:39:30', '2020-05-05 11:33:43');
INSERT INTO `sys_job` VALUES (12, '软件测试', b'1', 5, NULL, 'admin', '2019-03-31 13:39:43', '2020-05-10 19:56:26');

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log`  (
  `log_id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `log_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `method` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `params` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `request_ip` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `time` bigint NULL DEFAULT NULL,
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `browser` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `exception_detail` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `create_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`log_id`) USING BTREE,
  INDEX `log_create_time_index`(`create_time`) USING BTREE,
  INDEX `inx_log_type`(`log_type`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统日志' ROW_FORMAT = COMPACT;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `menu_id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `pid` bigint NULL DEFAULT NULL COMMENT '上级菜单ID',
  `sub_count` int NULL DEFAULT 0 COMMENT '子菜单数目',
  `type` int NULL DEFAULT NULL COMMENT '菜单类型',
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单标题',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '组件名称',
  `component` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '组件',
  `menu_sort` int NULL DEFAULT NULL COMMENT '排序',
  `icon` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图标',
  `path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '链接地址',
  `i_frame` bit(1) NULL DEFAULT NULL COMMENT '是否外链',
  `cache` bit(1) NULL DEFAULT b'0' COMMENT '缓存',
  `hidden` bit(1) NULL DEFAULT b'0' COMMENT '隐藏',
  `permission` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限',
  `create_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `update_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建日期',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`menu_id`) USING BTREE,
  UNIQUE INDEX `uniq_title`(`title`) USING BTREE,
  UNIQUE INDEX `uniq_name`(`name`) USING BTREE,
  INDEX `inx_pid`(`pid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 140 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统菜单' ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, NULL, 7, 0, '系统管理', NULL, NULL, 1, 'system', 'system', b'0', b'0', b'0', NULL, NULL, NULL, '2018-12-18 15:11:29', NULL);
INSERT INTO `sys_menu` VALUES (2, 1, 3, 1, '用户管理', 'User', 'system/user/index', 2, 'peoples', 'user', b'0', b'0', b'0', 'user:list', NULL, NULL, '2018-12-18 15:14:44', NULL);
INSERT INTO `sys_menu` VALUES (3, 1, 3, 1, '角色管理', 'Role', 'system/role/index', 3, 'role', 'role', b'0', b'0', b'0', 'roles:list', NULL, NULL, '2018-12-18 15:16:07', NULL);
INSERT INTO `sys_menu` VALUES (5, 1, 3, 1, '菜单管理', 'Menu', 'system/menu/index', 5, 'menu', 'menu', b'0', b'0', b'0', 'menu:list', NULL, NULL, '2018-12-18 15:17:28', NULL);
INSERT INTO `sys_menu` VALUES (6, NULL, 5, 0, '系统监控', NULL, NULL, 10, 'monitor', 'monitor', b'0', b'0', b'0', NULL, NULL, NULL, '2018-12-18 15:17:48', NULL);
INSERT INTO `sys_menu` VALUES (7, 6, 0, 1, '操作日志', 'Log', 'monitor/log/index', 11, 'log', 'logs', b'0', b'1', b'0', NULL, NULL, 'admin', '2018-12-18 15:18:26', '2020-06-06 13:11:57');
INSERT INTO `sys_menu` VALUES (9, 6, 0, 1, 'SQL监控', 'Sql', 'monitor/sql/index', 18, 'sqlMonitor', 'druid', b'0', b'0', b'0', NULL, NULL, NULL, '2018-12-18 15:19:34', NULL);
INSERT INTO `sys_menu` VALUES (10, NULL, 5, 0, '组件管理', NULL, NULL, 50, 'zujian', 'components', b'0', b'0', b'0', NULL, NULL, NULL, '2018-12-19 13:38:16', NULL);
INSERT INTO `sys_menu` VALUES (11, 10, 0, 1, '图标库', 'Icons', 'components/icons/index', 51, 'icon', 'icon', b'0', b'0', b'0', NULL, NULL, NULL, '2018-12-19 13:38:49', NULL);
INSERT INTO `sys_menu` VALUES (14, 36, 0, 1, '邮件工具', 'Email', 'tools/email/index', 35, 'email', 'email', b'0', b'0', b'0', NULL, NULL, NULL, '2018-12-27 10:13:09', NULL);
INSERT INTO `sys_menu` VALUES (15, 10, 0, 1, '富文本', 'Editor', 'components/Editor', 52, 'fwb', 'tinymce', b'0', b'0', b'0', NULL, NULL, NULL, '2018-12-27 11:58:25', NULL);
INSERT INTO `sys_menu` VALUES (18, 36, 3, 1, '存储管理', 'Storage', 'tools/storage/index', 34, 'qiniu', 'storage', b'0', b'0', b'0', 'storage:list', NULL, NULL, '2018-12-31 11:12:15', NULL);
INSERT INTO `sys_menu` VALUES (19, 36, 0, 1, '支付宝工具', 'AliPay', 'tools/aliPay/index', 37, 'alipay', 'aliPay', b'0', b'0', b'0', NULL, NULL, NULL, '2018-12-31 14:52:38', NULL);
INSERT INTO `sys_menu` VALUES (21, NULL, 2, 0, '多级菜单', NULL, '', 900, 'menu', 'nested', b'0', b'0', b'0', NULL, NULL, 'admin', '2019-01-04 16:22:03', '2020-06-21 17:27:35');
INSERT INTO `sys_menu` VALUES (22, 21, 2, 0, '二级菜单1', NULL, '', 999, 'menu', 'menu1', b'0', b'0', b'0', NULL, NULL, 'admin', '2019-01-04 16:23:29', '2020-06-21 17:27:20');
INSERT INTO `sys_menu` VALUES (23, 21, 0, 1, '二级菜单2', NULL, 'nested/menu2/index', 999, 'menu', 'menu2', b'0', b'0', b'0', NULL, NULL, NULL, '2019-01-04 16:23:57', NULL);
INSERT INTO `sys_menu` VALUES (24, 22, 0, 1, '三级菜单1', 'Test', 'nested/menu1/menu1-1', 999, 'menu', 'menu1-1', b'0', b'0', b'0', NULL, NULL, NULL, '2019-01-04 16:24:48', NULL);
INSERT INTO `sys_menu` VALUES (27, 22, 0, 1, '三级菜单2', NULL, 'nested/menu1/menu1-2', 999, 'menu', 'menu1-2', b'0', b'0', b'0', NULL, NULL, NULL, '2019-01-07 17:27:32', NULL);
INSERT INTO `sys_menu` VALUES (28, 1, 3, 1, '任务调度', 'Timing', 'system/timing/index', 999, 'timing', 'timing', b'0', b'0', b'0', 'timing:list', NULL, NULL, '2019-01-07 20:34:40', NULL);
INSERT INTO `sys_menu` VALUES (30, 36, 0, 1, '代码生成', 'GeneratorIndex', 'generator/index', 32, 'dev', 'generator', b'0', b'1', b'0', NULL, NULL, NULL, '2019-01-11 15:45:55', NULL);
INSERT INTO `sys_menu` VALUES (32, 6, 0, 1, '异常日志', 'ErrorLog', 'monitor/log/errorLog', 12, 'error', 'errorLog', b'0', b'0', b'0', NULL, NULL, NULL, '2019-01-13 13:49:03', NULL);
INSERT INTO `sys_menu` VALUES (33, 10, 0, 1, 'Markdown', 'Markdown', 'components/MarkDown', 53, 'markdown', 'markdown', b'0', b'0', b'0', NULL, NULL, NULL, '2019-03-08 13:46:44', NULL);
INSERT INTO `sys_menu` VALUES (34, 10, 0, 1, 'Yaml编辑器', 'YamlEdit', 'components/YamlEdit', 54, 'dev', 'yaml', b'0', b'0', b'0', NULL, NULL, NULL, '2019-03-08 15:49:40', NULL);
INSERT INTO `sys_menu` VALUES (35, 1, 3, 1, '部门管理', 'Dept', 'system/dept/index', 6, 'dept', 'dept', b'0', b'0', b'0', 'dept:list', NULL, NULL, '2019-03-25 09:46:00', NULL);
INSERT INTO `sys_menu` VALUES (36, NULL, 7, 0, '系统工具', NULL, '', 30, 'sys-tools', 'sys-tools', b'0', b'0', b'0', NULL, NULL, NULL, '2019-03-29 10:57:35', NULL);
INSERT INTO `sys_menu` VALUES (37, 1, 3, 1, '岗位管理', 'Job', 'system/job/index', 7, 'Steve-Jobs', 'job', b'0', b'0', b'0', 'job:list', NULL, NULL, '2019-03-29 13:51:18', NULL);
INSERT INTO `sys_menu` VALUES (38, 36, 0, 1, '接口文档', 'Swagger', 'tools/swagger/index', 36, 'swagger', 'swagger2', b'0', b'0', b'0', NULL, NULL, NULL, '2019-03-29 19:57:53', NULL);
INSERT INTO `sys_menu` VALUES (39, 1, 3, 1, '字典管理', 'Dict', 'system/dict/index', 8, 'dictionary', 'dict', b'0', b'0', b'0', 'dict:list', NULL, NULL, '2019-04-10 11:49:04', NULL);
INSERT INTO `sys_menu` VALUES (41, 6, 0, 1, '在线用户', 'OnlineUser', 'monitor/online/index', 10, 'Steve-Jobs', 'online', b'0', b'0', b'0', NULL, NULL, NULL, '2019-10-26 22:08:43', NULL);
INSERT INTO `sys_menu` VALUES (44, 2, 0, 2, '用户新增', NULL, '', 2, '', '', b'0', b'0', b'0', 'user:add', NULL, NULL, '2019-10-29 10:59:46', NULL);
INSERT INTO `sys_menu` VALUES (45, 2, 0, 2, '用户编辑', NULL, '', 3, '', '', b'0', b'0', b'0', 'user:edit', NULL, NULL, '2019-10-29 11:00:08', NULL);
INSERT INTO `sys_menu` VALUES (46, 2, 0, 2, '用户删除', NULL, '', 4, '', '', b'0', b'0', b'0', 'user:del', NULL, NULL, '2019-10-29 11:00:23', NULL);
INSERT INTO `sys_menu` VALUES (48, 3, 0, 2, '角色创建', NULL, '', 2, '', '', b'0', b'0', b'0', 'roles:add', NULL, NULL, '2019-10-29 12:45:34', NULL);
INSERT INTO `sys_menu` VALUES (49, 3, 0, 2, '角色修改', NULL, '', 3, '', '', b'0', b'0', b'0', 'roles:edit', NULL, NULL, '2019-10-29 12:46:16', NULL);
INSERT INTO `sys_menu` VALUES (50, 3, 0, 2, '角色删除', NULL, '', 4, '', '', b'0', b'0', b'0', 'roles:del', NULL, NULL, '2019-10-29 12:46:51', NULL);
INSERT INTO `sys_menu` VALUES (52, 5, 0, 2, '菜单新增', NULL, '', 2, '', '', b'0', b'0', b'0', 'menu:add', NULL, NULL, '2019-10-29 12:55:07', NULL);
INSERT INTO `sys_menu` VALUES (53, 5, 0, 2, '菜单编辑', NULL, '', 3, '', '', b'0', b'0', b'0', 'menu:edit', NULL, NULL, '2019-10-29 12:55:40', NULL);
INSERT INTO `sys_menu` VALUES (54, 5, 0, 2, '菜单删除', NULL, '', 4, '', '', b'0', b'0', b'0', 'menu:del', NULL, NULL, '2019-10-29 12:56:00', NULL);
INSERT INTO `sys_menu` VALUES (56, 35, 0, 2, '部门新增', NULL, '', 2, '', '', b'0', b'0', b'0', 'dept:add', NULL, NULL, '2019-10-29 12:57:09', NULL);
INSERT INTO `sys_menu` VALUES (57, 35, 0, 2, '部门编辑', NULL, '', 3, '', '', b'0', b'0', b'0', 'dept:edit', NULL, NULL, '2019-10-29 12:57:27', NULL);
INSERT INTO `sys_menu` VALUES (58, 35, 0, 2, '部门删除', NULL, '', 4, '', '', b'0', b'0', b'0', 'dept:del', NULL, NULL, '2019-10-29 12:57:41', NULL);
INSERT INTO `sys_menu` VALUES (60, 37, 0, 2, '岗位新增', NULL, '', 2, '', '', b'0', b'0', b'0', 'job:add', NULL, NULL, '2019-10-29 12:58:27', NULL);
INSERT INTO `sys_menu` VALUES (61, 37, 0, 2, '岗位编辑', NULL, '', 3, '', '', b'0', b'0', b'0', 'job:edit', NULL, NULL, '2019-10-29 12:58:45', NULL);
INSERT INTO `sys_menu` VALUES (62, 37, 0, 2, '岗位删除', NULL, '', 4, '', '', b'0', b'0', b'0', 'job:del', NULL, NULL, '2019-10-29 12:59:04', NULL);
INSERT INTO `sys_menu` VALUES (64, 39, 0, 2, '字典新增', NULL, '', 2, '', '', b'0', b'0', b'0', 'dict:add', NULL, NULL, '2019-10-29 13:00:17', NULL);
INSERT INTO `sys_menu` VALUES (65, 39, 0, 2, '字典编辑', NULL, '', 3, '', '', b'0', b'0', b'0', 'dict:edit', NULL, NULL, '2019-10-29 13:00:42', NULL);
INSERT INTO `sys_menu` VALUES (66, 39, 0, 2, '字典删除', NULL, '', 4, '', '', b'0', b'0', b'0', 'dict:del', NULL, NULL, '2019-10-29 13:00:59', NULL);
INSERT INTO `sys_menu` VALUES (73, 28, 0, 2, '任务新增', NULL, '', 2, '', '', b'0', b'0', b'0', 'timing:add', NULL, NULL, '2019-10-29 13:07:28', NULL);
INSERT INTO `sys_menu` VALUES (74, 28, 0, 2, '任务编辑', NULL, '', 3, '', '', b'0', b'0', b'0', 'timing:edit', NULL, NULL, '2019-10-29 13:07:41', NULL);
INSERT INTO `sys_menu` VALUES (75, 28, 0, 2, '任务删除', NULL, '', 4, '', '', b'0', b'0', b'0', 'timing:del', NULL, NULL, '2019-10-29 13:07:54', NULL);
INSERT INTO `sys_menu` VALUES (77, 18, 0, 2, '上传文件', NULL, '', 2, '', '', b'0', b'0', b'0', 'storage:add', NULL, NULL, '2019-10-29 13:09:09', NULL);
INSERT INTO `sys_menu` VALUES (78, 18, 0, 2, '文件编辑', NULL, '', 3, '', '', b'0', b'0', b'0', 'storage:edit', NULL, NULL, '2019-10-29 13:09:22', NULL);
INSERT INTO `sys_menu` VALUES (79, 18, 0, 2, '文件删除', NULL, '', 4, '', '', b'0', b'0', b'0', 'storage:del', NULL, NULL, '2019-10-29 13:09:34', NULL);
INSERT INTO `sys_menu` VALUES (80, 6, 0, 1, '服务监控', 'ServerMonitor', 'monitor/server/index', 14, 'codeConsole', 'server', b'0', b'0', b'0', 'monitor:list', NULL, 'admin', '2019-11-07 13:06:39', '2020-05-04 18:20:50');
INSERT INTO `sys_menu` VALUES (82, 36, 0, 1, '生成配置', 'GeneratorConfig', 'generator/config', 33, 'dev', 'generator/config/:tableName', b'0', b'1', b'1', '', NULL, NULL, '2019-11-17 20:08:56', NULL);
INSERT INTO `sys_menu` VALUES (83, 10, 0, 1, '图表库', 'Echarts', 'components/Echarts', 50, 'chart', 'echarts', b'0', b'1', b'0', '', NULL, NULL, '2019-11-21 09:04:32', NULL);
INSERT INTO `sys_menu` VALUES (90, NULL, 5, 1, '运维管理', 'Mnt', '', 20, 'mnt', 'mnt', b'0', b'0', b'0', NULL, NULL, NULL, '2019-11-09 10:31:08', NULL);
INSERT INTO `sys_menu` VALUES (92, 90, 3, 1, '服务器', 'ServerDeploy', 'mnt/server/index', 22, 'server', 'mnt/serverDeploy', b'0', b'0', b'0', 'serverDeploy:list', NULL, NULL, '2019-11-10 10:29:25', NULL);
INSERT INTO `sys_menu` VALUES (93, 90, 3, 1, '应用管理', 'App', 'mnt/app/index', 23, 'app', 'mnt/app', b'0', b'0', b'0', 'app:list', NULL, NULL, '2019-11-10 11:05:16', NULL);
INSERT INTO `sys_menu` VALUES (94, 90, 3, 1, '部署管理', 'Deploy', 'mnt/deploy/index', 24, 'deploy', 'mnt/deploy', b'0', b'0', b'0', 'deploy:list', NULL, NULL, '2019-11-10 15:56:55', NULL);
INSERT INTO `sys_menu` VALUES (97, 90, 1, 1, '部署备份', 'DeployHistory', 'mnt/deployHistory/index', 25, 'backup', 'mnt/deployHistory', b'0', b'0', b'0', 'deployHistory:list', NULL, NULL, '2019-11-10 16:49:44', NULL);
INSERT INTO `sys_menu` VALUES (98, 90, 3, 1, '数据库管理', 'Database', 'mnt/database/index', 26, 'database', 'mnt/database', b'0', b'0', b'0', 'database:list', NULL, NULL, '2019-11-10 20:40:04', NULL);
INSERT INTO `sys_menu` VALUES (102, 97, 0, 2, '删除', NULL, '', 999, '', '', b'0', b'0', b'0', 'deployHistory:del', NULL, NULL, '2019-11-17 09:32:48', NULL);
INSERT INTO `sys_menu` VALUES (103, 92, 0, 2, '服务器新增', NULL, '', 999, '', '', b'0', b'0', b'0', 'serverDeploy:add', NULL, NULL, '2019-11-17 11:08:33', NULL);
INSERT INTO `sys_menu` VALUES (104, 92, 0, 2, '服务器编辑', NULL, '', 999, '', '', b'0', b'0', b'0', 'serverDeploy:edit', NULL, NULL, '2019-11-17 11:08:57', NULL);
INSERT INTO `sys_menu` VALUES (105, 92, 0, 2, '服务器删除', NULL, '', 999, '', '', b'0', b'0', b'0', 'serverDeploy:del', NULL, NULL, '2019-11-17 11:09:15', NULL);
INSERT INTO `sys_menu` VALUES (106, 93, 0, 2, '应用新增', NULL, '', 999, '', '', b'0', b'0', b'0', 'app:add', NULL, NULL, '2019-11-17 11:10:03', NULL);
INSERT INTO `sys_menu` VALUES (107, 93, 0, 2, '应用编辑', NULL, '', 999, '', '', b'0', b'0', b'0', 'app:edit', NULL, NULL, '2019-11-17 11:10:28', NULL);
INSERT INTO `sys_menu` VALUES (108, 93, 0, 2, '应用删除', NULL, '', 999, '', '', b'0', b'0', b'0', 'app:del', NULL, NULL, '2019-11-17 11:10:55', NULL);
INSERT INTO `sys_menu` VALUES (109, 94, 0, 2, '部署新增', NULL, '', 999, '', '', b'0', b'0', b'0', 'deploy:add', NULL, NULL, '2019-11-17 11:11:22', NULL);
INSERT INTO `sys_menu` VALUES (110, 94, 0, 2, '部署编辑', NULL, '', 999, '', '', b'0', b'0', b'0', 'deploy:edit', NULL, NULL, '2019-11-17 11:11:41', NULL);
INSERT INTO `sys_menu` VALUES (111, 94, 0, 2, '部署删除', NULL, '', 999, '', '', b'0', b'0', b'0', 'deploy:del', NULL, NULL, '2019-11-17 11:12:01', NULL);
INSERT INTO `sys_menu` VALUES (112, 98, 0, 2, '数据库新增', NULL, '', 999, '', '', b'0', b'0', b'0', 'database:add', NULL, NULL, '2019-11-17 11:12:43', NULL);
INSERT INTO `sys_menu` VALUES (113, 98, 0, 2, '数据库编辑', NULL, '', 999, '', '', b'0', b'0', b'0', 'database:edit', NULL, NULL, '2019-11-17 11:12:58', NULL);
INSERT INTO `sys_menu` VALUES (114, 98, 0, 2, '数据库删除', NULL, '', 999, '', '', b'0', b'0', b'0', 'database:del', NULL, NULL, '2019-11-17 11:13:14', NULL);
INSERT INTO `sys_menu` VALUES (116, 36, 0, 1, '生成预览', 'Preview', 'generator/preview', 999, 'java', 'generator/preview/:tableName', b'0', b'1', b'1', NULL, NULL, NULL, '2019-11-26 14:54:36', NULL);
INSERT INTO `sys_menu` VALUES (118, NULL, 5, 0, '问答管理', NULL, NULL, 100, 'education', 'question', b'0', b'0', b'0', NULL, 'admin', 'admin', '2022-01-17 13:50:16', '2022-01-17 13:50:35');
INSERT INTO `sys_menu` VALUES (119, 118, 2, 1, '题库分类', 'QuestionMenu', 'question/menu/index', 1, 'menu', 'menu', b'0', b'0', b'0', 'questionMenu:list', 'admin', 'admin', '2022-01-17 13:52:37', '2022-01-17 14:04:51');
INSERT INTO `sys_menu` VALUES (120, 119, 0, 2, '新增题库分类', NULL, NULL, 1, NULL, NULL, b'0', b'0', b'0', 'questionMenu:add', 'admin', 'admin', '2022-01-17 13:53:23', '2022-03-24 10:48:12');
INSERT INTO `sys_menu` VALUES (121, 119, 0, 2, '修改题库分类', NULL, NULL, 2, NULL, NULL, b'0', b'0', b'0', 'questionMenu:edit', 'admin', 'admin', '2022-01-17 13:54:14', '2022-03-24 10:48:26');
INSERT INTO `sys_menu` VALUES (122, NULL, 5, 0, '微信用户', NULL, NULL, 101, 'peoples', 'wxUsers', b'0', b'0', b'0', NULL, 'admin', 'admin', '2022-03-22 12:58:33', '2022-03-22 12:58:57');
INSERT INTO `sys_menu` VALUES (123, 122, 0, 1, '用户列表', 'WxUser', 'wx/user/index', 1, 'user', 'wxuser', b'0', b'0', b'0', 'wxUser:list', 'admin', 'admin', '2022-03-22 13:01:50', '2022-03-22 13:01:50');
INSERT INTO `sys_menu` VALUES (124, 122, 0, 1, '用户信息', 'UserInfo', 'user/info/index', 2, 'visits', 'wxUserInfo', b'0', b'0', b'0', 'userInfo:list', 'admin', 'admin', '2022-03-22 13:52:11', '2022-03-22 13:52:11');
INSERT INTO `sys_menu` VALUES (125, 118, 0, 1, '题库列表', 'QuestionInfo', 'question/index/index', 2, 'education', 'questionInfo', b'0', b'0', b'0', 'questionInfo:list', 'admin', 'admin', '2022-03-23 16:52:32', '2022-03-23 16:52:32');
INSERT INTO `sys_menu` VALUES (126, 118, 0, 1, '题库纠错', 'UserCorrectQuestion', 'question/correct/index', 3, 'tools', 'userCorrectQuestion', b'0', b'0', b'0', 'userCorrectQuestion:list', 'admin', 'admin', '2022-03-24 10:50:35', '2022-03-24 10:50:35');
INSERT INTO `sys_menu` VALUES (127, NULL, 2, 0, 'token管理', NULL, NULL, 8, 'app', 'token', b'0', b'0', b'0', NULL, 'admin', 'admin', '2022-03-30 21:11:24', '2022-03-30 21:11:24');
INSERT INTO `sys_menu` VALUES (128, 127, 0, 1, '令牌信息', 'SysToken', 'system/token/index', 1, 'develop', '/sys/token', b'0', b'0', b'0', 'sysToken:list', 'admin', 'admin', '2022-03-30 21:13:04', '2022-03-30 21:13:04');
INSERT INTO `sys_menu` VALUES (129, 127, 0, 1, '微信app', 'WxApp', 'wx/app/index', 2, 'wechat', 'wx/app', b'0', b'0', b'0', 'wxApp:list', 'admin', 'admin', '2022-04-02 10:41:54', '2022-04-02 10:41:54');
INSERT INTO `sys_menu` VALUES (130, 118, 0, 1, '每日一题', 'QuestionToday', 'question/today/index', 4, 'date', 'question/today', b'0', b'0', b'0', 'questionToday:list', 'admin', 'admin', '2022-04-04 15:47:16', '2022-04-04 15:47:16');
INSERT INTO `sys_menu` VALUES (131, 118, 0, 1, '每日答题', 'QuestionTodayAnswer', 'question/todayAnswer/index', 5, 'database', 'question/today/answer', b'0', b'0', b'0', 'questionTodayAnswer:list', 'admin', 'admin', '2022-04-04 15:55:59', '2022-04-04 15:55:59');
INSERT INTO `sys_menu` VALUES (132, NULL, 1, 0, '导出管理', NULL, NULL, 101, 'download', 'export', b'0', b'0', b'0', NULL, 'admin', 'admin', '2022-04-04 16:11:29', '2022-04-04 16:16:39');
INSERT INTO `sys_menu` VALUES (133, 132, 0, 1, '导出下载', 'UserExportLog', 'question/exportq/index', 1, 'develop', 'export/list', b'0', b'0', b'0', 'userExportLog:list', 'admin', 'admin', '2022-04-04 16:19:14', '2022-04-04 16:19:14');
INSERT INTO `sys_menu` VALUES (134, NULL, 2, 0, '试卷管理', NULL, NULL, 102, 'education', 'exam', b'0', b'0', b'0', NULL, 'admin', 'admin', '2022-04-04 21:18:01', '2022-04-04 21:18:01');
INSERT INTO `sys_menu` VALUES (135, 134, 0, 1, '试卷分类', 'ExamMenu', 'exam/menu/index', 1, 'menu', 'exam/menu', b'0', b'0', b'0', 'examMenu:list', 'admin', 'admin', '2022-04-04 21:20:01', '2022-04-04 21:20:01');
INSERT INTO `sys_menu` VALUES (136, 134, 0, 1, '试卷信息', 'ExamInfo', 'exam/index', 2, 'swagger', 'exam/info', b'0', b'0', b'0', 'examInfo:list', 'admin', 'admin', '2022-04-04 21:40:53', '2022-04-04 21:40:53');
INSERT INTO `sys_menu` VALUES (137, 122, 0, 1, '签到记录', 'UserSignLog', 'user/sign/index', 3, 'login', 'user/sign', b'0', b'0', b'0', 'userSignLog:list', 'admin', 'admin', '2022-04-05 15:39:35', '2022-04-05 15:39:35');
INSERT INTO `sys_menu` VALUES (138, 122, 0, 1, '邀请记录', 'UserInviteLog', 'user/invite/index', 4, 'link', 'user/invite', b'0', b'0', b'0', 'userInviteLog:list', 'admin', 'admin', '2022-04-05 15:46:35', '2022-04-05 15:46:35');
INSERT INTO `sys_menu` VALUES (139, 122, 0, 1, '积分记录', 'UserIntegralLog', 'user/integral/index', 4, 'log', 'integral', b'0', b'0', b'0', 'userIntegralLog:list', 'admin', 'admin', '2022-04-06 16:39:52', '2022-04-06 16:39:52');

-- ----------------------------
-- Table structure for sys_quartz_job
-- ----------------------------
DROP TABLE IF EXISTS `sys_quartz_job`;
CREATE TABLE `sys_quartz_job`  (
  `job_id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `bean_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Spring Bean名称',
  `cron_expression` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'cron 表达式',
  `is_pause` bit(1) NULL DEFAULT NULL COMMENT '状态：1暂停、0启用',
  `job_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '任务名称',
  `method_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '方法名称',
  `params` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '参数',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `person_in_charge` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '负责人',
  `email` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '报警邮箱',
  `sub_task` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '子任务ID',
  `pause_after_failure` bit(1) NULL DEFAULT NULL COMMENT '任务失败后是否暂停',
  `create_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `update_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建日期',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`job_id`) USING BTREE,
  INDEX `inx_is_pause`(`is_pause`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '定时任务' ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of sys_quartz_job
-- ----------------------------
INSERT INTO `sys_quartz_job` VALUES (2, 'testTask', '0/5 * * * * ?', b'1', '测试1', 'run1', 'test', '带参测试，多参使用json', '测试', NULL, NULL, NULL, NULL, 'admin', '2019-08-22 14:08:29', '2020-05-24 13:58:33');
INSERT INTO `sys_quartz_job` VALUES (3, 'testTask', '0/5 * * * * ?', b'1', '测试', 'run', '', '不带参测试', 'Zheng Jie', '', '5,6', b'1', NULL, 'admin', '2019-09-26 16:44:39', '2020-05-24 14:48:12');
INSERT INTO `sys_quartz_job` VALUES (5, 'Test', '0/5 * * * * ?', b'1', '任务告警测试', 'run', NULL, '测试', 'test', '', NULL, b'1', 'admin', 'admin', '2020-05-05 20:32:41', '2020-05-05 20:36:13');
INSERT INTO `sys_quartz_job` VALUES (6, 'testTask', '0/5 * * * * ?', b'1', '测试3', 'run2', NULL, '测试3', 'Zheng Jie', '', NULL, b'1', 'admin', 'admin', '2020-05-05 20:35:41', '2020-05-05 20:36:07');
INSERT INTO `sys_quartz_job` VALUES (7, 'exportQuestionTask', '0 0/5 * * * ? * ', b'0', '导出题库', 'handlerExportQuestion', NULL, '处理导出请求', 'mark', NULL, NULL, b'1', 'admin', 'admin', '2022-03-29 17:49:29', '2022-03-29 17:55:49');
INSERT INTO `sys_quartz_job` VALUES (8, 'refreshQuestionTask', '1 0 0 * * ? * ', b'0', '刷新每日一题', 'handlerRefreshQuestion', NULL, '刷新每日一题', 'mark', NULL, NULL, b'1', 'admin', 'admin', '2022-03-29 17:55:11', '2022-04-02 21:46:11');
INSERT INTO `sys_quartz_job` VALUES (9, 'exportQuestionTask', '0 0/5 * * * ? *', b'0', '下载试卷', 'handlerExportExam', NULL, '下载试卷', 'mark', NULL, NULL, b'1', 'admin', 'admin', '2022-04-02 21:45:44', '2022-04-02 21:45:44');

-- ----------------------------
-- Table structure for sys_quartz_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_quartz_log`;
CREATE TABLE `sys_quartz_log`  (
  `log_id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `bean_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `cron_expression` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `exception_detail` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `is_success` bit(1) NULL DEFAULT NULL,
  `job_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `method_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `params` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `time` bigint NULL DEFAULT NULL,
  PRIMARY KEY (`log_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '定时任务日志' ROW_FORMAT = COMPACT;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `role_id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '名称',
  `level` int NULL DEFAULT NULL COMMENT '角色级别',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `data_scope` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '数据权限',
  `create_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `update_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建日期',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`role_id`) USING BTREE,
  UNIQUE INDEX `uniq_name`(`name`) USING BTREE,
  INDEX `role_name_index`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色表' ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '超级管理员', 1, '-', '全部', NULL, 'admin', '2018-11-23 11:04:37', '2022-01-17 13:54:47');
INSERT INTO `sys_role` VALUES (2, '普通用户', 2, '-', '本级', NULL, 'admin', '2018-11-23 13:09:06', '2020-09-05 10:45:12');

-- ----------------------------
-- Table structure for sys_roles_depts
-- ----------------------------
DROP TABLE IF EXISTS `sys_roles_depts`;
CREATE TABLE `sys_roles_depts`  (
  `role_id` bigint NOT NULL,
  `dept_id` bigint NOT NULL,
  PRIMARY KEY (`role_id`, `dept_id`) USING BTREE,
  INDEX `FK7qg6itn5ajdoa9h9o78v9ksur`(`dept_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色部门关联' ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of sys_roles_depts
-- ----------------------------

-- ----------------------------
-- Table structure for sys_roles_menus
-- ----------------------------
DROP TABLE IF EXISTS `sys_roles_menus`;
CREATE TABLE `sys_roles_menus`  (
  `menu_id` bigint NOT NULL COMMENT '菜单ID',
  `role_id` bigint NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`menu_id`, `role_id`) USING BTREE,
  INDEX `FKcngg2qadojhi3a651a5adkvbq`(`role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色菜单关联' ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of sys_roles_menus
-- ----------------------------
INSERT INTO `sys_roles_menus` VALUES (1, 1);
INSERT INTO `sys_roles_menus` VALUES (2, 1);
INSERT INTO `sys_roles_menus` VALUES (3, 1);
INSERT INTO `sys_roles_menus` VALUES (5, 1);
INSERT INTO `sys_roles_menus` VALUES (6, 1);
INSERT INTO `sys_roles_menus` VALUES (7, 1);
INSERT INTO `sys_roles_menus` VALUES (9, 1);
INSERT INTO `sys_roles_menus` VALUES (10, 1);
INSERT INTO `sys_roles_menus` VALUES (11, 1);
INSERT INTO `sys_roles_menus` VALUES (14, 1);
INSERT INTO `sys_roles_menus` VALUES (15, 1);
INSERT INTO `sys_roles_menus` VALUES (18, 1);
INSERT INTO `sys_roles_menus` VALUES (19, 1);
INSERT INTO `sys_roles_menus` VALUES (21, 1);
INSERT INTO `sys_roles_menus` VALUES (22, 1);
INSERT INTO `sys_roles_menus` VALUES (23, 1);
INSERT INTO `sys_roles_menus` VALUES (24, 1);
INSERT INTO `sys_roles_menus` VALUES (27, 1);
INSERT INTO `sys_roles_menus` VALUES (28, 1);
INSERT INTO `sys_roles_menus` VALUES (30, 1);
INSERT INTO `sys_roles_menus` VALUES (32, 1);
INSERT INTO `sys_roles_menus` VALUES (33, 1);
INSERT INTO `sys_roles_menus` VALUES (34, 1);
INSERT INTO `sys_roles_menus` VALUES (35, 1);
INSERT INTO `sys_roles_menus` VALUES (36, 1);
INSERT INTO `sys_roles_menus` VALUES (37, 1);
INSERT INTO `sys_roles_menus` VALUES (38, 1);
INSERT INTO `sys_roles_menus` VALUES (39, 1);
INSERT INTO `sys_roles_menus` VALUES (41, 1);
INSERT INTO `sys_roles_menus` VALUES (44, 1);
INSERT INTO `sys_roles_menus` VALUES (45, 1);
INSERT INTO `sys_roles_menus` VALUES (46, 1);
INSERT INTO `sys_roles_menus` VALUES (48, 1);
INSERT INTO `sys_roles_menus` VALUES (49, 1);
INSERT INTO `sys_roles_menus` VALUES (50, 1);
INSERT INTO `sys_roles_menus` VALUES (52, 1);
INSERT INTO `sys_roles_menus` VALUES (53, 1);
INSERT INTO `sys_roles_menus` VALUES (54, 1);
INSERT INTO `sys_roles_menus` VALUES (56, 1);
INSERT INTO `sys_roles_menus` VALUES (57, 1);
INSERT INTO `sys_roles_menus` VALUES (58, 1);
INSERT INTO `sys_roles_menus` VALUES (60, 1);
INSERT INTO `sys_roles_menus` VALUES (61, 1);
INSERT INTO `sys_roles_menus` VALUES (62, 1);
INSERT INTO `sys_roles_menus` VALUES (64, 1);
INSERT INTO `sys_roles_menus` VALUES (65, 1);
INSERT INTO `sys_roles_menus` VALUES (66, 1);
INSERT INTO `sys_roles_menus` VALUES (73, 1);
INSERT INTO `sys_roles_menus` VALUES (74, 1);
INSERT INTO `sys_roles_menus` VALUES (75, 1);
INSERT INTO `sys_roles_menus` VALUES (77, 1);
INSERT INTO `sys_roles_menus` VALUES (78, 1);
INSERT INTO `sys_roles_menus` VALUES (79, 1);
INSERT INTO `sys_roles_menus` VALUES (80, 1);
INSERT INTO `sys_roles_menus` VALUES (82, 1);
INSERT INTO `sys_roles_menus` VALUES (83, 1);
INSERT INTO `sys_roles_menus` VALUES (90, 1);
INSERT INTO `sys_roles_menus` VALUES (92, 1);
INSERT INTO `sys_roles_menus` VALUES (93, 1);
INSERT INTO `sys_roles_menus` VALUES (94, 1);
INSERT INTO `sys_roles_menus` VALUES (97, 1);
INSERT INTO `sys_roles_menus` VALUES (98, 1);
INSERT INTO `sys_roles_menus` VALUES (102, 1);
INSERT INTO `sys_roles_menus` VALUES (103, 1);
INSERT INTO `sys_roles_menus` VALUES (104, 1);
INSERT INTO `sys_roles_menus` VALUES (105, 1);
INSERT INTO `sys_roles_menus` VALUES (106, 1);
INSERT INTO `sys_roles_menus` VALUES (107, 1);
INSERT INTO `sys_roles_menus` VALUES (108, 1);
INSERT INTO `sys_roles_menus` VALUES (109, 1);
INSERT INTO `sys_roles_menus` VALUES (110, 1);
INSERT INTO `sys_roles_menus` VALUES (111, 1);
INSERT INTO `sys_roles_menus` VALUES (112, 1);
INSERT INTO `sys_roles_menus` VALUES (113, 1);
INSERT INTO `sys_roles_menus` VALUES (114, 1);
INSERT INTO `sys_roles_menus` VALUES (116, 1);
INSERT INTO `sys_roles_menus` VALUES (118, 1);
INSERT INTO `sys_roles_menus` VALUES (119, 1);
INSERT INTO `sys_roles_menus` VALUES (120, 1);
INSERT INTO `sys_roles_menus` VALUES (121, 1);
INSERT INTO `sys_roles_menus` VALUES (122, 1);
INSERT INTO `sys_roles_menus` VALUES (123, 1);
INSERT INTO `sys_roles_menus` VALUES (124, 1);
INSERT INTO `sys_roles_menus` VALUES (125, 1);
INSERT INTO `sys_roles_menus` VALUES (126, 1);
INSERT INTO `sys_roles_menus` VALUES (127, 1);
INSERT INTO `sys_roles_menus` VALUES (128, 1);
INSERT INTO `sys_roles_menus` VALUES (129, 1);
INSERT INTO `sys_roles_menus` VALUES (130, 1);
INSERT INTO `sys_roles_menus` VALUES (131, 1);
INSERT INTO `sys_roles_menus` VALUES (132, 1);
INSERT INTO `sys_roles_menus` VALUES (133, 1);
INSERT INTO `sys_roles_menus` VALUES (134, 1);
INSERT INTO `sys_roles_menus` VALUES (135, 1);
INSERT INTO `sys_roles_menus` VALUES (136, 1);
INSERT INTO `sys_roles_menus` VALUES (137, 1);
INSERT INTO `sys_roles_menus` VALUES (138, 1);
INSERT INTO `sys_roles_menus` VALUES (139, 1);
INSERT INTO `sys_roles_menus` VALUES (1, 2);
INSERT INTO `sys_roles_menus` VALUES (2, 2);
INSERT INTO `sys_roles_menus` VALUES (6, 2);
INSERT INTO `sys_roles_menus` VALUES (7, 2);
INSERT INTO `sys_roles_menus` VALUES (9, 2);
INSERT INTO `sys_roles_menus` VALUES (10, 2);
INSERT INTO `sys_roles_menus` VALUES (11, 2);
INSERT INTO `sys_roles_menus` VALUES (14, 2);
INSERT INTO `sys_roles_menus` VALUES (15, 2);
INSERT INTO `sys_roles_menus` VALUES (19, 2);
INSERT INTO `sys_roles_menus` VALUES (21, 2);
INSERT INTO `sys_roles_menus` VALUES (22, 2);
INSERT INTO `sys_roles_menus` VALUES (23, 2);
INSERT INTO `sys_roles_menus` VALUES (24, 2);
INSERT INTO `sys_roles_menus` VALUES (27, 2);
INSERT INTO `sys_roles_menus` VALUES (30, 2);
INSERT INTO `sys_roles_menus` VALUES (32, 2);
INSERT INTO `sys_roles_menus` VALUES (33, 2);
INSERT INTO `sys_roles_menus` VALUES (34, 2);
INSERT INTO `sys_roles_menus` VALUES (36, 2);
INSERT INTO `sys_roles_menus` VALUES (80, 2);
INSERT INTO `sys_roles_menus` VALUES (82, 2);
INSERT INTO `sys_roles_menus` VALUES (83, 2);
INSERT INTO `sys_roles_menus` VALUES (116, 2);

-- ----------------------------
-- Table structure for sys_token
-- ----------------------------
DROP TABLE IF EXISTS `sys_token`;
CREATE TABLE `sys_token`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `token` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT 'token值',
  `day_max_limit` int NULL DEFAULT 0 COMMENT '每天最大请求次数',
  `thread_limit` int NULL DEFAULT 0 COMMENT '并发限制次数',
  `status` int NOT NULL DEFAULT 1 COMMENT '1-启用,0-禁用',
  `remark` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '备注',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `wx_app_id` int NULL DEFAULT NULL COMMENT '绑定的微信小程序id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = 'token配置信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `user_id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `dept_id` bigint NULL DEFAULT NULL COMMENT '部门名称',
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `nick_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `gender` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '性别',
  `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号码',
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `avatar_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像地址',
  `avatar_path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像真实路径',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `is_admin` bit(1) NULL DEFAULT b'0' COMMENT '是否为admin账号',
  `enabled` bigint NULL DEFAULT NULL COMMENT '状态：1启用、0禁用',
  `create_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `update_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新者',
  `pwd_reset_time` datetime NULL DEFAULT NULL COMMENT '修改密码的时间',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建日期',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE INDEX `UK_kpubos9gc2cvtkb0thktkbkes`(`email`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE,
  UNIQUE INDEX `uniq_username`(`username`) USING BTREE,
  UNIQUE INDEX `uniq_email`(`email`) USING BTREE,
  INDEX `FK5rwmryny6jthaaxkogownknqp`(`dept_id`) USING BTREE,
  INDEX `FKpq2dhypk2qgt68nauh2by22jb`(`avatar_name`) USING BTREE,
  INDEX `inx_enabled`(`enabled`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统用户' ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 2, 'admin', '管理员', '男', '18888888888', 'siival@qq.com', 'avatar-20200806032259161.png',
  '/Users/jie/Documents/work/me/admin/eladmin/~/avatar/avatar-20200806032259161.png', '$2a$10$Egp1/gvFlt7zhlXVfEFw4OfWQCGPw0ClmMcc6FjTnvXNRVf9zdMRa', b'1', 1, NULL, 'admin', '2020-05-03 16:38:31', '2018-08-23 09:11:56', '2022-04-09 22:51:53');

-- ----------------------------
-- Table structure for sys_users_jobs
-- ----------------------------
DROP TABLE IF EXISTS `sys_users_jobs`;
CREATE TABLE `sys_users_jobs`  (
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `job_id` bigint NOT NULL COMMENT '岗位ID',
  PRIMARY KEY (`user_id`, `job_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_users_jobs
-- ----------------------------
INSERT INTO `sys_users_jobs` VALUES (1, 11);
INSERT INTO `sys_users_jobs` VALUES (2, 12);

-- ----------------------------
-- Table structure for sys_users_roles
-- ----------------------------
DROP TABLE IF EXISTS `sys_users_roles`;
CREATE TABLE `sys_users_roles`  (
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `role_id` bigint NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`user_id`, `role_id`) USING BTREE,
  INDEX `FKq4eq273l04bpu4efj0jd0jb98`(`role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户角色关联' ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of sys_users_roles
-- ----------------------------
INSERT INTO `sys_users_roles` VALUES (1, 1);
INSERT INTO `sys_users_roles` VALUES (2, 2);

-- ----------------------------
-- Table structure for tg_bot_info
-- ----------------------------
DROP TABLE IF EXISTS `tg_bot_info`;
CREATE TABLE `tg_bot_info`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `token` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'bot token 加密存储',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'bot name',
  `status` tinyint(1) NULL DEFAULT 1 COMMENT '1-启用,0-禁用',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'tg bot token info' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tg_bot_info
-- ----------------------------

-- ----------------------------
-- Table structure for tool_alipay_config
-- ----------------------------
DROP TABLE IF EXISTS `tool_alipay_config`;
CREATE TABLE `tool_alipay_config`  (
  `config_id` bigint NOT NULL COMMENT 'ID',
  `app_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '应用ID',
  `charset` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '编码',
  `format` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '类型 固定格式json',
  `gateway_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '网关地址',
  `notify_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '异步回调',
  `private_key` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '私钥',
  `public_key` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '公钥',
  `return_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '回调地址',
  `sign_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '签名方式',
  `sys_service_provider_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商户号',
  PRIMARY KEY (`config_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '支付宝配置类' ROW_FORMAT = COMPACT;

-- ----------------------------
-- Table structure for tool_email_config
-- ----------------------------
DROP TABLE IF EXISTS `tool_email_config`;
CREATE TABLE `tool_email_config`  (
  `config_id` bigint NOT NULL COMMENT 'ID',
  `from_user` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收件人',
  `host` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮件服务器SMTP地址',
  `pass` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `port` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '端口',
  `user` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发件者用户名',
  PRIMARY KEY (`config_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '邮箱配置' ROW_FORMAT = COMPACT;



-- ----------------------------
-- Table structure for tool_local_storage
-- ----------------------------
DROP TABLE IF EXISTS `tool_local_storage`;
CREATE TABLE `tool_local_storage`  (
  `storage_id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `real_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件真实的名称',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件名',
  `suffix` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '后缀',
  `path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '路径',
  `type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '类型',
  `size` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '大小',
  `create_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `update_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建日期',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`storage_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '本地存储' ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of tool_local_storage
-- ----------------------------

-- ----------------------------
-- Table structure for tool_qiniu_config
-- ----------------------------
DROP TABLE IF EXISTS `tool_qiniu_config`;
CREATE TABLE `tool_qiniu_config`  (
  `config_id` bigint NOT NULL COMMENT 'ID',
  `access_key` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT 'accessKey',
  `bucket` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Bucket 识别符',
  `host` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '外链域名',
  `secret_key` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT 'secretKey',
  `type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '空间类型',
  `zone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '机房',
  PRIMARY KEY (`config_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '七牛云配置' ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of tool_qiniu_config
-- ----------------------------

-- ----------------------------
-- Table structure for tool_qiniu_content
-- ----------------------------
DROP TABLE IF EXISTS `tool_qiniu_content`;
CREATE TABLE `tool_qiniu_content`  (
  `content_id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `bucket` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Bucket 识别符',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件名称',
  `size` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件大小',
  `type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件类型：私有或公开',
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件url',
  `suffix` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件后缀',
  `update_time` datetime NULL DEFAULT NULL COMMENT '上传或同步的时间',
  PRIMARY KEY (`content_id`) USING BTREE,
  UNIQUE INDEX `uniq_name`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '七牛云文件存储' ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of tool_qiniu_content
-- ----------------------------

-- ----------------------------
-- Table structure for user_correct_question
-- ----------------------------
DROP TABLE IF EXISTS `user_correct_question`;
CREATE TABLE `user_correct_question`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `pid` int NOT NULL DEFAULT 1 COMMENT '题库分类id',
  `question_id` int NOT NULL DEFAULT 0 COMMENT '问题id',
  `uid` int NOT NULL DEFAULT 0 COMMENT '用户id',
  `reason` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '纠错原因',
  `status` tinyint(1) NULL DEFAULT 1 COMMENT '1-用户提交纠错,2-管理员审核通过,3-审核不通过',
  `check_reason` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '审核不通过原因',
  `check_uid` int NULL DEFAULT NULL COMMENT '管理员用户id',
  `check_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户纠错列表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for user_exam_score
-- ----------------------------
DROP TABLE IF EXISTS `user_exam_score`;
CREATE TABLE `user_exam_score`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `pid` int NOT NULL DEFAULT 1 COMMENT '题库分类id',
  `score` int NULL DEFAULT 0 COMMENT '得分',
  `uid` int NULL DEFAULT 0 COMMENT '用户id',
  `wrong` int NULL DEFAULT 0 COMMENT '错题数量',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户模拟考试得分' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for user_export_log
-- ----------------------------
DROP TABLE IF EXISTS `user_export_log`;
CREATE TABLE `user_export_log`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `uid` int NOT NULL DEFAULT 0 COMMENT '用户id',
  `integral` int NULL DEFAULT 0 COMMENT '消耗积分',
  `pid` int NULL DEFAULT 0 COMMENT '题库id',
  `email` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '邮箱',
  `status` tinyint(1) NULL DEFAULT 0 COMMENT '0-用户提交, 1-导出成功, 2-导出失败',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `export_type` int NOT NULL DEFAULT 1 COMMENT '1-题库  2-试卷',
  `exam_id` int NULL DEFAULT NULL COMMENT '试卷id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户导出题库log' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_favorite_question
-- ----------------------------
DROP TABLE IF EXISTS `user_favorite_question`;
CREATE TABLE `user_favorite_question`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `pid` int NOT NULL DEFAULT 1 COMMENT '题库分类id',
  `question_id` int NULL DEFAULT 0 COMMENT '问题id',
  `uid` int NULL DEFAULT 0 COMMENT '用户id',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户收藏列表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `uid` int NOT NULL DEFAULT 0 COMMENT '用户id',
  `integral` int NULL DEFAULT 0 COMMENT '积分',
  `sign_total_count` int NULL DEFAULT 0 COMMENT '累计签到天数',
  `sign_continuous_count` int NULL DEFAULT 0 COMMENT '连续签到天数',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for user_integral_log
-- ----------------------------
DROP TABLE IF EXISTS `user_integral_log`;
CREATE TABLE `user_integral_log`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `uid` int NOT NULL DEFAULT 0 COMMENT '用户id',
  `integral` int NULL DEFAULT 0 COMMENT '积分',
  `type` int NULL DEFAULT 0 COMMENT '1-增加,2-减少',
  `before_count` int NULL DEFAULT 0 COMMENT '操作前数量',
  `after_count` int NULL DEFAULT 0 COMMENT '操作后数量',
  `remark` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户积分log' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for user_invite_log
-- ----------------------------
DROP TABLE IF EXISTS `user_invite_log`;
CREATE TABLE `user_invite_log`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `uid` int NOT NULL DEFAULT 0 COMMENT '用户id',
  `integral` int NULL DEFAULT 0 COMMENT '获得积分',
  `invite_uid` int NOT NULL DEFAULT 0 COMMENT '邀请用户id',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户邀请日志' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_sign_log
-- ----------------------------
DROP TABLE IF EXISTS `user_sign_log`;
CREATE TABLE `user_sign_log`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `uid` int NOT NULL DEFAULT 0 COMMENT '用户id',
  `integral` int NULL DEFAULT 0 COMMENT '签到获得积分数量用于展示',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户签到' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for user_wrong_question
-- ----------------------------
DROP TABLE IF EXISTS `user_wrong_question`;
CREATE TABLE `user_wrong_question`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `pid` int NOT NULL DEFAULT 1 COMMENT '题库分类id',
  `question_id` int NULL DEFAULT 0 COMMENT '问题id',
  `uid` int NULL DEFAULT 0 COMMENT '用户id',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户错题列表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for wx_app
-- ----------------------------
DROP TABLE IF EXISTS `wx_app`;
CREATE TABLE `wx_app`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `app_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'app id',
  `app_secret` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'app secret',
  `app_token` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'token',
  `aes_key` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'aes 加密key',
  `msg_data_format` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'XML' COMMENT '消息格式',
  `type` tinyint(1) NULL DEFAULT 1 COMMENT '1-小程序,2-公众号,3-服务号',
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '名称',
  `remark` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  `status` tinyint(1) NOT NULL DEFAULT 1 COMMENT '1-启用,0-禁用',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `index_app_id`(`app_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = 'wx app' ROW_FORMAT = Dynamic;


-- ----------------------------
-- Table structure for wx_user
-- ----------------------------
DROP TABLE IF EXISTS `wx_user`;
CREATE TABLE `wx_user`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `nick_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '昵称',
  `avatar_url` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '头像地址',
  `country` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '国家',
  `gender` tinyint(1) NULL DEFAULT NULL COMMENT '性别',
  `language` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '语言',
  `province` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '省份',
  `unionid` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'unionid',
  `openid` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'openid',
  `session_key` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'session key',
  `status` tinyint(1) NOT NULL DEFAULT 1 COMMENT '1-启用,0-禁用',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = 'wx user info' ROW_FORMAT = DYNAMIC;

DROP TABLE IF EXISTS `user_wheel_log`;
CREATE TABLE `user_wheel_log` (
  `id` int NOT NULL AUTO_INCREMENT,
  `uid` int NOT NULL DEFAULT '0' COMMENT '用户id',
  `random_number` int not null default 0 comment '用户随机数',
  `reward_name` varchar(32) comment '奖项名称',
  `integral` int DEFAULT '0' COMMENT '转盘获得积分数量用于展示',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户抽奖大转盘记录';

drop table if exists `sys_wheel_info`;
CREATE TABLE `sys_wheel_info` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(32) NOT NULL COMMENT '奖项名称',
  `integral` int DEFAULT 0 COMMENT '奖项赠送积分',
  `status` tinyint(1) DEFAULT NULL COMMENT '1-启用,0-禁用',
	`start_num` int(4) default 0 comment '随机数起始值  随机在start end之间中奖',
	`sort` int(4) default 0 comment '排序值，默认为升序',
	`end_num` int(4) default 0 comment '随机数结束值',
  `remark` varchar(128) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT NULL COMMENT '创建日期',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='大转盘奖项';

DROP TABLE IF EXISTS `sys_setting`;
CREATE TABLE `sys_setting`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '设置key',
  `value` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '设置值',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
  `create_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `update_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建日期',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `name`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统设置' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_setting
-- ----------------------------
INSERT INTO `sys_setting` VALUES (1, 'exam_integral', '100', '导出试卷消耗积分', 'admin', 'admin', '2022-05-06 16:39:12', '2022-05-06 18:28:20');
INSERT INTO `sys_setting` VALUES (2, 'invite_integral', '100', '邀请好友获得积分', 'admin', 'admin', '2022-05-06 16:40:30', '2022-05-06 18:28:14');
INSERT INTO `sys_setting` VALUES (3, 'today_question_integral', '10', '每日一题赠送积分', 'admin', 'admin', '2022-05-06 16:56:59', '2022-05-06 18:05:42');
INSERT INTO `sys_setting` VALUES (4, 'correct_question_integral', '10', '题目纠错', 'admin', 'admin', '2022-05-06 16:57:20', '2022-05-06 18:05:36');
INSERT INTO `sys_setting` VALUES (5, 'handle_pdf_count', '5', '每天免费处理pdf次数', 'admin', 'admin', '2022-05-15 17:35:39', '2022-05-15 17:35:39');
INSERT INTO `sys_setting` VALUES (6, 'handle_pdf_integral', '100', '处理pdf文件消耗积分', 'admin', 'admin', '2022-05-15 19:18:26', '2022-05-15 19:39:11');
INSERT INTO `sys_setting` VALUES (7, 'contact_qq_group', '123955944', '关于我们qq群号', 'admin', 'admin', '2022-05-15 19:18:26', '2022-05-15 19:39:11');



SET FOREIGN_KEY_CHECKS = 1;

