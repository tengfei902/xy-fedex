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
-- Table structure for table `dim`
--

DROP TABLE IF EXISTS `dim`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dim` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `biz_line_id` bigint NOT NULL COMMENT 'biz line id',
  `dim_code` varchar(64) NOT NULL COMMENT 'dim code',
  `dim_name` varchar(64) NOT NULL DEFAULT '' COMMENT 'dim name',
  `dim_type` int NOT NULL DEFAULT '0' COMMENT 'dim type',
  `dim_comment` varchar(256) DEFAULT NULL COMMENT 'app desc',
  `status` int NOT NULL DEFAULT '1' COMMENT '1:valid,-1:deleted',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `creator` varchar(64) NOT NULL DEFAULT '' COMMENT 'creator user id',
  `tenant_id` varchar(64) NOT NULL DEFAULT '' COMMENT 'tenant',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unq_dim_code` (`biz_line_id`,`dim_code`)
) ENGINE=InnoDB AUTO_INCREMENT=115 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dim`
--

LOCK TABLES `dim` WRITE;
/*!40000 ALTER TABLE `dim` DISABLE KEYS */;
INSERT INTO `dim` VALUES (9,0,'dt','日期',0,'日期',1,'2023-03-10 16:17:40','2023-03-10 16:17:40','',''),(10,0,'hour','小时',0,'小时',1,'2023-03-10 16:17:40','2023-03-10 16:17:40','',''),(11,0,'group_id','组',0,'组',1,'2023-03-10 16:17:40','2023-03-10 16:17:40','',''),(12,0,'account_status','账户状态',0,'账户状态',1,'2023-03-10 16:17:40','2023-03-10 16:17:40','',''),(13,0,'out_trade_no','订单号',0,'订单号',1,'2023-03-10 16:17:40','2023-03-10 16:17:40','',''),(14,0,'opr_type','订单类型',0,'订单类型',1,'2023-03-10 16:17:40','2023-03-10 16:17:40','',''),(15,0,'provider_code','供应商编号',0,'供应商编号',1,'2023-03-10 16:17:40','2023-03-10 16:17:40','',''),(16,0,'provider_name','供应商名称',0,'供应商名称',1,'2023-03-10 16:17:40','2023-03-10 16:17:40','',''),(17,0,'mch_id','客户id',0,'客户id',1,'2023-03-10 16:17:40','2023-03-10 16:17:40','',''),(18,0,'service','通道类型',0,'通道类型',1,'2023-03-10 16:17:40','2023-03-10 16:17:40','',''),(19,0,'trade_type','交易类型',0,'交易类型',1,'2023-03-10 16:17:40','2023-03-10 16:17:40','',''),(20,0,'pay_result','交易结果',0,'交易结果',1,'2023-03-10 16:17:40','2023-03-10 16:17:40','',''),(21,0,'buyer_id','购买方id',0,'购买方id',1,'2023-03-10 16:17:40','2023-03-10 16:17:40','',''),(22,0,'appid','app',0,'app',1,'2023-03-10 16:19:21','2023-03-10 16:19:21','',''),(23,0,'status','状态',0,'状态',1,'2023-03-10 16:20:22','2023-03-10 16:20:22','',''),(24,0,'channel_provider_code','通道类型code',0,'通道类型code',1,'2023-03-10 16:21:37','2023-03-10 16:21:37','',''),(25,0,'channel_provider_name','通道类型',0,'通道类型',1,'2023-03-10 16:22:18','2023-03-10 16:22:18','',''),(70,0,'shop_id','',0,NULL,1,'2023-06-13 15:36:59','2023-06-13 15:36:59','',''),(71,0,'shop_name','',0,NULL,1,'2023-06-13 15:36:59','2023-06-13 15:36:59','',''),(72,0,'shop_full_name','',0,NULL,1,'2023-06-13 15:36:59','2023-06-13 15:36:59','',''),(73,0,'tenant_id','',0,NULL,1,'2023-06-13 15:36:59','2023-06-13 15:36:59','',''),(74,0,'tenant_name','',0,NULL,1,'2023-06-13 15:36:59','2023-06-13 15:36:59','',''),(75,0,'brand_id','',0,NULL,1,'2023-06-13 15:36:59','2023-06-13 15:36:59','',''),(76,0,'brand_name','',0,NULL,1,'2023-06-13 15:36:59','2023-06-13 15:36:59','',''),(77,0,'ditrict_code','',0,NULL,1,'2023-06-13 15:36:59','2023-06-13 15:36:59','',''),(78,0,'business_district_id','',0,NULL,1,'2023-06-13 15:36:59','2023-06-13 15:36:59','',''),(79,0,'latitude','',0,NULL,1,'2023-06-13 15:36:59','2023-06-13 15:36:59','',''),(80,0,'longitude','',0,NULL,1,'2023-06-13 15:36:59','2023-06-13 15:36:59','',''),(81,0,'district_name','',0,NULL,1,'2023-06-13 15:36:59','2023-06-13 15:36:59','',''),(82,0,'city_code','',0,NULL,1,'2023-06-13 15:36:59','2023-06-13 15:36:59','',''),(83,0,'city_name','',0,NULL,1,'2023-06-13 15:36:59','2023-06-13 15:36:59','',''),(84,0,'province_code','',0,NULL,1,'2023-06-13 15:36:59','2023-06-13 15:36:59','',''),(85,0,'province_name','',0,NULL,1,'2023-06-13 15:36:59','2023-06-13 15:36:59','',''),(86,0,'country_code','',0,NULL,1,'2023-06-13 15:36:59','2023-06-13 15:36:59','',''),(87,0,'country_name','',0,NULL,1,'2023-06-13 15:36:59','2023-06-13 15:36:59','',''),(88,0,'day_of_week','',0,NULL,1,'2023-06-13 15:36:59','2023-06-13 15:36:59','',''),(89,0,'day_of_month','',0,NULL,1,'2023-06-13 15:36:59','2023-06-13 15:36:59','',''),(90,0,'day_of_year','',0,NULL,1,'2023-06-13 15:36:59','2023-06-13 15:36:59','',''),(91,0,'week_of_month','',0,NULL,1,'2023-06-13 15:36:59','2023-06-13 15:36:59','',''),(92,0,'week_of_year','',0,NULL,1,'2023-06-13 15:36:59','2023-06-13 15:36:59','',''),(93,0,'month_of_year','',0,NULL,1,'2023-06-13 15:36:59','2023-06-13 15:36:59','',''),(94,0,'year','',0,NULL,1,'2023-06-13 15:36:59','2023-06-13 15:36:59','',''),(95,0,'holiday','',0,NULL,1,'2023-06-13 15:36:59','2023-06-13 15:41:51','',''),(96,0,'sku_id','',0,NULL,1,'2023-06-13 15:36:59','2023-06-13 15:36:59','',''),(97,0,'sku_name','',0,NULL,1,'2023-06-13 15:36:59','2023-06-13 15:36:59','',''),(98,0,'spu_id','',0,NULL,1,'2023-06-13 15:36:59','2023-06-13 15:36:59','',''),(99,0,'spu_name','',0,NULL,1,'2023-06-13 15:36:59','2023-06-13 15:36:59','',''),(100,0,'catagory1_id','',0,NULL,1,'2023-06-13 15:36:59','2023-06-13 15:36:59','',''),(101,0,'catagory1_name','',0,NULL,1,'2023-06-13 15:36:59','2023-06-13 15:36:59','',''),(102,0,'catagory2_id','',0,NULL,1,'2023-06-13 15:36:59','2023-06-13 15:36:59','',''),(103,0,'catagory2_name','',0,NULL,1,'2023-06-13 15:36:59','2023-06-13 15:36:59','',''),(104,0,'catagory3_id','',0,NULL,1,'2023-06-13 15:36:59','2023-06-13 15:36:59','',''),(105,0,'catagory3_name','',0,NULL,1,'2023-06-13 15:36:59','2023-06-13 15:36:59','',''),(106,0,'catagory4_id','',0,NULL,1,'2023-06-13 15:36:59','2023-06-13 15:36:59','',''),(107,0,'catagory4_name','',0,NULL,1,'2023-06-13 15:36:59','2023-06-13 15:36:59','',''),(108,0,'catagory5_id','',0,NULL,1,'2023-06-13 15:36:59','2023-06-13 15:36:59','',''),(109,0,'catagory5_name','',0,NULL,1,'2023-06-13 15:36:59','2023-06-13 15:36:59','',''),(110,0,'weight','',0,NULL,1,'2023-06-13 15:36:59','2023-06-13 15:36:59','',''),(111,0,'volume','',0,NULL,1,'2023-06-13 15:36:59','2023-06-13 15:36:59','',''),(112,0,'specifications','',0,NULL,1,'2023-06-13 15:37:00','2023-06-13 15:37:00','',''),(113,0,'manufacturer','',0,NULL,1,'2023-06-13 15:37:00','2023-06-13 15:37:00','',''),(114,0,'district_code','',0,NULL,1,'2023-06-20 17:03:19','2023-06-20 17:03:19','','');
/*!40000 ALTER TABLE `dim` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-07-22  0:07:21
