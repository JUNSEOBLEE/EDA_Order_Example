# EDA_Order_Example


### Kafka Docker Compose
```
version: '3.8'

services:
  zookeeper:
    image: confluentinc/cp-zookeeper:7.6.0
    hostname: zookeeper
    container_name: zookeeper
    ports:
      - "2181:2181"
    environment:
      ZOOKEEPER_CLIENT_PORT: 2181
      ZOOKEEPER_SERVER_ID: 1
      ZOOKEEPER_SERVERS: zookeeper:2888:3888
    restart: always
    networks:
      - kafka-network

  kafka:
    image: confluentinc/cp-kafka:7.6.0
    hostname: kafka
    container_name: kafka
    ports:
      - "9092:9092"
      - "29092:29092" # 추가된 포트 매핑
    environment:
      KAFKA_ADVERTISED_LISTENERS: INTERNAL://kafka:19092,EXTERNAL://localhost:9092
      KAFKA_LISTENER_SECURITY_PROTOCOL_MAP: INTERNAL:PLAINTEXT,EXTERNAL:PLAINTEXT
      KAFKA_INTER_BROKER_LISTENER_NAME: INTERNAL
      KAFKA_ZOOKEEPER_CONNECT: "zookeeper:2181"
      KAFKA_BROKER_ID: 1
      KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR: 1
      KAFKA_TRANSACTION_STATE_LOG_REPLICATION_FACTOR: 1
      KAFKA_TRANSACTION_STATE_LOG_MIN_ISR: 1
      KAFKA_NUM_PARTITIONS: 10
    restart: always
    depends_on:
      - zookeeper
    networks:
      - kafka-network

  connector:
    image: confluentinc/cp-kafka-connect:7.6.0
    hostname: kafka-connect
    container_name: kafka-connect
    ports:
      - "8083:8083"
    environment:
      CONNECT_BOOTSTRAP_SERVERS: kafka:19092
      CONNECT_REST_PORT: 8083
      CONNECT_GROUP_ID: compose-connect-group
      CONNECT_CONFIG_STORAGE_TOPIC: docker-connect-configs
      CONNECT_OFFSET_STORAGE_TOPIC: docker-connect-offsets
      CONNECT_STATUS_STORAGE_TOPIC: docker-connect-status
      CONNECT_CONFIG_STORAGE_REPLICATION_FACTOR: 1
      CONNECT_OFFSET_STORAGE_REPLICATION_FACTOR: 1
      CONNECT_STATUS_STORAGE_REPLICATION_FACTOR: 1
      CONNECT_KEY_CONVERTER: "org.apache.kafka.connect.json.JsonConverter"
      CONNECT_VALUE_CONVERTER: "org.apache.kafka.connect.json.JsonConverter"
      CONNECT_INTERNAL_KEY_CONVERTER: "org.apache.kafka.connect.json.JsonConverter"
      CONNECT_INTERNAL_VALUE_CONVERTER: "org.apache.kafka.connect.json.JsonConverter"
      CONNECT_REST_ADVERTISED_HOST_NAME: "kafka-connect"
      CONNECT_LOG4J_ROOT_LOGLEVEL: INFO
      CONNECT_PLUGIN_PATH: "/usr/share/java,/etc/kafka-connect/jars"
    restart: always
    volumes:
      - ./jars:/etc/kafka-connect/jars
    depends_on:
      - zookeeper
      - kafka
    networks:
      - kafka-network

  kowl:
    image: docker.redpanda.com/vectorized/console:v2.3.3
    hostname: kowl
    container_name: kowl
    restart: on-failure
    entrypoint: /bin/sh
    command: -c "echo \"$$CONSOLE_CONFIG_FILE\" > /tmp/config.yml; /app/console"
    environment:
      CONFIG_FILEPATH: /tmp/config.yml
      CONSOLE_CONFIG_FILE: |
        kafka:
          brokers: ["kafka:19092"]
        connect:
          enabled: true
          clusters:
            - name: local-kafka
              url: http://kafka-connect:8083
    depends_on:
      - kafka
    networks:
      - kafka-network
    ports:
      - "8086:8080"

networks:
  kafka-network:
    driver: bridge
```

### MariaDB Docker Command

```
docker run --name local-mariadb -d -p 13306:3306 --restart=always -e MYSQL_ROOT_PASSWORD=1234 mariadb
```
