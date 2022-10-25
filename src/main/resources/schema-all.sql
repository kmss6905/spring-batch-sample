DROP TABLE people IF EXISTS;

CREATE TABLE people
(
    id  INT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(20),
    last_name  VARCHAR(20)
);