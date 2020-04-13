/*
 Navicat Premium Data Transfer

 Source Server         : local_mysql5.7.26
 Source Server Type    : MySQL
 Source Server Version : 50726
 Source Host           : localhost:3306
 Source Schema         : flink_explore

 Target Server Type    : MySQL
 Target Server Version : 50726
 File Encoding         : 65001

 Date: 13/04/2020 09:12:22
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for flink_cluster
-- ----------------------------
DROP TABLE IF EXISTS `flink_cluster`;
CREATE TABLE `flink_cluster`  (
  `cluster_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `cluster_code` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `cluster_desc` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `job_manager_url` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `job_manager_standby_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '逗号分割',
  `enabled_flag` tinyint(1) NULL DEFAULT 1,
  `tenant_id` bigint(20) NULL DEFAULT 0 COMMENT '租户ID',
  `object_version_number` bigint(20) NOT NULL DEFAULT 1 COMMENT '版本号',
  `creation_date` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  `created_by` int(11) NOT NULL DEFAULT -1,
  `last_updated_by` int(11) NOT NULL DEFAULT -1,
  `last_update_date` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`cluster_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for flink_node
-- ----------------------------
DROP TABLE IF EXISTS `flink_node`;
CREATE TABLE `flink_node`  (
  `node_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '表ID，主键，供其他表做外键',
  `cluster_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT 'flink集群编码',
  `node_type` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'master/slave',
  `node_code` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '节点唯一编码',
  `node_desc` varchar(127) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '节点描述',
  `setting_info` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '节点配置信息',
  `tenant_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '租户ID',
  `enabled_flag` tinyint(1) NOT NULL DEFAULT 1 COMMENT '默认启用',
  `object_version_number` bigint(20) NOT NULL DEFAULT 1 COMMENT '行版本号，用来处理锁',
  `creation_date` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  `created_by` int(11) NOT NULL DEFAULT -1,
  `last_updated_by` int(11) NOT NULL DEFAULT -1,
  `last_update_date` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`node_id`) USING BTREE,
  UNIQUE INDEX `index_node_code_u1`(`node_code`, `tenant_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '流处理节点表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for flink_upload_jar
-- ----------------------------
DROP TABLE IF EXISTS `flink_upload_jar`;
CREATE TABLE `flink_upload_jar`  (
  `upload_jar_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `jar_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `jar_code` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT 'jar的编码与version一起作为唯一标识',
  `version` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT 'jar的version与jar_code一起作为唯一标识',
  `cluster_code` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `system_provided` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否是系统提供的（平台预先上传jar做为平台功能使用）',
  `entry_class` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '默认的主类',
  `filename` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '上传后返回，作为flink run jar的jar_id',
  `jar_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '上传后返回的filename，截取最后的名称',
  `status` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '上传后返回',
  `tenant_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '租户ID',
  `object_version_number` bigint(20) NOT NULL DEFAULT 1 COMMENT '行版本号，用来处理锁',
  `creation_date` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  `created_by` int(11) NOT NULL DEFAULT -1,
  `last_updated_by` int(11) NOT NULL DEFAULT -1,
  `last_update_date` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`upload_jar_id`) USING BTREE,
  UNIQUE INDEX `index_jar_u1`(`jar_code`, `version`, `tenant_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
