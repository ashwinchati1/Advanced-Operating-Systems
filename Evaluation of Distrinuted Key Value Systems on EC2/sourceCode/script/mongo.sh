#!/bin/sh

pscp -h host.txt -O StrictHostKeyChecking=no -r /home/ashwin/Desktop/PROJ_CHATI_ASHWIN/config.properties -r /home/ubuntu

pssh -h host.txt -t 1000000 -O StrictHostKeyChecking=no -o . 'sh ./start_mongo.sh'

scp -i ashwinnew.pem ubuntu@54.165.203.211:/home/ubuntu/testMongoDb/MongoTest.log  /home/ashwin/Desktop/PROJ_CHATI_ASHWIN/logs/mongo/server1
scp -i ashwinnew.pem ubuntu@54.165.151.98:/home/ubuntu/testMongoDb/MongoTest.log  /home/ashwin/Desktop/PROJ_CHATI_ASHWIN/logs/mongo/server2
scp -i ashwinnew.pem ubuntu@54.165.69.93:/home/ubuntu/testMongoDb/MongoTest.log  /home/ashwin/Desktop/PROJ_CHATI_ASHWIN/logs/mongo/server3
scp -i ashwinnew.pem ubuntu@54.165.167.126:/home/ubuntu/testMongoDb/MongoTest.log  /home/ashwin/Desktop/PROJ_CHATI_ASHWIN/logs/mongo/server4
scp -i ashwinnew.pem ubuntu@54.165.192.4:/home/ubuntu/testMongoDb/MongoTest.log  /home/ashwin/Desktop/PROJ_CHATI_ASHWIN/logs/mongo/server5
scp -i ashwinnew.pem ubuntu@54.165.121.97:/home/ubuntu/testMongoDb/MongoTest.log  /home/ashwin/Desktop/PROJ_CHATI_ASHWIN/logs/mongo/server6
scp -i ashwinnew.pem ubuntu@54.165.112.202:/home/ubuntu/testMongoDb/MongoTest.log  /home/ashwin/Desktop/PROJ_CHATI_ASHWIN/logs/mongo/server7
scp -i ashwinnew.pem ubuntu@54.165.179.34:/home/ubuntu/testMongoDb/MongoTest.log  /home/ashwin/Desktop/PROJ_CHATI_ASHWIN/logs/mongo/server8
scp -i ashwinnew.pem ubuntu@54.165.111.188:/home/ubuntu/testMongoDb/MongoTest.log  /home/ashwin/Desktop/PROJ_CHATI_ASHWIN/logs/mongo/server9
scp -i ashwinnew.pem ubuntu@54.165.113.42:/home/ubuntu/testMongoDb/MongoTest.log  /home/ashwin/Desktop/PROJ_CHATI_ASHWIN/logs/mongo/server10
scp -i ashwinnew.pem ubuntu@54.165.189.244:/home/ubuntu/testMongoDb/MongoTest.log  /home/ashwin/Desktop/PROJ_CHATI_ASHWIN/logs/mongo/server11
scp -i ashwinnew.pem ubuntu@54.165.172.102:/home/ubuntu/testMongoDb/MongoTest.log  /home/ashwin/Desktop/PROJ_CHATI_ASHWIN/logs/mongo/server12
scp -i ashwinnew.pem ubuntu@54.165.126.148:/home/ubuntu/testMongoDb/MongoTest.log  /home/ashwin/Desktop/PROJ_CHATI_ASHWIN/logs/mongo/server13
scp -i ashwinnew.pem ubuntu@54.165.187.68:/home/ubuntu/testMongoDb/MongoTest.log  /home/ashwin/Desktop/PROJ_CHATI_ASHWIN/logs/mongo/server14
scp -i ashwinnew.pem ubuntu@52.90.199.192:/home/ubuntu/testMongoDb/MongoTest.log  /home/ashwin/Desktop/PROJ_CHATI_ASHWIN/logs/mongo/server15
scp -i ashwinnew.pem ubuntu@54.165.50.78:/home/ubuntu/testMongoDb/MongoTest.log  /home/ashwin/Desktop/PROJ_CHATI_ASHWIN/logs/mongo/server16

