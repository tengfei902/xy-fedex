-- MySQL dump 10.13  Distrib 8.0.31, for macos12 (x86_64)
--
-- Host: 127.0.0.1    Database: xy_cs
-- ------------------------------------------------------
-- Server version	8.0.31

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `app_column`
--

DROP TABLE IF EXISTS `app_column`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `app_column` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `app_id` bigint NOT NULL COMMENT 'biz line id',
  `column_name` varchar(64) NOT NULL COMMENT 'column name',
  `column_type` int NOT NULL COMMENT 'column type',
  `column_id` bigint NOT NULL COMMENT 'dim id or metric id',
  `column_format` varchar(64) NOT NULL DEFAULT '' COMMENT 'column format',
  `status` int NOT NULL DEFAULT '1' COMMENT '1:valid,-1:deleted',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `column_code` varchar(64) NOT NULL DEFAULT '' COMMENT 'column code',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unq_l` (`app_id`,`column_name`)
) ENGINE=InnoDB AUTO_INCREMENT=253 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `app_column`
--

LOCK TABLES `app_column` WRITE;
/*!40000 ALTER TABLE `app_column` DISABLE KEYS */;
INSERT INTO `app_column` VALUES (232,33,'销售额',0,54,'decimal(38, 6)',1,'2023-06-21 16:18:07','2023-06-21 16:18:07','sale_amt'),(233,33,'销量',0,55,'int',1,'2023-06-21 16:18:07','2023-06-21 16:18:07','sale_cnt'),(234,33,'退款额',0,56,'decimal(38, 6)',1,'2023-06-21 16:18:07','2023-06-21 16:18:07','refund_amt'),(235,33,'退款量',0,57,'int',1,'2023-06-21 16:18:07','2023-06-21 16:18:07','refund_cnt'),(236,33,'退款率',0,58,'decimal(38, 6)',1,'2023-06-21 16:18:07','2023-06-21 16:18:07','refund_amt_rate'),(237,33,'退货率',0,59,'decimal(38, 6)',1,'2023-06-21 16:18:07','2023-06-21 16:18:07','refund_cnt_rate'),(238,33,'曝光pv',0,60,'bigint',1,'2023-06-21 16:18:07','2023-06-21 16:18:07','expose_pv'),(239,33,'曝光uv',0,61,'bigint',1,'2023-06-21 16:18:07','2023-06-21 16:18:07','expose_uv'),(240,33,'点击pv',0,62,'bigint',1,'2023-06-21 16:18:07','2023-06-21 16:18:07','click_pv'),(241,33,'点击uv',0,63,'bigint',1,'2023-06-21 16:18:07','2023-06-21 16:18:07','click_uv'),(242,33,'浏览pv',0,64,'bigint',1,'2023-06-21 16:18:07','2023-06-21 16:18:07','view_pv'),(243,33,'浏览uv',0,65,'bigint',1,'2023-06-21 16:18:07','2023-06-21 16:18:07','view_uv'),(244,33,'访购率',0,66,'decimal(38, 6)',1,'2023-06-21 16:18:07','2023-06-21 16:18:07','visit_buy_rate'),(245,33,'日期',1,9,'string',1,'2023-06-21 16:18:07','2023-06-21 16:18:07','dt'),(246,33,'门店id',1,70,'bigint',1,'2023-06-21 16:18:07','2023-06-21 16:18:07','shop_id'),(247,33,'门店名',1,71,'string',1,'2023-06-21 16:18:07','2023-06-21 16:18:07','shop_name'),(248,33,'门店全名',1,72,'string',1,'2023-06-21 16:18:07','2023-06-21 16:18:07','shop_full_name'),(249,33,'租户ID',1,73,'bigint',1,'2023-06-21 16:18:07','2023-06-21 16:18:07','tenant_id'),(250,33,'租户名',1,74,'string',1,'2023-06-21 16:18:07','2023-06-21 16:18:07','tenant_name'),(251,33,'品牌id',1,75,'bigint',1,'2023-06-21 16:18:07','2023-06-21 16:18:07','brand_id'),(252,33,'品牌名',1,76,'string',1,'2023-06-21 16:18:07','2023-06-21 16:18:07','brand_name');
/*!40000 ALTER TABLE `app_column` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-07-22  0:07:18
