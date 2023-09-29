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
-- Table structure for table `model_table_relation`
--

DROP TABLE IF EXISTS `model_table_relation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `model_table_relation` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `model_id` bigint NOT NULL COMMENT 'model id',
  `schema_name` varchar(64) NOT NULL DEFAULT '' COMMENT 'schema name',
  `table_name` varchar(64) NOT NULL COMMENT 'table name',
  `table_alias` varchar(64) NOT NULL COMMENT 'table alias',
  `status` int NOT NULL DEFAULT '1' COMMENT '1:valid,-1:deleted',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unq_model_table_relation` (`model_id`,`table_alias`),
  KEY `idx_model_id` (`model_id`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB AUTO_INCREMENT=127 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `model_table_relation`
--

LOCK TABLES `model_table_relation` WRITE;
/*!40000 ALTER TABLE `model_table_relation` DISABLE KEYS */;
INSERT INTO `model_table_relation` VALUES (12,10,'','pay_request','t1',1,'2023-03-11 15:15:48','2023-03-11 15:15:48'),(13,10,'','channel_provider','t2',1,'2023-03-11 15:15:48','2023-03-11 15:15:48'),(14,10,'','pay_request_back','t3',1,'2023-03-11 15:15:48','2023-03-11 15:15:48'),(15,11,'','pay_request_back','t1',1,'2023-03-11 15:15:48','2023-03-11 15:15:48'),(16,11,'','pay_request','t2',1,'2023-03-11 15:15:48','2023-03-11 15:15:48'),(17,11,'','channel_provider','t3',1,'2023-03-11 15:15:48','2023-03-11 15:15:48'),(111,23,'','dwd_flow_sku_d_ss','t1',1,'2023-06-27 16:17:17','2023-06-27 16:17:17'),(112,23,'','dim_shop_ss','t2',1,'2023-06-27 16:17:17','2023-06-27 16:17:17'),(113,23,'','dim_city_ss','t3',1,'2023-06-27 16:17:17','2023-06-27 16:17:17'),(114,23,'','dim_dt_ss','t4',1,'2023-06-27 16:17:17','2023-06-27 16:17:17'),(115,23,'','dim_sku_ss','t5',1,'2023-06-27 16:17:17','2023-06-27 16:17:17'),(116,23,'','dim_catagory_ss','t6',1,'2023-06-27 16:17:17','2023-06-27 16:17:17'),(117,24,'xy_ec_demo_flow','dws_trade_shop_d_ss','t1',1,'2023-06-27 16:17:17','2023-06-27 16:17:17'),(118,24,'xy_ec_demo_flow','dim_shop_ss','t2',1,'2023-06-27 16:17:17','2023-06-27 16:17:17'),(119,24,'xy_ec_demo_flow','dim_city_ss','t3',1,'2023-06-27 16:17:17','2023-06-27 16:17:17'),(120,24,'xy_ec_demo_flow','dim_dt_ss','t4',1,'2023-06-27 16:17:17','2023-06-27 16:17:17'),(121,25,'','dws_trade_shop_d_ss','t1',1,'2023-06-27 16:17:18','2023-06-27 16:17:18'),(122,25,'','dim_shop_ss','t2',1,'2023-06-27 16:17:18','2023-06-27 16:17:18'),(123,25,'','dim_city_ss','t3',1,'2023-06-27 16:17:18','2023-06-27 16:17:18'),(124,25,'','dim_dt_ss','t4',1,'2023-06-27 16:17:18','2023-06-27 16:17:18'),(125,25,'','dim_sku_ss','t5',1,'2023-06-27 16:17:18','2023-06-27 16:17:18'),(126,25,'','dim_catagory_ss','t6',1,'2023-06-27 16:17:18','2023-06-27 16:17:18');
/*!40000 ALTER TABLE `model_table_relation` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-09-29 12:39:28
