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


--
-- procedures
--

--
-- Input är username, den returnerar password för det usernamet
--
DELIMITER ;;
CREATE PROCEDURE get_pass(
    uId INT
)
BEGIN
    SELECT password FROM login WHERE = uId
END ;;

DELIMITER ;


--
-- Input är userId, den stämplar in användaren at current time
--

DELIMITER ;;
CREATE PROCEDURE stamp_in(
    uId INT
)
BEGIN

    INSERT INTO time_report (userId, inTime, currentDate)
        VALUES(uId, CURRENT_TIME(), CURRENT_DATE());

END ;;

DELIMITER ;

--
-- Input är userId, den stämplar ut användaren at current time, (kollar var out = null)
--


DELIMITER ;;
CREATE PROCEDURE stamp_out (
    uId INT
)
BEGIN

UPDATE time_report SET outTime = CURRENT_TIME() WHERE (userId = uId AND outTime is NULL);

END ;;

DELIMITER ;


--
-- Input är userId och anledning av frånvaro i form av string
-- (t.ex sjuk, ledig osv) denna funktione stämplar in dagen som abscence med
--


DELIMITER ;;
CREATE PROCEDURE report_abscence (
    uId INT,
    absString VARCHAR(20)
)
BEGIN

    SET @stampToday = (SELECT currentDate FROM time_report WHERE userId = uId);

    IF (@stampToday IS NULL) THEN
        INSERT INTO time_report (userId, currentDate, absence)
        VALUES(uId, CURRENT_DATE(),absString );
    ELSE
        UPDATE time_report SET outTime = CURRENT_TIME(), absence = absString WHERE (userId = uId AND outTime is NULL);
    END IF;


END ;;

DELIMITER ;

--
-- input är allt som behövs för en användare (obs shiftId och classId måste finnas (FK))
-- användare skapas och returnerar dess userId
--


DELIMITER ;;
CREATE PROCEDURE user_create(
    IN cId INT,
    IN shId INT,
    IN hourPay INT,
    IN manId INT,
    IN fName VARCHAR(30),
    IN lName VARCHAR(30),
    IN adrs VARCHAR(20),
    IN phne VARCHAR(20),
    IN sSecureNumber VARCHAR(13),
    INOUT outId INT

)
BEGIN
    INSERT INTO user (classificationId, shiftId, hourlyPay, managerId, firstName, lastName, adress, phone, socialSecurityNumber)
        VALUES(cId, shId, hourPay, manId, fName, lName, adrs, phne, sSecureNumber);

    SET outId = (SELECT id FROM user ORDER BY id DESC LIMIT 1);


END ;;

DELIMITER ;


--
-- hämtar alla nuvarande usernames,
--
DELIMITER ;;
CREATE PROCEDURE get_usernames()
BEGIN
    SELECT username FROM login;

END ;;

DELIMITER ;


--
--
--


DELIMITER ;;
CREATE PROCEDURE login_create(
    uId INT,
    userIn VARCHAR(40),
    passIn VARCHAR(20)

)
BEGIN

    INSERT INTO login (userid, username, password)
        VALUES(uId, userIn, passIn);



END ;;

DELIMITER ;


DELIMITER ;;
CREATE PROCEDURE get_user_info(
    uId INT

)
BEGIN

    SELECT u.id, CONCAT(firstName, " ", lastName) as name, adress, phone, socialSecurityNumber, s.shiftType, c.role, managerId,
    (SELECT  CONCAT(firstName, " ", lastName)from user as us WHERE u.managerId = us.id ) as managerName
        FROM user as u
            INNER JOIN shift s on u.classificationId = s.id
            INNER JOIN classification c on c.id = u.classificationId WHERE u.id = uId;


END ;;

DELIMITER ;
