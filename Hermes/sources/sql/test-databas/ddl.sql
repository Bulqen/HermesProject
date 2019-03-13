--
-- Create scheme for database eshop.
-- By Justad for course databas.


DROP PROCEDURE IF EXISTS show_lager;
DROP PROCEDURE IF EXISTS delete_produkt;
DROP PROCEDURE IF EXISTS edit_produkt;
DROP PROCEDURE IF EXISTS show_lager_filter_Opt;
DROP PROCEDURE IF EXISTS logg_limit;
DROP PROCEDURE IF EXISTS show_categories;
DROP PROCEDURE IF EXISTS show_produkt_info;
DROP PROCEDURE IF EXISTS add_produkt;
DROP PROCEDURE IF EXISTS show_produkt;

DROP view if exists antal_orderrader;
DROP VIEW IF EXISTS produkt_info;
DROP VIEW IF EXISTS lager_info;

DROP TRIGGER IF EXISTS produktlogg_create;
DROP TRIGGER IF EXISTS produktlogg_update;
DROP TRIGGER IF EXISTS produktlogg_delete;

DROP TABLE IF EXISTS orderrad;
DROP TABLE IF EXISTS produktlista;
DROP TABLE IF EXISTS plocklista;
DROP TABLE IF EXISTS lager;
DROP TABLE IF EXISTS plats;
DROP TABLE IF EXISTS produktlogg;
DROP TABLE IF EXISTS produkt;
DROP TABLE IF EXISTS kategori;
DROP TABLE IF EXISTS faktura;
DROP TABLE IF EXISTS logg;
DROP TABLE IF EXISTS ordrar;
DROP TABLE IF EXISTS kund;


Create TABLE kund (
    id INT AUTO_INCREMENT NOT NULL,
    fornamn VARCHAR(20),
    efternamn VARCHAR(20),
    adress VARCHAR(30),
    postnummer VARCHAR(12),
    ort VARCHAR(20),
    land VARCHAR(20),
    telefon VARCHAR(12),
    PRIMARY KEY(id)

);

Create TABLE ordrar (
    id INT AUTO_INCREMENT NOT NULL,
    totalsumma INT DEFAULT 0,
    skapad TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updaterad TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                        ON UPDATE CURRENT_TIMESTAMP,
    raderad TIMESTAMP DEFAULT NULL,
    beställt TIMESTAMP DEFAULT NULL,
    skickad TIMESTAMP DEFAULT NULL,
    kundId INT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY(kundId) REFERENCES kund(id)
);

CREATE TABLE faktura (
    id INT AUTO_INCREMENT NOT NULL,
    datum TIMESTAMP,
    frakt INT DEFAULT 0,
    kundId INT NOT NULL,
    ordrarId INT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY(kundId) REFERENCES kund(id),
    FOREIGN KEY (ordrarID) REFERENCES ordrar(id)
);

-- CREATE TABLE logg (
--     id INT AUTO_INCREMENT NOT NULL,
--     handelse VARCHAR(10),
--     datum TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
--     ordrarId INT NOT NULL,
--     PRIMARY KEY (id),
--     FOREIGN KEY (ordrarID) REFERENCES ordrar (id)
-- );

CREATE TABLE produkt (
    id INT AUTO_INCREMENT NOT NULL,
    beskrivning VARCHAR (30),
    pris INT DEFAULT 0,
    PRIMARY KEY(id)
);

