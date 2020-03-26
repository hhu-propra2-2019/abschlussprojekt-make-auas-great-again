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
  `frage` bigint(20) unsigned NOT NULL,
  `textantwort` text,
  PRIMARY KEY (`id`),
  KEY `frage` (`frage`),
  CONSTRAINT `antwort_ibfk_1` FOREIGN KEY (`frage`) REFERENCES `frage` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `antwort`
--

LOCK TABLES `antwort` WRITE;
/*!40000 ALTER TABLE `antwort` DISABLE KEYS */;
INSERT INTO `antwort` VALUES (1,1,'Vieles war gut'),(2,1,'Manches'),(3,1,'Gar nichts'),(4,2,'Garnichts'),(5,2,'Es hat mir alles gefallen'),(6,3,'Weiß nicht'),(7,3,'Warum nicht?'),(8,4,NULL),(9,4,NULL),(10,4,NULL);
/*!40000 ALTER TABLE `antwort` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `auswahl`
--

DROP TABLE IF EXISTS `auswahl`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `auswahl` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `auswahltext` varchar(100) NOT NULL,
  `frage` bigint(20) unsigned DEFAULT NULL,
  `antwort` bigint(20) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `frage` (`frage`),
  KEY `antwort` (`antwort`),
  CONSTRAINT `auswahl_ibfk_1` FOREIGN KEY (`frage`) REFERENCES `frage` (`id`),
  CONSTRAINT `auswahl_ibfk_2` FOREIGN KEY (`antwort`) REFERENCES `antwort` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `auswahl`
--

LOCK TABLES `auswahl` WRITE;
/*!40000 ALTER TABLE `auswahl` DISABLE KEYS */;
INSERT INTO `auswahl` VALUES (1,'Sehr gut',4,8),(2,'OK',4,9),(3,'Garnicht',4,10);
/*!40000 ALTER TABLE `auswahl` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dozent`
--

DROP TABLE IF EXISTS `dozent`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dozent` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `vorname` varchar(50) NOT NULL,
  `nachname` varchar(50) NOT NULL,
  `anrede` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dozent`
--

LOCK TABLES `dozent` WRITE;
/*!40000 ALTER TABLE `dozent` DISABLE KEYS */;
INSERT INTO `dozent` VALUES (1,'orga1','Jens','Bendisposto','Herr'),(2,'orga2','Christian','Meter','Herr'),(3,'orga3','Chris','Rutenkolk','Herr');
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
INSERT INTO `dozentOrganisiertVeranstaltung` VALUES (1,1),(2,1),(1,2),(3,2),(1,3),(2,3),(3,3),(1,4),(3,4),(1,5),(2,5),(1,6),(3,6),(1,7),(2,7);
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
  `oeffentlich` tinyint(1) NOT NULL,
  `fragebogen` bigint(20) unsigned DEFAULT NULL,
  `fragebogentemplate` bigint(20) unsigned DEFAULT NULL,
  `fragetext` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fragebogen` (`fragebogen`),
  KEY `fragebogentemplate` (`fragebogentemplate`),
  CONSTRAINT `frage_ibfk_1` FOREIGN KEY (`fragebogen`) REFERENCES `fragebogen` (`id`),
  CONSTRAINT `frage_ibfk_2` FOREIGN KEY (`fragebogentemplate`) REFERENCES `fragebogentemplate` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `frage`
--

LOCK TABLES `frage` WRITE;
/*!40000 ALTER TABLE `frage` DISABLE KEYS */;
INSERT INTO `frage` VALUES (1,1,1,NULL,'Was war gut?'),(2,1,1,NULL,'Was war schlecht?'),(3,1,1,NULL,'Warum?'),(4,1,1,NULL,'Bitte ankreuzen wie gut es gefallen hat'),(5,0,NULL,1,'Testfrage');
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
  `startzeit` datetime DEFAULT NULL,
  `endzeit` datetime NOT NULL,
  `veranstaltung` bigint(20) unsigned DEFAULT NULL,
  `einheit` enum('VORLESUNG','UEBUNG','AUFGABE','PRAKTIKUM','DOZENT','BERATUNG','GRUPPE') DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `veranstaltung` (`veranstaltung`),
  CONSTRAINT `fragebogen_ibfk_1` FOREIGN KEY (`veranstaltung`) REFERENCES `veranstaltung` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fragebogen`
--

