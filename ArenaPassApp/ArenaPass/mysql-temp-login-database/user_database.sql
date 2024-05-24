
/*  CREATE TABLES SECTION */

CREATE TABLE fans (
    fan_account_id INT(9) NOT NULL AUTO_INCREMENT,
    fan_username VARCHAR(30) NOT NULL,
    fan_password VARCHAR(30) NOT NULL,
    fan_legal_name VARCHAR(30) NOT NULL,
    fan_legal_surname VARCHAR(30) NOT NULL,
    fan_citizen_id_number INT(8) NOT NULL,
    fan_citizen_id_expiration_date DATE NOT NULL,
    fan_citizen_id_dob DATE NOT NULL, /*dob=date of birth*/
    fan_registration_date DATE DEFAULT(CURRENT_DATE()),
    fan_pass_id INT(9) NOT NULL,
    fan_account_status ENUM ('VERIFIED','BANNED','PENDING') NOT NULL,
    fan_phone VARCHAR(30) ,
    fan_email VARCHAR(30) ,
    fan_address VARCHAR(100),
    fan_city VARCHAR(100),
    PRIMARY KEY (fan_account_id),
    UNIQUE (fan_pass_id),
    UNIQUE (fan_username),
    UNIQUE (fan_citizen_id_number)
);

CREATE TABLE reservations(
    reservation_id INT(8) NOT NULL AUTO_INCREMENT,
    reservation_fan_pass_id INT(9) NOT NULL,
    reservation_ticket_number INT(8) NOT NULL,
    reservation_seat_id INT(8) NOT NULL,
    reservation_date_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    reservation_type ENUM ('TICKET','SEASON TICKET','NOT AVAILABLE'),
    reservation_match_id INT(6) NOT NULL,
    PRIMARY KEY (reservation_id)
);

CREATE TABLE stadiums(
    stadium_id INT(3) NOT NULL AUTO_INCREMENT,
    stadium_name VARCHAR(30) NOT NULL,
    stadium_max_capacity INT(6) NOT NULL,
    stadium_city VARCHAR(30) NOT NULL,
    PRIMARY KEY (stadium_id)
);

CREATE TABLE seats(
    seat_id INT(8) NOT NULL AUTO_INCREMENT,
    seat_stadium_id INT(3) NOT NULL,
    seat_section VARCHAR(4) NOT NULL,
    seat_number VARCHAR(4) NOT NULL,
    seat_side ENUM ('AT','HT'),
    PRIMARY KEY (seat_id),
    UNIQUE (seat_section,seat_number),
    CONSTRAINT SEAT_STADIUM FOREIGN KEY (seat_stadium_id) REFERENCES stadiums(stadium_id)
);

CREATE TABLE teams (
    team_id INT(3) NOT NULL AUTO_INCREMENT,
    team_name VARCHAR(30) NOT NULL,
    team_def_home_stadium_id INT(3), /* team default home stadium */
    team_points INT(3) NOT NULL DEFAULT 0,
    team_logo_path VARCHAR(100) DEFAULT NULL,
    PRIMARY KEY (team_id),
    UNIQUE (team_name),
    CONSTRAINT TEAM_STADIUM FOREIGN KEY (team_def_home_stadium_id) REFERENCES stadiums(stadium_id)
);

CREATE TABLE season_tickets(
    season_ticket_number INT(8) NOT NULL AUTO_INCREMENT,
    season_ticket_seat_id INT(8) NOT NULL,
    season_ticket_team_id INT(3) NOT NULL,
    season_ticket_stadium_id INT(3) NOT NULL,
    season_ticket_fan_pass_id INT(9) NOT NULL,
    season_ticket_purchase_datetime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (season_ticket_number),
    CONSTRAINT SEASON_SEAT FOREIGN KEY (season_ticket_seat_id) REFERENCES seats(seat_id),
    CONSTRAINT SEASON_FAN FOREIGN KEY (season_ticket_fan_pass_id) REFERENCES fans(fan_pass_id),
    CONSTRAINT SEASON_TEAM FOREIGN KEY (season_ticket_team_id) REFERENCES teams(team_id),
    CONSTRAINT SEASON_STADIUM FOREIGN KEY (season_ticket_stadium_id) REFERENCES stadiums(stadium_id)
);

CREATE TABLE matches(
    match_id INT(6) NOT NULL AUTO_INCREMENT,
    match_stadium_id INT(3) NOT NULL,
    match_home_team INT(3) NOT NULL,
    match_away_team INT(3) NOT NULL,
    match_date DATE NOT NULL,
    match_time TIME NOT NULL,
    match_ht_max_capacity INT(6) NOT NULL, /*ht=home team*/
    match_at_max_capacity INT(6) NOT NULL, /*at=away team*/
    match_restrictions ENUM('NO RESTRICTION','NO AWAY FANS','NO FANS'), /*for determining which sections/seats are available for buying will be set by external script by buying 'empty' tickets for all non available seats*/
    PRIMARY KEY (match_id),
    CONSTRAINT MATCH_STADIUM FOREIGN KEY (match_stadium_id) REFERENCES stadiums(stadium_id),
    CONSTRAINT HOME_TEAM FOREIGN KEY (match_home_team) REFERENCES teams(team_id),
    CONSTRAINT AWAY_TEAM FOREIGN KEY (match_away_team) REFERENCES teams(team_id)

);

