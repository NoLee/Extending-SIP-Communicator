-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Mar 27, 2016 at 03:23 PM
-- Server version: 10.1.10-MariaDB
-- PHP Version: 5.6.19

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `sipdb`
--

-- --------------------------------------------------------

--
-- Table structure for table `billing`
--

CREATE TABLE `billing` (
  `user` varchar(40) NOT NULL,
  `total_cost` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `billing`
--

INSERT INTO `billing` (`user`, `total_cost`) VALUES
('alice', 0),
('bob', 0),
('dan', 0),
('giorgos', 0),
('kostas', 0),
('manolis', 0),
('yiannis', 0);

-- --------------------------------------------------------

--
-- Table structure for table `blocks`
--

CREATE TABLE `blocks` (
  `blocker` varchar(40) NOT NULL,
  `blocked` varchar(40) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `blocks`
--

INSERT INTO `blocks` (`blocker`, `blocked`) VALUES
('yiannis', 'kostas');

-- --------------------------------------------------------

--
-- Table structure for table `forwards`
--

CREATE TABLE `forwards` (
  `fromUser` varchar(40) NOT NULL,
  `toUser` varchar(40) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `forwards`
--

INSERT INTO `forwards` (`fromUser`, `toUser`) VALUES
('alice', 'dan'),
('dan', 'alice'),
('manolis', 'giorgos');

-- --------------------------------------------------------

--
-- Table structure for table `friends`
--

CREATE TABLE `friends` (
  `friend1` varchar(40) NOT NULL,
  `friend2` varchar(40) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `friends_package`
--

CREATE TABLE `friends_package` (
  `user` varchar(40) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `premium`
--

CREATE TABLE `premium` (
  `user` varchar(40) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `user_info`
--

CREATE TABLE `user_info` (
  `username` varchar(40) NOT NULL,
  `password` varchar(40) NOT NULL,
  `address` varchar(200) DEFAULT NULL,
  `email` varchar(200) DEFAULT NULL,
  `phone` varchar(200) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user_info`
--

INSERT INTO `user_info` (`username`, `password`, `address`, `email`, `phone`) VALUES
('alice', '522b276a356bdf39013dfabea2cd43e141ecc9e8', 'Address 7', 'alice@hotmail.com', '2483943222'),
('bob', '48181acd22b3edaebc8a447868a7df7ce629920a', 'Adress 34', 'bob@hotmail.com', '4183657465'),
('dan', '2591e5f46f28d303f9dc027d475a5c60d8dea17a', 'Address 58', 'dan@yahoo.gr', '394782375924'),
('giorgos', '4f0561b536eb9180575b09802f074ab4f72dba3d', 'Address 10', 'giorgos@hotmail.com', '37467356435'),
('kostas', 'e1a8399e36effe75a124102eb2a2bdfc82e0aab4', 'Address 1', 'kostas@hotmail.com', '123456789'),
('manolis', 'f2620df262d9e0ce7ac6ed6631fe1cc2c20ab35a', 'Address 3', 'manolis@hotmail.com', '42576784598'),
('yiannis', '97cd3975513bd55eee215dc9f60defaabc7649f2', 'Address 2', 'yiannis@hotmail.com', '5673950953');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `billing`
--
ALTER TABLE `billing`
  ADD PRIMARY KEY (`user`);

--
-- Indexes for table `blocks`
--
ALTER TABLE `blocks`
  ADD PRIMARY KEY (`blocker`,`blocked`);

--
-- Indexes for table `forwards`
--
ALTER TABLE `forwards`
  ADD PRIMARY KEY (`fromUser`);

--
-- Indexes for table `friends`
--
ALTER TABLE `friends`
  ADD PRIMARY KEY (`friend1`,`friend2`);

--
-- Indexes for table `friends_package`
--
ALTER TABLE `friends_package`
  ADD PRIMARY KEY (`user`);

--
-- Indexes for table `premium`
--
ALTER TABLE `premium`
  ADD PRIMARY KEY (`user`);

--
-- Indexes for table `user_info`
--
ALTER TABLE `user_info`
  ADD PRIMARY KEY (`username`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `billing`
--
ALTER TABLE `billing`
  ADD CONSTRAINT `billing_ibfk_1` FOREIGN KEY (`user`) REFERENCES `user_info` (`username`) ON DELETE CASCADE;

--
-- Constraints for table `blocks`
--
ALTER TABLE `blocks`
  ADD CONSTRAINT `blocks_ibfk_1` FOREIGN KEY (`blocker`) REFERENCES `user_info` (`username`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `forwards`
--
ALTER TABLE `forwards`
  ADD CONSTRAINT `forwards_ibfk_1` FOREIGN KEY (`fromUser`) REFERENCES `user_info` (`username`) ON DELETE CASCADE;

--
-- Constraints for table `friends`
--
ALTER TABLE `friends`
  ADD CONSTRAINT `friends_ibfk_1` FOREIGN KEY (`friend1`) REFERENCES `user_info` (`username`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `friends_package`
--
ALTER TABLE `friends_package`
  ADD CONSTRAINT `friends_package_ibfk_1` FOREIGN KEY (`user`) REFERENCES `user_info` (`username`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `premium`
--
ALTER TABLE `premium`
  ADD CONSTRAINT `premium_ibfk_1` FOREIGN KEY (`user`) REFERENCES `user_info` (`username`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
