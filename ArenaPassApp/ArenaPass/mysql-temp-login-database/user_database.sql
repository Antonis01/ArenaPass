use arenapass;

CREATE TABLE fans (
    fan_acount_id INT(9) NOT NULL AUTO_INCREMENT,
    fan_username VARCHAR(30) NOT NULL,
    fan_password VARCHAR(30) NOT NULL,
    fan_legal_name VARCHAR(30) NOT NULL,
    fan_legal_surname VARCHAR(30) NOT NULL,
    fan_citizen_id_number INT(8) NOT NULL,
    fan_citizen_id_expiration_date DATE NOT NULL,
    fan_citizen_id_dob DATE NOT NULL, /*dob=date of birth*/
    fan_registration_date DATE DEFAULT(CURRENT_DATE()),
    fan_pass_id INT(9) NOT NULL,
    fan_acount_status ENUM ('VERIFIED','BANNED','PENDING') NOT NULL,
    PRIMARY KEY (fan_acount_id),
    UNIQUE (fan_pass_id),
    UNIQUE (fan_username),
    UNIQUE (fan_citizen_id_number)
);

CREATE TABLE tickets (
    ticket_number INT(9) NOT NULL AUTO_INCREMENT,
    ticket_seat_section VARCHAR(4) NOT NULL,
    ticket_seat_number VARCHAR(4) NOT NULL,
    ticket_match_id INT(9) NOT NULL,
    ticket_fan_pass_id INT(9) NOT NULL,
    ticket_purchase_datetime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (ticket_number),
    UNIQUE (ticket_section,ticket_seat),
    CONSTRAINT SEAT_SECTION FOREIGN KEY (ticket_seat_section) REFERENCES seats(seat_section),
    CONSTRAINT SEAT_NUMBER FOREIGN KEY (ticket_seat_number) REFERENCES seats(seat_number),
    CONSTRAINT MATCH FOREIGN KEY (ticket_match_id) REFERENCES matches(match_id),
    CONSTRAINT FAN FOREIGN KEY (ticket_fan_pass_id) REFERENCES fans(fan_pass_id)
);

CREATE TABLE seats(
    seat_id INT(8) NOT NULL AUTO_INCREMENT,
    seat_stadium_id INT(3) NOT NULL,
    seat_section VARCHAR(4) NOT NULL,
    seat_number VARCHAR(4) NOT NULL,
    PRIMARY KEY (seat_id),
    UNIQUE (seat_section,seat_number),
    CONSTRAINT SEAT_STADIUM FOREIGN KEY (seat_stadium_id) REFERENCES stadiums(stadium_id)
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

CREATE TABLE stadiums(
    stadium_id INT(3) NOT NULL AUTO_INCREMENT,
    stadium_name VARCHAR(30) NOT NULL,
    stadium_max_capacity INT(6) NOT NULL,
    PRIMARY KEY (stadium_id)
);

CREATE TABLE teams (
    team_id INT(3) NOT NULL AUTO_INCREMENT,
    team_name VARCHAR(30) NOT NULL,
    team_def_home_stadium_id INT(3),
    team_points INT(3) NOT NULL DEFAULT 0,
    PRIMARY KEY (team_id),
    UNIQUE (team_name),
    CONSTRAINT TEAM_STADIUM FOREIGN KEY (team_def_home_stadium_id) REFERENCES stadiums(stadium_id)
);
