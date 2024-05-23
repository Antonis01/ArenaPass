-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 23, 2024 at 06:09 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `arenapass`
--

-- --------------------------------------------------------

--
-- Table structure for table `fans`
--

CREATE TABLE `fans` (
  `fan_account_id` int(9) NOT NULL,
  `fan_username` varchar(30) NOT NULL,
  `fan_password` varchar(30) NOT NULL,
  `fan_legal_name` varchar(30) NOT NULL,
  `fan_legal_surname` varchar(30) NOT NULL,
  `fan_citizen_id_number` int(8) NOT NULL,
  `fan_citizen_id_expiration_date` date NOT NULL,
  `fan_citizen_id_dob` date NOT NULL,
  `fan_registration_date` date DEFAULT curdate(),
  `fan_pass_id` int(9) NOT NULL,
  `fan_account_status` enum('VERIFIED','BANNED','PENDING') NOT NULL,
  `fan_phone` varchar(30) NOT NULL,
  `fan_email` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `fans`
--

INSERT INTO `fans` (`fan_account_id`, `fan_username`, `fan_password`, `fan_legal_name`, `fan_legal_surname`, `fan_citizen_id_number`, `fan_citizen_id_expiration_date`, `fan_citizen_id_dob`, `fan_registration_date`, `fan_pass_id`, `fan_account_status`, `fan_phone`, `fan_email`) VALUES
(1, 'fan1', 'pass1', 'John', 'Doe', 12345678, '2030-01-01', '1990-01-01', '2024-05-23', 1001, 'VERIFIED', '', ''),
(2, 'fan2', 'pass2', 'Jane', 'Smith', 87654321, '2030-01-01', '1991-02-02', '2024-05-23', 1002, 'VERIFIED', '', ''),
(3, 'fan3', 'pass3', 'Alice', 'Johnson', 11223344, '2030-01-01', '1992-03-03', '2024-05-23', 1003, 'VERIFIED', '', ''),
(4, 'fan4', 'pass4', 'Bob', 'Brown', 44332211, '2030-01-01', '1993-04-04', '2024-05-23', 1004, 'VERIFIED', '', ''),
(5, 'fan5', 'pass5', 'Charlie', 'Davis', 55667788, '2030-01-01', '1994-05-05', '2024-05-23', 1005, 'VERIFIED', '', '');

-- --------------------------------------------------------

--
-- Table structure for table `matches`
--

CREATE TABLE `matches` (
  `match_id` int(6) NOT NULL,
  `match_stadium_id` int(3) NOT NULL,
  `match_home_team` int(3) NOT NULL,
  `match_away_team` int(3) NOT NULL,
  `match_date` date NOT NULL,
  `match_time` time NOT NULL,
  `match_ht_max_capacity` int(6) NOT NULL,
  `match_at_max_capacity` int(6) NOT NULL,
  `match_restrictions` enum('NO RESTRICTION','NO AWAY FANS','NO FANS') DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `matches`
--

INSERT INTO `matches` (`match_id`, `match_stadium_id`, `match_home_team`, `match_away_team`, `match_date`, `match_time`, `match_ht_max_capacity`, `match_at_max_capacity`, `match_restrictions`) VALUES
(1, 1, 1, 2, '2024-05-20', '15:00:00', 25000, 25000, 'NO RESTRICTION'),
(2, 2, 3, 4, '2024-05-21', '18:00:00', 20000, 20000, 'NO RESTRICTION');

-- --------------------------------------------------------

--
-- Table structure for table `reservations`
--

CREATE TABLE `reservations` (
  `reservation_id` int(8) NOT NULL,
  `reservation_fan_pass_id` int(9) NOT NULL,
  `reservation_ticket_number` int(8) NOT NULL,
  `reservation_seat_id` int(8) NOT NULL,
  `reservation_date_time` timestamp NOT NULL DEFAULT current_timestamp(),
  `reservation_type` enum('TICKET','SEASON TICKET','NOT AVAILABLE') DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `season_tickets`
--

CREATE TABLE `season_tickets` (
  `season_ticket_number` int(8) NOT NULL,
  `season_ticket_seat_id` int(8) NOT NULL,
  `season_ticket_team_id` int(3) NOT NULL,
  `season_ticket_stadium_id` int(3) NOT NULL,
  `season_ticket_fan_pass_id` int(9) NOT NULL,
  `season_ticket_purchase_datetime` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `seats`
--

CREATE TABLE `seats` (
  `seat_id` int(8) NOT NULL,
  `seat_stadium_id` int(3) NOT NULL,
  `seat_section` varchar(4) NOT NULL,
  `seat_number` varchar(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `seats`
--

INSERT INTO `seats` (`seat_id`, `seat_stadium_id`, `seat_section`, `seat_number`) VALUES
(1, 1, 'A1', '001'),
(2, 1, 'A1', '002'),
(3, 1, 'A1', '003'),
(4, 1, 'A1', '004'),
(5, 1, 'A1', '005'),
(6, 1, 'A2', '001'),
(7, 1, 'A2', '002'),
(8, 1, 'A2', '003'),
(9, 1, 'A2', '004'),
(10, 1, 'A2', '005'),
(11, 2, 'B1', '001'),
(12, 2, 'B1', '002'),
(13, 2, 'B1', '003'),
(14, 2, 'B1', '004'),
(15, 2, 'B1', '005'),
(16, 2, 'B2', '001'),
(17, 2, 'B2', '002'),
(18, 2, 'B2', '003'),
(19, 2, 'B2', '004'),
(20, 2, 'B3', '001');

-- --------------------------------------------------------

--
-- Table structure for table `stadiums`
--

CREATE TABLE `stadiums` (
  `stadium_id` int(3) NOT NULL,
  `stadium_name` varchar(30) NOT NULL,
  `stadium_max_capacity` int(6) NOT NULL,
  `stadium_city` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `stadiums`
--

INSERT INTO `stadiums` (`stadium_id`, `stadium_name`, `stadium_max_capacity`, `stadium_city`) VALUES
(1, 'Stadium A', 50000, ''),
(2, 'Stadium B', 40000, '');

-- --------------------------------------------------------

--
-- Table structure for table `teams`
--

CREATE TABLE `teams` (
  `team_id` int(3) NOT NULL,
  `team_name` varchar(30) NOT NULL,
  `team_def_home_stadium_id` int(3) DEFAULT NULL,
  `team_points` int(3) NOT NULL DEFAULT 0,
  `team_logo_path` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `teams`
--

INSERT INTO `teams` (`team_id`, `team_name`, `team_def_home_stadium_id`, `team_points`, `team_logo_path`) VALUES
(1, 'Panseraikos', 1, 0, 'Panseraikos_new_emblem.png'),
(2, 'Lamia', 1, 0, 'PAS_Lamia_1964_logo.png'),
(3, 'Asteras Tripolis', 2, 0, 'Asteras_Tripolis_FC_logo.svg.png'),
(4, 'Atromitos', 2, 0, 'Atromitos.png');

-- --------------------------------------------------------

--
-- Table structure for table `tickets`
--

CREATE TABLE `tickets` (
  `ticket_number` int(8) NOT NULL,
  `ticket_seat_id` int(8) NOT NULL,
  `ticket_match_id` int(9) NOT NULL,
  `ticket_fan_pass_id` int(9) NOT NULL,
  `ticket_purchase_datetime` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tickets`
--

INSERT INTO `tickets` (`ticket_number`, `ticket_seat_id`, `ticket_match_id`, `ticket_fan_pass_id`, `ticket_purchase_datetime`) VALUES
(1, 1, 1, 1001, '2024-05-23 15:16:21'),
(2, 2, 1, 1002, '2024-05-23 15:16:21'),
(3, 11, 2, 1003, '2024-05-23 15:16:21'),
(4, 12, 2, 1004, '2024-05-23 15:16:21'),
(5, 13, 2, 1005, '2024-05-23 15:16:21');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `fans`
--
ALTER TABLE `fans`
  ADD PRIMARY KEY (`fan_account_id`),
  ADD UNIQUE KEY `fan_pass_id` (`fan_pass_id`),
  ADD UNIQUE KEY `fan_username` (`fan_username`),
  ADD UNIQUE KEY `fan_citizen_id_number` (`fan_citizen_id_number`);

--
-- Indexes for table `matches`
--
ALTER TABLE `matches`
  ADD PRIMARY KEY (`match_id`),
  ADD KEY `MATCH_STADIUM` (`match_stadium_id`),
  ADD KEY `HOME_TEAM` (`match_home_team`),
  ADD KEY `AWAY_TEAM` (`match_away_team`);

--
-- Indexes for table `reservations`
--
ALTER TABLE `reservations`
  ADD PRIMARY KEY (`reservation_id`);

--
-- Indexes for table `season_tickets`
--
ALTER TABLE `season_tickets`
  ADD PRIMARY KEY (`season_ticket_number`),
  ADD KEY `SEASON_SEAT` (`season_ticket_seat_id`),
  ADD KEY `SEASON_FAN` (`season_ticket_fan_pass_id`),
  ADD KEY `SEASON_TEAM` (`season_ticket_team_id`),
  ADD KEY `SEASON_STADIUM` (`season_ticket_stadium_id`);

--
-- Indexes for table `seats`
--
ALTER TABLE `seats`
  ADD PRIMARY KEY (`seat_id`),
  ADD UNIQUE KEY `seat_section` (`seat_section`,`seat_number`),
  ADD KEY `SEAT_STADIUM` (`seat_stadium_id`);

--
-- Indexes for table `stadiums`
--
ALTER TABLE `stadiums`
  ADD PRIMARY KEY (`stadium_id`);

--
-- Indexes for table `teams`
--
ALTER TABLE `teams`
  ADD PRIMARY KEY (`team_id`),
  ADD UNIQUE KEY `team_name` (`team_name`),
  ADD KEY `TEAM_STADIUM` (`team_def_home_stadium_id`);

--
-- Indexes for table `tickets`
--
ALTER TABLE `tickets`
  ADD PRIMARY KEY (`ticket_number`),
  ADD KEY `SEAT` (`ticket_seat_id`),
  ADD KEY `MATCH_ID` (`ticket_match_id`),
  ADD KEY `FAN` (`ticket_fan_pass_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `fans`
--
ALTER TABLE `fans`
  MODIFY `fan_account_id` int(9) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `matches`
--
ALTER TABLE `matches`
  MODIFY `match_id` int(6) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `reservations`
--
ALTER TABLE `reservations`
  MODIFY `reservation_id` int(8) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `season_tickets`
--
ALTER TABLE `season_tickets`
  MODIFY `season_ticket_number` int(8) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `seats`
--
ALTER TABLE `seats`
  MODIFY `seat_id` int(8) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT for table `stadiums`
--
ALTER TABLE `stadiums`
  MODIFY `stadium_id` int(3) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `teams`
--
ALTER TABLE `teams`
  MODIFY `team_id` int(3) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `tickets`
--
ALTER TABLE `tickets`
  MODIFY `ticket_number` int(8) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `matches`
--
ALTER TABLE `matches`
  ADD CONSTRAINT `AWAY_TEAM` FOREIGN KEY (`match_away_team`) REFERENCES `teams` (`team_id`),
  ADD CONSTRAINT `HOME_TEAM` FOREIGN KEY (`match_home_team`) REFERENCES `teams` (`team_id`),
  ADD CONSTRAINT `MATCH_STADIUM` FOREIGN KEY (`match_stadium_id`) REFERENCES `stadiums` (`stadium_id`);

--
-- Constraints for table `season_tickets`
--
ALTER TABLE `season_tickets`
  ADD CONSTRAINT `SEASON_FAN` FOREIGN KEY (`season_ticket_fan_pass_id`) REFERENCES `fans` (`fan_pass_id`),
  ADD CONSTRAINT `SEASON_SEAT` FOREIGN KEY (`season_ticket_seat_id`) REFERENCES `seats` (`seat_id`),
  ADD CONSTRAINT `SEASON_STADIUM` FOREIGN KEY (`season_ticket_stadium_id`) REFERENCES `stadiums` (`stadium_id`),
  ADD CONSTRAINT `SEASON_TEAM` FOREIGN KEY (`season_ticket_team_id`) REFERENCES `teams` (`team_id`);

--
-- Constraints for table `seats`
--
ALTER TABLE `seats`
  ADD CONSTRAINT `SEAT_STADIUM` FOREIGN KEY (`seat_stadium_id`) REFERENCES `stadiums` (`stadium_id`);

--
-- Constraints for table `teams`
--
ALTER TABLE `teams`
  ADD CONSTRAINT `TEAM_STADIUM` FOREIGN KEY (`team_def_home_stadium_id`) REFERENCES `stadiums` (`stadium_id`);

--
-- Constraints for table `tickets`
--
ALTER TABLE `tickets`
  ADD CONSTRAINT `FAN` FOREIGN KEY (`ticket_fan_pass_id`) REFERENCES `fans` (`fan_pass_id`),
  ADD CONSTRAINT `MATCH_ID` FOREIGN KEY (`ticket_match_id`) REFERENCES `matches` (`match_id`),
  ADD CONSTRAINT `SEAT` FOREIGN KEY (`ticket_seat_id`) REFERENCES `seats` (`seat_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
