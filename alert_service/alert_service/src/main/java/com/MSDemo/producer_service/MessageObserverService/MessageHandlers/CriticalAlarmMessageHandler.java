package com.MSDemo.producer_service.MessageObserverService.MessageHandlers;

import com.MSDemo.producer_service.KafkaListener.KafkaMessageListener;
import com.MSDemo.producer_service.MessageObserverService.KafkaMessageObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class CriticalAlarmMessageHandler implements KafkaMessageObserver {

    private final KafkaMessageListener kafkaMessageListener;

    @Autowired
    public CriticalAlarmMessageHandler(KafkaMessageListener kafkaMessageListener) {
        this.kafkaMessageListener = kafkaMessageListener;
    }

    @PostConstruct
    public void init() {
        // Register this handler with the KafkaMessageListener
        kafkaMessageListener.registerObserver("com.reddy.alarm.critical", this);
        System.out.println("CriticalAlarmMessageHandler initialized and registered with KafkaMessageListener.");
    }

    @Override
    public void handleMessage(String message) {
        // Logic to handle critical alarm messages
        System.out.println("Handling critical alarm message: " + message);
        // Here you can add the logic to process the critical alarm message
        // For example, you might want to log it, send notifications, etc.
    }
}
