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

 Date: 04/05/2024 23:54:22
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for corporation_blockchain
-- ----------------------------
DROP TABLE IF EXISTS `corporation_blockchain`;
CREATE TABLE `corporation_blockchain`  (
  `corporation_id` int(0) NULL DEFAULT NULL,
  `blockchain_id` int(0) NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
