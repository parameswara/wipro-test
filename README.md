# wipro-test

To run an application MongoDB server should be up and running and Kafka also should be up and running.
Installation and Run Process:
1. Download Kafka (https://kafka.apache.org/downloads)
2. Unzip Kafka and go to Kafka Folder
3. Run Zookeeper: zookeeper-server-start.bat ../../config/zookeeper.properties
4. Run Kafka: Kafka-server-start.bat ../../config/server.properties
5. Create Kafka Topic: kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic demo
6. Start Producer: kafka-console-producer.bat --broker-list localhost:9092 --topic demo
7. Start Consumer: kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic demo --from-beginning

Swagger URL for the documentation
http://localhost:8080/swagger-ui.html
