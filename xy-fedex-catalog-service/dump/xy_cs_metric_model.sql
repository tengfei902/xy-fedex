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
-- Table structure for table `metric_model`
--

DROP TABLE IF EXISTS `metric_model`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `metric_model` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `model_id_array` json NOT NULL COMMENT 'model id array',
  `model_id` varchar(256) GENERATED ALWAYS AS (json_extract(`model_id_array`,_utf8mb4'$')) STORED,
  `metric_id` bigint NOT NULL COMMENT 'metric id',
  `metric_type` int NOT NULL DEFAULT '0' COMMENT 'metric type,0:primary,1:derive',
  `formula` varchar(128) NOT NULL COMMENT 'formula',
  `advance_calculate` varchar(2048) DEFAULT NULL COMMENT 'advance calculate',
  `status` int NOT NULL DEFAULT '1' COMMENT '1:valid,-1:deleted',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `creator` varchar(64) DEFAULT NULL COMMENT 'creator user id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unq_metric` (`model_id`,`metric_id`),
  KEY `idx_metric` (`metric_id`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB AUTO_INCREMENT=200 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `metric_model`
--

LOCK TABLES `metric_model` WRITE;
/*!40000 ALTER TABLE `metric_model` DISABLE KEYS */;
INSERT INTO `metric_model` (`id`, `model_id_array`, `metric_id`, `metric_type`, `formula`, `advance_calculate`, `status`, `create_time`, `update_time`, `creator`) VALUES (179,'[23]',60,0,'count(if(user_action = \'expose\', user_id, NULL))','[{\"assist\":false}]',1,'2023-06-27 16:17:17','2023-06-27 16:17:17',NULL),(180,'[23]',61,0,'count(DISTINCT if(user_action = \'expose\', user_id, NULL))','[{\"assist\":false}]',1,'2023-06-27 16:17:17','2023-06-27 16:17:17',NULL),(181,'[23]',62,0,'count(if(user_action = \'click\', user_id, NULL))','[{\"assist\":false}]',1,'2023-06-27 16:17:17','2023-06-27 16:17:17',NULL),(182,'[23]',63,0,'count(DISTINCT if(user_action = \'click\', user_id, NULL))','[{\"assist\":false}]',1,'2023-06-27 16:17:17','2023-06-27 16:17:17',NULL),(183,'[23]',64,0,'count(if(user_action = \'view\', user_id, NULL))','[{\"assist\":false}]',1,'2023-06-27 16:17:17','2023-06-27 16:17:17',NULL),(184,'[23]',65,0,'count(DISTINCT if(user_action = \'view\', user_id, NULL))','[{\"assist\":false}]',1,'2023-06-27 16:17:17','2023-06-27 16:17:17',NULL),(185,'[23]',67,0,'count(if(user_action = \'buy\', user_id, NULL))','[{\"assist\":false}]',1,'2023-06-27 16:17:17','2023-06-27 16:17:17',NULL),(186,'[23]',68,0,'count(DISTINCT if(user_action = \'buy\', user_id, NULL))','[{\"assist\":false}]',1,'2023-06-27 16:17:17','2023-06-27 16:17:17',NULL),(187,'[23]',66,0,'count(DISTINCT if(user_action = \'buy\', user_id, NULL)) / count(DISTINCT if(user_action = \'view\', user_id, NULL))','[{\"assist\":false}]',1,'2023-06-27 16:17:17','2023-06-27 16:17:17',NULL),(188,'[24]',54,0,'sum(t1.sale_amt)','[{\"assist\":false}]',1,'2023-06-27 16:17:17','2023-06-27 16:17:17',NULL),(189,'[24]',55,0,'sum(t1.sale_cnt)','[{\"assist\":false}]',1,'2023-06-27 16:17:17','2023-06-27 16:17:17',NULL),(190,'[24]',56,0,'sum(t1.refund_amt)','[{\"assist\":false}]',1,'2023-06-27 16:17:17','2023-06-27 16:17:17',NULL),(191,'[24]',57,0,'sum(t1.refund_cnt)','[{\"assist\":false}]',1,'2023-06-27 16:17:17','2023-06-27 16:17:17',NULL),(192,'[24]',14,0,'sum(t1.fee)','[{\"assist\":false}]',1,'2023-06-27 16:17:17','2023-06-27 16:17:17',NULL),(193,'[25]',54,0,'sum(t1.sale_amt)','[{\"assist\":false}]',1,'2023-06-27 16:17:18','2023-06-27 16:17:18',NULL),(194,'[25]',55,0,'sum(t1.sale_cnt)','[{\"assist\":false}]',1,'2023-06-27 16:17:18','2023-06-27 16:17:18',NULL),(195,'[25]',56,0,'sum(t1.refund_amt)','[{\"assist\":false}]',1,'2023-06-27 16:17:18','2023-06-27 16:17:18',NULL),(196,'[25]',57,0,'sum(t1.refund_cnt)','[{\"assist\":false}]',1,'2023-06-27 16:17:18','2023-06-27 16:17:18',NULL),(197,'[25]',14,0,'sum(t1.fee)','[{\"assist\":false}]',1,'2023-06-27 16:17:18','2023-06-27 16:17:18',NULL);
/*!40000 ALTER TABLE `metric_model` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-09-29 12:39:35
