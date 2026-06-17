/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80045 (8.0.45)
 Source Host           : localhost:3306
 Source Schema         : library1

 Target Server Type    : MySQL
 Target Server Version : 80045 (8.0.45)
 File Encoding         : 65001

 Date: 17/06/2026 23:52:00
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键ID，自增',
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名',
  `password` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码',
  `adminType` int NOT NULL COMMENT '管理员类型',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES (1, 'cwlw', '123456', 1);
INSERT INTO `admin` VALUES (2, 'yuyy', '123456', 2);

-- ----------------------------
-- Table structure for book
-- ----------------------------
DROP TABLE IF EXISTS `book`;
CREATE TABLE `book`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键ID，自增',
  `ISBN` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'ISBN号',
  `bname` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '书名',
  `author` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '作者',
  `publisher` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '出版社',
  `introduction` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '简介',
  `pubDate` date NULL DEFAULT NULL COMMENT '出版日期',
  `clcNum` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '分类号',
  `bookStatus` int NOT NULL DEFAULT 1 COMMENT '书籍状态：1=在馆，2=借出，3=遗失',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 35 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of book
-- ----------------------------
INSERT INTO `book` VALUES (1, '9787111593812', '深入理解计算机系统', 'Randal E. Bryant', '机械工业出版社', '从程序员的视角讲解计算机系统，包括处理器、存储器、编译、链接等内容', '2016-11-01', 'TP3', 1);
INSERT INTO `book` VALUES (2, '9787115522621', '数据结构与算法分析', 'Mark Allen Weiss', '人民邮电出版社', '用C语言讲解数据结构和算法的基础内容', '2019-10-01', 'TP311', 1);
INSERT INTO `book` VALUES (3, '9787115428022', 'Python编程：从入门到实践', 'Eric Matthes', '人民邮电出版社', 'Python入门经典教材，包含基础语法和项目实战', '2016-07-01', 'TP311.56', 2);
INSERT INTO `book` VALUES (4, '9787111568292', '算法导论', 'Thomas H. Cormen', '机械工业出版社', '算法领域的经典教材，讲解各种基础算法', '2013-01-01', 'TP301.6', 3);
INSERT INTO `book` VALUES (5, '9787020002207', '红楼梦', '曹雪芹', '人民文学出版社', '中国古典四大名著之首，讲述贾史王薛四大家族的兴衰', '1996-01-01', 'I242.47', 1);
INSERT INTO `book` VALUES (6, '9787020090000', '西游记', '吴承恩', '人民文学出版社', '古典神魔小说，唐僧师徒西天取经的故事', '1997-01-01', 'I242.47', 1);
INSERT INTO `book` VALUES (7, '9787020090014', '三国演义', '罗贯中', '人民文学出版社', '古典历史演义小说，描绘三国争霸历史', '1998-01-01', 'I242.47', 1);
INSERT INTO `book` VALUES (8, '9787020090021', '水浒传', '施耐庵', '人民文学出版社', '古典英雄传奇小说，梁山好汉聚义故事', '1999-01-01', 'I242.47', 1);
INSERT INTO `book` VALUES (9, '9787020016945', '朝花夕拾', '鲁迅', '人民文学出版社', '鲁迅回忆性散文集，记录童年与青年往事', '2000-01-01', 'I266', 1);
INSERT INTO `book` VALUES (10, '9787020024752', '骆驼祥子', '老舍', '人民文学出版社', '讲述北平车夫祥子的人生起落', '2001-01-01', 'I246.57', 1);
INSERT INTO `book` VALUES (11, '9787020042802', '围城', '钱钟书', '人民文学出版社', '讽刺长篇小说，描绘民国知识分子百态', '2002-01-01', 'I246.57', 1);
INSERT INTO `book` VALUES (12, '9787020033107', '子夜', '茅盾', '人民文学出版社', '现代长篇小说，描写近代民族工业发展', '2003-01-01', 'I246.57', 1);
INSERT INTO `book` VALUES (13, '9787020043007', '边城', '沈从文', '人民文学出版社', '湘西风情小说，纯净唯美的乡土故事', '2004-01-01', 'I246.57', 1);
INSERT INTO `book` VALUES (14, '9787020051905', '呐喊', '鲁迅', '人民文学出版社', '鲁迅短篇小说集，揭露旧时代社会弊病', '2005-01-01', 'I246.7', 1);
INSERT INTO `book` VALUES (15, '9787508053222', '百年孤独', '加西亚·马尔克斯', '华夏出版社', '魔幻现实主义经典，布恩迪亚家族百年兴衰', '2006-01-01', 'I775.45', 1);
INSERT INTO `book` VALUES (16, '9787508053239', '简·爱', '夏洛蒂·勃朗特', '华夏出版社', '经典女性励志长篇小说', '2007-01-01', 'I561.44', 1);
INSERT INTO `book` VALUES (17, '9787508053246', '呼啸山庄', '艾米莉·勃朗特', '华夏出版社', '英国经典复仇题材长篇小说', '2008-01-01', 'I561.44', 1);
INSERT INTO `book` VALUES (18, '9787508053253', '巴黎圣母院', '雨果', '华夏出版社', '法国浪漫主义文学代表作', '2009-01-01', 'I565.44', 1);
INSERT INTO `book` VALUES (19, '9787508053260', '悲惨世界', '雨果', '华夏出版社', '讲述小人物在苦难中坚守善良的故事', '2010-01-01', 'I565.44', 1);
INSERT INTO `book` VALUES (20, '9787508053277', '红与黑', '司汤达', '华夏出版社', '欧洲现实主义文学经典', '2011-01-01', 'I565.44', 1);
INSERT INTO `book` VALUES (21, '9787508053284', '基督山伯爵', '大仲马', '华夏出版社', '经典复仇与报恩长篇小说', '2012-01-01', 'I565.44', 1);
INSERT INTO `book` VALUES (22, '9787508053291', '傲慢与偏见', '简·奥斯汀', '华夏出版社', '英国婚恋题材经典小说', '2013-01-01', 'I561.44', 1);
INSERT INTO `book` VALUES (23, '9787508053307', '战争与和平', '列夫·托尔斯泰', '华夏出版社', '俄国史诗级长篇名著', '2014-01-01', 'I512.44', 1);
INSERT INTO `book` VALUES (24, '9787508053314', '安娜·卡列尼娜', '列夫·托尔斯泰', '华夏出版社', '俄国社会现实题材经典', '2015-01-01', 'I512.44', 1);
INSERT INTO `book` VALUES (25, '9787508053321', '罪与罚', '陀思妥耶夫斯基', '华夏出版社', '探讨人性与救赎的经典小说', '2016-01-01', 'I512.44', 1);
INSERT INTO `book` VALUES (26, '9787508053338', '童年', '高尔基', '华夏出版社', '高尔基自传体三部曲第一部', '2017-01-01', 'I512.45', 1);
INSERT INTO `book` VALUES (27, '9787508053345', '在人间', '高尔基', '华夏出版社', '高尔基自传体三部曲第二部', '2018-01-01', 'I512.45', 1);
INSERT INTO `book` VALUES (28, '9787508053352', '我的大学', '高尔基', '华夏出版社', '高尔基自传体三部曲第三部', '2019-01-01', 'I512.45', 1);
INSERT INTO `book` VALUES (29, '9787508053369', '哈姆雷特', '莎士比亚', '华夏出版社', '四大悲剧之首，经典戏剧作品', '2020-01-01', 'I561.33', 1);
INSERT INTO `book` VALUES (30, '9787508053376', '罗密欧与朱丽叶', '莎士比亚', '华夏出版社', '经典爱情悲剧戏剧', '2021-01-01', 'I561.33', 1);
INSERT INTO `book` VALUES (31, '9787508053383', '堂吉诃德', '塞万提斯', '华夏出版社', '西班牙文学巅峰之作', '2022-01-01', 'I551.44', 1);
INSERT INTO `book` VALUES (32, '9787508053390', '鲁滨逊漂流记', '笛福', '华夏出版社', '经典冒险励志小说', '2023-01-01', 'I561.44', 1);
INSERT INTO `book` VALUES (33, '9787508053406', '格列佛游记', '斯威夫特', '华夏出版社', '讽刺奇幻冒险小说', '2024-01-01', 'I561.44', 1);
INSERT INTO `book` VALUES (34, '9787508053413', '海底两万里', '凡尔纳', '华夏出版社', '经典科幻探险小说', '2025-01-01', 'I565.45', 1);

