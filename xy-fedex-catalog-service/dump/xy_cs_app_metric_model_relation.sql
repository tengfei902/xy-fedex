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
-- Table structure for table `app_metric_model_relation`
--

DROP TABLE IF EXISTS `app_metric_model_relation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `app_metric_model_relation` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `app_id` bigint NOT NULL COMMENT 'app id',
  `metric_model_id` bigint NOT NULL COMMENT 'metric model id',
  `status` int NOT NULL DEFAULT '1' COMMENT '1:valid,-1:invalid',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `creator` varchar(64) DEFAULT NULL COMMENT 'creator user id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unq_app_metric_model` (`app_id`,`metric_model_id`)
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `app_metric_model_relation`
--

LOCK TABLES `app_metric_model_relation` WRITE;
/*!40000 ALTER TABLE `app_metric_model_relation` DISABLE KEYS */;
INSERT INTO `app_metric_model_relation` VALUES (39,21,14,1,'2023-03-15 16:03:28','2023-03-15 16:03:28',NULL),(40,21,15,1,'2023-03-15 16:03:28','2023-03-15 16:03:28',NULL),(41,21,16,1,'2023-03-15 16:03:28','2023-03-15 16:03:28',NULL),(42,21,17,1,'2023-03-15 16:03:28','2023-03-15 16:03:28',NULL),(43,21,18,1,'2023-03-15 16:03:28','2023-03-15 16:03:28',NULL),(44,21,19,1,'2023-03-15 16:03:28','2023-03-15 16:03:28',NULL),(45,21,20,1,'2023-03-15 16:03:28','2023-03-15 16:03:28',NULL),(46,21,21,1,'2023-03-15 16:03:28','2023-03-15 16:03:28',NULL),(47,21,22,1,'2023-03-15 16:03:28','2023-03-15 16:03:28',NULL),(48,21,23,1,'2023-03-15 16:03:28','2023-03-15 16:03:28',NULL);
/*!40000 ALTER TABLE `app_metric_model_relation` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-09-29 12:39:33
