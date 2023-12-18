-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Dec 18, 2023 at 09:49 AM
-- Server version: 5.7.42-0ubuntu0.18.04.1
-- PHP Version: 8.2.5

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `2324DEV3063`
--

-- --------------------------------------------------------

--
-- Table structure for table `colors`
--

CREATE TABLE `colors` (
  `id_color` int(11) NOT NULL,
  `color` varchar(255) CHARACTER SET utf8 NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `colors`
--

INSERT INTO `colors` (`id_color`, `color`) VALUES
(1, 'Orange'),
(2, 'Red'),
(3, 'Brown'),
(4, 'White'),
(5, 'Yellow'),
(6, 'n');

-- --------------------------------------------------------

--
-- Table structure for table `color_mushrooms`
--

CREATE TABLE `color_mushrooms` (
  `id` int(11) NOT NULL,
  `id_mushroom` int(11) NOT NULL,
  `Id_color` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `color_mushrooms`
--

INSERT INTO `color_mushrooms` (`id`, `id_mushroom`, `Id_color`) VALUES
(1, 1, 2),
(2, 1, 4),
(3, 2, 3),
(4, 2, 1),
(5, 3, 5),
(6, 4, 3),
(7, 5, 4),
(8, 5, 3),
(9, 6, 1),
(10, 7, 3),
(11, 8, 4),
(12, 8, 2),
(13, 9, 5),
(14, 10, 4),
(15, 10, 5),
(16, 11, 3),
(17, 11, 1),
(18, 12, 2),
(19, 13, 4),
(20, 14, 3),
(21, 15, 3),
(22, 16, 2),
(23, 17, 4),
(24, 17, 5),
(25, 18, 5),
(26, 19, 4),
(27, 19, 5),
(28, 20, 4),
(29, 32, 6);

-- --------------------------------------------------------

--
-- Table structure for table `growth_periods`
--

CREATE TABLE `growth_periods` (
  `id` int(11) NOT NULL,
  `growth_period` varchar(255) CHARACTER SET utf8 NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `growth_periods`
--

INSERT INTO `growth_periods` (`id`, `growth_period`) VALUES
(1, 'Winter'),
(2, 'Spring'),
(3, 'Summer'),
(4, 'Autumn');

-- --------------------------------------------------------

--
-- Table structure for table `growth_periods_mushrooms`
--

CREATE TABLE `growth_periods_mushrooms` (
  `id` int(11) NOT NULL,
  `id_mushroom` int(11) NOT NULL,
  `id_growth_period` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `growth_periods_mushrooms`
--

INSERT INTO `growth_periods_mushrooms` (`id`, `id_mushroom`, `id_growth_period`) VALUES
(1, 1, 4),
(2, 1, 3),
(3, 2, 3),
(4, 2, 2),
(5, 4, 3),
(6, 5, 4),
(7, 5, 1),
(8, 6, 2),
(9, 7, 1),
(10, 7, 2),
(11, 8, 4),
(12, 9, 2),
(13, 10, 4),
(14, 10, 3),
(15, 11, 1),
(16, 12, 3),
(17, 12, 2),
(18, 13, 3),
(19, 13, 2),
(20, 14, 3),
(21, 15, 4),
(22, 16, 4),
(23, 17, 3),
(24, 18, 3),
(25, 18, 2),
(26, 19, 3),
(27, 20, 4),
(28, 20, 3),
(29, 20, 2),
(31, 32, 1);

-- --------------------------------------------------------

--
-- Table structure for table `habitats`
--

CREATE TABLE `habitats` (
  `id` int(11) NOT NULL,
  `habitat` varchar(255) CHARACTER SET utf8 NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `habitats`
--

INSERT INTO `habitats` (`id`, `habitat`) VALUES
(1, 'Coniferous forests'),
(2, 'Mixed forests'),
(3, 'Grassland'),
(4, 'Deciduous forests'),
(10, 'test');

-- --------------------------------------------------------

--
-- Table structure for table `mushrooms`
--

CREATE TABLE `mushrooms` (
  `id` int(11) NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 NOT NULL,
  `hat_shape` varchar(255) CHARACTER SET utf8 NOT NULL,
  `spore_patern` varchar(255) CHARACTER SET utf8 NOT NULL,
  `odor` varchar(255) CHARACTER SET utf8 NOT NULL,
  `toxicity_level` varchar(255) CHARACTER SET utf8 NOT NULL,
  `size` varchar(255) CHARACTER SET utf8 NOT NULL,
  `stem_shape` varchar(255) CHARACTER SET utf8 NOT NULL,
  `lamellae_presence` varchar(255) CHARACTER SET utf8 NOT NULL,
  `habitat_id` int(11) NOT NULL,
  `statistic` int(11) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `mushrooms`
--

INSERT INTO `mushrooms` (`id`, `name`, `hat_shape`, `spore_patern`, `odor`, `toxicity_level`, `size`, `stem_shape`, `lamellae_presence`, `habitat_id`, `statistic`) VALUES
(1, 'Amanita muscaria (Fly agaric)', 'Spherical', 'Lamellae', 'Unremarkable', 'Toxic', 'Medium', 'Cylindrical', 'Present', 3, 0),
(2, 'Boletus edulis (Ceps)', 'Spherical', 'No Lamellae', 'Earthy', 'Edible', 'Large', 'Cylindrical', 'Present', 2, 0),
(3, 'Cantharellus cibarius (Cockscomb)', 'Wavy', 'Lamellae', 'Fruity', 'Edible', 'Small', 'Cylindrical', 'Present', 1, 0),
(4, 'Psilocybe cubensis (Psilocybin mushroom)', 'Bell-shaped', 'Lamellae', 'Unremarkable', 'Hallucinogenic', 'Small', 'Cylindrical', 'Present', 4, 0),
(5, 'Agaricus bisporus (Mushroom)', 'Spherical', 'Lamellae', 'Mild', 'Edible', 'Medium', 'Cylindrical', 'Present', 2, 0),
(6, 'Cantharellus tubaeformis (Winter tufted mushroom)', 'Funnel-shaped', 'Lamellae', 'Fruity', 'Edible', 'Small', 'Cylindrical', 'Present', 3, 0),
(7, 'Clitocybe nebularis (Fog mushroom)', 'Funnel-shaped', 'Lamellae', 'Floury', 'Toxic', 'Medium', 'Slim', 'Present', 1, 0),
(8, 'Coprinus comatus (Large Parasol fungus)', 'Cylindrical', 'Lamellae', 'Fragrant', 'Edible', 'Medium Medium', 'Slim', 'Present', 1, 0),
(9, 'Hypholoma fasciculare (Sulphur head)', 'Conical', 'Lamellae', 'Sulfur', 'Toxic', 'Small', 'Cylindrical', 'Present', 2, 0),
(10, 'Lycoperdon perlatum (Common dusty mushroom)', 'Spherical', 'No Lamellae', 'Unremarkable', 'Non Toxic', 'Small', 'Short', 'Absent', 4, 0),
(11, 'Macrolepiota procera (Parasol fungus)', 'Oblong', 'Lamellae', 'pleasant', 'Edible', 'Large', 'Long', 'Present', 2, 0),
(12, 'Russula emetica (Braakrussula)', 'Flat', 'Lamellae', 'Sour', 'Toxic', 'Large', 'Solid', 'Present', 1, 0),
(13, 'Inocybe geophylla (White tuberous ammonite)', 'Cone-shaped', 'Lamellae', 'Earthy', 'Toxic', 'Small', 'Slim', 'Present', 4, 0),
(14, 'Galerina marginata (Common fop fungus)', 'Cone-shaped', 'Lamellae', 'Unpleasant', 'Toxic', 'Small', 'Cylindrical', 'Present', 4, 0),
(15, 'Armillaria mellea (Common honey fungus)', 'Funnel-shaped', 'Lamellae', 'Sulfur', 'Toxic', 'Medium', 'Slim', 'Present', 3, 0),
(16, 'Cortinarius orellanus (Red-brown curtain fungus)', 'Oblong', 'Lamellae', 'Weak', 'Toxic', 'Medium', 'Cylindrical', 'Present', 1, 0),
(17, 'Entoloma sinuatum (White funnel mushroom)', 'Oblong', 'Lamellae', 'Musty', 'Toxic', 'Medium', 'Present', 'Present', 1, 0),
(18, 'Tricholoma equestre (Yellow knights mushroom)', 'Convex', 'Lamellae', 'Anise', 'Edible', 'Medium', 'Slim', 'Present', 1, 0),
(19, 'Hericium erinaceus (Wig fungus)', 'Roundish', 'No Lamellae', 'Pleasant', 'Edible', 'Medium', 'Short', 'Absent', 3, 0),
(20, 'Phallus impudicus (Stinking short shield fungus)', 'Conical', 'No Lamellae', 'Stinky', 'Toxic', 'Small', 'Long', 'Absent', 2, 0),
(32, 'a', 'z', 'e', 'r', 't', 'y', 'u', '', 10, 0);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `colors`
--
ALTER TABLE `colors`
  ADD PRIMARY KEY (`id_color`);

--
-- Indexes for table `color_mushrooms`
--
ALTER TABLE `color_mushrooms`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_mushroom` (`id_mushroom`),
  ADD KEY `Id_color` (`Id_color`);

--
-- Indexes for table `growth_periods`
--
ALTER TABLE `growth_periods`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `growth_periods_mushrooms`
--
ALTER TABLE `growth_periods_mushrooms`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_mushroom` (`id_mushroom`),
  ADD KEY `id_growth_period` (`id_growth_period`);

--
-- Indexes for table `habitats`
--
ALTER TABLE `habitats`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `mushrooms`
--
ALTER TABLE `mushrooms`
  ADD PRIMARY KEY (`id`),
  ADD KEY `habitat_id` (`habitat_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `colors`
--
ALTER TABLE `colors`
  MODIFY `id_color` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `color_mushrooms`
--
ALTER TABLE `color_mushrooms`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=30;

--
-- AUTO_INCREMENT for table `growth_periods`
--
ALTER TABLE `growth_periods`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `growth_periods_mushrooms`
--
ALTER TABLE `growth_periods_mushrooms`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=32;

--
-- AUTO_INCREMENT for table `habitats`
--
ALTER TABLE `habitats`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `mushrooms`
--
ALTER TABLE `mushrooms`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=33;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `color_mushrooms`
--
ALTER TABLE `color_mushrooms`
  ADD CONSTRAINT `color_mushrooms_ibfk_1` FOREIGN KEY (`id_mushroom`) REFERENCES `mushrooms` (`id`),
  ADD CONSTRAINT `color_mushrooms_ibfk_2` FOREIGN KEY (`Id_color`) REFERENCES `colors` (`id_color`);

--
-- Constraints for table `growth_periods_mushrooms`
--
ALTER TABLE `growth_periods_mushrooms`
  ADD CONSTRAINT `growth_periods_mushrooms_ibfk_1` FOREIGN KEY (`id_mushroom`) REFERENCES `mushrooms` (`id`),
  ADD CONSTRAINT `growth_periods_mushrooms_ibfk_2` FOREIGN KEY (`id_growth_period`) REFERENCES `growth_periods` (`id`);

--
-- Constraints for table `mushrooms`
--
ALTER TABLE `mushrooms`
  ADD CONSTRAINT `mushrooms_ibfk_1` FOREIGN KEY (`habitat_id`) REFERENCES `habitats` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
