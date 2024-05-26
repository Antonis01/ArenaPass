
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
    fan_phone VARCHAR(30),
    fan_email VARCHAR(30),
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
    match_ht_max_capacity INT(6) DEFAULT 0, /*ht=home team*/
    match_at_max_capacity INT(6) DEFAULT 0, /*at=away team*/
    match_restrictions ENUM('NO RESTRICTION','NO AWAY FANS','NO FANS') DEFAULT 'NO RESTRICTION', /*for determining which sections/seats are available for buying will be set by external script by buying 'empty' tickets for all non available seats*/
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









/* ----------- QYERIES SECTION ------------ (FOR TESTING) */


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

DELIMITER $

CREATE PROCEDURE season_reservations_match (IN r_match_id INT, IN r_team_id INT)
BEGIN
    DECLARE res_ticket_number INT;
    DECLARE res_ticket_seat_id INT;
    DECLARE res_ticket_fan_pass_id INT;

    DECLARE finishedFlag INT;
    DECLARE ticket_cursor CURSOR FOR
    SELECT season_ticket_number,season_ticket_seat_id,season_ticket_fan_pass_id FROM season_tickets WHERE season_ticket_team_id=r_team_id;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET finishedFlag=1;
    OPEN ticket_cursor;


    SET finishedFlag=0;
    FETCH ticket_cursor INTO res_ticket_number, res_ticket_seat_id, res_ticket_fan_pass_id;

     WHILE (finishedFlag=0) DO
        /* INSERT INTO reservations (reservation_fan_pass_id,reservation_ticket_number,reservation_seat_id,reservation_match_id,reservation_type) VALUES
        (res_ticket_fan_pass_id,res_ticket_number,res_ticket_seat_id,r_match_id,'SEASON TICKET'); */
        UPDATE reservations SET reservation_fan_pass_id = res_ticket_fan_pass_id,reservation_ticket_number=res_ticket_number,reservation_match_id=r_match_id,reservation_type='SEASON TICKET'
        WHERE reservation_match_id = r_match_id AND reservation_seat_id = res_ticket_seat_id;
        FETCH ticket_cursor INTO res_ticket_number, res_ticket_seat_id, res_ticket_fan_pass_id;
    END WHILE;
    CLOSE ticket_cursor;
END$

DELIMITER ;


DELIMITER $

CREATE PROCEDURE set_home_available (IN r_match_id INT, IN r_stadium_id INT)
BEGIN
    DECLARE res_seat_id INT;
    DECLARE finishedFlag INT DEFAULT 0;

    /* Declare cursor for home team sections */
    DECLARE seat_cursor_ht CURSOR FOR
    SELECT seat_id FROM seats WHERE seat_stadium_id = r_stadium_id AND seat_side = 'HT';

    /* Declare cursor for away team sections */
    DECLARE seat_cursor_at CURSOR FOR
    SELECT seat_id FROM seats WHERE seat_stadium_id = r_stadium_id AND seat_side = 'AT';

    /* Declare handler for NOT FOUND to set the finished flag */
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET finishedFlag = 1;

    /* Open cursor for home team sections */
    OPEN seat_cursor_ht;

    /* Fetch first row */
    FETCH seat_cursor_ht INTO res_seat_id;

    /* Create reservations for home team as available */
    WHILE (finishedFlag = 0) DO
        INSERT INTO reservations (reservation_fan_pass_id, reservation_ticket_number, reservation_seat_id, reservation_type, reservation_match_id)
        VALUES (-1, -1, res_seat_id, 'AVAILABLE', r_match_id);
        FETCH seat_cursor_ht INTO res_seat_id;
    END WHILE;
    CLOSE seat_cursor_ht;

    /* Reset finishedFlag for next use */
    SET finishedFlag = 0;

    /* Open cursor for away team sections */
    OPEN seat_cursor_at;

    /* Fetch first row */
    FETCH seat_cursor_at INTO res_seat_id;

    /* Create reservations for away team as not available */
    WHILE (finishedFlag = 0) DO
        INSERT INTO reservations (reservation_fan_pass_id, reservation_ticket_number, reservation_seat_id, reservation_type, reservation_match_id)
        VALUES (-1, -1, res_seat_id, 'NOT AVAILABLE', r_match_id);
        FETCH seat_cursor_at INTO res_seat_id;
    END WHILE;
    CLOSE seat_cursor_at;

