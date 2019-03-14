LOAD DATA LOCAL INFILE 'data_kunder.csv'
INTO TABLE kund
CHARSET utf8
FIELDS
    TERMINATED BY ','
    ENCLOSED BY '"'
LINES
    TERMINATED BY '\n'
IGNORE 1 LINES
(fornamn, efternamn, adress, postnummer,ort, land, telefon)
;

LOAD DATA LOCAL INFILE 'data_kategorier.csv'
INTO TABLE kategori
CHARSET utf8
FIELDS
    TERMINATED BY ','
    ENCLOSED BY '"'
LINES
    TERMINATED BY '\n'
IGNORE 1 LINES
(beskrivning)
;


LOAD DATA LOCAL INFILE 'data_plats.csv'
INTO TABLE plats
CHARSET utf8
FIELDS
    TERMINATED BY ','
    ENCLOSED BY '"'
LINES
    TERMINATED BY '\n'
IGNORE 1 LINES
;

LOAD DATA LOCAL INFILE 'data_produkter.csv'
INTO TABLE produkt
CHARSET utf8
FIELDS
    TERMINATED BY ','
    ENCLOSED BY '"'
LINES
    TERMINATED BY '\n'
IGNORE 1 LINES
(beskrivning, pris)
;

LOAD DATA LOCAL INFILE 'data_lager.csv'
INTO TABLE lager
CHARSET utf8
FIELDS
    TERMINATED BY ','
    ENCLOSED BY '"'
LINES
    TERMINATED BY '\n'
IGNORE 1 LINES
(produktId, platsId, antal)
;

LOAD DATA LOCAL INFILE 'data_produktlista.csv'
INTO TABLE produktlista
CHARSET utf8
FIELDS
    TERMINATED BY ','
    ENCLOSED BY '"'
LINES
    TERMINATED BY '\n'
IGNORE 1 LINES
(produktId, kategoriId)
;
