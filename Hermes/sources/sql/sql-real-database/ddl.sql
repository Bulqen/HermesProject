--
-- Skapar table till databasen hermes
--



DROP PROCEDURE IF EXISTS get_pass;
DROP PROCEDURE IF EXISTS stamp_in;
DROP PROCEDURE IF EXISTS stamp_out;
DROP PROCEDURE IF EXISTS report_abscence;
DROP PROCEDURE IF EXISTS user_create;
DROP PROCEDURE IF EXISTS get_usernames;
DROP PROCEDURE IF EXISTS login_create;
DROP PROCEDURE IF EXISTS get_user_info;
DROP PROCEDURE IF EXISTS add_scheduled_pass;
DROP PROCEDURE IF EXISTS edit_time_report;
DROP PROCEDURE IF EXISTS get_time_report;
DROP PROCEDURE IF EXISTS get_scheduled_pass;
DROP PROCEDURE IF EXISTS get_to_date_scheduled_pass;
DROP PROCEDURE IF EXISTS project_create;
DROP PROCEDURE IF EXISTS project_add_user;
DROP PROCEDURE IF EXISTS scheduled_activities_add;
DROP PROCEDURE IF EXISTS get_project_activities;
DROP PROCEDURE IF EXISTS get_project_activities_user;
DROP PROCEDURE IF EXISTS get_user_id_by_username;
DROP PROCEDURE IF EXISTS delete_time_report;
DROP PROCEDURE IF EXISTS add_time_report;
DROP PROCEDURE IF EXISTS generate_salaryslip;
DROP PROCEDURE IF EXISTS edit_user;
DROP PROCEDURE IF EXISTS delete_user;
DROP PROCEDURE IF EXISTS change_pw;
DROP PROCEDURE IF EXISTS get_all_users;
DROP PROCEDURE IF EXISTS get_time_report_intervall;
DROP PROCEDURE IF EXISTS get_users_by_manager;
DROP PROCEDURE IF EXISTS get_users_by_project;
DROP PROCEDURE IF EXISTS get_users_by_project_manager;
DROP PROCEDURE IF EXISTS apply_vacation_dates;
DROP PROCEDURE IF EXISTS generate_schedule;
DROP PROCEDURE IF EXISTS get_project_by_manager;
DROP PROCEDURE IF EXISTS get_users_not_in_project;
DROP PROCEDURE IF EXISTS remove_from_project;
DROP PROCEDURE IF EXISTS finish_Project;
DROP PROCEDURE IF EXISTS project_edit;
DROP PROCEDURE IF EXISTS get_project;
DROP PROCEDURE IF EXISTS edit_scheduled_activities;
DROP PROCEDURE IF EXISTS remove_scheduled_activities;

DROP FUNCTION IF EXISTS get_hours;

DROP TRIGGER IF EXISTS current_salary;

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
    FOREIGN KEY (classificationId) REFERENCES classification(id),
    FOREIGN KEY (shiftId) REFERENCES shift(id)


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
    id INT AUTO_INCREMENT NOT NULL,
    userId INT NOT NULL,
    inTime TIME,
    outTime TIME,
    absence VARCHAR(10),
    currentDate Date,
    comment VARCHAR(200),
    PRIMARY KEY (id),
    FOREIGN KEY(userid) REFERENCES user(id)
);

CREATE TABLE project (
    id INT AUTO_INCREMENT NOT NULL,
    startDate DATE,
    endDate DATE,
    goals VARCHAR(1000),
    budget INT,
    status VARCHAR(20),
    name VARCHAR(30),
    manager INT, -- not foreign cause don´t know which procedures that will be affected
    PRIMARY KEY (id)
);

CREATE TABLE project_cart (
    userId INT NOT NULL,
    projectId INT NOT NULL,
    FOREIGN KEY(userId) REFERENCES user(id),
    FOREIGN KEY(projectId) REFERENCES project(id)
);

CREATE TABLE scheduled_activities (
    id INT AUTO_INCREMENT NOT NULL,
    start TIME,
    description VARCHAR(200),
    stop TIME,
    currentDate DATE,
    projectId INT NOT NULL,
    PRIMARY KEY (id),
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
    uName VARCHAR(40)
)
BEGIN
    SELECT password FROM login WHERE username = uName;
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


