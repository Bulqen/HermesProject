--
-- Skapar table till databasen hermes
--


DROP TABLE IF EXISTS oB;
DROP TABLE IF EXISTS overtimePay;
DROP TABLE IF EXISTS oT;
DROP TABLE IF EXISTS project_cart;
DROP TABLE IF EXISTS scheduled_activities;
DROP TABLE IF EXISTS project;
DROP TABLE IF EXISTS salary;
DROP TABLE IF EXISTS scheduled_pass;
DROP TABLE IF EXISTS time_report;
DROP TABLE IF EXISTS login;
DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS classification;
DROP TABLE IF EXISTS shift_pass;
DROP TABLE IF EXISTS pass;
DROP TABLE IF EXISTS shift;

CREATE TABlE classification (
    id INT NOT NULL,
    role VARCHAR(20),
    PRIMARY KEY(id)
);

CREATE TABLE pass (
    id INT AUTO_INCREMENT NOT NULL,
    start TIME,
    stop TIME,
    dag INT,
    PRIMARY KEY(id)
);
-- finns ingen bra typ för att använda dag!

CREATE TABLE shift (
    id INT AUTO_INCREMENT NOT NULL,
    shiftType VARCHAR(20),
    PRIMARY KEY (id)
);

CREATE TABLE shift_pass (
    shiftId INT NOT NULL,
    passId INT NOT NULL,
    FOREIGN KEY(shiftId) REFERENCES shift(id),
    FOREIGN KEY (passId) REFERENCES pass(id)
);

CREATE TABLE user (
    id INT AUTO_INCREMENT NOT NULL,
    classificationId INT NOT NULL,
    shiftId INT NOT NULL,
    hourlyPay INT,
    managerId INT,
    firstName VARCHAR(30),
    lastName VARCHAR(30),
    adress VARCHAR(20),
    phone VARCHAR(20),
    socialSecurityNumber VARCHAR(13),
    PRIMARY KEY (id),
    FOREIGN KEY (classificationId) REFERENCES shift(id)

);
-- ska nog lägga in mangerId Not null,

CREATE TABLE login (
    username VARCHAR(40),
    password VARCHAR(20),
    userId INT NOT NULL,
    PRIMARY KEY(username),
    FOREIGN KEY (userId) REFERENCES user(id)
);-- fixa kryptering med lösenord!

CREATE TABLE salary (
    userId INT NOT NULL,
    currentMonthlySalary INT,
    FOREIGN KEY (userId) REFERENCES user(id)
);

CREATE TABLE scheduled_pass (
    userId INT NOT NULL,
    start TIME,
    stop TIME,
    currentDate DATE,
    FOREIGN KEY (userId) REFERENCES user(id)

);

CREATE TABLE time_report (
    userId INT NOT NULL,
    inTime TIME,
    outTime TIME,
    absence VARCHAR(10),
    currentDate Date,
    FOREIGN KEY(userid) REFERENCES user(id)
);

CREATE TABLE project (
    id INT AUTO_INCREMENT NOT NULL,
    startDate DATE,
    endDate DATE,
    goals VARCHAR(100),
    budget INT,
    Status VARCHAR(20),
    PRIMARY KEY (id)
);

CREATE TABLE project_cart (
    userId INT NOT NULL,
    projectId INT NOT NULL,
    FOREIGN KEY(userId) REFERENCES user(id),
    FOREIGN KEY(projectId) REFERENCES project(id)
);

CREATE TABLE scheduled_activities (
    start TIME,
    stop TIME,
    currentDate DATE,
    projectId INT NOT NULL,
    FOREIGN KEY(projectId) REFERENCES project(id)
);
CREATE TABLE oT (
    id INT AUTO_INCREMENT NOT NULL,
    addon INT,
    PRIMARY KEY(id)
);
CREATE TABLE overtimePay (
    id INT AUTO_INCREMENT NOT NULL,
    type VARCHAR(20),
    status VARCHAR(10),
    userId INT NOT NULL,
    mangerId INT NOT NULL,
    PRIMARY KEY(id)
);-- blir kosntigt med userId och magerId, båda är typ FK, avaktar med FK

CREATE TABLE oB (
id INT AUTO_INCREMENT NOT NULL,
addon INT,
PRIMARY KEY(id)
);
