#!/usr/bin/env bash
#
# create new values the database.
#

file="ddl.sql"
echo ">>> Create tables ($file)"
mysql -uuser -ppass -h 192.168.1.16 hermes < $file > /dev/null

file="insert.sql"
echo ">>> läser in filer från ($file)"
mysql -uuser -ppass -h 192.168.1.16 --local-infile=1 hermes < $file > /dev/null
