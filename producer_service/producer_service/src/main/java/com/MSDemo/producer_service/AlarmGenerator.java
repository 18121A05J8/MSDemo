package com.MSDemo.producer_service;

import com.MSDemo.producer_service.AlarmProducer.*;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

@Service
public class AlarmGenerator {
    CriticalAlarmProducer criticalAlarmProducer;
    MajorAlarmProducer majorAlarmProducer;
    MinorAlarmProducer minorAlarmProducer;
    NormalAlarmProducer normalAlarmProducer;

    public void generateAlarms() {
        List<AlarmProducer> alarmProducerList = new ArrayList<>(List.of(criticalAlarmProducer,
                majorAlarmProducer,
                minorAlarmProducer,
                normalAlarmProducer));
        for (AlarmProducer alarmProducer : alarmProducerList) {
            alarmProducer.start();
        }
    }

    @Autowired
    @Lazy
    public void setCriticalAlarmProducer(CriticalAlarmProducer criticalAlarmProducer) {
        this.criticalAlarmProducer = criticalAlarmProducer;
    }

    @Autowired
    @Lazy
    public void setMajorAlarmProducer(MajorAlarmProducer majorAlarmProducer) {
        this.majorAlarmProducer = majorAlarmProducer;
    }

    @Autowired
    @Lazy
    public void setMinorAlarmProducer(MinorAlarmProducer minorAlarmProducer) {
        this.minorAlarmProducer = minorAlarmProducer;
    }

    @Autowired
    @Lazy
    public void setNormalAlarmProducer(NormalAlarmProducer normalAlarmProducer) {
        this.normalAlarmProducer = normalAlarmProducer;
    }
}
