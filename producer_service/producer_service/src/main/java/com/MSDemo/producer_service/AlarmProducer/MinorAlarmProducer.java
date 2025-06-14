package com.MSDemo.producer_service.AlarmProducer;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MinorAlarmProducer implements AlarmProducer {

    private static MinorAlarmProducer instance = null;
    private static MinorAlarmGenerator minorAlarmGenerator = null;

    private MinorAlarmProducer() {}

    public static MinorAlarmProducer getInstance() {
        if (instance == null) {
            synchronized (MinorAlarmProducer.class) {
                if (instance == null) {
                    instance = new MinorAlarmProducer();
                    minorAlarmGenerator = new MinorAlarmGenerator();
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
            System.out.println("Minor alarm generated at: " + System.currentTimeMillis());
            //Todo:push notification to kafka
        }
    }
}
