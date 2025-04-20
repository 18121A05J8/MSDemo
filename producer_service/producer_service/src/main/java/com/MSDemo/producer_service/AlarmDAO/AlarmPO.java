package com.MSDemo.producer_service.AlarmDAO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class AlarmPO {
    @Setter
    @Getter
    private AlarmType alarmType;
    private AlarmCause alarmCause;
    private String reason;
    @Setter
    @Getter
    private String deviceName;

    public AlarmCause getAlarmCause() {
        return alarmCause;
    }

    public void setAlarmCause(AlarmCause alarmCause) {
        this.alarmCause = alarmCause;
        setReason(alarmCause.toString());
    }

    public String getReason() {
        return reason;
    }

    private void setReason(String reason) {
        this.reason = reason;
    }
}
