Start Kafka

```
export DOCKER_HOST_IP=$(ifconfig | grep "inet " | grep -v 127.0.0.1 | cut -d\  -f2)
docker-compose up
```

Create Quarkus applications with simple Kafka producer and consumer:

https://quarkus.io/guides/kafka

```
mvn io.quarkus:quarkus-maven-plugin:1.2.1.Final:create -DprojectGroupId=com.talkdesk.tdx.nlp -DprojectArtifactId=test-kafka-producer-consumer -Dextensions=kafka
```

Run:

```
mvn quarkus:dev
```

Webpages:

Application: http://localhost:8080/prices.html
Kafdro: https://localhost:9000

Create Producer and Consumer with partition based on the payload:

```
kafka-topics --create --topic transcriptions -zookeeper localhost:2181 --replication-factor 1 --partitions 4
```

Useful commands:

```
kafka-topics --list --zookeeper localhost:2181
kafka-topics --describe --topic transcriptions --zookeeper localhost:2181
kafka-console-consumer --topic transcriptions --bootstrap-server localhost:9092
```

