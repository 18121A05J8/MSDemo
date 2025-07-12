package com.MSDemo.producer_service.AlarmProducer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public abstract class AbstractAlarmProducer implements AlarmProducer {
// Abstract class for AlarmProducer to provide common functionality or properties
    protected static KafkaProducer<String, String> producer = null;

    public abstract void start();

    protected static KafkaProducer<String, String> initKafkaProducer() {
        // Initialization logic if needed
        String kafkaBroker = System.getenv().getOrDefault("KAFKA_BROKER", "localhost:9092");

        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaBroker);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.CLIENT_ID_CONFIG, "producer_alarm_client");

        return new KafkaProducer<>(props);
    }
}
