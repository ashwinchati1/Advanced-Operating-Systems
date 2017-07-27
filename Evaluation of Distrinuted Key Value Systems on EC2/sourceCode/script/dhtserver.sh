#!/bin/sh



pssh -h host.txt -t 1000000 -O StrictHostKeyChecking=no -o . 'sh ./start_dhtserver.sh'


