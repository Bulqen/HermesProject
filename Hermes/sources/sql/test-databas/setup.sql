-- Skapa databas
-- CREATE DATABASE skolan;
CREATE DATABASE IF NOT EXISTS eshop;

-- Välj vilken databas du vill använda
USE eshop;



-- Skapa en användare user med lösenorder pass och ge tillgång oavsett
-- hostnamn.
CREATE USER IF NOT EXISTS 'user'@'%'
    IDENTIFIED BY 'pass'
;





-- Ge användaren alla rättigheter på en specifk databas.
GRANT ALL PRIVILEGES
    ON eshop.*
    TO 'user'@'%'
;

SET GLOBAL local_infile = 1;
SET NAMES 'utf8';
SET GLOBAL log_bin_trust_function_creators = 1;



-- Visa vad en användare kan göra mot vilken databas.


-- -- Visa för nuvarande användare
-- SHOW GRANTS FOR CURRENT_USER;
