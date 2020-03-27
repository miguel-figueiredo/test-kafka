#!/bin/bash

function show_help {
  cat <<EOF
Usage: run.sh [-a] [-b] [-k] [-c] [-p] [-x] [-h]
-a: Run all
-b: Build
-k: Run Kafka
-p: Run producer
-c: Run consumer
-x: Stops continers and clean data
-h: Shows this help

If no options are provided the script executes the build and runs all the containers.
EOF
}

function build {
  mvn package -T C1 && docker-compose build
}

function run_kafka {
  docker-compose up -d zoo1 kafka1 kafdrop
  until (echo dump | nc localhost 2181 | grep brokers); do sleep 1; done
  kafka-topics --create --topic transcriptions -zookeeper localhost:2181 --replication-factor 1 --partitions 4
  kafka-topics --create --topic transcription-states -zookeeper localhost:2181 --replication-factor 1 --partitions 4
  kafka-topics --create --topic stateful-transcriptions -zookeeper localhost:2181 --replication-factor 1 --partitions 4
}

function run_producer {
  docker-compose up -d kafka-producer
}

function run_consumer {
  docker-compose up -d --scale kafka-consumer=4 kafka-consumer
}

function clean {
  docker-compose stop
  docker-compose rm
  rm -rf zk-single-kafka-single
}

export DOCKER_HOST_IP=$(ifconfig | grep "inet " | grep -v 127.0.0.1 | cut -d\  -f2)

operations=()

while getopts "abkpPcCxh" OPTION; do
    case $OPTION in
    b)
        operations+=(build)
        ;;
    k)
        operations+=(run_kafka)
        ;;
    p)
        operations+=(run_producer)
        ;;
    c)
        operations+=(run_consumer)
        ;;
    x)
        clean
        exit 0
        ;;
    h)
        show_help
        exit 0
        ;;
    a)
        operations=(build run_kafka run_producer run_consumer)
        ;;
    *)
        show_help
        exit 1
        ;;
    esac
done

if [ -z $operations ];then
  show_help
  exit 1
fi

for operation in ${operations[@]}; do
  $operation
done
