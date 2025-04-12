package com.MSDemo.producer_service;

import com.MSDemo.producer_service.AlarmProducer.*;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;


public class AlarmGenerator {
    private static  AlarmGenerator alarmGenerator = null;
    private AlarmGenerator() {}
    public static AlarmGenerator getInstance() {
        if(alarmGenerator == null) {
            synchronized (AlarmGenerator.class) {
                if(alarmGenerator == null) {
                    alarmGenerator = new AlarmGenerator();
                }
            }
        }
        return alarmGenerator;
    }
    public void generateAlarm() {
        //need to implement Threadpool which takes AlarmProducer Classes
        List<AlarmProducer> alarmProducerList = new ArrayList<>(List.of(CriticalAlarmProducer.getInstance(),
                                                                MajorAlarmProducer.getInstance(),
                                                                MinorAlarmProducer.getInstance(),
                                                                NormalAlarmProducer.getInstance()));
        for (AlarmProducer alarmProducer : alarmProducerList) {
            alarmProducer.start();
        }
    }
}