END$

DELIMITER ;

DELIMITER $

CREATE PROCEDURE set_not_available (IN r_match_id INT, IN r_stadium_id INT)
BEGIN

    DECLARE res_seat_id INT;

    DECLARE finishedFlag INT;
    DECLARE seat_cursor CURSOR FOR
    SELECT seat_id FROM seats WHERE seat_stadium_id=r_stadium_id;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET finishedFlag=1;
    OPEN seat_cursor;

    SET finishedFlag=0;
    FETCH seat_cursor INTO res_seat_id;

     WHILE (finishedFlag=0) DO
        INSERT INTO reservations (reservation_fan_pass_id,reservation_ticket_number,reservation_seat_id,reservation_type,reservation_match_id) VALUES
        (-1,-1,res_seat_id,'NOT AVAILABLE',r_match_id);
        FETCH seat_cursor INTO res_seat_id;
    END WHILE;
    CLOSE seat_cursor;
END$

DELIMITER ;

DELIMITER $

CREATE PROCEDURE set_available (IN r_match_id INT, IN r_stadium_id INT)
BEGIN

    DECLARE res_seat_id INT;

    DECLARE finishedFlag INT;
    DECLARE seat_cursor CURSOR FOR
    SELECT seat_id FROM seats WHERE seat_stadium_id=r_stadium_id;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET finishedFlag=1;
    OPEN seat_cursor;

    SET finishedFlag=0;
    FETCH seat_cursor INTO res_seat_id;

     WHILE (finishedFlag=0) DO
        INSERT INTO reservations (reservation_fan_pass_id,reservation_ticket_number,reservation_seat_id,reservation_type,reservation_match_id) VALUES
        (-1,-1,res_seat_id,'AVAILABLE',r_match_id);
        FETCH seat_cursor INTO res_seat_id;
    END WHILE;
    CLOSE seat_cursor;
END$

DELIMITER ;

DELIMITER $

CREATE TRIGGER set_reservation_availability AFTER INSERT ON matches
FOR EACH ROW
BEGIN
    DECLARE ht_def_hs_id INT; /*home team default home stadium*/

    /* SET AVAILABILITY FOR ALL SEATS OF MATCH */
    IF NEW.match_restrictions = 3 THEN
        CALL set_not_available(NEW.match_id,NEW.match_stadium_id); /*--> reservations*/
    ELSEIF NEW.match_restrictions = 2 THEN
        CALL set_home_available(NEW.match_id,NEW.match_stadium_id); /*--> reservations*/
    ELSEIF NEW.match_restrictions = 1 THEN
        CALL set_available(NEW.match_id,NEW.match_stadium_id); /*--> reservations*/
    ELSE
        SIGNAL SQLSTATE '45001' SET MESSAGE_TEXT = 'PROBLEM ELSE';
    END IF;

    /* RESERVE SEATS FOR SEASON TICKET HOLDERS */
    SELECT teams.team_def_home_stadium_id INTO ht_def_hs_id
    FROM teams
    WHERE teams.team_id = NEW.match_home_team;
    
    IF NEW.match_restrictions != 3 AND NEW.match_stadium_id = ht_def_hs_id THEN
        CALL season_reservations_match(NEW.match_id, NEW.match_home_team);
    END IF;

END$

DELIMITER ;



/*create reservations for bought tickets, convert reservation type from available to ticket*/

DELIMITER $
CREATE TRIGGER ticket_reservation AFTER INSERT ON tickets 
FOR EACH ROW 
BEGIN

    UPDATE reservations SET reservation_fan_pass_id = NEW.ticket_fan_pass_id,reservation_ticket_number=NEW.ticket_number,reservation_type='TICKET'
    WHERE reservation_match_id = NEW.ticket_match_id AND reservation_seat_id = NEW.ticket_seat_id;

END$

DELIMITER ;