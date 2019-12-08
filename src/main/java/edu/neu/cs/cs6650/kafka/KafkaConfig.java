package edu.neu.cs.cs6650.kafka;

public class KafkaConfig {
  public static String KAFKA_BROKERS = "localhost:9092";
  public static Integer MESSAGE_COUNT = 1000;
  public static String CLIENT_ID = "skier-servlet";

  public static String GROUP_ID_CONFIG = "consumerGroup1";
  public static Integer MAX_NO_MESSAGE_FOUND_COUNT = 100;
  public static String OFFSET_RESET_LATEST = "latest";
  public static String OFFSET_RESET_EARLIEST = "earliest";
  public static Integer MAX_POLL_RECORDS = 1;

  // Topics
  public static String INPUT_TOPIC = "lift-usage-input";
  public static String OUTPUT_TOPIC = "lift-usage-output";
}
