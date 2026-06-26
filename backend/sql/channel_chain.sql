/*
 Navicat Premium Data Transfer

 Source Server         : wyres
 Source Server Type    : MySQL
 Source Server Version : 80032
 Source Host           : localhost:3306
 Source Schema         : blc

 Target Server Type    : MySQL
 Target Server Version : 80032
 File Encoding         : 65001

 Date: 06/05/2024 10:41:39
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for channel_chain
-- ----------------------------
DROP TABLE IF EXISTS `channel_chain`;
CREATE TABLE `channel_chain`  (
  `channel_id` int(0) NULL DEFAULT NULL,
  `chaincode_id` int(0) NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
