DROP TABLE IF EXISTS MOVIES;

CREATE TABLE MOVIES (
    id INT AUTO_INCREMENT PRIMARY KEY,
    year INT NOT NULL,
    title VARCHAR(255) NOT NULL,
    studios VARCHAR(255) NOT NULL,
    producers VARCHAR(255) NOT NULL,
    winner VARCHAR(3) NOT NULL
);