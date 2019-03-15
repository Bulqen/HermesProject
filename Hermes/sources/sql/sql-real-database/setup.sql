-- Skapa databas
-- CREATE DATABASE hermes;
CREATE DATABASE IF NOT EXISTS hermes;

-- Välj vilken databas du vill använda
USE hermes;



-- Skapa en användare user med lösenorder pass och ge tillgång oavsett
-- hostnamn.
CREATE USER IF NOT EXISTS 'user'@'%'
    IDENTIFIED BY 'pass'
;





-- Ge användaren alla rättigheter på en specifk databas.
GRANT ALL PRIVILEGES
    ON hermes.*
    TO 'user'@'%'
;

-- kan läsa in lokala filer
SET GLOBAL local_infile = 1;
-- standard för tecken processing, åäö funkar t.ex
SET NAMES 'utf8';
-- log av stored procedures
SET GLOBAL log_bin_trust_function_creators = 1;
-- timezone fix
SET GLOBAL time_zone = '+1:00';
