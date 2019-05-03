start transaction;

create database `Acme-Rookies`;

use `Acme-Rookies`;

create user 'acme-user'@'%' 
	identified by password '*4F10007AADA9EE3DBB2CC36575DFC6F4FDE27577';

create user 'acme-manager'@'%' 
	identified by password '*FDB8CD304EB2317D10C95D797A4BD7492560F55F';

grant select, insert, update, delete 
	on `Acme-Rookies`.* to 'acme-user'@'%';

grant select, insert, update, delete, create, drop, references, index, alter, 
        create temporary tables, lock tables, create view, create routine, 
        alter routine, execute, trigger, show view
    on `Acme-Rookies`.* to 'acme-manager'@'%';

-- MySQL dump 10.13  Distrib 5.5.29, for Win64 (x86)
--
-- Host: localhost    Database: Acme-Rookies
-- ------------------------------------------------------
-- Server version	5.5.29

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `administrator`
--

DROP TABLE IF EXISTS `administrator`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `administrator` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `vatnumber` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `cvv_code` int(11) NOT NULL,
  `expiration_month` varchar(255) DEFAULT NULL,
  `expiration_year` varchar(255) DEFAULT NULL,
  `holder` varchar(255) DEFAULT NULL,
  `make` varchar(255) DEFAULT NULL,
  `number` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `is_spammer` bit(1) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `user_account` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_7ohwsa2usmvu0yxb44je2lge` (`user_account`),
  UNIQUE KEY `UK_jj3mmcc0vjobqibj67dvuwihk` (`email`),
  CONSTRAINT `FK_7ohwsa2usmvu0yxb44je2lge` FOREIGN KEY (`user_account`) REFERENCES `user_account` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `administrator`
--

LOCK TABLES `administrator` WRITE;
/*!40000 ALTER TABLE `administrator` DISABLE KEYS */;
INSERT INTO `administrator` VALUES (6915,0,65,'Calle Admin 1',724,'08','21','holder1','VISA','38353348140483','admin1@gmail.com',NULL,'Admin1','63018754','http://www.littlehearts.ind.in/wp-content/themes/lhs/Birthday/images/adm.png','Ruiz',6887),(6916,0,97,'Calle Admin 2',587,'10','22','holder2','VISA','4716895067094219','admin2@gmail.com',NULL,'System','63015521','https://image.freepik.com/free-photo/linux-kubuntu-operating-logo-system-options_121-97849.jpg','Reina',6888);
/*!40000 ALTER TABLE `administrator` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `answer`
--

DROP TABLE IF EXISTS `answer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `answer` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `code_link` varchar(255) DEFAULT NULL,
  `text` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `answer`
--

