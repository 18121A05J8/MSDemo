package com.MSDemo.producer_service.AlarmProducer;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class NormalAlarmProducer implements AlarmProducer {

    private static NormalAlarmProducer instance = null;
    private static NormalAlarmGenerator normalAlarmGenerator = null;

    private NormalAlarmProducer() {}

    public static NormalAlarmProducer getInstance() {
        if (instance == null) {
            synchronized (NormalAlarmProducer.class) {
                if (instance == null) {
                    instance = new NormalAlarmProducer();
                    normalAlarmGenerator = new NormalAlarmGenerator();
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
            //Todo: push notification to kafka
        }
    }
}
