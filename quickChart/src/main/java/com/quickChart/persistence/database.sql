CREATE DATABASE  IF NOT EXISTS `stats_db` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `stats_db`;
-- MySQL dump 10.13  Distrib 8.0.22, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: stats_db
-- ------------------------------------------------------
-- Server version	8.0.22

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
-- Table structure for table `background_colors`
--

DROP TABLE IF EXISTS `background_colors`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `background_colors` (
                                     `color_id` int NOT NULL AUTO_INCREMENT,
                                     `color` varchar(45) DEFAULT NULL,
                                     `dataset_id` int DEFAULT NULL,
                                     PRIMARY KEY (`color_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `charts`
--

DROP TABLE IF EXISTS `charts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `charts` (
                          `chart_id` int NOT NULL AUTO_INCREMENT,
                          `chart_title` varchar(45) DEFAULT NULL,
                          `chart_url` varchar(512) DEFAULT NULL,
                          `type` varchar(45) DEFAULT NULL,
                          `width` int DEFAULT NULL,
                          `height` int DEFAULT NULL,
                          `backgroundColor` varchar(45) DEFAULT NULL,
                          `user_id` int DEFAULT NULL,
                          PRIMARY KEY (`chart_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `dataset_data`
--

DROP TABLE IF EXISTS `dataset_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dataset_data` (
                                `value_id` int NOT NULL AUTO_INCREMENT,
                                `value` int DEFAULT NULL,
                                `dataset_id` int DEFAULT NULL,
                                PRIMARY KEY (`value_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `datasets`
--

DROP TABLE IF EXISTS `datasets`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `datasets` (
                            `dataset_id` int NOT NULL AUTO_INCREMENT,
                            `chart_id` int DEFAULT NULL,
                            `label` varchar(45) DEFAULT NULL,
                            `chart_type` varchar(45) DEFAULT NULL,
                            `border_color` varchar(45) DEFAULT NULL,
                            `background_color` varchar(45) DEFAULT NULL,
                            `border_width` int DEFAULT NULL,
                            `fill` varchar(45) DEFAULT NULL,
                            `pointRadius` int DEFAULT NULL,
                            `showLine` varchar(45) DEFAULT NULL,
                            PRIMARY KEY (`dataset_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `labels`
--

DROP TABLE IF EXISTS `labels`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `labels` (
                          `label_id` int NOT NULL AUTO_INCREMENT,
                          `title` varchar(45) DEFAULT NULL,
                          `chart_id` int DEFAULT NULL,
                          PRIMARY KEY (`label_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
                         `user_id` int NOT NULL AUTO_INCREMENT,
                         `first_name` varchar(45) DEFAULT NULL,
                         `last_name` varchar(45) DEFAULT NULL,
                         `password` varchar(110) DEFAULT NULL,
                         `token` varchar(45) DEFAULT NULL,
                         PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'feb','fran','$2a$10$V/jCrwE4DNuciL9CS8uue.kH5Dls6aKWOH1Wu7f./hYmPeE53SrWm','1234');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-04-19 16:52:02
