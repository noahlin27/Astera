-- --------------------------------------------------------
-- 主机:                           127.0.0.1
-- 服务器版本:                        10.6.3-MariaDB - mariadb.org binary distribution
-- 服务器操作系统:                      Win64
-- HeidiSQL 版本:                  11.3.0.6295
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

-- 正在导出表  astera.comment 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
/*!40000 ALTER TABLE `comment` ENABLE KEYS */;

-- 正在导出表  astera.feed 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `feed` DISABLE KEYS */;
/*!40000 ALTER TABLE `feed` ENABLE KEYS */;

-- 正在导出表  astera.login_ticket 的数据：~32 rows (大约)
/*!40000 ALTER TABLE `login_ticket` DISABLE KEYS */;
REPLACE INTO `login_ticket` (`id`, `user_id`, `ticket`, `expired`, `status`) VALUES
	(2, 11, '9927588bb7e640a582eb5779985dd439', '2021-09-03 14:56:00', 0),
	(3, 11, 'cfbaaab3aa244413b9ccc3d2478ca5da', '2021-09-03 14:58:56', 0),
	(4, 11, 'ee6aefcb3b1b429088d10aa49eefe3d4', '2021-09-03 15:00:04', 0),
	(5, 11, '328de644bd154a8ca597c0bbec7ae919', '2021-09-03 16:02:23', 0),
	(6, 11, '470956b839fa4bd0a4c648c31dda6d5a', '2021-09-06 11:11:19', 1),
	(7, 11, 'be8c1e452207475a8d29f82b1239d8cb', '2021-09-06 12:11:59', 1),
	(8, 12, '637cbf0460dc468bb61201d22362b01f', '2021-09-06 12:47:30', 1),
	(9, 11, '5f38bc554b514a898dbbb648c8cf82c4', '2021-09-06 12:48:15', 1),
	(10, 11, 'fc235c5e34a04ae59b111e9231b44071', '2021-09-06 12:49:08', 0),
	(11, 11, 'c98f16fdf01947e19ee4e7ee1128fb48', '2021-09-06 15:50:01', 1),
	(12, 11, 'df3ba5eac157490e9f255aa0fb44a7e5', '2021-09-06 16:30:55', 0),
	(13, 13, '30c7370951c042e7941c019a666313ee', '2021-09-06 16:31:29', 1),
	(14, 13, 'c3844d789b00459b8ea7d23739ac8b04', '2021-09-06 17:00:10', 1),
	(15, 13, '1a1e6e9082424bf3afe2673b089d4162', '2021-09-06 17:00:25', 1),
	(16, 14, 'a2a8df85b1b94089bb89b4571100420e', '2021-09-06 17:01:07', 1),
	(17, 1, '808fc70e35f34156966ac9624d70400f', '2021-09-06 18:46:39', 0),
	(18, 1, '2f232a732c3c41febedf8c7de7a8e835', '2021-09-06 18:47:58', 1),
	(19, 1, 'bf90981993b649b4bc5ff9be67bc8bf0', '2021-09-06 18:48:04', 1),
	(20, 1, '0c21b3c2626a4f0990ab326595bfe926', '2021-09-07 15:00:54', 1),
	(21, 11, '86a5eb9690c6444893ea0bbfc785de94', '2021-09-07 15:01:00', 0),
	(22, 11, 'd48db4f8deb041d5bd7c6c43472ad561', '2021-09-07 19:52:27', 0),
	(23, 11, '7283458721fb4e7698ee481458164fd3', '2021-09-08 12:57:04', 1),
	(24, 12, '4d566e2bef2e4c93a132c42dfc52e681', '2021-09-08 12:57:17', 0),
	(25, 13, '1aa63944d3f64747a29f6a9e387f5422', '2021-09-08 15:15:12', 0),
	(26, 14, '29f1d5a4e12248bd959603182fe74403', '2021-09-08 19:39:47', 0),
	(27, 14, '538b955575a84e80bfb3f50e5ea5a799', '2021-09-09 10:43:49', 0),
	(28, 14, '6f2a635e38cf48e59e7b57c67429af9f', '2021-09-09 11:49:29', 0),
	(29, 1, '03fee01a89e3476db74fa473734b6577', '2021-09-09 15:47:42', 0),
	(30, 1, '377fe37413334d9fbdc37bf22c6977d5', '2021-09-09 17:06:00', 0),
	(31, 1, 'b45ad18bdd534bb2a75183576a420fc6', '2021-09-09 18:41:57', 0),
	(32, 1, 'c11c2ad5c8e24983beffb39d718a16c4', '2021-09-10 17:57:57', 1),
	(33, 2, '1563ddd774d44f41bf8965409f87ad7a', '2021-09-10 17:58:12', 0);
/*!40000 ALTER TABLE `login_ticket` ENABLE KEYS */;

-- 正在导出表  astera.message 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `message` DISABLE KEYS */;
/*!40000 ALTER TABLE `message` ENABLE KEYS */;

