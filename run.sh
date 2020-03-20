#!/bin/bash

export DOCKER_HOST_IP=$(ifconfig | grep "inet " | grep -v 127.0.0.1 | cut -d\  -f2)
mvn package -T C1 && docker-compose build && docker-compose up -d zoo1 kafka1 kafdrop

until (echo dump | nc localhost 2181 | grep brokers); do sleep 1; done
kafka-topics --create --topic transcriptions -zookeeper localhost:2181 --replication-factor 1 --partitions 4
kafka-topics --create --topic transcription-states -zookeeper localhost:2181 --replication-factor 1 --partitions 4

docker-compose up kafka-producer kafka-consumer