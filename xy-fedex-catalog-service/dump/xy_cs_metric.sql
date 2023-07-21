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
-- Table structure for table `metric`
--

DROP TABLE IF EXISTS `metric`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `metric` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `biz_line_id` bigint NOT NULL COMMENT 'biz line id',
  `subject_id` bigint NOT NULL DEFAULT '0' COMMENT 'metric subject',
  `metric_code` varchar(64) NOT NULL COMMENT 'metric code',
  `formula` varchar(128) NOT NULL DEFAULT '' COMMENT 'default formula',
  `metric_name` varchar(64) NOT NULL DEFAULT '' COMMENT 'metric name',
  `metric_comment` varchar(256) DEFAULT NULL COMMENT 'app desc',
  `unit` int NOT NULL DEFAULT '0' COMMENT 'metric unit',
  `metric_format` int NOT NULL DEFAULT '0' COMMENT 'metric format',
  `status` int NOT NULL DEFAULT '1' COMMENT '1:valid,-1:deleted',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `creator` varchar(64) NOT NULL DEFAULT '' COMMENT 'creator user id',
  `metric_type` int NOT NULL DEFAULT '0' COMMENT 'metric type,0:primary,1:derive',
  `tenant` varchar(64) NOT NULL DEFAULT '' COMMENT 'tenant',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unq_metric_code` (`biz_line_id`,`metric_code`)
) ENGINE=InnoDB AUTO_INCREMENT=69 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `metric`
--

LOCK TABLES `metric` WRITE;
/*!40000 ALTER TABLE `metric` DISABLE KEYS */;
INSERT INTO `metric` VALUES (13,0,0,'order_cnt','count(distinct out_trade_no)','订单数','订单数',0,0,1,'2023-03-03 15:49:21','2023-03-03 15:49:21','',0,''),(14,0,0,'fee','sum(fee)','手续费','手续费',0,0,1,'2023-03-03 15:49:21','2023-03-03 15:49:21','',0,''),(15,0,0,'actual_amount','sum(actual_amount)','实际金额','实际金额',0,0,1,'2023-03-03 15:49:21','2023-03-03 15:49:21','',0,''),(16,0,0,'amount','sum(amount)','交易金额','交易金额',0,0,1,'2023-03-03 15:49:21','2023-03-03 15:49:21','',0,''),(17,0,0,'lock_amount','sum(lock_amount)','锁定金额',NULL,0,0,1,'2023-03-03 15:49:21','2023-03-03 15:49:21','',0,''),(18,0,0,'paid_amount','sum(paid_amount)','已支付金额',NULL,0,0,1,'2023-03-03 15:49:21','2023-03-03 15:49:21','',0,''),(19,0,0,'total_amount','sum(total_amount)','总金额',NULL,0,0,1,'2023-03-03 15:49:21','2023-03-03 15:49:21','',0,''),(20,0,0,'agent_pay_amount','sum(agent_pay_amount)','机构支付金额','机构支付金额',0,0,1,'2023-03-03 15:49:21','2023-03-03 15:49:21','',0,''),(21,0,0,'refund_order_cnt','count(distinct out_trade_no)','退款订单','退款订单',0,0,1,'2023-03-03 15:49:21','2023-03-03 15:49:21','',0,''),(22,0,0,'refund_amount','sum(total_fee)','',NULL,0,0,1,'2023-03-03 15:49:21','2023-03-03 15:49:21','',0,''),(23,0,0,'refund_fee','sum(fee)','',NULL,0,0,1,'2023-03-03 15:49:21','2023-03-03 15:49:21','',0,''),(24,0,0,'refund_actual_amount','sum(actual_amount)','',NULL,0,0,1,'2023-03-03 15:49:21','2023-03-03 15:49:21','',0,''),(25,0,0,'refund_order_cnt_rate','${21}/${13}','',NULL,0,0,1,'2023-03-03 15:49:21','2023-03-03 15:57:37','',1,''),(26,0,0,'refund_amount_rate','${22}/${16}','',NULL,0,0,1,'2023-03-03 15:49:21','2023-03-03 15:57:37','',1,''),(27,0,0,'fee_rate','14/16','',NULL,0,0,1,'2023-03-10 15:58:32','2023-03-10 15:58:32','',1,''),(28,0,0,'refund_fee_rate','{23}/{22}','',NULL,0,0,1,'2023-03-11 14:39:23','2023-03-11 14:39:23','',1,''),(54,0,0,'sale_amt','','',NULL,0,0,1,'2023-06-13 15:37:18','2023-06-13 15:37:18','',0,''),(55,0,0,'sale_cnt','','',NULL,0,0,1,'2023-06-13 15:37:18','2023-06-13 15:37:18','',0,''),(56,0,0,'refund_amt','','',NULL,0,0,1,'2023-06-13 15:37:18','2023-06-13 15:37:18','',0,''),(57,0,0,'refund_cnt','','',NULL,0,0,1,'2023-06-13 15:37:18','2023-06-13 15:37:18','',0,''),(58,0,0,'refund_amt_rate','','',NULL,0,0,1,'2023-06-18 04:25:44','2023-06-18 04:25:44','',0,''),(59,0,0,'refund_cnt_rate','','',NULL,0,0,1,'2023-06-18 04:26:59','2023-06-18 04:26:59','',0,''),(60,0,0,'expose_pv','','',NULL,0,0,1,'2023-06-18 04:30:09','2023-06-18 04:30:09','',0,''),(61,0,0,'expose_uv','','',NULL,0,0,1,'2023-06-18 04:30:09','2023-06-18 04:30:09','',0,''),(62,0,0,'click_pv','','',NULL,0,0,1,'2023-06-18 04:30:09','2023-06-18 04:30:09','',0,''),(63,0,0,'click_uv','','',NULL,0,0,1,'2023-06-18 04:30:09','2023-06-18 04:30:09','',0,''),(64,0,0,'view_pv','','',NULL,0,0,1,'2023-06-18 04:30:09','2023-06-18 04:30:09','',0,''),(65,0,0,'view_uv','','',NULL,0,0,1,'2023-06-18 04:30:09','2023-06-18 04:30:09','',0,''),(66,0,0,'visit_buy_rate','','',NULL,0,0,1,'2023-06-18 04:30:09','2023-06-18 04:30:09','',0,''),(67,0,0,'buy_pv','','',NULL,0,0,1,'2023-06-20 17:00:04','2023-06-20 17:00:04','',0,''),(68,0,0,'buy_uv','','',NULL,0,0,1,'2023-06-20 17:00:05','2023-06-20 17:00:05','',0,'');
/*!40000 ALTER TABLE `metric` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-07-22  0:07:26