-- 正在导出表  astera.question 的数据：~64 rows (大约)
/*!40000 ALTER TABLE `question` DISABLE KEYS */;
REPLACE INTO `question` (`id`, `title`, `content`, `user_id`, `create_time`, `comment_count`) VALUES
	(1, 'TITLE{0}', 'Content 0', 1, '2021-08-27 11:59:06', 4),
	(2, 'TITLE{1}', 'Content 1', 2, '2021-08-27 12:59:06', 2),
	(3, 'TITLE{2}', 'Content 2', 3, '2021-08-27 13:59:06', 8),
	(4, 'TITLE{3}', 'Content 3', 4, '2021-08-27 14:59:06', 6),
	(5, 'TITLE{4}', 'Content 4', 5, '2021-08-27 15:59:06', 5),
	(6, 'TITLE{5}', 'Content 5', 6, '2021-08-27 16:59:06', 3),
	(7, 'TITLE{6}', 'Content 6', 7, '2021-08-27 17:59:06', 8),
	(8, 'TITLE{7}', 'Content 7', 8, '2021-08-27 18:59:06', 7),
	(9, 'TITLE{8}', 'Content 8', 9, '2021-08-27 19:59:06', 3),
	(10, 'TITLE{9}', 'Content 9', 10, '2021-08-27 20:59:06', 10),
	(21, 'TITLE{0}', 'Content 0', 1, '2021-08-27 18:25:31', 4),
	(22, 'TITLE{1}', 'Content 1', 2, '2021-08-27 19:25:32', 5),
	(23, 'TITLE{2}', 'Content 2', 3, '2021-08-27 20:25:32', 3),
	(24, 'TITLE{3}', 'Content 3', 4, '2021-08-27 21:25:32', 9),
	(25, 'TITLE{4}', 'Content 4', 5, '2021-08-27 22:25:32', 6),
	(26, 'TITLE{5}', 'Content 5', 6, '2021-08-27 23:25:32', 2),
	(27, 'TITLE{6}', 'Content 6', 7, '2021-08-28 00:25:32', 1),
	(28, 'TITLE{7}', 'Content 7', 8, '2021-08-28 01:25:32', 1),
	(29, 'TITLE{8}', 'Content 8', 9, '2021-08-28 02:25:32', 7),
	(30, 'TITLE{9}', 'Content 9', 10, '2021-08-28 03:25:32', 5),
	(31, 'TITLE{0}', 'Content 0', 1, '2021-08-27 18:25:47', 2),
	(32, 'TITLE{1}', 'Content 1', 2, '2021-08-27 19:25:47', 5),
	(33, 'TITLE{2}', 'Content 2', 3, '2021-08-27 20:25:47', 3),
	(34, 'TITLE{3}', 'Content 3', 4, '2021-08-27 21:25:47', 1),
	(35, 'TITLE{4}', 'Content 4', 5, '2021-08-27 22:25:47', 6),
	(36, 'TITLE{5}', 'Content 5', 6, '2021-08-27 23:25:47', 10),
	(37, 'TITLE{6}', 'Content 6', 7, '2021-08-28 00:25:47', 2),
	(38, 'TITLE{7}', 'Content 7', 8, '2021-08-28 01:25:47', 8),
	(39, 'TITLE{8}', 'Content 8', 9, '2021-08-28 02:25:47', 6),
	(40, 'TITLE{9}', 'Content 9', 10, '2021-08-28 03:25:47', 7),
	(41, 'TITLE{0}', 'Content 0', 1, '2021-08-27 18:25:56', 1),
	(42, 'TITLE{1}', 'Content 1', 2, '2021-08-27 19:25:56', 5),
	(43, 'TITLE{2}', 'Content 2', 3, '2021-08-27 20:25:56', 4),
	(44, 'TITLE{3}', 'Content 3', 4, '2021-08-27 21:25:56', 2),
	(45, 'TITLE{4}', 'Content 4', 5, '2021-08-27 22:25:56', 9),
	(46, 'TITLE{5}', 'Content 5', 6, '2021-08-27 23:25:56', 6),
	(47, 'TITLE{6}', 'Content 6', 7, '2021-08-28 00:25:56', 3),
	(48, 'TITLE{7}', 'Content 7', 8, '2021-08-28 01:25:56', 6),
	(49, 'TITLE{8}', 'Content 8', 9, '2021-08-28 02:25:56', 5),
	(50, 'TITLE{9}', 'Content 9', 10, '2021-08-28 03:25:56', 1),
	(51, 'TITLE{0}', 'Content 0', 1, '2021-08-27 18:26:03', 8),
	(52, 'TITLE{1}', 'Content 1', 2, '2021-08-27 19:26:04', 5),
	(53, 'TITLE{2}', 'Content 2', 3, '2021-08-27 20:26:04', 10),
	(54, 'TITLE{3}', 'Content 3', 4, '2021-08-27 21:26:04', 1),
	(55, 'TITLE{4}', 'Content 4', 5, '2021-08-27 22:26:04', 9),
	(56, 'TITLE{5}', 'Content 5', 6, '2021-08-27 23:26:04', 7),
	(57, 'TITLE{6}', 'Content 6', 7, '2021-08-28 00:26:04', 6),
	(58, 'TITLE{7}', 'Content 7', 8, '2021-08-28 01:26:04', 7),
	(59, 'TITLE{8}', 'Content 8', 9, '2021-08-28 02:26:04', 6),
	(60, 'TITLE{9}', 'Content 9', 10, '2021-08-28 03:26:04', 8),
	(61, 'test1', 'Content test1', 11, '2021-09-07 14:01:27', 0),
	(62, '抵制**', '坚 决 抵 制 **\n坚 决 抵 制 #**#', 11, '2021-09-07 18:53:18', 0),
	(63, 'test2', 'whitespace test', 12, '2021-09-08 11:57:44', 0),
	(64, 'test2', 'whitespace test2\ntest', 12, '2021-09-08 11:58:48', 0),
	(68, 'test3', 'line1\nline2      \nline3', 13, '2021-09-08 14:15:44', 0),
	(72, 'test4', 'dupin *\nbaoli *\ndubo *\nseqing *', 14, '2021-09-09 10:21:32', 0),
	(73, 'test4', 'dupin ？？？\nbaoli ？？？', 14, '2021-09-09 10:23:48', 0),
	(74, 'test4', '你???', 14, '2021-09-09 10:24:35', 0),
	(75, 'test4', '你***', 14, '2021-09-09 10:24:52', 0),
	(77, 'test4', '&lt;script&gt;alert(&#39;hello&#39;)&lt;/script&gt;', 14, '2021-09-09 10:54:54', 0),
	(79, 'USER0', '毒品\n暴力\n赌博\n色情\n敏感词保存，读取时不可见', 1, '2021-09-09 16:06:08', 0),
	(80, 'USER0', '&lt;script&gt;alert(&#39;hello&#39;)&lt;/script&gt;\nhtml标签转义保存，读取时反转义', 1, '2021-09-09 16:08:26', 0),
	(82, 'filterTest', '毒品\n暴力\n赌博\n色情\n敏感词保存，读取时不可见', 2, '2021-09-10 16:59:26', 0),
	(83, 'filterTest', '&lt;script&gt;alert(&#39;hello&#39;)&lt;/script&gt;\nhtml标签转义保存，读取时反转义', 2, '2021-09-10 16:59:57', 0);
