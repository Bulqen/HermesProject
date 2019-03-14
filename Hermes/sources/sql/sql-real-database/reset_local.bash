#!/usr/bin/env bash
#
# Recreate the database.
#
echo ">>> Reset hermes"
echo ">>> Recreate the database (as root)"
mysql -uroot -p < setup.sql > /dev/null

file="ddl.sql"
echo ">>> Create tables ($file)"
mysql -uuser -ppass hermes < $file > /dev/null

file="insert.sql"
echo ">>> läser in filer från ($file)"
mysql -uuser -ppass --local-infile=1 hermes < $file > /dev/null
