# RESTful API with Jersey &amp; Apache Tomcat

A demo project which implements a RESTful API (specs [here](https://app.swaggerhub.com/apis/cloud-perf/SkiDataAPI/1.1)) with JAX-RS framework over Apache Tomcat.  
Related technologies include:

1. **HikariCP** for connection pooling to local mysql instance &amp; production database hosted on AWS RDS
2. **Log4j2** for logging
3. **Redis** for caching requests and implementing `/statistics` API
4. **Kafka Streaming** for data analysis.


## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

What you need to install and how to install them

1. [Redis](https://redis.io/topics/quickstart)
    ```
    wget http://download.redis.io/redis-stable.tar.gz
    tar xvzf redis-stable.tar.gz
    cd redis-stable
    make
    ```
  
    It is a good idea to copy both the Redis server and the command line interface into the proper places, either manually using the following commands:
    
    ```
    sudo cp src/redis-server /usr/local/bin/
    sudo cp src/redis-cli /usr/local/bin/
    Or just using sudo make install.
    ```

2. Log4j2
    
    Include these following dependencies:
    ```
        <dependency>
          <groupId>org.apache.logging.log4j</groupId>
          <artifactId>log4j-api</artifactId>
          <version>2.12.1</version>
        </dependency>
        <dependency>
          <groupId>org.apache.logging.log4j</groupId>
          <artifactId>log4j-core</artifactId>
          <version>2.12.1</version>
        </dependency>
        <dependency>
          <groupId>org.apache.logging.log4j</groupId>
          <artifactId>log4j-slf4j-impl</artifactId>
          <version>2.12.1</version>
        </dependency>
    ``` 
3. [Kafka](https://kafka.apache.org/quickstart)
    
### Installing

1. Start up a local MySql instance for local testing or connect HikariDS to a cloud MySql instance of choice
2. Start up a Redis server

    ```
    redis-server
    ```
    
    Reset Redis cache if necessary using redis-cli
    
    ```
    redis-cli -h localhost -p 6379
    >redis-cli FLUSHALL
    ```
  
3. Start up Zookeeper

    ```
    cd kafka-install-folder
    bin/zookeeper-server-start.sh config/zookeeper.properties
    ```

4. Start up Kafka Broker

    ```
    bin/kafka-server-start.sh config/server.properties
    ```

5. Create a Kafka topic
    ```
    bin/kafka-topics.sh --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic lift-usage-input
    bin/kafka-topics.sh --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic lift-usage-output
    ```

6. Create a Kafka Producer (**optional**)
    
    * Command line:
    
      `bin/kafka-console-producer.sh --broker-list localhost:9092 --topic lift-usage-input`
    * Application:
      
      SkierServlet.java sends liftId to life-usage-input upon receiving POST requests. See `edu.neu.cs.cs6650.kafka.StreamProducer`.
    
7. Create a Kafka Consumer (**optional**)
    
    * Command line:
      ```
      bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic lift-usage-output \
      --from-beginning --formatter kafka.tools.DefaultMessageFormatter --property print.key=true --property print.value=true \
      --property key.deserializer=org.apache.kafka.common.serialization.StringDeserializer --property value.deserializer=org.apache.kafka.common.serialization.LongDeserializer
      ```
    * Application:
      - Run `edu.neu.cs.cs6650.kafka.demo.LiftUsageCount` to read from `lift-usage-input` topic, count frequency by liftId and output results to `lift-usage-output` topic.
      - Run `edu.neu.cs.cs6650.kafka.StreamConsumer` to read from `lift-usage-output` topic and display results to console.
      

## Deployment on AWS

1. Create an EC2 instance

    ##### Installing
    1. Java 8
        ```
        sudo yum install java-1.8.0
        sudo yum remove java-1.7.0-openjdk
        ```
    2. Tomcat 8
        ```
        sudo yum install tomcat8 tomcat8-webapps tomcat8-admin-webapps
        sudo service tomcat8 start
        ```
        
        Allow access to manager-gui. Comment out <!-- Valve --> in both files:
        ```
        sudo vim webapps/manager/META-INF/context.xml
        sudo vim webapps/manager/host-manager/META-INF/context.xml
        ```
        
        Tomcat logs: `/var/log/tomcat8`
    
2. Create an RDS MySql instance

    * Allows inbound security settings from EC2 instance (custom rule using CIDR block)

3. Create an ElastiCache Redis server

4. Create another EC2 instance to host zookeeper/ kafka broker
    1. Set up security settings to allow inbound connections on port 9092 from VPC and/or your machine
    
    2. Update jdk
        ``` 
        sudo yum update -y
        sudo yum install java-1.8.0
        sudo yum remove java-1.7.0-openjdk
        ```
    
    3. Download Kafka
        ```
        wget https://www-eu.apache.org/dist/kafka/2.3.1/kafka-2.3.1-src.tgz
        tar -xzf kafka-2.3.1-src.tgz
        cd kafka-2.3.1-src/
        ```
        
    4. Start Zookeeper
        ```
        bin/zookeeper-server-start.sh -daemon config/zookeeper.properties
        ```
        
        Test that zookeeper is up and running: `echo "ruok" | nc localhost 2181`
    
    5. Set up advertised.listeners config param in **server.properties**
        
        `advertised.listeners=PLAINTEXT://<kafka-hostname>:9092`
        
    6. Start Kafka server
        ```
        bin/kafka-topics.sh –create –bootstrap-server localhost:9092 –replication-factor 1 –partitions 1 –topic lift-usage-input
        bin/kafka-topics.sh –create –bootstrap-server localhost:9092 –replication-factor 1 –partitions 1 –topic lift-usage-output
        ```
        
## Built With

* [Maven](https://maven.apache.org/) - Dependency Management

## Contributing

Please read [CONTRIBUTING.md]() for details on our code of conduct, and the process for submitting pull requests to us.

## Versioning
 

## Authors

* **QA Hoang**

See also the list of [contributors]() who participated in this project.

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

## Acknowledgments