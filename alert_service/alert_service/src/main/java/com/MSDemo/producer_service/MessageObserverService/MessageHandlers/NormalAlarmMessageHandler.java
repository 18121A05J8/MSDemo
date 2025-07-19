package com.MSDemo.producer_service.MessageObserverService.MessageHandlers;

import com.MSDemo.producer_service.KafkaListener.KafkaMessageListener;
import com.MSDemo.producer_service.MessageObserverService.KafkaMessageObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class NormalAlarmMessageHandler implements KafkaMessageObserver {

    private final KafkaMessageListener kafkaMessageListener;

    @Autowired
    public NormalAlarmMessageHandler(KafkaMessageListener kafkaMessageListener) {
        this.kafkaMessageListener = kafkaMessageListener;
    }

    @PostConstruct
    public void init() {
        // Register this handler with the KafkaMessageListener
        kafkaMessageListener.registerObserver("com.reddy.alarm.normal", this);
        System.out.println("NormalAlarmMessageHandler initialized and registered with KafkaMessageListener.");
    }

    @Override
    public void handleMessage(String message) {
        // Logic to handle normal alarm messages
        System.out.println("Handling normal alarm message: " + message);
        // Here you can add the logic to process the normal alarm message
        // For example, you might want to log it, send notifications, etc.
    }
}
