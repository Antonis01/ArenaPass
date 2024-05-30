ALTER TABLE reservations ADD reservation_ticket_type ENUM ('STUDENT','FULL','CHILD') DEFAULT NULL;

ALTER TABLE tickets ADD ticket_type ENUM ('STUDENT','FULL','CHILD') DEFAULT 'FULL';

DROP PROCEDURE IF EXISTS season_reservations_match;

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
        UPDATE reservations SET reservation_fan_pass_id = res_ticket_fan_pass_id,reservation_ticket_number=res_ticket_number,reservation_type='SEASON TICKET',reservation_ticket_type='FULL'
        WHERE reservation_match_id = r_match_id AND reservation_seat_id = res_ticket_seat_id;
        FETCH ticket_cursor INTO res_ticket_number, res_ticket_seat_id, res_ticket_fan_pass_id;
    END WHILE;
    CLOSE ticket_cursor;
END$

DELIMITER ;


DROP TRIGGER IF EXISTS ticket_reservation;

DELIMITER $
CREATE TRIGGER ticket_reservation AFTER INSERT ON tickets 
FOR EACH ROW 
BEGIN

    UPDATE reservations SET reservation_fan_pass_id = NEW.ticket_fan_pass_id,reservation_ticket_number=NEW.ticket_number,reservation_type='TICKET',reservation_date_time=NOW(),reservation_ticket_type=NEW.ticket_type
    WHERE reservation_match_id = NEW.ticket_match_id AND reservation_seat_id = NEW.ticket_seat_id;

END$

DELIMITER ;