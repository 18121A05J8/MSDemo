package com.MSDemo.producer_service.MessageObserverService.MessageHandlers;

import com.MSDemo.producer_service.KafkaListener.KafkaMessageListener;
import com.MSDemo.producer_service.MessageObserverService.KafkaMessageObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class MajorAlarmMessageHandler implements KafkaMessageObserver {

    private final KafkaMessageListener kafkaMessageListener;

    @Autowired
    public MajorAlarmMessageHandler(KafkaMessageListener kafkaMessageListener) {
        this.kafkaMessageListener = kafkaMessageListener;
    }

    @PostConstruct
    public void init() {
        // Register this handler with the KafkaMessageListener
        kafkaMessageListener.registerObserver("com.reddy.alarm.major", this);
        System.out.println("MajorAlarmMessageHandler initialized and registered with KafkaMessageListener.");
    }

    @Override
    public void handleMessage(String message) {
        // Logic to handle major alarm messages
        System.out.println("Handling major alarm message: " + message);
        // Here you can add the logic to process the major alarm message
        // For example, you might want to log it, send notifications, etc.
    }
}
