#!/bin/sh

pssh -h host.txt -t 1000000 -O StrictHostKeyChecking=no -o . 'sh ./start_dhttest.sh'

scp -i ashwinnew.pem ubuntu@54.165.203.211:/home/ubuntu/DHT/PerformanceTest.log  /home/ashwin/Desktop/PROJ_CHATI_ASHWIN/logs/DHT/server1
scp -i ashwinnew.pem ubuntu@54.165.151.98:/home/ubuntu/DHT/PerformanceTest.log  /home/ashwin/Desktop/PROJ_CHATI_ASHWIN/logs/DHT/server2
scp -i ashwinnew.pem ubuntu@54.165.69.93:/home/ubuntu/DHT/PerformanceTest.log  /home/ashwin/Desktop/PROJ_CHATI_ASHWIN/logs/DHT/server3
scp -i ashwinnew.pem ubuntu@54.165.167.126:/home/ubuntu/DHT/PerformanceTest.log  /home/ashwin/Desktop/PROJ_CHATI_ASHWIN/logs/DHT/server4
scp -i ashwinnew.pem ubuntu@54.165.192.4:/home/ubuntu/DHT/PerformanceTest.log  /home/ashwin/Desktop/PROJ_CHATI_ASHWIN/logs/DHT/server5
scp -i ashwinnew.pem ubuntu@54.165.121.97:/home/ubuntu/DHT/PerformanceTest.log  /home/ashwin/Desktop/PROJ_CHATI_ASHWIN/logs/DHT/server6
scp -i ashwinnew.pem ubuntu@54.165.112.202:/home/ubuntu/DHT/PerformanceTest.log  /home/ashwin/Desktop/PROJ_CHATI_ASHWIN/logs/DHT/server7
scp -i ashwinnew.pem ubuntu@54.165.179.34:/home/ubuntu/DHT/PerformanceTest.log  /home/ashwin/Desktop/PROJ_CHATI_ASHWIN/logs/DHT/server8
scp -i ashwinnew.pem ubuntu@54.165.111.188:/home/ubuntu/DHT/PerformanceTest.log  /home/ashwin/Desktop/PROJ_CHATI_ASHWIN/logs/DHT/server9
scp -i ashwinnew.pem ubuntu@54.165.113.42:/home/ubuntu/DHT/PerformanceTest.log  /home/ashwin/Desktop/PROJ_CHATI_ASHWIN/logs/DHT/server10
scp -i ashwinnew.pem ubuntu@54.165.189.244:/home/ubuntu/DHT/PerformanceTest.log  /home/ashwin/Desktop/PROJ_CHATI_ASHWIN/logs/DHT/server11
scp -i ashwinnew.pem ubuntu@54.165.172.102:/home/ubuntu/DHT/PerformanceTest.log  /home/ashwin/Desktop/PROJ_CHATI_ASHWIN/logs/DHT/server12
scp -i ashwinnew.pem ubuntu@54.165.126.148:/home/ubuntu/DHT/PerformanceTest.log  /home/ashwin/Desktop/PROJ_CHATI_ASHWIN/logs/DHT/server13
scp -i ashwinnew.pem ubuntu@54.165.187.68:/home/ubuntu/DHT/PerformanceTest.log  /home/ashwin/Desktop/PROJ_CHATI_ASHWIN/logs/DHT/server14
scp -i ashwinnew.pem ubuntu@52.90.199.192:/home/ubuntu/DHT/PerformanceTest.log  /home/ashwin/Desktop/PROJ_CHATI_ASHWIN/logs/DHT/server15
scp -i ashwinnew.pem ubuntu@54.165.50.78:/home/ubuntu/DHT/PerformanceTest.log  /home/ashwin/Desktop/PROJ_CHATI_ASHWIN/logs/DHT/server16
