package com.MSDemo.producer_service.AlarmProducer;

import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class NormalAlarmProducer extends AbstractAlarmProducer {

    private static NormalAlarmProducer instance = null;
    private static NormalAlarmGenerator normalAlarmGenerator = null;

    private NormalAlarmProducer() {}

    public static NormalAlarmProducer getInstance() {
        if (instance == null) {
            synchronized (NormalAlarmProducer.class) {
                if (instance == null) {
                    instance = new NormalAlarmProducer();
                    normalAlarmGenerator = new NormalAlarmGenerator();
                    producer = initKafkaProducer();
                }
            }
        }
        return instance;
    }

    @Override
    public void start(){
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(normalAlarmGenerator, 0, 10, TimeUnit.SECONDS);
    }

    private static class NormalAlarmGenerator implements Runnable{
        @Override
        public void run() {
            String message = "Normal alarm generated at: " + System.currentTimeMillis();
            String topic = "com.reddy.alarm.normal";
            ProducerRecord<String, String> record = new ProducerRecord<>(topic, null, message);
            producer.send(record, (metadata, exception) -> {
                if (exception != null) {
                    System.err.println("Kafka send error: " + exception.getMessage());
                } else {
                    System.out.println("Sent to topic: " + metadata.topic() + ", offset: " + metadata.offset());
                }
            });
        }
    }
}