LOCK TABLES `answer` WRITE;
/*!40000 ALTER TABLE `answer` DISABLE KEYS */;
INSERT INTO `answer` VALUES (6917,0,'https://www.code.com','Answer1, Application1'),(6918,0,'https://www.code.com','Answer2, Application2'),(6919,0,'https://www.code.com','Answer4, Application4'),(6920,0,'https://www.code.com','Answer5, Application5'),(6921,0,'https://www.code.com','Answer6, Application6'),(6922,0,'https://www.code.com','Answer7, Application7'),(6923,0,'https://www.code.com','Answer9, Application9'),(6924,0,'https://www.code.com','Answer10, Application10'),(6925,0,'https://www.code.com','Answer11, Application11'),(6926,0,'https://www.code.com','Answer12, Application12'),(6927,0,'https://www.code.com','Answer14, Application14');
/*!40000 ALTER TABLE `answer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `application`
--

DROP TABLE IF EXISTS `application`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `application` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `application_moment` datetime DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `submitted_moment` datetime DEFAULT NULL,
  `answer` int(11) DEFAULT NULL,
  `curriculum` int(11) NOT NULL,
  `position` int(11) NOT NULL,
  `problem` int(11) NOT NULL,
  `rookie` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_sjing1smhndaor3vyjfk4hsn2` (`curriculum`),
  UNIQUE KEY `UK_67lpdm77p0ia8oehbe0j4re24` (`rookie`,`position`),
  KEY `UK_oewbcxg4om4a1qgnqbyyqi4bm` (`rookie`,`status`),
  KEY `UK_m10tgfnkn56kq5i6mrhpahm0g` (`position`,`status`),
  KEY `UK_27c4kx8mbp4u274o66goerrc3` (`problem`,`rookie`),
  KEY `FK_lvkbgejfw0xfg2m80h1bjjmau` (`answer`),
  CONSTRAINT `FK_dq1om37bx4hgli24rbkjo2n7` FOREIGN KEY (`rookie`) REFERENCES `rookie` (`id`),
  CONSTRAINT `FK_7gn6fojv7rim6rxyglc6n9mt2` FOREIGN KEY (`problem`) REFERENCES `problem` (`id`),
  CONSTRAINT `FK_cqpb54jon3yjef814oj6g6o4n` FOREIGN KEY (`position`) REFERENCES `position` (`id`),
  CONSTRAINT `FK_lvkbgejfw0xfg2m80h1bjjmau` FOREIGN KEY (`answer`) REFERENCES `answer` (`id`),
  CONSTRAINT `FK_sjing1smhndaor3vyjfk4hsn2` FOREIGN KEY (`curriculum`) REFERENCES `curriculum` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `application`
--

LOCK TABLES `application` WRITE;
/*!40000 ALTER TABLE `application` DISABLE KEYS */;
INSERT INTO `application` VALUES (7141,0,'2019-01-01 10:10:00','ACCEPTED','2019-02-01 10:10:00',6917,7096,7026,7054,6952),(7142,0,'2019-01-01 10:10:00','ACCEPTED','2019-02-01 10:10:00',6918,7099,7026,7054,6953),(7143,0,'2019-01-01 10:10:00','PENDING',NULL,NULL,7103,7026,7055,6954),(7144,0,'2019-01-01 10:10:00','SUBMITTED','2019-02-01 10:10:00',6919,7113,7027,7054,6957),(7145,0,'2019-01-01 10:10:00','REJECTED','2019-02-01 10:10:00',6920,7097,7027,7056,6952),(7146,0,'2019-01-01 10:10:00','REJECTED','2019-02-01 10:10:00',6921,7106,7031,7058,6955),(7147,0,'2019-01-01 10:10:00','SUBMITTED','2019-02-01 10:10:00',6922,7110,7031,7062,6956),(7148,0,'2019-01-01 10:10:00','PENDING',NULL,NULL,7100,7032,7059,6953),(7149,0,'2019-01-01 10:10:00','REJECTED','2019-02-01 10:10:00',6923,7111,7032,7060,6956),(7150,0,'2019-01-01 10:10:00','ACCEPTED','2019-02-01 10:10:00',6924,7115,7032,7061,6958),(7151,0,'2019-01-01 10:10:00','REJECTED','2019-02-01 10:10:00',6925,7101,7033,7065,6953),(7152,0,'2019-01-01 10:10:00','SUBMITTED','2019-02-01 10:10:00',6926,7107,7033,7066,6955),(7153,0,'2019-01-01 10:10:00','PENDING',NULL,NULL,7108,7034,7066,6955),(7154,0,'2019-01-01 10:10:00','ACCEPTED','2019-02-01 10:10:00',6927,7104,7034,7066,6954);
/*!40000 ALTER TABLE `application` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `audit`
--

DROP TABLE IF EXISTS `audit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `audit` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `final_mode` bit(1) NOT NULL,
  `score` int(11) NOT NULL,
  `text` varchar(255) DEFAULT NULL,
  `written_moment` datetime DEFAULT NULL,
  `auditor` int(11) NOT NULL,
  `position` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_sdojnckc3yy7n0fk3avxbfbag` (`auditor`,`position`),
  KEY `UK_dcabtqn528wn120ycgluk0sg4` (`position`,`final_mode`),
  CONSTRAINT `FK_bumsxo4hc038y25pbfsinc38u` FOREIGN KEY (`position`) REFERENCES `position` (`id`),
  CONSTRAINT `FK_3m6p53wfvy7kcl4f4fvphkh9h` FOREIGN KEY (`auditor`) REFERENCES `auditor` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `audit`
--

LOCK TABLES `audit` WRITE;
/*!40000 ALTER TABLE `audit` DISABLE KEYS */;
INSERT INTO `audit` VALUES (7087,0,'',7,'Text audit 1','2019-01-01 10:10:00',6928,7026),(7088,0,'',5,'Text audit 2','2017-01-01 10:10:00',6929,7034),(7089,0,'\0',8,'Text audit 3','2018-01-01 10:10:00',6930,7031),(7090,0,'',4,'Text audit 4','2019-01-01 10:10:00',6930,7026),(7091,0,'',1,'Text audit 5','2017-01-01 10:10:00',6931,7027),(7092,0,'\0',10,'Text audit 6','2018-01-01 10:10:00',6933,7031),(7093,0,'',9,'Text audit 7','2019-01-01 10:10:00',6934,7032),(7094,0,'',6,'Text audit 8','2017-01-01 10:10:00',6934,7033);
/*!40000 ALTER TABLE `audit` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `auditor`
--

DROP TABLE IF EXISTS `auditor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `auditor` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `vatnumber` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `cvv_code` int(11) NOT NULL,
  `expiration_month` varchar(255) DEFAULT NULL,
  `expiration_year` varchar(255) DEFAULT NULL,
  `holder` varchar(255) DEFAULT NULL,
  `make` varchar(255) DEFAULT NULL,
  `number` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `is_spammer` bit(1) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `user_account` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_1hfceldjralkadedlv9lg1tl8` (`user_account`),
  UNIQUE KEY `UK_lmcp5r2bol31t270dvfqypbmk` (`email`),
  CONSTRAINT `FK_1hfceldjralkadedlv9lg1tl8` FOREIGN KEY (`user_account`) REFERENCES `user_account` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `auditor`
--

LOCK TABLES `auditor` WRITE;
/*!40000 ALTER TABLE `auditor` DISABLE KEYS */;
INSERT INTO `auditor` VALUES (6928,0,97,'Calle Auditor 1',950,'04','21','holder15','MCARD','4382235426482099','auditor1@gmail.com',NULL,'Auditor1','63015471','http://webfeb.in/wp-content/uploads/2016/11/logo-design-for-it-company.jpg','Garcia',6901),(6929,0,97,'Calle Auditor 2',124,'03','22','holder16','MCARD','4262922023905342','auditor2@gmail.com',NULL,'Auditor2','63012521','http://webfeb.in/wp-content/uploads/2016/11/logo-design-for-it-company.jpg','Garcia',6902),(6930,0,97,'Calle Auditor 3',180,'04','21','holder17','MCARD','340422775030815','auditor3@gmail.com',NULL,'Auditor3','63015471','http://webfeb.in/wp-content/uploads/2016/11/logo-design-for-it-company.jpg','Garcia',6903),(6931,0,97,'Calle Auditor 4',613,'07','24','holder18','MCARD','3559081929041845','auditor4@gmail.com',NULL,'Auditor4','63012521','http://webfeb.in/wp-content/uploads/2016/11/logo-design-for-it-company.jpg','Garcia',6904),(6932,0,97,'Calle Auditor 5',613,'07','24','holder19','MCARD','5772487597524838','auditor5@gmail.com',NULL,'Auditor5','63015471','http://webfeb.in/wp-content/uploads/2016/11/logo-design-for-it-company.jpg','Garcia',6905),(6933,0,97,'Calle Auditor 6',950,'04','21','holder20','MCARD','4382235426482099','auditor6@gmail.com',NULL,'Auditor6','63012521','http://webfeb.in/wp-content/uploads/2016/11/logo-design-for-it-company.jpg','Garcia',6906),(6934,0,97,'Calle Auditor 7',124,'03','22','holder21','MCARD','4057772385714058','auditor7@gmail.com',NULL,'Auditor1','63015471','http://webfeb.in/wp-content/uploads/2016/11/logo-design-for-it-company.jpg','Garcia',6907);
/*!40000 ALTER TABLE `auditor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `company`
--

DROP TABLE IF EXISTS `company`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `company` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `vatnumber` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `cvv_code` int(11) NOT NULL,
  `expiration_month` varchar(255) DEFAULT NULL,
  `expiration_year` varchar(255) DEFAULT NULL,
  `holder` varchar(255) DEFAULT NULL,
  `make` varchar(255) DEFAULT NULL,
  `number` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `is_spammer` bit(1) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `user_account` int(11) NOT NULL,
  `audit_score` double DEFAULT NULL,
  `commercial_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_pno7oguspp7fxv0y2twgplt0s` (`user_account`),
  UNIQUE KEY `UK_bma9lv19ba3yjwf12a34xord3` (`email`),
  UNIQUE KEY `UK_4askypslrvhwn9noojdiojojc` (`commercial_name`),
  KEY `UK_2meev7ta9tqxd649vg75xlss8` (`audit_score`),
  CONSTRAINT `FK_pno7oguspp7fxv0y2twgplt0s` FOREIGN KEY (`user_account`) REFERENCES `user_account` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `company`
--

LOCK TABLES `company` WRITE;
/*!40000 ALTER TABLE `company` DISABLE KEYS */;
INSERT INTO `company` VALUES (6935,0,97,'Calle Company 1',587,'03','22','holder3','VISA','4389142361978458','company1@gmail.com',NULL,'Company1','63015521','http://webfeb.in/wp-content/uploads/2016/11/logo-design-for-it-company.jpg','Garcia',6889,0.4,'One Commercial Name'),(6936,0,97,'Calle Company 2',147,'02','21','holder4','VISA','4916210851536995','company2@gmail.com',NULL,'Company2','63015521','https://www.freelogodesign.org/Content/img/logo-ex-6.png','Lobato',6890,NULL,'Two Commercial Name'),(6937,0,97,'Calle Company 3',258,'01','20','holder5','MCARD','4539294619605521','company3@gmail.com',NULL,'Company3','63015521','https://www.freelogodesign.org/Content/img/logo-ex-5.png','Perez',6891,0.55,'Three Commercial Name');
/*!40000 ALTER TABLE `company` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `curriculum`
--

DROP TABLE IF EXISTS `curriculum`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `curriculum` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `is_original` bit(1) NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  `personal_data` int(11) NOT NULL,
  `rookie` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_14ypx9rpvo8410mt1jmkx1wyw` (`personal_data`),
  KEY `UK_67te1vg81wgqwqhf5nseb3aip` (`is_original`,`rookie`),
  KEY `FK_ocbjn8fkeauy3dpywaikgw4vs` (`rookie`),
  CONSTRAINT `FK_ocbjn8fkeauy3dpywaikgw4vs` FOREIGN KEY (`rookie`) REFERENCES `rookie` (`id`),
  CONSTRAINT `FK_14ypx9rpvo8410mt1jmkx1wyw` FOREIGN KEY (`personal_data`) REFERENCES `personal_data` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `curriculum`
--

LOCK TABLES `curriculum` WRITE;
/*!40000 ALTER TABLE `curriculum` DISABLE KEYS */;
INSERT INTO `curriculum` VALUES (7095,0,'','Curriculum del Rookie 1',7004,6952),(7096,0,'\0','Copia de Curriculum del Rookie 1',7005,6952),(7097,0,'\0','Copia de Curriculum del Rookie 1',7006,6952),(7098,0,'','Curriculum del Rookie 2',7007,6953),(7099,0,'\0','Copia de Curriculum del Rookie 2',7008,6953),(7100,0,'\0','Copia de Curriculum del Rookie 2',7012,6953),(7101,0,'\0','Copia de Curriculum del Rookie 2',7020,6957),(7102,0,'','Curriculum del Rookie 3',7009,6954),(7103,0,'\0','Copia de Curriculum del Rookie 3',7010,6954),(7104,0,'\0','Copia de Curriculum del Rookie 3',7011,6954),(7105,0,'','Curriculum del Rookie 4',7013,6955),(7106,0,'\0','Copia de Curriculum del Rookie 4',7014,6955),(7107,0,'\0','Copia de Curriculum del Rookie 4',7015,6955),(7108,0,'\0','Copia de Curriculum del Rookie 4',7023,6958),(7109,0,'','Curriculum del Rookie 5',7016,6956),(7110,0,'\0','Copia de Curriculum del Rookie 5',7017,6956),(7111,0,'\0','Copia de Curriculum del Rookie 5',7024,6958),(7112,0,'','Curriculum del Rookie 6',7018,6957),(7113,0,'\0','Copia de Curriculum del Rookie 6',7019,6957),(7114,0,'','Curriculum del Rookie 7',7021,6958),(7115,0,'\0','Copia de Curriculum del Rookie 7',7022,6958),(7116,0,'','Curriculum del Rookie 8',7025,6959);
/*!40000 ALTER TABLE `curriculum` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `curriculum_education_datas`
--

DROP TABLE IF EXISTS `curriculum_education_datas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `curriculum_education_datas` (
  `curriculum` int(11) NOT NULL,
  `education_datas` int(11) NOT NULL,
  UNIQUE KEY `UK_h03suk6hhcmp350vmwcs9m7k3` (`education_datas`),
  KEY `FK_o783xr31yi8vovi9q0fxe3pm2` (`curriculum`),
  CONSTRAINT `FK_o783xr31yi8vovi9q0fxe3pm2` FOREIGN KEY (`curriculum`) REFERENCES `curriculum` (`id`),
  CONSTRAINT `FK_h03suk6hhcmp350vmwcs9m7k3` FOREIGN KEY (`education_datas`) REFERENCES `education_data` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `curriculum_education_datas`
--

LOCK TABLES `curriculum_education_datas` WRITE;
/*!40000 ALTER TABLE `curriculum_education_datas` DISABLE KEYS */;
INSERT INTO `curriculum_education_datas` VALUES (7095,6939),(7096,6940),(7096,6941),(7097,6942),(7097,6943),(7105,6944),(7106,6945),(7107,6946),(7109,6947),(7109,6948),(7110,6949),(7110,6950),(7116,6951);
/*!40000 ALTER TABLE `curriculum_education_datas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `curriculum_miscellaneous_datas`
--

DROP TABLE IF EXISTS `curriculum_miscellaneous_datas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `curriculum_miscellaneous_datas` (
  `curriculum` int(11) NOT NULL,
  `miscellaneous_datas` int(11) NOT NULL,
  UNIQUE KEY `UK_pn9k3t3bcipwqxkyraju77p47` (`miscellaneous_datas`),
  KEY `FK_asudhgax0bfxmqjshst691ml` (`curriculum`),
  CONSTRAINT `FK_asudhgax0bfxmqjshst691ml` FOREIGN KEY (`curriculum`) REFERENCES `curriculum` (`id`),
  CONSTRAINT `FK_pn9k3t3bcipwqxkyraju77p47` FOREIGN KEY (`miscellaneous_datas`) REFERENCES `miscellaneous_data` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `curriculum_miscellaneous_datas`
--

LOCK TABLES `curriculum_miscellaneous_datas` WRITE;
/*!40000 ALTER TABLE `curriculum_miscellaneous_datas` DISABLE KEYS */;
INSERT INTO `curriculum_miscellaneous_datas` VALUES (7095,6967),(7095,6968),(7096,6969),(7097,6970),(7098,6971),(7098,6972),(7099,6973),(7099,6974),(7100,6978),(7101,6985),(7101,6986),(7102,6975),(7103,6976),(7104,6977),(7108,6989),(7109,6979),(7110,6980),(7111,6990),(7112,6981),(7112,6982),(7113,6983),(7113,6984),(7114,6987),(7115,6988);
/*!40000 ALTER TABLE `curriculum_miscellaneous_datas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `curriculum_position_datas`
--

DROP TABLE IF EXISTS `curriculum_position_datas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `curriculum_position_datas` (
  `curriculum` int(11) NOT NULL,
  `position_datas` int(11) NOT NULL,
  UNIQUE KEY `UK_imvuypdut7j9qj727srfqihuh` (`position_datas`),
  KEY `FK_9m5vouqexttm1x4o5r5o1u0f8` (`curriculum`),
  CONSTRAINT `FK_9m5vouqexttm1x4o5r5o1u0f8` FOREIGN KEY (`curriculum`) REFERENCES `curriculum` (`id`),
  CONSTRAINT `FK_imvuypdut7j9qj727srfqihuh` FOREIGN KEY (`position_datas`) REFERENCES `position_data` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `curriculum_position_datas`
--

LOCK TABLES `curriculum_position_datas` WRITE;
/*!40000 ALTER TABLE `curriculum_position_datas` DISABLE KEYS */;
INSERT INTO `curriculum_position_datas` VALUES (7095,7035),(7095,7036),(7098,7037),(7099,7038),(7100,7042),(7101,7048),(7102,7039),(7103,7040),(7104,7041),(7105,7043),(7106,7044),(7107,7045),(7108,7051),(7111,7052),(7112,7046),(7113,7047),(7114,7049),(7115,7050),(7116,7053);
/*!40000 ALTER TABLE `curriculum_position_datas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customisation`
--

DROP TABLE IF EXISTS `customisation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customisation` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `vattax` int(11) NOT NULL,
  `banner` varchar(255) DEFAULT NULL,
  `country_code` varchar(255) DEFAULT NULL,
  `frate` double NOT NULL,
  `is_rebrand_notification_sent` bit(1) NOT NULL,
  `max_number_results` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `spam_words` varchar(255) DEFAULT NULL,
  `time_cached_results` int(11) NOT NULL,
  `welcome_message_en` varchar(255) DEFAULT NULL,
  `welcome_message_es` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customisation`
--

LOCK TABLES `customisation` WRITE;
/*!40000 ALTER TABLE `customisation` DISABLE KEYS */;
INSERT INTO `customisation` VALUES (6938,0,47,'https://i.ibb.co/LQXvBrh/Copia-de-I-m-Back.png','+34',500,'\0',10,'Acme Rookie','sex,viagra,cialis,one million,you\'ve been selected,Nigeria,sexo,un millon,ha sido seleccionado',1,'Welcome to Acme Rookie! We\'re IT rookie\'s favourite job marketplace!','¡Bienvenidos a Acme Rookie! ¡Somos el mercado de trabajo favorito de los profesionales de las TICs!');
/*!40000 ALTER TABLE `customisation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `education_data`
--

DROP TABLE IF EXISTS `education_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `education_data` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `degree` varchar(255) DEFAULT NULL,
  `end_date` date DEFAULT NULL,
  `institution` varchar(255) DEFAULT NULL,
  `mark` double NOT NULL,
  `start_date` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `education_data`
--

LOCK TABLES `education_data` WRITE;
/*!40000 ALTER TABLE `education_data` DISABLE KEYS */;
INSERT INTO `education_data` VALUES (6939,0,'Ingeniería Informática del Software',NULL,'Universidad de Sevilla',6.2,'2014-10-20'),(6940,0,'Ingeniería Informática del Software','2016-09-25','Universidad de Málaga',8.6,'2010-10-11'),(6941,0,'Ingeniería Informática','2018-05-20','Universidad de Sevilla',7.5,'2014-10-12'),(6942,0,'Ingeniería Informática','2018-05-20','Universidad de Sevilla',7.5,'2014-10-12'),(6943,0,'Ingeniería Informática','2018-05-20','Universidad de Sevilla',7.5,'2014-10-12'),(6944,0,'Ingeniería Informática Tecnologías','2014-07-24','Universidad de Sevilla',5.6,'2010-10-20'),(6945,0,'Ingeniería Informática Tecnologías','2015-07-01','Universidad de Sevilla',8.6,'2011-10-02'),(6946,0,'Ingeniería Informática Tecnologías','2015-07-01','Universidad de Sevilla',8.6,'2011-10-02'),(6947,0,'Ingeniería Informática Tecnologías','2014-07-24','Universidad de Sevilla',7.6,'2010-10-20'),(6948,0,'Ingeniería Informática Tecnologías','2017-07-01','Universidad de Sevilla',9.6,'2012-10-02'),(6949,0,'Ingeniería Informática Tecnologías','2014-07-24','Universidad de Sevilla',7.6,'2010-10-20'),(6950,0,'Ingeniería Informática Tecnologías','2017-07-01','Universidad de Sevilla',9.6,'2012-10-02'),(6951,0,'Ingeniería Informática Tecnologías','2017-07-01','Universidad de Sevilla',6.3,'2012-10-02');
/*!40000 ALTER TABLE `education_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `finder`
--

DROP TABLE IF EXISTS `finder`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `finder` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `deadline` date DEFAULT NULL,
  `keyword` varchar(255) DEFAULT NULL,
  `maximum_deadline` date DEFAULT NULL,
  `minimum_salary` double DEFAULT NULL,
  `updated_moment` datetime DEFAULT NULL,
  `rookie` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_71xsphsa6mnvd5ul0ow71r6we` (`rookie`),
  KEY `UK_n08k0vrypppbsqf2gwmtk7y8o` (`keyword`,`deadline`,`minimum_salary`,`maximum_deadline`),
  CONSTRAINT `FK_71xsphsa6mnvd5ul0ow71r6we` FOREIGN KEY (`rookie`) REFERENCES `rookie` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `finder`
--

LOCK TABLES `finder` WRITE;
/*!40000 ALTER TABLE `finder` DISABLE KEYS */;
INSERT INTO `finder` VALUES (7117,0,'2020-10-05','estoEsUnaPruebaParaQueNoMeDevuelvaNada','2022-10-05',965.24,'2019-01-01 10:10:00',6952),(7118,0,NULL,'',NULL,NULL,'2019-01-01 10:10:00',6953),(7119,0,'2020-10-05','','2022-10-05',7965.24,'2019-01-01 10:10:00',6954),(7120,0,NULL,'position4',NULL,NULL,'2019-01-01 10:10:00',6955),(7121,0,NULL,'position3',NULL,NULL,'2019-01-01 10:10:00',6956),(7122,0,NULL,'company2',NULL,NULL,'2019-01-01 10:10:00',6957),(7123,0,'2020-10-05','NoQuieroNada','2022-10-05',965.24,'2019-01-01 10:10:00',6958),(7124,0,NULL,'Company',NULL,NULL,'2019-01-01 10:10:00',6959),(7125,0,NULL,'',NULL,NULL,'2019-01-01 10:15:00',6960);
/*!40000 ALTER TABLE `finder` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `finder_positions`
--

DROP TABLE IF EXISTS `finder_positions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `finder_positions` (
  `finder` int(11) NOT NULL,
  `positions` int(11) NOT NULL,
  KEY `FK_3d46gil0nks2dhgg7cyhv2m39` (`positions`),
  KEY `FK_l0b3qg4nly59oeqhe8ig5yssc` (`finder`),
  CONSTRAINT `FK_l0b3qg4nly59oeqhe8ig5yssc` FOREIGN KEY (`finder`) REFERENCES `finder` (`id`),
  CONSTRAINT `FK_3d46gil0nks2dhgg7cyhv2m39` FOREIGN KEY (`positions`) REFERENCES `position` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `finder_positions`
--

LOCK TABLES `finder_positions` WRITE;
/*!40000 ALTER TABLE `finder_positions` DISABLE KEYS */;
INSERT INTO `finder_positions` VALUES (7118,7026),(7118,7027),(7118,7029),(7120,7029),(7122,7027),(7124,7026),(7124,7027),(7124,7029),(7125,7026),(7125,7027),(7125,7029);
/*!40000 ALTER TABLE `finder_positions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hibernate_sequences`
--

DROP TABLE IF EXISTS `hibernate_sequences`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hibernate_sequences` (
  `sequence_name` varchar(255) DEFAULT NULL,
  `sequence_next_hi_value` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequences`
--

LOCK TABLES `hibernate_sequences` WRITE;
/*!40000 ALTER TABLE `hibernate_sequences` DISABLE KEYS */;
INSERT INTO `hibernate_sequences` VALUES ('domain_entity',1);
/*!40000 ALTER TABLE `hibernate_sequences` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `item`
--

DROP TABLE IF EXISTS `item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `item` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `link` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `picture` longtext,
  `provider` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_isojc9iaj7goju6s26847atbn` (`provider`),
  CONSTRAINT `FK_isojc9iaj7goju6s26847atbn` FOREIGN KEY (`provider`) REFERENCES `provider` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item`
--

LOCK TABLES `item` WRITE;
/*!40000 ALTER TABLE `item` DISABLE KEYS */;
INSERT INTO `item` VALUES (7126,0,'descripcion item1','https://www.madmen-onlinemarketing.de/wp-content/uploads/jan-madmen-onlinemarketing-e1525337078609.jpg','Item1','https://www.madmen-onlinemarketing.de/wp-content/uploads/jan-madmen-onlinemarketing-e1525337078609.jpg',7072),(7127,0,'descripcion item2','https://www.madmen-onlinemarketing.de/wp-content/uploads/jan-madmen-onlinemarketing-e1525337078609.jpg','Item2','https://www.madmen-onlinemarketing.de/wp-content/uploads/jan-madmen-onlinemarketing-e1525337078609.jpg',7072),(7128,0,'descripcion item3','https://www.madmen-onlinemarketing.de/wp-content/uploads/jan-madmen-onlinemarketing-e1525337078609.jpg','Item3','https://www.madmen-onlinemarketing.de/wp-content/uploads/jan-madmen-onlinemarketing-e1525337078609.jpg',7072),(7129,0,'descripcion item4','https://www.madmen-onlinemarketing.de/wp-content/uploads/jan-madmen-onlinemarketing-e1525337078609.jpg','Item4','https://www.madmen-onlinemarketing.de/wp-content/uploads/jan-madmen-onlinemarketing-e1525337078609.jpg',7073),(7130,0,'descripcion item5','https://www.madmen-onlinemarketing.de/wp-content/uploads/jan-madmen-onlinemarketing-e1525337078609.jpg','Item5','https://www.madmen-onlinemarketing.de/wp-content/uploads/jan-madmen-onlinemarketing-e1525337078609.jpg',7073),(7131,0,'descripcion item6','https://www.madmen-onlinemarketing.de/wp-content/uploads/jan-madmen-onlinemarketing-e1525337078609.jpg','Item6','https://www.madmen-onlinemarketing.de/wp-content/uploads/jan-madmen-onlinemarketing-e1525337078609.jpg',7074),(7132,0,'descripcion item7','https://www.madmen-onlinemarketing.de/wp-content/uploads/jan-madmen-onlinemarketing-e1525337078609.jpg','Item7','https://www.madmen-onlinemarketing.de/wp-content/uploads/jan-madmen-onlinemarketing-e1525337078609.jpg',7075),(7133,0,'descripcion item8','https://www.madmen-onlinemarketing.de/wp-content/uploads/jan-madmen-onlinemarketing-e1525337078609.jpg','Item8','https://www.madmen-onlinemarketing.de/wp-content/uploads/jan-madmen-onlinemarketing-e1525337078609.jpg',7076),(7134,0,'descripcion item9','https://www.madmen-onlinemarketing.de/wp-content/uploads/jan-madmen-onlinemarketing-e1525337078609.jpg','Item9','https://www.madmen-onlinemarketing.de/wp-content/uploads/jan-madmen-onlinemarketing-e1525337078609.jpg',7076),(7135,0,'descripcion item10','https://www.madmen-onlinemarketing.de/wp-content/uploads/jan-madmen-onlinemarketing-e1525337078609.jpg','Item10','https://www.madmen-onlinemarketing.de/wp-content/uploads/jan-madmen-onlinemarketing-e1525337078609.jpg',7076),(7136,0,'descripcion item11','https://www.madmen-onlinemarketing.de/wp-content/uploads/jan-madmen-onlinemarketing-e1525337078609.jpg','Item11','https://www.madmen-onlinemarketing.de/wp-content/uploads/jan-madmen-onlinemarketing-e1525337078609.jpg',7076),(7137,0,'descripcion item12','https://www.madmen-onlinemarketing.de/wp-content/uploads/jan-madmen-onlinemarketing-e1525337078609.jpg','Item12','https://www.madmen-onlinemarketing.de/wp-content/uploads/jan-madmen-onlinemarketing-e1525337078609.jpg',7077),(7138,0,'descripcion item13','https://www.madmen-onlinemarketing.de/wp-content/uploads/jan-madmen-onlinemarketing-e1525337078609.jpg','Item13','https://www.madmen-onlinemarketing.de/wp-content/uploads/jan-madmen-onlinemarketing-e1525337078609.jpg',7077);
/*!40000 ALTER TABLE `item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `message`
--

DROP TABLE IF EXISTS `message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `message` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `body` longtext,
  `is_spam` bit(1) NOT NULL,
  `sent_moment` datetime DEFAULT NULL,
  `subject` varchar(255) DEFAULT NULL,
  `tags` varchar(255) DEFAULT NULL,
  `sender` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `UK_iwf26op1bvdfl6ubuvhbbp49p` (`sender`,`is_spam`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message`
--

LOCK TABLES `message` WRITE;
/*!40000 ALTER TABLE `message` DISABLE KEYS */;
INSERT INTO `message` VALUES (6961,0,'Thanks for your reply','\0','2017-05-09 10:17:00','Thank you','notification',6952),(6962,0,'Thanks for your reply','\0','2017-05-09 10:17:00','Thank you','problem',6915),(6963,0,'Thanks for your reply','\0','2017-05-09 10:17:00','Thank you','position',6916),(6964,0,'Thanks for your reply','\0','2017-05-09 10:17:00','Thank you','answer',6959),(6965,0,'Thanks for your reply','\0','2017-05-09 10:17:00','Thank you','curriculum',6935),(6966,0,'Thanks for your reply','\0','2017-05-09 10:17:00','Thank you','position',6916);
/*!40000 ALTER TABLE `message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `message_recipients`
--

DROP TABLE IF EXISTS `message_recipients`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `message_recipients` (
  `message` int(11) NOT NULL,
  `recipients` int(11) NOT NULL,
  KEY `FK_1odmg2n3n487tvhuxx5oyyya2` (`message`),
  CONSTRAINT `FK_1odmg2n3n487tvhuxx5oyyya2` FOREIGN KEY (`message`) REFERENCES `message` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message_recipients`
--

LOCK TABLES `message_recipients` WRITE;
/*!40000 ALTER TABLE `message_recipients` DISABLE KEYS */;
INSERT INTO `message_recipients` VALUES (6961,6953),(6962,6952),(6962,6953),(6962,6954),(6962,6955),(6962,6956),(6962,6957),(6962,6959),(6963,6935),(6963,6936),(6963,6937),(6964,6954),(6965,6952),(6965,6953),(6965,6954),(6965,6955),(6965,6956),(6965,6957),(6965,6959),(6966,6935),(6966,6936),(6966,6937),(6966,6952),(6966,6953),(6966,6954),(6966,6955);
/*!40000 ALTER TABLE `message_recipients` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `miscellaneous_data`
--

DROP TABLE IF EXISTS `miscellaneous_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `miscellaneous_data` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `attachments` varchar(255) DEFAULT NULL,
  `text` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `miscellaneous_data`
--

LOCK TABLES `miscellaneous_data` WRITE;
/*!40000 ALTER TABLE `miscellaneous_data` DISABLE KEYS */;
INSERT INTO `miscellaneous_data` VALUES (6967,0,'http://www.attachment.com/data1','text miscelleneous data1 del rookie 1'),(6968,0,'http://www.attachment.com/data2','text miscelleneous data2 del rookie 1'),(6969,0,'http://www.attachment.com/data3','text miscelleneous data3 del rookie 1'),(6970,0,'http://www.attachment.com/data3','text miscelleneous data4 del rookie 1'),(6971,0,'http://www.attachment.com/data4','text miscelleneous data1 del rookie 2'),(6972,0,'http://www.attachment.com/data5','text miscelleneous data2 del rookie 2'),(6973,0,'http://www.attachment.com/data4','text miscelleneous data3 del rookie 2'),(6974,0,'http://www.attachment.com/data5','text miscelleneous data4 del rookie 2'),(6975,0,'http://www.attachment.com/data6','text miscelleneous data1 del rookie 3'),(6976,0,'http://www.attachment.com/data6','text miscelleneous data2 del rookie 3'),(6977,0,'http://www.attachment.com/data6','text miscelleneous data3 del rookie 3'),(6978,0,'http://www.attachment.com/data6','text miscelleneous'),(6979,0,'http://www.attachment.com/data6','text miscelleneous data1 del rookie 5'),(6980,0,'http://www.attachment.com/data6','text miscelleneous data2 del rookie 5'),(6981,0,'http://www.attachment.com/data6','text miscelleneous data1 del rookie 6'),(6982,0,'http://www.attachment.com/data6','text miscelleneous data2 del rookie 6'),(6983,0,'http://www.attachment.com/data6','text miscelleneous data3 del rookie 6'),(6984,0,'http://www.attachment.com/data6','text miscelleneous data4 del rookie 6'),(6985,0,'http://www.attachment.com/data6','text miscelleneous'),(6986,0,'http://www.attachment.com/data6','text miscelleneous'),(6987,0,'http://www.attachment.com/data6','text miscelleneous data1 del rookie 7'),(6988,0,'http://www.attachment.com/data6','text miscelleneous data2 del rookie 7'),(6989,0,'http://www.attachment.com/data6','text miscelleneous'),(6990,0,'http://www.attachment.com/data6','text miscelleneous');
/*!40000 ALTER TABLE `miscellaneous_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `personal_data`
--

DROP TABLE IF EXISTS `personal_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `personal_data` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `fullname` varchar(255) DEFAULT NULL,
  `github_profile` varchar(255) DEFAULT NULL,
  `linked_in_profile` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `statement` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `personal_data`
--

LOCK TABLES `personal_data` WRITE;
/*!40000 ALTER TABLE `personal_data` DISABLE KEYS */;
INSERT INTO `personal_data` VALUES (7004,0,'Rookie1 Alvarez','https://github.com/personal1','https://www.linkedin.com/personal1','631047853','statement personal data 1 del rookie 1'),(7005,0,'Rookie1 Alvarez','https://github.com/personal1','https://www.linkedin.com/personal1','631047853','statement personal data 2 del rookie 1'),(7006,0,'Rookie1 Alvarez','https://github.com/personal1','https://www.linkedin.com/personal1','631047853','statement personal data 2 del rookie 1'),(7007,0,'Rookie2 Munoz','https://github.com/personal3','https://www.linkedin.com/personal3','632587412','statement personal data 1 del rookie 2'),(7008,0,'Rookie2 Munoz','https://github.com/personal3','https://www.linkedin.com/personal3','632587412','statement personal data 2 del rookie 2'),(7009,0,'Rookie3 Esteban','https://github.com/personal4','https://www.linkedin.com/personal4','632587412','statement personal data 1 del rookie 3'),(7010,0,'Rookie3 Esteban','https://github.com/personal4','https://www.linkedin.com/personal4','632587412','statement personal data 1 del rookie 3'),(7011,0,'Rookie3 Esteban','https://github.com/personal4','https://www.linkedin.com/personal4','632587412','statement personal data 1 del rookie 3'),(7012,0,'Rookie3 Esteban','https://github.com/personal4','https://www.linkedin.com/personal4','632587412','statement personal data 1 del rookie 3'),(7013,0,'Rookie4 Rodriguez','https://github.com/personal5','https://www.linkedin.com/personal5','632587412','statement personal data 1 del rookie 4'),(7014,0,'Rookie4 Rodriguez','https://github.com/personal5','https://www.linkedin.com/personal5','632587412','statement personal data 5'),(7015,0,'Rookie4 Rodriguez','https://github.com/personal5','https://www.linkedin.com/personal5','632587412','statement personal data 5'),(7016,0,'Rookie5 Lopez','https://github.com/personal5','https://www.linkedin.com/personal5','632587412','statement personal data 5'),(7017,0,'Rookie5 Lopez','https://github.com/personal5','https://www.linkedin.com/personal5','632587412','statement personal data 5'),(7018,0,'Rookie6 Barrera','https://github.com/personal5','https://www.linkedin.com/personal5','632587412','statement personal data 5'),(7019,0,'Rookie6 Barrera','https://github.com/personal5','https://www.linkedin.com/personal5','632587412','statement personal data 5'),(7020,0,'Rookie6 Barrera','https://github.com/personal5','https://www.linkedin.com/personal5','632587412','statement personal data'),(7021,0,'Rookie7 Navarro','https://github.com/personal5','https://www.linkedin.com/personal5','632587412','statement personal data 5'),(7022,0,'Rookie7 Navarro','https://github.com/personal5','https://www.linkedin.com/personal5','632587412','statement personal data 5'),(7023,0,'Rookie7 Navarro','https://github.com/personal5','https://www.linkedin.com/personal5','632587412','statement personal data'),(7024,0,'Rookie7 Navarro','https://github.com/personal5','https://www.linkedin.com/personal5','632587412','statement personal data'),(7025,0,'Rookie8 Moreno','https://github.com/personal5','https://www.linkedin.com/personal5','632587412','statement personal data 5');
/*!40000 ALTER TABLE `personal_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `position`
--

DROP TABLE IF EXISTS `position`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `position` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `deadline` date DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `is_cancelled` bit(1) NOT NULL,
  `is_final_mode` bit(1) NOT NULL,
  `profile` varchar(255) DEFAULT NULL,
  `salary` double NOT NULL,
  `skills` varchar(255) DEFAULT NULL,
  `technologies` varchar(255) DEFAULT NULL,
  `ticker` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `company` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_15390c4j2aeju6ha0iwvi6mc5` (`ticker`),
  KEY `UK_7mmmvedajgdrjcq4940r6leib` (`salary`),
  KEY `UK_motj024ne7yvs6124kugwbrck` (`is_final_mode`),
  KEY `UK_sbvjiwr8lnatlindwii8ijw3h` (`company`,`is_final_mode`,`is_cancelled`),
  KEY `UK_hdrydnvokbdh3vfme3as797ig` (`is_final_mode`,`is_cancelled`),
  KEY `UK_p1dvtfa0heqwte443u9lms0kh` (`is_final_mode`,`is_cancelled`,`title`,`description`,`profile`,`skills`,`technologies`,`company`),
  KEY `UK_l01961ptql97qten0obe0f5qs` (`is_final_mode`,`is_cancelled`,`ticker`,`title`,`description`,`profile`,`skills`,`technologies`,`deadline`,`salary`),
  CONSTRAINT `FK_7qlfn4nye234rrm4w83nms1g8` FOREIGN KEY (`company`) REFERENCES `company` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `position`
--

LOCK TABLES `position` WRITE;
/*!40000 ALTER TABLE `position` DISABLE KEYS */;
INSERT INTO `position` VALUES (7026,0,'2019-12-05','Esta es position1 del company1','\0','','Economy',1586.23,'Number','Android','ONEC-4562','Position 1 - Company 1',6935),(7027,0,'2019-12-05','Esta es position2 del company1','\0','','Math',3586.23,'Number','R','ONEC-4752','Position 2 - Company 1',6935),(7028,0,'2019-12-05','Esta es position3 del company1','\0','\0','Math',3586.23,'Number','R','ONEC-4772','Position 3 - Company 1',6935),(7029,0,'2019-12-05','Esta es position4 del company1','','','Math',3586.23,'Number','R','ONEC-4122','Position 4 - Company 1',6935),(7030,0,'2019-11-05','Esta es position5 del company2','\0','\0','Computing',2686.23,'Pyhton','Eclipse','TWOC-4556','Position 5 - Company 2',6936),(7031,0,'2019-11-05','Esta es position6 del company2','\0','','Junior',2686.23,'Pyhton','Eclipse','TWOC-4146','Position 6 - Company 2',6936),(7032,0,'2019-11-05','Esta es position7 del company2','\0','','Senior',2686.23,'Pyhton','Eclipse','TWOC-4786','Position 7 - Company 2',6936),(7033,0,'2019-11-08','Esta es position8 del company3','\0','','App',2886.23,'Android','Java','FOUR-2556','Position 8 - Company 3',6937),(7034,0,'2019-11-08','Esta es position9 del company3','\0','','App',2886.23,'Android','Java','THRE-2556','Position 9 - Company 3',6937);
/*!40000 ALTER TABLE `position` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `position_data`
--

DROP TABLE IF EXISTS `position_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `position_data` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `end_date` date DEFAULT NULL,
  `start_date` date DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `position_data`
--

LOCK TABLES `position_data` WRITE;
/*!40000 ALTER TABLE `position_data` DISABLE KEYS */;
INSERT INTO `position_data` VALUES (7035,0,'description','2014-10-20','2014-08-20','Position data 1 del rookie 1'),(7036,0,'description','2016-01-03','2015-09-02','Position data 2 del rookie 1'),(7037,0,'description',NULL,'2018-12-22','Position data 1 del rookie 2'),(7038,0,'description',NULL,'2018-12-22','Position data 2 del rookie 2'),(7039,0,'description','2018-01-03','2017-08-31','Position data 1 del rookie 3'),(7040,0,'description','2018-01-03','2017-08-31','Position data 2 del rookie 3'),(7041,0,'description','2018-01-03','2017-08-31','Position data 3 del rookie 3'),(7042,0,'description','2018-01-03','2017-08-31','Position'),(7043,0,'description','2013-01-13','2013-01-12','Position data 1 del rookie 4'),(7044,0,'description',NULL,'2018-08-20','Position data 2 del rookie 4'),(7045,0,'description',NULL,'2018-08-20','Position data 3 del rookie 4'),(7046,0,'description',NULL,'2016-08-20','Position data 1 del rookie 6'),(7047,0,'description',NULL,'2016-08-20','Position data 2 del rookie 6'),(7048,0,'description',NULL,'2016-08-20','Position data'),(7049,0,'description',NULL,'2018-08-20','Position data 1 del rookie 7'),(7050,0,'description',NULL,'2018-08-20','Position data 2 del rookie 7'),(7051,0,'description',NULL,'2018-08-20','Position data'),(7052,0,'description',NULL,'2018-08-20','Position data'),(7053,0,'description',NULL,'2016-08-20','Position data 1 del rookie 8');
/*!40000 ALTER TABLE `position_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `position_problems`
--

DROP TABLE IF EXISTS `position_problems`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `position_problems` (
  `position` int(11) NOT NULL,
  `problems` int(11) NOT NULL,
  KEY `FK_7pe330ganri24wsftsajlm4l9` (`problems`),
  KEY `FK_iji6l3ytrjgytbgo6oi061elj` (`position`),
  CONSTRAINT `FK_iji6l3ytrjgytbgo6oi061elj` FOREIGN KEY (`position`) REFERENCES `position` (`id`),
  CONSTRAINT `FK_7pe330ganri24wsftsajlm4l9` FOREIGN KEY (`problems`) REFERENCES `problem` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `position_problems`
--

LOCK TABLES `position_problems` WRITE;
/*!40000 ALTER TABLE `position_problems` DISABLE KEYS */;
INSERT INTO `position_problems` VALUES (7026,7054),(7026,7055),(7027,7054),(7027,7056),(7031,7058),(7031,7060),(7031,7062),(7032,7059),(7032,7060),(7032,7061),(7033,7065),(7033,7066),(7034,7066);
/*!40000 ALTER TABLE `position_problems` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `problem`
--

DROP TABLE IF EXISTS `problem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `problem` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `attachments` varchar(255) DEFAULT NULL,
  `hint` varchar(255) DEFAULT NULL,
  `is_final_mode` bit(1) NOT NULL,
  `statement` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `company` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `UK_is3c456yjdg242skxcgn8exgb` (`company`,`is_final_mode`),
  CONSTRAINT `FK_1dla8eoii1nn6emoo4yv86pgb` FOREIGN KEY (`company`) REFERENCES `company` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `problem`
--

LOCK TABLES `problem` WRITE;
/*!40000 ALTER TABLE `problem` DISABLE KEYS */;
INSERT INTO `problem` VALUES (7054,0,'','Propongo que el rookie esté cualificado','','Statement','Problem 1 - Company 1',6935),(7055,0,'','Que el rookie viva en Sevilla','','Statement','Problem 2 - Company 1',6935),(7056,0,'','Que el rookie viva en Sevilla','','Statement','Problem 3 - Company 1',6935),(7057,0,'','Que el rookie viva en Sevilla','\0','Statement','Problem 4 - Company 1',6935),(7058,0,'','Necesito que trabaje los fines de semana','','Statement','Problem 5 - Company 2',6936),(7059,0,'','Necesito que trabaje los fines de semana','','Statement','Problem 6 - Company 2',6936),(7060,0,'','Necesito que trabaje los fines de semana','','Statement','Problem 7 - Company 2',6936),(7061,0,'','Necesito que trabaje los fines de semana','','Statement','Problem 8 - Company 2',6936),(7062,0,'','Necesito que trabaje los fines de semana','','Statement','Problem 9 - Company 2',6936),(7063,0,'','Necesito que trabaje los fines de semana','\0','Statement','Problem 10 - Company 3',6937),(7064,0,'','Necesito que trabaje los fines de semana','','Statement','Problem 11 - Company 3',6937),(7065,0,'','Necesito que trabaje los fines de semana','','Statement','Problem 12 - Company 3',6937),(7066,0,'','Necesito que trabaje los fines de semana','','Statement','Problem 13 - Company 3',6937),(7067,0,'','Necesito que trabaje los fines de semana','','Statement','Problem 14 - Company 1',6935),(7068,0,'','Necesito que trabaje los fines de semana','','Statement','Problem 15 - Company 2',6936),(7069,0,'','Necesito que trabaje los fines de semana','','Statement','Problem 16 - Company 3',6937),(7070,0,'','Necesito que trabaje los fines de semana','\0','Statement','Problem 17 - Company 1',6935),(7071,0,'','Necesito que trabaje los fines de semana','\0','Statement','Problem 18 - Company 2',6936);
/*!40000 ALTER TABLE `problem` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `provider`
--

DROP TABLE IF EXISTS `provider`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `provider` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `vatnumber` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `cvv_code` int(11) NOT NULL,
  `expiration_month` varchar(255) DEFAULT NULL,
  `expiration_year` varchar(255) DEFAULT NULL,
  `holder` varchar(255) DEFAULT NULL,
  `make` varchar(255) DEFAULT NULL,
  `number` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `is_spammer` bit(1) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `user_account` int(11) NOT NULL,
  `provider_make` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_pj40m1p8m3lcs2fkdl1n3f3lq` (`user_account`),
  UNIQUE KEY `UK_7pvp08p4hu0e5k4452khlhv78` (`email`),
  CONSTRAINT `FK_pj40m1p8m3lcs2fkdl1n3f3lq` FOREIGN KEY (`user_account`) REFERENCES `user_account` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `provider`
--

LOCK TABLES `provider` WRITE;
/*!40000 ALTER TABLE `provider` DISABLE KEYS */;
INSERT INTO `provider` VALUES (7072,0,97,'Calle Provider 1',180,'04','21','holder22','MCARD','4507131688273419','provider1@gmail.com',NULL,'Provider1','63015521','http://webfeb.in/wp-content/uploads/2016/11/logo-design-for-it-company.jpg','Lobato',6908,'Make del Provider 1'),(7073,0,97,'Calle Provider 2',613,'07','24','holder23','MCARD','4737888229428065','provider2@gmail.com',NULL,'Provider2','63015521','http://webfeb.in/wp-content/uploads/2016/11/logo-design-for-it-company.jpg','Pavon',6909,'Make del Provider 2'),(7074,0,97,'Calle Provider 3',613,'07','24','holder24','MCARD','4436860896310276','provider3@gmail.com',NULL,'Provider3','63015521','http://webfeb.in/wp-content/uploads/2016/11/logo-design-for-it-company.jpg','Lobato',6910,'Make del Provider 3'),(7075,0,97,'Calle Provider 4',950,'04','21','holder25','MCARD','4393228453922746','provider4@gmail.com',NULL,'Provider4','63015521','http://webfeb.in/wp-content/uploads/2016/11/logo-design-for-it-company.jpg','Pavon',6911,'Make del Provider 4'),(7076,0,97,'Calle Provider 5',124,'03','22','holder26','MCARD','6991117604891978','provider5@gmail.com',NULL,'Provider5','63015521','http://webfeb.in/wp-content/uploads/2016/11/logo-design-for-it-company.jpg','Lobato',6912,'Make del Provider 5'),(7077,0,97,'Calle Provider 6',180,'04','21','holder27','MCARD','6686452513127994','provider6@gmail.com',NULL,'Provider6','63015521','http://webfeb.in/wp-content/uploads/2016/11/logo-design-for-it-company.jpg','Pavon',6913,'Make del Provider 6'),(7078,0,97,'Calle Provider 7',613,'07','24','holder28','MCARD','6372154754847707','provider7@gmail.com',NULL,'Provider7','63015521','http://webfeb.in/wp-content/uploads/2016/11/logo-design-for-it-company.jpg','Lobato',6914,'Make del Provider 7');
/*!40000 ALTER TABLE `provider` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rookie`
--

DROP TABLE IF EXISTS `rookie`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rookie` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `vatnumber` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `cvv_code` int(11) NOT NULL,
  `expiration_month` varchar(255) DEFAULT NULL,
  `expiration_year` varchar(255) DEFAULT NULL,
  `holder` varchar(255) DEFAULT NULL,
  `make` varchar(255) DEFAULT NULL,
  `number` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `is_spammer` bit(1) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `user_account` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_2n8nv9qsl5pnxhnosngfkkm4i` (`user_account`),
  UNIQUE KEY `UK_dudgelrrmk8b9clu196w7gjgy` (`email`),
  CONSTRAINT `FK_2n8nv9qsl5pnxhnosngfkkm4i` FOREIGN KEY (`user_account`) REFERENCES `user_account` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rookie`
--

LOCK TABLES `rookie` WRITE;
/*!40000 ALTER TABLE `rookie` DISABLE KEYS */;
INSERT INTO `rookie` VALUES (6952,0,97,'Calle Rookie 1',258,'02','22','holder6','MCARD','4024007150182987','rookie1@gmail.com',NULL,'Rookie1','63015521','https://www.madmen-onlinemarketing.de/wp-content/uploads/jan-madmen-onlinemarketing-e1525337078609.jpg','Alvarez',6892),(6953,0,97,'Calle Rookie 2',254,'02','21','holder7','MCARD','4556001181801737','rookie2@gmail.com',NULL,'Rookie2','63015521','https://www.madmen-onlinemarketing.de/wp-content/uploads/jan-madmen-onlinemarketing-e1525337078609.jpg','Munoz',6893),(6954,0,97,'Calle Rookie 3',218,'03','22','holder8','MCARD','4485923119929700','rookie3@gmail.com',NULL,'Rookie3','63015521','https://www.madmen-onlinemarketing.de/wp-content/uploads/jan-madmen-onlinemarketing-e1525337078609.jpg','Esteban',6894),(6955,0,97,'Calle Rookie 4',250,'04','21','holder9','MCARD','5265265673659825','rookie4@gmail.com',NULL,'Rookie4','63015521','https://www.madmen-onlinemarketing.de/wp-content/uploads/jan-madmen-onlinemarketing-e1525337078609.jpg','Rodriguez',6895),(6956,0,97,'Calle Rookie 5',118,'03','22','holder10','MCARD','5466799096597995','rookie5@gmail.com',NULL,'Rookie5','63015521','https://www.madmen-onlinemarketing.de/wp-content/uploads/jan-madmen-onlinemarketing-e1525337078609.jpg','Lopez',6896),(6957,0,97,'Calle Rookie 6',950,'04','21','holder11','MCARD','5386393115853022','rookie6@gmail.com',NULL,'Rookie6','63015521','https://www.madmen-onlinemarketing.de/wp-content/uploads/jan-madmen-onlinemarketing-e1525337078609.jpg','Barrera',6897),(6958,0,97,'Calle Rookie 7',124,'03','22','holder12','MCARD','5283115543844354','rookie7@gmail.com',NULL,'Rookie7','63015521','https://www.madmen-onlinemarketing.de/wp-content/uploads/jan-madmen-onlinemarketing-e1525337078609.jpg','Navarro',6898),(6959,0,97,'Calle Rookie 8',180,'04','21','holder13','MCARD','5311247326144102','rookie8@gmail.com',NULL,'Rookie8','63015521','https://www.madmen-onlinemarketing.de/wp-content/uploads/jan-madmen-onlinemarketing-e1525337078609.jpg','Moreno',6899),(6960,0,32,'Calle Rookie 9',613,'07','24','holder14','MCARD','5363673506446743','rookie9@gmail.com',NULL,'Rookie9','632321239','https://www.madmen-onlinemarketing.de/wp-content/uploads/jan-madmen-onlinemarketing-e1525337078609.jpg','Rubio',6900);
/*!40000 ALTER TABLE `rookie` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `social_profile`
--

DROP TABLE IF EXISTS `social_profile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `social_profile` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `link_profile` varchar(255) DEFAULT NULL,
  `nick` varchar(255) DEFAULT NULL,
  `social_network` varchar(255) DEFAULT NULL,
  `actor` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_nedqor7tomp44srq0vbui1h6b` (`link_profile`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `social_profile`
--

LOCK TABLES `social_profile` WRITE;
/*!40000 ALTER TABLE `social_profile` DISABLE KEYS */;
INSERT INTO `social_profile` VALUES (6991,0,'http://www.twitter1.com','nick1','Twitter',6915),(6992,0,'http://www.instagram1.com','nick2','Instagram',6952),(6993,0,'http://www.facebook1.com','nick3','Facebook',6952),(6994,0,'http://www.facebook2.com','nick4','Facebook',6953),(6995,0,'http://www.facebook3.com','nick5','Facebook',6953),(6996,0,'https://www.flickr1.com','nick6','Flickr',6954),(6997,0,'http://www.tinder1.com','nick7','Tinder',6935),(6998,0,'https://www.tuenti1.es','nick8','Tuenti',6935),(6999,0,'https://www.messenger1.es','nick9','Messenger',6935),(7000,0,'https://www.twitter2.com','nick10','Twitter',6936),(7001,0,'http://www.tinder2.com','nick11','Tinder',6936),(7002,0,'https://www.tuenti2.es','nick12','Tuenti',6937),(7003,0,'https://www.messenger2.es','nick13','Messenger',6937);
/*!40000 ALTER TABLE `social_profile` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sponsorship`
--

DROP TABLE IF EXISTS `sponsorship`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sponsorship` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `banner` varchar(255) DEFAULT NULL,
  `cvv_code` int(11) NOT NULL,
  `expiration_month` varchar(255) DEFAULT NULL,
  `expiration_year` varchar(255) DEFAULT NULL,
  `holder` varchar(255) DEFAULT NULL,
  `make` varchar(255) DEFAULT NULL,
  `number` varchar(255) DEFAULT NULL,
  `target_page` varchar(255) DEFAULT NULL,
  `position` int(11) NOT NULL,
  `provider` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_jnrjojfnyyaie1n4jhsdxjbig` (`position`),
  KEY `FK_dwk5ymekhnr143u957f7gtns6` (`provider`),
  CONSTRAINT `FK_dwk5ymekhnr143u957f7gtns6` FOREIGN KEY (`provider`) REFERENCES `provider` (`id`),
  CONSTRAINT `FK_jnrjojfnyyaie1n4jhsdxjbig` FOREIGN KEY (`position`) REFERENCES `position` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sponsorship`
--

LOCK TABLES `sponsorship` WRITE;
/*!40000 ALTER TABLE `sponsorship` DISABLE KEYS */;
INSERT INTO `sponsorship` VALUES (7079,0,'https://www.digitalprinting.co.uk/media/images/products/slides/31/vinyl-pvc-banners-1.jpg',724,'08','21','holder29','VISA','4931301939300792','http://www.targetpage.com',7026,7072),(7080,0,'https://www.digitalprinting.co.uk/media/images/products/slides/31/vinyl-pvc-banners-1.jpg',587,'10','22','holder30','VISA','4149491757454652','http://www.targetpage.com',7027,7072),(7081,0,'https://www.digitalprinting.co.uk/media/images/products/slides/31/vinyl-pvc-banners-1.jpg',587,'03','22','holder31','VISA','4868000464935082','http://www.targetpage.com',7027,7073),(7082,0,'https://www.digitalprinting.co.uk/media/images/products/slides/31/vinyl-pvc-banners-1.jpg',147,'02','21','holder32','VISA','4507260447657628','http://www.targetpage.com',7031,7074),(7083,0,'https://www.digitalprinting.co.uk/media/images/products/slides/31/vinyl-pvc-banners-1.jpg',258,'01','20','holder33','MCARD','4937251840717218','http://www.targetpage.com',7026,7075),(7084,0,'https://www.digitalprinting.co.uk/media/images/products/slides/31/vinyl-pvc-banners-1.jpg',258,'02','22','holder34','MCARD','4774207809404400','http://www.targetpage.com',7032,7076),(7085,0,'https://www.digitalprinting.co.uk/media/images/products/slides/31/vinyl-pvc-banners-1.jpg',254,'02','21','holder35','MCARD','5506947852834563','http://www.targetpage.com',7033,7076),(7086,0,'https://www.digitalprinting.co.uk/media/images/products/slides/31/vinyl-pvc-banners-1.jpg',218,'03','22','holder36','MCARD','5257782243656891','http://www.targetpage.com',7032,7078);
/*!40000 ALTER TABLE `sponsorship` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `system_tag`
--

DROP TABLE IF EXISTS `system_tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `system_tag` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `text` varchar(255) DEFAULT NULL,
  `actor` int(11) NOT NULL,
  `message` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `UK_rp2knd2grlj4ao3ek3p8a9pmh` (`actor`,`message`,`text`),
  KEY `UK_iru8dbu07gaqhsc8c8xvjijsq` (`message`,`text`),
  CONSTRAINT `FK_k3jdfke3ocmep23m2gcc5y7kd` FOREIGN KEY (`message`) REFERENCES `message` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `system_tag`
--

LOCK TABLES `system_tag` WRITE;
/*!40000 ALTER TABLE `system_tag` DISABLE KEYS */;
INSERT INTO `system_tag` VALUES (7139,0,'HARDDELETED',6953,6961),(7140,0,'DELETED',6953,6962);
/*!40000 ALTER TABLE `system_tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_account`
--

DROP TABLE IF EXISTS `user_account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_account` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `is_banned` bit(1) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_castjbvpeeus0r8lbpehiu0e4` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_account`
--

LOCK TABLES `user_account` WRITE;
/*!40000 ALTER TABLE `user_account` DISABLE KEYS */;
INSERT INTO `user_account` VALUES (6887,0,'\0','e00cf25ad42683b3df678c61f42c6bda','admin1'),(6888,0,'\0','54b53072540eeeb8f8e9343e71f28176','system'),(6889,0,'\0','df655f976f7c9d3263815bd981225cd9','company1'),(6890,0,'\0','d196a28097115067fefd73d25b0c0be8','company2'),(6891,0,'\0','e828ae3339b8d80b3902c1564578804e','company3'),(6892,0,'\0','9701eb1802a4c63f51e1501512fbdd30','rookie1'),(6893,0,'\0','124be4fa2a59341a1d7e965ac49b2923','rookie2'),(6894,0,'\0','b723fa2fd1c2dc65d166df3e7f83e329','rookie3'),(6895,0,'\0','78dfa0fab9872b58c8affee48f0c4252','rookie4'),(6896,0,'\0','71c3559edfab3474860637d46b7c39de','rookie5'),(6897,0,'\0','2ac0966e96640cedb5d2f770e21b86db','rookie6'),(6898,0,'\0','03e82f65437ab841c7249c041060d6f9','rookie7'),(6899,0,'\0','a91134f99b47c300dd03771543b77b59','rookie8'),(6900,0,'\0','b03ff77f0b1dec7fe41676276cc5854c','rookie9'),(6901,0,'\0','175d2e7a63f386554a4dd66e865c3e14','auditor1'),(6902,0,'\0','04dd94ba3212ac52ad3a1f4cbfb52d4f','auditor2'),(6903,0,'\0','fc2073dbe4f65dfd71e46602f8e6a5f3','auditor3'),(6904,0,'\0','6cc8affcba51a854192a33af68c08f49','auditor4'),(6905,0,'\0','3775bf00262284e83013c9cea5f41431','auditor5'),(6906,0,'\0','5d78d53d65022ce05a759cd3c057782e','auditor6'),(6907,0,'\0','cb22ac7c563939f50b8b44ec70dfecb4','auditor7'),(6908,0,'\0','cdb82d56473901641525fbbd1d5dab56','provider1'),(6909,0,'\0','ebfc815ee2cc6a16225105bb7b3e1e53','provider2'),(6910,0,'\0','a575bf1b9ca7d068cef7bbc8fa7f43e1','provider3'),(6911,0,'\0','5d35a7d891c4aae477f56ebf807fbd0a','provider4'),(6912,0,'\0','bd19ee25be71cfc77f532c4b26b4a54e','provider5'),(6913,0,'\0','85202849e05dc23e4b80f2af8be3650c','provider6'),(6914,0,'\0','081695deabc539ca794c3437d1937158','provider7');
/*!40000 ALTER TABLE `user_account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_account_authorities`
--

DROP TABLE IF EXISTS `user_account_authorities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_account_authorities` (
  `user_account` int(11) NOT NULL,
  `authority` varchar(255) DEFAULT NULL,
  KEY `FK_pao8cwh93fpccb0bx6ilq6gsl` (`user_account`),
  CONSTRAINT `FK_pao8cwh93fpccb0bx6ilq6gsl` FOREIGN KEY (`user_account`) REFERENCES `user_account` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_account_authorities`
--

LOCK TABLES `user_account_authorities` WRITE;
/*!40000 ALTER TABLE `user_account_authorities` DISABLE KEYS */;
INSERT INTO `user_account_authorities` VALUES (6887,'ADMIN'),(6888,'ADMIN'),(6889,'COMPANY'),(6890,'COMPANY'),(6891,'COMPANY'),(6892,'ROOKIE'),(6893,'ROOKIE'),(6894,'ROOKIE'),(6895,'ROOKIE'),(6896,'ROOKIE'),(6897,'ROOKIE'),(6898,'ROOKIE'),(6899,'ROOKIE'),(6900,'ROOKIE'),(6901,'AUDITOR'),(6902,'AUDITOR'),(6903,'AUDITOR'),(6904,'AUDITOR'),(6905,'AUDITOR'),(6906,'AUDITOR'),(6907,'AUDITOR'),(6908,'PROVIDER'),(6909,'PROVIDER'),(6910,'PROVIDER'),(6911,'PROVIDER'),(6912,'PROVIDER'),(6913,'PROVIDER'),(6914,'PROVIDER');
/*!40000 ALTER TABLE `user_account_authorities` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-05-03  6:05:42

commit;