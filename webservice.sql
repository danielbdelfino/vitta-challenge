-- phpMyAdmin SQL Dump
-- version 4.5.2
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: 20-Jul-2017 às 05:48
-- Versão do servidor: 5.7.9
-- PHP Version: 5.6.16

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `webservice`
--

-- --------------------------------------------------------

--
-- Estrutura da tabela `square`
--

DROP TABLE IF EXISTS `square`;
CREATE TABLE IF NOT EXISTS `square` (
  `idterritory` int(11) NOT NULL,
  `x` int(11) NOT NULL,
  `y` int(11) NOT NULL,
  `painted` tinyint(1) NOT NULL,
  PRIMARY KEY (`idterritory`,`x`,`y`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estrutura da tabela `territory`
--

DROP TABLE IF EXISTS `territory`;
CREATE TABLE IF NOT EXISTS `territory` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `area` int(11) NOT NULL DEFAULT '2500',
  `paintedarea` int(11) NOT NULL DEFAULT '0',
  `startx` int(11) NOT NULL,
  `starty` int(11) NOT NULL,
  `endx` int(11) NOT NULL,
  `endy` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
