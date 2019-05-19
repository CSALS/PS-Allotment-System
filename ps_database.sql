-- phpMyAdmin SQL Dump
-- version 4.8.3
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 17, 2019 at 12:44 PM
-- Server version: 10.1.36-MariaDB
-- PHP Version: 7.2.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `ps_database`
--

DELIMITER $$
--
-- Procedures
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `addMobileOfStudent` (IN `id` CHAR(8), IN `mobile` CHAR(10))  MODIFIES SQL DATA
BEGIN
insert into student_mobile values (id,mobile);
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `addPreference` (IN `student_id` CHAR(8), IN `station_id` INT, IN `priority` INT)  NO SQL
BEGIN	
	insert into student_preference values (student_id,station_id,priority);
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `addStudent` (IN `studentId` CHAR(8), IN `name` VARCHAR(20), IN `password` VARCHAR(20), IN `age` INT, IN `year` INT, IN `branch` VARCHAR(20), IN `cgpa` FLOAT, IN `allotted_station_id` INT)  MODIFIES SQL DATA
BEGIN
insert into student values (studentId,name,password,age,year,branch,cgpa,allotted_station_id);
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `checkAdminCredentials` (IN `id` INT, IN `pass` VARCHAR(20), OUT `flag` VARCHAR(20))  NO SQL
BEGIN
select password into flag from admin where adminId=id AND password=pass;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `checkStudentCredentials` (IN `id` CHAR(8), IN `pass` VARCHAR(20), OUT `flag` CHAR(8))  READS SQL DATA
BEGIN
select studentId into flag from student where studentId=id AND password=pass;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteNotify` ()  MODIFIES SQL DATA
BEGIN
	DELETE from student_notify;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `maxStationId` (OUT `id` INT)  READS SQL DATA
BEGIN
select max(stationId) into id from station;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `updateCgpa` (IN `cg` FLOAT, IN `id` CHAR(8), OUT `flag` INT)  MODIFIES SQL DATA
BEGIN
IF cg <= 10 AND cg >= 0 THEN
update student set cgpa=cg where studentId=id;
set flag = 1;
ELSE
set flag = 0;
END IF;
END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE `admin` (
  `adminId` int(11) NOT NULL,
  `password` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`adminId`, `password`) VALUES
(101, '2'),
(102, '3');

-- --------------------------------------------------------

--
-- Table structure for table `station`
--

CREATE TABLE `station` (
  `stationId` int(11) NOT NULL,
  `stationName` varchar(20) NOT NULL,
  `domain` varchar(10) NOT NULL,
  `accommodation` varchar(3) NOT NULL,
  `city` varchar(20) NOT NULL,
  `Stipend` int(11) NOT NULL,
  `slots2ndYear` int(11) DEFAULT NULL,
  `slots4thYear` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `station`
--

INSERT INTO `station` (`stationId`, `stationName`, `domain`, `accommodation`, `city`, `Stipend`, `slots2ndYear`, `slots4thYear`) VALUES
(1, 'Happiest Minds', 'IT', 'No', 'Bangalore', 0, 1, 0),
(2, 'Paytm', 'IT', 'No', 'Mumbai', 0, 1, 0),
(3, 'Microsoft', 'IT', 'Yes', 'Hyderabad', 0, 1, 1),
(4, 'AMAZON', 'IT', 'Yes', 'Chennai', 1000, 0, 1),
(5, 'HPCL', 'IT', 'No', 'Mumbai', 100, 0, 1);

-- --------------------------------------------------------

--
-- Table structure for table `student`
--

CREATE TABLE `student` (
  `studentId` char(8) NOT NULL,
  `name` varchar(20) NOT NULL,
  `password` varchar(20) NOT NULL,
  `age` int(11) NOT NULL,
  `year` int(11) NOT NULL,
  `branch` varchar(20) NOT NULL,
  `cgpa` float NOT NULL,
  `allotted_station_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `student`
--