LOCK TABLES `fragebogen` WRITE;
/*!40000 ALTER TABLE `fragebogen` DISABLE KEYS */;
INSERT INTO `fragebogen` VALUES (1,'Fragebogen zum Praktikum','2020-01-01 12:00:00','2020-05-01 12:00:00',2,'PRAKTIKUM');
/*!40000 ALTER TABLE `fragebogen` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fragebogentemplate`
--

DROP TABLE IF EXISTS `fragebogentemplate`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fragebogentemplate` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `dozent` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `dozent` (`dozent`),
  CONSTRAINT `fragebogentemplate_ibfk_1` FOREIGN KEY (`dozent`) REFERENCES `dozent` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fragebogentemplate`
--

LOCK TABLES `fragebogentemplate` WRITE;
/*!40000 ALTER TABLE `fragebogentemplate` DISABLE KEYS */;
INSERT INTO `fragebogentemplate` VALUES (1,'Vorlage',1);
/*!40000 ALTER TABLE `fragebogentemplate` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student`
--

DROP TABLE IF EXISTS `student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `student` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `vorname` varchar(50) DEFAULT NULL,
  `nachname` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student`
--

LOCK TABLES `student` WRITE;
/*!40000 ALTER TABLE `student` DISABLE KEYS */;
INSERT INTO `student` VALUES (1,'studentin',NULL,NULL),(2,'studentin2',NULL,NULL),(3,'studentin3',NULL,NULL),(4,'studentin1',NULL,NULL);
/*!40000 ALTER TABLE `student` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `studentBeantwortetFragebogen`
--

DROP TABLE IF EXISTS `studentBeantwortetFragebogen`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `studentBeantwortetFragebogen` (
  `student` bigint(20) unsigned NOT NULL,
  `fragebogen` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`student`,`fragebogen`),
  KEY `fragebogen` (`fragebogen`),
  CONSTRAINT `studentBeantwortetFragebogen_ibfk_1` FOREIGN KEY (`student`) REFERENCES `student` (`id`),
  CONSTRAINT `studentBeantwortetFragebogen_ibfk_2` FOREIGN KEY (`fragebogen`) REFERENCES `fragebogen` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `studentBeantwortetFragebogen`
--

LOCK TABLES `studentBeantwortetFragebogen` WRITE;
/*!40000 ALTER TABLE `studentBeantwortetFragebogen` DISABLE KEYS */;
INSERT INTO `studentBeantwortetFragebogen` VALUES (2,1),(3,1);
/*!40000 ALTER TABLE `studentBeantwortetFragebogen` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `studentBelegtVeranstaltung`
--

DROP TABLE IF EXISTS `studentBelegtVeranstaltung`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `studentBelegtVeranstaltung` (
  `student` bigint(20) unsigned NOT NULL,
  `veranstaltung` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`student`,`veranstaltung`),
  KEY `veranstaltung` (`veranstaltung`),
  CONSTRAINT `studentBelegtVeranstaltung_ibfk_1` FOREIGN KEY (`student`) REFERENCES `student` (`id`),
  CONSTRAINT `studentBelegtVeranstaltung_ibfk_2` FOREIGN KEY (`veranstaltung`) REFERENCES `veranstaltung` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `studentBelegtVeranstaltung`
--

LOCK TABLES `studentBelegtVeranstaltung` WRITE;
/*!40000 ALTER TABLE `studentBelegtVeranstaltung` DISABLE KEYS */;
INSERT INTO `studentBelegtVeranstaltung` VALUES (1,1),(2,1),(3,1),(4,1),(1,2),(2,2),(3,2),(1,3),(2,3),(1,4),(3,4),(1,5),(1,6),(1,7),(2,7),(3,7);
/*!40000 ALTER TABLE `studentBelegtVeranstaltung` ENABLE KEYS */;
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
  `semester` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `veranstaltung`
--

LOCK TABLES `veranstaltung` WRITE;
/*!40000 ALTER TABLE `veranstaltung` DISABLE KEYS */;
INSERT INTO `veranstaltung` VALUES (1,'Professionelle Softwareentwicklung','SoSe2020'),(2,'Softwareentwicklung im Team','WS2019'),(3,'Programmierung','WS2020'),(4,'Algorithmen & Datenstrukturen','WS2020'),(5,'Rechnernetze','SoSe2020'),(6,'Datenbanksysteme','WS2019'),(7,'Rechnerarchitektur','WS2018');
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

-- Dump completed on 2020-03-26  9:59:20
