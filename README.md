# Project for Kafka Exploration

## Start

```
./run.sh -a
```

## Run Dev

```
cd test-kafka-producer
mvn quarkus:dev
```
and/or

```
cd test-kafka-consumer
mvn quarkus:dev -Dquarkus.http.port=8081
```

## Webpages

Application: http://localhost:8080/transcriptions.html

## Useful commands

```
kafka-topics --list --zookeeper localhost:2181
kafka-topics --describe --topic transcriptions --zookeeper localhost:2181
kafka-console-consumer --topic transcriptions --bootstrap-server localhost:9092
```

## Notes

Create Quarkus applications with simple Kafka producer and consumer:

https://quarkus.io/guides/kafka

```
mvn io.quarkus:quarkus-maven-plugin:1.2.1.Final:create -DprojectGroupId=com.talkdesk.tdx.nlp -DprojectArtifactId=test-kafka-producer-consumer -Dextensions=kafka
```

