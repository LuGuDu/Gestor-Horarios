-- MySQL dump 10.13  Distrib 8.0.26, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: horariosaux
-- ------------------------------------------------------
-- Server version	8.0.26

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
-- Table structure for table `auxiliares`
--

DROP TABLE IF EXISTS `auxiliares`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `auxiliares` (
  `id` varchar(45) NOT NULL COMMENT 'Almacena id del auxiliar',
  `dni` varchar(150) NOT NULL COMMENT 'Almacena dni del auxiliar',
  `nombre` varchar(150) NOT NULL COMMENT 'Almacena nombre del auxiliar',
  `apellido1` varchar(150) NOT NULL COMMENT 'Almacena primer apellido del auxiliar',
  `apellido2` varchar(150) NOT NULL COMMENT 'Almacena segundo apellido del auxiliar',
  `telefono` varchar(150) NOT NULL COMMENT 'Almacena telefono del auxiliar',
  `fechainiciocontrato` date NOT NULL COMMENT 'Almacena la fecha del inicio del contrato del auxiliar',
  `correo` varchar(150) NOT NULL COMMENT 'Almacena el correo electronico del auxiliar',
  `activo` int NOT NULL COMMENT 'Almacena si el auxiliar esta activo o no',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `auxiliares`
--

LOCK TABLES `auxiliares` WRITE;
/*!40000 ALTER TABLE `auxiliares` DISABLE KEYS */;
/*!40000 ALTER TABLE `auxiliares` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `incidencias`
--

DROP TABLE IF EXISTS `incidencias`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `incidencias` (
  `id` varchar(45) NOT NULL COMMENT 'Almacena el id de la incidencia',
  `idauxiliar` varchar(45) NOT NULL COMMENT 'Almacena el id del auxiliar que tiene la incidencia',
  `fecha` date NOT NULL COMMENT 'Almacena la fecha de la incidencia',
  `horasNormales` double NOT NULL COMMENT 'Almacena las horas normales realizadas en la incidencia',
  `horasFestivas` double NOT NULL COMMENT 'Almacena las horas festivasrealizadas en la incidencia',
  `kilometraje` double NOT NULL COMMENT 'Almacena el kilometraje realizado en la incidencia',
  `horasFueraConvenio` double NOT NULL COMMENT 'Almacena las horas fuera de conveniorealizadas en la incidencia',
  PRIMARY KEY (`id`),
  KEY `idauxiliar_idx` (`idauxiliar`),
  CONSTRAINT `idauxiliar` FOREIGN KEY (`idauxiliar`) REFERENCES `auxiliares` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `incidencias`
--

LOCK TABLES `incidencias` WRITE;
/*!40000 ALTER TABLE `incidencias` DISABLE KEYS */;
/*!40000 ALTER TABLE `incidencias` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `registrohorarios`
--

DROP TABLE IF EXISTS `registrohorarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `registrohorarios` (
  `id` varchar(45) NOT NULL COMMENT 'Almacena el id del registro',
  `idauxiliar` varchar(45) NOT NULL COMMENT 'Almacena el id del auxiliar que tiene el registro',
  `desde` varchar(8) NOT NULL COMMENT 'Almacena la hora a la que comienza el registro',
  `hasta` varchar(8) NOT NULL COMMENT 'Almacena la hora a la que finaliza el registro',
  `nombre` varchar(255) NOT NULL COMMENT 'Almacena el nombre de la persona a visitar',
  `direccion` varchar(255) NOT NULL COMMENT 'Almacena la direccion de la persona a visitar',
  `telefono` varchar(45) NOT NULL COMMENT 'Almacena el telefono de la persona a visitar',
  `dependiente` varchar(50) NOT NULL COMMENT 'Alamcena si la persona a visitar es dependiente o no',
  `dia` varchar(45) NOT NULL COMMENT 'Almacena el dia del registro',
  PRIMARY KEY (`id`),
  KEY `idauxiliar_idx` (`idauxiliar`),
  CONSTRAINT `idaux` FOREIGN KEY (`idauxiliar`) REFERENCES `auxiliares` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `registrohorarios`
--

LOCK TABLES `registrohorarios` WRITE;
/*!40000 ALTER TABLE `registrohorarios` DISABLE KEYS */;
/*!40000 ALTER TABLE `registrohorarios` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-09-02 15:07:18
