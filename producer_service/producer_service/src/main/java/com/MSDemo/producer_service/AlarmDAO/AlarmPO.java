package com.MSDemo.producer_service.AlarmDAO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
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
    @Setter
    @Getter
    private long alarmTime;

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

    @Override
    public String toString() {
        return "AlarmPO{" +
                "alarmType=" + alarmType +
                ", alarmCause=" + alarmCause +
                ", reason='" + reason + '\'' +
                ", deviceName='" + deviceName + '\'' +
                ", alarmTime=" + alarmTime +
                '}';
    }

    public static class AlarmBuilder {
        private AlarmType alarmType;
        private AlarmCause alarmCause;
        private String reason;
        private String deviceName;
        private long alarmTime;
        public static AlarmBuilder newBuilder() {
            return new AlarmBuilder();
        }
        public AlarmBuilder setAlarmType(AlarmType alarmType) {
            this.alarmType = alarmType;
            return this;
        }
        public AlarmBuilder setAlarmCause(AlarmCause alarmCause) {
            this.alarmCause = alarmCause;
            return this;
        }
        public AlarmBuilder setReason(String reason) {
            this.reason = reason;
            return this;
        }
        public AlarmBuilder setDeviceName(String deviceName) {
            this.deviceName = deviceName;
            return this;
        }
        public AlarmBuilder setAlarmTime(long alarmTime) {
            this.alarmTime = alarmTime;
            return this;
        }
        public AlarmPO build(){
            return new AlarmPO(this.alarmType, this.alarmCause, this.reason, this.deviceName, this.alarmTime);
        }
    }
}
