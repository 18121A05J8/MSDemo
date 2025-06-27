package com.MSDemo.producer_service.AlarmProducer;

import com.MSDemo.producer_service.AlarmDAO.AlarmCause;
import com.MSDemo.producer_service.AlarmDAO.AlarmPO;
import com.MSDemo.producer_service.AlarmDAO.AlarmType;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CriticalAlarmProducer extends AbstractAlarmProducer {

    private static CriticalAlarmProducer instance = null;
    private static CriticalAlarmGenerator criticalAlarmGenerator = null;

    private CriticalAlarmProducer() {}

    public static CriticalAlarmProducer getInstance() {
        if (instance == null) {
            synchronized (CriticalAlarmProducer.class) {
                if (instance == null) {
                    instance = new CriticalAlarmProducer();
                    criticalAlarmGenerator = new CriticalAlarmGenerator();
                    producer = initKafkaProducer();
                }
            }
        }
        return instance;
    }

    @Override
    public void start() {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(criticalAlarmGenerator, 0 ,10, TimeUnit.SECONDS);
    }

    private static class CriticalAlarmGenerator implements Runnable {

        @Override
        public void run() {
            AlarmPO alarmPO = getCriticalAlarmPO();

            String topic = "com.reddy.alarm.critical";
            ProducerRecord<String, String> record = new ProducerRecord<>(topic, null, alarmPO.toString());
            producer.send(record, (metadata, exception) -> {
                if (exception != null) {
                    System.err.println("Kafka send error: " + exception.getMessage());
                } else {
                    System.out.println("Sent to topic: " + metadata.topic() + ", offset: " + metadata.offset());
                }
            });
        }
    }

    private static AlarmPO getCriticalAlarmPO() {
        return AlarmPO.AlarmBuilder
                .newBuilder()
                .setAlarmType(AlarmType.CRITICAL)
                .setAlarmCause(AlarmCause.LOS)
                .setReason("Loss of Signal")
                .setDeviceName("Device1")
                .setAlarmTime(System.currentTimeMillis())
                .build();
    }
}
