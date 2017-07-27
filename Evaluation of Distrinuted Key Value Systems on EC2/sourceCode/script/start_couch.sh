#!/bin/sh

cp ~/config.properties /home/ubuntu/testCouchDb
cd /home/ubuntu/testCouchDb/build/jar
rm -f CouchTest.jar
cd /home/ubuntu/testCouchDb
ant runcouch