/*!40000 ALTER TABLE `question` ENABLE KEYS */;

-- 正在导出表  astera.user 的数据：~14 rows (大约)
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
REPLACE INTO `user` (`id`, `name`, `password`, `salt`, `head_url`, `is_deleted`) VALUES
	(1, 'USER0', '0d8573591cd3de34ae856af75dd54bce', 'ea454', 'https://images.nowcoder.com/head/510t.png', 0),
	(2, 'USER1', 'c7c386c7917ae14a1f68d6d6dbc06897', '88abc', 'https://images.nowcoder.com/head/550t.png', 0),
	(3, 'USER2', '915d2e65f7fa9066ebfd8603d88a4402', '1cd80', 'https://images.nowcoder.com/head/66t.png', 1),
	(4, 'USER3', '2054c278d54ed2bb78dc83c0b6931524', 'c1f9e', 'https://images.nowcoder.com/head/505t.png', 0),
	(5, 'USER4', 'd1dbdf6da23fdb267a1be7e7bfb3cfd6', 'cacbd', 'https://images.nowcoder.com/head/629t.png', 0),
	(6, 'USER5', '8ebc861b12760306f69c4baf29fd229b', '6d963', 'https://images.nowcoder.com/head/529t.png', 0),
	(7, 'USER6', '49b00e0098bbef9a0639c887cd8a2590', '87fae', 'https://images.nowcoder.com/head/570t.png', 0),
	(8, 'USER7', '312d665478b98fabcab687c00a6fd3f8', '77eec', 'https://images.nowcoder.com/head/103t.png', 0),
	(9, 'USER8', '9774352985d41192465c1a648b761c5c', '3a149', 'https://images.nowcoder.com/head/546t.png', 0),
	(10, 'USER9', 'fef2b9b16bc4ccf121d69857b958bc9e', '50d4e', 'https://images.nowcoder.com/head/64t.png', 0),
	(11, 'test1', '0a57923364ba7215b64940d74f54ff53', 'fc6e6', 'https://images.nowcoder.com/head/803t.png', 0),
	(12, 'test2', '99b2fb27b4d5fc83991447790abc3f99', 'd9675', 'https://images.nowcoder.com/head/954t.png', 0),
	(13, 'test3', '5a6fe1b2398b26cc7aa2417844775321', '61267', 'https://images.nowcoder.com/head/423t.png', 0),
	(14, 'test4', 'a227afd391c4bec92006ba47ab77b87c', '91b04', 'https://images.nowcoder.com/head/243t.png', 0);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
