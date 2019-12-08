package edu.neu.cs.cs6650.kafka;

import java.util.Properties;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.streams.StreamsConfig;

public class StreamProducer {
  private static Properties props = new Properties();

  static {
    props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConfig.KAFKA_BROKERS);
    props.put(StreamsConfig.CLIENT_ID_CONFIG, KafkaConfig.CLIENT_ID);

    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
    props.put("acks", "all");
    props.put("retries", 0);
    props.put("batch.size", 16384);
    props.put("linger.ms", 1);
    props.put("buffer.memory", 33554432);
  }

  private static Producer<String, String> producer = new KafkaProducer<>(props);

  public static void sendKafkaMessage(String topic, String payload) {
    producer.send(new ProducerRecord<>(topic, payload));
  }
}
