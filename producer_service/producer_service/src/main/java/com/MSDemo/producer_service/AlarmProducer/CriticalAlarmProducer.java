package com.MSDemo.producer_service.AlarmProducer;

import com.MSDemo.producer_service.AlarmDAO.AlarmCause;
import com.MSDemo.producer_service.AlarmDAO.AlarmPO;
import com.MSDemo.producer_service.AlarmDAO.AlarmType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
@NoArgsConstructor
@ConfigurationProperties(prefix = "alarm.producer.critical") //works same as $Values() in spring boot
public class CriticalAlarmProducer extends AbstractAlarmProducer {

    private CriticalAlarmGenerator criticalAlarmGenerator;

    @Getter
    @Setter
    private long initialDelay;
    @Setter
    @Getter
    private long period;

    @PostConstruct
    public void init() {
        criticalAlarmGenerator = new CriticalAlarmGenerator();
        producer = initKafkaProducer();
    }

    @Override
    public void start() {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(criticalAlarmGenerator, initialDelay ,period, TimeUnit.SECONDS);
    }

    @Autowired
    public void setCriticalAlarmGenerator(CriticalAlarmGenerator criticalAlarmGenerator) {
        this.criticalAlarmGenerator = criticalAlarmGenerator;
    }

    @Component
    @Lazy
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
