package com.MSDemo.producer_service.AlarmProducer;

import com.MSDemo.producer_service.AlarmDAO.AlarmCause;
import com.MSDemo.producer_service.AlarmDAO.AlarmPO;
import com.MSDemo.producer_service.AlarmDAO.AlarmType;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MinorAlarmProducer extends AbstractAlarmProducer {

    private static MinorAlarmProducer instance = null;
    private static MinorAlarmGenerator minorAlarmGenerator = null;

    private MinorAlarmProducer() {}

    public static MinorAlarmProducer getInstance() {
        if (instance == null) {
            synchronized (MinorAlarmProducer.class) {
                if (instance == null) {
                    instance = new MinorAlarmProducer();
                    minorAlarmGenerator = new MinorAlarmGenerator();
                    producer = initKafkaProducer();
                }
            }
        }
        return instance;
    }

    @Override
    public void start(){
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(minorAlarmGenerator, 0, 10, TimeUnit.SECONDS);
    }

    private static class MinorAlarmGenerator implements Runnable {
        @Override
        public void run() {
            AlarmPO alarmPO = getMinorAlarmPO();
            String topic = "com.reddy.alarm.minor";
            ProducerRecord<String, String> record = new ProducerRecord<>(topic, null, alarmPO.toString());
            producer.send(record, (metadata, exception) -> {
                if (exception != null) {
                    System.err.println("Kafka send error: " + exception.getMessage());
                } else {
                    System.out.println("Sent to topic: " + metadata.topic() + ", offset: " + metadata.offset());
                }
            });
        }

        public static AlarmPO getMinorAlarmPO() {
            return AlarmPO.AlarmBuilder
                    .newBuilder()
                    .setAlarmType(AlarmType.MINOR)
                    .setAlarmCause(AlarmCause.SIGNAL_DEGRADE)
                    .setReason("Signal Degrade Alert")
                    .setDeviceName("Device3")
                    .setAlarmTime(System.currentTimeMillis())
                    .build();
        }
    }
}