DROP procedure if exists report_abscence;
DELIMITER ;;
CREATE PROCEDURE report_abscence (
    uId INT,
    absString VARCHAR(20),
    com VARCHAR(200)
)
BEGIN

    SET @stampToday = (SELECT MAX(currentDate) FROM time_report WHERE userId = uId AND currentDate = current_date);
    SET @stampIn = (SELECT MAX(inTime) FROM time_report where userId = uId AND currentDate = current_date);
    SET @id = (SELECT id FROM time_report WHERE userId = uId AND currentDate = @stampToday AND inTime = @stampToday);

    IF (@stampToday IS NULL) THEN
        INSERT INTO time_report (userId, currentDate, absence, comment)
        VALUES(uId, CURRENT_DATE(),absString, com);
    ELSE
        UPDATE time_report SET outTime = CURRENT_TIME(), absence = absString, comment = com WHERE (userId = uId AND outTime is NULL AND currentDate = @stampToday OR id = @id);
    END IF;


END ;;


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

    INSERT INTO salary (userId, currentMonthlySalary)
        VALUES (outId, 0);


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
    (SELECT  CONCAT(firstName, " ", lastName)from user as us WHERE u.managerId = us.id ) as managerName,
      u.hourlyPay, u.classificationId, u.shiftId
      FROM user as u
          INNER JOIN shift s on u.classificationId = s.id
          INNER JOIN classification c on c.id = u.classificationId WHERE u.id = uId;


END ;;

DELIMITER ;



DELIMITER ;;
CREATE PROCEDURE add_scheduled_pass(
    uId INT,
    startT TIME,
    stopT TIME,
    currentDateT DATE

)
BEGIN
    INSERT INTO scheduled_pass(userId, start, stop, currentDate)
      VALUES (uId, startT, stopT, currentDateT);

END ;;

DELIMITER ;

DELIMITER ;;
CREATE PROCEDURE edit_time_report(
    startT TIME,
    stopT TIME,
    currentDateT DATE,
    com VARCHAR(200),
    tId INT

)
BEGIN
    UPDATE time_report SET inTime = startT, outTime = stopT, currentDate = currentDateT,
        comment = com WHERE id = tId;
END ;;

DELIMITER ;

DELIMITER ;;
CREATE PROCEDURE get_time_report(
   uId INT

)
BEGIN
   SELECT *, Round(get_hours(outTime)-get_hours(inTime),2) as hours FROM time_report WHERE userId = uId;
END ;;

DELIMITER ;

DELIMITER ;;

CREATE PROCEDURE get_scheduled_pass(
   uId INT

)
BEGIN
   SELECT * FROM scheduled_pass WHERE userId = uId;
END ;;

DELIMITER ;

DELIMITER ;;
CREATE PROCEDURE get_to_date_scheduled_pass(
   uId INT

)
BEGIN
    SELECT * FROM scheduled_pass WHERE userId = uId AND currentDate >= current_date();
END ;;

DELIMITER ;


DELIMITER ;;

CREATE TRIGGER current_salary
    AFTER UPDATE
    ON time_report FOR EACH ROW
    BEGIN
    set @a = (SELECT hourlyPay from user WHERE id = NEW.userId);

    SET @hours = (SELECT SUM((timediff(outTime, inTime)/3600)) FROM time_report WHERE userId = NEW.userId);

    SET @salar = ROUND(@a * @hours);

    UPDATE salary SET currentMonthlySalary = @salar WHERE userId = NEW.userId;



    END ;;

DELIMITER ;


DELIMITER ;;
CREATE PROCEDURE project_create(
   start Date,
   stop Date,
   goal VARCHAR(1000),
   budg INT,
   Status VARCHAR(20),
   namn VARCHAR(30),
   man INT

)
BEGIN

    Insert into project (startDate, endDate, goals, budget, status, manager, name)
        VALUES(start, stop, goal, budg, Status, man, namn);

END ;;

DELIMITER ;


DELIMITER ;;
CREATE PROCEDURE project_add_user(
   uId INT,
   pId INT
)
BEGIN

    INSERT INTO project_cart (userId, projectId)
        VALUES(uId, pId);

