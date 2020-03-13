Start Kafka

docker-compose up

Create Quarkus applications with simple Kafka producer and consumer:

https://quarkus.io/guides/kafka

mvn io.quarkus:quarkus-maven-plugin:1.2.1.Final:create -DprojectGroupId=com.talkdesk.tdx.nlp -DprojectArtifactId=test-kafka-producer-consumer -Dextensions=kafka

Run:

mvn quarkus:dev

Open webpage:

http://localhost:8080/prices.html

Create Producer and Consumer with partition based on the payload:

kafka-topics --create --topic transcription -zookeeper localhost:2181 --replication-factor 1 --partitions 1