-- ----------------------------
-- Table structure for bookcopy
-- ----------------------------
DROP TABLE IF EXISTS `bookcopy`;
CREATE TABLE `bookcopy`  (
  `barCode` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `ISBN` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `place` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `status` int NULL DEFAULT NULL,
  `oldStatus` int NULL DEFAULT NULL,
  `location` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '馆藏地',
  `nowLocation` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '当前所在地',
  PRIMARY KEY (`barCode`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of bookcopy
-- ----------------------------
INSERT INTO `bookcopy` VALUES ('BC0001', '9787111593812', '图书馆A区1架', 1, NULL, '花溪校区-中山图书馆', '花溪校区-中山图书馆');
INSERT INTO `bookcopy` VALUES ('BC0002', '9787111593812', '图书馆A区1架', 2, 1, '花溪校区-中山图书馆', '两江校区-丸美图书馆');
INSERT INTO `bookcopy` VALUES ('BC0003', '9787115522621', '图书馆A区2架', 1, NULL, '花溪校区-中山图书馆', '花溪校区-中山图书馆');
INSERT INTO `bookcopy` VALUES ('BC0004', '9787115428022', '图书馆B区3架', 2, 1, '两江校区-丸美图书馆', '两江校区-丸美图书馆');
INSERT INTO `bookcopy` VALUES ('BC0005', '9787111568292', '图书馆C区5架', 3, 1, '两江校区-丸美图书馆', '花溪校区-中山图书馆');
INSERT INTO `bookcopy` VALUES ('BC0006', '9787020002207', '图书馆A区3架', 1, NULL, '花溪校区-中山图书馆', '花溪校区-中山图书馆');
INSERT INTO `bookcopy` VALUES ('BC0007', '9787020090000', '图书馆A区3架', 1, NULL, '花溪校区-中山图书馆', '花溪校区-中山图书馆');
INSERT INTO `bookcopy` VALUES ('BC0008', '9787020090014', '图书馆A区3架', 1, NULL, '花溪校区-中山图书馆', '花溪校区-中山图书馆');
INSERT INTO `bookcopy` VALUES ('BC0009', '9787020090021', '图书馆A区3架', 1, NULL, '花溪校区-中山图书馆', '花溪校区-中山图书馆');
INSERT INTO `bookcopy` VALUES ('BC0010', '9787020016945', '图书馆A区4架', 1, NULL, '花溪校区-中山图书馆', '花溪校区-中山图书馆');
INSERT INTO `bookcopy` VALUES ('BC0011', '9787020024752', '图书馆A区4架', 1, NULL, '花溪校区-中山图书馆', '花溪校区-中山图书馆');
INSERT INTO `bookcopy` VALUES ('BC0012', '9787020042802', '图书馆A区4架', 1, NULL, '花溪校区-中山图书馆', '花溪校区-中山图书馆');
INSERT INTO `bookcopy` VALUES ('BC0013', '9787020033107', '图书馆A区4架', 1, NULL, '花溪校区-中山图书馆', '花溪校区-中山图书馆');
INSERT INTO `bookcopy` VALUES ('BC0014', '9787020043007', '图书馆A区4架', 1, NULL, '花溪校区-中山图书馆', '花溪校区-中山图书馆');
INSERT INTO `bookcopy` VALUES ('BC0015', '9787020051905', '图书馆A区4架', 1, NULL, '花溪校区-中山图书馆', '花溪校区-中山图书馆');
INSERT INTO `bookcopy` VALUES ('BC0016', '9787508053222', '图书馆B区1架', 1, NULL, '两江校区-丸美图书馆', '两江校区-丸美图书馆');
INSERT INTO `bookcopy` VALUES ('BC0017', '9787508053239', '图书馆B区1架', 1, NULL, '两江校区-丸美图书馆', '两江校区-丸美图书馆');
INSERT INTO `bookcopy` VALUES ('BC0018', '9787508053246', '图书馆B区1架', 1, NULL, '两江校区-丸美图书馆', '两江校区-丸美图书馆');
INSERT INTO `bookcopy` VALUES ('BC0019', '9787508053253', '图书馆B区1架', 1, NULL, '两江校区-丸美图书馆', '两江校区-丸美图书馆');
INSERT INTO `bookcopy` VALUES ('BC0020', '9787508053260', '图书馆B区1架', 1, NULL, '两江校区-丸美图书馆', '两江校区-丸美图书馆');
INSERT INTO `bookcopy` VALUES ('BC0021', '9787508053277', '图书馆B区2架', 1, NULL, '两江校区-丸美图书馆', '两江校区-丸美图书馆');
INSERT INTO `bookcopy` VALUES ('BC0022', '9787508053284', '图书馆B区2架', 1, NULL, '两江校区-丸美图书馆', '两江校区-丸美图书馆');
INSERT INTO `bookcopy` VALUES ('BC0023', '9787508053291', '图书馆B区2架', 1, NULL, '两江校区-丸美图书馆', '两江校区-丸美图书馆');
INSERT INTO `bookcopy` VALUES ('BC0024', '9787508053307', '图书馆B区2架', 1, NULL, '两江校区-丸美图书馆', '两江校区-丸美图书馆');
INSERT INTO `bookcopy` VALUES ('BC0025', '9787508053314', '图书馆B区2架', 1, NULL, '两江校区-丸美图书馆', '两江校区-丸美图书馆');
INSERT INTO `bookcopy` VALUES ('BC0026', '9787508053321', '图书馆C区1架', 1, NULL, '两江校区-丸美图书馆', '两江校区-丸美图书馆');
INSERT INTO `bookcopy` VALUES ('BC0027', '9787508053338', '图书馆C区1架', 1, NULL, '两江校区-丸美图书馆', '两江校区-丸美图书馆');
INSERT INTO `bookcopy` VALUES ('BC0028', '9787508053345', '图书馆C区1架', 1, NULL, '两江校区-丸美图书馆', '两江校区-丸美图书馆');
INSERT INTO `bookcopy` VALUES ('BC0029', '9787508053352', '图书馆C区1架', 1, NULL, '两江校区-丸美图书馆', '两江校区-丸美图书馆');
INSERT INTO `bookcopy` VALUES ('BC0030', '9787508053369', '图书馆C区1架', 1, NULL, '两江校区-丸美图书馆', '两江校区-丸美图书馆');
INSERT INTO `bookcopy` VALUES ('BC0031', '9787508053376', '图书馆C区2架', 1, NULL, '两江校区-丸美图书馆', '两江校区-丸美图书馆');
INSERT INTO `bookcopy` VALUES ('BC0032', '9787508053383', '图书馆C区2架', 1, NULL, '两江校区-丸美图书馆', '两江校区-丸美图书馆');
INSERT INTO `bookcopy` VALUES ('BC0033', '9787508053390', '图书馆C区2架', 1, NULL, '两江校区-丸美图书馆', '两江校区-丸美图书馆');
INSERT INTO `bookcopy` VALUES ('BC0034', '9787508053406', '图书馆C区2架', 1, NULL, '两江校区-丸美图书馆', '两江校区-丸美图书馆');
INSERT INTO `bookcopy` VALUES ('shelf', '9787508053413', '图书馆C区2架', 1, NULL, '两江校区-丸美图书馆', '两江校区-丸美图书馆');

-- ----------------------------
-- Table structure for booktype
-- ----------------------------
DROP TABLE IF EXISTS `booktype`;
CREATE TABLE `booktype`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '分类编号',
  `typeName` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '分类名称',
  `intro` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '分类简介',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of booktype
-- ----------------------------
INSERT INTO `booktype` VALUES (1, '计算机类', '计算机、编程、软件开发相关图书');
INSERT INTO `booktype` VALUES (2, '文学类', '小说、散文、诗歌等文学作品');
INSERT INTO `booktype` VALUES (3, '历史类', '历史、传记、人文社科类图书');
INSERT INTO `booktype` VALUES (4, '教育类', '教材、考试、学习资料类图书');
INSERT INTO `booktype` VALUES (5, '艺术类', '音乐、美术、设计类图书');

-- ----------------------------
-- Table structure for borrowrec
-- ----------------------------
DROP TABLE IF EXISTS `borrowrec`;
CREATE TABLE `borrowrec`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `serNum` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '借阅流水号',
  `sno` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '读者学号/用户编号',
  `barCode` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '图书条码',
  `borDate` date NOT NULL COMMENT '借阅日期',
  `retDate` date NULL DEFAULT NULL COMMENT '应还日期',
  `realRetDate` date NULL DEFAULT NULL COMMENT '实际归还日期',
  `retStatus` int NOT NULL DEFAULT 0 COMMENT '归还状态：0=未还，1=已还，2=逾期',
  `oddDays` int NULL DEFAULT 0 COMMENT '逾期天数',
  `fineMoney` decimal(10, 2) NULL DEFAULT 0.00 COMMENT '罚款金额',
  `fineStatus` int NULL DEFAULT 0 COMMENT '罚款状态：0=未缴，1=已缴',
  `paySerNum` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '缴费流水号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of borrowrec
-- ----------------------------
INSERT INTO `borrowrec` VALUES (1, 'BR20260001', '2023001', 'BC0001', '2026-05-01', '2026-05-15', '2026-05-14', 1, 0, 0.00, 0, NULL);
INSERT INTO `borrowrec` VALUES (2, 'BR20260002', '2023002', 'BC0002', '2026-05-02', '2026-05-16', NULL, 0, 0, 0.00, 0, NULL);
INSERT INTO `borrowrec` VALUES (3, 'BR20260003', '2023003', 'BC0003', '2026-04-20', '2026-05-04', '2026-05-20', 2, 16, 8.00, 1, 'PAY20260001');
INSERT INTO `borrowrec` VALUES (4, 'BR20260004', '2023004', 'BC0004', '2026-05-05', '2026-05-19', NULL, 0, 0, 0.00, 0, NULL);
INSERT INTO `borrowrec` VALUES (5, 'BR20260005', '2023001', 'BC0006', '2026-05-06', '2026-05-20', '2026-05-18', 1, 0, 0.00, 0, NULL);
INSERT INTO `borrowrec` VALUES (6, 'BR20260006', '2023001', 'BC0007', '2026-05-07', '2026-05-21', '2026-05-20', 1, 0, 0.00, 0, NULL);
INSERT INTO `borrowrec` VALUES (7, 'BR20260007', '2023002', 'BC0008', '2026-05-08', '2026-05-22', '2026-05-25', 2, 3, 1.50, 1, 'PAY20260004');
INSERT INTO `borrowrec` VALUES (8, 'BR20260008', '2023005', 'BC0009', '2026-05-09', '2026-05-23', NULL, 0, 0, 0.00, 0, NULL);
INSERT INTO `borrowrec` VALUES (9, 'BR20260009', '2023006', 'BC0010', '2026-05-10', '2026-05-24', '2026-05-22', 1, 0, 0.00, 0, NULL);
INSERT INTO `borrowrec` VALUES (10, 'BR20260010', '2023007', 'BC0011', '2026-05-11', '2026-05-25', '2026-05-28', 2, 3, 1.50, 1, 'PAY20260005');
INSERT INTO `borrowrec` VALUES (11, 'BR20260011', '2023008', 'BC0012', '2026-05-12', '2026-05-26', NULL, 0, 0, 0.00, 0, NULL);
INSERT INTO `borrowrec` VALUES (12, 'BR20260012', '2023009', 'BC0013', '2026-05-13', '2026-05-27', '2026-05-26', 1, 0, 0.00, 0, NULL);
INSERT INTO `borrowrec` VALUES (13, 'BR20260013', '2023010', 'BC0014', '2026-05-14', '2026-05-28', '2026-05-30', 2, 2, 1.00, 1, 'PAY20260006');
INSERT INTO `borrowrec` VALUES (14, 'BR20260014', '2023001', 'BC0015', '2026-05-15', '2026-05-29', '2026-05-28', 1, 0, 0.00, 0, NULL);
INSERT INTO `borrowrec` VALUES (15, 'BR20260015', '2023003', 'BC0016', '2026-05-16', '2026-05-30', NULL, 0, 0, 0.00, 0, NULL);
INSERT INTO `borrowrec` VALUES (16, 'BR20260016', '2023004', 'BC0017', '2026-05-17', '2026-05-31', '2026-06-02', 2, 2, 1.00, 1, 'PAY20260007');
INSERT INTO `borrowrec` VALUES (17, 'BR20260017', '2023005', 'BC0018', '2026-05-18', '2026-06-01', NULL, 0, 0, 0.00, 0, NULL);
INSERT INTO `borrowrec` VALUES (18, 'BR20260018', '2023006', 'BC0019', '2026-05-19', '2026-06-02', '2026-06-01', 1, 0, 0.00, 0, NULL);
INSERT INTO `borrowrec` VALUES (19, 'BR20260019', '2023007', 'BC0020', '2026-05-20', '2026-06-03', NULL, 0, 0, 0.00, 0, NULL);
INSERT INTO `borrowrec` VALUES (20, 'BR20260020', '2023008', 'BC0021', '2026-05-21', '2026-06-04', '2026-06-06', 2, 2, 1.00, 1, 'PAY20260008');

-- ----------------------------
-- Table structure for cardrec
-- ----------------------------
DROP TABLE IF EXISTS `cardrec`;
CREATE TABLE `cardrec`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `serNum` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '操作流水号',
  `sno` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '读者学号/用户编号',
  `originCardNo` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '原卡号',
  `newCardNo` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '新卡号',
  `opType` int NOT NULL COMMENT '操作类型：1=挂失，2=补卡，3=换卡，4=注销',
  `opTime` datetime NULL DEFAULT NULL COMMENT '卡片操作时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cardrec
-- ----------------------------
INSERT INTO `cardrec` VALUES (1, 'CR20260001', '2023001', 'C202300101', '', 2, '2026-06-08 11:17:28');
INSERT INTO `cardrec` VALUES (2, 'CR20260002', '2023002', '', 'C202300201', 1, '2026-06-08 11:17:28');
INSERT INTO `cardrec` VALUES (3, 'CR20260003', '2023003', 'C202300301', 'C202300302', 3, '2026-06-17 21:05:48');
INSERT INTO `cardrec` VALUES (4, 'CR20260004', '2023004', 'C202300401', NULL, 4, '2026-06-17 21:05:48');
INSERT INTO `cardrec` VALUES (5, 'CR20260005', '2023005', '', 'C202300501', 1, '2026-06-16 09:22:30');
INSERT INTO `cardrec` VALUES (6, 'CR20260006', '2023006', '', 'C202300601', 1, '2026-06-16 09:22:30');
INSERT INTO `cardrec` VALUES (7, 'CR20260007', '2023007', '', 'C202300701', 1, '2026-06-16 09:22:30');
INSERT INTO `cardrec` VALUES (8, 'CR20260008', '2023001', 'C202300101', '', 4, '2026-06-17 21:05:48');
INSERT INTO `cardrec` VALUES (9, 'CR20260009', '2023003', 'C202300301', '', 2, '2026-06-17 21:05:48');
INSERT INTO `cardrec` VALUES (10, 'CR20260010', '2023005', 'C202300501', '', 4, '2026-06-17 21:05:48');
INSERT INTO `cardrec` VALUES (11, 'CR20260011', '2023010', 'C202301001', '', 2, '2026-06-17 21:05:48');
INSERT INTO `cardrec` VALUES (12, 'CR20260012', '2023021', 'C202302101', '', 4, '2026-06-17 21:05:48');
INSERT INTO `cardrec` VALUES (13, 'CR20260013', '2023002', 'C202300201', '', 2, '2026-06-17 21:05:48');
INSERT INTO `cardrec` VALUES (14, 'CR20260014', '2023004', 'C202300401', 'C202300402', 3, '2026-06-17 21:05:48');
INSERT INTO `cardrec` VALUES (15, 'CR2026061700000002', '2023039', '', 'C202303901', 1, '2026-06-17 22:55:16');
INSERT INTO `cardrec` VALUES (16, 'CR2026061700000003', '2023006', 'C202300601', '', 2, '2026-06-17 22:56:16');
INSERT INTO `cardrec` VALUES (17, 'CR2026061700000001', '2023006', 'C202300601', '', 4, '2026-06-17 23:10:56');

-- ----------------------------
-- Table structure for libcard
-- ----------------------------
DROP TABLE IF EXISTS `libcard`;
CREATE TABLE `libcard`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `cardNo` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '借阅卡号',
  `sno` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '读者学号/编号',
  `sname` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '读者姓名',
  `type` int NOT NULL COMMENT '读者类型：1=学生，2=教师，3=其他',
  `collage` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '所属学院',
  `major` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '专业',
  `birth` date NULL DEFAULT NULL COMMENT '出生日期',
  `originPlace` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '籍贯',
  `cardStatus` int NOT NULL DEFAULT 1 COMMENT '卡状态：1=正常，2=挂失，3=注销',
  `times` int NOT NULL DEFAULT 0 COMMENT '已借书次数',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `cardNo`(`cardNo` ASC) USING BTREE,
  UNIQUE INDEX `sno`(`sno` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 38 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of libcard
-- ----------------------------
INSERT INTO `libcard` VALUES (1, 'C202300101', '2023001', '张三', 3, '计算机学院', '软件工程', '2004-01-15', '北京', 4, 2);
INSERT INTO `libcard` VALUES (2, 'C202300201', '2023002', '李四', 3, '文学院', '汉语言文学', '2003-08-20', '上海', 2, 1);
INSERT INTO `libcard` VALUES (3, 'C202300302', '2023003', '王五', 3, '机械学院', '机械工程', '1990-05-10', '广州', 2, 3);
INSERT INTO `libcard` VALUES (4, 'C202300402', '2023004', '赵六', 3, '电子学院', '电子信息', '2004-11-05', '深圳', 3, 0);
INSERT INTO `libcard` VALUES (5, 'C202300501', '2023005', '周七', 3, '文学院', '汉语言文学', '2004-03-12', '天津', 4, 0);
INSERT INTO `libcard` VALUES (6, 'C202300601', '2023006', '吴八', 3, '文学院', '汉语国际教育', '2004-06-16', '重庆', 4, 0);
INSERT INTO `libcard` VALUES (7, 'C202300701', '2023007', '郑九', 3, '计算机学院', '网络工程', '2003-09-22', '成都', 1, 0);
INSERT INTO `libcard` VALUES (8, 'C202300801', '2023008', '钱十', 3, '计算机学院', '人工智能', '2004-02-05', '杭州', 1, 0);
INSERT INTO `libcard` VALUES (9, 'C202300901', '2023009', '孙一', 3, '机械学院', '智能制造', '2003-07-11', '武汉', 1, 0);
INSERT INTO `libcard` VALUES (10, 'C202301001', '2023010', '冯二', 3, '电子学院', '通信工程', '2004-10-19', '南京', 2, 0);
INSERT INTO `libcard` VALUES (11, 'C202301101', '2023011', '陈三', 3, '外国语学院', '英语', '2004-01-08', '西安', 1, 0);
INSERT INTO `libcard` VALUES (12, 'C202301201', '2023012', '褚四', 3, '外国语学院', '日语', '2003-04-14', '青岛', 1, 0);
INSERT INTO `libcard` VALUES (13, 'C202301301', '2023013', '卫五', 3, '经管学院', '会计学', '2004-08-21', '大连', 1, 0);
INSERT INTO `libcard` VALUES (14, 'C202301401', '2023014', '蒋六', 3, '经管学院', '市场营销', '2003-11-03', '厦门', 1, 0);
INSERT INTO `libcard` VALUES (15, 'C202301501', '2023015', '沈七', 3, '法学院', '法学', '2004-05-17', '合肥', 1, 0);
INSERT INTO `libcard` VALUES (16, 'C202301601', '2023016', '韩八', 3, '艺术学院', '视觉传达', '2003-12-25', '兰州', 1, 0);
INSERT INTO `libcard` VALUES (17, 'C202301701', '2023017', '杨九', 3, '体育学院', '体育教育', '2004-02-09', '贵阳', 1, 0);
INSERT INTO `libcard` VALUES (18, 'C202301801', '2023018', '朱十', 3, '医学院', '护理学', '2003-06-13', '昆明', 1, 0);
INSERT INTO `libcard` VALUES (19, 'C202301901', '2023019', '秦一', 3, '文学院', '古典文献学', '2004-09-20', '太原', 1, 0);
INSERT INTO `libcard` VALUES (20, 'C202302001', '2023020', '尤二', 3, '计算机学院', '大数据技术', '2003-03-07', '长春', 1, 0);
INSERT INTO `libcard` VALUES (21, 'C202302101', '2023021', '许三', 3, '机械学院', '车辆工程', '2004-07-15', '哈尔滨', 4, 0);
INSERT INTO `libcard` VALUES (22, 'C202302201', '2023022', '何四', 3, '电子学院', '微电子', '2003-10-23', '南宁', 1, 0);
INSERT INTO `libcard` VALUES (23, 'C202302301', '2023023', '吕五', 3, '外国语学院', '韩语', '2004-01-01', '惠州', 1, 0);
INSERT INTO `libcard` VALUES (24, 'C202302401', '2023024', '施六', 3, '经管学院', '金融管理', '2003-04-10', '中山', 1, 0);
INSERT INTO `libcard` VALUES (25, 'C202302501', '2023025', '张七', 3, '法学院', '社会工作', '2004-08-16', '珠海', 1, 0);
INSERT INTO `libcard` VALUES (26, 'C202302601', '2023026', '孔八', 3, '艺术学院', '音乐表演', '2003-11-24', '泉州', 1, 0);
INSERT INTO `libcard` VALUES (27, 'C202302701', '2023027', '曹九', 3, '体育学院', '运动训练', '2004-02-06', '烟台', 1, 0);
INSERT INTO `libcard` VALUES (28, 'C202302801', '2023028', '严十', 3, '医学院', '临床医学', '2003-06-12', '潍坊', 1, 0);
INSERT INTO `libcard` VALUES (29, 'C202302901', '2023029', '华一', 3, '文学院', '戏剧影视文学', '2004-09-18', '济宁', 1, 0);
INSERT INTO `libcard` VALUES (30, 'C202303001', '2023030', '金二', 3, '计算机学院', '信息安全', '2003-03-26', '聊城', 1, 0);
INSERT INTO `libcard` VALUES (31, 'C202303101', '2023031', '魏三', 3, '机械学院', '材料工程', '2004-07-04', '德州', 1, 0);
INSERT INTO `libcard` VALUES (32, 'C202303201', '2023032', '陶四', 3, '电子学院', '光电信息', '2003-10-12', '滨州', 1, 0);
INSERT INTO `libcard` VALUES (33, 'C202303301', '2023033', '姜五', 3, '外国语学院', '法语', '2004-01-20', '临沂', 1, 0);
INSERT INTO `libcard` VALUES (34, 'C202303401', '2023034', '戚六', 3, '经管学院', '国际贸易', '2003-04-02', '菏泽', 1, 0);
INSERT INTO `libcard` VALUES (35, 'C202303501', '2023035', '谢七', 3, '法学院', '知识产权', '2004-08-08', '枣庄', 1, 0);
INSERT INTO `libcard` VALUES (36, 'C202303601', '2023036', '邹八', 3, '艺术学院', '环境设计', '2003-11-16', '泰安', 1, 0);
INSERT INTO `libcard` VALUES (37, 'C202303901', '2023039', '水一', 3, '历史学院', '历史学', '2004-09-17', '聊城', 1, 3);

-- ----------------------------
-- Table structure for notice
-- ----------------------------
DROP TABLE IF EXISTS `notice`;
CREATE TABLE `notice`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键ID，自增',
  `topic` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '公告标题',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '公告内容',
  `author` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '发布人',
  `createDate` date NOT NULL COMMENT '发布日期',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of notice
-- ----------------------------
INSERT INTO `notice` VALUES (1, '图书馆闭馆通知', '因系统升级维护，本馆将于2026-06-15闭馆一天，请读者提前安排好借书还书事宜。', '管理员', '2026-06-08');
INSERT INTO `notice` VALUES (2, '新书上架公告', '本馆新到一批计算机类书籍，包括《深入理解计算机系统》《算法导论》等，欢迎读者借阅。', '管理员', '2026-06-01');
INSERT INTO `notice` VALUES (3, '逾期罚款提醒', '请各位读者及时归还逾期图书，逾期将按每天0.5元收取罚款，谢谢配合。', '管理员', '2026-05-20');

-- ----------------------------
-- Table structure for operationlog
-- ----------------------------
DROP TABLE IF EXISTS `operationlog`;
CREATE TABLE `operationlog`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '日志编号',
  `adminId` int NOT NULL COMMENT '操作管理员ID',
  `operateTime` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间',
  `operateType` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '操作类型',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '操作内容',
  `ip` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '操作IP地址',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of operationlog
-- ----------------------------
INSERT INTO `operationlog` VALUES (1, 1, '2026-06-08 11:17:28', '登录系统', '管理员登录图书管理系统', '192.168.1.100');
INSERT INTO `operationlog` VALUES (2, 1, '2026-06-08 11:17:28', '新增图书', '新增图书《深入理解计算机系统》', '192.168.1.100');
INSERT INTO `operationlog` VALUES (3, 2, '2026-06-08 11:17:28', '办理借阅卡', '为学生2023001办理借阅卡', '192.168.1.101');
INSERT INTO `operationlog` VALUES (4, 2, '2026-06-08 11:17:28', '挂失借阅卡', '为学生2023002挂失借阅卡', '192.168.1.101');
INSERT INTO `operationlog` VALUES (5, 1, '2026-06-16 09:20:10', '新增图书', '批量上架中外名著30本', '192.168.1.102');
INSERT INTO `operationlog` VALUES (6, 2, '2026-06-16 09:22:30', '办理借阅卡', '为多名新生统一办理借阅卡', '192.168.1.103');
INSERT INTO `operationlog` VALUES (7, 2, '2026-06-17 21:05:48', '挂失借阅卡', '为学生2023001挂失借阅卡', '192.168.1.101');
INSERT INTO `operationlog` VALUES (8, 2, '2026-06-17 21:05:48', '注销借阅卡', '为学生2023001注销借阅卡', '192.168.1.101');
INSERT INTO `operationlog` VALUES (9, 2, '2026-06-17 21:05:48', '补办借阅卡', '为学生2023003补办借阅卡，更换新卡号', '192.168.1.101');
INSERT INTO `operationlog` VALUES (10, 2, '2026-06-17 21:05:48', '挂失借阅卡', '为学生2023003挂失借阅卡', '192.168.1.101');
INSERT INTO `operationlog` VALUES (11, 2, '2026-06-17 21:05:48', '注销借阅卡', '为学生2023004注销旧借阅卡', '192.168.1.101');
INSERT INTO `operationlog` VALUES (12, 2, '2026-06-17 21:05:48', '补办借阅卡', '为学生2023004补办借阅卡，更换新卡号', '192.168.1.101');
INSERT INTO `operationlog` VALUES (13, 2, '2026-06-17 21:05:48', '注销借阅卡', '为学生2023005注销借阅卡', '192.168.1.101');
INSERT INTO `operationlog` VALUES (14, 2, '2026-06-17 21:05:48', '挂失借阅卡', '为学生2023010挂失借阅卡', '192.168.1.101');
INSERT INTO `operationlog` VALUES (15, 2, '2026-06-17 21:05:48', '注销借阅卡', '为学生2023021注销借阅卡', '192.168.1.101');
INSERT INTO `operationlog` VALUES (16, 1, '2026-06-17 14:55:16', '办理借阅卡', '为学号2023039新办借阅卡，卡号：C202303901', '127.0.0.1');
INSERT INTO `operationlog` VALUES (17, 1, '2026-06-17 14:56:16', '挂失借阅卡', '为学号2023006挂失卡号：C202300601', '127.0.0.1');
INSERT INTO `operationlog` VALUES (18, 1, '2026-06-17 15:10:56', '注销借阅卡', '为学号2023006注销卡号：C202300601', '127.0.0.1');

-- ----------------------------
-- Table structure for payrec
-- ----------------------------
DROP TABLE IF EXISTS `payrec`;
CREATE TABLE `payrec`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键ID，自增',
  `serNum` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '缴费流水号',
  `sno` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '读者学号/编号',
  `payAmount` decimal(10, 2) NOT NULL COMMENT '缴费金额',
  `payDate` date NOT NULL COMMENT '缴费日期',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `serNum`(`serNum` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of payrec
-- ----------------------------
INSERT INTO `payrec` VALUES (1, 'PAY20260001', '2023003', 8.00, '2026-05-20');
INSERT INTO `payrec` VALUES (2, 'PAY20260002', '2023005', 5.00, '2026-05-22');
INSERT INTO `payrec` VALUES (3, 'PAY20260003', '2023006', 12.50, '2026-06-01');
INSERT INTO `payrec` VALUES (4, 'PAY20260004', '2023002', 1.50, '2026-05-26');
INSERT INTO `payrec` VALUES (5, 'PAY20260005', '2023007', 1.50, '2026-05-29');
INSERT INTO `payrec` VALUES (6, 'PAY20260006', '2023010', 1.00, '2026-05-31');
INSERT INTO `payrec` VALUES (7, 'PAY20260007', '2023004', 1.00, '2026-06-03');
INSERT INTO `payrec` VALUES (8, 'PAY20260008', '2023008', 1.00, '2026-06-07');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色唯一标识',
  `role_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色名称',
  `remark` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '角色说明',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '系统角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('cataloger', '编目员', '负责图书信息录入、分类、编目管理');
INSERT INTO `role` VALUES ('librarian', '图书管理员', '负责借书还书、馆藏管理、逾期处理，以及借阅卡办理、补办、挂失业务');
INSERT INTO `role` VALUES ('reader', '读者', '普通借阅用户，可查询、借书、还书、缴费');

-- ----------------------------
-- Table structure for ruleconfig
-- ----------------------------
DROP TABLE IF EXISTS `ruleconfig`;
CREATE TABLE `ruleconfig`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '规则编号',
  `ruleName` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '规则名称',
  `ruleValue` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '规则值',
  `intro` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '规则说明',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ruleconfig
-- ----------------------------
INSERT INTO `ruleconfig` VALUES (1, '学生最大借阅数', '5', '学生一次最多可借5本书');
INSERT INTO `ruleconfig` VALUES (2, '教师最大借阅数', '10', '教师一次最多可借10本书');
INSERT INTO `ruleconfig` VALUES (3, '默认借阅天数', '30', '图书默认借阅期限为30天');
INSERT INTO `ruleconfig` VALUES (4, '逾期每日罚款', '0.5', '图书逾期每天罚款0.5元');
INSERT INTO `ruleconfig` VALUES (5, '借阅卡押金', '20', '办理借阅卡所需押金金额');

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键ID，自增',
  `sno` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '学号/读者编号',
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名/姓名',
  `type` int NOT NULL COMMENT '读者类型：1=学生，2=教师，3=其他',
  `collage` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '所属学院',
  `major` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '专业',
  `birth` date NULL DEFAULT NULL COMMENT '出生日期',
  `originPlace` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '籍贯',
  `password` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '123456' COMMENT '登录密码',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `sno`(`sno` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 40 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES (1, '2023001', '张三', 3, '计算机学院', '软件工程', '2004-01-15', '北京', '123456');
INSERT INTO `student` VALUES (2, '2023002', '李四', 3, '文学院', '汉语言文学', '2003-08-20', '上海', '123456');
INSERT INTO `student` VALUES (3, '2023003', '王五', 3, '机械学院', '机械工程', '1990-05-10', '广州', '123456');
INSERT INTO `student` VALUES (4, '2023004', '赵六', 3, '电子学院', '电子信息', '2004-11-05', '深圳', '123456');
INSERT INTO `student` VALUES (5, '2023005', '周七', 3, '文学院', '汉语言文学', '2004-03-12', '天津', '123456');
INSERT INTO `student` VALUES (6, '2023006', '吴八', 3, '文学院', '汉语国际教育', '2004-06-18', '重庆', '123456');
INSERT INTO `student` VALUES (7, '2023007', '郑九', 3, '计算机学院', '网络工程', '2003-09-22', '成都', '123456');
INSERT INTO `student` VALUES (8, '2023008', '钱十', 3, '计算机学院', '人工智能', '2004-02-05', '杭州', '123456');
INSERT INTO `student` VALUES (9, '2023009', '孙一', 3, '机械学院', '智能制造', '2003-07-11', '武汉', '123456');
INSERT INTO `student` VALUES (10, '2023010', '冯二', 3, '电子学院', '通信工程', '2004-10-19', '南京', '123456');
INSERT INTO `student` VALUES (11, '2023011', '陈三', 3, '外国语学院', '英语', '2004-01-08', '西安', '123456');
INSERT INTO `student` VALUES (12, '2023012', '褚四', 3, '外国语学院', '日语', '2003-04-14', '青岛', '123456');
INSERT INTO `student` VALUES (13, '2023013', '卫五', 3, '经管学院', '会计学', '2004-08-21', '大连', '123456');
INSERT INTO `student` VALUES (14, '2023014', '蒋六', 3, '经管学院', '市场营销', '2003-11-03', '厦门', '123456');
INSERT INTO `student` VALUES (15, '2023015', '沈七', 3, '法学院', '法学', '2004-05-17', '合肥', '123456');
INSERT INTO `student` VALUES (16, '2023016', '韩八', 3, '艺术学院', '视觉传达', '2003-12-25', '兰州', '123456');
INSERT INTO `student` VALUES (17, '2023017', '杨九', 3, '体育学院', '体育教育', '2004-02-09', '贵阳', '123456');
INSERT INTO `student` VALUES (18, '2023018', '朱十', 3, '医学院', '护理学', '2003-06-13', '昆明', '123456');
INSERT INTO `student` VALUES (19, '2023019', '秦一', 3, '文学院', '古典文献学', '2004-09-20', '太原', '123456');
INSERT INTO `student` VALUES (20, '2023020', '尤二', 3, '计算机学院', '大数据技术', '2003-03-07', '长春', '123456');
INSERT INTO `student` VALUES (21, '2023021', '许三', 3, '机械学院', '车辆工程', '2004-07-15', '哈尔滨', '123456');
INSERT INTO `student` VALUES (22, '2023022', '何四', 3, '电子学院', '微电子', '2003-10-23', '南宁', '123456');
INSERT INTO `student` VALUES (23, '2023023', '吕五', 3, '外国语学院', '韩语', '2004-01-01', '惠州', '123456');
INSERT INTO `student` VALUES (24, '2023024', '施六', 3, '经管学院', '金融管理', '2003-04-10', '中山', '123456');
INSERT INTO `student` VALUES (25, '2023025', '张七', 3, '法学院', '社会工作', '2004-08-16', '珠海', '123456');
INSERT INTO `student` VALUES (26, '2023026', '孔八', 3, '艺术学院', '音乐表演', '2003-11-24', '泉州', '123456');
INSERT INTO `student` VALUES (27, '2023027', '曹九', 3, '体育学院', '运动训练', '2004-02-06', '烟台', '123456');
INSERT INTO `student` VALUES (28, '2023028', '严十', 3, '医学院', '临床医学', '2003-06-12', '潍坊', '123456');
INSERT INTO `student` VALUES (29, '2023029', '华一', 3, '文学院', '戏剧影视文学', '2004-09-18', '济宁', '123456');
INSERT INTO `student` VALUES (30, '2023030', '金二', 3, '计算机学院', '信息安全', '2003-03-26', '聊城', '123456');
INSERT INTO `student` VALUES (31, '2023031', '魏三', 3, '机械学院', '材料工程', '2004-07-04', '德州', '123456');
INSERT INTO `student` VALUES (32, '2023032', '陶四', 3, '电子学院', '光电信息', '2003-10-12', '滨州', '123456');
INSERT INTO `student` VALUES (33, '2023033', '姜五', 3, '外国语学院', '法语', '2004-01-20', '临沂', '123456');
INSERT INTO `student` VALUES (34, '2023034', '戚六', 3, '经管学院', '国际贸易', '2003-04-02', '菏泽', '123456');
INSERT INTO `student` VALUES (35, '2023035', '谢七', 3, '法学院', '知识产权', '2004-08-08', '枣庄', '123456');
INSERT INTO `student` VALUES (36, '2023036', '邹八', 3, '艺术学院', '环境设计', '2003-11-16', '泰安', '123456');
INSERT INTO `student` VALUES (37, '2023037', '喻九', 3, '体育学院', '社会体育', '2004-02-24', '莱芜', '123456');
INSERT INTO `student` VALUES (38, '2023038', '柏十', 3, '医学院', '药学', '2003-06-10', '日照', '123456');
INSERT INTO `student` VALUES (39, '2023039', '水一', 3, '历史学院', '历史学', '2004-09-18', '聊城', '123456');

SET FOREIGN_KEY_CHECKS = 1;
