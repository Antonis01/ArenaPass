CREATE TABLE feedback_forms(
    feedback_id INT(8) NOT NULL AUTO_INCREMENT,
    feedback_category ENUM ('BUG','TYPO ERROR','OTHER') NOT NULL,
    feedback_body TEXT NOT NULL,
    feedback_status ENUM ('PENDING','RESOLVED') DEFAULT 'PENDING',
    PRIMARY KEY (feedback_id)
);

INSERT INTO feedback_forms (feedback_category,feedback_body) VALUES
('BUG','When i go to buy a ticket at the select seat section the program doesnt proceed when i press the button'),
('BUG','The program does not display any seats to select, available or not...NOTHING PLEASE FIX'),
('TYPO ERROR','H OMADA ONOMAZETAI PAS LAMIA OXI LAMIA... ELEOS. FTIAXTE TO'),
('OTHER','I have canceled my reservation 2 weeks ago, I have not received my money back yet, I didnt know who to contact,please help me');