INSERT INTO `student` (`studentId`, `name`, `password`, `age`, `year`, `branch`, `cgpa`, `allotted_station_id`) VALUES
('20150110', 'jon ', 'cs', 20, 4, 'eee', 5.4, NULL),
('20150188', 'robert', 'cs', 20, 4, 'cs', 8, NULL),
('20151111', 'charan', 'cs', 18, 4, 'cs', 8, NULL),
('20170110', 'charan', 'cs', 18, 2, 'cs', 5, NULL),
('20170160', 'arun', 'cs', 18, 2, 'cs', 7.6, NULL),
('20170168', 'leo', 'cs', 19, 2, 'cs', 8, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `student_mobile`
--

CREATE TABLE `student_mobile` (
  `student_id` char(9) NOT NULL,
  `mobile` char(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `student_mobile`
--

INSERT INTO `student_mobile` (`student_id`, `mobile`) VALUES
('20150110', '9110520573'),
('20150188', '9985231110'),
('20150188', '9985231118'),
('20151111', '1234567890'),
('20170110', '9110520572'),
('20170110', '9985231118'),
('20170160', '1234567890'),
('20170168', '9985231115');

-- --------------------------------------------------------

--
-- Table structure for table `student_notify`
--

CREATE TABLE `student_notify` (
  `student_id` char(8) NOT NULL,
  `admin_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `student_preference`
--

CREATE TABLE `student_preference` (
  `sid` char(8) NOT NULL,
  `station_id` int(11) NOT NULL,
  `priority` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `student_preference`
--

INSERT INTO `student_preference` (`sid`, `station_id`, `priority`) VALUES
('20150110', 3, 1),
('20150110', 4, 2),
('20150110', 5, 3),
('20150188', 3, 1),
('20150188', 4, 2),
('20150188', 5, 3),
('20151111', 3, 1),
('20151111', 4, 2),
('20151111', 5, 3),
('20170110', 1, 1),
('20170110', 2, 2),
('20170110', 3, 3),
('20170160', 1, 1),
('20170160', 2, 2),
('20170160', 3, 3),
('20170168', 1, 1),
('20170168', 2, 2),
('20170168', 3, 3);

-- --------------------------------------------------------

--
-- Table structure for table `swap_requests`
--

CREATE TABLE `swap_requests` (
  `student1` char(8) NOT NULL,
  `student2` char(8) NOT NULL,
  `status` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`adminId`);

--
-- Indexes for table `station`
--
ALTER TABLE `station`
  ADD PRIMARY KEY (`stationId`);

--
-- Indexes for table `student`
--
ALTER TABLE `student`
  ADD PRIMARY KEY (`studentId`),
  ADD KEY `alloted_station_id` (`allotted_station_id`);

--
-- Indexes for table `student_mobile`
--
ALTER TABLE `student_mobile`
  ADD PRIMARY KEY (`student_id`,`mobile`);

--
-- Indexes for table `student_notify`
--
ALTER TABLE `student_notify`
  ADD PRIMARY KEY (`student_id`,`admin_id`),
  ADD KEY `cfk2` (`admin_id`);

--
-- Indexes for table `student_preference`
--
ALTER TABLE `student_preference`
  ADD PRIMARY KEY (`sid`,`station_id`),
  ADD KEY `student_preference_ibfk_2` (`station_id`);

--
-- Indexes for table `swap_requests`
--
ALTER TABLE `swap_requests`
  ADD PRIMARY KEY (`student1`),
  ADD UNIQUE KEY `student2` (`student2`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `student`
--
ALTER TABLE `student`
  ADD CONSTRAINT `student_ibfk_1` FOREIGN KEY (`allotted_station_id`) REFERENCES `station` (`stationId`);

--
-- Constraints for table `student_mobile`
--
ALTER TABLE `student_mobile`
  ADD CONSTRAINT `cfk` FOREIGN KEY (`student_id`) REFERENCES `student` (`studentId`);

--
-- Constraints for table `student_notify`
--
ALTER TABLE `student_notify`
  ADD CONSTRAINT `cfk1` FOREIGN KEY (`student_id`) REFERENCES `student` (`studentId`),
  ADD CONSTRAINT `cfk2` FOREIGN KEY (`admin_id`) REFERENCES `admin` (`adminId`);

--
-- Constraints for table `student_preference`
--
ALTER TABLE `student_preference`
  ADD CONSTRAINT `student_preference_ibfk_1` FOREIGN KEY (`sid`) REFERENCES `student` (`studentId`),
  ADD CONSTRAINT `student_preference_ibfk_2` FOREIGN KEY (`station_id`) REFERENCES `station` (`stationId`);

--
-- Constraints for table `swap_requests`
--
ALTER TABLE `swap_requests`
  ADD CONSTRAINT `fkk1` FOREIGN KEY (`student1`) REFERENCES `student` (`studentId`),
  ADD CONSTRAINT `fkk2` FOREIGN KEY (`student1`) REFERENCES `student` (`studentId`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