CREATE TABLE tickets (
    ticket_number INT(8) NOT NULL AUTO_INCREMENT,
    ticket_seat_id INT(8) NOT NULL,
    ticket_match_id INT(9) NOT NULL,
    ticket_fan_pass_id INT(9) NOT NULL,
    ticket_purchase_datetime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (ticket_number),
    CONSTRAINT SEAT FOREIGN KEY (ticket_seat_id) REFERENCES seats(seat_id),
    CONSTRAINT MATCH_ID FOREIGN KEY (ticket_match_id) REFERENCES matches(match_id),
    CONSTRAINT FAN FOREIGN KEY (ticket_fan_pass_id) REFERENCES fans(fan_pass_id)
);









/* QYERIES SECTION ------------ (FOR TESTING) */


select fan_account_id,fan_username,fan_pass_id,ticket_number,ticket_purchase_datetime
from fans,tickets
where fans.fan_pass_id = tickets.ticket_fan_pass_id AND fans.fan_pass_id = 1001;


/* ------------ TRIGGERS SECTION ------------ */

DELIMITER $
CREATE TRIGGER check_ban_status BEFORE INSERT ON tickets /* VERIFIED TO BE WORKING THROUGH 'select * from tickets' AND 'ERROR 1644 (45000): Fan is not qualified to buy ticket!' */
FOR EACH ROW 
BEGIN
    DECLARE f_status ENUM ('VERIFIED','BANNED','PENDING');

    SELECT fan_account_status INTO f_status
    FROM fans
    WHERE fans.fan_pass_id = NEW.ticket_fan_pass_id;

    IF f_status != 'VERIFIED' THEN
        SIGNAL SQLSTATE VALUE '45000'
        SET MESSAGE_TEXT = 'Fan is not qualified to buy ticket!';
    END IF;
END$

DELIMITER ;

DELIMITER $
CREATE TRIGGER check_double_ticket BEFORE INSERT ON tickets /* VERIFIED TO BE WORKING THROUGH 'select * from tickets' AND ERROR 1644 (45001): Fan alreade has ticket for same match! */
FOR EACH ROW 
BEGIN
    DECLARE t_count INT(2);

    SELECT count(*) INTO t_count
    FROM tickets
    WHERE tickets.ticket_fan_pass_id = NEW.ticket_fan_pass_id AND tickets.ticket_match_id=NEW.ticket_match_id
    GROUP BY tickets.ticket_fan_pass_id;

    IF t_count >=1 THEN
        SIGNAL SQLSTATE VALUE '45001'
        SET MESSAGE_TEXT = 'Fan already has ticket for same match!';
    END IF;

END$

DELIMITER ;


DELIMITER $
CREATE TRIGGER check_same_team BEFORE INSERT ON matches /* VERIFIED TO BE WORKING THROUGH  'select * from matches;' AND ERROR 1644 (45003): Match cannot be set: same away and home team! */
FOR EACH ROW 
BEGIN

    IF NEW.match_home_team = NEW.match_away_team THEN
        SIGNAL SQLSTATE VALUE '45003'
        SET MESSAGE_TEXT = 'Match cannot be set: same away and home team!';
    END IF;

END$

DELIMITER ;

DELIMITER $
CREATE TRIGGER check_ban_status_season BEFORE INSERT ON season_tickets
FOR EACH ROW 
BEGIN
    DECLARE f_status ENUM ('VERIFIED','BANNED','PENDING');

    SELECT fan_acount_status INTO f_status
    FROM fans
    WHERE fans.fan_pass_id = NEW.season_ticket_fan_pass_id;

    IF f_status != 'VERIFIED' THEN
        SIGNAL SQLSTATE VALUE '45005'
        SET MESSAGE_TEXT = 'Fan is not qualified to buy ticket!';
    END IF;
END$
DELIMITER ;

DELIMITER $
CREATE TRIGGER check_double_season_ticket BEFORE INSERT ON season_tickets 
FOR EACH ROW 
BEGIN
    DECLARE t_count INT(2);

    SELECT count(*) INTO t_count
    FROM season_tickets
    WHERE season_tickets.season_ticket_fan_pass_id = NEW.season_ticket_fan_pass_id AND season_tickets.season_ticket_team_id=NEW.season_ticket_team_id
    GROUP BY season_tickets.season_ticket_fan_pass_id;

    IF t_count >=1 THEN
        SIGNAL SQLSTATE VALUE '45001'
        SET MESSAGE_TEXT = 'Fan already has season ticket for this team!';
    END IF;

END$

DELIMITER ;