END ;;

DELIMITER ;


DELIMITER ;;
CREATE PROCEDURE scheduled_activities_add(
   pId INT,
   starts TIME,
   stops TIME,
   currDate Date,
   descrip VARCHAR(200)
)
BEGIN

    INSERT INTO scheduled_activities (projectId, start, stop, currentDate, description)
        VALUES (pId, starts, stops, currDate, descrip);

END ;;

DELIMITER ;

DELIMITER ;;
CREATE PROCEDURE get_project_activities(
   pId INT

)
BEGIN
    SELECT * FROM scheduled_activities WHERE projectId = pId;
END ;;

DELIMITER ;

DELIMITER ;;
CREATE PROCEDURE get_project_activities_user(
   uId INT

)
BEGIN

SELECT DISTINCT userId ,sa.projectId, sa.start, sa.stop, sa.currentDate, sa.id, sa.description FROM scheduled_activities as sa
    INNER JOIN project_cart as pc
        ON sa.projectId = pc.projectId WHERE pc.userId = uId;
END ;;

DELIMITER ;

DELIMITER ;;
CREATE PROCEDURE get_user_id_by_username(
   uName VARCHAR(20)

)
BEGIN


SELECT u.id FROM user as u INNER JOIN login as l on u.id = l.userId WHERE l.username = uName;

END ;;

DELIMITER ;


DELIMITER ;;
CREATE PROCEDURE delete_time_report(
  tId INT
)
BEGIN


DELETE FROM time_report WHERE id = tId;

END ;;

DELIMITER ;

DELIMITER ;;
CREATE PROCEDURE add_time_report(
  uId INT,
  start TIME,
  stopp TIME,
  abse VARCHAR(20),
  curDate DATE,
  com VARCHAR(200)
)
BEGIN


INSERT INTO time_report(userId, inTime, outTime, absence, currentDate, comment)
    VALUES (uId, start, stopp, abs, curDate, com);

END ;;

DELIMITER ;

DROP FUNCTION IF EXISTS get_hours;
DELIMITER ;;
CREATE FUNCTION get_hours(
 tid TIME
)
RETURNS DOUBLE
  NOT DETERMINISTIC NO SQL
  BEGIN

    RETURN ROUND(HOUR(tid) + MINUTE(tid)/60, 2);

  end ;;

DELIMITER ;


DELIMITER ;;
CREATE PROCEDURE generate_salaryslip(
    uId INT

)
BEGIN

  DECLARE today DATE;
  DECLARE hourlyP INT;
  DECLARE monthYear VARCHAR(25);
  DECLARE totalHours DOUBLE;
  DECLARE pay INT;

  set today  = current_date();



  IF(DAY(today) <= 25) THEN
    SET monthYear = DATE_FORMAT(DATE_SUB(today, INTERVAL 1 MONTH), "%Y-%m" );

  ELSE
    SET monthYear = DATE_FORMAT(today, "%Y-%m");

  end if ;


  SET hourlyP = (SELECT hourlyPay FROM user WHERE id = uId );
  SET totalHours = (SELECT  SUM( CASE
                WHEN outTime is null
                    then 0
                  ELSE
                   mod(get_hours(outTime) - get_hours(inTime) + 24, 24)
            END ) as 'lon'
    FROM time_report as tr INNER JOIN user as u ON tr.userId = u.id
              WHERE DATE_FORMAT(currentDate,"%Y-%m") = '2019-03' AND u.id =uId);
  SET pay = hourlyP * totalHours;


  SELECT totalHours, hourlyP, monthYear, pay, CONCAT(u.firstName, ' ', u.lastName) as namn FROM user as u WHERE id = uId;



END ;;

DELIMITER ;

DELIMITER ;;
CREATE PROCEDURE edit_user(
  uId INT,
  cId INT,
  shId INT,
  hourPay INT,
  manId INT,
  fName VARCHAR(30),
  lName VARCHAR(30),
  adrs VARCHAR(20),
  phne VARCHAR(20),
  sSecureNumber VARCHAR(13)
)
BEGIN


