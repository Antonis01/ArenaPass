CREATE TABLE citizen_records (
    citizen_id_number INT(8) NOT NULL,
    citizen_legal_name VARCHAR(30) NOT NULL,
    citizen_legal_surname VARCHAR(30) NOT NULL,
    citizen_id_expiration_date DATE NOT NULL,
    citizen_id_dob DATE NOT NULL, /*dob=date of birth*/
    citizen_status ENUM ('VERIFIED','BANNED') NOT NULL,
    PRIMARY KEY (citizen_id_number)
);

INSERT citizen_records (citizen_legal_name,citizen_legal_surname,citizen_id_number,citizen_id_expiration_date,citizen_id_dob,citizen_status) VALUES
('John','Doe','12345678','2030-01-01','1990-01-01','VERIFIED'),
('Jane','Smith','87654321','2030-01-01','1991-02-02','VERIFIED'),
('Alice','Johnson','11223344','2030-01-01','1992-03-03','VERIFIED'),
('Bob','Brown','44332211','2030-01-01','1993-04-04','VERIFIED'),
('Charlie','Davis','55667788','2030-01-01','1994-05-05','VERIFIED'),
('tik','tok','34892605','2030-01-01','1990-01-01','BANNED'),
('John','Smith','87654321','2030-02-02','2003-11-11','BANNED');