CREATE TABLE produktlogg (
    id INT AUTO_INCREMENT NOT NULL,
    handelse VARCHAR(30),
    datum TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    produktId INT NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE kategori (
    id INT AUTO_INCREMENT NOT NULL,
    beskrivning VARCHAR(40),
    PRIMARY KEY(id)
);

CREATE TABLE plats (
    id INT NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE produktlista (
    produktId INT NOT NULL,
    kategoriId INT NOT NULL,

    FOREIGN KEY(kategoriId) REFERENCES kategori (id),
    FOREIGN KEY (produktId) REFERENCES produkt (id)
);

CREATE TABLE lager (
    id INT AUTO_INCREMENT NOT NULL,
    antal INT DEFAULT 0,
    platsId INT NOT NULL,
    produktId INT NOT NULL,
    PRIMARY KEY(id),
    FOREIGN KEY(platsId) REFERENCES plats (id),
    FOREIGN KEY (produktId) REFERENCES produkt (id),
    KEY index_points (produktId)
);

CREATE TABLE plocklista (
    produktId INT NOT NULL,
    antal INT NOT NULL,
    ordrarId INT NOT NULL,
    platsId INT NOT NULL,
    summa INT DEFAULT 0,
    FOREIGN KEY (ordrarId) REFERENCES ordrar(id),
    FOREIGN KEY (produktId) REFERENCES produkt(id),
    FOREIGN KEY (platsId) REFERENCES lager(id)
);

CREATE TABLE orderrad (
    produktId INT NOT NULL,
    antal INT NOT NULL,
    ordrarId INT NOT NULL,
    FOREIGN KEY (ordrarId) REFERENCES ordrar(id),
    FOREIGN KEY (produktId) REFERENCES produkt(id),
    KEY index_points (ordrarId)
);

CREATE TRIGGER produktlogg_create
AFTER INSERT
ON produkt FOR EACH ROW
    INSERT INTO produktlogg
        (handelse, produktId)
    VALUES
        ('Ny produkt lades till', NEW.id);


CREATE TRIGGER produktlogg_update
AFTER UPDATE
ON produkt FOR EACH ROW
    INSERT INTO produktlogg
        (handelse, produktId)
VALUES
    ('Produkt uppdaterades', NEW.id);

CREATE TRIGGER produktlogg_delete
BEFORE DELETE
ON produkt FOR EACH ROW
    INSERT INTO produktlogg
    (handelse, produktId)
VALUES
    ('Produkt Raderades', OLD.id);

--
-- Test av triggers
--

-- INSERT INTO produkt
--     (beskrivning, pris)
-- VALUES
--     ("kaffe från Afrika", 204);
--
-- UPDATE produkt
-- SET pris = 310
--     WHERE
--         id = 6;
--
-- DELETE FROM produkt
--     WHERE id = 6;
DELIMITER ;;

CREATE PROCEDURE show_categories()
BEGIN
    SELECT * FROM kategori;
END ;;

DELIMITER ;

DROP VIEW IF EXISTS lager_info;
CREATE view lager_info as
SELECT produktId, SUM(antal) as antal, GROUP_CONCAT(platsID) as plats FROM lager group by produktId;

CREATE VIEW produkt_info AS
SELECT DISTINCT p.id, p.beskrivning, p.pris, GROUP_CONCAT(k.beskrivning) as kategori, l.antal, l.plats FROM produktlista as pl JOIN produkt as p ON p.id = pl.produktID
JOIN kategori as k on pl.kategoriId = k.id JOIN lager_info as l ON p.id = l.produktId GROUP BY p.id;

DROP PROCEDURE IF EXISTS show_produkt_info;
DELIMITER ;;

CREATE PROCEDURE show_produkt_info()
BEGIN
    SELECT DISTINCT p.id, p.beskrivning, p.pris, pi.kategori, pi.antal, pi.plats FROM produkt_info as pi RIGHT OUTER JOIN produkt as p on pi.id = p.id;
END ;;

DELIMITER ;

create View antal_orderrader as SELECT ordrarId, COUNT(produktId) as antal_rader from orderrad GROUP BY ordrarId;

DELIMITER ;;

CREATE PROCEDURE add_produkt(
    besk VARCHAR(20),
    priset INT
)
BEGIN
    INSERT INTO produkt
        (beskrivning, pris)
    VALUES (besk, priset);
END ;;

DELIMITER ;

DELIMITER ;;
CREATE PROCEDURE edit_produkt(

    produktId INT,
    besk VARCHAR(20),
    priset INT
)
BEGIN
    UPDATE produkt
        SET beskrivning = besk, pris = priset
        WHERE id = produktId;
END ;;

DELIMITER ;


DELIMITER ;;
CREATE PROCEDURE delete_produkt(
    pId INT
)
BEGIN
    DELETE FROM produktlista WHERE produktId = pId;
    DELETE FROM lager WHERE produktId = pId;
    DELETE FROM produkt WHERE id = pId;

END ;;

DELIMITER ;

DELIMITER ;;
CREATE PROCEDURE show_produkt(
    pId INT
)
BEGIN
SELECT * FROM produkt WHERE id = pId;
END ;;

DELIMITER ;


DELIMITER ;;
CREATE PROCEDURE logg_limit(
    lim INT
)
BEGIN
SELECT * FROM produktlogg ORDER BY datum DESC LIMIT lim;
END ;;

DELIMITER ;

DELIMITER ;;
CREATE PROCEDURE show_lager_filter_Opt(
  filter VARCHAR(20)
)
BEGIN

  IF filter = 'id' then
      SELECT p.id, p.beskrivning, l.platsId from lager as l JOIN produkt as p ON l.produktId = p.id ORDER BY p.id;
  ELSEIF filter = 'platsId' then
    SELECT p.id, p.beskrivning, l.platsId from lager as l JOIN produkt as p ON l.produktId = p.id ORDER BY l.platsId ;
  ELSEIF filter = 'beskrivning' then
     SELECT p.id, p.beskrivning, l.platsId from lager as l JOIN produkt as p ON l.produktId = p.id ORDER BY p.beskrivning;
    ELSE
      SELECT p.id, p.beskrivning, l.platsId from lager as l JOIN produkt as p ON l.produktId = p.id;
  end if;
END ;;

DELIMITER ;


DELIMITER ;;
CREATE PROCEDURE show_lager()
  BEGIN

  SELECT * FROM lager;
    END ;;

DELIMITER ;

DELIMITER ;;

DROP FUNCTION IF EXISTS order_status;
CREATE FUNCTION order_status(
    skapad TIMESTAMP,
    updaterad TIMESTAMP,
    raderad TIMESTAMP,
    beställt TIMESTAMP,
    skickad TIMESTAMP
)
RETURNS VARCHAR (20)
NOT DETERMINISTIC NO SQL
BEGIN
    IF raderad IS NOT NULL THEN
        RETURN 'raderad';
    ELSEIF skickad IS NOT NULL THEN
        RETURN 'skickad';
    ELSEIF beställt IS NOT NULL THEN
        RETURN 'beställt';
    ELSEIF updaterad > skapad THEN
        RETURN 'uppdaterad';
    ELSE
        RETURN 'skapad';
    END IF;

END ;;


DELIMITER ;

DROP PROCEDURE IF EXISTS show_customers;
DELIMITER ;;
CREATE PROCEDURE show_customers()
BEGIN
SELECT * FROM kund;
END ;;

DELIMITER ;


DROP PROCEDURE IF EXISTS create_order;
DELIMITER ;;
CREATE PROCEDURE create_order(
    kId INT
)
BEGIN

INSERT INTO ordrar (kundId) VALUES (kId);

END ;;

DELIMITER ;

DROP PROCEDURE IF EXISTS show_orders;
DELIMITER ;;
CREATE PROCEDURE show_orders()
BEGIN

SELECT o.id as order_id, k.id as kund_id, CONCAT(k.fornamn, " ", k.efternamn) as namn, ao.antal_rader as antal_rader,
DATE_FORMAT(skapad, "%d/%m-%y %T") as skapad, order_status(skapad,updaterad, raderad, beställt, skickad ) as status
from ordrar as o INNER JOIN kund as k ON o.kundId = k.id LEFT OUTER JOIN antal_orderrader as ao on o.id = ao.ordrarId;

END ;;

DELIMITER ;



DROP PROCEDURE IF EXISTS add_to_order;
DELIMITER ;;
CREATE PROCEDURE add_to_order(
    oId INT,
    pId INT,
    amount INT
)
BEGIN
INSERT INTO orderrad (ordrarId, produktId, antal) VALUES(oId, pId, amount);
UPDATE ordrar SET updaterad = CURRENT_TIMESTAMP WHERE id = oId;
END ;;

DELIMITER ;



DROP PROCEDURE IF EXISTS show_orderrad_for_order;
DELIMITER ;;
CREATE PROCEDURE show_orderrad_for_order(
    oId INT
)
BEGIN
SELECT DISTINCT * FROM orderrad as orad INNER JOIN produkt as p ON p.id =orad.produktId
    WHERE orad.ordrarId = oId;
END ;;

DELIMITER ;

DROP PROCEDURE IF EXISTS finish_order;
DELIMITER ;;
CREATE PROCEDURE finish_order(
    oId INT
)
BEGIN
Update ordrar SET beställt = CURRENT_TIMESTAMP WHERE id = oId;
END ;;

DELIMITER ;


DROP PROCEDURE IF EXISTS search_order_info;
DELIMITER ;;

CREATE PROCEDURE search_order_info(
sId INT
)
BEGIN
SELECT o.id as order_id, k.id as kund_id, CONCAT(k.fornamn, " ", k.efternamn) as namn, ao.antal_rader as antal_rader,
DATE_FORMAT(skapad, "%d/%m-%y %T") as skapad, order_status(skapad,updaterad, raderad, beställt, skickad ) as status
from ordrar as o INNER JOIN kund as k ON o.kundId = k.id LEFT OUTER JOIN antal_orderrader as ao on o.id = ao.ordrarId
WHERE o.id = sId or k.id = sId;

END ;;

DELIMITER ;


DROP Procedure if EXISTS generete_plocklista;
DELIMITER ;;
CREATE PROCEDURE generete_plocklista(
oId INT
)
    BEGIN
    SELECT Distinct l.produktId, l.platsId, orad.antal as beställda, l.antal as "produkt i lager",
    IF(l.antal >= orad.antal, "finns i lager", "finns ej i lager") as lagerstaus
    FROM orderrad as orad INNER JOIN lager as l ON orad.produktId = l.produktId  WHERE orad.ordrarId = oId;

    END ;;



DELIMITER ;



DROP Procedure if EXISTS order_info;
DELIMITER ;;
CREATE PROCEDURE order_info()
  BEGIN
  SELECT o.id as order_id, k.id as kund_id, CONCAT(k.fornamn, " ", k.efternamn) as namn, ao.antal_rader as antal_rader,
DATE_FORMAT(skapad, "%d/%m-%y %T") as skapad, order_status(skapad,updaterad, raderad, beställt, skickad ) as status
from ordrar as o INNER JOIN kund as k ON o.kundId = k.id LEFT OUTER JOIN antal_orderrader as ao on o.id = ao.ordrarId;

  END ;;



DELIMITER ;
