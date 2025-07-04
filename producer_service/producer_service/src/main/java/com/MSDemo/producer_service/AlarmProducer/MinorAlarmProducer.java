package com.MSDemo.producer_service.AlarmProducer;

import com.MSDemo.producer_service.AlarmDAO.AlarmCause;
import com.MSDemo.producer_service.AlarmDAO.AlarmPO;
import com.MSDemo.producer_service.AlarmDAO.AlarmType;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class MinorAlarmProducer extends AbstractAlarmProducer {

    private MinorAlarmGenerator minorAlarmGenerator;

    @PostConstruct
    public void init() {
        producer = initKafkaProducer();
    }

    @Override
    public void start(){
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(minorAlarmGenerator, 0, 10, TimeUnit.SECONDS);
    }

    @Autowired
    public void setMinorAlarmGenerator(MinorAlarmGenerator minorAlarmGenerator) {
        this.minorAlarmGenerator = minorAlarmGenerator;
    }

    @Component
    @Lazy
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
