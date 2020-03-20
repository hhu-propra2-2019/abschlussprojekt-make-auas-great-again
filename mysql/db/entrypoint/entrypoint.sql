-- MySQL dump 10.13  Distrib 8.0.18, for macos10.14 (x86_64)
--
-- Host: localhost    Database: feedback
-- ------------------------------------------------------
-- Server version	5.7.28

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
-- Table structure for table `antwort`
--

DROP TABLE IF EXISTS `antwort`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `antwort` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `antwort`
--

LOCK TABLES `antwort` WRITE;
/*!40000 ALTER TABLE `antwort` DISABLE KEYS */;
/*!40000 ALTER TABLE `antwort` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dozent`
--

DROP TABLE IF EXISTS `dozent`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dozent` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `vorname` varchar(50) NOT NULL,
  `name` varchar(50) NOT NULL,
  `anrede` varchar(10) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=99 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dozent`
--

LOCK TABLES `dozent` WRITE;
/*!40000 ALTER TABLE `dozent` DISABLE KEYS */;
INSERT INTO `dozent` VALUES (67,'Chris','Rutenkolk','Herr'),(89,'Jens','Bendisposto','Herr'),(98,'Christian','Meter','Herr');
/*!40000 ALTER TABLE `dozent` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dozentOrganisiertVeranstaltung`
--

DROP TABLE IF EXISTS `dozentOrganisiertVeranstaltung`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dozentOrganisiertVeranstaltung` (
  `dozent` bigint(20) unsigned NOT NULL,
  `veranstaltung` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`dozent`,`veranstaltung`),
  KEY `veranstaltung` (`veranstaltung`),
  CONSTRAINT `dozentOrganisiertVeranstaltung_ibfk_1` FOREIGN KEY (`dozent`) REFERENCES `dozent` (`id`),
  CONSTRAINT `dozentOrganisiertVeranstaltung_ibfk_2` FOREIGN KEY (`veranstaltung`) REFERENCES `veranstaltung` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dozentOrganisiertVeranstaltung`
--

LOCK TABLES `dozentOrganisiertVeranstaltung` WRITE;
/*!40000 ALTER TABLE `dozentOrganisiertVeranstaltung` DISABLE KEYS */;
/*!40000 ALTER TABLE `dozentOrganisiertVeranstaltung` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `frage`
--

DROP TABLE IF EXISTS `frage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `frage` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `titel` text NOT NULL,
  `fragebogen` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fragebogen` (`fragebogen`),
  CONSTRAINT `frage_ibfk_1` FOREIGN KEY (`fragebogen`) REFERENCES `fragebogen` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `frage`
--

LOCK TABLES `frage` WRITE;
/*!40000 ALTER TABLE `frage` DISABLE KEYS */;
INSERT INTO `frage` VALUES (56,'Was?',34),(78,'Warum?',34),(96,'Warum nicht?',5),(98,'Wo?',34),(99,'Wieso?',5);
/*!40000 ALTER TABLE `frage` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fragebogen`
--

DROP TABLE IF EXISTS `fragebogen`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fragebogen` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `status` int(11) NOT NULL,
  `startzeit` datetime DEFAULT NULL,
  `endzeit` datetime NOT NULL,
  `veranstaltung` bigint(20) unsigned NOT NULL,
  `einheit` enum('VORLESUNG','UEBUNG','AUFGABE','PRAKTIKUM','DOZENT','BERATUNG','GRUPPE') DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `veranstaltung` (`veranstaltung`),
  CONSTRAINT `fragebogen_ibfk_1` FOREIGN KEY (`veranstaltung`) REFERENCES `veranstaltung` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=88 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fragebogen`
--

LOCK TABLES `fragebogen` WRITE;
/*!40000 ALTER TABLE `fragebogen` DISABLE KEYS */;
INSERT INTO `fragebogen` VALUES (5,'Feedback zur Hardwarenahe Programmierung',0,'2020-01-01 11:00:00','2020-03-01 11:00:00',89,'PRAKTIKUM'),(34,'Fragebogen zum Praktikum',0,'2020-02-02 12:00:00','2020-05-02 12:00:00',78,'PRAKTIKUM'),(87,'Fragebogen zur Vorlesung',0,'2020-03-15 12:00:00','2020-05-15 12:00:00',78,'VORLESUNG');
/*!40000 ALTER TABLE `fragebogen` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `multipleResponseAntwort`
--

DROP TABLE IF EXISTS `multipleResponseAntwort`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `multipleResponseAntwort` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `antwort` int(11) DEFAULT NULL,
  `frage` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `frage` (`frage`),
  CONSTRAINT `multipleResponseAntwort_ibfk_1` FOREIGN KEY (`id`) REFERENCES `antwort` (`id`),
  CONSTRAINT `multipleResponseAntwort_ibfk_2` FOREIGN KEY (`frage`) REFERENCES `multipleResponseFrage` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `multipleResponseAntwort`
--

LOCK TABLES `multipleResponseAntwort` WRITE;
/*!40000 ALTER TABLE `multipleResponseAntwort` DISABLE KEYS */;
/*!40000 ALTER TABLE `multipleResponseAntwort` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `multipleResponseFrage`
--

DROP TABLE IF EXISTS `multipleResponseFrage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `multipleResponseFrage` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`),
  CONSTRAINT `multipleResponseFrage_ibfk_1` FOREIGN KEY (`id`) REFERENCES `frage` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `multipleResponseFrage`
--

LOCK TABLES `multipleResponseFrage` WRITE;
/*!40000 ALTER TABLE `multipleResponseFrage` DISABLE KEYS */;
/*!40000 ALTER TABLE `multipleResponseFrage` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `singleResponseAntwort`
--

DROP TABLE IF EXISTS `singleResponseAntwort`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `singleResponseAntwort` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `antwort` tinyint(1) DEFAULT NULL,
  `frage` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `frage` (`frage`),
  CONSTRAINT `singleResponseAntwort_ibfk_1` FOREIGN KEY (`id`) REFERENCES `antwort` (`id`),
  CONSTRAINT `singleResponseAntwort_ibfk_2` FOREIGN KEY (`frage`) REFERENCES `singleResponseFrage` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `singleResponseAntwort`
--

LOCK TABLES `singleResponseAntwort` WRITE;
/*!40000 ALTER TABLE `singleResponseAntwort` DISABLE KEYS */;
/*!40000 ALTER TABLE `singleResponseAntwort` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `singleResponseFrage`
--

DROP TABLE IF EXISTS `singleResponseFrage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `singleResponseFrage` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`),
  CONSTRAINT `singleResponseFrage_ibfk_1` FOREIGN KEY (`id`) REFERENCES `frage` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `singleResponseFrage`
--

LOCK TABLES `singleResponseFrage` WRITE;
/*!40000 ALTER TABLE `singleResponseFrage` DISABLE KEYS */;
/*!40000 ALTER TABLE `singleResponseFrage` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student`
--

DROP TABLE IF EXISTS `student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `student` (
  `username` varchar(50) NOT NULL,
  `vorname` varchar(50) DEFAULT NULL,
  `nachname` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student`
--

LOCK TABLES `student` WRITE;
/*!40000 ALTER TABLE `student` DISABLE KEYS */;
INSERT INTO `student` VALUES ('ann00','anna','berger'),('chr90','christian','richter'),('student',NULL,NULL),('studentin',NULL,NULL);
/*!40000 ALTER TABLE `student` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `studentBeantwortetFragebogen`
--

DROP TABLE IF EXISTS `studentBeantwortetFragebogen`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `studentBeantwortetFragebogen` (
  `student` varchar(50) NOT NULL,
  `fragebogen` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`student`,`fragebogen`),
  KEY `fragebogen` (`fragebogen`),
  CONSTRAINT `studentBeantwortetFragebogen_ibfk_1` FOREIGN KEY (`student`) REFERENCES `student` (`username`),
  CONSTRAINT `studentBeantwortetFragebogen_ibfk_2` FOREIGN KEY (`fragebogen`) REFERENCES `fragebogen` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `studentBeantwortetFragebogen`
--

LOCK TABLES `studentBeantwortetFragebogen` WRITE;
/*!40000 ALTER TABLE `studentBeantwortetFragebogen` DISABLE KEYS */;
/*!40000 ALTER TABLE `studentBeantwortetFragebogen` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `studentBelegtVeranstaltung`
--

DROP TABLE IF EXISTS `studentBelegtVeranstaltung`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `studentBelegtVeranstaltung` (
  `student` varchar(50) NOT NULL,
  `veranstaltung` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`student`,`veranstaltung`),
  KEY `veranstaltung` (`veranstaltung`),
  CONSTRAINT `studentBelegtVeranstaltung_ibfk_1` FOREIGN KEY (`student`) REFERENCES `student` (`username`),
  CONSTRAINT `studentBelegtVeranstaltung_ibfk_2` FOREIGN KEY (`veranstaltung`) REFERENCES `veranstaltung` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `studentBelegtVeranstaltung`
--

LOCK TABLES `studentBelegtVeranstaltung` WRITE;
/*!40000 ALTER TABLE `studentBelegtVeranstaltung` DISABLE KEYS */;
/*!40000 ALTER TABLE `studentBelegtVeranstaltung` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `textAntwort`
--

DROP TABLE IF EXISTS `textAntwort`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `textAntwort` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `antwort` varchar(100) DEFAULT NULL,
  `frage` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `frage` (`frage`),
  CONSTRAINT `textAntwort_ibfk_1` FOREIGN KEY (`id`) REFERENCES `antwort` (`id`),
  CONSTRAINT `textAntwort_ibfk_2` FOREIGN KEY (`frage`) REFERENCES `textFrage` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `textAntwort`
--

LOCK TABLES `textAntwort` WRITE;
/*!40000 ALTER TABLE `textAntwort` DISABLE KEYS */;
/*!40000 ALTER TABLE `textAntwort` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `textFrage`
--

DROP TABLE IF EXISTS `textFrage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `textFrage` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`),
  CONSTRAINT `textFrage_ibfk_1` FOREIGN KEY (`id`) REFERENCES `frage` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `textFrage`
--

LOCK TABLES `textFrage` WRITE;
/*!40000 ALTER TABLE `textFrage` DISABLE KEYS */;
/*!40000 ALTER TABLE `textFrage` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `veranstaltung`
--

DROP TABLE IF EXISTS `veranstaltung`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `veranstaltung` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `semester` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=99 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `veranstaltung`
--

LOCK TABLES `veranstaltung` WRITE;
/*!40000 ALTER TABLE `veranstaltung` DISABLE KEYS */;
INSERT INTO `veranstaltung` VALUES (78,'Professionelle Softwareentwicklung',4),(87,'Theoretische Informatik',4),(89,'Rechnerarchitektur',2),(98,'Programmierung',1);
/*!40000 ALTER TABLE `veranstaltung` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-03-20 11:58:42
