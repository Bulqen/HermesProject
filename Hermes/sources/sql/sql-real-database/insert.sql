LOAD DATA LOCAL INFILE 'data_shift.csv'
INTO TABLE shift
CHARSET utf8
FIELDS
    TERMINATED BY ','
    ENCLOSED BY '"'
LINES
    TERMINATED BY '\n'
IGNORE 1 LINES
(shiftType)
;

LOAD DATA LOCAL INFILE 'data_classification.csv'
INTO TABLE classification
CHARSET utf8
FIELDS
    TERMINATED BY ','
    ENCLOSED BY '"'
LINES
    TERMINATED BY '\n'
IGNORE 1 LINES
(id, role)
;

LOAD DATA LOCAL INFILE 'data_user.csv'
INTO TABLE user
CHARSET utf8
FIELDS
    TERMINATED BY ','
    ENCLOSED BY '"'
LINES
    TERMINATED BY '\n'
IGNORE 1 LINES
(classificationId, shiftId, hourlyPay, managerId, firstName, lastName, adress,
phone, socialSecurityNumber)
;