UPDATE user SET classificationId = cId,
                shiftId = shId,
                hourlyPay = hourPay,
                managerId = manId,
                firstName = fName,
                lastName = lName,
                adress = adrs,
                phone = phne,
                socialSecurityNumber = sSecureNumber
                    WHERE id = uId;
END ;;

DELIMITER ;

DELIMITER ;;
CREATE PROCEDURE delete_user(
  uId INT
)
BEGIN

    DELETE FROM login WHERE userId = uId;
    DELETE FROM salary WHERE userId = uId;
    DELETE FROM scheduled_pass WHERE userId = uId;
    DELETE FROM time_report WHERE userId = uId;
    DELETE FROM project_cart WHERE userId = uId;
    DELETE FROM user WHERE id = uId;

END ;;

DELIMITER ;

DELIMITER ;;
CREATE PROCEDURE change_pw(
  uId INT,
  nePw VARCHAR(20)
)
BEGIN

  SET @uName = (SELECT username FROM user as u INNER JOIN login l on u.id = l.userId WHERE u.id = uId);

  UPDATE login SET password = nePw WHERE username = @uName;


END ;;

DELIMITER ;


DELIMITER ;;
CREATE PROCEDURE get_all_users(

)
BEGIN

SELECT u.id, CONCAT(firstName, " ", lastName) as name, adress, phone, socialSecurityNumber, s.shiftType, c.role, managerId,
    (SELECT  CONCAT(firstName, " ", lastName)from user as us WHERE u.managerId = us.id ) as managerName,
      u.hourlyPay, u.classificationId, u.shiftId
      FROM user as u
          INNER JOIN shift s on u.classificationId = s.id
          INNER JOIN classification c on c.id = u.classificationId;

END ;;

DELIMITER ;

DELIMITER ;;
CREATE PROCEDURE get_time_report_intervall(
  uId INT,
  startt DATE,
  stopp DATE
)
BEGIN

SELECT *, ROUND(get_hours(outTime)-get_hours(inTime),2) as hours FROM time_report WHERE userId = uId AND(currentDate BETWEEN startt AND stopp);

END ;;

DELIMITER ;

DELIMITER ;;
CREATE PROCEDURE  get_users_by_manager(
  manId INT
)
BEGIN

  SELECT u.id, CONCAT(firstName, " ", lastName) as name, adress, phone, socialSecurityNumber, s.shiftType, c.role, managerId,
    (SELECT  CONCAT(firstName, " ", lastName)from user as us WHERE u.managerId = us.id ) as managerName,
      u.hourlyPay, u.classificationId, u.shiftId
      FROM user as u
          INNER JOIN shift s on u.classificationId = s.id
          INNER JOIN classification c on c.id = u.classificationId WHERE u.managerId = manId;


END ;;

DELIMITER ;



DELIMITER ;;
CREATE PROCEDURE  get_users_by_project(
  proId INT

)
BEGIN

  SELECT u.id, CONCAT(firstName, " ", lastName) as name, adress, phone, socialSecurityNumber, s.shiftType, c.role, managerId,
    (SELECT  CONCAT(firstName, " ", lastName)from user as us WHERE u.managerId = us.id ) as managerName,
      u.hourlyPay, u.classificationId, u.shiftId
      FROM user as u
          INNER JOIN shift s on u.classificationId = s.id
          INNER JOIN classification c on c.id = u.classificationId
          INNER JOIN project_cart cart on u.id = cart.userId WHERE cart.projectId = proId;


END ;;

DELIMITER ;

DROP PROCEDURE IF EXISTS get_users_by_project_manager;
DELIMITER ;;
CREATE PROCEDURE  get_users_by_project_manager(
  manId INT

)
BEGIN

SELECT u.id, CONCAT(firstName, " ", lastName) as name, adress, phone, socialSecurityNumber, s.shiftType, c.role, managerId,
    (SELECT  CONCAT(firstName, " ", lastName)from user as us WHERE u.managerId = us.id ) as managerName,
      u.hourlyPay, u.classificationId, u.shiftId
      FROM user as u
          INNER JOIN shift s on u.classificationId = s.id
          INNER JOIN classification c on c.id = u.classificationId
          INNER JOIN project_cart cart on u.id = cart.userId
          INNER JOIN project p on cart.projectId = p.id WHERE p.manager = manId;


