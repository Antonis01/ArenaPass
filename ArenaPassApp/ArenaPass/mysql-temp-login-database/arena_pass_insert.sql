-- Insert into stadiums
INSERT INTO stadiums (stadium_name, stadium_max_capacity) VALUES 
('Stadium A', 50000),
('Stadium B', 40000);

-- Insert into teams
INSERT INTO teams (team_name, team_def_home_stadium_id) VALUES 
('Team 1', 1),
('Team 2', 1),
('Team 3', 2),
('Team 4', 2);

-- Insert into matches
INSERT INTO matches (match_stadium_id, match_home_team, match_away_team, match_date, match_time, match_ht_max_capacity, match_at_max_capacity, match_restrictions) VALUES 
(1, 1, 2, '2024-05-20', '15:00:00', 25000, 25000, 'NO RESTRICTION'),
(2, 3, 4, '2024-05-21', '18:00:00', 20000, 20000, 'NO RESTRICTION');

-- Insert into fans
INSERT INTO fans (fan_username, fan_password, fan_legal_name, fan_legal_surname, fan_citizen_id_number, fan_citizen_id_expiration_date, fan_citizen_id_dob, fan_pass_id, fan_acount_status) VALUES 
('fan1', 'pass1', 'John', 'Doe', 12345678, '2030-01-01', '1990-01-01', 1001, 'VERIFIED'),
('fan2', 'pass2', 'Jane', 'Smith', 87654321, '2030-01-01', '1991-02-02', 1002, 'VERIFIED'),
('fan3', 'pass3', 'Alice', 'Johnson', 11223344, '2030-01-01', '1992-03-03', 1003, 'VERIFIED'),
('fan4', 'pass4', 'Bob', 'Brown', 44332211, '2030-01-01', '1993-04-04', 1004, 'VERIFIED'),
('fan5', 'pass5', 'Charlie', 'Davis', 55667788, '2030-01-01', '1994-05-05', 1005, 'VERIFIED');

-- Insert into seats
-- Seats for Stadium A (10 entries, 2 sections)
INSERT INTO seats (seat_stadium_id, seat_section, seat_number) VALUES 
(1, 'A1', '001'),
(1, 'A1', '002'),
(1, 'A1', '003'),
(1, 'A1', '004'),
(1, 'A1', '005'),
(1, 'A2', '001'),
(1, 'A2', '002'),
(1, 'A2', '003'),
(1, 'A2', '004'),
(1, 'A2', '005');

-- Seats for Stadium B (10 entries, 3 sections)
INSERT INTO seats (seat_stadium_id, seat_section, seat_number) VALUES 
(2, 'B1', '001'),
(2, 'B1', '002'),
(2, 'B1', '003'),
(2, 'B1', '004'),
(2, 'B1', '005'),
(2, 'B2', '001'),
(2, 'B2', '002'),
(2, 'B2', '003'),
(2, 'B2', '004'),
(2, 'B3', '001');

-- Insert into tickets
INSERT INTO tickets (ticket_seat_id, ticket_match_id, ticket_fan_pass_id) VALUES 
-- Tickets for Match 1
(1, 1, 1001),
(2, 1, 1002),
-- Tickets for Match 2
(11, 2, 1003),
(12, 2, 1004),
(13, 2, 1005);

--THIS INSERT WAS CREATED TO TEST HISTORY/QUERY FUNCTIONALITIES FOR USERS WITH MORE THAN 1 TICKET
INSERT INTO tickets (ticket_seat_id, ticket_match_id, ticket_fan_pass_id) VALUES
(17, 2, 1001);

--THESE INSERTS ARE CREATED AFTER THE CREATION OF THE TRIGGER 'check_ban_status' TO TEST IF IT'S WORKING -- VERIFIED TO BE WORKING THROUGH 'select * from tickets' AND 'ERROR 1644 (45000): Fan is not qualified to buy ticket!'

INSERT INTO fans (fan_username, fan_password, fan_legal_name, fan_legal_surname, fan_citizen_id_number, fan_citizen_id_expiration_date, fan_citizen_id_dob, fan_pass_id, fan_acount_status) VALUES 
('fan6', 'pass6', 'tik', 'tok', 34892605, '2030-01-01', '1990-01-01', 1006, 'BANNED');

INSERT INTO tickets (ticket_seat_id, ticket_match_id, ticket_fan_pass_id) VALUES
(20, 2, 1006);

INSERT INTO tickets (ticket_seat_id, ticket_match_id, ticket_fan_pass_id) VALUES
(20, 2, 1005);

--THESE INSERTS ARE CREATED AFTER THE CREATION OF THE TRIGGER 'check_double_ticket' TO TEST IF IT'S WORKING

--DELETE MANUALLY ABOVE INSERT BECAUSE IT VIOLATES TRIGGER 'check_double_ticket'. WILL BE INSERTED AGAIN TO CHECK 'check_double_ticket' FUNCTIONALITY
DELETE FROM tickets WHERE ticket_number = 7;

INSERT INTO tickets (ticket_seat_id, ticket_match_id, ticket_fan_pass_id) VALUES
(20, 2, 1005);

--THESE INSERTS ARE CREATED AFTER THE CREATION OF THE TRIGGER 'check_same_team' TO TEST IF IT'S WORKING

INSERT INTO matches (match_stadium_id, match_home_team, match_away_team, match_date, match_time, match_ht_max_capacity, match_at_max_capacity, match_restrictions) VALUES 
(1, 3, 3, '2024-07-20', '15:00:00', 25000, 25000, 'NO RESTRICTION');

INSERT INTO matches (match_stadium_id, match_home_team, match_away_team, match_date, match_time, match_ht_max_capacity, match_at_max_capacity, match_restrictions) VALUES 
(1, 1, 4, '2024-07-20', '15:00:00', 25000, 25000, 'NO RESTRICTION');


