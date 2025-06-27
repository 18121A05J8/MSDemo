package com.MSDemo.producer_service.AlarmProducer;

import com.MSDemo.producer_service.AlarmDAO.AlarmCause;
import com.MSDemo.producer_service.AlarmDAO.AlarmPO;
import com.MSDemo.producer_service.AlarmDAO.AlarmType;
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
            AlarmPO alarmPO = getMajorAlarmPO();
            String topic = "com.reddy.alarm.major";
            ProducerRecord<String, String> record = new ProducerRecord<>(topic, null, alarmPO.toString());
            producer.send(record, (metadata, exception) -> {
                if (exception != null) {
                    System.err.println("Kafka send error: " + exception.getMessage());
                } else {
                    System.out.println("Sent to topic: " + metadata.topic() + ", offset: " + metadata.offset());
                }
            });
        }

        private static AlarmPO getMajorAlarmPO() {
            return AlarmPO.AlarmBuilder
                    .newBuilder()
                    .setAlarmType(AlarmType.MAJOR)
                    .setAlarmCause(AlarmCause.LOW_POWER)
                    .setReason("Low Power Alert")
                    .setDeviceName("Device2")
                    .setAlarmTime(System.currentTimeMillis())
                    .build();
        }
    }
}