END ;;

DELIMITER ;

DELIMITER ;;
CREATE PROCEDURE  apply_vacation_dates(
  starts DATE,
  stops DATE,
  com VARCHAR(200),
  uId INT
)
BEGIN


  WHILE starts <= stops DO

  INSERT INTO time_report(userId, absence, currentDate, comment)
    VALUES (uid, 'vacation',starts, com);
    SET starts = starts + INTERVAL 1 DAY;


END WHILE;


END ;;


DELIMITER ;;
CREATE PROCEDURE generate_schedule(
  uId INT
)
BEGIN



  SET @shift = (SELECT shiftId FROM user WHERE id = uId);

  IF @shift = 1 THEN
      SELECT "16:00" as starts, "23:00" as ends, 3 as start_day, 7 as end_day, "2-shift" as type;
    ELSEIF @shift = 2 THEN
      SELECT "08:00" as starts, "17:00" as ends, 1 as start_day, 5 as end_day, "förmiddag" as type;
    ELSEIF @shift = 3 THEN
      SELECT "18:00" as starts, "03:00" as ends, 1 as start_day, 5 as end_day, "natt" as type;
    ELSEIF @shift = 4 THEN
      SELECT "08:00" as starts, "17:00" as ends, 6 as start_day, 7 as end_day, "helg" as type;
    ELSE
      SELECT "08:00" as starts, "17:00" as ends, 1 as start_day, 7 as end_day, "default" as type;
  END IF ;


END ;;

DELIMITER ;

DELIMITER ;;
CREATE PROCEDURE  get_project_by_manager(
  uId INT
)
BEGIN



 SELECT * From project WHERE manager = uId;


END ;;

DELIMITER ;


DELIMITER ;;
CREATE PROCEDURE  get_users_not_in_project(
  pId INT
)
BEGIN

  set @manId = (SELECT manager FROM project WHERE id = pId);



SELECT u.id, CONCAT(u.firstName, " ", u.lastName) as name
    FROM user as u LEFT JOIN (SELECT us.id, CONCAT(us.firstName, " ", us.lastName) as name, us.adress, us.phone, us.socialSecurityNumber
      FROM user as us
          INNER JOIN project_cart cart on us.id = cart.userId WHERE cart.projectId = pID) as pc ON u.id = pc.id WHERE (pc.id IS NULL AND u.id != @manId );



END ;;
DELIMITER ;

DELIMITER ;;
CREATE PROCEDURE  remove_from_project(
  pId INT,
  uId INT
)
BEGIN

 DELETE FROM project_cart WHERE userId = uId AND projectId = pId;



END ;;
DELIMITER ;



DELIMITER ;;
CREATE PROCEDURE  finish_Project(
  pId INT
)
BEGIN

 UPDATE project SET manager = -1 where id = pId;



END ;;
DELIMITER ;


DELIMITER ;;
CREATE PROCEDURE project_edit(
   start Date,
   stop Date,
   goal VARCHAR(1000),
   budg INT,
   Status VARCHAR(20),
   namn VARCHAR(30),
   man INT,
  pId INT

)
BEGIN

    UPDATE project  SET startDate = start, endDate = stop, goals = goal, budget = budg, status = Status, manager = man, name = namn
        WHERE id = pId;

END ;;

DELIMITER ;

DELIMITER ;;
CREATE PROCEDURE get_project(
  pId INT

)
BEGIN

  SELECT * FROM project WHERE id = pId;

END ;;

DELIMITER ;


DELIMITER ;;
CREATE PROCEDURE edit_scheduled_activities(
  scheduleId INT,
  starts TIME,
  stops TIME,
  datum DATE,
  pId INT,
  descrip VARCHAR(200)


)
BEGIN

  UPDATE scheduled_activities SET start = starts, stop = stops, currentDate = datum, projectId =pId, description = descrip
      WHERE id = scheduleId;

END ;;

DELIMITER ;

DELIMITER ;;
CREATE PROCEDURE remove_scheduled_activities(
  scheduleId INT


)
BEGIN

  DELETE FROM scheduled_activities WHERE id = scheduleId;

END ;;

DELIMITER ;
