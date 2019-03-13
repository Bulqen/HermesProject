
SELECT DISTINCT p.id, p.beskrivning, SUM(l.antal), l.platsId FROM produkt as p JOIN lager as l ON p.id = l.produktid;

DROP VIEW IF EXISTS lager_info;
CREATE view lager_info as
SELECT produktId, SUM(antal) as antal, GROUP_CONCAT(platsID) as plats FROM lager group by produktId;

SELECT produktId, GROUP_CONCAT(platsId) FROM lager GROUP BY produktId;
SELECT * from lager;

SELECT DISTINCT p.id, p.beskrivning, p.pris, GROUP_CONCAT(k.beskrivning) as kategori, l.antal, l.plats FROM produktlista as pl JOIN produkt as p ON p.id = pl.produktID
JOIN kategori as k on pl.kategoriId = k.id JOIN lager_info as l ON p.id = l.produktId GROUP BY p.id;

CREATE VIEW produkt_info AS
SELECT DISTINCT p.id, p.beskrivning, p.pris, GROUP_CONCAT(k.beskrivning) as kategori, l.antal, l.plats FROM produktlista as pl JOIN produkt as p ON p.id = pl.produktID
JOIN kategori as k on pl.kategoriId = k.id JOIN lager_info as l ON p.id = l.produktId GROUP BY p.id;
-uuser -ppass --local-infile=1 eshop
