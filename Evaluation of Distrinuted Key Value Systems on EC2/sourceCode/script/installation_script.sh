#!/bin/sh

echo "Starting software installation"

echo "----------------------------------"
echo "Java installation start"
echo "----------------------------------"
sudo apt-get update
sudo apt-get install openjdk-7-jdk
echo "----------------------------------"
echo "Java installation end"
echo "----------------------------------"

echo "----------------------------------"
echo "Ant installation start"
echo "----------------------------------"
sudo apt-get install ant
echo "----------------------------------"
echo "Ant installation end"
echo "----------------------------------"

echo "----------------------------------"
echo "MongoDb installation start"
echo "----------------------------------"
sudo apt-get install -y mongodb-org-server mongodb-org-shell mongodb-org-tools
sudo rm /var/lib/dpkg/lock
sudo apt-get install mongodb
sudo service mongod start
sudo apt-get install mongodb-clients
echo "----------------------------------"
echo "MongoDb installation end"
echo "----------------------------------"

echo "----------------------------------"
echo "Redis installation start"
echo "----------------------------------"
sudo apt-get install -y python-software-properties
sudo add-apt-repository -y ppa:rwky/redis
sudo apt-get update
sudo apt-get install -y redis-server
sudo service redis-server stop
sudo service redis-server start
echo "----------------------------------"
echo "Redis installation end"
echo "----------------------------------"

echo "----------------------------------"
echo "CouchDb installation start"
echo "----------------------------------"
sudo apt-get install couchdb
echo "----------------------------------"
echo "CouchDb installation end"
echo "----------------------------------"
