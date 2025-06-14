package com.MSDemo.producer_service.AlarmProducer;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CriticalAlarmProducer implements AlarmProducer {

    private static CriticalAlarmProducer instance = null;
    private static CriticalAlarmGenerator criticalAlarmGenerator = null;

    private CriticalAlarmProducer() {}

    public static CriticalAlarmProducer getInstance() {
        if (instance == null) {
            synchronized (CriticalAlarmProducer.class) {
                if (instance == null) {
                    instance = new CriticalAlarmProducer();
                    criticalAlarmGenerator = new CriticalAlarmGenerator();
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
            System.out.println("Critical alarm generated at: " + System.currentTimeMillis());
            //Todo:push message to kafka
        }
    }
}
