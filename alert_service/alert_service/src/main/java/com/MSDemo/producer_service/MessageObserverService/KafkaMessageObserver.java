package com.MSDemo.producer_service.MessageObserverService;

public interface KafkaMessageObserver {
    void handleMessage(String message);
}
