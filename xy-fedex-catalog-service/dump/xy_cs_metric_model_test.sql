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
-- Table structure for table `metric_model_test`
--

DROP TABLE IF EXISTS `metric_model_test`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `metric_model_test` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `model_id_struct` json NOT NULL COMMENT 'model id',
  `model_id` varchar(256) GENERATED ALWAYS AS (json_extract(`model_id_struct`,_utf8mb4'$')) STORED,
  `metric_id` bigint NOT NULL COMMENT 'metric id struct',
  `metric_type` int NOT NULL DEFAULT '0' COMMENT 'metric type,0:primary,1:derive',
  `formula` varchar(128) NOT NULL COMMENT 'formula',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unq_metric` (`model_id`,`metric_id`),
  KEY `idx_metric` (`metric_id`)
) ENGINE=InnoDB AUTO_INCREMENT=97 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `metric_model_test`
--

LOCK TABLES `metric_model_test` WRITE;
/*!40000 ALTER TABLE `metric_model_test` DISABLE KEYS */;
INSERT INTO `metric_model_test` (`id`, `model_id_struct`, `metric_id`, `metric_type`, `formula`) VALUES (96,'[\"a\", \"b\", \"c\"]',1000,0,'a+b+c');
/*!40000 ALTER TABLE `metric_model_test` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-09-29 12:39:32
