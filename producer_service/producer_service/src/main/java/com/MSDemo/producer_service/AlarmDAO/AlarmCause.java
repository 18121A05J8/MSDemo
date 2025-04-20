package com.MSDemo.producer_service.AlarmDAO;

public enum AlarmCause {
    LOS("Loss Of signal"),
    SIGNAL_DEGRADE("Signal Degrade"),
    HEAT("Over Heat"),
    LOW_POWER("Ran out of Power");

    private final String cause;
    AlarmCause(String cause){
        this.cause = cause;
    }

    public String getCause(){
        return cause;
    }
}
