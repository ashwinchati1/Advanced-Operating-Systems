#!/bin/sh

pscp -h host.txt -O StrictHostKeyChecking=no -r /home/ashwin/Desktop/PROJ_CHATI_ASHWIN/config.properties -r /home/ubuntu

pssh -h host.txt -t 1000000 -O StrictHostKeyChecking=no -o . 'sh ./start_couch.sh'

scp -i ashwinnew.pem ubuntu@54.173.124.118:/home/ubuntu/testCouchDb/CouchTest.log  /home/ashwin/Desktop/PROJ_CHATI_ASHWIN/logs/couch/server1
scp -i ashwinnew.pem ubuntu@54.173.45.143:/home/ubuntu/testCouchDb/CouchTest.log  /home/ashwin/Desktop/PROJ_CHATI_ASHWIN/logs/couch/server2
scp -i ashwinnew.pem ubuntu@54.164.238.114:/home/ubuntu/testCouchDb/CouchTest.log  /home/ashwin/Desktop/PROJ_CHATI_ASHWIN/logs/couch/server3
scp -i ashwinnew.pem ubuntu@54.173.163.225:/home/ubuntu/testCouchDb/CouchTest.log  /home/ashwin/Desktop/PROJ_CHATI_ASHWIN/logs/couch/server4
scp -i ashwinnew.pem ubuntu@54.173.124.71:/home/ubuntu/testCouchDb/CouchTest.log  /home/ashwin/Desktop/PROJ_CHATI_ASHWIN/logs/couch/server5
scp -i ashwinnew.pem ubuntu@54.173.46.181:/home/ubuntu/testCouchDb/CouchTest.log  /home/ashwin/Desktop/PROJ_CHATI_ASHWIN/logs/couch/server6
scp -i ashwinnew.pem ubuntu@54.173.102.39:/home/ubuntu/testCouchDb/CouchTest.log  /home/ashwin/Desktop/PROJ_CHATI_ASHWIN/logs/couch/server7
scp -i ashwinnew.pem ubuntu@54.173.45.51:/home/ubuntu/testCouchDb/CouchTest.log  /home/ashwin/Desktop/PROJ_CHATI_ASHWIN/logs/couch/server8
scp -i ashwinnew.pem ubuntu@54.173.188.32:/home/ubuntu/testCouchDb/CouchTest.log  /home/ashwin/Desktop/PROJ_CHATI_ASHWIN/logs/couch/server9
scp -i ashwinnew.pem ubuntu@54.173.184.186:/home/ubuntu/testCouchDb/CouchTest.log  /home/ashwin/Desktop/PROJ_CHATI_ASHWIN/logs/couch/server10
scp -i ashwinnew.pem ubuntu@54.173.173.192:/home/ubuntu/testCouchDb/CouchTest.log  /home/ashwin/Desktop/PROJ_CHATI_ASHWIN/logs/couch/server11
scp -i ashwinnew.pem ubuntu@54.172.121.81:/home/ubuntu/testCouchDb/CouchTest.log  /home/ashwin/Desktop/PROJ_CHATI_ASHWIN/logs/couch/server12
scp -i ashwinnew.pem ubuntu@54.173.70.100:/home/ubuntu/testCouchDb/CouchTest.log  /home/ashwin/Desktop/PROJ_CHATI_ASHWIN/logs/couch/server13
scp -i ashwinnew.pem ubuntu@54.173.186.136:/home/ubuntu/testCouchDb/CouchTest.log  /home/ashwin/Desktop/PROJ_CHATI_ASHWIN/logs/couch/server14
scp -i ashwinnew.pem ubuntu@54.173.76.208:/home/ubuntu/testCouchDb/CouchTest.log  /home/ashwin/Desktop/PROJ_CHATI_ASHWIN/logs/couch/server15
scp -i ashwinnew.pem ubuntu@52.91.230.240:/home/ubuntu/testCouchDb/CouchTest.log  /home/ashwin/Desktop/PROJ_CHATI_ASHWIN/logs/couch/server16
