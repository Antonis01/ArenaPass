CREATE TABLE fan_info (
  name VARCHAR(20),
  password VARCHAR(20),
  role ENUM('fan', 'admin') DEFAULT 'fan'
);

INSERT INTO fan_info VALUES
("Antonis", "1234", "fan"),
("Spiros", "1111", "fan"),
("Lambros", "2222", "fan"),
("Valantis", "3333", "fan"),
("Mitsios", "4444", "fan"),
("admin", "admin", "admin");
