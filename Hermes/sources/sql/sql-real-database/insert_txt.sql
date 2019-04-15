LOAD DATA LOCAL INFILE 'data_shift.txt'
INTO TABLE shift
CHARSET utf8
FIELDS
    TERMINATED BY ','
    ENCLOSED BY '"'
LINES
    TERMINATED BY '\r\n'
IGNORE 1 LINES
(shiftType)
;

LOAD DATA LOCAL INFILE 'data_classification.txt'
INTO TABLE classification
CHARSET utf8
FIELDS
    TERMINATED BY ','
    ENCLOSED BY '"'
LINES
    TERMINATED BY '\r\n'
IGNORE 1 LINES
(id, role)
;

LOAD DATA LOCAL INFILE 'data_user.txt'
INTO TABLE user
CHARSET utf8
FIELDS
    TERMINATED BY ','
    ENCLOSED BY '"'
LINES
    TERMINATED BY '\r\n'
IGNORE 1 LINES
(classificationId, shiftId, hourlyPay, managerId, firstName, lastName, adress,
phone, socialSecurityNumber)
;

LOAD DATA LOCAL INFILE 'data_login.txt'
INTO TABLE login
CHARSET utf8
FIELDS
    TERMINATED BY ','
    ENCLOSED BY '"'
LINES
    TERMINATED BY '\r\n'
IGNORE 1 LINES
(username, password, userId)
;


LOAD DATA LOCAL INFILE 'data_salary.txt'
INTO TABLE salary
CHARSET utf8
FIELDS
    TERMINATED BY ','
    ENCLOSED BY '"'
LINES
    TERMINATED BY '\r\n'
IGNORE 1 LINES
(userId, currentMonthlySalary)
;

LOAD DATA LOCAL INFILE 'data_time_report.txt'
INTO TABLE time_report
CHARSET utf8
FIELDS
    TERMINATED BY ','
    ENCLOSED BY '"'
LINES
    TERMINATED BY '\r\n'
IGNORE 1 LINES
(userId, inTime, outTime, currentDate)
;
