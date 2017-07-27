#!/bin/sh

pscp -h host.txt -O StrictHostKeyChecking=no -r /home/ashwin/Desktop/PROJ_CHATI_ASHWIN/config.properties -r /home/ubuntu

pssh -h host.txt -t 1000000 -O StrictHostKeyChecking=no -o . 'sh ./start_redis.sh'

scp -i ashwinnew.pem ubuntu@54.165.203.211:/home/ubuntu/redisTest/RedisTest.log  /home/ashwin/Desktop/PROJ_CHATI_ASHWIN/logs/redis/server1
scp -i ashwinnew.pem ubuntu@54.165.151.98:/home/ubuntu/redisTest/RedisTest.log  /home/ashwin/Desktop/PROJ_CHATI_ASHWIN/logs/redis/server2
scp -i ashwinnew.pem ubuntu@54.165.69.93:/home/ubuntu/redisTest/RedisTest.log  /home/ashwin/Desktop/PROJ_CHATI_ASHWIN/logs/redis/server3
scp -i ashwinnew.pem ubuntu@54.165.167.126:/home/ubuntu/redisTest/RedisTest.log  /home/ashwin/Desktop/PROJ_CHATI_ASHWIN/logs/redis/server4
scp -i ashwinnew.pem ubuntu@54.165.192.4:/home/ubuntu/redisTest/RedisTest.log  /home/ashwin/Desktop/PROJ_CHATI_ASHWIN/logs/redis/server5
scp -i ashwinnew.pem ubuntu@54.165.121.97:/home/ubuntu/redisTest/RedisTest.log  /home/ashwin/Desktop/PROJ_CHATI_ASHWIN/logs/redis/server6
scp -i ashwinnew.pem ubuntu@54.165.112.202:/home/ubuntu/redisTest/RedisTest.log  /home/ashwin/Desktop/PROJ_CHATI_ASHWIN/logs/redis/server7
scp -i ashwinnew.pem ubuntu@54.165.179.34:/home/ubuntu/redisTest/RedisTest.log  /home/ashwin/Desktop/PROJ_CHATI_ASHWIN/logs/redis/server8
scp -i ashwinnew.pem ubuntu@54.165.111.188:/home/ubuntu/redisTest/RedisTest.log  /home/ashwin/Desktop/PROJ_CHATI_ASHWIN/logs/redis/server9
scp -i ashwinnew.pem ubuntu@54.165.113.42:/home/ubuntu/redisTest/RedisTest.log  /home/ashwin/Desktop/PROJ_CHATI_ASHWIN/logs/redis/server10
scp -i ashwinnew.pem ubuntu@54.165.189.244:/home/ubuntu/redisTest/RedisTest.log  /home/ashwin/Desktop/PROJ_CHATI_ASHWIN/logs/redis/server11
scp -i ashwinnew.pem ubuntu@54.165.172.102:/home/ubuntu/redisTest/RedisTest.log  /home/ashwin/Desktop/PROJ_CHATI_ASHWIN/logs/redis/server12
scp -i ashwinnew.pem ubuntu@54.165.126.148:/home/ubuntu/redisTest/RedisTest.log  /home/ashwin/Desktop/PROJ_CHATI_ASHWIN/logs/redis/server13
scp -i ashwinnew.pem ubuntu@54.165.187.68:/home/ubuntu/redisTest/RedisTest.log  /home/ashwin/Desktop/PROJ_CHATI_ASHWIN/logs/redis/server14
scp -i ashwinnew.pem ubuntu@52.90.199.192:/home/ubuntu/redisTest/RedisTest.log  /home/ashwin/Desktop/PROJ_CHATI_ASHWIN/logs/redis/server15
scp -i ashwinnew.pem ubuntu@54.165.50.78:/home/ubuntu/redisTest/RedisTest.log  /home/ashwin/Desktop/PROJ_CHATI_ASHWIN/logs/redis/server16




