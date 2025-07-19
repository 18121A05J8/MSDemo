package com.MSDemo.producer_service.KafkaListener;

import com.MSDemo.producer_service.MessageObserverService.KafkaMessageObserver;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class KafkaMessageListener implements MessageListener<String, String> {

    private final Map<String, KafkaMessageObserver> topicObserverMap = new HashMap<>();

    // Method to register observers
    public void registerObserver(String topic, KafkaMessageObserver observer) {
        topicObserverMap.put(topic, observer);
    }

    // Method to unregister observers (optional)
    public void unregisterObserver(String topic) {
        topicObserverMap.remove(topic);
    }

    @Override
    public void onMessage(ConsumerRecord<String, String> record) {
        // Get the topic and message from the record
        String topic = record.topic();
        String message = record.value();

        // Notify the corresponding observer
        KafkaMessageObserver observer = topicObserverMap.get(topic);
        if (observer != null) {
            observer.handleMessage(message);
        } else {
            System.out.println("No observer found for topic: " + topic);
        }

    }
}
