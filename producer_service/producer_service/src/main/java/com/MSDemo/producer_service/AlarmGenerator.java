package com.MSDemo.producer_service;

import com.MSDemo.producer_service.AlarmProducer.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
    public void setCriticalAlarmProducer(CriticalAlarmProducer criticalAlarmProducer) {
        this.criticalAlarmProducer = criticalAlarmProducer;
    }

    @Autowired
    public void setMajorAlarmProducer(MajorAlarmProducer majorAlarmProducer) {
        this.majorAlarmProducer = majorAlarmProducer;
    }

    @Autowired
    public void setMinorAlarmProducer(MinorAlarmProducer minorAlarmProducer) {
        this.minorAlarmProducer = minorAlarmProducer;
    }

    @Autowired
    public void setNormalAlarmProducer(NormalAlarmProducer normalAlarmProducer) {
        this.normalAlarmProducer = normalAlarmProducer;
    }
}
