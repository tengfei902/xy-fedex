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
-- Table structure for table `model`
--

DROP TABLE IF EXISTS `model`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `model` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `biz_line_id` bigint NOT NULL COMMENT 'biz line id',
  `model_name` varchar(64) NOT NULL COMMENT 'model name',
  `model_desc` varchar(256) DEFAULT NULL COMMENT 'model desc',
  `dsn_id` bigint NOT NULL COMMENT 'dsn id',
  `table_source` varchar(2048) NOT NULL DEFAULT '' COMMENT 'table source',
  `condition` varchar(256) DEFAULT NULL COMMENT 'condition',
  `model_prop` text COMMENT 'model properties',
  `status` int NOT NULL DEFAULT '1' COMMENT '1:valid,-1:deleted',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `creator` varchar(64) NOT NULL DEFAULT '' COMMENT 'creator user id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unq_model_name` (`biz_line_id`,`model_name`),
  KEY `idx_biz_line` (`biz_line_id`),
  KEY `idx_status` (`status`),
  KEY `idx_creator` (`creator`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `model`
--

LOCK TABLES `model` WRITE;
/*!40000 ALTER TABLE `model` DISABLE KEYS */;
INSERT INTO `model` VALUES (10,0,'支付订单','支付订单',0,'pay_request t1 left join channel_provider t2 on t1.channel_provider_code = t2.provider_code left join pay_request_back t3 on t1.out_trade_no = t3.out_trade_no','t3.id is not null','null',1,'2023-03-11 15:15:47','2023-03-11 15:15:47',''),(11,0,'退款订单模型','退款订单',0,'pay_request_back t1 left join pay_request t2 on t1.out_trade_no = t2.out_trade_no left join channel_provider t3 on t2.channel_provider_code = t3.provider_code',NULL,'null',1,'2023-03-11 15:15:48','2023-03-11 15:15:48',''),(23,0,'flow_shop_sku_dt','商品流量数据',0,'dwd_flow_sku_d_ss t1 LEFT JOIN dim_shop_ss t2 ON t1.shop_id = t2.shop_id\nAND t1.dt = t2.dt LEFT JOIN dim_city_ss t3 ON t2.district_code = t3.district_code\nAND t2.dt = t3.dt LEFT JOIN dim_dt_ss t4 ON t1.dt = t4.dt LEFT JOIN dim_sku_ss t5 ON t1.sku_id = t5.sku_id\nAND t1.dt = t5.dt LEFT JOIN dim_catagory_ss t6 ON t5.dt = t6.dt\nAND t5.catagory_id = t6.catagory5_id',NULL,'null',1,'2023-06-21 16:09:34','2023-06-21 16:09:34',''),(24,0,'trade_shop_dt','门店交易数据',0,'xy_ec_demo_flow.dws_trade_shop_d_ss t1 LEFT JOIN xy_ec_demo_flow.dim_shop_ss t2 ON t1.shop_id = t2.shop_id\nAND t1.dt = t2.dt LEFT JOIN xy_ec_demo_flow.dim_city_ss t3 ON t2.district_code = t3.district_code\nAND t2.dt = t3.dt LEFT JOIN xy_ec_demo_flow.dim_dt_ss t4 ON t1.dt = t4.dt',NULL,'null',1,'2023-06-21 16:09:34','2023-06-21 16:09:34',''),(25,0,'trade_shop_sku_dt','商品交易数据',0,'dws_trade_shop_d_ss t1 LEFT JOIN dim_shop_ss t2 ON t1.shop_id = t2.shop_id\nAND t1.dt = t2.dt LEFT JOIN dim_city_ss t3 ON t2.district_code = t3.district_code\nAND t2.dt = t3.dt LEFT JOIN dim_dt_ss t4 ON t1.dt = t4.dt LEFT JOIN dim_sku_ss t5 ON t1.sku_id = t5.sku_id LEFT JOIN dim_catagory_ss t6 ON t1.category_id = t6.category_id',NULL,'null',1,'2023-06-21 16:09:35','2023-06-21 16:09:35','');
/*!40000 ALTER TABLE `model` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-07-22  0:07:19
