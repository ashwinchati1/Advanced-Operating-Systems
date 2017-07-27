#!/bin/sh

pscp -h host.txt -O StrictHostKeyChecking=no -r /home/ashwin/Desktop/PROJ_CHATI_ASHWIN/script/installation_script.sh -r /home/ubuntu
pscp -h host.txt -O StrictHostKeyChecking=no -r /home/ashwin/Desktop/PROJ_CHATI_ASHWIN/script/start_redis.sh -r /home/ubuntu
pscp -h host.txt -O StrictHostKeyChecking=no -r /home/ashwin/Desktop/PROJ_CHATI_ASHWIN/script/start_mongo.sh -r /home/ubuntu
pscp -h host.txt -O StrictHostKeyChecking=no -r /home/ashwin/Desktop/PROJ_CHATI_ASHWIN/script/start_couch.sh -r /home/ubuntu
pscp -h host.txt -O StrictHostKeyChecking=no -r /home/ashwin/Desktop/PROJ_CHATI_ASHWIN/script/start_dhttest.sh -r /home/ubuntu
pscp -h host.txt -O StrictHostKeyChecking=no -r /home/ashwin/Desktop/PROJ_CHATI_ASHWIN/script/start_dhtserver.sh -r /home/ubuntu
pscp -h host.txt -O StrictHostKeyChecking=no -r /home/ashwin/Desktop/PROJ_CHATI_ASHWIN/code/DHT -r /home/ubuntu
pscp -h host.txt -O StrictHostKeyChecking=no -r /home/ashwin/Desktop/PROJ_CHATI_ASHWIN/code/redisTest -r /home/ubuntu
pscp -h host.txt -O StrictHostKeyChecking=no -r /home/ashwin/Desktop/PROJ_CHATI_ASHWIN/code/testCouchDb -r /home/ubuntu
pscp -h host.txt -O StrictHostKeyChecking=no -r /home/ashwin/Desktop/PROJ_CHATI_ASHWIN/code/testMongoDb -r /home/ubuntu
pssh -h host.txt -t 1000000 -O StrictHostKeyChecking=no -o . 'sh ./installation_script.sh'
