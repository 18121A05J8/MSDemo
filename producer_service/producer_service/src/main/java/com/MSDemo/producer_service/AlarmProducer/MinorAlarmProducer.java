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
@ConfigurationProperties(prefix = "alarm.producer.minor") // works same as $Values() in spring boot
public class MinorAlarmProducer extends AbstractAlarmProducer {

    private MinorAlarmGenerator minorAlarmGenerator;

    @Setter
    @Getter
    private long initialDelay;
    @Setter
    @Getter
    private long period;

    @PostConstruct
    public void init() {
        producer = initKafkaProducer();
    }

    @Override
    public void start(){
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(minorAlarmGenerator, initialDelay, period, TimeUnit.SECONDS);
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
