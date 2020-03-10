SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for w_file_t
-- ----------------------------
DROP TABLE IF EXISTS `w_file_t`;
CREATE TABLE `w_file_t`  (
                           `id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
                           `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
                           `version` int(15) NULL DEFAULT NULL,
                           `size` int(40) NULL DEFAULT NULL,
                           `creator` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
                           `create_time` bigint(20) NULL DEFAULT NULL,
                           `modifier` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
                           `modify_time` bigint(20) NULL DEFAULT NULL,
                           `download_url` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
                           `deleted` char(2) DEFAULT 'N',
                           `can_delete` char(2) DEFAULT 'Y',
                           PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of w_file_t
-- ----------------------------
INSERT INTO `w_file_t` VALUES ('1', 'log.doc', 1, 25, '1', 1563079046, '1', 1563079046, 'https://xxx/log.doc');

-- ----------------------------
-- Table structure for w_file_version_t
-- ----------------------------
DROP TABLE IF EXISTS `w_file_version_t`;
CREATE TABLE `w_file_version_t`  (
                                   `id` int(11) NOT NULL AUTO_INCREMENT,
                                   `file_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
                                   `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
                                   `version` int(11) NULL DEFAULT NULL,
                                   `download_url` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
                                   `size` double NULL DEFAULT NULL,
                                   `creator` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
                                   `create_time` bigint(20) NULL DEFAULT NULL,
                                   `modifier` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
                                   `modify_time` bigint(20) NULL DEFAULT NULL,
                                   PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for w_file_watermark_t
-- ----------------------------
DROP TABLE IF EXISTS `w_file_watermark_t`;
CREATE TABLE `w_file_watermark_t`  (
                                     `id` int(11) NOT NULL AUTO_INCREMENT,
                                     `file_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
                                     `type` int(11) NULL DEFAULT NULL,
                                     `value` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
                                     `fillstyle` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
                                     `font` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
                                     `rotate` decimal(20, 0) NULL DEFAULT NULL,
                                     `horizontal` int(11) NULL DEFAULT NULL,
                                     `vertical` int(11) NULL DEFAULT NULL,
                                     PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of w_file_watermark_t
-- ----------------------------
INSERT INTO `w_file_watermark_t` VALUES (1, '1', 0, '', 'rgba( 192, 192, 192, 0.6 )', 'bold 20px Serif', 0, 50, 50);

-- ----------------------------
-- Table structure for w_user_acl_t
-- ----------------------------
DROP TABLE IF EXISTS `w_user_acl_t`;
CREATE TABLE `w_user_acl_t`  (
                               `id` int(11) NOT NULL AUTO_INCREMENT,
                               `user_id` int(40) NULL DEFAULT NULL,
                               `file_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
                               `permission` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
                               `re_name` int(11) NULL DEFAULT NULL,
                               `history` int(11) NULL DEFAULT NULL,
                               PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of w_user_acl_t
-- ----------------------------
INSERT INTO `w_user_acl_t` VALUES (1, 1, '1', 'write', 0, 0);
INSERT INTO `w_user_acl_t` VALUES (2, 2, '2', 'write', 0, 0);

-- ----------------------------
-- Table structure for w_user_t
-- ----------------------------
DROP TABLE IF EXISTS `w_user_t`;
CREATE TABLE `w_user_t`  (
                           `id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
                           `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
                           `avatar_url` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
                           PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of w_user_t
-- ----------------------------
INSERT INTO `w_user_t` VALUES ('1', '张三', 'https://xxx/user0.png');
INSERT INTO `w_user_t` VALUES ('2', '李四', 'https://xxx/user1.png');

SET FOREIGN_KEY_CHECKS = 1;
