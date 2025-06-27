package com.MSDemo.producer_service.AlarmProducer;

import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MajorAlarmProducer extends AbstractAlarmProducer {

    private static MajorAlarmProducer instance = null;
    private static MajorAlarmGenerator majorAlarmGenerator = null;

    private MajorAlarmProducer() {}

    public static MajorAlarmProducer getInstance() {
        if (instance == null) {
            synchronized (MajorAlarmProducer.class) {
                if (instance == null) {
                    instance = new MajorAlarmProducer();
                    majorAlarmGenerator = new MajorAlarmGenerator();
                    producer = initKafkaProducer();
                }
            }
        }
        return instance;
    }

    @Override
    public void start(){
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(majorAlarmGenerator, 0 ,10, TimeUnit.SECONDS);
    }

    private static class MajorAlarmGenerator implements Runnable {
        @Override
        public void run() {
            String message = "Major alarm generated at: " + System.currentTimeMillis();
            String topic = "com.reddy.alarm.major";
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
