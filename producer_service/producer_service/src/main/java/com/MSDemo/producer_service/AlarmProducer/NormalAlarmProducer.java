package com.MSDemo.producer_service.AlarmProducer;

import com.MSDemo.producer_service.AlarmDAO.AlarmCause;
import com.MSDemo.producer_service.AlarmDAO.AlarmPO;
import com.MSDemo.producer_service.AlarmDAO.AlarmType;
import lombok.NoArgsConstructor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
@NoArgsConstructor
public class NormalAlarmProducer extends AbstractAlarmProducer {

    private NormalAlarmGenerator normalAlarmGenerator;

    @Value("${alarm.producer.normal.initialDelay}")
    private long initialDelay;
    @Value("${alarm.producer.normal.period}")
    private long period;

    @PostConstruct
    public void init() {
        producer = initKafkaProducer();
    }

    @Override
    public void start(){
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(normalAlarmGenerator, initialDelay, period, TimeUnit.SECONDS);
    }

    @Autowired
    public void setNormalAlarmGenerator(NormalAlarmGenerator normalAlarmGenerator) {
        this.normalAlarmGenerator = normalAlarmGenerator;
    }

    @Component
    @Lazy
    private static class NormalAlarmGenerator implements Runnable{
        @Override
        public void run() {
            AlarmPO alarmPO = getNormalAlarmPO();
            String topic = "com.reddy.alarm.normal";
            ProducerRecord<String, String> record = new ProducerRecord<>(topic, null, alarmPO.toString());
            producer.send(record, (metadata, exception) -> {
                if (exception != null) {
                    System.err.println("Kafka send error: " + exception.getMessage());
                } else {
                    System.out.println("Sent to topic: " + metadata.topic() + ", offset: " + metadata.offset());
                }
            });
        }

        public static AlarmPO getNormalAlarmPO() {
            return AlarmPO.AlarmBuilder
                    .newBuilder()
                    .setAlarmType(AlarmType.NORMAL)
                    .setAlarmCause(AlarmCause.HEAT)
                    .setReason("Heat Alert")
                    .setDeviceName("Device4")
                    .setAlarmTime(System.currentTimeMillis())
                    .build();
        }
    }
}
