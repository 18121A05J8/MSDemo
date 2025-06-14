package com.MSDemo.producer_service.AlarmProducer;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MajorAlarmProducer implements AlarmProducer {

    private static MajorAlarmProducer instance = null;
    private static MajorAlarmGenerator majorAlarmGenerator = null;

    private MajorAlarmProducer() {}

    public static MajorAlarmProducer getInstance() {
        if (instance == null) {
            synchronized (MajorAlarmProducer.class) {
                if (instance == null) {
                    instance = new MajorAlarmProducer();
                    majorAlarmGenerator = new MajorAlarmGenerator();
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
            System.out.println("Major alarm generated at: " + System.currentTimeMillis());
            //Todo:push message to kafka
        }
    }
}
